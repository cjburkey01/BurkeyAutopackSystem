package com.cjburkey.autopacker.action;

import org.w3c.dom.Element;

public interface IAction {
	
	void loadData(Element xmlElement);
	void startAction();
	String getActionType();
	float getActionProgress();
	boolean isActionDone();
	boolean didActionFail();
	String toString();
	
}