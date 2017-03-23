import java.sql.*;
import java.io.*;
import java.util.regex.*;

public class GetStopInfo {
	// jdbc�����������ݿ�URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/gp_db";
	
	// ���ݿ���û���������
	static final String USER = "root";
	static final String PASSWORD = "syf0313";
	
	// ����Դ·��
	static final String PATH = "E:\\GraduationProject\\ViFiExport\\Static\\route";

	public static void main(String args[]) {
		Connection conn = null;
		Statement stmt = null;
		File dataFile = new File(PATH);
		
		try {
			//ע��jdbc����
			Class.forName(JDBC_DRIVER);
			
			//������
			System.out.println("�������ݿ�...");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("���ӳɹ�");
			
			//ʵ����
			stmt = conn.createStatement();
			
			//�������
			String sub_sql = "INSERT IGNORE INTO stopinfo VALUES ";
			
			//��ȡ����Դ
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
					stopName = "�������";
					
				}
				
				System.out.print("д�����ݣ�" + stopId + ", "
                        					   + stopName + ", "
                        					   + lat + ", "
                        					   + lon);
				
				
				sql = sub_sql + "( \"" + stopId + "\", \"" + stopName + "\", " + lat + ", " + lon + " )";
				sql = new String(sql.getBytes("gb2312"), "latin1");
				stmt.execute(sql);	
				
				System.out.println("   �ɹ�");
			}
			
			System.out.println("���");
			stmt.close();
			conn.close();
			source.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//�ر���Դ
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
