package javaclass9;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AdminReservationMain extends JFrame {
	private JButton btnLogout,btnMyPage,btnReserve;
	private JLabel lblTitle;
	private JScrollPane scrollPane;
	
	ReservationDAO dao = new ReservationDAO();
	private JTable table;
	private Vector title,vData;
	private DefaultTableModel dtm;
	private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	
	public AdminReservationMain(String id) {
		super("관리자 메인 화면");
		setSize(600,450);
		getContentPane().setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBounds(12, 10, 560, 50);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		btnLogout = new JButton("로그아웃");
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnLogout.setBounds(443, 10, 105, 30);
		pn1.add(btnLogout);
		
		btnMyPage = new JButton("학생 관리");
		btnMyPage.setBackground(new Color(255, 255, 255));
		btnMyPage.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnMyPage.setBounds(12, 10, 105, 30);
		pn1.add(btnMyPage);
		
		lblTitle = new JLabel("연 습 실   예 약");
		lblTitle.setFont(new Font("이사만루체 Bold", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(177, 4, 204, 40);
		pn1.add(lblTitle);
		
		JPanel pn2 = new JPanel();
		pn2.setBounds(12, 70, 560, 270);
		getContentPane().add(pn2);
		pn2.setLayout(null);
		
		JPanel pn3 = new JPanel();
		pn3.setBounds(12, 350, 560, 50);
		getContentPane().add(pn3);
		pn3.setLayout(null);
		
		btnReserve = new JButton("예약하기");
		btnReserve.setBackground(new Color(255, 255, 255));
		btnReserve.setFont(new Font("이사만루체 Light", Font.PLAIN, 14));
		btnReserve.setBounds(12, 10, 97, 30);
		pn3.add(btnReserve);
		
		
		// 메인화면에 전체 예약현황 출력
		title = new Vector<>();
		title.add("연습실");
		title.add("날짜");
		title.add("시간");
		title.add("예약자(학번)");
		
		vData = dao.getAllList();
		dtm = new DefaultTableModel(vData,title);
		table = new JTable(dtm);
		TableColumnModel tcm = table.getColumnModel();
		table.setRowSorter(new TableRowSorter(dtm));
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0; i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}		
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 10, 536, 250);
		pn2.add(scrollPane);
		
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
		
		// 예약 관리
		btnMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminMyPage(id);
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