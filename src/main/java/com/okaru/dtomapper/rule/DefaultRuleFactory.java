package com.okaru.dtomapper.rule;

import com.okaru.dtomapper.exception.RuleException;

@SuppressWarnings("rawtypes")
public class DefaultRuleFactory extends RuleFactory {

	@Override
	protected Rule getRuleInstance(Class<? extends Rule> someRuleClass) throws RuleException {
		return null;
	}

}
