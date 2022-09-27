package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.Broadcaster;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户攻击指令处理器.
 */
public class UserAttkCmdHandler implements CmdHandler<GameMsgProtocol.UserAttkCmd> {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserAttkCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserAttkCmd msg) {
        if (null == ctx || null == msg) {
            return;
        }

        // 获取攻击者 id
        Integer attkUserId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();

        if (null == attkUserId) {
            return;
        }

        // 获取被攻击者 id
        int targetUserId = msg.getTargetUserId();

        GameMsgProtocol.UserAttkResult.Builder attkResultBuilder = GameMsgProtocol.UserAttkResult.newBuilder();
        attkResultBuilder.setAttkUserId(attkUserId);
        attkResultBuilder.setTargetUserId(targetUserId);

        GameMsgProtocol.UserAttkResult attkResult = attkResultBuilder.build();
        Broadcaster.broadcast(attkResult);

        LOGGER.info("attkUserId: {} targetUserId: {}", attkUserId, targetUserId);

        GameMsgProtocol.UserSubtractHpResult.Builder subtractHpResultBuilder = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        subtractHpResultBuilder.setTargetUserId(targetUserId);
        subtractHpResultBuilder.setSubtractHp(10);

        GameMsgProtocol.UserSubtractHpResult subtractHpResult = subtractHpResultBuilder.build();
        Broadcaster.broadcast(subtractHpResult);
    }
}
