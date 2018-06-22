package com.bcu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.bcu.bean.Emp;

import utils.JDBCUtils;
import utils.JDBCUtils2;

/*
 * 
 * 此类负责客户相关的操作
 * 		查询客户
 * 		删除客户
 * 		添加客户
 * 		修改客户
 */


public class EmpDao {
	
	public List<Emp> getEmps() {
		Connection connection = null;
		
		try {
			connection = JDBCUtils2.getConnection();
			String sql = "select * from emp";
			QueryRunner queryRunner = new QueryRunner();
			List<Emp> list = (List<Emp>)queryRunner.query(connection, sql,new BeanListHandler(Emp.class));
			
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
			JDBCUtils.release(null, null, connection);
		}
		return null;
	}
	public List<Emp> getEmps(int start,int number) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils2.getConnection();
			String sql = "select * from emp limit ?,?";
			QueryRunner queryRunner = new QueryRunner();
			Object[] pramas = {start,number};
			List<Emp> list = (List<Emp>)queryRunner.query(connection, sql,new BeanListHandler(Emp.class),pramas);
			
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
			JDBCUtils.release(null, null, connection);
		}
		return null;
	}
//	数据的总条数
	public int getCount(){
		Connection connection = null;
		connection = JDBCUtils2.getConnection();
		String sqlString = "select count(empno) from emp";
		QueryRunner queryRunner = new QueryRunner();
		/*
		 * BeanListHandler:封装成list集合
		 * BeanHandler：封装为对象
		 * 
		 */
		long count;
		try {
			count = (long) queryRunner.query(connection, sqlString,new ScalarHandler());
			return (int) count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils2.release(null, null, connection);
		}
		return 0;
	}
	public void add(Emp emp) {
		Connection connection = null;
		try {
			connection = JDBCUtils2.getConnection();
			String sql = "insert into emp (ename,job,hiredate,sal) values (?,?,?,?)";
			QueryRunner queryRunner = new QueryRunner();
			Object[] params = {emp.getEname(),emp.getJob(),emp.getHiredate(),emp.getSal()};
			queryRunner.update(connection, sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
			JDBCUtils2.release(null, null, connection);
		}
	}
	public void updata(Emp emp) {
		Connection connection = null;
		try {
			connection = JDBCUtils2.getConnection();
			String sql = "update emp set ename = ?,job = ?,hiredate = ?,sal = ? where empno = ?";
			QueryRunner queryRunner = new QueryRunner();
			
			Object[] params = {emp.getEname(),emp.getJob(),emp.getHiredate(),emp.getSal(),emp.getEmpno()};
			queryRunner.update(connection, sql,params);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
			JDBCUtils2.release(null, null, connection);
		}
		
	}
	public void delete(int empno) {
		// TODO Auto-generated method stub
		Connection connection = null;
		try {
			connection = JDBCUtils2.getConnection();
			String sql = "delete from emp where empno = ?";
			QueryRunner queryRunner = new QueryRunner();
			
			Object[] params = {empno};
			queryRunner.update(connection, sql,params);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
			JDBCUtils2.release(null, null, connection);
		}
	}
		
}
