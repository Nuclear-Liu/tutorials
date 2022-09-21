package org.hui.netty.tinygame;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息识别器
 */
public final class GameMsgRecognizer {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameMsgRecognizer.class);

    /**
     * 消息编码-消息体字典
     */
    private static final Map<Short, GeneratedMessageV3> MSG_CODE_MSG_BODY_CACHE = new HashMap<>();
    /**
     * 消息类型-消息编码字典
     */
    public static final Map<Class<?>, Short> MSG_CLAZZ_MSG_CODE_CACHE = new HashMap<>();

    private GameMsgRecognizer() {
    }

    public static void init() {
        Class<?>[] innerClazzs = GameMsgProtocol.class.getDeclaredClasses();

        for (Class<?> innerClazz : innerClazzs) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)) {
                continue;
            }
            String clazzName = innerClazz.getSimpleName();

            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String msgCodeName = msgCode.name().replaceAll("_", "");
                if (!msgCodeName.toLowerCase().startsWith(clazzName.toLowerCase())) {
                    continue;
                }

                try {
                    Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);

                    MSG_CODE_MSG_BODY_CACHE.put((short)msgCode.getNumber(), (GeneratedMessageV3) returnObj);
                    LOGGER.info("{} <--> {}",returnObj.getClass().getSimpleName(),  msgCode.getNumber());

                    MSG_CLAZZ_MSG_CODE_CACHE.put(innerClazz, (short)msgCode.getNumber());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    LOGGER.error(e.getMessage(), e);
                }


            }
        }
    }

    public static Message.Builder getBuilder(short msgCode) {
        if (msgCode < 0) {
            return null;
        }
        GeneratedMessageV3 msg = MSG_CODE_MSG_BODY_CACHE.get(msgCode);
        if (null == msg) {
            return null;
        }
        return msg.newBuilderForType();
    }

    public static short getMsgCode(Class<?> msgClazz) {
        if (null == msgClazz) {
            return -1;
        }
        Short msgCode = MSG_CLAZZ_MSG_CODE_CACHE.get(msgClazz);
        if (null != msgCode) {
            return msgCode;
        } else {
            return -1;
        }
    }
}
