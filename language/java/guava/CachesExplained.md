# Caches 缓存

> Example:
> 
> 

## 适用性

缓存在各种用例中都非常有用。
例如，当某个值的计算或检索成本很高，而且在某个输入中需要多次使用该值时，就应该考虑使用缓存。

`Cache` 类似于 `CurrentMap` ，但不完全相同。
最根本的区别是， `CurrentMap` 会**保留添加到其中的所有元素**，直到它们被显式删除。
另一方面， `Cache` 通常配置为**自动逐出条目**，以**限制其内存占用**。
在某些情况下， **`LoadCache`** 即使不逐出条目也很有用，因为它会**自动加载缓存**。

通常， Guava 缓存组件适用于以下情况：

* 额外的内存开销提高速度。
* 预计 `key` 会被查询多次。
* 缓存数据不会超过 RAM 中的可用量。（Guava 缓存是应用程序单次运行的**本地**缓存；不会将数据存储在文件中或外部服务器上。）

使用 `CacheBuilder` 构建器模式获得 `Cache` ，重点在于定制缓存。

> **注意：**
> 如果不需要 `Cache` 的功能，那么 `ConcurrentHashMap` 会更节省内存。

## 入口

关于缓存，首先要问自己的问题是：是否有一些*可执行默认*函数来加载或计算与键相关的值？
如果是这样，应该使用 `CacheLoader` 。
如果没有，或者需要覆盖默认值，但仍然想要原子式的 **"get-if-absent-compute"** 语义，你就应该在 `get` 调用中传递一个 `Callable` 。
元素可以使用 `Cache.put()` 直接插入，但**首选**自动缓存加载，因为它可以更轻松推理所有缓存内容的一致性。

#### From a `CacheLoader`

`LoadingCache` 是使用附加的 `CacheLoader` 构建的 `Cache` 。
创建 `CacheLoader` 通常与实现 `V load(K key) throws Exception` 方法一样简单。
例如：

```jshelllanguage
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
       .maximumSize(1000)
       .build(
           new CacheLoader<Key, Graph>() {
             public Graph load(Key key) throws AnyException {
               return createExpensiveGraph(key);
             }
           });

// ...
try {
  return graphs.get(key);
} catch (ExecutionException e) {
  throw new OtherException(e.getCause());
}
```

查询 `LoadingCache` 的标准方法是使用方法 `get(K)` 。
该方法要么返回一个已缓存的值，要么使用缓存的 `CacheLoader` 原子式地将一个新值加载到缓存中。
由于 `CacheLoader` 可能会引发 `Exception` 异常，因此 `LoadingCache.get(K)` 会引发 `ExecutionException` 异常。
（如果缓存加载器抛出一个 _unchecked_ 异常， `get(K)` 将抛出一个包裹它的 `UncheckedExceptionException` 异常。）
您也可以选择使用 `getUnchecked(K)` ，它会用 `UnchedkedExecutionException` 封装所有异常，但如果底层的 `CacheLoader` 通常抛出已检查异常，这可能会导致令人惊讶的行为。

```jshelllanguage
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
       .expireAfterAccess(10, TimeUnit.MINUTES)
       .build(
           new CacheLoader<Key, Graph>() {
             public Graph load(Key key) { // no checked exception
               return createExpensiveGraph(key);
             }
           });

// ...
return graphs.getUnchecked(key);
```

批量查找可以通过方法 `getAll(Iterable<? extends K>)` 来执行。
默认情况下， `getAll` 会为缓存中缺失的每个**键**单独调用 `CacheLoader.load` 。
当批量检索比多次单独查找更有效时，可以覆盖 `CacheLoader.loadAll` 来利用这一点。
`getAll(Iterable)` 的性能也会相应提高。

**注意**，您可以编写一个 `CacheLoader.loadAll()` 实现来加载未被特别请求的键值。
例如，如果计算某个组中的任何键的值都能得到该组中所有键的值。
`loadAll` 可能会同时加载该组的其他键。

#### From a `Callable`

所有 Guava 缓存（无论是否加载）都支持 `get(K, Callable<V>)` 方法。
该方法会返回缓存中与键相关联的值，或从指定的 `Callable` 计算出该值并将其添加到缓存中。
在加载完成之后，与该缓存相关的可观察状态不会被修改。
该方法可简单替代传统的**如果已缓存，则返回；否则创建、缓存并返回**模式。

