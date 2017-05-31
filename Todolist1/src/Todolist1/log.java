package Todolist1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class log extends Frame
{
	private File datadir;
	private File data;
	private FileReader dataread;
	private FileWriter datatext;
	private JLabel Todolist, ID, PASSWD;
	private JButton login, toregister;
	private JTextField IDin;
	private JPasswordField PASSWDin;
	private register rf;
	private todolist tdl;
	class idpass {
		private String id;
		private String pass;
		
		idpass(String id, String pass) {
			this.id = id;
			this.pass = pass;
			}
		public String getId(){
			return id;
		}
		public String getPass() {
			return pass;
		}
	}
	
    public void initdatadir() {
		datadir = new File("C:\\todolist");
		if(!datadir.exists()) {
			datadir.mkdirs();
		}	
		datadir = new File("C:\\todolist\\ForDefaultToFile");
		if(!datadir.exists()) {
			datadir.mkdirs();
		}
    }
    
    public void initdatafile() {
    	data = new File("C:\\todolist\\ForDefaultToFile\\ForDefaultToFile.txt");
    	data = new File("C:\\todolist\\savetext.txt");
		if(!data.exists()){
    		try{
    			datatext = new FileWriter("C:\\todolist\\savetext.txt",false);
    			datatext.write("ForDefaultToFile ForDefaultToFile ");
    			datatext.flush();
    			datatext.close();

    			datatext = new FileWriter("C:\\todolist\\ForDefaultToFile\\ForDefaultToFile.txt",false);
    			datatext.write("ForDefaultToFile ");
    			datatext.flush();
    			datatext.close();
    			
    		}catch (Exception e) {
    			System.out.println("오류initdatafile");
    		}
		}
    }
    
    public void readdata(idpass idandpass[]) {
    	try{
    		int i = 0;
    		dataread = new FileReader("C:\\todolist\\savetext.txt");
        	BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/2);
    		
    		for(int j = 0; j<i;j++){
	    		String id = tokenizer.nextToken();
	    		String pass = tokenizer.nextToken();
	    		idandpass[j] = new idpass(id,pass);
	    		
    		}
    		
    	}catch (Exception e) {
    		System.out.println("오류readdata");
    	}
    }
    
    public int readdatacnt() {
    	int i = 0;
    	try{
    		dataread = new FileReader("C:\\todolist\\savetext.txt");
        	BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/2);
    		
    	}catch (Exception e) {
    		System.out.println("오류readdatacn");
    	}
    	return i;
    }
        
    public log(){
    	super("login");
    	setSize(400,200);
        setLayout(null);
        
        Todolist = new JLabel("To do list");
		Todolist.setLocation(150,30);
		Todolist.setSize(120,30);
		Todolist.setFont(new Font("고딕체",Font.BOLD,20));
		Todolist.setHorizontalAlignment(Todolist.CENTER);
		add(Todolist);
		
		ID = new JLabel("ID");
		ID.setLocation(100,73);
		ID.setSize(30,20);
		ID.setFont(new Font("고딕체",Font.BOLD,15));
		ID.setHorizontalAlignment(ID.CENTER);
		add(ID);

		PASSWD = new JLabel("PASSWD");
		PASSWD.setLocation(50,103);
		PASSWD.setSize(80,20);
		PASSWD.setFont(new Font("고딕체",Font.BOLD,15));
		PASSWD.setHorizontalAlignment(PASSWD.CENTER);
		add(PASSWD);
		
		IDin = new JTextField(10);
		IDin.setLocation(163,73);
		IDin.setSize(120,20);
		add(IDin);
		
		PASSWDin = new JPasswordField(10);
		PASSWDin.setLocation(163,103);
		PASSWDin.setSize(120,20);
		PASSWDin.setEchoChar('*');
		add(PASSWDin);
		
		login = new JButton("로그인");
		login.setLocation(143,130);
		login.setSize(70,30);
		login.setFont(new Font("고딕체",Font.BOLD,10));
		login.addActionListener(new logActionListener());
		add(login);
		
		toregister = new JButton("계정 등록");
		toregister.setLocation(216,130);
		toregister.setSize(90,30);
		toregister.setFont(new Font("고딕체",Font.BOLD,10));
		toregister.addActionListener(new regActionListener());
		add(toregister);
		
		rf = new register();
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
					System.exit(0);
				}
			}
		);
		
		initdatadir();
		initdatafile();
    }
    
    class logActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Object input = e.getSource();
        	int i = 0, id = 0, pass =0;
        	i = readdatacnt();	
        	idpass idandpass[]= new idpass[i];
        	readdata(idandpass);
        	if(input==login){	
	        		if(IDin.getText().length()==0){	
	        			JOptionPane.showMessageDialog(null,"아이디를 입력하십시오.");
	        		}
	        		else if(!IDin.getText().matches("[0-9|a-z|A-Z]*")){
	        			JOptionPane.showMessageDialog(null,"아이디는 영문이나 숫자 10글자만 가능합니다.");
   			
	        		}
	        		else if(new String(PASSWDin.getPassword()).length()==0){
	        			JOptionPane.showMessageDialog(null,"패스워드를 입력하십시오.");

	        		}
	        		else if(!new String(PASSWDin.getPassword()).matches("[0-9|a-z|A-Z]*")) {
	        			JOptionPane.showMessageDialog(null,"비밀번호는 영문이나 숫자 10글자만 가능합니다.");
  
	        		}
	        		else {
		        		for(int j = 0 ; j<i; j++){
		        			if((IDin.getText().equals(idandpass[j].id))&& new String(PASSWDin.getPassword()).equals(idandpass[j].pass)){
		        				datadir = new File("C:\\todolist\\"+IDin.getText());
		        				if(!datadir.exists())
		        					datadir.mkdirs();
		        				
		        				data = new File("C:\\todolist\\"+IDin.getText()+"\\"+IDin.getText()+".txt");
		        			
		        				if(!data.exists()){
		        					try{
			        	    			datatext = new FileWriter("C:\\todolist\\"+IDin.getText()+"\\"+IDin.getText()+".txt",false);
			        	    			datatext.write("ForDefaultToFile ");
			        	    			datatext.flush();
			        	    			datatext.close();
			        	    			
			        	    			datatext = new FileWriter("C:\\todolist\\"+IDin.getText()+"\\todo.txt",false);
			        	    			datatext.write("ForDefaultToFile ");
			        	    			datatext.flush();
			        	    			datatext.close();
			        	    			
			        	    		}catch (Exception e1) {
			        	    			System.out.println("오류");
			        	    		}
		        				}
		        			
			        			tdl = new todolist(IDin.getText());
			        			tdl.setSize(1100,600);
			        			tdl.setVisible(true);
		        				break;
		        			}
			        		if(!(IDin.getText().equals(idandpass[j].id))) {
			        			id += 1;
			        			if(id == i){
				        			JOptionPane.showMessageDialog(null,"등록되지 않은 아이디입니다.");
				        			break;
				        		}
			        		}
			        		else if((IDin.getText().equals(idandpass[j].id))&&!(new String(PASSWDin.getPassword()).equals(idandpass[j].pass))) {
			        			JOptionPane.showMessageDialog(null,"패스워드가 틀렸습니다.");
			        			break;
			        		}
		        		}
		        	}
        	}
        }
    }		
    
    class regActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Object input = e.getSource();
        	if(input==toregister);
        		rf.setSize(400,200);
    			rf.setVisible(true);
        }
     }

    public static void main(String[] args) 
    {
    	log d = new log();
    	d.setSize(400,200);
		d.setVisible(true);
    }

}

