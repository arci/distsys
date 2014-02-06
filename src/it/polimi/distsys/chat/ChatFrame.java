package it.polimi.distsys.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public class ChatFrame extends JFrame {
	private static final long serialVersionUID = 5288322019518493055L;
	private JTextArea chat = new JTextArea();
	JScrollPane scroll = new JScrollPane(chat,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JPanel inputPanel = new JPanel();
	private JTextField textField = new JTextField();
	private JLabel nickname = new JLabel("nickname");
	private static ChatFrame instance;

	public static ChatFrame get() {
		if (instance == null) {
			instance = new ChatFrame();
		}
		return instance;
	}

	private ChatFrame() {
		super("Secure Group Communication");
		this.setPreferredSize(new Dimension(800, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		chat.setEditable(false);
		DefaultCaret caret = (DefaultCaret) chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		chat.setBorder(BorderFactory.createCompoundBorder(null, padding));
		nickname.setBorder(BorderFactory.createCompoundBorder(null, padding));
		textField.addKeyListener(new SubmitListener());
		this.add(scroll, BorderLayout.CENTER);
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(nickname, BorderLayout.WEST);
		inputPanel.add(textField, BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.SOUTH);
		pack();
		textField.requestFocusInWindow();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void append(String message) {
		this.chat.append(nickname.getText() + "> " + message + "\n");
	}

	public void print(String message) {
		this.chat.append(message + "\n");
	}

	public void debug(String debugMessage) {
		this.chat.append(debugMessage + "\n");
	}

	public String getMessage() throws InterruptedException {
		synchronized (textField) {
			if (this.textField.getText().equals("")) {
				textField.wait();
			}
			String text = this.textField.getText();
			textField.setText("");
			return text;
		}
	}

	public void setNickname(String nickname) {
		this.nickname.setText(nickname);
	}

	private class SubmitListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// not implemented
		}

		@Override
		public void keyPressed(KeyEvent e) {
			synchronized (textField) {
				if (e.getKeyCode() == 10) {
					append(textField.getText());
					textField.notify();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// not implemented
		}

	}

}
