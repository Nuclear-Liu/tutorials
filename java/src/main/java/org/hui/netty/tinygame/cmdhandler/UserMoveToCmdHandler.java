package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.Broadcaster;
import org.hui.netty.tinygame.model.MoveState;
import org.hui.netty.tinygame.model.User;
import org.hui.netty.tinygame.model.UserManager;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMoveToCmdHandler implements CmdHandler<GameMsgProtocol.UserMoveToCmd> {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserMoveToCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd msg) {
        if (null == ctx || null == msg) {
            return;
        }

        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();

        if (null == userId) {
            return;
        }

        User movedUser = UserManager.getUser(userId);
        if (null == movedUser) {
            return;
        }

        MoveState moveState = movedUser.getMoveState();
        moveState.setFromPosX(msg.getMoveFromPosX());
        moveState.setFromPosY(msg.getMoveFromPosY());
        moveState.setToPosX(msg.getMoveToPosX());
        moveState.setToPosY(msg.getMoveToPosY());
        moveState.setStartTime(System.currentTimeMillis());


        GameMsgProtocol.UserMoveToResult.Builder resultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
        resultBuilder.setMoveUserId(userId);
        resultBuilder.setMoveToPosX(moveState.getToPosX());
        resultBuilder.setMoveToPosY(moveState.getToPosY());
        resultBuilder.setMoveFromPosX(moveState.getFromPosX());
        resultBuilder.setMoveFromPosY(moveState.getFromPosY());
        resultBuilder.setMoveStartTime(moveState.getStartTime());

        GameMsgProtocol.UserMoveToResult result = resultBuilder.build();

        Broadcaster.broadcast(result);
    }
}
