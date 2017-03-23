import java.sql.*;
import java.io.*;
import java.util.regex.*;

public class GetStopInfo {
	// jdbc驱动名及数据库URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/gp_db";
	
	// 数据库的用户名与密码
	static final String USER = "root";
	static final String PASSWORD = "syf0313";

	public static void main(String args[]) {
		Connection conn = null;
		Statement stmt = null;
		File dataFile = new File(PATH);
		
		try {
			//注册jdbc驱动
			Class.forName(JDBC_DRIVER);
			
			//打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("连接成功");
			
			//实例化
			stmt = conn.createStatement();
			
			//完成一个mysql语句，赋值到sql中，并执行
			String sql = "INSERT INTO ... VALUES ...";
			stmt.execute(sql);
      
			//如果sql为查询语句，代码如下
			String sql = "SELECT ... FROM ...";
			ResultSet rs = stmt.executeQuery(sql);
      
			//获取查询结果
			while(rs.next()) {
				int id  = rs.getInt("id");
				String name = rs.getString("name");
				...
			}
         
			System.out.println("完成");
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//关闭资源
			try {
				if(stmt!=null)
					stmt.close();
				
				if(conn!=null)
					conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
