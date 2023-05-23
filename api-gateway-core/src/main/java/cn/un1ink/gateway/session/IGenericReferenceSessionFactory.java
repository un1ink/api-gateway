package cn.un1ink.gateway.session;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @description: 泛化代理会话工厂接口
 * @author：un1ink
 * @date: 2023/5/23
 */
public interface IGenericReferenceSessionFactory {
    /**
     * @return 开启会话后任务结果
     */
    Future<Channel> openSession() throws  ExecutionException, InterruptedException;
}
