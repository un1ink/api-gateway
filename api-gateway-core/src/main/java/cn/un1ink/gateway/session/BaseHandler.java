package cn.un1ink.gateway.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description: 数据处理基类
 * @author：un1ink
 * @date: 2023/5/22
 */
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, T request) throws Exception {
        session(channelHandlerContext, channelHandlerContext.channel(), request);
    }

    /**
     * @param channelHandlerContext 上下文
     * @param channel 上下文对应channel
     * @param request 待处理请求信息
     */
    protected abstract void session(ChannelHandlerContext channelHandlerContext, final Channel channel, T request);
}
