package com.cjburkey.autopacker.update.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import com.alee.utils.SwingUtils;

public class FrameProgress extends JFrame {
	
	private boolean showing = false;
	private boolean first = true;

	private JProgressBar totalProg;
	private JProgressBar perUpdateProg;
	private JProgressBar perActionProg;
	private JPanel core;
	
	public FrameProgress() {
		SwingUtils.invokeLater(() -> {
			core = new JPanel();
			totalProg = new JProgressBar(0, 1000);
			perUpdateProg = new JProgressBar(0, 1000);
			perActionProg = new JProgressBar(0, 1000);
			
			totalProg.setString("Please wait...");
			perUpdateProg.setString("Loading...");
			perActionProg.setString("Quit being so impatient...");
			
			totalProg.setStringPainted(true);
			perUpdateProg.setStringPainted(true);
			perActionProg.setStringPainted(true);
			
			core.setLayout(new BoxLayout(core, BoxLayout.Y_AXIS));
			core.add(totalProg);
			core.add(perUpdateProg);
			core.add(perActionProg);
			
			setType(Type.UTILITY);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			setContentPane(core);
			pack();
			setSize(640, getHeight());
			setTitle("Working");
			setResizable(false);
			setVisible(false);
			setLocationRelativeTo(null);
		});
	}
	
	public void display() {
		SwingUtils.invokeLater(() -> {
			if (!showing) {
				showing = true;
				setVisible(showing);
			}
		});
	}
	
	public void destroy() {
		SwingUtils.invokeLater(() -> {
			if (showing) {
				showing = false;
				setVisible(showing);
				dispose();
			}
		});
	}
	
	public void setUpdateProgress(float progOutOfOne, String text) {
		setProgress(totalProg, progOutOfOne, text);
	}
	
	public void setPerUpdateProgress(float progOutOfOne, String text) {
		setProgress(perUpdateProg, progOutOfOne, text);
	}
	
	public void setPerActionProgress(float progOutOfOne, String text) {
		setProgress(perActionProg, progOutOfOne, text);
	}
	
	private void setProgress(JProgressBar bar, float progOutOfOne, String text) {
		SwingUtils.invokeLater(() -> {
			bar.setString(text);
			bar.setValue((int) (progOutOfOne * 1000));
		});
	}
	
}