class register extends Frame {
	
    private JLabel Register, id, passwd;
    private JButton register;
    private JTextField IDmade;
    private JPasswordField passwdmade;
    private FileWriter datawrite;
    private FileReader dataread;
    private Frame TODOLIST;
    
    public int readdata(String id, String pass) {
    	int q = 0;
    	try{
    		int i = 0;
    		dataread = new FileReader("C:\\todolist\\savetext.txt");
        	BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/2);
    		
    		for(int j = 0; j<i;j++){
	    		String idv= tokenizer.nextToken();
	    		String passv = tokenizer.nextToken();
	    		if(idv.equals(id)){
	    			q = 1;
	    			break;
	    		}
    		}
    		
    	}catch (Exception e) {
    		System.out.println("readdata오류");
    	}
    	return q;
    }
    
    public void writedata(String id, String pass) {
    	try{
    		datawrite = new FileWriter("C:\\todolist\\savetext.txt",true);
    		BufferedWriter out = new BufferedWriter(datawrite); 
    		out.write(" "+id+" "+pass);
    		out.flush();
    		out.close();
    		datawrite.close();
    	}catch (Exception e) {
    		System.out.println("writedatals");
    	}
    }
    
    public register(){
    	super("Register");
        setSize(400,200);
    	setLayout(null);
      
    	Register = new JLabel("계정 등록");
    	Register.setLocation(150,30);
    	Register.setSize(120,30);
    	Register.setFont(new Font("고딕체",Font.BOLD,20));
    	Register.setHorizontalAlignment(Register.CENTER);
		add(Register);
		
		id = new JLabel("id");
		id.setLocation(100,73);
		id.setSize(30,20);
		id.setFont(new Font("고딕체",Font.BOLD,15));
		id.setHorizontalAlignment(id.CENTER);
		add(id);

		passwd = new JLabel("passwd");
		passwd.setLocation(50,103);
		passwd.setSize(80,20);
		passwd.setFont(new Font("고딕체",Font.BOLD,15));
		passwd.setHorizontalAlignment(passwd.CENTER);
		add(passwd);
		
		IDmade = new JTextField(10);
		IDmade.setLocation(163,73);
		IDmade.setSize(120,20);
		add(IDmade);
	
		passwdmade = new JPasswordField(10);
		passwdmade.setLocation(163,103);
		passwdmade.setSize(120,20);
		passwdmade.setEchoChar('*');
		add(passwdmade);
		
		register = new JButton("계정 등록");
		register.setLocation(173,130);
		register.setSize(100,30);
		register.setFont(new Font("고딕체",Font.BOLD,10));
		register.addActionListener(new madeActionListener());
		add(register);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				}
			}
		);
		
    }
    
    class madeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Object input2 = e.getSource();
        	String id = IDmade.getText();
        	String passwd = new String(passwdmade.getPassword());
        	int quit = readdata(id, passwd);
        	if(input2==register){
        		if (id.length()==0) {
        			JOptionPane.showMessageDialog(null,"아이디를 입력하십시오.");
        		}
        		else if(!id.matches("[0-9|a-z|A-Z]*")) {
        			JOptionPane.showMessageDialog(null,"아이디는 영문 또는 숫자입니다.");
        		}
        		else if(id.length()>10){
        			JOptionPane.showMessageDialog(null,"아이디는 10자 이하입니다.");
        		}
        		else if (quit == 1){
        			JOptionPane.showMessageDialog(null,"이미 있는 아이디입니다.");
        		}
        		else if (passwd.length()==0) {
        			JOptionPane.showMessageDialog(null,"비밀번호를 입력하십시오.");
        		}
        		else if(!passwd.matches("[0-9|a-z|A-Z]*")) {
        			JOptionPane.showMessageDialog(null,"비밀번호는 영문 또는 숫자입니다.");
        		}
        		else if (passwd.length()>10) {
        			JOptionPane.showMessageDialog(null,"비밀번호는 10자 이하입니다.");
        		}
        		else {
        			writedata(id, passwd);
        			JOptionPane.showMessageDialog(null,"등록되었습니다.");
					dispose();
        		}
        	}
        }
     }
}

