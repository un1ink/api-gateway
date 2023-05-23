package cn.un1ink.gateway.session;

import cn.un1ink.gateway.session.support.GenericReferenceSessionFactory;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @description: 泛化代理会话工厂构造器
 * @author：un1ink
 * @date: 2023/5/23
 */
public class GenericReferenceSessionFactoryBuilder {

    public Future<Channel> build(Configuration configuration){
        IGenericReferenceSessionFactory genericReferenceSessionFactory = new GenericReferenceSessionFactory(configuration);
        try {
            return genericReferenceSessionFactory.openSession();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
