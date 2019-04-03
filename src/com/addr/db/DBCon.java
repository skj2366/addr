package com.addr.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBCon {

	private static final String USER;
	private static final String URL;
	private static final String PASSWORD;
	private static final String CLASSNAME;
	private static Connection con;
	
	static {
		InputStream is = DBCon.class.getResourceAsStream("/config/db.properties");
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL = prop.getProperty("url");
		USER = prop.getProperty("user");
		PASSWORD = prop.getProperty("password");
		CLASSNAME = prop.getProperty("classname");
	}
	
	private static boolean open() {
		if(con==null) {
			try {
				Class.forName(CLASSNAME);
				con = DriverManager.getConnection(URL,USER,PASSWORD);
				return true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static Connection getCon() {
		if(con==null) {
			if(open()) {
				return con;
			}
		}
		return null;	 // 얘 땜시 finally{ DBCon.close()}를 안해주면 500에러 (nullException)가 뜨므로 무조건 close()를 해주어야한다.
						 // 다른 방법으로는 이거처럼 메서드 3개 (open())을 쓰는게 아니라 getCon() 하나만 써서 무조건 return con;으로 가는것도 방법 .
						 //but 다른 값이 들어가면 이것도 무조건 에러가 .. 뜨지만 어짜피 그건 위에서 설정을 잘못한거기에 .. 후 .. DBCon.close()를 쓰는 습관을 기르자.
	}
	
	public static void close() {
		if(con!=null) {
			try {
				if(!con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con = null;
	}
}
