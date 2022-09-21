package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.Broadcaster;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMoveToCmdHandler implements CmdHandler<GameMsgProtocol.UserMoveToCmd> {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserMoveToCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd msg) {
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();

        if (null == userId) {
            return;
        }

        GameMsgProtocol.UserMoveToCmd cmd = msg;
        GameMsgProtocol.UserMoveToResult.Builder resultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
        resultBuilder.setMoveUserId(userId);
        resultBuilder.setMoveToPosX(cmd.getMoveToPosX());
        resultBuilder.setMoveToPosY(cmd.getMoveToPosY());

        GameMsgProtocol.UserMoveToResult result = resultBuilder.build();

        Broadcaster.broadcast(result);
    }
}
