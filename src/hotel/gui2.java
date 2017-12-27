package hotel;


import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import hotel.dbconnection;





class gui2 implements  ActionListener, ItemListener {
	 static dbconnection dbcon = new dbconnection();
	 
	
	 
	 //���� �α��ν� �� ������ �̸��� ������ �޾Ƶδ� �����Դϴ�.
	 static String staffname = ""; static String stafflevel = "";	 	
	
   JFrame frame = new JFrame();
   //open�޴��� �� �󺧵�
   JMenuBar upmenu; 
   JMenuItem fileopen, menulogin;/////
   JFileChooser jf = new JFileChooser();    
   
   
 
   JLabel  NameLabel_menu,StaffName,date,GuestNamelabel,title,room,roomname,stateroom,menu,regi,currentdate;
   JLabel  customer,year,ro, day;
   static JPanel ptable,search_regi,personTab, RoomTab,StaffTab,menuTab,reserve_panel;   
   JTextField personname_T;
   TextField Nametext_menu,insertcustomer,insertyear;
   TextField StaffNametext;
   TextField GuestNametext;
   static JTextArea TextArea,textarea_room,Stafftextarea,textarea_menu;/////�ֹ�����ȭ��
   static Choice tableNumber,datechoice,roomnum,daycount,roomno;
   
   JButton  StaffRegi,StaffSearch,menuRegi_menu,Searchmenu;
   JButton GuestRegi,GuestSearch, enroll,cancel;
   JTabbedPane tabPanel;   
   static JLabel table[] = new JLabel[21];    
    
  

