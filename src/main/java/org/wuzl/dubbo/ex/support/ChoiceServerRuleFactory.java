package org.wuzl.dubbo.ex.support;

import java.util.concurrent.ConcurrentHashMap;

public class ChoiceServerRuleFactory {
	private static final ConcurrentHashMap<String, ChoiceServerRule> CHOICE_SERVER_RULE_MAP = new ConcurrentHashMap<String, ChoiceServerRule>();

	public static ChoiceServerRule getChoiceServerRule(String serviceName) {
		return CHOICE_SERVER_RULE_MAP.get(serviceName);
	}

	public static boolean putRule(String serviceName, ChoiceServerRule rule) {
		if (rule == null) {
			return false;
		}
		return CHOICE_SERVER_RULE_MAP.putIfAbsent(serviceName, rule) == rule;
	}
}
