package com.bcu.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bcu.bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import utils.JDBCUtils;
import utils.JDBCUtils2;

/*���ฺ���û���ز���
 * ���磺
 * ��ѯ�û���
 * �޸��û���Ϣ��
 * ɾ���û���
 * ����û�
 * */
public class UserDao {
	
	public User findUser(String username,String password) {
		Connection connction = null;
		
		try {
			connction=JDBCUtils2.getConnection();
			String sqlString = "select * from user where username = ? and password = ?";
			QueryRunner queryRunner = new QueryRunner();
			Object[] params = {username,password};
			/*
			 * BeanHandler:���������еĵ�һ������
			 * BeanListHandler:���������е���������
			 */
			User user;
			user = (User)queryRunner.query(connction, sqlString,new BeanHandler(User.class),params);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ͷ���Դ�Ĳ�����һ��Ҫ����finally��
			JDBCUtils.release(null, null, connction);
		}
		return null;
	}
//	
//	public User changeUser(String username,String newpassword){
//		
//		return null;
//		
//	}
}
