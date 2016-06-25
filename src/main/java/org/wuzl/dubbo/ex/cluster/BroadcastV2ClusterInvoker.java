package org.wuzl.dubbo.ex.cluster;

import java.util.List;

import org.wuzl.dubbo.ex.support.LocalServerUtil;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import com.alibaba.dubbo.rpc.cluster.support.AbstractClusterInvoker;

public class BroadcastV2ClusterInvoker<T> extends AbstractClusterInvoker<T> {

	private static final Logger logger = LoggerFactory
			.getLogger(BroadcastV2ClusterInvoker.class);

	public BroadcastV2ClusterInvoker(Directory<T> directory) {
		super(directory);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result doInvoke(final Invocation invocation,
			List<Invoker<T>> invokers, LoadBalance loadbalance)
			throws RpcException {
		if (invokers == null || invokers.size() == 0) {
			return new RpcResult();
		}
		RpcContext.getContext().setInvokers((List) invokers);
		RpcException exception = null;
		Result result = null;

		for (Invoker<T> invoker : invokers) {
			try {
				if (!LocalServerUtil.isLocalServer(invoker.getUrl()
						.getAddress())) {
					// 非本地的服务远程调用
					result = invoker.invoke(invocation);
				} else {
					// 调用本地服务
					Invoker<T> localInvoker = LocalServerUtil
							.getLocalInvover(invoker.getUrl());
					result = localInvoker.invoke(invocation);
				}
			} catch (RpcException e) {
				exception = e;
				logger.warn(e.getMessage(), e);
			} catch (Throwable e) {
				exception = new RpcException(e.getMessage(), e);
				logger.warn(e.getMessage(), e);
			}
		}
		if (exception != null) {
			throw exception;
		}
		return result;
	}

}