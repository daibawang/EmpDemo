package com.bcu.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bcu.bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import utils.JDBCUtils;
import utils.JDBCUtils2;

/*此类负责用户相关操作
 * 例如：
 * 查询用户，
 * 修改用户信息，
 * 删除用户，
 * 添加用户
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
			 * BeanHandler:处理结果集中的第一条数据
			 * BeanListHandler:处理结果集中的所有数据
			 */
			User user;
			user = (User)queryRunner.query(connction, sqlString,new BeanHandler(User.class),params);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//释放资源的操作，一定要放在finally中
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
