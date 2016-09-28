package org.wuzl.dubbo.ex.balance;

import java.util.List;

import org.wuzl.dubbo.ex.support.ChoiceServerRule;
import org.wuzl.dubbo.ex.support.ChoiceServerRuleFactory;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

public class ManualLoadBalance extends AbstractLoadBalance {

	public static final String NAME = "manual";

	protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url,
			Invocation invocation) {
		String serviceName = url.getParameter("interface");
		ChoiceServerRule rule = ChoiceServerRuleFactory
				.getChoiceServerRule(serviceName);
		if (rule == null) {
			throw new RpcException(String.format("找不到接口【%s】的服务调用规则",
					serviceName));
		}
		String serverName = rule.getServerUrl(invocation.getMethodName(),
				invocation.getArguments());
		if (serverName == null || serverName.equals("")) {
			throw new RpcException(String.format(
					"接口【%s】的服务调用规则有误，必须返回要调用的服务名称", serviceName));
		}
		for (Invoker<T> invoker : invokers) {
			if (invoker.getUrl().getAddress().equals(serverName)) {
				return invoker;
			}

		}
		throw new RpcException(String.format("接口【%s】的服务调用规则有误，或者指定的服务已经不存在",
				serviceName));

	}

}