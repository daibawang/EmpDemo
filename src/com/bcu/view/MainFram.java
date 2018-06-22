package com.bcu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.bcu.Model.EmpTableModel;
import com.bcu.bean.Emp;
import com.bcu.dao.EmpDao;

public class MainFram extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private JButton btnFirst;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLast;
	
	private int start;
	private int number = 5;
	private EmpTableModel tableModel;
	private JPanel panel_1;
	private JButton btnAdd;
	private JButton btnSave;
	private JButton btnDelete;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//创建方法
					MainFram frame = new MainFram();
					//显示
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
	public MainFram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*
//		使用数组的方式给表格提供数据
		String[][] data = {
				{"张三","22","男"},
				{"张三","22","男"},
				{"张三","22","男"},
				{"张三","22","男"}
		};
		String[] titles = {"姓名","年龄","性别"};
		table = new JTable(data,titles);
//		如果直接将table加到contentPane上，不会显示表头，我们需要在中间加一层
//		JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		*/
		
		tableModel = new EmpTableModel();
		table = new JTable(tableModel);
//		如果直接将table加到contentPane上，不会显示表头，我们需要在中间加一层
//		JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnFirst = new JButton("first");
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = 0;
				//调用dao新的获取数据
				updateTable();
				updateButton();
			}
		});
		panel.add(btnFirst);
		
		btnPrevious = new JButton("previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start -= number;
				updateTable();
				updateButton();
			}
		});
		panel.add(btnPrevious);
		
		btnNext = new JButton("next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start += number;
				updateTable();
				updateButton();
			}
		});
		panel.add(btnNext);
		
		btnLast = new JButton("last");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				start = last();
				updateTable();
				updateButton();
				
			}
		});
		panel.add(btnLast);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
//		panel_1因为设置了绝对布局，需要设置宽高才能显示
		panel_1.setPreferredSize(new Dimension(100,300));
		
		btnAdd = new JButton("add");
		btnAdd.setBounds(5, 5, 81, 27);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				增加空行，给model的list增加一个emp对象
				tableModel.addRow();
				table.updateUI();
			}
		});
		panel_1.setLayout(null);
		panel_1.add(btnAdd);
		
		btnSave = new JButton("save");
		btnSave.setBounds(5, 45, 81, 27);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//保存,同步list中的数据
				tableModel.save();
			}
		});
		panel_1.add(btnSave);
		
		btnDelete = new JButton("delete");
		btnDelete.setBounds(5, 92, 81, 27);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				删除客户，获取到当前选中的行
				int index = table.getSelectedRow();
				if(index != -1){
					tableModel.delete(index);
					table.updateUI();	
				}
			}
		});
		panel_1.add(btnDelete);
		
//		界面加载以后，先判断按钮的状态
		updateButton();
		
	}
	
	//判断按钮是否禁用
	protected void updateButton() {
		if (start == 0) {
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
		}else{
			btnFirst.setEnabled(true);
			btnPrevious.setEnabled(true);
		}
		
		if(start == last()){
			btnLast.setEnabled(false);
			btnNext.setEnabled(false);
		}else {
			btnLast.setEnabled(true);
			btnNext.setEnabled(true);
		}
	}

	protected int last() {
//		计算最后一页的起始值
//		总：20  每页5条  最后一页起始值 15  20 - 5
//		总：18 每页5条   最后一页   15
		int last = 0;
		int total = new EmpDao().getCount();
		if(total % number == 0){
			last = total - number;
		}else {
			last = total - total % number;
		}
		return last;
	}

	protected void updateTable() {
		List<Emp> list = new EmpDao().getEmps(start, number);
		tableModel.setList(list);	
//		更新table界面显示
		table.updateUI();
	}

}
