package org.hui.netty.tinygame.cmdhandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

public interface CmdHandler<Tmsg extends GeneratedMessageV3> {
    void handle(ChannelHandlerContext ctx, Tmsg msg);
}
