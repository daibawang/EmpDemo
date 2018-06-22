package com.bcu.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.bcu.bean.User;
import com.bcu.dao.UserDao;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JFramDemo extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel label_2;
	private JLabel label_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFramDemo frame = new JFramDemo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFramDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("�û���");
		label.setBounds(87, 58, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("����");
		label_1.setBounds(97, 95, 72, 18);
		contentPane.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(161, 92, 147, 24);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("ʧȥ����ʱ����");
				String username = textField.getText();
				if(username==null||username.trim().length()==0){
					label_3.setText("�û�������Ϊ��");
				}else{
					label_3.setText("");
				}
			}
		});
		textField.setBounds(161, 55, 147, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		//������Ϣ
		label_2 = new JLabel("");
		label_2.setBounds(145, 128, 85, 18);
		contentPane.add(label_2);
		label_2.setForeground(new Color(255,0,0));
		
		JButton button = new JButton("��¼");
		//�ȼ�¼һ��this����Ϊ������ķ����� this��ָ�����˱仯
		final JFramDemo self = this;
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ȡ�û���������
				String username = textField.getText();
				String pwd = new String(passwordField.getPassword());
				System.out.println(username);
				System.out.println(pwd);
				// ��ѯ���ݿ��Ƿ��д��û�
				/*
				 *  �������ݵĲ�����Ҫ���ڴ˴�ʵ�֣�Ӧ�÷ŵ�ר�ŵ��������
				 *  
				 *  ���ݳ־û�����  
				 *  			dao��
				 *  				UserDao��
				 *                      ��ѯ�û��ķ���
				 * 
				 */
				UserDao dao = new UserDao();
				User user = dao.findUser(username, pwd);
				if (user == null) {
					//System.out.println("��¼ʧ��");
					label_2.setText("��¼ʧ��");
				}else{
					System.out.println("��¼�ɹ�");
					//��ǰ��������
					self.dispose();
					//��ʾ����
					MainFram frame = new MainFram();
					//��ʾ
					frame.setVisible(true);
				}
		
			}
		});
		button.setBounds(130, 173, 113, 27);
		contentPane.add(button);
		
		label_3 = new JLabel("");
		label_3.setBounds(322, 58, 110, 18);
		contentPane.add(label_3);


	}
}
