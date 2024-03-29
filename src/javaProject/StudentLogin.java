package javaProject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class StudentLogin extends JFrame {
	ReservationDAO dao = new ReservationDAO();
	
	private JTextField txtStudentID;
	private JPasswordField txtPassword;
	private JLabel lblStudentID,lblPassword;
	private JButton btnNewButton;
	
	public StudentLogin() {
		super("로그인");
		setSize(300, 150);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(0, 0, 284, 111);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		lblStudentID = new JLabel("학 번");
		lblStudentID.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		lblStudentID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentID.setBounds(5, 25, 60, 25);
		pn1.add(lblStudentID);
		
		lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(5, 60, 60, 25);
		pn1.add(lblPassword);
		
		txtStudentID = new JTextField();
		txtStudentID.setBounds(70, 25, 120, 22);
		pn1.add(txtStudentID);
		txtStudentID.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(70, 60, 120, 22);
		pn1.add(txtPassword);
		
		btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		btnNewButton.setBounds(200, 35, 72, 40);
		pn1.add(btnNewButton);
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------------
		
		// 로그인 버튼 마우스로 실행
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentIDCheck();
			}
		});
		// 로그인 버튼 키보드로 실행
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				studentIDCheck();
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}

	// 로그인 시 유효성 검사
	protected void studentIDCheck() {
		String id = txtStudentID.getText().trim();
		String pw = txtPassword.getText().trim();
		if(!Pattern.matches("^[0-9]{9}$", id) && !id.equals("admin")) {
			JOptionPane.showMessageDialog(null, "학번을 다시 확인해주세요.");
			txtStudentID.requestFocus();
		}
		else if(pw.equals("")) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
			txtPassword.requestFocus();
		}
		else { // 아이디와 비밀번호 매치 확인
			String res = dao.matchIDPW(id);
			if(res.equals(pw)) {
				dispose();
				new ReservationMain(id);				
			}
			else {
				JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.","",JOptionPane.WARNING_MESSAGE);
				txtStudentID.setText("");
				txtPassword.setText("");
				txtStudentID.requestFocus();
			}
		}
	}
		
	public static void main(String[] args) {
		new StudentLogin();
	}
}