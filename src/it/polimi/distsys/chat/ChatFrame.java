package it.polimi.distsys.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ChatFrame extends JFrame {
	private static final long serialVersionUID = 5288322019518493055L;
	private JTextArea chatPanel = new JTextArea();
	private JPanel inputPanel = new JPanel();
	private JTextField textField = new JTextField();
	private JLabel nickname = new JLabel("nickname");

	public ChatFrame(String title) {
		super(title);
		this.setPreferredSize(new Dimension(800, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		chatPanel.setEditable(false);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		chatPanel.setBorder(BorderFactory.createCompoundBorder(null, padding));
		nickname.setBorder(BorderFactory.createCompoundBorder(null, padding));
		textField.addKeyListener(new SubmitListener());
		this.add(chatPanel, BorderLayout.CENTER);
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(nickname, BorderLayout.WEST);
		inputPanel.add(textField, BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.SOUTH);
		pack();
		textField.requestFocusInWindow();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	class SubmitListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// not implemented
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
				chatPanel.append(nickname.getText() + ": "
						+ textField.getText() + "\n");
				textField.setText("");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// not implemented
		}

	}

}