```jshelllanguage
Cache<Key, Value> cache = CacheBuilder.newBuilder()
	.maximumSize(1000)
	.build(); // look Ma, no CacheLoader
// ...
try {
    // If the key wasn't in the "easy to compute" group, we need to do things the hard way.
	cache.get(key, new Callable<Value>() {
        @Override
	    public Value call() throws AnyException {
            return doThingsTheHardWay(key);
        }
    });
} catch(ExecutionException e) {
    throw new OtherException(e.getCause());
}
```

#### 直接插入

可以使用 `cache.put(key, value)` ，将值直接插入缓存。
这将覆盖缓存中指定键的任何先前条目。
也可以使用由 `Cache.asMap()` 视图公开的任何 `ConcurrentMap` 方法对缓存进行更改。
请注意， `asMap` 视图上的任何方法都不会导致条目被自动加载到缓存中。
此外，该视图上的原子操作不在自动加载缓存的范围内，因此在使用 `CacheLoader` 或 `Callable` 加载值的缓存中， `Cache.get(K, Callable<V>)` 应始终优先于 `Cache.asMap().putIfAbsent()` 。

## 淘汰

残酷的现实是，我们几乎*肯定*没有足够的内存来缓存我们客户以缓存的所有内容。
必须决定：什么时候不值得保留缓存条目？
Guava 提供了三种基本类型的淘汰：**基于大小的淘汰**、**基于时间的淘汰**和**基于引用的淘汰**。

#### 基于大小的淘汰

如果您的缓存不能超过一定的大小，只需使用 `CacheBuilder.maximumSize(long)` 。
缓存会尝试驱逐最近未被使用或不常用的条目。
**警告**：缓存可能在超过此限制前——通常实在缓存大小接近限制时——驱逐条目。

另外，如果不同的缓存条目具有不同的**权重**（例如，如果缓存值占用的内存空间完全不同），则可以使用 `CacheBuilder.weighter(Weighter)` 指定权重函数，并使用 `CacheBuilder.maximumWeigh(long)` 指定最大缓存权重。
除了与 `maximumSize` 相同的注意事项外，请注意权重是在创建条目时计算的，此后将保持静态。

```jshelllanguage
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
       .maximumWeight(100000)
       .weigher(new Weigher<Key, Graph>() {
          public int weigh(Key k, Graph g) {
            return g.vertices().size();
          }
        })
       .build(
           new CacheLoader<Key, Graph>() {
             public Graph load(Key key) { // no checked exception
               return createExpensiveGraph(key);
             }
           });
```

#### 基于时间的淘汰

`CacheBuilder` 提供了两种定时淘汰的方法：

* `expireAfterAccess(long, TimeUnit)` 只有在条目上次读取或写入后的指定持续时间过去后，条目才会过期。请注意，条目被淘汰的顺序与**基于大小的驱逐**类似。
* `expireAfterWrite(long, TimeUnit)` 在条目创建后或最近一次更新后，在指定的持续时间过后淘汰该条目。如果缓存数据在一定时间后变得陈旧，这可能时可取的。

定时过期会在写入过程中定期维护，偶尔也会在读取过程中定期维护，下文将对此进行讨论。

##### 测试定时淘汰

测试定时淘汰并不一定很痛苦...也不一定非要花两秒钟测试两秒钟的过期时间。
使用 `Ticker` 接口和 `CacheBuilder.ticker(Ticker)` 方法在缓存创建器中执行一个时间源，而不必等待系统时钟。

#### 基于引用的淘汰

Guava 允许通过对键或值使用**弱引用**和对值使用**软引**来设置缓存，以允许条目的垃圾收集。

* `CacheBuilder.weakKeys()` 使用弱引用存储键。如果键没有其它（强或软）引用，条目就能够被垃圾回收。由于垃圾回收只能依赖于身份相等，这将导致整个缓存使用身份 `==` 相等来比较键，而不是使用 `equals()` 。
* `CacheBuilder.weakValues()` 使用弱引用存储值。如果没有其他（强或软）引用，条目就能被垃圾回收。由于垃圾回收只依赖于身份相等，这会导致整个缓存使用身份 `==` 相等来比较值，而不是使用 `equals()` 。
* `CacheBuilder.softValues()` 将值封装为软引用。软引用对象会根据内存需求，在全局范围内以最近使用次数最少的方式进行垃圾回收。由于使用软引用会影响性能，通常建议使用更可预测的**最大缓存大小**基于大小的淘汰。使用 `softValues()` 将导致使用身份 `==` 相等而不是 `equals()` 来比较值。

