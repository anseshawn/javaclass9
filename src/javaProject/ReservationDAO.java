package javaProject;

import java.sql.SQLException;

public class ReservationDAO extends DBConn {
	ReservationVO vo = null;

	// 아이디 로그인 시 비밀번호 확인
	public String matchIDPW(String id) {
		String res = "";
		try {
			sql = "select * from student where studentID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ReservationVO();
				vo.setStudentID(rs.getString("studentID"));
				vo.setStudentPW(rs.getString("studentPW"));
			}
			res = vo.getStudentPW();
			
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return res;
	}
	
	
}
