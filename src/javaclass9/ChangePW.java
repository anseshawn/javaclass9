package javaclass9;

import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
@SuppressWarnings({ "deprecation", "serial" })
public class ChangePW extends JFrame{
	private JLabel lblNewPW,lblConfirmPW;
	private JTextField txtNewPW;
	private JPasswordField txtConfirmPW;
	private JButton btnSubmit;
	private JButton btnCancel;
	
	ReservationDAO dao = new ReservationDAO();	
	ReservationVO vo = null;
	int res = 0, ans = 0;
	String pw = "";
	
	public ChangePW(String id, String admin) {
		super("비밀번호 변경");
		setSize(300, 180);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(0, 0, 284, 111);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		lblNewPW = new JLabel("새 비밀번호");
		lblNewPW.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		lblNewPW.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPW.setBounds(31, 25, 80, 25);
		pn1.add(lblNewPW);
		
		lblConfirmPW = new JLabel("비밀번호 확인");
		lblConfirmPW.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		lblConfirmPW.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPW.setBounds(31, 61, 80, 25);
		pn1.add(lblConfirmPW);
		
		txtNewPW = new JTextField();
		txtNewPW.setBounds(116, 26, 120, 22);
		pn1.add(txtNewPW);
		txtNewPW.setColumns(10);
		
		txtConfirmPW = new JPasswordField();
		txtConfirmPW.setBounds(116, 62, 120, 22);
		pn1.add(txtConfirmPW);
		txtConfirmPW.setColumns(10);
		
		btnSubmit = new JButton("확인");
		btnSubmit.setBackground(new Color(255, 255, 255));
		btnSubmit.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		btnSubmit.setBounds(68, 101, 72, 30);
		pn1.add(btnSubmit);
		
		btnCancel = new JButton("취소");
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("이사만루체 Medium", Font.PLAIN, 12));
		btnCancel.setBounds(142, 101, 72, 30);
		pn1.add(btnCancel);
		
		if(admin.equals("admin")) {
			txtNewPW.setText("123456789");
			txtConfirmPW.setText("123456789");
		}
		else {
			txtNewPW.setText("");
			txtConfirmPW.setText("");
		}
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------------
		
		// 확인버튼
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmBtn(id,admin);
			}
		});
		btnSubmit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				confirmBtn(id,admin);
			}
		});
		
		// 취소버튼
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelBtn(id,admin);
			}
		});
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				cancelBtn(id,admin);
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}

	protected void cancelBtn(String id, String admin) {
		dispose();
		if(admin.equals("admin") || id.equals("admin")) {
			new AdminMyPage("admin");
		}
		else {
			new MyPage(id);										
		}
	}

	protected void confirmBtn(String id, String admin) {
		ans = JOptionPane.showConfirmDialog(null, "비밀번호를 변경하시겠습니까?","",JOptionPane.YES_NO_OPTION);
		if(ans == 0) {
			if(!txtNewPW.getText().trim().equals(txtConfirmPW.getText().trim())) {
				JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
				txtConfirmPW.setText("");
				txtConfirmPW.requestFocus();
			}
			else {
				String pw = txtNewPW.getText().trim();
				res = dao.changePW(id,pw);
				JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.");
				dispose();
				if(admin.equals("admin") || id.equals("admin")) {
					new AdminMyPage("admin");
				}
				else {
					new MyPage(id);										
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "변경을 취소합니다.");
		}
	}
	
}
