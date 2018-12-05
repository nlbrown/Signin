
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * @author Norman Brown
 *
 */
public class Signin extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtDMACCID;	
	private JTextField PtxtMsg;
	private String outrec = "";
	private static String ClassChoice;
	private static LocalDateTime datetime;
	static String filename ="";
	private static String StudentFileprex = System.getProperty("user.home")
//			+ "\\Desktop\\CIS151\\Admin\\Student.txt";
			+ "\\Desktop\\Signin\\Admin\\"+filename;
	private static String StudentFile;
	private static final String ClassFile = System.getProperty("user.home")
			+ "\\Desktop\\Signin\\Admin\\Class.txt";
	private static final String AttendanceFile = System.getProperty("user.home")
			+ "\\Desktop\\Signin\\Admin\\Attendance.txt";
	ArrayList<String> signedin = new ArrayList<String>();
	ArrayList<String> StuOptions = new ArrayList<String>();
	private static ArrayList<String> ClassOptions = new ArrayList<String>();
	private static int[] STime;
	private static int[] ETime;
	private static char[] Day;
	private static String[] Students;
	private static String[] Classes;
	private static String[] StuFiles = new String[5];
	String pattern = ",";
	String retval = "";
	private static String StuRec = "";
	private static String[] temp1;
	JFrame form = new JFrame();
	JFrame form2;
	JPanel panel;
	JPanel pane2;

	
	Signin() {	
		    filename = "";
		    StudentFile = "";
			getDateTime();
			createFrame(); 		
		}
		
		private void getDateTime() {
			datetime = LocalDateTime.now();
		}
		
		/** createFrame()
		 * Creates opening JFrame
		 */
		private void createFrame() {			
			setTitle("Sign in");
			setLocation(150,150);
			setPreferredSize(new Dimension(360,360));
			setContentPane(new JLabel(new ImageIcon("DMACC.jpg")));
			setSize(800, 400);
			setDefaultCloseOperation(EXIT_ON_CLOSE); 
			add(createPanel());
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		/** createPanel()
		 * creates entry panel. Gets info from
		 * student and class files for validation
		 * get student info
		 * @return
		 */
		
		private JPanel createPanel() {
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(360,90));
			setLayout(new FlowLayout()); // set the layout manager
			ClassOptions = Utility.ReadFile(ClassFile);	
			STime = new int[ClassOptions.size()];
			ETime = new int[ClassOptions.size()];
			Day = new char[ClassOptions.size()];
			Classes = new String[ClassOptions.size()];
			for (int i=0;i<ClassOptions.size();i++) {
				retval = ClassOptions.get(i);
				if(ClassOptions.get(i).charAt(0) != '-') {
					temp1 = retval.split(pattern);
					Day[i] =  temp1[0].charAt(0);
					STime[i] = Integer.parseInt(temp1[1]);
//					System.out.println("S  time"+STime[i]);
					ETime[i] = Integer.parseInt(temp1[2]);
					Classes[i] = temp1[3];
					StuFiles[i] = temp1[4];
				}
			}		
			GetStuFile();    // <=== not method. calling method below>
			StuOptions = Utility.ReadFile(StudentFile);		
		    Students = new String[StuOptions.size()];
			StuOptions.toArray(Students);
			JLabel lblDmaccId = new JLabel("DMACC ID #: ");
			panel.add(lblDmaccId);
			txtDMACCID = new JTextField(9);
			panel.add(txtDMACCID);
			JButton submit=new JButton("Submit");
			submit.setLayout(new BoxLayout(submit, BoxLayout.PAGE_AXIS));
			ButtonListener bl = new ButtonListener();
			submit.addActionListener(bl);
			panel.add(submit);			
			PtxtMsg = new JTextField(55);
			PtxtMsg.setBackground(Color.gray);
			PtxtMsg.setVisible(false);
			panel.add(PtxtMsg);
			return panel;		
		}
	
		/** GetStuFile(
		 * Get student file info
		 * 
		 */
		public void GetStuFile() {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
			String dayOfWeek = dateFormat.format(date);	
			char dayChar = dayOfWeek.charAt(0);
			Date now = new Date();
		    SimpleDateFormat militaryTime = new SimpleDateFormat("kkmm");
		    int CurrMilTime = Integer.parseInt(militaryTime.format(now));
//******  testing time *******
//		    CurrMilTime = 1000; // set time to 1000 am
//		    dayChar = 'T';
 //******  testing time end *******
		    for (int z=0;z<STime.length;z++) {
		    	if ( (Day[z]==dayChar) && (CurrMilTime >= STime[z]) && (CurrMilTime <= ETime[z])) { 
//		    		System.out.println("Found "+z);
		    		filename = StuFiles[z]; 
		    		System.out.println("filename : "+filename);
		    		System.out.println("file complete : "+StudentFileprex+filename);
    	    		StudentFile = StudentFileprex+filename;
//    	    		System.out.println("Class "+Classes[z]);
    	    		ClassChoice = Classes[z];
		    		break;
		    	}
		    	if(z==STime.length-1) {StudentFile = StudentFile+"NoClass.txt";}
		    }
		    		
		}

      //  Button listner which does validation of input DMACCID
		class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) { 
				boolean valid = false;
				String ID = txtDMACCID.getText();
					for(int u=0;u<Students.length;u++) {					
						if (Students[u].contains(ID)){
//							System.out.println("Id "+ID);
//							System.out.println("Student : "+ Students[u]);
							StuRec = Students[u];
							valid = true;
							break;
							}
						}
//***********************************************************************
					if(!valid) {
						JOptionPane.showMessageDialog(null, 
						  " Student ID Not in Class Roster "
								  + ID);
							valid = false;						
						}
//********************************************************
					if(ID.length() != 9 || !ID.startsWith("900") ||
							ID.isEmpty()){
								JOptionPane.showMessageDialog(null, 
										" InValid Student ID "+ ID);
								valid = false;			        	
					}							
//**************************************************************************
					if(signedin.contains(ID)) {
						JOptionPane.showMessageDialog(null, 
								"Student ID signed in "+ ID);
						valid = false;			        
					}
				if(valid) {
				outrec = ClassChoice + "," + datetime
						+ ","+StuRec;
//				System.out.print("outrec"+outrec);
				boolean sucessWrite = Utility.WriteFile(AttendanceFile,outrec);
				
				if(sucessWrite) {
					signedin.add(ID);
					PtxtMsg.setVisible(true);
					validate();
					PtxtMsg.setText("Sucessfull entry - "+ outrec);
					PtxtMsg.setBackground(Color.green);
					PtxtMsg.setVisible(true);
					repaint();

				JOptionPane.showMessageDialog(null, 
							  " Please click OK ");
				PtxtMsg.setVisible(false);
				txtDMACCID.setText(null);
				repaint();
					}//sucessWrite
				}//valid
			}//actionperformed
	}//ButtonListener
}
