package javaProject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;

public class ReservationMain extends JFrame {
	JButton btnLogout,btnMyPage,btnReserve;
	JLabel lblTitle;
	
	public ReservationMain(String id) {
		super("메인 화면");
		setSize(800,600);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 760, 50);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		btnLogout = new JButton("로그아웃");
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnLogout.setBounds(631, 10, 105, 30);
		pn1.add(btnLogout);
		
		btnMyPage = new JButton("마이페이지");
		btnMyPage.setBackground(new Color(255, 255, 255));
		btnMyPage.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnMyPage.setBounds(29, 10, 105, 30);
		pn1.add(btnMyPage);
		
		lblTitle = new JLabel("연 습 실   예 약");
		lblTitle.setFont(new Font("이사만루체 Bold", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(155, 5, 447, 40);
		pn1.add(lblTitle);
		
		JPanel pn2 = new JPanel();
		pn2.setBounds(12, 70, 760, 420);
		getContentPane().add(pn2);
		pn2.setLayout(null);
		
		JPanel pn3 = new JPanel();
		pn3.setBounds(12, 500, 760, 50);
		getContentPane().add(pn3);
		pn3.setLayout(null);
		
		btnReserve = new JButton("예약하기");
		btnReserve.setBackground(new Color(255, 255, 255));
		btnReserve.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnReserve.setBounds(29, 10, 97, 30);
		pn3.add(btnReserve);
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------------
		
		// 예약하기
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ReservationStudio(id);
			}
		});
		
		// 마이페이지
		btnMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MyPage(id);
			}
		});
		
		// 로그아웃
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		btnLogout.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				logout();
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}

	protected void logout() {
		int ans = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","",JOptionPane.YES_NO_OPTION);
		if(ans == 0) {
			dispose();
			new StudentLogin();
		}
		else {
			JOptionPane.showMessageDialog(null, "취소되었습니다.");
		}
	}
}