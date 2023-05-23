package cn.un1ink.gateway.bind;

import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description: 泛化调用代理
 * @author：un1ink
 * @date: 2023/5/23
 */
public class GenericReferenceProxy implements MethodInterceptor {

    private final GenericService genericService;

    private final String methodName;

    public GenericReferenceProxy(GenericService genericService, String methodName) {
        this.genericService = genericService;
        this.methodName = methodName;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameters = new String[parameterTypes.length];
        for(int i = 0; i < parameterTypes.length; i++){
            parameters[i] = parameterTypes[i].getName();
        }
        return genericService.$invoke(methodName, parameters, args);
    }
}
