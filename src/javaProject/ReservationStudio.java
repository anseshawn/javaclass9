package javaProject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ReservationStudio extends JFrame{
	public ReservationStudio() {
		super("예약하기");
		setSize(600,400);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 560, 50);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		JLabel lblTitle = new JLabel("예 약 하 기");
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
		
		JLabel lblStudio = new JLabel("연습실");
		lblStudio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudio.setBounds(12, 22, 90, 25);
		pn3.add(lblStudio);
		
		JLabel lblDate = new JLabel("날 짜");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(12, 84, 90, 25);
		pn3.add(lblDate);
		
		JLabel lblTime = new JLabel("시 간");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setBounds(12, 147, 90, 25);
		pn3.add(lblTime);
		
		JPanel pn4 = new JPanel();
		pn4.setBounds(217, 328, 355, 23);
		getContentPane().add(pn4);
		pn4.setLayout(null);
	}
}
