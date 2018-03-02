package bas.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.Element;
import com.cjburkey.autopacker.FileSafe;
import com.cjburkey.autopacker.action.IAction;

public class ActionDownload implements IAction {
	
	private boolean done = false;
	private boolean fail = false;
	
	private String href;
	private String file;
	
	private float progress = 0.0f;
	
	public void loadData(Element xmlElement) {
		href = xmlElement.getAttribute("href");
		file = xmlElement.getTextContent();
	}
	
	public void startAction() {
		new Thread(() -> {
			try {
				URL url = new URL(href);
				File file = FileSafe.getFile(this.file);
				if (url == null || file == null) {
					fail = true;
					return;
				}
				if (file.exists()) {
					file.delete();
				}
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				long totalFileSize = conn.getContentLengthLong();
				
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
				byte[] data = new byte[1024];
				long totalDownloaded = 0;
				int x = 0;
				while ((x = bis.read(data, 0, 1024)) >= 0) {
					totalDownloaded += x;
					if (totalFileSize > 0) {
						progress = (float) ((double) totalDownloaded / (double) totalFileSize);
					}
					bos.write(data, 0, x);
				}
				bos.close();
				bis.close();
				
				done = true;
			} catch(Exception e) {
				e.printStackTrace();
				fail = true;
			}
		}).start();
	}

	public String getActionType() {
		return "download";
	}

	public float getActionProgress() {
		return progress;
	}

	public boolean isActionDone() {
		return done;
	}
	
	public boolean didActionFail() {
		return fail;
	}
	
	public String toString() {
		return "Download to " + file;
	}
	
}