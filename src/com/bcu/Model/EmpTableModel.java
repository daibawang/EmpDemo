package com.bcu.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.bcu.bean.Emp;
import com.bcu.dao.EmpDao;

public class EmpTableModel extends AbstractTableModel{
	//ÿҳ��ʾ����������
	private int number=5;
	
//	��ʱ��list���ϣ��洢���ĵĿͻ���Ϣ
	private List<Emp> tempList = new ArrayList<>();
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	

	public List<Emp> getList() {
		return list;
	}

	public void setList(List<Emp> list) {
		this.list = list;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	//����ȼ���ǰ��������
	private  List<Emp> list = new EmpDao().getEmps(0,number);
	private String[] titles = {"Ա������","����","��ְ����","н��"};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Emp emp = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return emp.getEname();
		case 1:
			
			return emp.getJob();
		case 2:
			
			return emp.getHiredate();			
		case 3:
			
			return emp.getSal();
		}
		return null;
	}
	
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}
	//�༭��Ԫ��
		public boolean isCellEditable(int rowIndex , int columnIndex){
			return true;
		}
		//����Ԫ��༭���֮�����ô˷���
		public void setValueAt( Object avalue , int rowIndex , int columnIndex ){
			Emp emp = list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				emp.setEname( (String) avalue);
				break;
			case 1:
				
				emp.setJob( (String) avalue );
				break;
			case 2:
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = simpleDateFormat.parse((String)avalue);
					Date hiredate = new java.sql.Date(date.getTime());
					emp.setHiredate(hiredate);	
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				break;
			case 3:
				emp.setSal( (Float.parseFloat((String)avalue)));
			}
			
			if(!tempList.contains(emp)){
				tempList.add(emp);
			}
			
		}
		
		
	public void addRow(){
		Emp emp = new Emp();
		//����Ա����Ϊ-1���������Ƿ�������ӵ�Ա��
		emp.setEmpno(-1);
		list.add(emp);
	}
	public void save(){
		for(Emp emp :tempList){
			if(emp.getEmpno()==-1){
				//���Ա��
				new EmpDao().add(emp);
			}else{
				//����Ա��
				new EmpDao().updata(emp);
			}
		}
		//���˷�ֹ�ظ����save��ť��ÿ��ͬ���������ʱlist
		tempList.clear();
	}
	public void delete(int index) {
		Emp emp = list.remove(index);
		new EmpDao().delete(emp.getEmpno());
	}

}
