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
					//��������
					MainFram frame = new MainFram();
					//��ʾ
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
//		ʹ������ķ�ʽ������ṩ����
		String[][] data = {
				{"����","22","��"},
				{"����","22","��"},
				{"����","22","��"},
				{"����","22","��"}
		};
		String[] titles = {"����","����","�Ա�"};
		table = new JTable(data,titles);
//		���ֱ�ӽ�table�ӵ�contentPane�ϣ�������ʾ��ͷ��������Ҫ���м��һ��
//		JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		*/
		
		tableModel = new EmpTableModel();
		table = new JTable(tableModel);
//		���ֱ�ӽ�table�ӵ�contentPane�ϣ�������ʾ��ͷ��������Ҫ���м��һ��
//		JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnFirst = new JButton("first");
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = 0;
				//����dao�µĻ�ȡ����
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
//		panel_1��Ϊ�����˾��Բ��֣���Ҫ���ÿ�߲�����ʾ
		panel_1.setPreferredSize(new Dimension(100,300));
		
		btnAdd = new JButton("add");
		btnAdd.setBounds(5, 5, 81, 27);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				���ӿ��У���model��list����һ��emp����
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
				//����,ͬ��list�е�����
				tableModel.save();
			}
		});
		panel_1.add(btnSave);
		
		btnDelete = new JButton("delete");
		btnDelete.setBounds(5, 92, 81, 27);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ɾ���ͻ�����ȡ����ǰѡ�е���
				int index = table.getSelectedRow();
				if(index != -1){
					tableModel.delete(index);
					table.updateUI();	
				}
			}
		});
		panel_1.add(btnDelete);
		
//		��������Ժ����жϰ�ť��״̬
		updateButton();
		
	}
	
	//�жϰ�ť�Ƿ����
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
//		�������һҳ����ʼֵ
//		�ܣ�20  ÿҳ5��  ���һҳ��ʼֵ 15  20 - 5
//		�ܣ�18 ÿҳ5��   ���һҳ   15
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
//		����table������ʾ
		table.updateUI();
	}

}
