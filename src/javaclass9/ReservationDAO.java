package javaclass9;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;

public class ReservationDAO extends DBConn {
	ReservationVO vo = null;
	private String name;

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

	// 마이페이지 상단 유저 정보 가져오기
	public ReservationVO studentList(String id) {
		ReservationVO vo = new ReservationVO();
		try {
			sql = "select studentID,studentName from student where studentID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ReservationVO();
				vo.setStudentName(rs.getString("studentName"));		
				vo.setStudentID(rs.getString("studentID"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
	}
	
	// 예약하기
	public int newReservation(String studio, String time, String id) {
		ReservationVO vo = new ReservationVO();
		int res = 0;
		try {
			vo = searchStudioIdx(studio); // 연습실 이름에 따른 idx 받아오기
			sql = "select concat(year(reservedDate),'-',month(reservedDate),'-',day(reservedDate),' ',hour(reservedDate),':',minute(reservedDate)) as reserved from reservation where studioIdx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getStudioIdx());
			rs = pstmt.executeQuery();
			if(rs.next()) { // 1.해당 연습실에 예약 내역이 있을 때
				vo.setReservedTime(rs.getString("reserved"));
				if(!vo.getReservedTime().equals(time)) { // 1-1.예약 시간이 겹치지 않을 때 입력
					sql = "insert into reservation values(?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.setInt(2, vo.getStudioIdx());
					pstmt.setString(3, time);
					res = pstmt.executeUpdate();		
				}
				else {
					res = 0;
					vo.setReservedTime(null);
				}
			}
			else { // 2. 해당 연습실에 예약 내역이 없을 때
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
				name = searchStudioName(rs.getInt("studioIdx")); // 연습실 idx에 따른 이름
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
		ReservationVO vo = new ReservationVO();
		try {
			vo = searchStudioIdx(studio); // 연습실 이름에 따른 idx 받아오기
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
	public int setDelete(String id, int row, JTable table) {
		int res = 0;
		String reservedDateTime = table.getValueAt(row, 1)+" "+table.getValueAt(row, 2);
		try {
			sql = "delete from reservation where studentID = ? and reservedDate = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, reservedDateTime);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}
	
	// 선택한 연습실 예약현황 출력하기
	public Vector getStudioList(String studio) {
		ReservationVO vo = new ReservationVO();
		Vector vData = new Vector<>();
		try {			
			vo = searchStudioIdx(studio);
			sql = "select r.* from reservation r, studio s where s.studioIdx = r.studioIdx and s.studioIdx = ? order by reservedDate";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getStudioIdx());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector vo2 = new Vector<>();
				vo2.add(rs.getString("reservedDate").substring(0,10));
				vo2.add(rs.getString("reservedDate").substring(11,16));
				
				vData.add(vo2);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vData;
	}
	
	// 마이페이지-비밀번호 변경하기
	public int changePW(String id, String pw) {
		int res = 0;
		try {
			sql = "update student set studentPW = ? where studentID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

	// 전체 예약 현황 출력
	public Vector getAllList() {
		Vector vData = new Vector<>();
		try {			
			sql = "select r.*,s.studioName from reservation r, studio s where s.studioIdx = r.studioIdx order by reservedDate";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector vo = new Vector<>();
				vo.add(rs.getString("studioName"));
				vo.add(rs.getString("reservedDate").substring(0,10));
				vo.add(rs.getString("reservedDate").substring(11,16));
				vo.add(rs.getString("studentID"));
				
				vData.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vData;
	}
		
	// 전체 학생정보 출력
	public Vector getStudentList() {
		Vector vData = new Vector<>();
		try {
			sql = "select studentID,studentName from student order by studentID";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector vo = new Vector<>();
				vo.add(rs.getString("studentName"));
				vo.add(rs.getString("studentID"));
				vData.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vData;
	}
	
	// 학생정보 수정
	public int setStudentInfo(ReservationVO vo) {
		int res = 0;
		try {
			sql = "update student set studentName=? where studentID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getStudentName());
			pstmt.setString(2, vo.getStudentID());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}
	
//------------------------------------부가 메소드---------------------------------
	// 연습실 이름에 따른 idx 찾기
	private ReservationVO searchStudioIdx(String studio) {
		ReservationVO vo = new ReservationVO();
		try {
			sql = "select studioIdx from studio where studioName = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, studio);
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
				vo.setStudioIdx(rs2.getInt("studioIdx"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rs2Close();
		}
		return vo;
	}
	
	//연습실 idx에 따른 이름 찾기
	private String searchStudioName(int studioIdx) {
		name = "";
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
	public ReservationVO getToday() {
		ReservationVO vo = new ReservationVO();
		try {
			sql="select concat(year(now()),'-',month(now()),'-',day(now())) as cbToday";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setCbToday(rs.getString("cbToday"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
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
			sql = "select day(last_day(?)) as lastDay";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				days = rs.getInt("lastDay");
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류"+e.getMessage());
		} finally {
			rsClose();
		}
		return days;
	}

}