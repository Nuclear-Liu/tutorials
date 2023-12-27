package org.hui.mapper;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.domain.City;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    private static final Logger LOGGER = LogManager.getLogger(CityMapperTest.class);

    @Test
    void selectCities() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        // SqlSessionFactory 实例不是通过 DefaultSqlSessionFactory 获取；
        // 而是通过 Builder 构建
        // # 为什么不直接使用 DefaultSqlSessionFactory 而使用构造模式
        //   * SqlSessionFactory 应该是单例；
        //   * 全局配置文件和映射文件只需要在系统启动时完成加载操作（仅一次）；
        // 通过建造者模式构建复杂的 DefaultSqlSessionFactory 对象，完成如上两件任务【配置文件、Factory单例对象】
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 创建数据库会话
        SqlSession session = sessionFactory.openSession();
        // 获取映射接口代理对象
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        List<City> cities = cityMapper.selectCities();
        cities.forEach(LOGGER::info);
        session.close();
    }

    @Test
    void selectCityById() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = sessionFactory.openSession();
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        City city = cityMapper.selectCityById(2);
        LOGGER.info("result: {}",city);
        session.close();
    }

    @Test
    public void testSelect() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = sessionFactory.openSession();
        List<City> cities = session.<City>selectList("org.hui.mapper.CityMapper.selectCities");
        LOGGER.info("selectCities size: {}", cities.size());
        PageHelper.startPage(0, 2);
        cities = session.selectList("org.hui.mapper.CityMapper.selectCities");
        LOGGER.info("selectCities with page size: {}", cities.size());
    }

    @Test
    public void testSessionCache() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sessionFactory.openSession();
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        List<City> cities = cityMapper.selectCities();
        System.out.println("first query: " + cities.size());
        cities = cityMapper.selectCities();
        System.out.println("second query use cache:" + cities.size());
        session.close();
        session = sessionFactory.openSession();
        cityMapper = session.getMapper(CityMapper.class);
        cities = cityMapper.selectCities();
        System.out.println("third query no use cache:" + cities.size());
        session.close();
        inputStream.close();
    }
}