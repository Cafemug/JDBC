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
	
	private JLabel idLabel = new JLabel("���̵�");
	private JLabel pwLabel = new JLabel("��й�ȣ");
	private JTextField idInput = new JTextField();
	private JPasswordField pwInput = new JPasswordField();
	private JButton login_B = new JButton("�α���");
	static dbconnection db = new dbconnection();
	
	private String id ,password; 
	//�α��� gui
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
	
	
	// �α��� ��ư�� ������ �� 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login_B){
			//idInput�� textField �� pwdInput�� �޾Ƽ� db ���ӽ� id, password�� �Է�			
			id = idInput.getText().trim(); 
			password = new String(pwInput.getPassword());		
			
			if(id.length() == 0 || password.length() == 0){
				//id, �Ǵ� ����� �Է����� �ʾ��� �� ����ó��
				JOptionPane.showMessageDialog(null, "id �Ǵ�  ��й�ȣ�� �Է����� �ʾҽ��ϴ�.");
			}
			//id, �Ǵ� ����� �Է��Ͽ��� ��
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
					
					JOptionPane.showMessageDialog(null, "DB id ��  ��ȣ�� �ٽ� �Է����ּ���");
							
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "DB ���ӿ� ������ ������ϴ�.");
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "DB ���ӿ� ������ ������ϴ�." + e1);
					
				}
			}		
			
		}
		
	}
	
	
}

