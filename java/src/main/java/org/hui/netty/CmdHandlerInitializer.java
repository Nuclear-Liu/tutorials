package org.hui.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    final byte SPACE = (byte) ' ';
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
    }
}
