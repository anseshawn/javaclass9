package javaProject;

public class ReservationVO {
	private String studentID;
	private String studentPW;
	private String studentName;
	
	private int studioIdx;
	private String studionName;
	
	private String reservedTime;

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

	public String getStudionName() {
		return studionName;
	}

	public void setStudionName(String studionName) {
		this.studionName = studionName;
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
				+ ", studioIdx=" + studioIdx + ", studionName=" + studionName + ", reservedTime=" + reservedTime + "]";
	}
	
}
