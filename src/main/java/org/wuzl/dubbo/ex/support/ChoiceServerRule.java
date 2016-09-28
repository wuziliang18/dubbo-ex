package org.wuzl.dubbo.ex.support;

/**
 * 指定的接口根据规则选择合适的服务端 目前只到接口级别 后期可以扩展
 * 
 * @author wuzl
 * 
 */
public interface ChoiceServerRule {
	/**
	 * 根据参数获取要调用的服务 必须是ip:port 的格式
	 * @param methodName
	 * @param params
	 * @return
	 */
	public String getServerUrl(String methodName,Object[] params);
}
