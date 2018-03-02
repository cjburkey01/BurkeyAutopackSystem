package com.cjburkey.autopacker.update.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import com.alee.utils.SwingUtils;

public class FrameConsole extends JFrame {
	
	private boolean showing = false;
	private boolean first = true;
	
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JPanel core;
	
	public FrameConsole() {
		SwingUtils.invokeLater(() -> {
			core = new JPanel();
			textArea = new JTextArea();
			scrollPane = new JScrollPane(textArea);
			
			core.setLayout(new FlowLayout());
			core.add(scrollPane);
			
			textArea.setText("Please wait, loading...");
			textArea.setColumns(75);
			textArea.setRows(20);
			textArea.setEditable(false);
			textArea.setFont(new Font("Courier New", Font.PLAIN, 13));
			
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			setContentPane(core);
			pack();
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
	
	public void println(String msg) {
		SwingUtils.invokeLater(() -> {
			if (first) {
				textArea.setText("");
				first = false;
			}
			textArea.append(msg + "\n");
			scroll();
		});
	}
	
	public void clear() {
		SwingUtils.invokeLater(() -> {
			textArea.setText("");
			scroll();
		});
	}
	
	private void scroll() {
		SwingUtils.invokeLater(() -> {
			textArea.setCaretPosition(textArea.getDocument().getLength());
		});
	}
	
}