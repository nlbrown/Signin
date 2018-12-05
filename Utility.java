import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Norman Brown  
 *
 */
public class Utility {
	

	static ArrayList<String> Utilread = new ArrayList<String>();

	/**
	 * @param inFile
	 * reads String infile name passed in from calling method
	 * @return ArrayList
	 */
	static ArrayList<String> ReadFile(String inFile){
		BufferedReader br = null;
		FileReader fr = null;
		Utilread.clear();
//		System.out.println("in read ");
		try {

			fr = new FileReader(inFile);
			br = new BufferedReader(fr);
			String sCurrentLine = "";
//			System.out.println("inFile = " + inFile);

			while ((sCurrentLine = br.readLine()) != null) {
					Utilread.add(sCurrentLine);
			} // while
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		inFile = "";
		return Utilread;
		// fr.reset();
	}
 
	/**
	 * @param outFile
	 * @param outline
	 * writes to outfile from outLINE passed from calling method
	 * @return
	 */
	static boolean WriteFile(String outFile,String outline){
		boolean sucess = true;
		FileWriter fw = null;
		try {
			fw = new FileWriter(outFile, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw.newLine();
			bw.append(outline);
//			bw.write(outline);
			bw.flush();
			bw.close();
		} catch (IOException e1) {
		    sucess = false;
//			e1.printStackTrace();
		}	
		return sucess;
	}
	
 }

