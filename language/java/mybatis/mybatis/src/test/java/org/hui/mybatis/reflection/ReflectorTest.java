package org.hui.mybatis.reflection;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.invoker.MethodInvoker;
import org.hui.domain.City;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectorTest {
    @Test
    public void test() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflector reflector = new Reflector(City.class);
        Arrays.stream(reflector.getGetablePropertyNames()).forEach(System.out::println);
        City city = (City) reflector.getDefaultConstructor().newInstance();
        System.out.println(city);
    }
    @Test
    public void testInvoker() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Reflector reflector = new DefaultReflectorFactory().findForClass(City.class);
        // 获取到对应的目标对象
        Object city = reflector.getDefaultConstructor().newInstance();
        // 对 Id 字段做赋值操作
        Invoker idSetInvoker = reflector.getSetInvoker("id");
        idSetInvoker.invoke(city,new Object[]{666});
        // 获取对象属性值
        Invoker idGetInvoker = reflector.getGetInvoker("id");
        Object idValue = idGetInvoker.invoke(city,null);
        System.out.println(idValue);
        // 执行普通方法
        Method toString = reflector.getType().getMethod("toString");
        Invoker toStringInvoker = new MethodInvoker(toString);
        Object objToStr = toStringInvoker.invoke(city, null);
        System.out.println(objToStr);
    }
}
