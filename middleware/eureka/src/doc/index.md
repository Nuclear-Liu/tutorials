# Eureka

> Eureka 在 `CAP` 中保证了 `AP` （三级缓存定时同步机制导致）；

`@EnableEurekaServer` 注解的作用 `new EurekaServerMarkerConfiguration.Marker()` `Bean` 对象；

`EurekaServerMarkerConfiguration.Marker` Bean 作为 `EurekaServerAutoConfiguration` 类条件注解的条件 `@ConditionalOnBean(EurekaServerMarkerConfiguration.Marker.class)` ；

[`EurekaServerAutoConfiguration`]() 有 `@Import(EurekaServerInitializerConfiguration.class)` 注解；

`EurekaServerInitializerConfiguration` 实现了 spring-context 的 `SmartLifecycle` 接口；
`SmartLifecycle` 接口继承 spring-context 的 `Lifecycle` 接口

## [`EurekaServerAutoConfiguration`]() 自动配置类



## 服务注册于服务发现

client 向注册中心发送：注册、心跳、下线；向注册中心请求：注册表；

## Eureka 自我保护

设置是否开启自我保护： `eureka.server.enable-self-preservation` ；

设置预值（默认 `0.85` ）： `eureka.server.renewal-percent-threshold` ；

设置剔除服务间隔的时间（单位 `ms` )： `eureka.server.eviction-interval-timer-in-ms` ；

设置是否使用三级缓存（默认 `true` ）： `use-read-only-response-cache` ；

设置三级缓存刷新时间（默认 `3000ms` ，单位 `ms` ）： `response-cache-update-interval-ms` ；

> Eureka (基于内存)三级缓存
> 
> 
> 一级： `registry` : `ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry`
> 
> 二级： `readWriteCacheMap` : `LoadingCache<Key, Value> readWriteCacheMap` 与 `registry` 保持一致；
> 
> 三级： `readOnlyCacheMap` : `ConcurrentMap<Key, Value> readOnlyCacheMap` 每 `30s` 从 `readWriteCacheMap` 同步一次，不是强一致性的；
> 
> 1. 新的客户端注册时，添加到以及缓存 `registry` 同时使 `readWriteCacheMap` 缓存失效;
> 2. 当有新的请求时，首先从 `readOnlyCacheMap` 获取，如果获取结果为 `null` ，从 `readWriteCacheMap` 中获取(`readWriteCacheMap` 的 `get()` 方法会刷新 `readWriteCacheMap` 内容)；通过设置 `use-read-only-response-cache` 为 `false` 加快发现速度；

> `Timer` `ScheduledExecutorService`
> 
> 多线程并发处理定时任务时， `Timer` 运行多个 `TimerTask` 时，只要其中一个没有捕获抛出的异常，其他任务便会自动终止运行，使用 `ScheduledExecutorService` 来避免这个问题；

## Eureka 优化

* 不同数量服务的自我保护；
* 快速下线；
* 服务被发现的速度；
* 三级缓存定时刷新处理方式；
