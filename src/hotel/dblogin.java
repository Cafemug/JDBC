package hotel;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hotel.gui2;

public class dblogin implements ActionListener{
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	
	private JLabel idLabel = new JLabel("아이디");
	private JLabel pwLabel = new JLabel("비밀번호");
	private JTextField idInput = new JTextField();
	private JPasswordField pwInput = new JPasswordField();
	private JButton login_B = new JButton("로그인");
	static dbconnection db = new dbconnection();
	
	private String id ,password; 
	//로그인 gui
	public dblogin() {
		
		frame.setTitle("DB LOGIN");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		idLabel.setBounds(20,10,60,30);
		pwLabel.setBounds(20,50,60,30);
		idInput.setBounds(100,10,80,30);
		pwInput.setBounds(100,50,80,30);
		login_B.setBounds(200,25,80,35);
		login_B.addActionListener(this);
		
		panel.add(idLabel); panel.add(pwLabel);
		panel.add(idInput); panel.add(pwInput);
		panel.add(login_B);
		
		frame.add(panel);
		frame.setSize(320,130);
		frame.setLocation(700, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	// 로그인 버튼을 눌렀을 때 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login_B){
			//idInput의 textField 와 pwdInput를 받아서 db 접속시 id, password로 입력			
			id = idInput.getText().trim(); 
			password = new String(pwInput.getPassword());		
			
			if(id.length() == 0 || password.length() == 0){
				//id, 또는 비번을 입력하지 않았을 때 예외처리
				JOptionPane.showMessageDialog(null, "id 또는  비밀번호를 입력하지 않았습니다.");
			}
			//id, 또는 비번을 입력하였을 때
			else{
				try {
					db.connectDB(id, password); 
					System.out.println("DB id : " + id);
					System.out.println("DB password : " + password);
					System.out.println("-------------------- ");
					frame.setVisible(false);
					new gui2();					
					
				} 
				
				
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "DB id 및  암호를 다시 입력해주세요");
							
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "DB 접속에 문제가 생겼습니다.");
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "DB 접속에 문제가 생겼습니다." + e1);
					
				}
			}		
			
		}
		
	}
	
	
}

