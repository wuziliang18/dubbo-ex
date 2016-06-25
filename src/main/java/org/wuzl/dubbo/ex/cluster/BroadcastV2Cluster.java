package org.wuzl.dubbo.ex.cluster;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.alibaba.dubbo.rpc.cluster.Directory;

/**
 * 调用所有服务 如果是本地服务内部调用
 * 
 * @author wuzl
 * 
 */
public class BroadcastV2Cluster implements Cluster {

	public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
		return new BroadcastV2ClusterInvoker<T>(directory);
	}

}