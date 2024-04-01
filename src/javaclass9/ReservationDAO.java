package javaProject;

import java.sql.SQLException;
import java.util.Vector;

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

	// 마이페이지 정보 가져오기
	public String studentList(String id) {
		String name = "";
		try {
			sql = "select studentID,studentName from student where studentID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ReservationVO();
				vo.setStudentName(rs.getString("studentName"));			
			}
			name = vo.getStudentName();
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return name;
	}
	
	// 예약하기
	public int newReservation(String studio, String time, String id) {
		ReservationVO vo = new ReservationVO();
		int res = 0;
		int num = 0;
		try {
			num = searchStudioIdx(studio); // 연습실 이름에 따른 idx 받아오기
			vo.setStudioIdx(num);
			sql = "select reservedDate from reservation where studioIdx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getStudioIdx());
			rs = pstmt.executeQuery();
			if(rs.next()) { // 1.해당 연습실에 예약 내역이 있을 때
				pstmtClose();
				if(rs.getString("reservedDate") != time) { // 1-1.예약 시간이 겹치지 않을 때 입력
					sql = "insert into reservation values(?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.setInt(2, vo.getStudioIdx());
					pstmt.setString(3, time);
					res = pstmt.executeUpdate();
				}
			}
			else { // 2. 해당 연습실에 예약 내역이 없을 때
				pstmtClose();
				sql = "insert into reservation values(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, vo.getStudioIdx());
				pstmt.setString(3, time);
				res = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return res;
	}
	
	// 마이페이지 전체 예약내역 출력
	public Vector getReserveList(String id) {
		Vector vData = new Vector<>();
		try {			
			sql = "select r.* from reservation r, student s where s.studentID = r.studentID and s.studentID = ? order by reservedDate;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector vo = new Vector<>();
				String name = searchStudioName(rs.getInt("studioIdx"));
				vo.add(name);
				vo.add(rs.getString("reservedDate").substring(0,10));
				vo.add(rs.getString("reservedDate").substring(11,16));
				
				vData.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vData;
	}
	
	// 예약내역 수정하기
	public int setUpdate(String id, String studio, String reservedDateTime) {
		int res = 0;
		int num = 0;
		ReservationVO vo = new ReservationVO();
		try {
			num = searchStudioIdx(studio); // 연습실 이름에 따른 idx 받아오기
			vo.setStudioIdx(num);
			sql = "Update reservation set reservedDate = ? where studentID = ? and studioIdx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reservedDateTime);
			pstmt.setString(2, id);
			pstmt.setInt(3, vo.getStudioIdx());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}
	
	// 예약내역 취소하기
//	public int setDelete(String id) {
//		int res = 0;
//		try {
//			
//		} catch (SQLException e) {
//			System.out.println("SQL 오류"+e.getMessage());
//		} finally {
//			pstmtClose();
//		}
//		return res;
//	}
//	
	
//------------------------------------부가 메소드---------------------------------
	// 연습실 이름에 따른 idx 찾기
	private int searchStudioIdx(String studio) {
		int num = 0;
		try {
			sql = "select studioIdx from studio where studioName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studio);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt("studioIdx");
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return num;
	}
	
	//연습실 idx에 따른 이름 찾기
	private String searchStudioName(int studioIdx) {
		String name = "";
		try {
			sql = "select studioName from studio where studioIDx = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, studioIdx);
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
				name = rs2.getString("studioName");
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rs2Close();
		}
		return name;
	}

	// 오늘 날짜 계산하여 콤보박스에 넣기
	public String getToday() {
		String todayDate = "";
		try {
			sql="select concat(year(today),'-',month(today),'-',day(today)) as cbToday from calendar";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				todayDate = rs.getString("cbToday");
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return todayDate;
	}

	// 예약 수정 시 날짜 불러와서 콤보박스에 넣기
	public String getReservedDate(String reservedDate) {
		String date = "";
		try {
			sql="select concat(year(?),'-',month(?),'-',day(?)) as cbDay from reservation";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reservedDate);
			pstmt.setString(2, reservedDate);
			pstmt.setString(3, reservedDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				date = rs.getString("cbDay");
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return date;
	}


	// 달의 마지막 날짜 콤보박스에 넣기
	public int getLastDay(String cbYY, String cbMM) {
		int days = 0;
		try {
			String date = cbYY+"-"+cbMM+"-"+1;
			System.out.println(date);
			sql = "select day(last_day(?)) as lastDay";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				days = rs.getInt("lastDay");
				System.out.println(days);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return days;
	}

}