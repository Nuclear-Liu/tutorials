package org.hui.netty.tinygame.cmdhandler;

import com.google.protobuf.GeneratedMessageV3;
import org.hui.netty.tinygame.util.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class CmdHandlerFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(CmdHandlerFactory.class);
    /**
     * 处理器字典
     */
    public static final Map<Class<?>, CmdHandler<? extends GeneratedMessageV3>> HANDLER_CACHE = new HashMap<>();

    private CmdHandlerFactory() {
    }

    public static void init() {
        Set<Class<?>> clazzs = PackageUtil.listSubClazz(
                CmdHandler.class.getPackage().getName(),
                true,
                CmdHandler.class);

        LOGGER.info("cmdhanler implements : {}", clazzs.size());

        for (Class<?> clazz : clazzs) {
            if ((clazz.getModifiers() & Modifier.ABSTRACT) != 0) {
                continue;
            }

            Class<?> msgTye = null;

            // 获取方法数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals("handle")) {
                    continue;
                }
                // 获取函数参数类型
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length < 2 ||!GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1])) {
                    continue;
                }

                msgTye = parameterTypes[1];
                break;
            }
            if (null == msgTye) {
                continue;
            }

            try {
                CmdHandler<?> handler = (CmdHandler<?>) clazz.newInstance();
                HANDLER_CACHE.put(msgTye, handler);
                LOGGER.info("put handle cache: {} - {}", msgTye.getName(), handler.getClass().getName());
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public static CmdHandler<? extends GeneratedMessageV3> create(Class<?> msgClazz) {
        if (null == msgClazz) {
            return null;
        }
        return HANDLER_CACHE.get(msgClazz);
    }
}
