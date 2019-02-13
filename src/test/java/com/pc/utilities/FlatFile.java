package com.pc.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JPopupMenu.Separator;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pc.constants.PCConstants;

public class FlatFile {
	
	
	static  Logger logger =Logger.getLogger(FlatFile.class);
	private static FlatFile flatFile = null;
	private static BufferedReader read;
	private static BufferedWriter write;
	private static FileWriter fw;
	private static FileReader fr;
	private static final String FILE_HEADER = "ID,Component_Name,Key_Name,Key_Value,Value_type";
	private static final String COMMA_DELIMITER = ",";
	private static final String newLine = System.getProperty("line.separator");
	private FlatFile(){
		
	}
	
	public static FlatFile getInstance(String sReadorWrite,String sFileName){
//		if (flatFile == null){
			flatFile = new FlatFile(sReadorWrite,sFileName);
//		}
		return flatFile;
	}
	
	public static FlatFile getInstance()
	{
		flatFile = new FlatFile();
		return flatFile;
	}
	

	/**
	 * Purpose- Constructor to pass Excel file name
	 * @param sFileName
	 */
	private FlatFile(String sReadorWrite, String sFileName)
	{
	
		try
		{
			//app = new ConfigManager("App");
			String dir = null;
			File directory = new File (".");
			dir = directory.getCanonicalPath();
			String sFilePath = dir + "\\Data\\FlatFiles\\" + sFileName + ".txt";
			//String sFilePath = "D:\\FrameWorks\\SeleniumJava\\WorkSpace\\GuidewirePC\\Data\\Data.xlsx";			 
			File file = new File(sFilePath);
			if(file.exists())
			{
				if(sReadorWrite.toUpperCase().contains("WRITE"))
				{
//					fw = new FileWriter(file);
					fw = new FileWriter(file,true);
					write = new BufferedWriter(fw);
				}else
				{
					fr = new FileReader(file);
		            read = new BufferedReader(fr);
				}
			}
			else
			{
				//UtilityMethods.infoBox("File with name-'"+sFileName+"' doesn't exists in Data Folder, Please Re-check given file name", "Config.properties");
				logger.error("Error Reading Flat File from FlatFiles Folder. Hence shutting down the application" );
				
				Exception e = new Exception("File with name-'"+sFileName+"' doesn't exists in FlatFiles Folder");
				logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
			}
		} 
		catch (Exception e)
		{			
			e.printStackTrace();
			logger.error("Error Reading Excel File from Data Folder. Hence shutting down the application" );	
			Exception e1 = new Exception("File with name-'"+sFileName+"' doesn't exists in Data Folder");
			logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e1.getMessage(), e1);
			//UtilityMethods.infoBox(e.getMessage(), "Exception");
			System.exit(0);
		} 
	}
	
	public Boolean CreateFile() 
	{
		Boolean status = false;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String sFileName = ""+PCThreadCache.getInstance().getProperty("TCID")+"_"+timeStamp+"";
		PCThreadCache.getInstance().setProperty(PCConstants.FlatFile, sFileName);
		FileWriter fw = null;
		BufferedWriter fileWriter = null;
		String dir = null;
        try{
			File directory = new File (".");
			dir = directory.getCanonicalPath();
			String sFilePath = dir + "\\Data\\FlatFiles\\" + sFileName + ".txt";
            File file = new File(sFilePath);
            file.createNewFile();
            fw = new FileWriter(file);
            fileWriter = new BufferedWriter(fw);
          /*  bw.write("ID");
            bw.write(",");
            bw.write("COMPONENTS");
            bw.write(",");
            bw.write("KEYNAME");
            bw.write(",");
            bw.write("KEYVALUE");
            bw.flush();
            bw.close();*/
            fileWriter.append(FILE_HEADER.toString());
			System.out.println("CSV file was created successfully !!!");
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	try {
				fileWriter.flush();
				fileWriter.close();
				fw= null;
				fileWriter = null;
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
        }
		return status;
	}

	public String Read(String readValue, String sFileName)
	{
            FlatFile.getInstance("Read", sFileName);
			Boolean status = false;
            String strLine = "";
            String FlatFileValue = null;
            String targetValue = null;
            try {
				while( (strLine = read.readLine()) != null)
				{
				    String[] Values = strLine.split(",");
				    for(int i =2;i<Values.length;i++)
					{
				    	FlatFileValue = Values[i];
//				    	System.out.println(FlatFileValue);
				    	if(FlatFileValue.toUpperCase().equals(readValue.toUpperCase()))
				    	{
				    		targetValue = Values[i+1];
				    		status = true;
				    	}
				    	if(status)
				    	{
				    		break;
				    	}
				    }
				    if(status)
			    	{
			    		break;
			    	}
				}
				if(!status)
				{
					logger.info(""+readValue+" is not avilable in the "+sFileName+" file");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
            {
				
            }
		return targetValue;
	}
	
	public Boolean write(String ID, String ComponentName, String KeyName, String KeyValue,String ValueType, String sFileName)
	{
		Boolean status = false;
		FlatFile.getInstance("Write", sFileName);
		try {
			/*write.newLine();
			write.write(a);
			write.write(",");
			write.write(b);
			write.write(",");
			write.write(c);
			write.write(",");
			write.write(d);*/
			write.write(newLine);
			write.append(ID);
			write.append(COMMA_DELIMITER);
			write.append(ComponentName);
			write.append(COMMA_DELIMITER);
			write.append(KeyName);
			write.append(COMMA_DELIMITER);
			write.append(KeyValue);
			write.append(COMMA_DELIMITER);
			write.append(ValueType);
//			write.flush();
			logger.info(KeyName + " Key is updated with the value of " + KeyValue);
			status = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
//				write.close();
				write.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public Boolean flatFileClose()
	{
		Boolean status = false;
		try {
			if(read != null && write != null)
			{
				read.close();
				write.close();
				status = true;
			}
			status = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public ArrayList<HashMap<String,Object>> executeSelectQuery(String sheetName,  HashMap<String,Object> whereConstraint ) throws IOException 
	{
		ArrayList<HashMap<String,Object>> resultData =  new ArrayList<HashMap<String,Object>>();
		whereConstraint.keySet();
		int	rowcount = TotalRow();
		new HashMap<String, Object>();  
		boolean selectRowValues = false;
		for(int i=0;i<rowcount-1;i++)
		{
			if(selectRowValues)
			{
				break;
			}
//			String whereColumnValue= ReadCSV(i);
			
		}
		return resultData;
		
	}	
	
	
	public Boolean writeText() throws IOException
	{
		Boolean status = true;
		write.write("\n");
		write.write("aa");
		write.write("bb");
		return status;
	}
	
	public static int count(String filename) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	     int count = 0;
	     while((bufferedReader.readLine()) != null)
	     {
	         count++;
	     }

	     System.out.println("Count : "+count);
	     return count;
	}

	
		public static long adsf() throws URISyntaxException, IOException
		{
			final Path path = Paths.get(ClassLoader.getSystemResource("sdf").toURI());
            System.out.println(Files.lines(path).skip(1L).count());
            return Files.lines(path).skip(1L).count();
		}
		
		public static int TotalRow() throws IOException
		{
            //csv file containing data
            String strFile = "C:\\Selenium\\WorkSpace\\PC\\Data\\New Text Document.csv";

            //create BufferedReader to read csv file
            BufferedReader br = new BufferedReader( new FileReader(strFile));
            int lineNumber = 0;
//            long lineCount = adsf();
//            System.out.println("Row COunt Is "+lineCount+"");
            		//read comma separated file line by line
            while( (br.readLine()) != null)
            {
                lineNumber++;
                System.out.println(lineNumber);
               
            }
			return lineNumber;
		}		
}