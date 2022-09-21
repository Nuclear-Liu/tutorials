package org.hui.netty.tinygame.cmdhandler;

import com.google.protobuf.GeneratedMessageV3;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(CmdHandlerFactory.class);
    /**
     * 处理器字典
     */
    public static final Map<Class<?>, CmdHandler<? extends GeneratedMessageV3>> handlerCache = new HashMap<>();

    private CmdHandlerFactory() {}

    public static void init() {
        handlerCache.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
        handlerCache.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
        handlerCache.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
    }

    public static CmdHandler<? extends GeneratedMessageV3> create(Class<?> msgClazz) {
        if (null == msgClazz) {
            return null;
        }
        return handlerCache.get(msgClazz);
    }
}
