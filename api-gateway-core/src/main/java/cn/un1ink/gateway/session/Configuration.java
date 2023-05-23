package cn.un1ink.gateway.session;

import cn.un1ink.gateway.bind.GenericReferenceRegistry;
import cn.un1ink.gateway.bind.IGenericReference;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 会话生命周期配置类
 * @author：un1ink
 * @date: 2023/5/23
 */
public class Configuration {

    private final GenericReferenceRegistry registry = new GenericReferenceRegistry(this);

    /** 用服务配置项 */
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

    /** 注册中心配置项 */
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

    /** 泛化调用配置项 */
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {
        // TODO 后续从配置中获取
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-gateway-test");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("cn.un1ink.gateway.rpc.IActivityBooth");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");

        applicationConfigMap.put(applicationConfig.getName(), applicationConfig);
        registryConfigMap.put(applicationConfig.getName(), registryConfig);
        referenceConfigMap.put(referenceConfig.getInterface(), referenceConfig);

    }


    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String registryName) {
        return registryConfigMap.get(registryName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String referenceName) {
        return referenceConfigMap.get(referenceName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName){
        registry.addGenericReference(application, interfaceName, methodName);
    }

    public IGenericReference getGenericReference(String methodName) {
        return registry.getGenericReference(methodName);
    }


}
