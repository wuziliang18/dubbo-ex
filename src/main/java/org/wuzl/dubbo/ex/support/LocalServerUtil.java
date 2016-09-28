package org.wuzl.dubbo.ex.support;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;

public class LocalServerUtil {

	public static String getServerString(URL url) {
		return url.getHost() + ":" + url.getPort();
	}

	/**
	 * 是否是本地服务
	 * 
	 * @param address
	 * @return
	 */
	public static boolean isLocalServer(String address) {
		Set<String> serverSet = new HashSet<String>();
		DubboProtocol dubboProtocol = DubboProtocol.getDubboProtocol();
		Collection<ExchangeServer> servers = dubboProtocol.getServers();

		for (ExchangeServer server : servers) {
			serverSet.add(getServerString(server.getUrl()));
		}
		return serverSet.contains(address);
	}

	public static String getDubboServerUrl() {
		DubboProtocol dubboProtocol = DubboProtocol.getDubboProtocol();
		Collection<ExchangeServer> servers = dubboProtocol.getServers();

		for (ExchangeServer server : servers) {
			return getServerString(server.getUrl());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> Invoker<T> getLocalInvover(URL url) {
		DubboProtocol dubboProtocol = DubboProtocol.getDubboProtocol();
		Collection<Exporter<?>> exporters = dubboProtocol.getExporters();
		for (Exporter<?> exporter : exporters) {
			if (exporter.getInvoker().getInterface().getName()
					.equals(url.getPath())) {
				return (Invoker<T>) exporter.getInvoker();
			}
		}
		return null;
	}
}
