package javaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	
	String sql = "";
	
	public DBConn() {
		String url="jdbc:mysql://localhost:3306/javaclass9";
		String user="root";
		String password="1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패"+e.getMessage());
		} catch (SQLException e) {
			System.out.println("데이터베이스 연동 실패"+e.getMessage());
		}
	}
	
	//conn Close
	public void connClose() {
		try {
			conn.close();
		} catch (SQLException e) {}
	}
	
	// pstmt Close
	public void pstmtClose() {
		try {
			if(pstmt != null)	pstmt.close();
		} catch (SQLException e) {}
	}
	
	// rs Close
	public void rsClose() {
		try {
			if(rs != null)	rs.close();
			pstmt.close();
		} catch (SQLException e) {}
	}
	
	// pstmt2 Close
	public void pstmt2Close() {
		try {
			if(pstmt2 != null)	pstmt2.close();
		} catch (SQLException e) {}
	}
	
	// rs2 Close
	public void rs2Close() {
		try {
			if(rs2 != null)	rs2.close();
			pstmt2.close();
		} catch (SQLException e) {}
	}
	
}
