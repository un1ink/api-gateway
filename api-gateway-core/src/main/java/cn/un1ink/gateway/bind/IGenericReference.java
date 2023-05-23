package cn.un1ink.gateway.bind;

/**
 * @description: 统一RPC泛化调用接口
 * @author：un1ink
 * @date: 2023/5/23
 */
public interface IGenericReference {
    String $invoke(String args);
}
