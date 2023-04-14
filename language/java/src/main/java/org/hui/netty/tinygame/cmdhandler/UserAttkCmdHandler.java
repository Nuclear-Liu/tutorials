package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.Broadcaster;
import org.hui.netty.tinygame.model.User;
import org.hui.netty.tinygame.model.UserManager;
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

        // 获取被攻击用户
        User targetUser = UserManager.getUser(targetUserId);
        if (null == targetUser) {
            return;
        }
        int subtractHp = 10;
        targetUser.setCurrHp(targetUser.getCurrHp() - subtractHp);

        broadcastSubtractHp(targetUserId, subtractHp);

        if (targetUser.getCurrHp() <= 0) {
            broadcastDie(targetUserId);
        }
    }

    /**
     * 广播减血消息.
     * @param targetUserId 被攻击用户
     * @param subtractHp 减血量
     */
    private static void broadcastSubtractHp(int targetUserId, int subtractHp){
        if (targetUserId <= 0 || subtractHp <=0) {
            return;
        }

        GameMsgProtocol.UserSubtractHpResult.Builder subtractHpResultBuilder = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        subtractHpResultBuilder.setTargetUserId(targetUserId);
        subtractHpResultBuilder.setSubtractHp(subtractHp);

        GameMsgProtocol.UserSubtractHpResult subtractHpResult = subtractHpResultBuilder.build();
        Broadcaster.broadcast(subtractHpResult);
    }

    /**
     * 广播死亡消息.
     * @param targetUserId 死亡用户id
     */
    private static void broadcastDie(int targetUserId) {
        if (targetUserId <= 0) {
            return;
        }
        GameMsgProtocol.UserDieResult.Builder userDieResultBuilder = GameMsgProtocol.UserDieResult.newBuilder();
        userDieResultBuilder.setTargetUserId(targetUserId);

        GameMsgProtocol.UserDieResult userDieResult = userDieResultBuilder.build();
        Broadcaster.broadcast(userDieResult);
    }
}
