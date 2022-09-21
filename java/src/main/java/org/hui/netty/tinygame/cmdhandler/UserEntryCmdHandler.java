package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.hui.netty.tinygame.Broadcaster;
import org.hui.netty.tinygame.model.User;
import org.hui.netty.tinygame.model.UserManager;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserEntryCmdHandler implements CmdHandler<GameMsgProtocol.UserEntryCmd> {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserEntryCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd msg) {
        // 从指令对象中获取 userId 与 heroAvatar
        GameMsgProtocol.UserEntryCmd cmd = msg;
        int userId = cmd.getUserId();
        String heroAvatar = cmd.getHeroAvatar();

        // 将用户添加到用户字典
        User user = new User();
        user.setUserId(userId);
        user.setHeroAvatar(heroAvatar);
        UserManager.addUser(user);

        // 将 userId 附着到 Channel
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);

        // 构建 UserEntryResult 对象 并发送
        GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(heroAvatar);
        GameMsgProtocol.UserEntryResult entryResult = resultBuilder.build();
        Broadcaster.broadcast(entryResult);

        LOGGER.info("广播新增用户ID: {}", userId);
    }
}
