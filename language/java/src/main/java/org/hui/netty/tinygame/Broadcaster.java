package org.hui.netty.tinygame;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Broadcaster {
    public static final Logger LOGGER = LoggerFactory.getLogger(Broadcaster.class);
    /**
     * 客户端信道数组
     */
    public static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Broadcaster() {
    }

    /**
     * add channel in broadcast.
     *
     * @param channel
     */
    public static void addChannel(Channel channel) {
        CHANNEL_GROUP.add(channel);
    }

    /**
     * remove channel out broadcast.
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        CHANNEL_GROUP.remove(channel);
    }

    /**
     * broadcast msg.
     *
     * @param msg
     */
    public static void broadcast(Object msg) {
        if (null == msg) {
            return;
        }

        CHANNEL_GROUP.writeAndFlush(msg);
    }
}