   public gui2() throws SQLException, ParseException{
	  
      frame = new JFrame("HOTEL");// ������ ����
      frame.setLayout(null);      //��� �޴��� ����             
       
      upmenu = new JMenuBar(); //��� �޴��� ����       
      //visible         
      JMenu file = new JMenu("file"); //�޴��� �����޴�
      fileopen = new JMenuItem("Open"); //�޴��� �����޴� ����
      
      
      file.add(fileopen); //�����޴��� �����޴� ����
     
      upmenu.add(file); //�����޴��� �޴��ٿ� �߰�
         
      fileopen.addActionListener((ActionListener) this);
      
       frame.setJMenuBar(upmenu); //visible      
      //�̰��� ȣ�� �����ý���, ��������, ���ǿ��� ��Ȳ, ���� ��¥ , ���/��ȸ �� ����      
       /////title Label
       title = new JLabel ("ȣ�� ���� �ý���",JLabel.CENTER);
       title.setFont(new Font("����",Font.BOLD,25)); //�۾�ü 
       title.setBounds(90,10,500,50); //label ��ġ
       title.setBorder(new LineBorder(Color.black)); // label �׵θ�����
       
       
       room = new JLabel("���� ������Ȳ",JLabel.CENTER);
       room.setFont(new Font("����",Font.BOLD,15));
       room.setBounds(320,65,150,20);
       Calendar cDateCal = Calendar.getInstance();
       cDateCal.add(Calendar.DATE, 0);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
       String result = sdf.format(cDateCal.getTime());
       currentdate = new JLabel(result,JLabel.RIGHT);
       currentdate.setFont(new Font("����",Font.BOLD,15));
       currentdate.setBounds(520,65,150,20);
       
       
       stateroom = new JLabel("��������",JLabel.CENTER);
       stateroom.setFont(new Font("����",Font.BOLD,15));
       stateroom.setBounds(20,65,60,20);
      
      
       regi = new JLabel("���/��ȸ",JLabel.CENTER);
       regi.setFont(new Font("����",Font.BOLD,15));
       regi.setBounds(20,330,80,22);   
            
       reserve_panel = new JPanel();
       reserve_panel.setLayout(null);
      
       
       customer = new JLabel("����");
       customer.setBounds(30,10,120,30);
       insertcustomer = new TextField();
       insertcustomer.setBounds(160,10,120,30);
       year = new JLabel("üũ��(YYYYMMDD)");
       year.setBounds(30,50,120,30);
       insertyear= new TextField();
       insertyear.setBounds(160,50,120,30);
       
       day = new JLabel("��");
       day.setBounds(30,90,120,30);
       daycount = new Choice();
       for(int i=1;i<=10;i++) {
    	   String idx=String.valueOf(i);
    	   daycount.add(idx);
       }
       daycount.addItemListener(this);
       daycount.setBounds(160,90,120,40);
      
       ro = new JLabel("����");
       ro.setBounds(30,130,120,30);
       roomnum = new Choice();
       dbcon.currentroomno();
       roomnum.addItemListener(this);
       roomnum.setBounds(160,130,120,40);
       
       enroll = new JButton("���� ���/����");
       enroll.addActionListener(this);
       enroll.setFont(new Font("����",Font.BOLD,11));
       enroll.setBounds(30,160,120,60);
       
       cancel = new JButton("���� ���");
       cancel.addActionListener(this);
       cancel.setBounds(160,160,120,60);
       cancel.setFont(new Font("����",Font.BOLD,11));
      
       reserve_panel.add(customer);
       reserve_panel.add(insertcustomer);
       reserve_panel.add(year);
       reserve_panel.add(insertyear);
       reserve_panel.add(day);
       reserve_panel.add(daycount);
       reserve_panel.add(ro);
       reserve_panel.add(roomnum);
       reserve_panel.add(enroll);
       reserve_panel.add(cancel);
       reserve_panel.setBounds(20,85,300,230);
       
       frame.add(reserve_panel);
       
      //���̺� ��Ȳ �г� ����
       ptable = new JPanel(new GridLayout(4,5,5,5));
       for(int i=1; i<=10; i++){
          table[i] = new JLabel(100+i+"");
           
          table[i].setFont(new Font("����",Font.BOLD,15));
          table[i].setBorder(new LineBorder(Color.black)); //��� ���� �׵θ��� ���� ���� ���̰� �߰�
          table[i].setOpaque(true); //���� ������ ���̰� ���ִ� ��         
              table[i].setBackground(Color.WHITE); // ������ ������� ����
              table[i].setHorizontalAlignment(JLabel.CENTER); //�󺧾��� ȣ�� ���ڸ� ��� ����
          ptable.add(table[i]); // �гο� ���� �������
       } 
       for(int i=11; i<=20; i++){
           table[i] = new JLabel(200+i-10+"");
           
           table[i].setFont(new Font("����",Font.BOLD,15));
           table[i].setBorder(new LineBorder(Color.black)); //��� ���� �׵θ��� ���� ���� ���̰� �߰�
           table[i].setOpaque(true); //���� ������ ���̰� ���ִ� ��         
               table[i].setBackground(Color.WHITE); // ������ ������� ����
               table[i].setHorizontalAlignment(JLabel.CENTER); //�󺧾��� ȣ�� ���ڸ� ��� ����
           ptable.add(table[i]); // �гο� ���� �������
        }
       ptable.setBounds(340, 90, 330, 220);/////
       ptable.setBorder(new LineBorder(Color.black));
       
   
       search_regi = new JPanel();
       search_regi.setLayout(null);
       
       tabPanel = new JTabbedPane();
       tabPanel.setBounds(10, 0, 610, 295);
       
       
       personTab = new JPanel();
       personTab.setBorder(new LineBorder(Color.black));
       personTab.setLayout(null);
      
       GuestNamelabel = new JLabel("����"); 
       GuestNamelabel.setFont(new Font("����",Font.BOLD,13));
       GuestNamelabel.setBounds(20,40,60,20);
             
       GuestNametext = new TextField(10);
       GuestNametext.setBounds(80,40,80,25);
             
       GuestRegi = new JButton("����");
       GuestRegi.addActionListener(this);
       GuestRegi.setBounds(25,80,55,30);
       GuestRegi.setFont(new Font("����", Font.PLAIN, 10));
       
       GuestSearch = new JButton("��ȸ");
       GuestSearch.setFont(new Font("����", Font.PLAIN, 10));
       GuestSearch.addActionListener(this);
       GuestSearch.setBounds(95,80,55,30);
       
       TextArea = new JTextArea(10,20);
       TextArea.setBounds(250,40,300,180);
       TextArea.setBorder(new LineBorder(Color.black));
       TextArea.setEditable(false);
       
       personTab.add(GuestNamelabel);
       personTab.add(GuestNametext);
       personTab.add(GuestRegi);
       personTab.add(GuestSearch);
       personTab.add(TextArea);
       
    
       RoomTab = new JPanel();
       RoomTab.setLayout(null);
       RoomTab.setBorder(new LineBorder(Color.black));
       
       
       roomname=new JLabel("����");
       roomname.setFont(new Font("����",Font.BOLD,15));
       roomname.setBounds(20,40,60,20);
       
       
       roomno = new Choice();
       dbcon.getroomno();
       roomno.setBounds(80,40,80,25);
       roomno.addItemListener(this);
       
       textarea_room = new JTextArea(10,20);
       textarea_room.setBounds(250,40,300,180);/////
       textarea_room.setBorder(new LineBorder(Color.black));
       textarea_room.setEditable(false);
       
       RoomTab.add(textarea_room);
       RoomTab.add(roomname);
       RoomTab.add(roomno);
       
     
       StaffTab = new JPanel();
       StaffTab.setLayout(null);
       StaffTab.setBorder(new LineBorder(Color.black));
       
       StaffName = new JLabel("������");       
       StaffName.setFont(new Font("����",Font.BOLD,15));
       StaffName.setBounds(20,40,60,20);
             
       StaffNametext = new TextField(10);
       StaffNametext.setBounds(80,40,80,25);
       
       StaffRegi = new JButton("�������");
       StaffRegi.addActionListener(this);
       StaffRegi.setBounds(25,80,75,30);
       StaffRegi.setFont(new Font("����", Font.PLAIN, 10));
             
       StaffSearch = new JButton("��ȸ");
       StaffSearch.addActionListener(this);
       StaffSearch.setBounds(115,80,55,30);
       StaffSearch.setFont(new Font("����", Font.PLAIN, 10));
       Stafftextarea = new JTextArea(10,20);
       Stafftextarea.setBounds(250,40,300,180);
       Stafftextarea.setBorder(new LineBorder(Color.black));
       Stafftextarea.setEditable(false);
             
       StaffTab.add(StaffNametext);
       StaffTab.add(StaffName);
       StaffTab.add(StaffNametext);
       StaffTab.add(StaffRegi);
       StaffTab.add(StaffSearch);
       StaffTab.add(Stafftextarea);
         
       tabPanel.add("��",personTab);
       tabPanel.add("����",RoomTab);
       tabPanel.add("����",StaffTab);
       
       
       search_regi.add(tabPanel);
       search_regi.setBounds(25, 355, 630, 300);
       search_regi.setBorder(new LineBorder(Color.black));
            
   
      frame.add(title);  
      frame.add(room); 
      frame.add(stateroom);
      
      frame.add(regi);
      frame.add(ptable);
      
      frame.add(search_regi);      
      frame.add(currentdate);
      frame.setSize(700,730); 
      frame.setLocation(0,0); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      frame.setVisible(true);         
      dbcon.updatetable();
   }
   

 
   public void readFile(File textfile) throws SQLException{
      Scanner scanner = null;
         
      int indexcount = 0;     
      int check = 0;
      
      try { 
         scanner = new Scanner(textfile);
         while(scanner.hasNextLine()){
            String test = scanner.nextLine().toString();
            
            try{
            	int number = Integer.parseInt(test);
       
            	check ++;     	
            	
            	if(check == 1){
            		System.out.println("ȸ������Դϴ�");
            		while(indexcount < number){
            	
            		test = scanner.nextLine().toString();            		
            		StringTokenizer token = new StringTokenizer(test,"\t");
            		while(token.hasMoreTokens()){     
            			 String cname = token.nextToken();
            			 String cgender = token.nextToken();
            			 String caddress = token.nextToken();
            			 String cphone = token.nextToken();
            			 
            			 System.out.println("guestname = " + cname + ", guestID = " + cgender+", guestBirthday = " + caddress+", guestLevel = " + cphone);
            			 dbcon.regicustomer(cname, cgender, caddress, cphone);
            		 }            		 
            		indexcount ++ ;
            		}            		
            		indexcount = 0;
            	
            	}
            	else if(check == 2){
            		
            		while(indexcount < number){
                		test = scanner.nextLine().toString();                		
                		StringTokenizer token = new StringTokenizer(test,"\t");
                		 while(token.hasMoreTokens()){     
                			 String mname = token.nextToken();
                			 String mgender = token.nextToken();
                			 String maddress = token.nextToken();
                			 String mphone = token.nextToken();              			
                			 
                			
                			 dbcon.regimanager(mname, mgender,maddress, mphone); 			
                		 }                		 
                		indexcount ++ ;
                		}                		
                		indexcount = 0;
                		
            	}
            	else if(check == 3){
            		
            		while(indexcount < number){
                		test = scanner.nextLine().toString();	                		
                		StringTokenizer token = new StringTokenizer(test,"\t");
                		 while(token.hasMoreTokens()){ 
                			 int roomno = Integer.parseInt(token.nextToken());
                			 int ableno = Integer.parseInt(token.nextToken());
                			 String roomtype = token.nextToken();
                			 
                			 
                			
                			 dbcon.regiroom(roomno,ableno,roomtype);
                		 }                		 
                		indexcount ++ ;
                		}                		
                		indexcount = 0;
                		
            	}
            }
            catch(Exception e){
            	JOptionPane.showMessageDialog(null, "���� �б⿡ �����߽��ϴ�. : �ٽ� �õ��� �ּ���" );
            }
         }                 
       }                     
      catch (FileNotFoundException e) {
         JOptionPane.showMessageDialog(null, "���� �б⿡ �����߽��ϴ�. : �ٽ� �õ��� �ּ���" );
      }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null, "���� �б⿡ �����߽��ϴ�. : �ٽ� �õ��� �ּ���" );      
      }      
      finally{
         scanner.close();
      }
   }
   
   
   public void itemStateChanged(ItemEvent e) {
	   if(e.getSource() == roomno){
	 		  try {
	    				dbcon.getroom(roomno.getSelectedItem());
	    			} catch (SQLException e1) {
	    			
	    				e1.printStackTrace();
	    			}
	     	}
	     	
	     }
	   
	  
   
   
  
  
   public void actionPerformed(ActionEvent e)  {
	      
      if(e.getSource()== fileopen){
         if(jf.showOpenDialog(upmenu) == JFileChooser.APPROVE_OPTION){
            File fSelectedFile = jf.getSelectedFile();   
            try {
               readFile(fSelectedFile);
              
            } catch (Exception e1) {               
               JOptionPane.showMessageDialog(null, "������ �о���̴� ���� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���.");    
            }
         }            
      }
      else if (e.getSource() == enroll){
    	  
  	    if(insertcustomer.getText().trim().length() == 0 || insertyear.getText().trim().length() == 0  ){
  	    	JOptionPane.showMessageDialog(null, "�Է��� �� ���� �ʾҽ��ϴ�");  
  	    }else{
      
          try {
          	int number = Integer.parseInt(roomnum.getSelectedItem().toString());
          	
          	dbcon.reservation(insertcustomer.getText().trim(), insertyear.getText().trim() ,Integer.parseInt(daycount.getSelectedItem().toString()),number);	
          	
          	
          	
           }
          catch (NumberFormatException e1) {
             JOptionPane.showMessageDialog(null, e1);    
          } catch (SQLException e1) {
		
			e1.printStackTrace();
		}  
  	    }
      }
      else if (e.getSource() == cancel){
    	  
    	    if(insertcustomer.getText().trim().length() == 0 || insertyear.getText().trim().length() == 0  ){
    	    	JOptionPane.showMessageDialog(null, "�Է��� �� ���� �ʾҽ��ϴ�");  
    	    }else{
        
            try {
            	int number = Integer.parseInt(roomnum.getSelectedItem().toString());
            	
            	dbcon.modifyres(insertcustomer.getText().trim(), insertyear.getText().trim());	
            	
            	
            	
             }
            catch (NumberFormatException e1) {
               JOptionPane.showMessageDialog(null, e1);    
            } catch (SQLException e1) {
  		
  			e1.printStackTrace();
  		}  
    	    }
        }
   
      
      else if(e.getSource() == GuestRegi){
    	
    		 regiguest gr = new regiguest();
             gr.guestRegistration(); 
    	
        
      }
      
      else if (e.getSource() == GuestSearch){
    	  
    		  if(GuestNametext.getText().trim().length() == 0){
    	            JOptionPane.showMessageDialog(null, "ȸ���̸��� �Է����ּ���");
    	         }
    	         else{
    	            try{
    	            	 dbcon.getcustomerInformation(GuestNametext.getText().trim());
    	            }catch(Exception e1){
    	               JOptionPane.showMessageDialog(null, "ȸ���̸��� �������� ���߿� ������ �߻��߽��ϴ�."+ e1);
    	            }
    	         
     	}
     	
                      
      }
      
  
      else if (e.getSource() == StaffRegi){
    	  
    		  registaff sr = new registaff();
    	    	 sr.staffRegistration();
     	
      }
      
      else if(e.getSource() == StaffSearch){
    	  
    		  if(StaffNametext.getText().trim().length() == 0){
    	            JOptionPane.showMessageDialog(null, "�����̸��� �Է����ּ���");
    	         }
    	         else{
    	            try{
    	               dbcon.getmanagerInformation(StaffNametext.getText().trim());
    	            }catch(SQLException e1){
    	               JOptionPane.showMessageDialog(null, "�����̸��� �������� ���߿� ������ �߻��߽��ϴ�."+ e1);
    	            }
    	         }
     	}
     	
      }
      
   }

