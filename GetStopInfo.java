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
	
	// 数据源路径
	static final String PATH = "E:\\GraduationProject\\ViFiExport\\Static\\route";

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
			
			//插入语句
			String sub_sql = "INSERT IGNORE INTO stopinfo VALUES ";
			
			//读取数据源
			//RandomAccessFile source = new RandomAccessFile(dataFile, "r");
			BufferedReader source = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "gb2312"));  
			String meta = source.readLine();
			String stopId, stopName, lat, lon, sql;
			while ((meta=source.readLine()) != null) {
				Pattern p = Pattern.compile("[^\",]+");
				Matcher m = p.matcher(meta);
				
				m.find(); //lineId
				m.find(); //direction
				m.find(); //stopId
				stopId = m.group(0); 
				m.find(); //stopName
				stopName = m.group(0);
				m.find(); //order
				m.find(); //lat
				lat = m.group(0);
				m.find();
				lon = m.group(0);
				
				if (stopId.equals("XBUS_00004881") || stopId.equals("XBUS_00004883")) {					
					stopId = "XBUS_00004881";
					stopName = "春风万佳";
					
				}
				
				System.out.print("写入数据：" + stopId + ", "
                        					   + stopName + ", "
                        					   + lat + ", "
                        					   + lon);
				
				
				sql = sub_sql + "( \"" + stopId + "\", \"" + stopName + "\", " + lat + ", " + lon + " )";
				sql = new String(sql.getBytes("gb2312"), "latin1");
				stmt.execute(sql);	
				
				System.out.println("   成功");
			}
			
			System.out.println("完成");
			stmt.close();
			conn.close();
			source.close();
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
