import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * @author Norman Brown
 *
 */
public class signinReport {
	private static String filename = "";
	private boolean success;
	private static String Classfile = "";
	private static String ClassName = "";
	private static String ClassNumber = "";
	private static String StudentFile = "";
	private static String date;
	private static String rpthead1 = "---------------------------------------";
	private static String rpthead2 = "SIGNIN  INFORMATION  ";
	private static int filenum;
	private static String StudentFilePX = System.getProperty("user.home")
//			+ "\\Desktop\\CIS151\\Admin\\Student.txt";
//			+ "\\Desktop\\Signin\\Student.txt";
			+ "\\Desktop\\Norm\\"+filename;
//		+ "\\Desktop\\Signin"+filename;
	private static final String ClassFile = System.getProperty("user.home")
			+ "\\Desktop\\Norm\\Class.txt";
//			+ "\\Desktop\\Signin"+filename;
	private static final String AttendanceFile = System.getProperty("user.home")
			+ "\\Desktop\\Norm\\Attendance.txt";
//			+ "\\Desktop\\Signin\\"+filename;
	private static final String AttendanceRptFile = System.getProperty("user.home")
			+ "\\Desktop\\Norm\\AttendanceRpt.txt";
//	private static ArrayList<String> signedin = new ArrayList<String>();
	private static ArrayList<String> temp;
//	private static ArrayList<String> temp1;
	private static String[] temp1;
	private static String[] AttenArr;
	private static String[] StuArr;
//	private static ArrayList<String> AttenArr;
//	private static ArrayList<String> AttenArr = new ArrayList<String>();
	private static ArrayList<String> ClassArr;
	private static ArrayList<String> StudentArr;
//	private static ArrayList<String> fileArr;
	private static String [][] fileArr;
	Queue<String> q = new LinkedList<String>();
	
	String pattern = ",";
	String retval = "";

	
	
	signinReport(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String date = sdf.format(new Date());
		success = Utility.WriteFile(AttendanceRptFile,"   ");
		success = Utility.WriteFile(AttendanceRptFile,"             Attemdance Report  "+date);
		loadClassfiles();
		loadAttendance();
//		processAttendance();
		for(filenum = 0;filenum<fileArr.length;filenum++) {
			processAttendance();
		}
		
	}

	/** LoadAttendance
	 * Load attendance file
	 */
	private void loadAttendance() {
		System.out.println(" Attendance : ");
		temp = Utility.ReadFile(AttendanceFile);
		AttenArr = new String[temp.size()];
		int z = 0;
		for(int i=temp.size()-1; i>0;i--) {
			retval = temp.get(i);
	
			if(retval != "") {
				AttenArr[z] = retval;
				z++;
			}
		}
		temp = null;
		System.out.println("AttenArr size : "+AttenArr.length);
		System.out.println(" Attendance Loaded ");
	}
	
	/**
	 * load class files into array & Queue
	 */
	private void loadClassfiles() {

		System.out.println(" Class : ");
		temp = Utility.ReadFile(ClassFile);
		fileArr = new String[temp.size()][temp.size()];
		for(int i=0;i<temp.size();i++) {
			q.add(temp.get(i)); // adding class file to queue
			retval = temp.get(i);
			if(retval != "") {
				temp1 = retval.split(pattern);
				System.out.println(" Class temp1 [3]: "+ temp1[3]);
				fileArr[i][1] = temp1[3]; 
				fileArr[i][2] = temp1[4];
			}		
		}
		System.out.println("File name loaded into file Arr & Queue ");
		System.out.println("Elements of queue-"+q); 
	}

	/**
	 * write attendance report class headings
	 * @param classtitle
	 */
	public void ReportHeadings(String classtitle){
		success = Utility.WriteFile(AttendanceRptFile,"  ");
		success = Utility.WriteFile(AttendanceRptFile,rpthead1);
	    success = Utility.WriteFile(AttendanceRptFile,classtitle);	
		success = Utility.WriteFile(AttendanceRptFile,rpthead2);
		success = Utility.WriteFile(AttendanceRptFile,rpthead1);
		
	}
	
	/**
	 *  load students from class files
	 */
	private void loadStudents() {
		System.out.println(" Students in class : ");
		ClassName = fileArr[filenum][1]; 
		ClassNumber = ClassName.substring(0,5);
		System.out.println("Class Name --"+ ClassName);
		System.out.println("Class Number ---"+ ClassNumber);
		ReportHeadings(ClassName);
		filename = fileArr[filenum][2]; 
		StudentFile = StudentFilePX+filename;
		StudentArr = Utility.ReadFile(StudentFile);
		List<String> list_without_null = new ArrayList<String>();
	    for(String new_string : StudentArr ) {
	        if(new_string != null && new_string.length() > 0) {
	                       list_without_null.add(new_string);
	          }
	    }
	    StuArr = list_without_null.toArray(new String[list_without_null.size()]);
		System.out.println("Students loaded ");
	}
	
	/**
	 * read attendance array, if StudentID and course number match
	 * write record to attendance report
	 */
	private void processAttendance() {
		loadStudents();		
		System.out.println("Processing file "+filename);
		System.out.println("StuArr size: "+StuArr.length);
		for(int s=0;s<StuArr.length;s++) {
			retval = StuArr[s];
			temp1 = retval.split(pattern);	
			System.out.println("Student :"+temp1[1]);	
			for(int a=0;a<AttenArr.length-1;a++) {	
//	reverse		for(int a=AttenArr.length-2;a>-1;a--) {	
				System.out.println("ClassNumber :"+ClassNumber);
				System.out.println("Atten :"+AttenArr[a]);			
				if((AttenArr[a]).contains(temp1[1]) && 
					(AttenArr[a]).contains(ClassNumber)) {
					//AttenArr is complete login info can be modified
					 String outline = AttenArr[a];
					success = Utility.WriteFile(AttendanceRptFile,outline);
					System.out.println("Found!!!! write code"+success);
					break;				
				}
		// **** todo  add code to display students not in
		//	attendance file
		//	boolean success = Utility.WriteFile(AttendanceRptFile,"Not found"+temp1[1]);
			}
//			System.out.println(AttenArr[s]);
//			System.out.println("0: "+temp1[0]);
//			System.out.println("1: "+temp1[1]);
			String outline = temp1[1];
		}
	}// end of processAttendance
}// end of class
