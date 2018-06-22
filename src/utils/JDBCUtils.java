package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * 	1. 注册驱动
 *  2. 连接数据库（获取连接）
 *  3. 关闭资源
 * 
 */
public class JDBCUtils {
	//在静态代码块中加载Driver类，注册驱动
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//获取连接的方法  properties文件 配置文件
	public static Connection getConnection(){
		//2. 获取连接，操作数据库一定要先连接数据库
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
	//释放资源
	public static void release(ResultSet set,Statement statement,Connection connection) {
		//释放资源的操作，一定要放在finally中
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
