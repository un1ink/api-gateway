package cn.un1ink.gateway.session.handler;

import cn.un1ink.gateway.bind.IGenericReference;
import cn.un1ink.gateway.session.BaseHandler;
import cn.un1ink.gateway.session.Configuration;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 会话处理器
 * @author：un1ink
 * @date: 2023/5/22
 */
public class SessionServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(SessionServerHandler.class);

    private final Configuration configuration;

    public SessionServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext channelHandlerContext, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求 uri：{} method：{}", request.uri(), request.method());

        //
        String methodName = request.uri().substring(1);
        if("favicon.ico".equals(methodName)) {
            return;
        }

        // 创建请求返回体，填充响应内容
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        IGenericReference genericReference = configuration.getGenericReference("sayHi");
        String result = genericReference.$invoke("test") + " " + System.currentTimeMillis();
//        response.content().writeBytes(JSON.toJSONBytes("访问路径被网关拦截 uri:" + request.uri(), SerializerFeature.PrettyFormat));
        response.content().writeBytes(JSON.toJSONBytes(result + SerializerFeature.PrettyFormat));


        // 创建请求返回头，填充请求头信息(内容类型、内容长度、支持心跳机制)
        HttpHeaders headers = response.headers();
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset = UTF-8");
        headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        // 配置跨域访问控制
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, true);

        channel.writeAndFlush(response);

    }
}