class todolist extends Frame {
	private JLabel sub, subname, pro, time, year, seme, todo, todosubname, content, deadline, finish, clear, importance, first, second, third;
	private JButton subsave, submodify, subdelete, todosave, todomodify, tododelete, search, sort, select, hide, unhide;
	private JTextField subnamein, proin, timein, yearin, semein, todosubnamein, contentin, deadlinein, finishin, clearin, importancein, context;
	private JTable sublist, todolist;
	private JScrollPane subscroll, todoscroll;
	private JComboBox fir, sec, thi, comyear, commonth;
	private DefaultTableModel subtable;
	private DefaultTableModel todotable;
	
	private String[] rank ={"과목명(오름차순)", "과목명(내림차순)", "마감 기한", "완료 날짜", "완료 여부", "중요도", "-"};
	private String[] todolistname = { "과옴명", "항목명 (해야할 일)", "마감 기한", "완료 여부", "완료 날짜", "중요도" };
	private String[] syear = {"2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021"};
	private String[] smonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};	

		
	private String[] sday = {"일", "월", "화", "수", "목", "금", "토"};
	
	private String dir;
	private int row;
	private int rowtodo;
	private Calendar today = Calendar.getInstance();
	
	private JLabel day[] = new JLabel[7];
	private JButton date[] = new JButton[42];;

	private int todayyear = today.get(Calendar.YEAR);
	private int todaymonth = (today.get(Calendar.MONTH)+1);
	
	private String toyear = Integer.toString(todayyear);
	private String tomonth = String.format("%02d", todaymonth);
	
	private int firstday;
	private int lastday;
	
	private String selyear;
	private String selmonth;
	
	private FileReader dataread;
	private FileWriter datawriter;
	
    private SUB[] subsub = new SUB[10000];
    private TODO[] todotodo = new TODO[10000];

    
	class SUB {
		private String Sub;
		private String Pro;
		private String Time;
		private String Year;
		private String Seme;
		
		SUB(String Sub, String Pro, String Time, String Year, String Seme) {
			this.Sub = Sub;
			this.Pro = Pro;
			this.Time = Time;
			this.Year = Year;
			this.Seme = Seme;
			}
	}
	
	class TODO {
		private String Todosubname;
		private String Todocontent;
		private String Tododeadline;
		private String Todofinish;
		private String Todoclear;
		private String Todoimportance;
		
		TODO(String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear, String Todoimportance) {
			this.Todosubname = Todosubname;
			this.Todocontent = Todocontent;
			this.Tododeadline = Tododeadline;
			this.Todofinish = Todofinish;
			this.Todoclear = Todoclear;
			this.Todoimportance = Todoimportance;
			}
	}
	
	public todolist(String path){
		super("TODOLIST");
		setSize(1100,600);
        setLayout(null);
        dir = path;
        int i = readsubcnt();
        int e = readtodocnt();
        subtable = new DefaultTableModel();
        subtable.setColumnIdentifiers(new String[] {"과목", "교수", "시간", "년도", "학기"});
        todotable = new DefaultTableModel();
        todotable.setColumnIdentifiers(new String[] {"과목명", "항목명(해야할 일)", "마감 기한", "완료 여부", "완료 날짜", "중요도"});
        
        sub = new JLabel("과목");
		sub.setOpaque(true);
		sub.setBackground(Color.WHITE);
		sub.setLocation(10,38);
		sub.setSize(150,30);
		sub.setFont(new Font("고딕체",Font.BOLD,20));
		sub.setHorizontalAlignment(sub.CENTER);
		add(sub);
		
		subsave = new JButton("저장");
		subsave.setLocation(180,40);
		subsave.setSize(60, 25);
		subsave.addActionListener(new subaddActionListener());
		add(subsave);
		
		submodify = new JButton("선택");
		submodify.setLocation(245,40);
		submodify.setSize(60, 25);
		submodify.addActionListener(new submodifyActionListener());
		add(submodify);
		
		subdelete = new JButton("삭제");
		subdelete.setLocation(310,40);
		subdelete.setSize(60, 25);
		subdelete.addActionListener(new subdeleteActionListener());
		add(subdelete);
		
		subname = new JLabel("과목명");
		subname.setOpaque(true);
		subname.setBackground(Color.GRAY);
		subname.setFont(new Font("고딕체",Font.BOLD,15));
		subname.setSize(120,30);
		subname.setLocation(10,70);
		subname.setHorizontalAlignment(subname.CENTER);
		add(subname);
		
		pro = new JLabel("담당교수");
		pro.setOpaque(true);
		pro.setBackground(Color.GRAY);
		pro.setFont(new Font("고딕체",Font.BOLD,15));
		pro.setSize(120,30);
		pro.setLocation(133,70);
		pro.setHorizontalAlignment(pro.CENTER);
		add(pro);
		
		time = new JLabel("강의 시간 및 요일");
		time.setOpaque(true);
		time.setBackground(Color.GRAY);
		time.setFont(new Font("고딕체",Font.BOLD,15));
		time.setSize(170,30);
		time.setLocation(256,70);
		time.setHorizontalAlignment(time.CENTER);
		add(time);
		
		year = new JLabel("수강년도");
		year.setOpaque(true);
		year.setBackground(Color.GRAY);
		year.setFont(new Font("고딕체",Font.BOLD,15));
		year.setSize(120,30);
		year.setLocation(429,70);
		year.setHorizontalAlignment(year.CENTER);
		add(year);
		
		seme = new JLabel("학기");
		seme.setOpaque(true);
		seme.setBackground(Color.GRAY);
		seme.setFont(new Font("고딕체",Font.BOLD,15));
		seme.setSize(120,30);
		seme.setLocation(552,70);
		seme.setHorizontalAlignment(seme.CENTER);
		add(seme);
        
		subnamein = new JTextField(20);
		subnamein.setSize(120,30);
		subnamein.setLocation(10,100);
		add(subnamein);
		
		proin = new JTextField(20);
		proin.setSize(120,30);
		proin.setLocation(133,100);
		add(proin);
		
		timein = new JTextField(20);
		timein.setSize(170,30);
		timein.setLocation(256,100);
		add(timein);
		
		yearin = new JTextField(20);
		yearin.setSize(120,30);
		yearin.setLocation(429,100);
		add(yearin);

		semein = new JTextField(20);
		semein.setSize(120,30);
		semein.setLocation(552,100);
		add(semein);
		
		sublist = new JTable(subtable);
		sublist.setSize(660,130);
		sublist.setLocation(10,135);
		sublist.addMouseListener(new SubMouseListener());
		add(sublist);
		
		subscroll = new JScrollPane(sublist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		subscroll.setBounds(10,135,660,130);
		add(subscroll);
		
		todo = new JLabel("항목");
		todo.setOpaque(true);
		todo.setBackground(Color.WHITE);
		todo.setLocation(10,288);
		todo.setSize(150,30);
		todo.setFont(new Font("고딕체",Font.BOLD,20));
		todo.setHorizontalAlignment(todosubname.CENTER);
		add(todo);
		
		todosave = new JButton("저장");
		todosave.setLocation(180,290);
		todosave.setSize(60, 25);
		todosave.addActionListener(new todoaddActionListener());
		add(todosave);
		
		todomodify = new JButton("선택");
		todomodify.setLocation(245,290);
		todomodify.setSize(60, 25);
		todomodify.addActionListener(new todomodifyActionListener());
		add(todomodify);
		
		tododelete = new JButton("삭제");
		tododelete.setLocation(310,290);
		tododelete.setSize(60, 25);
		tododelete.addActionListener(new tododeleteActionListener());
		add(tododelete);
		
		hide = new JButton("숨기기");
		hide.setLocation(375,290);
		hide.setSize(75, 25);
		add(hide);
		
		unhide = new JButton("보이기");
		unhide.setLocation(455,290);
		unhide.setSize(75, 25);
		add(unhide);
		
		todosubname = new JLabel("과목명");
		todosubname.setOpaque(true);
		todosubname.setBackground(Color.GRAY);
		todosubname.setFont(new Font("고딕체",Font.BOLD,15));
		todosubname.setSize(120,30);
		todosubname.setLocation(10,320);
		todosubname.setHorizontalAlignment(todosubname.CENTER);
		add(todosubname);
		
		todo = new JLabel("항목명(해야할 일)");
		todo.setOpaque(true);
		todo.setBackground(Color.GRAY);
		todo.setFont(new Font("고딕체",Font.BOLD,15));
		todo.setSize(210,30);
		todo.setLocation(133,320);
		todo.setHorizontalAlignment(todo.CENTER);
		add(todo);
		
		deadline = new JLabel("마감 기한");
		deadline.setOpaque(true);
		deadline.setBackground(Color.GRAY);
		deadline.setFont(new Font("고딕체",Font.BOLD,15));
		deadline.setSize(80,30);
		deadline.setLocation(346,320);
		deadline.setHorizontalAlignment(deadline.CENTER);
		add(deadline);
		
		finish = new JLabel("완료 여부");
		finish.setOpaque(true);
		finish.setBackground(Color.GRAY);
		finish.setFont(new Font("고딕체",Font.BOLD,15));
		finish.setSize(80,30);
		finish.setLocation(429,320);
		finish.setHorizontalAlignment(finish.CENTER);
		add(finish);
		
		clear = new JLabel("완료 날짜");
		clear.setOpaque(true);
		clear.setBackground(Color.GRAY);
		clear.setFont(new Font("고딕체",Font.BOLD,15));
		clear.setSize(80,30);
		clear.setLocation(512,320);
		clear.setHorizontalAlignment(clear.CENTER);
		add(clear);
		
		
		importance = new JLabel("중요도");
		importance.setOpaque(true);
		importance.setBackground(Color.GRAY);
		importance.setFont(new Font("고딕체",Font.BOLD,15));
		importance.setSize(77,30);
		importance.setLocation(595,320);
		importance.setHorizontalAlignment(importance.CENTER);
		add(importance);
		
		todosubnamein = new JTextField(20);
		todosubnamein.setSize(120,30);
		todosubnamein.setLocation(10,350);
		add(todosubnamein);
		
		contentin = new JTextField(20);
		contentin.setSize(210,30);
		contentin.setLocation(133,350);
		add(contentin);
		
		deadlinein = new JTextField(20);
		deadlinein.setSize(80,30);
		deadlinein.setLocation(346,350);
		add(deadlinein);
		
		finishin = new JTextField(20);
		finishin.setSize(80,30);
		finishin.setLocation(429,350);
		add(finishin);

		clearin = new JTextField(20);
		clearin.setSize(80,30);
		clearin.setLocation(512,350);
		add(clearin);
		
		importancein = new JTextField(20);
		importancein.setSize(77,30);
		importancein.setLocation(595,350);
		add(importancein);
		
		todolist = new JTable(todotable);
		todolist.setSize(660,200);
		todolist.setLocation(10,385);
		todolist.addMouseListener(new TodoMouseListener());
		add(todolist);
		
		todoscroll = new JScrollPane(todolist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		todoscroll.setBounds(10,385,660,200);
		add(todoscroll);
		
		first = new JLabel("1순위");
		first.setOpaque(true);
		first.setBackground(Color.GRAY);
		first.setFont(new Font("고딕체",Font.BOLD,15));
		first.setSize(105,27);
		first.setLocation(680,495);
		first.setHorizontalAlignment(first.CENTER);
		add(first);
		
		second = new JLabel("2순위");
		second.setOpaque(true);
		second.setBackground(Color.GRAY);
		second.setFont(new Font("고딕체",Font.BOLD,15));
		second.setSize(105,27);
		second.setLocation(788,495);
		second.setHorizontalAlignment(second.CENTER);
		add(second);
		
		third = new JLabel("3순위");
		third.setOpaque(true);
		third.setBackground(Color.GRAY);
		third.setFont(new Font("고딕체",Font.BOLD,15));
		third.setSize(105,27);
		third.setLocation(896,495);
		third.setHorizontalAlignment(third.CENTER);
		add(third);
		
		sort = new JButton("정렬");
		sort.setSize(70, 25);
		sort.setLocation(1004,525);
		sort.addActionListener(new sortActionListener());
		add(sort);
		
		search = new JButton("검색");
		search.setSize(70, 30);
		search.setLocation(1004,554);
		search.addActionListener(new searchActionListener());

		add(search);
		
		context = new JTextField(20);
		context.setSize(321,29);
		context.setLocation(680,555);
		add(context);
		
		fir = new JComboBox(rank);
		fir.setSize(105, 25);
		fir.setLocation(680,525);
		add(fir);
		
		sec = new JComboBox(rank);
		sec.setSize(105, 25);
		sec.setLocation(788,525);
		add(sec);
		
		thi = new JComboBox(rank);
		thi.setSize(105, 25);
		thi.setLocation(896,525);
		add(thi);
		
		comyear = new JComboBox(syear);
		comyear.setSelectedItem(toyear);
		comyear.setSize(100,30);
		comyear.setLocation(760,65);
		add(comyear);
		
		commonth = new JComboBox(smonth);
		commonth.setSelectedItem(tomonth);
		commonth.setSize(100, 30);
		commonth.setLocation(863,65);
		add(commonth);
		
		select = new JButton("선택");
		select.setSize(63,30);
		select.setLocation(1005,65);
		select.addActionListener(new calActionListener());
		add(select);
		
		selyear = Integer.toString(todayyear);
		selmonth = Integer.toString(todaymonth);
		today(todayyear,todaymonth);
		readsub(subsub);
		readtodo(todotodo);
		
		for(int daylabel = 0; daylabel<7; daylabel++) {
			day[daylabel] = new JLabel(sday[daylabel]);
			day[daylabel].setSize(50, 30);
			day[daylabel].setLocation(720+daylabel*53,95);
			if(daylabel==0)
				day[daylabel].setForeground(new Color(255,0,0));
			else if(daylabel == 6)
				day[daylabel].setForeground(new Color(0,0,255));
			add(day[daylabel]);
		}
		
		for(int j = 0; j<i ; j++){
			subsave(subtable, subsub[j].Sub, subsub[j].Pro, subsub[j].Time, subsub[j].Year, subsub[j].Seme);
		}
		for(int b = 0; b<e ; b++) {
			todosave(todotable, todotodo[b].Todosubname, todotodo[b].Todocontent, todotodo[b].Tododeadline, todotodo[b].Todofinish, todotodo[b].Todoclear, todotodo[b].Todoimportance);
		}
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				}
			}
		);
		
		
	}
	
	class calActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			
			if(source == select) {
				selyear=comyear.getSelectedItem().toString();
				selmonth=commonth.getSelectedItem().toString();
				todayyear = Integer.parseInt(selyear);
				todaymonth = Integer.parseInt(selmonth);	
			}
			
			for(int a= 0; a<42;a++){
					date[a].setVisible(false);
			}
			
			today(todayyear,todaymonth);
		}
	}
	
	public void readsub (SUB subsub[]){
		try{
			int i = 0;
			String ForDefaultToFile;
			dataread = new FileReader("C:\\todolist\\"+dir+"\\"+dir+".txt");
    		BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/5);
    		ForDefaultToFile = tokenizer.nextToken();
    		
    		for(int j = 0; j<i;j++){
    			String Sub = tokenizer.nextToken();
    			String Pro = tokenizer.nextToken();
    			String Time = tokenizer.nextToken();
    			String Year = tokenizer.nextToken();
    			String Seme = tokenizer.nextToken();
	    		subsub[j]=new SUB(Sub, Pro, Time, Year, Seme);
    		}	
    	}catch (Exception e7) {
    		System.out.println("readsub오류");
    	}
	}
	
	public int readsubcnt(){
		int i = 0;
		try{
    		dataread = new FileReader("C:\\todolist\\"+dir+"\\"+dir+".txt");
    		BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/5);
    		
    	}catch (Exception e8) {
    		System.out.println("readsubcnt오류");
    	}
		
		return i;
	}
	
	public void writesub (SUB sub[],int cnt) {
		try{
    		datawriter = new FileWriter("C:\\todolist\\"+dir+"\\"+dir+".txt",false);
    		BufferedWriter out = new BufferedWriter(datawriter);
    		out.write("ForDefaultToFile ");
    		if(cnt==0){
    			out.write("ForDefaultToFile ");
    		}
    		else{
    			for(int i = 0 ;i<cnt;i++)
    				out.write(" "+sub[i].Sub+" "+sub[i].Pro+" "+sub[i].Time+" "+sub[i].Year+" "+sub[i].Seme);
    		}
    		out.flush();
    		out.close();
    		datawriter.close();
    	}catch (Exception e) {
    		System.out.println(" writesub ls");
    	}		
	}
	
	public void subsave (DefaultTableModel table, String Sub, String Pro, String Time, String Year, String Seme){
		Object sublist[] = {Sub, Pro, Time, Year, Seme};
		table.addRow(sublist);
	}
	
	public void submodify(DefaultTableModel table, String sub){
		int i = 0;
		for(; i<table.getRowCount(); i++){
			    if(sub.equals(table.getValueAt(i,0)))
			     table.removeRow(i);
		}
	}
	
	public void subdelete(DefaultTableModel table, String sub){
		int k = table.getRowCount();
		int i = 0;
		int cnt = readsubcnt();
		for(; i<table.getRowCount(); i++){
			    if(sub.equals(table.getValueAt(i,0))){
			    	table.removeRow(i);
			    	break;
			    }
		}
		for(;i<cnt-1;i++)
			subsub[i]=subsub[i+1];
		writesub(subsub,k-1);
	}
	
	public void readtodo (TODO todotodo[]){
		try{
			int i = 0;
			String ForDefaultToFile;

    		dataread = new FileReader("C:\\todolist\\"+dir+"\\todo.txt");
    		BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/6);
    		ForDefaultToFile = tokenizer.nextToken();
    		
    		for(int j = 0; j<i;j++){
    			String Todosubname = tokenizer.nextToken();
    			String Todocontent = tokenizer.nextToken();
    			String Tododeadline = tokenizer.nextToken();
    			String Todofinish = tokenizer.nextToken();
    			String Todoclear = tokenizer.nextToken();
    			String Todoimportance = tokenizer.nextToken();
	    		todotodo[j]=new TODO(Todosubname, Todocontent, Tododeadline, Todofinish, Todoclear, Todoimportance);
    		}	
    	}catch (Exception e7) {
    		System.out.println("readsub오류");
    	}
	}
	
	public int readtodocnt(){
		int i = 0;
		try{
    		dataread = new FileReader("C:\\todolist\\"+dir+"\\todo.txt");
    		BufferedReader in = new BufferedReader(dataread);
    		String line = in.readLine();
    		StringTokenizer tokenizer = new StringTokenizer(line);
    		i = (tokenizer.countTokens()/6);
    		
    	}catch (Exception e8) {
    		System.out.println("readsubcnt오류");
    	}
		
		return i;
	}
	
	public void writetodo (TODO todotodo[],int cnt) {
		try{
    		datawriter = new FileWriter("C:\\todolist\\"+dir+"\\todo.txt",false);
    		BufferedWriter out = new BufferedWriter(datawriter);
    		if(cnt==0){
    			out.write("ForDefaultToFile ");
    		}
    		else{
	    		out.write("ForDefaultToFile ");
	    		for(int i = 0 ;i<cnt;i++)
	    			out.write(" "+todotodo[i].Todosubname+" "+todotodo[i].Todocontent+" "+todotodo[i].Tododeadline+" "+todotodo[i].Todofinish+" "+todotodo[i].Todoclear+" "+todotodo[i].Todoimportance+" ");
    		}
	    	out.flush();
    		out.close();
    		datawriter.close();
    	}catch (Exception e) {
    		System.out.println(" writesub ls");
    	}		
	}
	
	public void todosave(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear, String Todoimportance) {
		int k = table.getRowCount();
		Object listtodo[] = {Todosubname, Todocontent, Tododeadline, Todofinish, Todoclear, Todoimportance};
		table.addRow(listtodo);	
		for(int i = 0; i<k;i++){
			if("0".equals(table.getValueAt(i,5))){
				
			}
			else if("1".equals(table.getValueAt(i,5))){
				
			}
			else if("2".equals(table.getValueAt(i,5))){
				
			}
			else{
		
			}
		}
	}
	
	public void todomodify(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear, String Todoimportance){
		int i = 0;
		for(; i<table.getRowCount(); i++){
			    if(Todosubname.equals(table.getValueAt(i,0))||Todocontent.equals(table.getValueAt(i,1))||Tododeadline.equals(table.getValueAt(i,2))||Todofinish.equals(table.getValueAt(i,3))||Todoclear.equals(table.getValueAt(i,4))||Todoimportance.equals(table.getValueAt(i,5)))
			     table.removeRow(i);
		}
	}
	
	public void tododelete(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear, String Todoimportance){
		int cnt = readtodocnt();
		int k = table.getRowCount();
		int i = 0;
		for(; i<table.getRowCount(); i++){
			    if((Todosubname.equals(table.getValueAt(i,0)))&&(Todocontent.equals(table.getValueAt(i, 1)))&&(Tododeadline.equals(table.getValueAt(i, 2)))&&(Todofinish.equals(table.getValueAt(i, 3)))&&(Todoclear.equals(table.getValueAt(i, 4)))&&(Todoimportance.equals(table.getValueAt(i, 5)))){
			     table.removeRow(i);
			     break;
			    }
		}
		for(;i<cnt-1;i++)
			todotodo[i]=todotodo[i+1];
		writetodo(todotodo,k-1);
	}
	
	public void search (String keyword, DefaultTableModel table) {
		int cnt = readtodocnt();
		table.setNumRows(0);
		if(keyword.length()==0) {
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else  {
			for (int j =0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				String ver = todotodo[j].Todosubname;
				String ver1 = todotodo[j].Todocontent;
				String ver2 = todotodo[j].Tododeadline;
				String ver3 = todotodo[j].Todofinish;
				String ver4 = todotodo[j].Todoclear;
				String ver5 = todotodo[j].Todoimportance;

				if(ver.contains(keyword)||ver1.contains(keyword)||ver2.contains(keyword)||ver3.contains(keyword)||ver4.contains(keyword)||ver5.contains(keyword)){
					table.addRow(todolist);
				}
			}
		}
	}
	
	public void sort(String fir, String sec, String thi, DefaultTableModel table) {
		int cnt = readtodocnt();
		String ver1, ver2;
		int ver3, ver4;
		TODO temp[]=new TODO[1];
		if(fir.equals("과목명(오름차순)")){
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 0);
					ver2=(String) table.getValueAt(j, 0);
					if(ver1.compareTo(ver2)>0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else if(fir.equals("과목명(내림차순)")) {
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 0);
					ver2=(String) table.getValueAt(j, 0);
					if(ver1.compareTo(ver2)<0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else if(fir.equals("마감 기한")) {
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 2);
					ver2=(String) table.getValueAt(j, 2);
					if(ver1.compareTo(ver2)>0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else if(fir.equals("완료 여부")) {
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 3);
					ver2=(String) table.getValueAt(j, 3);
					if(ver1.compareTo(ver2)>0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else if(fir.equals("완료 날짜")) {
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 4);
					ver2=(String) table.getValueAt(j, 4);
					if(ver1.compareTo(ver2)>0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
		else if(fir.equals("중요도")) {
			for(int i = 0 ;i<cnt;i++){
				for(int j = i; j<cnt; j++){
					ver1=(String) table.getValueAt(i, 5);
					ver2=(String) table.getValueAt(j, 5);
					if(ver1.compareTo(ver2)>0){
						temp[0]=todotodo[i];
						todotodo[i]=todotodo[j];
						todotodo[j]=temp[0];
					}
				}
			}
			table.setNumRows(0);
			for(int j = 0; j<cnt; j++) {
				Object todolist[] = {todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline, todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance};
				table.addRow(todolist);
			}
		}
	}
	
	class subaddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int k = subtable.getRowCount();
			int q = 0;
			int inyear = 0;
			if(source == subsave) {		
				if(yearin.getText().matches(".*[0-9].*"))
					inyear = Integer.parseInt(yearin.getText());
				if(subnamein.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"과목명을 입력하십시오.");
				else if(proin.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"담당 교수를 입력하십시오.");
				else if(timein.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"강의 시간 및 요일을 입력하십시오.");
				/*else if(timein.getText()==null){
				}*/
				else if(yearin.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"수강 년도를 입력하십시오.");
				else if(inyear<2013||inyear>2020||!yearin.getText().matches(".*[0-9].*")){
        			JOptionPane.showMessageDialog(null,"수강 년도의 범위는 2013년부터 2020년까지입니다.");
				}
				else if(semein.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"학기를 입력하십시오");
				else if(!semein.getText().equals("1학기")&&!semein.getText().equals("2학기")) {
        			JOptionPane.showMessageDialog(null,"학기의 범위는 1학기, 2학기입니다.");
				}
				else {
					for(int j =0 ; j<subtable.getRowCount(); j++){
					    if(subnamein.getText().equals(subtable.getValueAt(j,0)))
							q=1;
					}
					if(q==1)
	        			JOptionPane.showMessageDialog(null,"이미 등록된 과목명입니다.");
					else{
						subsave(subtable, subnamein.getText(),proin.getText(), timein.getText(), yearin.getText(), semein.getText());
						subsub[k]= new SUB(subnamein.getText(),proin.getText(), timein.getText(), yearin.getText(), semein.getText());
						writesub(subsub,k+1);
					}
				}
			}
		}
	}
	
	class SubMouseListener implements MouseListener{   
	    public void mouseClicked(java.awt.event.MouseEvent e) {
	        JTable jtable = (JTable)e.getSource();
	        row = jtable.getSelectedRow();             
	    }
	    public void mouseEntered(java.awt.event.MouseEvent e) {
	    }
	    public void mouseExited(java.awt.event.MouseEvent e) {    
	    }
	    public void mousePressed(java.awt.event.MouseEvent e) {
	    }
	    public void mouseReleased(java.awt.event.MouseEvent e) {
	    }
	}
	
	class submodifyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int i = readsubcnt();
			int j = 0;
			if(source == submodify) {		
				 subnamein.setText((String) subtable.getValueAt(row, 0));
			     proin.setText((String) subtable.getValueAt(row, 1));
			     timein.setText((String) subtable.getValueAt(row, 2));
			     yearin.setText((String) subtable.getValueAt(row, 3));
			     semein.setText((String) subtable.getValueAt(row, 4));
			     submodify(subtable, (String) subtable.getValueAt(row, 0));
			}
		}
	}
	
	class subdeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int cnt1 = subtable.getRowCount();
			int cnt2 = todotable.getRowCount();
			int q = 0;
			if(source == subdelete) {
				for(int i = 0 ; i<cnt1 ; i++) {
					for(int j =0; j <cnt2;j++) {
						if(subtable.getValueAt(i, 0).equals(todotable.getValueAt(j,0)))
							q=1;
					}
				}
				if(q==1){
        			JOptionPane.showMessageDialog(null,"항목이 있는 과목입니다.");
				}
				else
					subdelete(subtable, (String) subtable.getValueAt(row, 0));
			}
		}
	}
	
	class todoaddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int k = todotable.getRowCount();
			int m = readsubcnt();
			int q = 0;
			String pattern = "(20)(1[3-9]|20)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])";
			Pattern p = Pattern.compile(pattern);
			Matcher ma = p.matcher(deadlinein.getText());
			Matcher mb = p.matcher(clearin.getText());
			String deadin = deadlinein.getText();
			String clein = clearin.getText();
			String inyear, inmonth, inday;
			GregorianCalendar calver = new GregorianCalendar();
			if(source == todosave) {
				if(todosubnamein.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"과목명을 입력하십시오.");
				else if(contentin.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"항목명(해야할 일)을 입력하십시오.");
				else if(deadlinein.getText().length()==0)
        			JOptionPane.showMessageDialog(null,"마감 기한을 입력하십시오.");
				else if(!ma.matches()){
        			JOptionPane.showMessageDialog(null,"마감 기한 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
				}
				else if(ma.matches()){
					inyear = deadin.substring(0,4);
					inmonth = deadin.substring(4,6);
					inday = deadin.substring(6,8);
					int year = Integer.parseInt(inyear);
					int month = Integer.parseInt(inmonth);
					int day = Integer.parseInt(inday);
					calver.set(year,month-1,1);
					int lastdayver = calver.getActualMaximum(calver.DAY_OF_MONTH);
					if(Integer.parseInt(inday)>lastdayver){
	        			JOptionPane.showMessageDialog(null,"마감 기한 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
					}
					else if(finishin.getText().length()==0)
	        			JOptionPane.showMessageDialog(null,"완료 여부를 입력하십시오.");
					else if(!finishin.getText().matches("[o|x]*"))
	        			JOptionPane.showMessageDialog(null,"완료 여부는 o,x만 가능합니다.");
					else if(clearin.getText().length()==0)
	        			JOptionPane.showMessageDialog(null,"완료 날짜를 입력하십시오.");
					else if(!mb.matches()){
	        			JOptionPane.showMessageDialog(null,"마감 기한 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
					}
					else if(mb.matches()){
						inyear = clein.substring(0,4);
						inmonth = clein.substring(4,6);
						inday = clein.substring(6,8);
						year = Integer.parseInt(inyear);
						month = Integer.parseInt(inmonth);
						day = Integer.parseInt(inday);
						calver.set(year,month-1,1);
						lastdayver = calver.getActualMaximum(calver.DAY_OF_MONTH);
						if(Integer.parseInt(inday)>lastdayver){
		        			JOptionPane.showMessageDialog(null,"완료 날짜 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
						}
						else if(importancein.getText().length()==0)
		        			JOptionPane.showMessageDialog(null,"중요도를 입력하십시오.");
						else if(!importancein.getText().matches("[0-3]*"))
		        			JOptionPane.showMessageDialog(null,"중요도는 0~3까지 입력할 수 있습니다.\n1 = 가장 중요(붉은색) \n2 = 중간 중요(노란색)\n3 = 약간 중요(초록색)");
						else {
							for(int j =0; j<m;j++) {
								if(todosubnamein.getText().equals(subsub[j].Sub)) {
									q+=1;
									break;
								}
								else
									continue;
							}
							if(q==1){
								todosave(todotable, todosubnamein.getText(),contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
								todotodo[k]= new TODO(todosubnamein.getText(),contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
								writetodo(todotodo,k+1);
							}
							else
			        			JOptionPane.showMessageDialog(null,"등록된 과목이 없습니다.");
						}
					}
				}
			}
		}
	}
	
	class TodoMouseListener implements MouseListener{   
	    public void mouseClicked(java.awt.event.MouseEvent e) {
	        JTable jtable = (JTable)e.getSource();
	        rowtodo = jtable.getSelectedRow();             
	    }
	    public void mouseEntered(java.awt.event.MouseEvent e) {
	    }
	    public void mouseExited(java.awt.event.MouseEvent e) {    
	    }
	    public void mousePressed(java.awt.event.MouseEvent e) {
	    }
	    public void mouseReleased(java.awt.event.MouseEvent e) {
	    }
	}
	
	class todomodifyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int i = readtodocnt();
			int j = 0;
			if(source == todomodify) {		
				 todosubnamein.setText((String) todotable.getValueAt(rowtodo, 0));
			     contentin.setText((String) todotable.getValueAt(rowtodo, 1));
			     deadlinein.setText((String) todotable.getValueAt(rowtodo, 2));
			     finishin.setText((String) todotable.getValueAt(rowtodo, 3));
			     clearin.setText((String) todotable.getValueAt(rowtodo, 4));
			     importancein.setText((String) todotable.getValueAt(rowtodo, 5));
			     todomodify(todotable, (String) todotable.getValueAt(rowtodo, 0), (String) todotable.getValueAt(rowtodo, 1), (String) todotable.getValueAt(rowtodo, 2), (String) todotable.getValueAt(rowtodo, 3), (String) todotable.getValueAt(rowtodo, 4), (String) todotable.getValueAt(rowtodo, 5));
			}
		}
	}
	
	class tododeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			if(source == tododelete) {	
			     tododelete(todotable, (String) todotable.getValueAt(rowtodo, 0), (String) todotable.getValueAt(rowtodo, 1), (String) todotable.getValueAt(rowtodo, 2), (String) todotable.getValueAt(rowtodo, 3), (String) todotable.getValueAt(rowtodo, 4), (String) todotable.getValueAt(rowtodo, 5));
			}
		}
	}
	
	class searchActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			if(source == search) {
			    search(context.getText(),todotable);
			}
		}
	}
	
	class calselectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			int j = 0;
			int click = 0;
			for(int i =0;i<42;i++){
				date[i].setBackground(new Color(255,255,255));
				if(i%7==0)
					date[i].setForeground(new Color(255,0,0));
				else  if(i%7==6) 
					date[i].setForeground(new Color(0,0,255));
				else
					date[i].setForeground(new Color(0,0,0));
				if(source==date[i]){
					click = i;
					if(date[i].getText().length()==0)
	        			JOptionPane.showMessageDialog(null,"날짜를 클릭하십시오.");
					else{
						j=Integer.parseInt(date[i].getText());
						date[click].setBackground(new Color(150,150,150));
						date[click].setForeground(new Color(234,204,026));
						String clickmonth = String.format("%02d", todaymonth);
						String clickday = String.format("%02d", Integer.parseInt(date[click].getText()));
						String datever = selyear+clickmonth+clickday;
						search(datever, todotable);
					}
				}
			}	
		}
	}
	
	class sortActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			if(source == sort) {	
			    sort(fir.getSelectedItem().toString(), sec.getSelectedItem().toString(), thi.getSelectedItem().toString(),todotable);
			}
		}
	}
	
	public void today(int year, int month) {
		GregorianCalendar cal;
		cal = new GregorianCalendar();
		
		int todayyear = cal.get(cal.YEAR);
		int todaymonth = (cal.get(cal.MONTH)+1);
		int today = cal.get(cal.DATE);
		
		cal.set(year, month-1, 1);
		firstday = cal.get(cal.DAY_OF_WEEK);
		lastday = cal.getActualMaximum(cal.DAY_OF_MONTH);
		
		int j = 700;
		int l = 125;
		int i = 0;

		String sdate;
		
		for(; i < firstday-1 ; i++) {
			sdate ="";
			date[i] = new JButton(sdate);
			date[i].setSize(50,30);
			date[i].setLocation(j,l);
			date[i].setBackground(new Color(255,255,255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j+=53;
			
			if(i%7==6) {
				l+=33;
				j=700; 
			}
		}
		
		for(int a = 1; a<lastday+1; i++,a++) {
			sdate =""+a;
			date[i] = new JButton(sdate);
			date[i].setSize(50,30);
			date[i].setLocation(j,l);
			date[i].setBackground(new Color(255,255,255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j+=53;
			
			if(i%7==0)
				date[i].setForeground(new Color(255,0,0));

			else  if(i%7==6) {
				date[i].setForeground(new Color(0,0,255));
				l+=33;
				j=700;
			}
			
			if((year==todayyear)&&(month==todaymonth)&&(today==a)){
				date[i].setForeground(new Color(0,150,0));
			}
		}

		
		while(i<42) {
			sdate ="";
			date[i] = new JButton(sdate);
			date[i].setSize(50,30);
			date[i].setLocation(j,l);
			date[i].setBackground(new Color(255,255,255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j+=53;
			if(i%7==6) {
				l+=33;
				j=700;
				
			}
			i++;
			
		}
	}
}

