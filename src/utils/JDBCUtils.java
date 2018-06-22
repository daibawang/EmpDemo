package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * 	1. ע������
 *  2. �������ݿ⣨��ȡ���ӣ�
 *  3. �ر���Դ
 * 
 */
public class JDBCUtils {
	//�ھ�̬������м���Driver�࣬ע������
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ���ӵķ���  properties�ļ� �����ļ�
	public static Connection getConnection(){
		//2. ��ȡ���ӣ��������ݿ�һ��Ҫ���������ݿ�
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "root";
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
