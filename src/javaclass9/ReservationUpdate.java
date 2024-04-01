package javaclass9;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ReservationUpdate extends JFrame{
	private final ButtonGroup buttonGroupStudio = new ButtonGroup();
	private final ButtonGroup buttonGroupTime = new ButtonGroup();
	private JLabel lblTitle,lblStudio,lblDate,lblTime;
	private JRadioButton rbPianoA,rbGuitarA,rbDrumA,rbPianoB,rbGuitarB,rbDrumB,rb17,rb18,rb19,rb20,rb21,rb22;
	private JButton btnCancel,btnSubmit;
	private JComboBox cbYY,cbMM,cbDD;
	
	ReservationDAO dao = new ReservationDAO();
	ReservationVO vo = null;
	int res=0, check=0;
	String reservedDate="", studio="", time="";
	
	public ReservationUpdate(String id, int row, JTable table) {
		super("예약하기");
		setSize(600,400);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 560, 50);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		lblTitle = new JLabel("예 약 하 기");
		lblTitle.setFont(new Font("이사만루체 Bold", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(12, 10, 536, 30);
		pn1.add(lblTitle);
		
		JPanel pn2 = new JPanel();
		pn2.setBounds(12, 70, 193, 281);
		getContentPane().add(pn2);
		pn2.setLayout(null);
		
		JPanel pn3 = new JPanel();
		pn3.setBounds(217, 70, 355, 248);
		getContentPane().add(pn3);
		pn3.setLayout(null);
		
		lblStudio = new JLabel("연습실");
		lblStudio.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		lblStudio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudio.setBounds(0, 43, 90, 25);
		pn3.add(lblStudio);
		
		lblDate = new JLabel("날 짜");
		lblDate.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(0, 111, 90, 25);
		pn3.add(lblDate);
		
		lblTime = new JLabel("시 간");
		lblTime.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setBounds(0, 179, 90, 25);
		pn3.add(lblTime);
		
		rbPianoA = new JRadioButton("피아노A");
		buttonGroupStudio.add(rbPianoA);
		rbPianoA.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbPianoA.setBounds(108, 24, 70, 25);
		pn3.add(rbPianoA);
		
		rbGuitarA = new JRadioButton("기 타A");
		buttonGroupStudio.add(rbGuitarA);
		rbGuitarA.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbGuitarA.setBounds(188, 24, 70, 25);
		pn3.add(rbGuitarA);
		
		rbDrumA = new JRadioButton("드 럼A");
		buttonGroupStudio.add(rbDrumA);
		rbDrumA.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbDrumA.setBounds(268, 24, 70, 25);
		pn3.add(rbDrumA);
		
		rbPianoB = new JRadioButton("피아노B");
		buttonGroupStudio.add(rbPianoB);
		rbPianoB.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbPianoB.setBounds(108, 61, 70, 25);
		pn3.add(rbPianoB);
		
		rbGuitarB = new JRadioButton("기 타B");
		buttonGroupStudio.add(rbGuitarB);
		rbGuitarB.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbGuitarB.setBounds(188, 61, 70, 25);
		pn3.add(rbGuitarB);
		
		rbDrumB = new JRadioButton("드 럼B");
		buttonGroupStudio.add(rbDrumB);
		rbDrumB.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rbDrumB.setBounds(268, 61, 70, 25);
		pn3.add(rbDrumB);
		
		// 올해 년도만 입력(해가 지나면 바뀌도록)
		LocalDate now = LocalDate.now();
		String[] yy = new String[1];
		yy[0] = now.toString().substring(0,4);
		cbYY = new JComboBox(yy);
		cbYY.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		cbYY.setBounds(109, 111, 65, 25);
		pn3.add(cbYY);
		
		// 월, 일 체크박스에 입력
		String[] mm = new String[12];	
		for(int i=0; i<mm.length; i++) {
			mm[i] = (i+1)+"";
		}
		cbMM = new JComboBox(mm);
		cbMM.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		cbMM.setBounds(184, 111, 50, 25);
		pn3.add(cbMM);		
		
		int days = 0;
		//days = dayCalculator(cbMM.getSelectedItem().toString());
		days = dao.getLastDay(cbYY.getSelectedItem()+"",cbMM.getSelectedItem()+"");
		String[] dd = new String[days];
		for(int i=0; i<dd.length; i++) {
			dd[i] = (i+1)+"";
		}
		cbDD = new JComboBox(dd);
		cbDD.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		cbDD.setBounds(244, 111, 50, 25);
		pn3.add(cbDD);
		
		rb17 = new JRadioButton("17:00");
		buttonGroupTime.add(rb17);
		rb17.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb17.setBounds(108, 161, 70, 25);
		pn3.add(rb17);
		
		rb18 = new JRadioButton("18:00");
		buttonGroupTime.add(rb18);
		rb18.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb18.setBounds(188, 161, 70, 25);
		pn3.add(rb18);
		
		rb19 = new JRadioButton("19:00");
		buttonGroupTime.add(rb19);
		rb19.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb19.setBounds(268, 161, 70, 25);
		pn3.add(rb19);
		
		rb20 = new JRadioButton("20:00");
		buttonGroupTime.add(rb20);
		rb20.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb20.setBounds(108, 198, 70, 25);
		pn3.add(rb20);
		
		rb21 = new JRadioButton("21:00");
		buttonGroupTime.add(rb21);
		rb21.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb21.setBounds(188, 198, 70, 25);
		pn3.add(rb21);
		
		rb22 = new JRadioButton("22:00");
		buttonGroupTime.add(rb22);
		rb22.setFont(new Font("이사만루체 Light", Font.PLAIN, 12));
		rb22.setBounds(268, 198, 70, 25);
		pn3.add(rb22);
		
		JPanel pn4 = new JPanel();
		pn4.setBounds(217, 328, 355, 23);
		getContentPane().add(pn4);
		pn4.setLayout(null);
		
		btnCancel = new JButton("취 소");
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("이사만루체 Light", Font.PLAIN, 13));
		btnCancel.setBounds(258, 0, 97, 23);
		pn4.add(btnCancel);
		
		btnSubmit = new JButton("수정하기");
		btnSubmit.setBackground(new Color(255, 255, 255));
		btnSubmit.setFont(new Font("이사만루체 Light", Font.PLAIN, 13));
		btnSubmit.setBounds(151, 0, 97, 23);		
		
		// 선택했던 연습실 라디오버튼 고정
		if(table.getValueAt(row, 0).toString().equals("피아노A")) {
			rbPianoA.setSelected(true);
		}
		else if(table.getValueAt(row, 0).toString().equals("피아노B")) {
			rbPianoB.setSelected(true);
		}
		else if(table.getValueAt(row, 0).toString().equals("기타A")) {
			rbGuitarA.setSelected(true);
		}
		else if(table.getValueAt(row, 0).toString().equals("기타B")) {
			rbGuitarB.setSelected(true);
		}
		else if(table.getValueAt(row, 0).toString().equals("드럼A")) {
			rbDrumA.setSelected(true);
		}
		else if(table.getValueAt(row, 0).toString().equals("드럼B")) {
			rbDrumA.setSelected(true);
		}
		// 선택했던 시간 라디오버튼 고정
		if(table.getValueAt(row, 2).toString().equals("17:00")) {
			rb17.setSelected(true);
		}
		else if(table.getValueAt(row, 2).toString().equals("18:00")) {
			rb18.setSelected(true);
		}
		else if(table.getValueAt(row, 2).toString().equals("19:00")) {
			rb19.setSelected(true);
		}
		else if(table.getValueAt(row, 2).toString().equals("20:00")) {
			rb20.setSelected(true);
		}
		else if(table.getValueAt(row, 2).toString().equals("21:00")) {
			rb21.setSelected(true);
		}
		else if(table.getValueAt(row, 2).toString().equals("22:00")) {
			rb22.setSelected(true);
		}
		
		btnSubmit.setEnabled(true);			
		pn4.add(btnSubmit);
		
		// 콤보상자 디폴트 값을 예약한 날짜로
		reservedDate = table.getValueAt(row, 1).toString();
		String date = dao.getReservedDate(reservedDate);
		String[] ymds = date.split("-");
		cbYY.setSelectedItem(ymds[0]);
		cbMM.setSelectedItem(ymds[1]);
		cbDD.setSelectedItem(ymds[2]);
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------------
		
		// 예약 수정하기 버튼
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbPianoA.isSelected()) studio = "피아노A";
				else if(rbPianoB.isSelected()) studio = "피아노B";
				else if(rbGuitarA.isSelected()) studio = "기타A";
				else if(rbGuitarB.isSelected()) studio = "기타B";
				else if(rbDrumA.isSelected()) studio = "드럼A";
				else if(rbDrumB.isSelected()) studio = "드럼B";
				
				if(rb17.isSelected()) time = "17:00:00";
				else if(rb18.isSelected()) time = "18:00:00";
				else if(rb19.isSelected()) time = "19:00:00";
				else if(rb20.isSelected()) time = "20:00:00";
				else if(rb21.isSelected()) time = "21:00:00";
				else if(rb22.isSelected()) time = "22:00:00";
				
				String reservedDate = cbYY.getSelectedItem()+"-"+cbMM.getSelectedItem()+"-"+cbDD.getSelectedItem();
				String reservedDateTime = reservedDate+" "+time;
				res = dao.setUpdate(id,studio,reservedDateTime);
				if(res != 0) {
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
					dispose();
					new MyPage(id);
				}
				else {
					JOptionPane.showMessageDialog(null, "예약 수정에 실패했습니다. 다시 확인해주세요.");
					buttonGroupStudio.clearSelection();
					buttonGroupTime.clearSelection();
				}
			}
		});
		
		// 취소하기
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MyPage(id);
			}
		});
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
				new MyPage(id);
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}
	
	
// 예약 내역이 있으면 라디오버튼 비활성화하기...?
//	private int checkStatus(String id) {
//		if(rbPianoA.isSelected()) studio = "피아노A";
//		else if(rbPianoB.isSelected()) studio = "피아노B";
//		else if(rbGuitarA.isSelected()) studio = "기타A";
//		else if(rbGuitarB.isSelected()) studio = "기타B";
//		else if(rbDrumA.isSelected()) studio = "드럼A";
//		else if(rbDrumB.isSelected()) studio = "드럼B";
//		
//		if(rb17.isSelected()) time = "17:00:00";
//		else if(rb18.isSelected()) time = "18:00:00";
//		else if(rb19.isSelected()) time = "19:00:00";
//		else if(rb20.isSelected()) time = "20:00:00";
//		else if(rb21.isSelected()) time = "21:00:00";
//		else if(rb22.isSelected()) time = "22:00:00";
//		
//		String reservedDate = cbYY.getSelectedItem()+"-"+cbMM.getSelectedItem()+"-"+cbDD.getSelectedItem();
//		String reservedDateTime = reservedDate+" "+time;
//		
//		check = dao.newReservation(studio,reservedDateTime,id);
//		
//		return check;
//	}



	// 라디오버튼 체크 완료 되었는지 확인
	private int checkAllBtn() {
		int res = 0;
		if(rbPianoA.isSelected() || rbPianoB.isSelected()
				|| rbGuitarA.isSelected() || rbGuitarB.isSelected()
				|| rbDrumA.isSelected() || rbDrumB.isSelected()) {
			if(rb17.isSelected() || rb18.isSelected() || rb19.isSelected()
					|| rb20.isSelected() || rb21.isSelected() || rb22.isSelected()) {
				res = 1;
			}
			else res = 0;
		}
		else res = 0;
		return res;
	}

	// 월에 따른 일 생성
//	private int dayCalculator(String cbMM) {
//		int month, days = 0;
//		month = Integer.parseInt(cbMM);
//		if(month <= 7 && month % 2 != 0)days = 31;
//		else if(month > 7 && month % 2 == 0)days = 31;
//		else if(month == 2)days = 29;
//		else days = 30;
//		return days;
//	}
	
}