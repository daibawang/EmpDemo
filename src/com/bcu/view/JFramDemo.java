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
		
		JLabel label = new JLabel("用户名");
		label.setBounds(87, 58, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(97, 95, 72, 18);
		contentPane.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(161, 92, 147, 24);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("失去焦点时调用");
				String username = textField.getText();
				if(username==null||username.trim().length()==0){
					label_3.setText("用户名不能为空");
				}else{
					label_3.setText("");
				}
			}
		});
		textField.setBounds(161, 55, 147, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		//错误信息
		label_2 = new JLabel("");
		label_2.setBounds(145, 128, 85, 18);
		contentPane.add(label_2);
		label_2.setForeground(new Color(255,0,0));
		
		JButton button = new JButton("登录");
		//先记录一下this，因为在下面的方法中 this的指向发生了变化
		final JFramDemo self = this;
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取用户名和密码
				String username = textField.getText();
				String pwd = new String(passwordField.getPassword());
				System.out.println(username);
				System.out.println(pwd);
				// 查询数据库是否有此用户
				/*
				 *  关于数据的操作不要放在此处实现，应该放到专门的类中完成
				 *  
				 *  数据持久化操作  
				 *  			dao层
				 *  				UserDao类
				 *                      查询用户的方法
				 * 
				 */
				UserDao dao = new UserDao();
				User user = dao.findUser(username, pwd);
				if (user == null) {
					//System.out.println("登录失败");
					label_2.setText("登录失败");
				}else{
					System.out.println("登录成功");
					//当前窗口销毁
					self.dispose();
					//显示窗口
					MainFram frame = new MainFram();
					//显示
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
