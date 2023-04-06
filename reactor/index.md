# Project Reactor

Reactor 是 JVM 的完全非阻塞的反应式编程基础，具有高效的需求管理（以管理**背压**的形式）；
直接与 Java8 函数式 API 集成（特别是 `CompletableFuture` `Stream` `Duration`）；
提供可组合异步序列 API : `Flux` `Mono`

> Reactor Core 运行在 Java8+

Maven 安装

Maven 原生支持 BOM 概念：
```xml
<dependencyManagement> 
    <dependencies>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-bom</artifactId>
            <version>2022.0.5</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

依赖项：
```xml
<dependencies>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-core</artifactId>
    </dependency>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Gradle 安装


