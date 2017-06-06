package se.smu;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class log extends Frame {
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

		public String getId() {
			return id;
		}

		public String getPass() {
			return pass;
		}
	}

	public void initdatadir() {
		datadir = new File("C:\\todolist");
		if (!datadir.exists()) {
			datadir.mkdirs();
		}
		datadir = new File("C:\\todolist\\ForDefaultToFile");
		if (!datadir.exists()) {
			datadir.mkdirs();
		}
	}

	public void initdatafile() {
		data = new File("C:\\todolist\\ForDefaultToFile\\ForDefaultToFile.txt");
		data = new File("C:\\todolist\\savetext.txt");
		if (!data.exists()) {
			try {
				datatext = new FileWriter("C:\\todolist\\savetext.txt", false);
				datatext.write("ForDefaultToFile ForDefaultToFile ");
				datatext.flush();
				datatext.close();

				datatext = new FileWriter("C:\\todolist\\ForDefaultToFile\\ForDefaultToFile.txt", false);
				datatext.write("ForDefaultToFile ");
				datatext.flush();
				datatext.close();

			} catch (Exception e) {
				System.out.println("오류initdatafile");
			}
		}
	}

	public void readdata(idpass idandpass[]) {
		try {
			int i = 0;
			dataread = new FileReader("C:\\todolist\\savetext.txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 2);

			for (int j = 0; j < i; j++) {
				String id = tokenizer.nextToken();
				String pass = tokenizer.nextToken();
				idandpass[j] = new idpass(id, pass);

			}

		} catch (Exception e) {
			System.out.println("오류readdata");
		}
	}

	public int readdatacnt() {
		int i = 0;
		try {
			dataread = new FileReader("C:\\todolist\\savetext.txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 2);

		} catch (Exception e) {
			System.out.println("오류readdatacn");
		}
		return i;
	}

	public log() {
		super("login");
		setSize(400, 200);
		setLayout(null);

		Todolist = new JLabel("To do list");
		Todolist.setLocation(20, 35);
		Todolist.setSize(360, 30);
		Todolist.setFont(new Font("고딕체", Font.BOLD, 20));
		Todolist.setBorder(new LineBorder(Color.gray, 3));
		Todolist.setHorizontalAlignment(Todolist.CENTER);
		add(Todolist);

		ID = new JLabel("ID");
		ID.setLocation(100, 75);
		ID.setSize(30, 20);
		ID.setFont(new Font("고딕체", Font.BOLD, 15));
		ID.setHorizontalAlignment(ID.RIGHT);
		add(ID);

		PASSWD = new JLabel("PASSWD");
		PASSWD.setLocation(75, 105);
		PASSWD.setSize(80, 20);
		PASSWD.setFont(new Font("고딕체", Font.BOLD, 15));
		PASSWD.setHorizontalAlignment(PASSWD.RIGHT);
		add(PASSWD);

		IDin = new JTextField(10);
		IDin.setLocation(163, 73);
		IDin.setSize(120, 25);
		IDin.setBorder(new LineBorder(Color.BLACK, 2));
		add(IDin);

		PASSWDin = new JPasswordField(10);
		PASSWDin.setLocation(163, 100);
		PASSWDin.setSize(120, 25);
		PASSWDin.setBorder(new LineBorder(Color.BLACK, 2));
		PASSWDin.setEchoChar('*');
		add(PASSWDin);

		login = new JButton("로그인");
		login.setLocation(90, 130);
		login.setSize(95, 30);
		login.setFont(new Font("고딕체", Font.BOLD, 12));
		login.setBackground(Color.gray);
		login.setBorder(new BevelBorder(BevelBorder.RAISED));
		login.addActionListener(new logActionListener());
		add(login);

		toregister = new JButton("계정 등록");
		toregister.setLocation(188, 130);
		toregister.setSize(95, 30);
		toregister.setFont(new Font("고딕체", Font.BOLD, 12));
		toregister.setBackground(Color.gray);
		toregister.setBorder(new BevelBorder(BevelBorder.RAISED));
		toregister.addActionListener(new regActionListener());
		add(toregister);

		rf = new register();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		initdatadir();
		initdatafile();
	}

	class logActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object input = e.getSource();
			int i = 0, id = 0, pass = 0;
			i = readdatacnt();
			idpass idandpass[] = new idpass[i];
			readdata(idandpass);
			if (input == login) {
				if (IDin.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하십시오.");
				} else if (!IDin.getText().matches("[0-9|a-z|A-Z]*")) {
					JOptionPane.showMessageDialog(null, "아이디는 영문이나 숫자 10글자만 가능합니다.");

				} else if (new String(PASSWDin.getPassword()).length() == 0) {
					JOptionPane.showMessageDialog(null, "패스워드를 입력하십시오.");

				} else if (!new String(PASSWDin.getPassword()).matches("[0-9|a-z|A-Z]*")) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문이나 숫자 10글자만 가능합니다.");

				} else {
					for (int j = 0; j < i; j++) {
						if ((IDin.getText().equals(idandpass[j].id))
								&& new String(PASSWDin.getPassword()).equals(idandpass[j].pass)) {
							datadir = new File("C:\\todolist\\" + IDin.getText());
							if (!datadir.exists())
								datadir.mkdirs();

							data = new File("C:\\todolist\\" + IDin.getText() + "\\" + IDin.getText() + ".txt");

							if (!data.exists()) {
								try {
									datatext = new FileWriter(
											"C:\\todolist\\" + IDin.getText() + "\\" + IDin.getText() + ".txt", false);
									datatext.write("ForDefaultToFile ");
									datatext.flush();
									datatext.close();

									datatext = new FileWriter("C:\\todolist\\" + IDin.getText() + "\\todo.txt", false);
									datatext.write("ForDefaultToFile ");
									datatext.flush();
									datatext.close();

								} catch (Exception e1) {
									System.out.println("오류");
								}
							}

							tdl = new todolist(IDin.getText());
							tdl.setSize(1100, 635);
							tdl.setVisible(true);
							break;
						}
						if (!(IDin.getText().equals(idandpass[j].id))) {
							id += 1;
							if (id == i) {
								JOptionPane.showMessageDialog(null, "등록되지 않은 아이디입니다.");
								break;
							}
						} else if ((IDin.getText().equals(idandpass[j].id))
								&& !(new String(PASSWDin.getPassword()).equals(idandpass[j].pass))) {
							JOptionPane.showMessageDialog(null, "패스워드가 틀렸습니다.");
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
			if (input == toregister)
				;
			rf.setSize(400, 200);
			rf.setVisible(true);
		}
	}

	public static void main(String[] args) {
		log d = new log();
		d.setSize(400, 200);
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
		try {
			int i = 0;
			dataread = new FileReader("C:\\todolist\\savetext.txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 2);

			for (int j = 0; j < i; j++) {
				String idv = tokenizer.nextToken();
				String passv = tokenizer.nextToken();
				if (idv.equals(id)) {
					q = 1;
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("readdata오류");
		}
		return q;
	}

	public void writedata(String id, String pass) {
		try {
			datawrite = new FileWriter("C:\\todolist\\savetext.txt", true);
			BufferedWriter out = new BufferedWriter(datawrite);
			out.write(" " + id + " " + pass);
			out.flush();
			out.close();
			datawrite.close();
		} catch (Exception e) {
			System.out.println("writedatals");
		}
	}

	public register() {
		super("Register");
		setSize(400, 200);
		setLayout(null);
		
		Register = new JLabel("계정 등록");
		Register.setLocation(20, 35);
		Register.setSize(360, 30);
		Register.setFont(new Font("고딕체", Font.BOLD, 20));
		Register.setBorder(new LineBorder(Color.gray, 3));
		Register.setHorizontalAlignment(Register.CENTER);
		add(Register);

		id = new JLabel("ID");
		id.setLocation(100, 75);
		id.setSize(30, 20);
		id.setFont(new Font("고딕체", Font.BOLD, 15));
		id.setHorizontalAlignment(id.RIGHT);
		add(id);

		passwd = new JLabel("PASSWD");
		passwd.setLocation(75, 105);
		passwd.setSize(80, 20);
		passwd.setFont(new Font("고딕체", Font.BOLD, 15));
		passwd.setHorizontalAlignment(passwd.RIGHT);
		add(passwd);

		IDmade = new JTextField(10);
		IDmade.setLocation(163, 73);
		IDmade.setSize(120, 25);
		IDmade.setBorder(new LineBorder(Color.BLACK, 2));
		add(IDmade);

		passwdmade = new JPasswordField(10);
		passwdmade.setLocation(163, 103);
		passwdmade.setSize(120, 25);
		passwdmade.setBorder(new LineBorder(Color.BLACK, 2));
		passwdmade.setEchoChar('*');
		add(passwdmade);

		register = new JButton("계정 등록");
		register.setLocation(83, 130);
		register.setSize(200, 30);
		register.setFont(new Font("고딕체", Font.BOLD, 10));
		register.setBackground(Color.gray);
		register.setBorder(new BevelBorder(BevelBorder.RAISED));
		register.addActionListener(new madeActionListener());
		add(register);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

	}

	class madeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object input2 = e.getSource();
			String id = IDmade.getText();
			String passwd = new String(passwdmade.getPassword());
			int quit = readdata(id, passwd);
			if (input2 == register) {
				if (id.length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하십시오.");
				} else if (!id.matches("[0-9|a-z|A-Z]*")) {
					JOptionPane.showMessageDialog(null, "아이디는 영문 또는 숫자입니다.");
				} else if (id.length() > 10) {
					JOptionPane.showMessageDialog(null, "아이디는 10자 이하입니다.");
				} else if (quit == 1) {
					JOptionPane.showMessageDialog(null, "이미 있는 아이디입니다.");
				} else if (passwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오.");
				} else if (!passwd.matches("[0-9|a-z|A-Z]*")) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문 또는 숫자입니다.");
				} else if (passwd.length() > 10) {
					JOptionPane.showMessageDialog(null, "비밀번호는 10자 이하입니다.");
				} else {
					writedata(id, passwd);
					JOptionPane.showMessageDialog(null, "등록되었습니다.");
					dispose();
				}
			}
		}
	}
}

class todolist extends Frame {
	private JLabel sub, subname, pro, time, year, seme, todo, todosubname, content, deadline, finish, clear, importance,
			first, second, third, cleartodo;
	private JButton subsave, submodify, subdelete, todosave, todomodify, tododelete, search, sort, select, hide, unhide;
	private JTextField subnamein, proin, timein, yearin, semein, todosubnamein, contentin, deadlinein, finishin,
			clearin, importancein, context;
	private JTable sublist, todolist;
	private JScrollPane subscroll, todoscroll;
	private JComboBox fir, sec, thi, comyear, commonth;
	private DefaultTableModel subtable;
	private DefaultTableModel todotable;

	private String[] rank = { "과목명(오름차순)", "과목명(내림차순)", "마감 기한", "완료 여부", "완료 날짜", "중요도", "-" };
	private String[] todolistname = { "과옴명", "항목명 (해야할 일)", "마감 기한", "완료 여부", "완료 날짜", "중요도" };
	private String[] syear = { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" };
	private String[] smonth = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	private String[] sday = { "일", "월", "화", "수", "목", "금", "토" };
	private int hiding=0;
	
	private String dir;
	private int row=10000;
	private int rowtodo=10000;
	private Calendar today = Calendar.getInstance();

	private JLabel day[] = new JLabel[7];
	private JButton date[] = new JButton[42];;

	private int todayyear = today.get(Calendar.YEAR);
	private int todaymonth = (today.get(Calendar.MONTH) + 1);

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

		TODO(String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear,
				String Todoimportance) {
			this.Todosubname = Todosubname;
			this.Todocontent = Todocontent;
			this.Tododeadline = Tododeadline;
			this.Todofinish = Todofinish;
			this.Todoclear = Todoclear;
			this.Todoimportance = Todoimportance;
		}
	}

	public todolist(String path) {
		super("TODOLIST");
		setSize(1100, 635);
		setLayout(null);
		dir = path;
		int i = readsubcnt();
		int e = readtodocnt();
		subtable = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		subtable.setColumnIdentifiers(new String[] { "과목", "교수", "시간", "년도", "학기" });
		todotable = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		todotable.setColumnIdentifiers(new String[] { "과목명", "항목명(해야할 일)", "마감 기한", "완료 여부", "완료 날짜", "중요도" });

		sub = new JLabel("과목");
		sub.setOpaque(true);
		sub.setBackground(Color.WHITE);
		sub.setLocation(10, 38);
		sub.setSize(150, 30);
		sub.setFont(new Font("고딕체", Font.BOLD, 20));
		sub.setHorizontalAlignment(sub.CENTER);
		sub.setBorder(new LineBorder(Color.GRAY, 3));
		add(sub);

		subsave = new JButton("저장");
		subsave.setLocation(180, 40);
		subsave.setSize(60, 25);
		subsave.setBackground(Color.gray);
		subsave.setBorder(new BevelBorder(BevelBorder.RAISED));
		subsave.addActionListener(new subaddActionListener());
		add(subsave);

		submodify = new JButton("선택");
		submodify.setLocation(245, 40);
		submodify.setSize(60, 25);
		submodify.setBackground(Color.gray);
		submodify.setBorder(new BevelBorder(BevelBorder.RAISED));
		submodify.addActionListener(new submodifyActionListener());
		add(submodify);

		subdelete = new JButton("삭제");
		subdelete.setLocation(310, 40);
		subdelete.setSize(60, 25);
		subdelete.setBackground(Color.gray);
		subdelete.setBorder(new BevelBorder(BevelBorder.RAISED));
		subdelete.addActionListener(new subdeleteActionListener());
		add(subdelete);

		subname = new JLabel("과목명");
		subname.setOpaque(true);
		subname.setBackground(new Color(192,192,192));
		subname.setFont(new Font("고딕체", Font.BOLD, 15));
		subname.setSize(120, 30);
		subname.setLocation(10, 70);
		subname.setHorizontalAlignment(subname.CENTER);
		add(subname);

		pro = new JLabel("담당교수");
		pro.setOpaque(true);
		pro.setBackground(new Color(192,192,192));
		pro.setFont(new Font("고딕체", Font.BOLD, 15));
		pro.setSize(120, 30);
		pro.setLocation(133, 70);
		pro.setHorizontalAlignment(pro.CENTER);
		add(pro);

		time = new JLabel("강의 시간 및 요일");
		time.setOpaque(true);
		time.setBackground(new Color(192,192,192));
		time.setFont(new Font("고딕체", Font.BOLD, 15));
		time.setSize(170, 30);
		time.setLocation(256, 70);
		time.setHorizontalAlignment(time.CENTER);
		add(time);

		year = new JLabel("수강년도");
		year.setOpaque(true);
		year.setBackground(new Color(192,192,192));
		year.setFont(new Font("고딕체", Font.BOLD, 15));
		year.setSize(120, 30);
		year.setLocation(429, 70);
		year.setHorizontalAlignment(year.CENTER);
		add(year);

		seme = new JLabel("학기");
		seme.setOpaque(true);
		seme.setBackground(new Color(192,192,192));
		seme.setFont(new Font("고딕체", Font.BOLD, 15));
		seme.setSize(120, 30);
		seme.setLocation(552, 70);
		seme.setHorizontalAlignment(seme.CENTER);
		add(seme);

		subnamein = new JTextField(20);
		subnamein.setSize(120, 30);
		subnamein.setLocation(10, 100);
		add(subnamein);

		proin = new JTextField(20);
		proin.setSize(120, 30);
		proin.setLocation(133, 100);
		add(proin);

		timein = new JTextField(20);
		timein.setSize(170, 30);
		timein.setLocation(256, 100);
		add(timein);

		yearin = new JTextField(20);
		yearin.setSize(120, 30);
		yearin.setLocation(429, 100);
		add(yearin);

		semein = new JTextField(20);
		semein.setSize(120, 30);
		semein.setLocation(552, 100);
		add(semein);

		sublist = new JTable(subtable);
		sublist.setSize(660, 140);
		sublist.setLocation(10, 135);
		sublist.addMouseListener(new SubMouseListener());
		add(sublist);

		subscroll = new JScrollPane(sublist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		subscroll.setBounds(10, 135, 660, 140);
		add(subscroll);

		todo = new JLabel("항목");
		todo.setOpaque(true);
		todo.setBackground(Color.WHITE);
		todo.setLocation(10, 288);
		todo.setSize(150, 30);
		todo.setFont(new Font("고딕체", Font.BOLD, 20));
		todo.setBorder(new LineBorder(Color.GRAY, 3));
		todo.setHorizontalAlignment(todosubname.CENTER);
		add(todo);

		todosave = new JButton("저장");
		todosave.setLocation(180, 290);
		todosave.setSize(60, 25);
		todosave.setBackground(Color.GRAY);
		todosave.setBorder(new BevelBorder(BevelBorder.RAISED));
		todosave.addActionListener(new todoaddActionListener());
		add(todosave);

		todomodify = new JButton("선택");
		todomodify.setLocation(245, 290);
		todomodify.setSize(60, 25);
		todomodify.setBackground(Color.GRAY);
		todomodify.setBorder(new BevelBorder(BevelBorder.RAISED));
		todomodify.addActionListener(new todomodifyActionListener());
		add(todomodify);

		tododelete = new JButton("삭제");
		tododelete.setLocation(310, 290);
		tododelete.setSize(60, 25);
		tododelete.setBackground(Color.GRAY);
		tododelete.setBorder(new BevelBorder(BevelBorder.RAISED));
		tododelete.addActionListener(new tododeleteActionListener());
		add(tododelete);

		cleartodo = new JLabel("완료된 항목을");	
		cleartodo.setOpaque(true);
		cleartodo.setFont(new Font("고딕체", Font.BOLD, 15));
		cleartodo.setBackground(Color.WHITE);
		cleartodo.setSize(170, 30);
		cleartodo.setLocation(10, 590);
		cleartodo.setBorder(new LineBorder(Color.GRAY, 3));
		cleartodo.setHorizontalAlignment(cleartodo.CENTER);
		add(cleartodo);
		
		hide = new JButton("숨기기");
		hide.setLocation(183, 590);
		hide.setSize(75, 30);
		hide.setBackground(Color.GRAY);
		hide.setBorder(new BevelBorder(BevelBorder.RAISED));
		hide.addActionListener(new hideActionListener());
		add(hide);

		unhide = new JButton("보이기");
		unhide.setLocation(261, 590);
		unhide.setSize(75, 30);
		unhide.setBackground(Color.GRAY);
		unhide.setBorder(new BevelBorder(BevelBorder.RAISED));
		unhide.addActionListener(new unhideActionListener());
		add(unhide);

		todosubname = new JLabel("과목명");
		todosubname.setOpaque(true);
		todosubname.setBackground(new Color(192,192,192));
		todosubname.setFont(new Font("고딕체", Font.BOLD, 15));
		todosubname.setSize(120, 30);
		todosubname.setLocation(10, 320);
		todosubname.setHorizontalAlignment(todosubname.CENTER);
		add(todosubname);

		todo = new JLabel("항목명(해야할 일)");
		todo.setOpaque(true);
		todo.setBackground(new Color(192,192,192));
		todo.setFont(new Font("고딕체", Font.BOLD, 15));
		todo.setSize(210, 30);
		todo.setLocation(133, 320);
		todo.setHorizontalAlignment(todo.CENTER);
		add(todo);

		deadline = new JLabel("마감 기한");
		deadline.setOpaque(true);
		deadline.setBackground(new Color(192,192,192));
		deadline.setFont(new Font("고딕체", Font.BOLD, 15));
		deadline.setSize(80, 30);
		deadline.setLocation(346, 320);
		deadline.setHorizontalAlignment(deadline.CENTER);
		add(deadline);

		finish = new JLabel("완료 여부");
		finish.setOpaque(true);
		finish.setBackground(new Color(192,192,192));
		finish.setFont(new Font("고딕체", Font.BOLD, 15));
		finish.setSize(80, 30);
		finish.setLocation(429, 320);
		finish.setHorizontalAlignment(finish.CENTER);
		add(finish);

		clear = new JLabel("완료 날짜");
		clear.setOpaque(true);
		clear.setBackground(new Color(192,192,192));
		clear.setFont(new Font("고딕체", Font.BOLD, 15));
		clear.setSize(80, 30);
		clear.setLocation(512, 320);
		clear.setHorizontalAlignment(clear.CENTER);
		add(clear);

		importance = new JLabel("중요도");
		importance.setOpaque(true);
		importance.setBackground(new Color(192,192,192));
		importance.setFont(new Font("고딕체", Font.BOLD, 15));
		importance.setSize(77, 30);
		importance.setLocation(595, 320);
		importance.setHorizontalAlignment(importance.CENTER);
		add(importance);

		todosubnamein = new JTextField(20);
		todosubnamein.setSize(120, 30);
		todosubnamein.setLocation(10, 350);
		add(todosubnamein);

		contentin = new JTextField(20);
		contentin.setSize(210, 30);
		contentin.setLocation(133, 350);
		add(contentin);

		deadlinein = new JTextField(20);
		deadlinein.setSize(80, 30);
		deadlinein.setLocation(346, 350);
		add(deadlinein);

		finishin = new JTextField(20);
		finishin.setSize(80, 30);
		finishin.setLocation(429, 350);
		add(finishin);

		clearin = new JTextField(20);
		clearin.setSize(80, 30);
		clearin.setLocation(512, 350);
		add(clearin);

		importancein = new JTextField(20);
		importancein.setSize(77, 30);
		importancein.setLocation(595, 350);
		add(importancein);

		todolist = new JTable(todotable);
		todolist.setSize(660, 200);
		todolist.setLocation(10, 385);
		todolist.addMouseListener(new TodoMouseListener());
		add(todolist);

		todoscroll = new JScrollPane(todolist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		todoscroll.setBounds(10, 385, 660, 200);
		add(todoscroll);

		first = new JLabel("1순위");
		first.setOpaque(true);
		first.setBackground(new Color(192,192,192));
		first.setFont(new Font("고딕체", Font.BOLD, 15));
		first.setSize(105, 27);
		first.setLocation(680, 495);
		first.setHorizontalAlignment(first.CENTER);
		add(first);

		second = new JLabel("2순위");
		second.setOpaque(true);
		second.setBackground(new Color(192,192,192));
		second.setFont(new Font("고딕체", Font.BOLD, 15));
		second.setSize(105, 27);
		second.setLocation(788, 495);
		second.setHorizontalAlignment(second.CENTER);
		add(second);

		third = new JLabel("3순위");
		third.setOpaque(true);
		third.setBackground(new Color(192,192,192));
		third.setFont(new Font("고딕체", Font.BOLD, 15));
		third.setSize(105, 27);
		third.setLocation(896, 495);
		third.setHorizontalAlignment(third.CENTER);
		add(third);

		sort = new JButton("정렬");
		sort.setSize(70, 25);
		sort.setLocation(1004, 525);
		sort.setBackground(Color.GRAY);
		sort.setBorder(new BevelBorder(BevelBorder.RAISED));
		sort.addActionListener(new sortActionListener());
		add(sort);

		search = new JButton("검색");
		search.setSize(70, 30);
		search.setLocation(1004, 554);
		search.setBackground(Color.GRAY);
		search.setBorder(new BevelBorder(BevelBorder.RAISED));
		search.addActionListener(new searchActionListener());

		add(search);

		context = new JTextField(20);
		context.setSize(321, 29);
		context.setLocation(680, 555);
		context.setBorder(new LineBorder(Color.black,2));
		add(context);

		fir = new JComboBox(rank);
		fir.setSize(105, 25);
		fir.setLocation(680, 525);
		fir.setBorder(new LineBorder(Color.black,2));
		add(fir);

		sec = new JComboBox(rank);
		sec.setSize(105, 25);
		sec.setLocation(788, 525);
		sec.setBorder(new LineBorder(Color.black,2));
		add(sec);

		thi = new JComboBox(rank);
		thi.setSize(105, 25);
		thi.setLocation(896, 525);
		thi.setBorder(new LineBorder(Color.black,2));
		add(thi);

		comyear = new JComboBox(syear);
		comyear.setSelectedItem(toyear);
		comyear.setSize(100, 30);
		comyear.setLocation(760, 65);
		comyear.setBorder(new LineBorder(Color.black,2));
		add(comyear);

		commonth = new JComboBox(smonth);
		commonth.setSelectedItem(tomonth);
		commonth.setSize(100, 30);
		commonth.setLocation(863, 65);
		commonth.setBorder(new LineBorder(Color.black,2));
		add(commonth);

		select = new JButton("선택");
		select.setSize(63, 30);
		select.setLocation(1005, 65);
		select.setBackground(Color.GRAY);
		select.setBorder(new BevelBorder(BevelBorder.RAISED));
		select.addActionListener(new calActionListener());
		add(select);

		selyear = Integer.toString(todayyear);
		selmonth = Integer.toString(todaymonth);
		today(todayyear, todaymonth);
		readsub(subsub);
		readtodo(todotodo);

		for (int daylabel = 0; daylabel < 7; daylabel++) {
			day[daylabel] = new JLabel(sday[daylabel]);
			day[daylabel].setSize(50, 25);
			day[daylabel].setLocation(700 + daylabel * 53, 98);
			day[daylabel].setHorizontalAlignment(day[daylabel].CENTER);
			day[daylabel].setBorder(new LineBorder(Color.BLACK, 1));
			if (daylabel == 0)
				day[daylabel].setForeground(new Color(255, 0, 0));
			else if (daylabel == 6)
				day[daylabel].setForeground(new Color(0, 0, 255));
			add(day[daylabel]);
		}

		for (int j = 0; j < i; j++) {
			subsave(subtable, subsub[j].Sub, subsub[j].Pro, subsub[j].Time, subsub[j].Year, subsub[j].Seme);
		}
		for (int b = 0; b < e; b++) {
			todosave(todotable, todotodo[b].Todosubname, todotodo[b].Todocontent, todotodo[b].Tododeadline,
					todotodo[b].Todofinish, todotodo[b].Todoclear, todotodo[b].Todoimportance);
		}
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

	}

	class calActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();

			if (source == select) {
				selyear = comyear.getSelectedItem().toString();
				selmonth = commonth.getSelectedItem().toString();
				todayyear = Integer.parseInt(selyear);
				todaymonth = Integer.parseInt(selmonth);
			}

			for (int a = 0; a < 42; a++) {
				date[a].setVisible(false);
			}

			today(todayyear, todaymonth);
		}
	}

	public void readsub(SUB subsub[]) {
		try {
			int i = 0;
			String ForDefaultToFile;
			dataread = new FileReader("C:\\todolist\\" + dir + "\\" + dir + ".txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 5);
			ForDefaultToFile = tokenizer.nextToken();

			for (int j = 0; j < i; j++) {
				String Sub = tokenizer.nextToken();
				String Pro = tokenizer.nextToken();
				String Time = tokenizer.nextToken();
				String Year = tokenizer.nextToken();
				String Seme = tokenizer.nextToken();
				subsub[j] = new SUB(Sub, Pro, Time, Year, Seme);
			}
		} catch (Exception e7) {
			System.out.println("readsub오류");
		}
	}

	public int readsubcnt() {
		int i = 0;
		try {
			dataread = new FileReader("C:\\todolist\\" + dir + "\\" + dir + ".txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 5);

		} catch (Exception e8) {
			System.out.println("readsubcnt오류");
		}

		return i;
	}

	public void writesub(SUB sub[], int cnt) {
		try {
			datawriter = new FileWriter("C:\\todolist\\" + dir + "\\" + dir + ".txt", false);
			BufferedWriter out = new BufferedWriter(datawriter);
			out.write("ForDefaultToFile ");
			if (cnt == 0) {
				out.write("ForDefaultToFile ");
			} else {
				for (int i = 0; i < cnt; i++) {
					out.write(" " + (String) subtable.getValueAt(i, 0) + " " + (String) subtable.getValueAt(i, 1) + " " + (String) subtable.getValueAt(i, 2) + " " + (String) subtable.getValueAt(i, 3) + " "+ (String) subtable.getValueAt(i, 4));
					subsub[i].Sub =(String) subtable.getValueAt(i, 0);
					subsub[i].Pro =(String) subtable.getValueAt(i, 1);
					subsub[i].Time =(String) subtable.getValueAt(i, 2);
					subsub[i].Year =(String) subtable.getValueAt(i, 3);
					subsub[i].Seme =(String) subtable.getValueAt(i, 4);
				}
			}
			out.flush();
			out.close();
			datawriter.close();
		} catch (Exception e) {
			System.out.println(" writesub ls");
		}
	}

	public void subsave(DefaultTableModel table, String Sub, String Pro, String Time, String Year, String Seme) {
		Object sublist[] = { Sub, Pro, Time, Year, Seme };
		table.addRow(sublist);
	}

	public void submodify(DefaultTableModel table, String sub, String Pro, String Time, String Year, String Seme) {
		int i = 0;
		for (; i < table.getRowCount(); i++) {
			if (sub.equals(table.getValueAt(i, 0)) && Pro.equals(table.getValueAt(i, 1))
					&& Time.equals(table.getValueAt(i, 2)) && Year.equals(table.getValueAt(i, 3))
					&& Seme.equals(table.getValueAt(i, 4)))
				table.removeRow(i);
		}
	}

	public void subdelete(DefaultTableModel table, String sub) {
		int k = table.getRowCount();
		int i = 0;
		int cnt = readsubcnt();
		for (; i < table.getRowCount(); i++) {
			if (sub.equals(table.getValueAt(i, 0))) {
				table.removeRow(i);
				break;
			}
		}
		for (; i < cnt - 1; i++)
			subsub[i] = subsub[i + 1];
		writesub(subsub, k - 1);
	}

	public void readtodo(TODO todotodo[]) {
		try {
			int i = 0;
			String ForDefaultToFile;

			dataread = new FileReader("C:\\todolist\\" + dir + "\\todo.txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 6);
			ForDefaultToFile = tokenizer.nextToken();

			for (int j = 0; j < i; j++) {
				String Todosubname = tokenizer.nextToken();
				String Todocontent = tokenizer.nextToken();
				String Tododeadline = tokenizer.nextToken();
				String Todofinish = tokenizer.nextToken();
				String Todoclear = tokenizer.nextToken();
				String Todoimportance = tokenizer.nextToken();
				todotodo[j] = new TODO(Todosubname, Todocontent, Tododeadline, Todofinish, Todoclear, Todoimportance);
			}
		} catch (Exception e7) {
			System.out.println("readsub오류");
		}
	}

	public int readtodocnt() {
		int i = 0;
		try {
			dataread = new FileReader("C:\\todolist\\" + dir + "\\todo.txt");
			BufferedReader in = new BufferedReader(dataread);
			String line = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			i = (tokenizer.countTokens() / 6);

		} catch (Exception e8) {
			System.out.println("readsubcnt오류");
		}

		return i;
	}

	public void writetodo(TODO todotodo[], int cnt) {
		try {
			datawriter = new FileWriter("C:\\todolist\\" + dir + "\\todo.txt", false);
			BufferedWriter out = new BufferedWriter(datawriter);
			if (cnt == 0) {
				out.write("ForDefaultToFile ");
			} else {
				out.write("ForDefaultToFile ");
				for (int i = 0; i < cnt; i++) {
					out.write(" " + (String)todotable.getValueAt(i, 0) + " " + (String)todotable.getValueAt(i, 1) + " "+ (String)todotable.getValueAt(i, 2) + " " + (String)todotable.getValueAt(i, 3) + " " + (String)todotable.getValueAt(i, 4)
+ " " + (String)todotable.getValueAt(i, 5) + " ");
					todotodo[i].Todosubname = (String)todotable.getValueAt(i, 0);
					todotodo[i].Todocontent = (String)todotable.getValueAt(i, 1);
					todotodo[i].Tododeadline = (String)todotable.getValueAt(i, 2);
					todotodo[i].Todofinish = (String)todotable.getValueAt(i, 3);
					todotodo[i].Todoclear = (String)todotable.getValueAt(i, 4);
					todotodo[i].Todoimportance = (String)todotable.getValueAt(i, 5);
				}
			}
			out.flush();
			out.close();
			datawriter.close();
		} catch (Exception e) {
			System.out.println(" writesub ls");
		}
	}

	public void todosave(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline, String Todofinish, String Todoclear, String Todoimportance) {
		Object listtodo[] = { Todosubname, Todocontent, Tododeadline, Todofinish, Todoclear, Todoimportance };
		table.addRow(listtodo);
	}

	public void todomodify(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline,
			String Todofinish, String Todoclear, String Todoimportance) {
		int i = 0;
		for (; i < table.getRowCount(); i++) {
			if (Todosubname.equals(table.getValueAt(i, 0)) && Todocontent.equals(table.getValueAt(i, 1))
					&& Tododeadline.equals(table.getValueAt(i, 2)) && Todofinish.equals(table.getValueAt(i, 3))
					&& Todoclear.equals(table.getValueAt(i, 4)) && Todoimportance.equals(table.getValueAt(i, 5)))
				table.removeRow(i);
		}
	}

	public void tododelete(DefaultTableModel table, String Todosubname, String Todocontent, String Tododeadline,
			String Todofinish, String Todoclear, String Todoimportance) {
		int cnt = readtodocnt();
		int k = table.getRowCount();
		int i = 0;
		for (; i < table.getRowCount(); i++) {
			if ((Todosubname.equals(table.getValueAt(i, 0))) && (Todocontent.equals(table.getValueAt(i, 1)))
					&& (Tododeadline.equals(table.getValueAt(i, 2))) && (Todofinish.equals(table.getValueAt(i, 3)))
					&& (Todoclear.equals(table.getValueAt(i, 4))) && (Todoimportance.equals(table.getValueAt(i, 5)))) {
				table.removeRow(i);
				break;
			}
		}
		for (; i < cnt - 1; i++)
			todotodo[i] = todotodo[i + 1];
		writetodo(todotodo, k - 1);
	}

	public void search(String keyword, DefaultTableModel table) {
		int cnt = readtodocnt();
		table.setNumRows(0);
		if (keyword.length() == 0) {
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		} else {
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				String ver = todotodo[j].Todosubname;
				String ver1 = todotodo[j].Todocontent;
				String ver2 = todotodo[j].Tododeadline;
				String ver3 = todotodo[j].Todofinish;
				String ver4 = todotodo[j].Todoclear;
				String ver5 = todotodo[j].Todoimportance;

				if (ver.contains(keyword) || ver1.contains(keyword) || ver2.contains(keyword) || ver3.contains(keyword)
						|| ver4.contains(keyword) || ver5.contains(keyword)) {
					table.addRow(todolist);
				}
			}
		}
	}

	public void sort(String fir, String sec, String thi, DefaultTableModel table) {
		int cnt = readtodocnt();
		String ver1, ver2, ver5, ver6, ver7, ver8;
		int ver3, ver4;
		TODO temp[] = new TODO[1];
		if (fir.equals("과목명(오름차순)")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 0);
					ver2 = (String) table.getValueAt(j, 0);
					if (ver1.compareTo(ver2) > 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("마감 기한")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 2);
						ver6 = (String) table.getValueAt(j, 2);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 여부")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 3);
						ver6 = (String) table.getValueAt(j, 3);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 날짜")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 4);
						ver6 = (String) table.getValueAt(j, 4);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("중요도")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 5);
						ver6 = (String) table.getValueAt(j, 5);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			  table.setNumRows(0);
			  for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		} else if (fir.equals("과목명(내림차순)")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 0);
					ver2 = (String) table.getValueAt(j, 0);
					if (ver1.compareTo(ver2) < 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("마감 기한")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 2);
						ver6 = (String) table.getValueAt(j, 2);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 여부")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 3);
						ver6 = (String) table.getValueAt(j, 3);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 날짜")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 4);
						ver6 = (String) table.getValueAt(j, 4);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("중요도")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 0);
						ver2 = (String) table.getValueAt(j, 0);
						ver5 = (String) table.getValueAt(i, 5);
						ver6 = (String) table.getValueAt(j, 5);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 0);
							ver2 = (String) table.getValueAt(j, 0);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			table.setNumRows(0);
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		} else if (fir.equals("마감 기한")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 2);
					ver2 = (String) table.getValueAt(j, 2);
					if (ver1.compareTo(ver2) > 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("과목명(오름차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 2);
						ver2 = (String) table.getValueAt(j, 2);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("과목명(내림차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 2);
						ver2 = (String) table.getValueAt(j, 2);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) < 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 여부")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 2);
						ver2 = (String) table.getValueAt(j, 2);
						ver5 = (String) table.getValueAt(i, 3);
						ver6 = (String) table.getValueAt(j, 3);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 날짜")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 2);
						ver2 = (String) table.getValueAt(j, 2);
						ver5 = (String) table.getValueAt(i, 4);
						ver6 = (String) table.getValueAt(j, 4);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("중요도")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 2);
						ver2 = (String) table.getValueAt(j, 2);
						ver5 = (String) table.getValueAt(i, 5);
						ver6 = (String) table.getValueAt(j, 5);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 2);
							ver2 = (String) table.getValueAt(j, 2);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			table.setNumRows(0);
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		} else if (fir.equals("완료 여부")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 3);
					ver2 = (String) table.getValueAt(j, 3);
					if (ver1.compareTo(ver2) > 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("과목명(오름차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 3);
						ver2 = (String) table.getValueAt(j, 3);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
								ver1 = (String) table.getValueAt(i, 3);
								ver2 = (String) table.getValueAt(j, 3);
								ver5 = (String) table.getValueAt(i, 0);
								ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("과목명(내림차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 3);
						ver2 = (String) table.getValueAt(j, 3);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) < 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
								ver1 = (String) table.getValueAt(i, 3);
								ver2 = (String) table.getValueAt(j, 3);
								ver5 = (String) table.getValueAt(i, 0);
								ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("마감 기한")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 3);
						ver2 = (String) table.getValueAt(j, 3);
						ver5 = (String) table.getValueAt(i, 2);
						ver6 = (String) table.getValueAt(j, 2);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 날짜")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 3);
						ver2 = (String) table.getValueAt(j, 3);
						ver5 = (String) table.getValueAt(i, 4);
						ver6 = (String) table.getValueAt(j, 4);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("중요도")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 3);
						ver2 = (String) table.getValueAt(j, 3);
						ver5 = (String) table.getValueAt(i, 5);
						ver6 = (String) table.getValueAt(j, 5);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 3);
							ver2 = (String) table.getValueAt(j, 3);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			table.setNumRows(0);
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		} else if (fir.equals("완료 날짜")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 4);
					ver2 = (String) table.getValueAt(j, 4);
					if (ver1.compareTo(ver2) > 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("과목명(오름차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 4);
						ver2 = (String) table.getValueAt(j, 4);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("과목명(내림차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 4);
						ver2 = (String) table.getValueAt(j, 4);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) < 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("마감 기한")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 4);
						ver2 = (String) table.getValueAt(j, 4);
						ver5 = (String) table.getValueAt(i, 2);
						ver6 = (String) table.getValueAt(j, 2);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 여부")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 4);
						ver2 = (String) table.getValueAt(j, 4);
						ver5 = (String) table.getValueAt(i, 3);
						ver6 = (String) table.getValueAt(j, 3);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("중요도")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 5);
							ver8 = (String) table.getValueAt(j, 5);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("중요도")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 4);
						ver2 = (String) table.getValueAt(j, 4);
						ver5 = (String) table.getValueAt(i, 5);
						ver6 = (String) table.getValueAt(j, 5);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 4);
							ver2 = (String) table.getValueAt(j, 4);
							ver5 = (String) table.getValueAt(i, 5);
							ver6 = (String) table.getValueAt(j, 5);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			table.setNumRows(0);
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}

		} else if (fir.equals("중요도")) {
			for (int i = 0; i < cnt; i++) {
				for (int j = i; j < cnt; j++) {
					ver1 = (String) table.getValueAt(i, 5);
					ver2 = (String) table.getValueAt(j, 5);
					if (ver1.compareTo(ver2) > 0) {
						temp[0] = todotodo[i];
						todotodo[i] = todotodo[j];
						todotodo[j] = temp[0];
					}
				}
			}
			if(sec.equals("과목명(오름차순)")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 5);
						ver2 = (String) table.getValueAt(j, 5);
						ver5 = (String) table.getValueAt(i, 0);
						ver6 = (String) table.getValueAt(j, 0);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 0);
							ver6 = (String) table.getValueAt(j, 0);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("마감 기한")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 5);
						ver2 = (String) table.getValueAt(j, 5);
						ver5 = (String) table.getValueAt(i, 2);
						ver6 = (String) table.getValueAt(j, 2);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 2);
							ver6 = (String) table.getValueAt(j, 2);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 여부")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 5);
						ver2 = (String) table.getValueAt(j, 5);
						ver5 = (String) table.getValueAt(i, 3);
						ver6 = (String) table.getValueAt(j, 3);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 날짜")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 3);
							ver6 = (String) table.getValueAt(j, 3);
							ver7 = (String) table.getValueAt(j, 4);
							ver8 = (String) table.getValueAt(j, 4);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			else if(sec.equals("완료 날짜")){
				for (int i = 0; i < cnt; i++) {
					for (int j = i; j < cnt; j++) {
						ver1 = (String) table.getValueAt(i, 5);
						ver2 = (String) table.getValueAt(j, 5);
						ver5 = (String) table.getValueAt(i, 4);
						ver6 = (String) table.getValueAt(j, 4);
						if (ver5.compareTo(ver6) > 0) {
							if(ver1.equals(ver2)){
								temp[0] = todotodo[i];
								todotodo[i] = todotodo[j];
								todotodo[j] = temp[0];
							}
							else
								continue;
						}
					}
				}
				if(thi.equals("과목명(오름차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("과목명(내림차순)")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 0);
							ver8 = (String) table.getValueAt(j, 0);
							if (ver7.compareTo(ver8) < 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("마감 기한")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 2);
							ver8 = (String) table.getValueAt(j, 2);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
				else if(thi.equals("완료 여부")){
					for (int i = 0; i < cnt; i++) {
						for (int j = i; j < cnt; j++) {
							ver1 = (String) table.getValueAt(i, 5);
							ver2 = (String) table.getValueAt(j, 5);
							ver5 = (String) table.getValueAt(i, 4);
							ver6 = (String) table.getValueAt(j, 4);
							ver7 = (String) table.getValueAt(j, 3);
							ver8 = (String) table.getValueAt(j, 3);
							if (ver7.compareTo(ver8) > 0) {
								if(ver1.equals(ver2)&&ver5.equals(ver6)){
									temp[0] = todotodo[i];
									todotodo[i] = todotodo[j];
									todotodo[j] = temp[0];
								}
								else
									continue;
							}
						}
					}
				}
			}
			table.setNumRows(0);
			for (int j = 0; j < cnt; j++) {
				Object todolist[] = { todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
						todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance };
				table.addRow(todolist);
			}
		}
	}

	class subaddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int k = subtable.getRowCount();
			int q = 0;
			int q1 = 0;
			int inyear = 0;
			String time1, time2, time3, time4, tablever;
			String ver[] = new String[5];
			String ver1[] = new String[5];
			String pattern = "([01][0-9]|2[0-4]):([0-5][0-9])~([01][0-9]|2[0-4]):([0-5][0-9])/(월|화|수|목|금|토|일)";
			Pattern p = Pattern.compile(pattern);
			Matcher ma = p.matcher(timein.getText());
			if (source == subsave) {
				if (yearin.getText().matches(".*[0-9].*"))
					inyear = Integer.parseInt(yearin.getText());
				if (subnamein.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "과목명을 입력하십시오.");
				else if (proin.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "담당 교수를 입력하십시오.");
				else if (timein.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "강의 시간 및 요일을 입력하십시오.");
				else if (!ma.matches()) {
					JOptionPane.showMessageDialog(null, "강의 시간 및 요일의 형식은 hh:mm~hh:mm/D입니다.\nex)18:00~19:00/수");
				} else if (ma.matches()) {
					ver = ((String) timein.getText()).split(":|~|/");
					time1 = ver[0] + ver[1];
					time2 = ver[2] + ver[3];
					if (time1.compareTo(time2) >= 0) {
						JOptionPane.showMessageDialog(null, "올바른 강의 시간 및 요일을 입력하십시오.");
					} else {
						for (int a = 0; a < subtable.getRowCount(); a++) {
							tablever = (String) subtable.getValueAt(a, 2);
							ver1 = (tablever).split(":|~|/");
							for (int d = 0; d < 5; d++) {
								if (ver[4].equals(ver1[4])) {
									time3 = ver1[0] + ver1[1];
									time4 = ver1[2] + ver1[3];
									if (((time1.compareTo(time3) <= 0) && (time2.compareTo(time4) >= 0))
											|| ((time1.compareTo(time3) <= 0) && (time2.compareTo(time3) >= 0))
											|| ((time1.compareTo(time4) <= 0) && (time2.compareTo(time4) >= 0))) {
										q1 = 1;
										break;
									}
								}
							}
						}
						if (q1 == 1) {
							JOptionPane.showMessageDialog(null, "겹치는 강의가 존재합니다.");
						} else if (yearin.getText().length() == 0)
							JOptionPane.showMessageDialog(null, "수강 년도를 입력하십시오.");
						else if (inyear < 2013 || inyear > 2020 || !yearin.getText().matches(".*[0-9].*")) {
							JOptionPane.showMessageDialog(null, "수강 년도의 범위는 2013년부터 2020년까지입니다.");
						} else if (semein.getText().length() == 0)
							JOptionPane.showMessageDialog(null, "학기를 입력하십시오");
						else if (!semein.getText().equals("1학기") && !semein.getText().equals("2학기")) {
							JOptionPane.showMessageDialog(null, "학기의 범위는 1학기, 2학기입니다.");
						} else {
							for (int j = 0; j < subtable.getRowCount(); j++) {
								if (subnamein.getText().equals(subtable.getValueAt(j, 0)))
									q = 1;
							}
							if (q == 1)
								JOptionPane.showMessageDialog(null, "이미 등록된 과목명입니다.");
							else {
								subsave(subtable, subnamein.getText(), proin.getText(), timein.getText(),
										yearin.getText(), semein.getText());
								subsub[k] = new SUB(subnamein.getText(), proin.getText(), timein.getText(),
										yearin.getText(), semein.getText());
								writesub(subsub, subtable.getRowCount());
								subnamein.setText("");
								proin.setText("");
								timein.setText("");
								yearin.setText("");
								semein.setText("");
							}
						}
					}

				}
			}
		}
	}

	class SubMouseListener implements MouseListener {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
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
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int i = readsubcnt();
			int j = 0;
			if (source == submodify) {
				if(subnamein.getText().length()!=0){
					JOptionPane.showMessageDialog(null, "수정중인 과목이 있습니다.");
				}
				else{
					if(row<=subtable.getRowCount()&&row>=0){
						subnamein.setText((String) subtable.getValueAt(row, 0));
						proin.setText((String) subtable.getValueAt(row, 1));
						timein.setText((String) subtable.getValueAt(row, 2));
						yearin.setText((String) subtable.getValueAt(row, 3));
						semein.setText((String) subtable.getValueAt(row, 4));
						submodify(subtable, (String) subtable.getValueAt(row, 0), (String) subtable.getValueAt(row, 1),
							(String) subtable.getValueAt(row, 2), (String) subtable.getValueAt(row, 3),
							(String) subtable.getValueAt(row, 4));
						row=10000;
					}
					else
						JOptionPane.showMessageDialog(null, "원하는 과목을 선택하세요.");
				}
			}
		}
	}

	class subdeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int cnt1 = subtable.getRowCount();
			int cnt2 = todotable.getRowCount();
			int q = 0;
			if (source == subdelete) {
				if(row<=subtable.getRowCount()&&row>=0){
					for (int j = 0; j < cnt2; j++) {
						if (subtable.getValueAt(row, 0).equals(todotable.getValueAt(j, 0))) {
							q = 1;
						}
					}
					if (q == 1) {
						JOptionPane.showMessageDialog(null, "항목이 있는 과목입니다.");
					} 
					else
						subdelete(subtable, (String) subtable.getValueAt(row, 0));
				}
				else
					JOptionPane.showMessageDialog(null, "원하는 과목을 선택하세요.");
			}
		}
	}

	class todoaddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int k = todotable.getRowCount();
			int m = readsubcnt();
			int q = 0;
			int q1 = 0;
			String pattern = "(20)(1[3-9]|20)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])";
			Pattern p = Pattern.compile(pattern);
			Matcher ma = p.matcher(deadlinein.getText());
			Matcher mb = p.matcher(clearin.getText());
			String deadin = deadlinein.getText();
			String clein = clearin.getText();
			String inyear, inmonth, inday;
			GregorianCalendar calver = new GregorianCalendar();
			if (source == todosave) {
				if (todosubnamein.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "과목명을 입력하십시오.");
				else if (contentin.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "항목명(해야할 일)을 입력하십시오.");
				else if (deadlinein.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "마감 기한을 입력하십시오.");
				else if (!ma.matches()) {
					JOptionPane.showMessageDialog(null,
							"마감 기한 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
				} else if (ma.matches()) {
					inyear = deadin.substring(0, 4);
					inmonth = deadin.substring(4, 6);
					inday = deadin.substring(6, 8);
					int year = Integer.parseInt(inyear);
					int month = Integer.parseInt(inmonth);
					int day = Integer.parseInt(inday);
					calver.set(year, month - 1, 1);
					int lastdayver = calver.getActualMaximum(calver.DAY_OF_MONTH);
					if (Integer.parseInt(inday) > lastdayver) {
						JOptionPane.showMessageDialog(null,"마감 기한 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
					} 
					else if (finishin.getText().length() == 0)
						JOptionPane.showMessageDialog(null, "완료 여부를 입력하십시오.");
					else if (!finishin.getText().matches("[o|x]*"))
						JOptionPane.showMessageDialog(null, "완료 여부는 o,x만 가능합니다.");
					else if (clearin.getText().length() == 0)
						JOptionPane.showMessageDialog(null, "완료 날짜를 입력하십시오.\n완료한 경우에는 날짜를 입력하시고 미완료한  경우에는 x를 입력하십시오.");
					else if (mb.matches()) {
							inyear = clein.substring(0, 4);
							inmonth = clein.substring(4, 6);
							inday = clein.substring(6, 8);
							year = Integer.parseInt(inyear);
							month = Integer.parseInt(inmonth);
							day = Integer.parseInt(inday);
							calver.set(year, month - 1, 1);
							lastdayver = calver.getActualMaximum(calver.DAY_OF_MONTH);
							if (Integer.parseInt(inday) > lastdayver) {
								JOptionPane.showMessageDialog(null,"완료 날짜 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
							} 
							else if (importancein.getText().length() == 0)
								JOptionPane.showMessageDialog(null, "중요도를 입력하십시오.");
							else if (!importancein.getText().matches("[0-3]*"))
								JOptionPane.showMessageDialog(null,
										"중요도는 0~3까지 입력할 수 있습니다.\n0 = 중요하지 않음\n1 = 가장 중요 \n2 = 중간 중요\n3 = 약간 중요");
							else {
								for (int j = 0; j < m; j++) {
									if (todosubnamein.getText().equals(subsub[j].Sub)) {
										q += 1;
										break;
									} else
										continue;
								}
								if (q == 1) {
									for (int i = 0; i < todotable.getRowCount(); i++) {
										if (contentin.getText().equals(todotable.getValueAt(i, 1))) {
											q1 = 1;
											break;
										}
									}
									if (q1 == 1) {
										JOptionPane.showMessageDialog(null, "이미 있는 항목명(해야할 일)입니다.");
									} 
									else {
										todosave(todotable, todosubnamein.getText(), contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
										todotodo[k] = new TODO(todosubnamein.getText(), contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
										writetodo(todotodo, todotable.getRowCount());
										readtodo(todotodo);
										todosubnamein.setText("");
										contentin.setText("");
										deadlinein.setText("");
										finishin.setText("");
										clearin.setText("");
										importancein.setText("");
									}
								} 
								else
									JOptionPane.showMessageDialog(null, "등록된 과목이 없습니다.");
							}	
						}
					else if((clearin.getText().equals("x"))){
						if (importancein.getText().length() == 0)
							JOptionPane.showMessageDialog(null, "중요도를 입력하십시오.");
						else if (!importancein.getText().matches("[0-3]*"))
							JOptionPane.showMessageDialog(null,"중요도는 0~3까지 입력할 수 있습니다.\n0 = 중요하지 않음\n1 = 가장 중요 \n2 = 중간 중요\n3 = 약간 중요");
						else {
							for (int j = 0; j < m; j++) {
								if (todosubnamein.getText().equals(subsub[j].Sub)) {
									q += 1;
									break;
								} else
									continue;
							}
							if (q == 1) {
								for (int i = 0; i < todotable.getRowCount(); i++) {
									if (contentin.getText().equals(todotable.getValueAt(i, 1))) {
										q1 = 1;
										break;
									}
								}
								if (q1 == 1) {
									JOptionPane.showMessageDialog(null, "이미 있는 항목명(해야할 일)입니다.");
								} else {
									todosave(todotable, todosubnamein.getText(), contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
									todotodo[k] = new TODO(todosubnamein.getText(), contentin.getText(), deadlinein.getText(), finishin.getText(), clearin.getText(), importancein.getText());
									writetodo(todotodo, todotable.getRowCount());
									readtodo(todotodo);
									todosubnamein.setText("");
									contentin.setText("");
									deadlinein.setText("");
									finishin.setText("");
									clearin.setText("");
									importancein.setText("");
								}
							} else
								JOptionPane.showMessageDialog(null, "등록된 과목이 없습니다.");
						}
					}
					
					else if (!mb.matches()) {
						JOptionPane.showMessageDialog(null, "완료 날짜 입력 형식은 yyyyMMdd이며 범위는 20130101~20201231까지입니다. \n달력에 있는 날짜를 입력하세요.");
					}
				}
			}
		}
	}

	class TodoMouseListener implements MouseListener {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
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
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int i = readtodocnt();
			int j = 0;
			if (source == todomodify) {
				if(todosubnamein.getText().length()!=0){
					JOptionPane.showMessageDialog(null, "수정중인 항목이 있습니다.");
				}
				else{
					if(rowtodo<=todotable.getRowCount()&&rowtodo>=0){
						todosubnamein.setText((String) todotable.getValueAt(rowtodo, 0));
						contentin.setText((String) todotable.getValueAt(rowtodo, 1));
						deadlinein.setText((String) todotable.getValueAt(rowtodo, 2));
						finishin.setText((String) todotable.getValueAt(rowtodo, 3));
						clearin.setText((String) todotable.getValueAt(rowtodo, 4));
						importancein.setText((String) todotable.getValueAt(rowtodo, 5));
						todomodify(todotable, (String) todotable.getValueAt(rowtodo, 0),
						(String) todotable.getValueAt(rowtodo, 1), (String) todotable.getValueAt(rowtodo, 2),
						(String) todotable.getValueAt(rowtodo, 3), (String) todotable.getValueAt(rowtodo, 4),
						(String) todotable.getValueAt(rowtodo, 5));
						rowtodo=10000;
					}
					else
						JOptionPane.showMessageDialog(null, "원하는 항목을 선택하세요.");
				}
			}
		}
	}

	class tododeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == tododelete) {
				if(rowtodo<=todotable.getRowCount()&&rowtodo>=0){
				tododelete(todotable, (String) todotable.getValueAt(rowtodo, 0),
						(String) todotable.getValueAt(rowtodo, 1), (String) todotable.getValueAt(rowtodo, 2),
						(String) todotable.getValueAt(rowtodo, 3), (String) todotable.getValueAt(rowtodo, 4),
						(String) todotable.getValueAt(rowtodo, 5));
				rowtodo=10000;
				}
				else
					JOptionPane.showMessageDialog(null, "원하는 항목을 선택하세요.");
			}
		}
	}

	class searchActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == search) {
				search(context.getText(), todotable);
			}
		}
	}

	class calselectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int j = 0;
			int click = 0;
			for (int i = 0; i < 42; i++) {
				date[i].setBackground(new Color(255, 255, 255));
				if (i % 7 == 0)
					date[i].setForeground(new Color(255, 0, 0));
				else if (i % 7 == 6)
					date[i].setForeground(new Color(0, 0, 255));
				else
					date[i].setForeground(new Color(0, 0, 0));
				if (source == date[i]) {
					click = i;
					if (date[i].getText().length() == 0)
						JOptionPane.showMessageDialog(null, "날짜를 클릭하십시오.");
					else {
						j = Integer.parseInt(date[i].getText());
						date[click].setBackground(new Color(150, 150, 150));
						date[click].setForeground(new Color(234, 204, 026));
						String clickmonth = String.format("%02d", todaymonth);
						String clickday = String.format("%02d", Integer.parseInt(date[click].getText()));
						String datever = selyear + clickmonth + clickday;
						search(datever, todotable);
					}
				}
			}
		}
	}

	class sortActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == sort) {
				String ver=fir.getSelectedItem().toString()+sec.getSelectedItem().toString()+thi.getSelectedItem().toString();
				if(hiding==1)
					JOptionPane.showMessageDialog(null, "숨김파일이 있습니다.");
				else if(fir.getSelectedItem().toString().equals(sec.getSelectedItem().toString())||fir.getSelectedItem().toString().equals(thi.getSelectedItem().toString())||thi.getSelectedItem().toString().equals(sec.getSelectedItem().toString()))
					JOptionPane.showMessageDialog(null, "중복된 정렬이 있습니다.");
				else if(ver.contains("내림차순")&&ver.contains("오름차순"))
					JOptionPane.showMessageDialog(null, "과목명(오름차순)과 과목명(내림차순)은 함께 할 수 없습니다.");
				else if(fir.getSelectedItem().toString().equals("-")&&!sec.getSelectedItem().toString().equals("-"))
					JOptionPane.showMessageDialog(null, "-다음 순위는 -밖에 될 수 없습니다.");
				else if(sec.getSelectedItem().toString().equals("-")&&!thi.getSelectedItem().toString().equals("-"))
					JOptionPane.showMessageDialog(null, "-다음 순위는 -밖에 될 수 없습니다.");
				else{
					for(int count =0; count<todotable.getColumnCount();count++)
						sort(fir.getSelectedItem().toString(), sec.getSelectedItem().toString(), thi.getSelectedItem().toString(), todotable);
				}
			}
		}
	}

	class hideActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int cnt = readtodocnt();
			todotable.setRowCount(0);
			if (source == hide) {
				for (int j = 0; j < cnt; j++) {
					if (todotodo[j].Todofinish.equals("o")) {
						hiding=1;
						continue;
					} else
						todosave(todotable, todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
								todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance);
				}
			}
		}
	}

	class unhideActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			int cnt = readtodocnt();
			todotable.setRowCount(0);
			if (source == unhide) {
				for (int j = 0; j < cnt; j++) {
					todosave(todotable, todotodo[j].Todosubname, todotodo[j].Todocontent, todotodo[j].Tododeadline,
							todotodo[j].Todofinish, todotodo[j].Todoclear, todotodo[j].Todoimportance);
					hiding=0;
				}
			}
		}
	}

	public void today(int year, int month) {
		GregorianCalendar cal;
		cal = new GregorianCalendar();

		int todayyear = cal.get(cal.YEAR);
		int todaymonth = (cal.get(cal.MONTH) + 1);
		int today = cal.get(cal.DATE);

		cal.set(year, month - 1, 1);
		firstday = cal.get(cal.DAY_OF_WEEK);
		lastday = cal.getActualMaximum(cal.DAY_OF_MONTH);

		int j = 700;
		int l = 125;
		int i = 0;

		String sdate;

		for (; i < firstday - 1; i++) {
			sdate = "";
			date[i] = new JButton(sdate);
			date[i].setSize(50, 30);
			date[i].setLocation(j, l);
			date[i].setBackground(new Color(255, 255, 255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j += 53;

			if (i % 7 == 6) {
				l += 33;
				j = 700;
			}
		}

		for (int a = 1; a < lastday + 1; i++, a++) {
			sdate = "" + a;
			date[i] = new JButton(sdate);
			date[i].setSize(50, 30);
			date[i].setLocation(j, l);
			date[i].setBackground(new Color(255, 255, 255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j += 53;

			if (i % 7 == 0)
				date[i].setForeground(new Color(255, 0, 0));

			else if (i % 7 == 6) {
				date[i].setForeground(new Color(0, 0, 255));
				l += 33;
				j = 700;
			}

			if ((year == todayyear) && (month == todaymonth) && (today == a)) {
				date[i].setForeground(new Color(0, 150, 0));
			}
		}

		while (i < 42) {
			sdate = "";
			date[i] = new JButton(sdate);
			date[i].setSize(50, 30);
			date[i].setLocation(j, l);
			date[i].setBackground(new Color(255, 255, 255));
			date[i].addActionListener(new calselectActionListener());
			add(date[i]);
			j += 53;
			if (i % 7 == 6) {
				l += 33;
				j = 700;

			}
			i++;

		}
	}
}
