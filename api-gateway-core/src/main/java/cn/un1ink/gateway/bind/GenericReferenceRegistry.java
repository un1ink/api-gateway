package cn.un1ink.gateway.bind;

import cn.un1ink.gateway.session.Configuration;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 泛化调用注册中心
 * @author：un1ink
 * @date: 2023/5/23
 */
public class GenericReferenceRegistry {

    private final Configuration configuration;

    /** 代理工厂返回值 */
    private final Map<String, GenericReferenceProxyFactory> genericReferenceProxyFactoryHashMap= new HashMap<>();

    public GenericReferenceRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public IGenericReference getGenericReference(String methodName) {
        GenericReferenceProxyFactory genericReferenceProxyFactory = genericReferenceProxyFactoryHashMap.get(methodName);
        if(null != genericReferenceProxyFactory) {
            throw new RuntimeException("Type " + methodName + " is not exist in the genericReferenceRegistry.");
        }
        return genericReferenceProxyFactory.newInstance(methodName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
        RegistryConfig registryConfig = configuration.getRegistryConfig(application);
        ReferenceConfig<GenericService> referenceConfig = configuration.getReferenceConfig(interfaceName);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig).registry(registryConfig).reference(referenceConfig).start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(referenceConfig);

        genericReferenceProxyFactoryHashMap.put(methodName, new GenericReferenceProxyFactory(genericService));


    }

}
