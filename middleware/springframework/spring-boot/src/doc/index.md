# SpringBoot

1. `2014.04` SpringBoot 1.0 最基础的版本
2. `2014.06` SpringBoot 1.1 改进模板支持
3. `2015.03` SpringBoot 1.2 (SpringFramework 4.1)支持 Servlet 3.1 Tomcat 8
4. `2016.12` SpringBoot 1.3 (SpringFramework 4.2) 缓存实现
5. `2017.01` SpringBoot 1.4 (SpringFramework 4.3)
6. `2017.02` SpringBoot 1.5 支持 Kafka Idap
7. `2018.03` SpringBoot 2.0 Java 8 Java 9 Quartz

> SpringBoot 核心理念：**约定优于配置**

> SpringFramework 发布节点：
> 
> 1. `2004.03.24` SpringFramework 1.0 注解启蒙阶段 `@Transaction`
> 2. `2006.10.13` SpringFramework 2.0 注解驱动过渡期 `@Required` `@Repository` `@Aspect` `@Autowired` `@Qualifier` `@Component` `@Service` `@Controller` `@RequestMapping`
> 3. `2009.12.16` SpringFramework 3.0 注解驱动黄金时期 `@Configuration` `@ImportResource` `@ComponentScan` `@Import`
> 4. `2013.11.01` SpringFramework 4.0 注解驱动完善时代 `@Conditional` `@EventListener` `@AliasFor` `@CrossOrigin`
> 5. `2017.09.28` SpringFramework 5.0 注解驱动成熟期 `@Indexed`


## SPI Service Provider Interface

在 `META-INF/services` 下创建接口名的文件，然后在文件中时接口的具体实现类；
