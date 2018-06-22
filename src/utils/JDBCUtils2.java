package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/*
 * 	1. ע������
 *  2. �������ݿ⣨��ȡ���ӣ�
 *  3. �ر���Դ
 * 
 */
public class JDBCUtils2 {
	private static Properties properties;
	//�ھ�̬������м���Driver�࣬ע������
	static{
		try {
			//��ȡproperties�ļ�����Ϣ
			
			//��ȡ���������
			ClassLoader cl = JDBCUtils2.class.getClassLoader();
			//����������Լ�����Դ�����ص���ԴĬ����binĿ¼����Դ��binĿ¼������
			//��ʵ���Ǵ�srcĿ¼������ȥ��
			
			//�˴�д��·���������src��
			InputStream is = cl.getResourceAsStream("db.properties");
			//Properties����԰����Ǽ���properties�ļ��е���Ϣ
			properties = new Properties();
			//�������Ժ�properties�����оͻ����ļ�����������
			properties.load(is);
			//����key��ȡ��Ӧ��ֵ
			String driver = properties.getProperty("driver");
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ���ӵķ���  properties�ļ� �����ļ�
	public static Connection getConnection(){
		//2. ��ȡ���ӣ��������ݿ�һ��Ҫ���������ݿ�
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//�ͷ���Դ
	public static void release(ResultSet set,Statement statement,Connection connection) {
		//�ͷ���Դ�Ĳ�����һ��Ҫ����finally��
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		if(set != null){
			try {
				set.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
