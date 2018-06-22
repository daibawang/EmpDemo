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
	//每页显示的数据数量
	private int number=5;
	
//	临时的list集合，存储更改的客户信息
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

	//最初先加载前五条数据
	private  List<Emp> list = new EmpDao().getEmps(0,number);
	private String[] titles = {"员工姓名","工作","入职日期","薪资"};

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
	//编辑单元格
		public boolean isCellEditable(int rowIndex , int columnIndex){
			return true;
		}
		//当单元格编辑完成之后会调用此方法
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
		//设置员工号为-1由于区别是否是新添加的员工
		emp.setEmpno(-1);
		list.add(emp);
	}
	public void save(){
		for(Emp emp :tempList){
			if(emp.getEmpno()==-1){
				//添加员工
				new EmpDao().add(emp);
			}else{
				//更新员工
				new EmpDao().updata(emp);
			}
		}
		//放了防止重复点击save按钮，每次同步后，清空临时list
		tempList.clear();
	}
	public void delete(int index) {
		Emp emp = list.remove(index);
		new EmpDao().delete(emp.getEmpno());
	}

}
