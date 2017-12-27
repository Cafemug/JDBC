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
	
	//ȸ������ �г�
	private JFrame title;
	private JLabel name, gender,address, phone;
	private JButton regi, cancel;
	private JTextField insertName, insertPhone;
	private Choice insertGender,insertAddress;  
	public void guestRegistration(){
		title = new JFrame("ȸ������");
		title.setLayout(null); 			
		
		name = new JLabel("�� �� ��"); 
		name.setBounds(40,40,60,30);			
		
		gender = new JLabel("����");
		gender.setBounds(40,100,60,30);
					
		address = new JLabel("�ּ�");
		address.setBounds(40,160,60,30);
				
		phone = new JLabel("����ó");
		phone.setBounds(40,220,60,30);
					
		regi = new JButton("ȸ������");
		regi.setBounds(40,280,100,30);
		
		regi.addActionListener(this);
		
		cancel = new JButton("���");
		cancel.setBounds(200,280,60,30);
		
		cancel.addActionListener(this);
		
		insertName = new JTextField(10);
		insertName.setBounds(160,40,100,30);
		
		insertGender = new Choice();
		insertGender.add("��");   
		insertGender.add("��");
		insertGender.setBounds(160,100,100,30);	

		insertAddress = new Choice();
		insertAddress.add("����");   
		insertAddress.add("���/��õ");
		insertAddress.add("���");
		insertAddress.add("�泲");
		insertAddress.add("����");
		insertAddress.add("����");
		insertAddress.add("����");
		insertAddress.add("����");
		insertAddress.add("���");
		insertAddress.add("�泲");
		insertAddress.add("����");
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
								
	
		title.setSize(400, 400); // ������ ũ��
		title.setLocation(650, 300); // ������ ��ġ
		title.setVisible(true);
		title.setDefaultCloseOperation(title.DISPOSE_ON_CLOSE);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == regi){	
			if(insertName.getText().trim().length() == 0 || insertPhone.getText().trim().length() == 0 ){
				JOptionPane.showMessageDialog(null, "�Է¶��� ����ֽ��ϴ�.");
			}else{
				try{
					//regiGuest(String guestname, String guestID, String guestbirthday, String guestLevel)
					dblogin.db.regicustomer(insertName.getText().trim(),insertGender.getSelectedItem(),insertAddress.getSelectedItem(),insertPhone.getText().trim());	 
					JOptionPane.showMessageDialog(null, "ȸ����� �Ϸ�");
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "�̸����̸� 10�ڳ���, ���ڴ� 5�ڸ��� �Է��ϼž� �մϴ�.");
				}	
			}								
		      	
		}else if(e.getSource() == cancel){
			title.dispose();
			
		}
		
	}
}

