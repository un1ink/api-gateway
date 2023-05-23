package cn.un1ink.gateway.interfaces;

import cn.un1ink.gateway.rpc.IActivityBooth;
import org.apache.dubbo.config.annotation.Service;

/**
 * @description:
 * @author：un1ink
 * @date: 2023/5/23
 */

@Service(version = "1.0.0")
public class ActivityBooth implements IActivityBooth {
    @Override
    public String sayHi(String str) {
        System.out.println("hi " + str + " by api-gateway-test-provider");
        return "hi " + str + " by api-gateway-test-provider";
    }
}
