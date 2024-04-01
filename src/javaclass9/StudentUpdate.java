package javaclass9;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class StudentUpdate extends JFrame {
	private JTextField txtStudentID,txtStudentName;
	private JLabel lblStudentID,lblStudentName;
	private JButton btnUpdate,btnCancel;
	ReservationDAO dao = new ReservationDAO();
	ReservationVO vo = null;
	
	private int res = 0;
	
	public StudentUpdate(int row, JTable table) {
		super("학생정보 수정");
		setSize(300, 200);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 260, 110);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		lblStudentID = new JLabel("학 번");
		lblStudentID.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		lblStudentID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentID.setBounds(11, 16, 50, 30);
		pn1.add(lblStudentID);
		
		lblStudentName = new JLabel("이 름");
		lblStudentName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentName.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		lblStudentName.setBounds(11, 62, 50, 30);
		pn1.add(lblStudentName);
		
		txtStudentID = new JTextField();
		txtStudentID.setEditable(false);
		txtStudentID.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		txtStudentID.setText(table.getValueAt(row, 1).toString());
		txtStudentID.setBounds(87, 16, 160, 30);
		pn1.add(txtStudentID);
		txtStudentID.setColumns(10);
		
		txtStudentName = new JTextField();
		txtStudentName.setText(table.getValueAt(row, 0).toString());
		txtStudentName.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		txtStudentName.setColumns(10);
		txtStudentName.setBounds(87, 62, 160, 30);
		pn1.add(txtStudentName);
		
		JPanel pn2 = new JPanel();
		pn2.setBounds(12, 122, 260, 29);
		getContentPane().add(pn2);
		pn2.setLayout(null);
		
		btnUpdate = new JButton("변경");
		btnUpdate.setFont(new Font("이사만루체 Light", Font.PLAIN, 13));
		btnUpdate.setBounds(118, 0, 60, 29);
		pn2.add(btnUpdate);
		
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("이사만루체 Light", Font.PLAIN, 13));
		btnCancel.setBounds(190, 0, 60, 29);
		pn2.add(btnCancel);
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------------
		
		// 변경 버튼
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtStudentName.getText().trim();
				vo = new ReservationVO();
				if(!Pattern.matches("^[a-zA-Z가-힣]+$", name)) {
					JOptionPane.showMessageDialog(null, "이름이 형식에 맞지 않습니다.");
					txtStudentName.setText("");
					txtStudentName.requestFocus();
				}
				else {
					vo.setStudentID(table.getValueAt(row, 1).toString());
					vo.setStudentName(txtStudentName.getText());				
					res = dao.setStudentInfo(vo);
					if(res != 0) {
						JOptionPane.showMessageDialog(null, "학생 정보가 수정되었습니다.");
						dispose();
						new AdminMyPage("admin");
					}
					else {
						JOptionPane.showMessageDialog(null, "수정 실패");
					}
				}
			}
		});
		
		// 취소 버튼
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminMyPage("admin");
			}
		});
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
				new AdminMyPage("admin");
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}
}
