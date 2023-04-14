package org.hui.netty.tinygame;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.cmdhandler.CmdHandler;
import org.hui.netty.tinygame.cmdhandler.CmdHandlerFactory;
import org.hui.netty.tinygame.model.UserManager;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Broadcaster.addChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("msg class: {}, msg: {}", msg.getClass().getName(), msg);

        CmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.create(msg.getClass());

        if (null != cmdHandler) {
            cmdHandler.handle(ctx, cast(msg));
        }
    }

    /**
     * 消息对象转型.
     *
     * @param msg
     * @param <Tmsg>
     * @return
     */
    private static <Tmsg extends GeneratedMessageV3> Tmsg cast(Object msg) {
        if (null == msg) {
            return null;
        } else {
            return (Tmsg) msg;
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        // 移除已经离线的用户
        Broadcaster.removeChannel(ctx.channel());
        // 移除字典
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == userId) {
            return;
        }
        UserManager.removeUser(userId);

        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);

        GameMsgProtocol.UserQuitResult result = resultBuilder.build();

        Broadcaster.broadcast(result);
    }
}
