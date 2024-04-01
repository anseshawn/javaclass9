package javaclass9;

public class ReservationVO {
	private String studentID;
	private String studentPW;
	private String studentName;
	
	private int studioIdx;
	private String studioName;
	
	private String reservedTime;
	
	private String cbToday;

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentPW() {
		return studentPW;
	}

	public void setStudentPW(String studentPW) {
		this.studentPW = studentPW;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudioIdx() {
		return studioIdx;
	}

	public void setStudioIdx(int studioIdx) {
		this.studioIdx = studioIdx;
	}

	public String getstudioName() {
		return studioName;
	}

	public void setstudioName(String studioName) {
		this.studioName = studioName;
	}

	public String getReservedTime() {
		return reservedTime;
	}

	public void setReservedTime(String reservedTime) {
		this.reservedTime = reservedTime;
	}

	@Override
	public String toString() {
		return "ReservationVO [studentID=" + studentID + ", studentPW=" + studentPW + ", studentName=" + studentName
				+ ", studioIdx=" + studioIdx + ", studioName=" + studioName + ", reservedTime=" + reservedTime + "]";
	}

	public String getCbToday() {
		return cbToday;
	}

	public void setCbToday(String cbToday) {
		this.cbToday = cbToday;
	}
	
}