package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 * 
 * @author AlexanderM
 * Class that should generate a report for a given project. Go through the whole project and requests the project classes generate a report and output it the file
 * Not really sure how it is supposed to do that...
 */
public class Report{
	//These shouldn't change, a Report is made once
	//private Scanner outputScanner;
	private PrintWriter outputWriter;
	private File reportFile;
	private String reportFileDestinationPath;
	
	
	
	
	private Account reportRequester;
	

	public Report(Project reportable,String reportFileName,String file_path,Account requester) {
		// TODO Auto-generated constructor stub
		reportRequester = requester;
		reportFileDestinationPath = file_path+reportFileName;
		reportFile = new File(reportFileDestinationPath);
		try {
			outputWriter = new PrintWriter(reportFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	private File generateReport()
	{
		return reportFile;
		
	}
	

	public File getReportFile() {
		return reportFile;
	}


	public void generateNewReportFile(File reportFile) {
		this.reportFile = reportFile;
	}


	public String getReportFileDestinationPath() {
		return reportFileDestinationPath;
	}


	public void setReportFileDestinationPath(String reportFileDestinationPath) {
		this.reportFileDestinationPath = reportFileDestinationPath;
	}
	
	
	
	
	

}