#### 显式删除

可以显式地使缓存条目失效，而不是等待条目被驱逐。

* 单个使用 `Cache.invalidate(key)`
* 批量使用 `Cache.invalidateAll(key)`
* 所有 `Cache.invalidateAll()`

#### 删除监听器

通过 `CacheBuilder.removealListener(RemovealListener)` 为缓存指定一个移除监听器以便在删除设备条目时执行某些操作。
删除监听器会收到一个 `RemovalNotification` ，其中指定了 `RemovalCause` `key` 和 `value` 。

**注意**： `RemovalListener` 抛出的任何异常都会被记录（使用 `Logger` ）并吞没。

```jshelllanguage
CacheLoader<Key, DatabaseConnection> loader = new CacheLoader<Key, DatabaseConnection> () {
  public DatabaseConnection load(Key key) throws Exception {
    return openConnection(key);
  }
};
RemovalListener<Key, DatabaseConnection> removalListener = new RemovalListener<Key, DatabaseConnection>() {
  public void onRemoval(RemovalNotification<Key, DatabaseConnection> removal) {
    DatabaseConnection conn = removal.getValue();
    conn.close(); // tear down properly
  }
};

return CacheBuilder.newBuilder()
  .expireAfterWrite(2, TimeUnit.MINUTES)
  .removalListener(removalListener)
  .build(loader);
```

**警告**：删除监听器操作默认是**同步执行**的，由于缓存维护通常是正在缓存操作期间进行的，因此昂贵的删除监听器会减慢正常缓存功能的运行速度！
如果有一个昂贵的删除监听器，请使用 `RemovalListener.asynchronous(RemovalListener, Executor)` 来装饰一个 `RemovalListener` ，使其异步运行。

#### 何时进行清理？

使用 `CacheBuilder` 构建的缓存不会**自动**或者在值过期后立即执行清理和值淘汰等类似的操作。
相反，它会写入操作期间执行少量维护工作，如果写操作很少，则在偶尔的读取操作期间执行少量维护工作。

这样做的原因：如果想持续执行**缓存**维护，就需要创建一个线程，而线程的操作将与用户操作竞争共享锁。
此外有些环境会限制创建线程，这将使 `CacheBuilder` 在该环境中无法使用。

相反，如果缓存使高吞吐量的，那么不必担心执行缓存维护来清理过期条目等。
如果缓存很少写入，并且不希望清理阻止缓存读取。
可能希望创建自己的维护线程，定期调用 `Cache.cleanUp()` 。

如果想为很少写入的缓存安排定期缓存维护，只需使用 `ScheduleExecutorService` 安排维护即可。

#### 刷新

刷新与淘汰不太一样。
正如 `LoadingCache.refresh(k)` 中所指定的，刷新键会加载键的新值，可能是异步的。
在键被刷新的同时，旧值（如果有的话（）仍会返回，这与淘汰相反，淘汰强制检索等到重新加载值。

**如果刷新时引发异常，则会保留旧值，异常会被记录并吞没。**

`CacheLoader` 可以通过覆盖 `CacheLoader.reload(K, V)` 来指定刷新时使用的智能行为，这样就可以在计算新值时使用旧值。

```jshelllanguage
// Some keys don't need refreshing, and we want refreshes to be done asynchronously.
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
    .maximumSize(1000)
    .rrefreshAfterWrite(1, TimeUnit.MINUTES)
    .build( new CacheLoader<Key, Graph>() {
        public Graph load(Key key) { // no checked exception
            return getGraphFormDatabase(key);
        }
        public ListenableFuture<Graph> reload(final Key key, Graph prevGraph) {
            if (neverNeedsRefresh(key)) {
                return Futures.immediateFuture(prevGraph);
            } else {
                // asynchronous
                ListenableFutureTask<Graph> task = ListenableFutureTask.create(new Callable<Graph>() {
                    public Graph call() {
                        return getGraphFromDatabase(key);
                    }
                });
                executor.execute(task);
                retrun task;
            }
        }
    });
```

