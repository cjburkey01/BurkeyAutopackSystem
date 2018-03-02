package com.cjburkey.autopacker.action;

import java.util.Set;
import org.reflections.Reflections;

public class Actions {
	
	private static boolean loaded = false;
	
	private static Set<Class<? extends IAction>> classes;
	
	public static final IAction getAction(String name) throws InstantiationException, IllegalAccessException {
		if (classes == null) {
			Reflections ref = new Reflections("bas.actions");
			classes = ref.getSubTypesOf(IAction.class);
			if (classes.size() < 1) {
				return null;
			}
		}
		for (Class<? extends IAction> clazz : classes) {
			IAction inst = clazz.newInstance();
			if (inst.getActionType().equalsIgnoreCase(name)) {
				return inst;
			}
		}
		return null;
	}
	
}