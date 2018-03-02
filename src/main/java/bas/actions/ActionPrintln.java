package bas.actions;

import org.w3c.dom.Element;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.action.IAction;

public class ActionPrintln implements IAction {
	
	private boolean done = false;
	
	private String msg;
	
	public void loadData(Element xmlElement) {
		msg = xmlElement.getTextContent();
	}
	
	public void startAction() {
		BurkeyAutopackSystem.log(msg);
		done = true;
	}

	public String getActionType() {
		return "println";
	}

	public float getActionProgress() {
		return 1.0f;
	}

	public boolean isActionDone() {
		return done;
	}
	
	public boolean didActionFail() {
		return false;
	}
	
	public String toString() {
		return msg;
	}
	
}