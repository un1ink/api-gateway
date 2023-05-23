package cn.un1ink.gateway;


import com.alibaba.fastjson.JSON;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author：un1ink
 * @date: 2023/5/23
 */
public class CGlibTest implements MethodInterceptor {


    @Test
    public void test_cglib() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        // 定义接口
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(new Signature("sayHi", Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
        Class<?> interfaceClass = interfaceMaker.create();

        // 创建代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[]{interfaceClass});
        enhancer.setCallback(this);
        Object obj = enhancer.create();

        // 调用方法
        Method method = obj.getClass().getMethod("sayHi", String.class);
        Object result = method.invoke(obj,"hi xiaofuge");

        System.out.println(result);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return JSON.toJSONString(objects);
    }
}
