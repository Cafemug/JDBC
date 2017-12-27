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
	 
	
	 
	 //직원 로그인시 그 직원의 이름과 권한을 받아두는 변수입니다.
	 static String staffname = ""; static String stafflevel = "";	 	
	
   JFrame frame = new JFrame();
   //open메뉴에 들어갈 라벨들
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
   static JTextArea TextArea,textarea_room,Stafftextarea,textarea_menu;/////주문내역화면
   static Choice tableNumber,datechoice,roomnum,daycount,roomno;
   
   JButton  StaffRegi,StaffSearch,menuRegi_menu,Searchmenu;
   JButton GuestRegi,GuestSearch, enroll,cancel;
   JTabbedPane tabPanel;   
   static JLabel table[] = new JLabel[21];    
    
  

   public gui2() throws SQLException, ParseException{
	  
      frame = new JFrame("HOTEL");// 프레임 제목
      frame.setLayout(null);      //상당 메뉴바 생성             
       
      upmenu = new JMenuBar(); //상당 메뉴바 생성       
      //visible         
      JMenu file = new JMenu("file"); //메뉴바 상위메뉴
      fileopen = new JMenuItem("Open"); //메뉴바 하위메뉴 생성
      
      
      file.add(fileopen); //상위메뉴에 하위메뉴 생성
     
      upmenu.add(file); //상위메뉴를 메뉴바에 추가
         
      fileopen.addActionListener((ActionListener) this);
      
       frame.setJMenuBar(upmenu); //visible      
      //이곳엔 호텔 관리시스템, 투숙예약, 객실예약 현황, 현재 날짜 , 등록/조회 라벨 생성      
       /////title Label
       title = new JLabel ("호텔 관리 시스템",JLabel.CENTER);
       title.setFont(new Font("돋움",Font.BOLD,25)); //글씨체 
       title.setBounds(90,10,500,50); //label 위치
       title.setBorder(new LineBorder(Color.black)); // label 테두리설정
       
       
       room = new JLabel("객실 예약현황",JLabel.CENTER);
       room.setFont(new Font("돋움",Font.BOLD,15));
       room.setBounds(320,65,150,20);
       Calendar cDateCal = Calendar.getInstance();
       cDateCal.add(Calendar.DATE, 0);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
       String result = sdf.format(cDateCal.getTime());
       currentdate = new JLabel(result,JLabel.RIGHT);
       currentdate.setFont(new Font("돋움",Font.BOLD,15));
       currentdate.setBounds(520,65,150,20);
       
       
       stateroom = new JLabel("투숙내역",JLabel.CENTER);
       stateroom.setFont(new Font("돋움",Font.BOLD,15));
       stateroom.setBounds(20,65,60,20);
      
      
       regi = new JLabel("등록/조회",JLabel.CENTER);
       regi.setFont(new Font("돋움",Font.BOLD,15));
       regi.setBounds(20,330,80,22);   
            
       reserve_panel = new JPanel();
       reserve_panel.setLayout(null);
      
       
       customer = new JLabel("고객명");
       customer.setBounds(30,10,120,30);
       insertcustomer = new TextField();
       insertcustomer.setBounds(160,10,120,30);
       year = new JLabel("체크인(YYYYMMDD)");
       year.setBounds(30,50,120,30);
       insertyear= new TextField();
       insertyear.setBounds(160,50,120,30);
       
       day = new JLabel("박");
       day.setBounds(30,90,120,30);
       daycount = new Choice();
       for(int i=1;i<=10;i++) {
    	   String idx=String.valueOf(i);
    	   daycount.add(idx);
       }
       daycount.addItemListener(this);
       daycount.setBounds(160,90,120,40);
      
       ro = new JLabel("객실");
       ro.setBounds(30,130,120,30);
       roomnum = new Choice();
       dbcon.currentroomno();
       roomnum.addItemListener(this);
       roomnum.setBounds(160,130,120,40);
       
       enroll = new JButton("예약 등록/변경");
       enroll.addActionListener(this);
       enroll.setFont(new Font("돋움",Font.BOLD,11));
       enroll.setBounds(30,160,120,60);
       
       cancel = new JButton("예약 취소");
       cancel.addActionListener(this);
       cancel.setBounds(160,160,120,60);
       cancel.setFont(new Font("돋움",Font.BOLD,11));
      
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
       
      //테이블 현황 패널 생성
       ptable = new JPanel(new GridLayout(4,5,5,5));
       for(int i=1; i<=10; i++){
          table[i] = new JLabel(100+i+"");
           
          table[i].setFont(new Font("돋움",Font.BOLD,15));
          table[i].setBorder(new LineBorder(Color.black)); //모든 라벨의 테두리에 검은 선이 보이게 추가
          table[i].setOpaque(true); //라벨의 배경색을 보이게 해주는 것         
              table[i].setBackground(Color.WHITE); // 배경색을 흰색으로 통일
              table[i].setHorizontalAlignment(JLabel.CENTER); //라벨안의 호수 숫자를 가운데 정렬
          ptable.add(table[i]); // 패널에 라벨을 집어넣음
       } 
       for(int i=11; i<=20; i++){
           table[i] = new JLabel(200+i-10+"");
           
           table[i].setFont(new Font("돋움",Font.BOLD,15));
           table[i].setBorder(new LineBorder(Color.black)); //모든 라벨의 테두리에 검은 선이 보이게 추가
           table[i].setOpaque(true); //라벨의 배경색을 보이게 해주는 것         
               table[i].setBackground(Color.WHITE); // 배경색을 흰색으로 통일
               table[i].setHorizontalAlignment(JLabel.CENTER); //라벨안의 호수 숫자를 가운데 정렬
           ptable.add(table[i]); // 패널에 라벨을 집어넣음
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
      
       GuestNamelabel = new JLabel("고객명"); 
       GuestNamelabel.setFont(new Font("돋움",Font.BOLD,13));
       GuestNamelabel.setBounds(20,40,60,20);
             
       GuestNametext = new TextField(10);
       GuestNametext.setBounds(80,40,80,25);
             
       GuestRegi = new JButton("가입");
       GuestRegi.addActionListener(this);
       GuestRegi.setBounds(25,80,55,30);
       GuestRegi.setFont(new Font("돋움", Font.PLAIN, 10));
       
       GuestSearch = new JButton("조회");
       GuestSearch.setFont(new Font("돋움", Font.PLAIN, 10));
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
       
       
       roomname=new JLabel("객실");
       roomname.setFont(new Font("돋움",Font.BOLD,15));
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
       
       StaffName = new JLabel("직원명");       
       StaffName.setFont(new Font("돋움",Font.BOLD,15));
       StaffName.setBounds(20,40,60,20);
             
       StaffNametext = new TextField(10);
       StaffNametext.setBounds(80,40,80,25);
       
       StaffRegi = new JButton("직원등록");
       StaffRegi.addActionListener(this);
       StaffRegi.setBounds(25,80,75,30);
       StaffRegi.setFont(new Font("돋움", Font.PLAIN, 10));
             
       StaffSearch = new JButton("조회");
       StaffSearch.addActionListener(this);
       StaffSearch.setBounds(115,80,55,30);
       StaffSearch.setFont(new Font("돋움", Font.PLAIN, 10));
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
         
       tabPanel.add("고객",personTab);
       tabPanel.add("객실",RoomTab);
       tabPanel.add("직원",StaffTab);
       
       
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
            		System.out.println("회원목록입니다");
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
            	JOptionPane.showMessageDialog(null, "파일 읽기에 실패했습니다. : 다시 시도해 주세요" );
            }
         }                 
       }                     
      catch (FileNotFoundException e) {
         JOptionPane.showMessageDialog(null, "파일 읽기에 실패했습니다. : 다시 시도해 주세요" );
      }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null, "파일 읽기에 실패했습니다. : 다시 시도해 주세요" );      
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
               JOptionPane.showMessageDialog(null, "파일을 읽어들이는 도중 문제가 발생했습니다. 다시 시도해 주세요.");    
            }
         }            
      }
      else if (e.getSource() == enroll){
    	  
  	    if(insertcustomer.getText().trim().length() == 0 || insertyear.getText().trim().length() == 0  ){
  	    	JOptionPane.showMessageDialog(null, "입력을 다 하지 않았습니다");  
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
    	    	JOptionPane.showMessageDialog(null, "입력을 다 하지 않았습니다");  
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
    	            JOptionPane.showMessageDialog(null, "회원이름을 입력해주세요");
    	         }
    	         else{
    	            try{
    	            	 dbcon.getcustomerInformation(GuestNametext.getText().trim());
    	            }catch(Exception e1){
    	               JOptionPane.showMessageDialog(null, "회원이름을 가져오는 도중에 문제가 발생했습니다."+ e1);
    	            }
    	         
     	}
     	
                      
      }
      
  
      else if (e.getSource() == StaffRegi){
    	  
    		  registaff sr = new registaff();
    	    	 sr.staffRegistration();
     	
      }
      
      else if(e.getSource() == StaffSearch){
    	  
    		  if(StaffNametext.getText().trim().length() == 0){
    	            JOptionPane.showMessageDialog(null, "직원이름을 입력해주세요");
    	         }
    	         else{
    	            try{
    	               dbcon.getmanagerInformation(StaffNametext.getText().trim());
    	            }catch(SQLException e1){
    	               JOptionPane.showMessageDialog(null, "직원이름을 가져오는 도중에 문제가 발생했습니다."+ e1);
    	            }
    	         }
     	}
     	
      }
      
   }

