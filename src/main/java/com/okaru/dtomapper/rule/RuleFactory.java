package com.okaru.dtomapper.rule;

import com.okaru.dtomapper.exception.RuleException;

@SuppressWarnings("rawtypes")
public abstract class RuleFactory {
	
	/**
	 * This class should return an instance of the specified rule class.  If an
	 * instance of the specified class cannot be instanciated then this method
	 * should either return null or throw a RuleException.
	 * 
	 * @param someRuleClass
	 * @return
	 */
	protected abstract Rule getRuleInstance(Class<? extends Rule> someRuleClass) throws RuleException;
	
	/**
	 * Returns an instance of the rule specified or null.
	 * 
	 * @param someRuleClass
	 * @return
	 */
	public Rule getRule(Class<? extends Rule> someRuleClass) throws RuleException{
		Rule<?> rule = getRuleInstance(someRuleClass);
		if(rule == null){
			rule = createRule(someRuleClass);
			if(rule == null){
				throw new RuleException("No such rule: " + someRuleClass.getName());
			}
		}
		return rule;
	}

	private Rule createRule(Class<? extends Rule> someClass){
	    try {
	        return someClass.newInstance();
        } catch (InstantiationException e) {
	        e.printStackTrace();
        } catch (IllegalAccessException e) {
	        e.printStackTrace();
        }
	    return null;
	}
}
