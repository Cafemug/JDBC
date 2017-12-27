package hotel;


import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import  hotel.dblogin;

public class regiguest implements ActionListener {
	
	//회원가입 패널
	private JFrame title;
	private JLabel name, gender,address, phone;
	private JButton regi, cancel;
	private JTextField insertName, insertPhone;
	private Choice insertGender,insertAddress;  
	public void guestRegistration(){
		title = new JFrame("회원가입");
		title.setLayout(null); 			
		
		name = new JLabel("고 객 명"); 
		name.setBounds(40,40,60,30);			
		
		gender = new JLabel("성별");
		gender.setBounds(40,100,60,30);
					
		address = new JLabel("주소");
		address.setBounds(40,160,60,30);
				
		phone = new JLabel("연락처");
		phone.setBounds(40,220,60,30);
					
		regi = new JButton("회원가입");
		regi.setBounds(40,280,100,30);
		
		regi.addActionListener(this);
		
		cancel = new JButton("취소");
		cancel.setBounds(200,280,60,30);
		
		cancel.addActionListener(this);
		
		insertName = new JTextField(10);
		insertName.setBounds(160,40,100,30);
		
		insertGender = new Choice();
		insertGender.add("남");   
		insertGender.add("여");
		insertGender.setBounds(160,100,100,30);	

		insertAddress = new Choice();
		insertAddress.add("서울");   
		insertAddress.add("경기/인천");
		insertAddress.add("충북");
		insertAddress.add("충남");
		insertAddress.add("세종");
		insertAddress.add("강원");
		insertAddress.add("전북");
		insertAddress.add("전남");
		insertAddress.add("경북");
		insertAddress.add("경남");
		insertAddress.add("제주");
		insertAddress.setBounds(160,160,100,30);

			
		insertPhone = new JTextField(10);
		insertPhone.setBounds(160, 220, 100, 30);			
		
				
					
		title.add(name); 			
		title.add(gender);
		title.add(address);
		title.add(phone);
		title.add(regi);			
		title.add(cancel);
		title.add(insertName);
		title.add(insertGender);
		title.add(insertAddress);
		title.add(insertPhone);
								
	
		title.setSize(400, 400); // 프레임 크기
		title.setLocation(650, 300); // 프레임 위치
		title.setVisible(true);
		title.setDefaultCloseOperation(title.DISPOSE_ON_CLOSE);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == regi){	
			if(insertName.getText().trim().length() == 0 || insertPhone.getText().trim().length() == 0 ){
				JOptionPane.showMessageDialog(null, "입력란이 비어있습니다.");
			}else{
				try{
					//regiGuest(String guestname, String guestID, String guestbirthday, String guestLevel)
					dblogin.db.regicustomer(insertName.getText().trim(),insertGender.getSelectedItem(),insertAddress.getSelectedItem(),insertPhone.getText().trim());	 
					JOptionPane.showMessageDialog(null, "회원등록 완료");
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "이름길이를 10자내로, 숫자는 5자리로 입력하셔야 합니다.");
				}	
			}								
		      	
		}else if(e.getSource() == cancel){
			title.dispose();
			
		}
		
	}
}

