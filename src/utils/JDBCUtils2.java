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
 * 	1. 注册驱动
 *  2. 连接数据库（获取连接）
 *  3. 关闭资源
 * 
 */
public class JDBCUtils2 {
	private static Properties properties;
	//在静态代码块中加载Driver类，注册驱动
	static{
		try {
			//读取properties文件中信息
			
			//获取到类加载器
			ClassLoader cl = JDBCUtils2.class.getClassLoader();
			//类加载器可以加载资源，加载的资源默认是bin目录的资源，bin目录的内容
			//其实都是从src目录拷贝过去的
			
			//此处写的路径是相对于src的
			InputStream is = cl.getResourceAsStream("db.properties");
			//Properties类可以帮我们加载properties文件中的信息
			properties = new Properties();
			//加载完以后，properties对象中就会有文件中所有内容
			properties.load(is);
			//根据key获取对应的值
			String driver = properties.getProperty("driver");
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取连接的方法  properties文件 配置文件
	public static Connection getConnection(){
		//2. 获取连接，操作数据库一定要先连接数据库
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
