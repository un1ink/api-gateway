package cn.un1ink.gateway.session;

import cn.un1ink.gateway.session.handler.SessionServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * @description: 会话频道初始化
 * @author：un1ink
 * @date: 2023/5/22
 */
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Configuration configuration;

    public SessionChannelInitializer(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new HttpRequestDecoder());
        channelPipeline.addLast(new HttpRequestEncoder());
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        channelPipeline.addLast(new SessionServerHandler(configuration));
    }
}