可以使用 `CacheBuilder.refreshAfterWrite(long, TimeUnit)` 为缓存添加自动定时刷新功能。
与 `expireAfterWrite` 不同的是， `refreshAfterWrite` 将使一个键在指定的持续时间后有*资格*被刷新，但只有在查询条目时才会实际启动刷新（如果 `CacheLoader.reload` 被实现为异步，那么查询不会因刷新而减慢）。
因此，举例来说，你**可以在同一个缓存上同时指定 `refreshAfterWrite` 和 `expireAfterWrite`** ，这样当一个条目符合刷新条件时，条目的过期计时器就不会被盲目重置，因此如果一个条目符合刷新条件后没有被查询，它就会过期。

## 特性

#### 统计

通过使用 `CacheBuilder.recordStats()` 可以打开 Guava 缓存的统计数据收集。
`Cache.stats()` 方法返回一个 `CacheStats` 对象，其中提供的统计信息包括：

* `hitRate()` 返回命中与请求的比率
* `averageLoadPenalty()` 以纳秒为单位的加载新值所花费的平均时间
* `evictionCount()` 缓存淘汰的次数

以及更多其他统计数据。
这些统计数据对缓存调整至关重要，我们建议性能关键型应用程序关注这些统计数据。

#### `asMap`

可以使用 `asMap` 视图将任何 `Cache` 作为 `ConcurrentMap` 来查看，但需要解释 `asMap` 视图与 `Cache` 的交互方式：

* `cache.asMap()` 包含缓存中当前加载的所有条目。例如 `cache.asMap().keySet()` 包含当前加载的所有键。
* `asMap().get(key)` 本质上等同于 `cache.getIfPassent(key)` ，并且不会导致值被加载。这与 `Map` 契约使一致的。
* 所有缓存读写操作（包括 `Cache.asMap().get(Object)` 和 `Cache.asMap.put(K, V)` ）都会被充值访问时间，但 `containsKey(Object)` 不会，对 `Cache.asMap()` 的集合视图进行的操作也不会。因此举例来说，遍历 `cache.asMap().entrySet()` 不会重置检索的条目的访问时间。

## 中断

加载方法(如 `get`)从不抛出 `InterruptedException` 。
我们本可以在设计这些方法时支持 `InterruptedException` ，但我们的支持将是不完整的，迫使所有用户付出代价，而只有部分用户收益。
有关详细信息，请继续阅读。

请求未缓存值的 `get` 调用可分为两大类：
加载值的调用和等待另一个线程进行中的加载的调用。
这两种调用在支持中断的能力上有所不同。
最简单的情况是等待另一个线程正在进行的加载：
在这种情况下，我们可以进入可中断的等待。
麻烦的情况是我们自己加载值：
在这种情况下，受到用户支持的 `CacheLoader` 的支配。
如果正好支持中断，可以支持中断；如果没有，就不能。

那么，当提供的 `CacheLoader` 支持中断时，为什么不支持中断呢？
从某种意义上说：如果 `CacheLoader` 抛出 `InterruptedException` ，则对该键的所有 `get` 调用将立即返回（就像任何其他异常一样）。
另外， `get` 将恢复加载线程中的中断位。
令人惊讶的部分是 `InterruptedException` 被包装在 `ExecutionException` 中。

原则上，我们可以为您解开此异常。
然而，这迫使所有 `LoadingCache` 用户处理 `InterruptedException` ，即使大多数 `CacheLoader` 实现未抛出它。
当您考虑所有 `no-loading` 线程等待仍然可能被中断时，也许这样做还是值得的。
但许多缓存只在单线程中使用。
它们的用户仍然必须捕获不可能发生的 `InterruptedException` 。
即使是那些跨线程共享缓存的用户，也只能*偶尔*中断它们的 `get` 调用，这取决于那个线程碰巧先发出请求。

我们做出这一决定的**指导原则**是，缓存的行为应如同所有的值都是在调用线程中加载的一样。
根据这一原则，可以很容易地将缓存引入到以钱每次调用都重新计算值的代码中。
如果旧的代码不可中断，那么新代码也可以不中断。

提议 `AsyncLoadingCache` 中投入更多精力，它将返回具有正确中断行为的 `Future` 对象。
