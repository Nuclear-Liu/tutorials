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
