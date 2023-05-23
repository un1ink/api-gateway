package cn.un1ink.gateway;

import cn.un1ink.gateway.session.Configuration;
import cn.un1ink.gateway.session.GenericReferenceSessionFactoryBuilder;
import cn.un1ink.gateway.session.SessionServer;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description:
 * @author：un1ink
 * @date: 2023/5/22
 */
public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_GenericReference() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.addGenericReference("api-gateway-test", "cn.un1ink.gateway.rpc.IActivityBooth", "sayHi");
        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        Future<Channel> future = builder.build(configuration);

        logger.info("服务启动完成 {}", future.get().id());

    }



}
