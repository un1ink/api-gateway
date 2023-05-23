package cn.un1ink.gateway.bind;

import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.asm.Type;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InterfaceMaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 泛化调用代理工厂
 * @author：un1ink
 * @date: 2023/5/23
 */
public class GenericReferenceProxyFactory {

    private final GenericService genericService;

    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public GenericReferenceProxyFactory(GenericService genericService) {
        this.genericService = genericService;
    }

    public IGenericReference newInstance(String method) {
        return genericReferenceCache.computeIfAbsent(method, k -> {
            GenericReferenceProxy genericReferenceProxy = new GenericReferenceProxy(genericService, method);
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            // Signature用于描述方法签名，构造函数参数为(methodName, returnType, argumentTypes)
            interfaceMaker.add(new Signature(method, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            // IGenericReference    统一泛化调用接口
            // interfaceClass       根据泛化调用注册信息创建的接口，连接 http -> rpc
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }



}
