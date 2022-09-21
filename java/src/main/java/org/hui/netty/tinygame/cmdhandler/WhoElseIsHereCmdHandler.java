package org.hui.netty.tinygame.cmdhandler;

import io.netty.channel.ChannelHandlerContext;
import org.hui.netty.tinygame.GameMsgEncoder;
import org.hui.netty.tinygame.model.User;
import org.hui.netty.tinygame.model.UserManager;
import org.hui.netty.tinygame.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhoElseIsHereCmdHandler implements CmdHandler<GameMsgProtocol.WhoElseIsHereCmd> {
    public static final Logger LOGGER = LoggerFactory.getLogger(WhoElseIsHereCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd msg) {
        GameMsgProtocol.WhoElseIsHereResult.Builder resultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();

        for (User user : UserManager.userCollection()) {
            if (null == user) {
                continue;
            }
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userBuilder.setUserId(user.getUserId());
            userBuilder.setHeroAvatar(user.getHeroAvatar());
            resultBuilder.addUserInfo(userBuilder);
        }

        GameMsgProtocol.WhoElseIsHereResult result = resultBuilder.build();

        ctx.writeAndFlush(result);
    }
}
