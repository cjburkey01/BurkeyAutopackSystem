package bas.actions;

import java.io.File;
import org.w3c.dom.Element;
import com.cjburkey.autopacker.FileSafe;
import com.cjburkey.autopacker.action.IAction;

public class ActionDelete implements IAction {
	
	private boolean done = false;
	
	private String file;
	
	public void loadData(Element xmlElement) {
		file = xmlElement.getTextContent();
	}

	public void startAction() {
		File f = FileSafe.getFile(file);
		if (f.exists()) {
			if (!f.delete()) {
				f.deleteOnExit();
			}
		}
		done = true;
	}

	public String getActionType() {
		return "delete";
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
		return "Delete: " + file;
	}
	
}