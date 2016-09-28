package org.wuzl.dubbo.ex.cluster;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.alibaba.dubbo.rpc.cluster.Directory;

/**
 * 调用所有服务 如果是本地服务内部调用 所有结果合并 注意需要返回结果是collection接口实现或者map的实现
 * 
 * @author wuzl
 * 
 */
public class BroadcastAndMergeCluster implements Cluster {
	public final static String NAME = "broadcastAndMerge";

	public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
		return new BroadcastAndMergeClusterInvoker<T>(directory);
	}

}