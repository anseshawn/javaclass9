package javaclass9;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings({ "unchecked", "serial","rawtypes" })
public class MyPage extends JFrame{
	private JButton btnBack,btnUpdate,btnDelete,btnChangePW;
	private JScrollPane scrollPane;
	private JLabel lblMypage;
	private JTable table;
	private Vector title,vData;
	private DefaultTableModel dtm;
	ReservationDAO dao = new ReservationDAO();
	ReservationVO vo = null;
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	int res = 0;
	
	public MyPage(String id) {
		super("마이페이지");
		setSize(600, 400);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 560, 35);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		btnBack = new JButton("뒤로가기");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("이사만루체 Medium", Font.PLAIN, 15));
		btnBack.setBounds(443, 5, 105, 25);
		pn1.add(btnBack);
		
		// 상단에 로그인 한 유저의 학번 및 이름 출력
		vo = myInfo(id);
		lblMypage = new JLabel("학번 : "+id+" / 이름 : "+vo.getStudentName());
		lblMypage.setBounds(12, 5, 207, 25);
		pn1.add(lblMypage);
		
		JPanel pn2 = new JPanel();
		pn2.setBounds(12, 55, 560, 262);
		getContentPane().add(pn2);
		pn2.setLayout(null);
				
		JPanel pn3 = new JPanel();
		pn3.setBounds(12, 316, 560, 35);
		getContentPane().add(pn3);
		pn3.setLayout(null);
		
		btnUpdate = new JButton("예약수정");
		btnUpdate.setBackground(new Color(255, 255, 255));
		btnUpdate.setFont(new Font("이사만루체 Light", Font.PLAIN, 15));
		btnUpdate.setBounds(12, 5, 105, 25);
		pn3.add(btnUpdate);
		
		btnDelete = new JButton("예약취소");
		btnDelete.setBackground(new Color(255, 255, 255));
		btnDelete.setFont(new Font("이사만루체 Light", Font.PLAIN, 15));
		btnDelete.setBounds(129, 5, 105, 25);
		pn3.add(btnDelete);
		
		btnChangePW = new JButton("비밀번호 변경");
		btnChangePW.setFont(new Font("이사만루체 Light", Font.PLAIN, 15));
		btnChangePW.setBackground(Color.WHITE);
		btnChangePW.setBounds(408, 6, 140, 25);
		pn3.add(btnChangePW);
		
		/* JTable 설계하기 */
		title = new Vector<>();
		title.add("연습실");
		title.add("날짜");
		title.add("시간");
		
		vData = dao.getReserveList(id);	
		dtm = new DefaultTableModel(vData,title);
		table = new JTable(dtm);
		JTableSetting(); // JTable 속성 메소드
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 560, 262);
		pn2.add(scrollPane);
		
		//--------------------------------------------------------------------
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 예약수정
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "예약내역을 선택 후 버튼을 눌러주세요.");
				}
				else {
					dispose();
					new ReservationUpdate(id,row,table);
				}
			}
		});
		
		// 예약취소
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "예약내역을 선택 후 버튼을 눌러주세요.");
				}
				else {
					int ans = JOptionPane.showConfirmDialog(null, "예약을 취소하시겠습니까?","",JOptionPane.YES_NO_OPTION);
					if(ans == 0) {
						res = dao.setDelete(id,row,table);
						if(res != 0) {
							JOptionPane.showMessageDialog(null, "예약이 취소되었습니다.");
							dispose();
							new MyPage(id);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "작업을 취소합니다.");
					}
				}
			}
		});
		
		// 비밀번호 변경
		btnChangePW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ChangePW(id,"");
			}
		});
		
		// 뒤로가기
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ReservationMain(id);
			}
		});
		
		//--------------------------------------------------------------------
		setVisible(true);
	}

	// JTable 속성
	private void JTableSetting() {
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getTableHeader().setReorderingAllowed(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setMaxWidth(400);
		for(int i=0; i<tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}

	// 상단에 학번, 이름 출력하기
	private ReservationVO myInfo(String id) {
		ReservationDAO dao = new ReservationDAO();
		ReservationVO vo = new ReservationVO();
		vo = dao.studentList(id);
		return vo;
	}
}