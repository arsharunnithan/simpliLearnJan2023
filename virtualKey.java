package lockedMe;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class virtualKey
{

public class VirtualKey
{
	
	//class instance variables
	private static String targetDirectory=null;
	private static String addFileName=null;
	private static Scanner sc;
	private static Path myFile;
	private static List<File> fileList=new ArrayList<File>();
	private static int numFilesinDirectory=0;
	static String file = null;
	static String fileName = null;
	//to be updated on future modifications
	static final int numberOfmainMenuOptions=3;
	static final int numberOfsubMenuOptions=4;
	
	//private setter methods for directory
	private static void setTargetDirectory(Scanner s)
	{
		System.out.println("Enter the target directory for the application: ");
		targetDirectory=s.next();
	}
	private static void setTargetDirectory()
	{
		targetDirectory ="D:\\JavaPrograms\\Java Learn\\Phase1Project\\Phase1ProjectDemo2\\src\\lockedMe\\Directory";
	}
	private static void determineDirectory()
	{
		try
		{
			System.out.println("Do you wish to use our default directory: y or n?");
			String Directory=sc.next();
			
			//set Target directory
			if(Directory.equalsIgnoreCase("y"))
				setTargetDirectory();
			else if(Directory.equalsIgnoreCase("n"))
				setTargetDirectory(sc);
			else
			{
				System.out.println("Please enter valid Info..");
				determineDirectory(); //Recursion
			}
		}catch(InputMismatchException e)
		{
			sc.nextLine();//clearing buffer
		}catch(Exception e){e.printStackTrace();}
	}
	//Thread interrupted exception handling
	public static void propagateException() throws InterruptedException
	{
	    Thread.sleep(2000);
//	    Thread.currentThread().interrupt();
	    if (Thread.interrupted())
	    {
	        throw new InterruptedException();
	    }
	}

	public static void main(String[] args)
	{
		openScanner();
		welcomeScreen();
		try
		{
			propagateException();
		} catch (InterruptedException e) {e.printStackTrace();}
		determineDirectory();
		mainMenu();
		closeScanner();
	}//end main
	
	//private static class methods
	private static void openScanner()
	{
		//open Scanner to read input from the console
		sc=new Scanner(System.in);
	}
	private static void closeScanner()
	{
		sc.close();
	}
	private static void welcomeScreen()
	{
		System.out.println("Company Lockers Pvt. Ltd. presents Locked Me 1.0");
		System.out.println("\n");
		System.out.println("-----------------------------");
		System.out.println("Welcome to LockedMe.com application."
				         + "\nVersion: 1.0 PROTOTYPE "
				         + "\nFull Stack Developer: Arsha R Unnithan");
		System.out.println("-----------------------------");
	}//end welcome
	private static void mainMenu()
	{
		int mainMenuChoice=0;
		do {
		String[] options= {"--------------------------------------",
				           "               Main Menu              ",
				           "--------------------------------------",
				           "1. Display the files in the directory ",
				           "2. Buisness Level Operations          ",
				           "3. Close the application              ",
				           "--------------------------------------"
		};
		//Display the main menu options
		for(int i=0;i<options.length;i++)
		{
			System.out.println(options[i]);
		}
		try
		{
			System.out.println("Please choose an option: ");
			mainMenuChoice=sc.nextInt();
			sc.nextLine();
		switch(mainMenuChoice)
		{
		case 1:
			System.out.println("Retrieving files from: "+targetDirectory+"\n");
			retrieveFiles();
			break;
		case 2:
			subMenu();
			break;
		case 3:
			System.out.println("Closing the application..\nThank You");
			System.exit(1);
		default: System.out.println("Please enter a valid option to continue");
		mainMenu();
		}
		}catch(InputMismatchException e) {
			sc.nextLine();//clears the input stream and accepts the input as a new line
		}catch(Exception e) {
			e.printStackTrace();
		}
		}while(mainMenuChoice!=numberOfmainMenuOptions);
	}//end of main menu
	
	//Retrieving files
	private static void retrieveFiles() throws IOException
	{

		int fileCount=0;
		Path dirPath=Path.of(targetDirectory);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath); //By default sorting() method sorts the list in ascending order
		if(Files.isDirectory(dirPath) && Files.exists(dirPath))
		{
			for (Path path : stream)
			{
	            if (Files.isRegularFile(path))
	            {
	                System.out.println(path.getFileName());
	                fileCount++;
	            }
	        }
		}
		
		numFilesinDirectory=fileCount;
		System.out.println("\nDirectory consists of "+numFilesinDirectory+" files.");
		
	}
	private static void subMenu()
	{
		String file = null;
		String fileName = null;
		int subMenuChoice = 0;
		
		do {
		String[] subOptions= {"---------------------------------------------",
				           "                 Business Menu               ",
				           "---------------------------------------------",
				           "1. Add a file to the existing directory list ",
				           "2. Delete a file from the directory list     ",
				           "3. Search a file from the main directory     ",
				           "4. Return to Main Directory                  ",
				           "---------------------------------------------"
		};
		//Display the sub menu options
		for(int i=0;i<subOptions.length;i++)
		{
			System.out.println(subOptions[i]);
		}
		try
		{
			System.out.println("Please choose an option: ");
			subMenuChoice=sc.nextInt();
		switch(subMenuChoice)
		{
	case 1:
			System.out.println("Please enter a file name: ");
			
			file=sc.next();
			fileName=file.trim();
			
			addFiles(targetDirectory, fileName);
			
			break;
	case 2:
			System.out.println("Enter the file name to be deleted from the following: ");
			
			//display the list of existing files
			File directory=new File(targetDirectory);
			
			String[] directoryFiles =  directory.list();
			
			Set<String> files=new TreeSet<String>();
			
			for(String names:directoryFiles)
				files.add(names);
				
			
			Iterator<String> setOfFiles=files.iterator();
			while(setOfFiles.hasNext())
				System.out.println(setOfFiles.next()); 
			
			
			file=sc.next();
			fileName=file.trim();
			
			deleteFiles(targetDirectory, fileName);
			
			break;
	case 3:
			
			System.out.println("Enter the file name to be found: ");
			
			file=sc.next();
			fileName=file.trim();
			
			searchFiles(targetDirectory, fileName);
			break;
     case 4:
			mainMenu();
			break;
		default: System.out.println("Please enter a valid option to continue");
		subMenu();
		}
		}catch(InputMismatchException e) {
			sc.nextLine();    //clears the input stream and accepts the input as a new line
		}catch(Exception e) {
			e.printStackTrace();
		}
		file = null;
		fileName = null;
		}while(subMenuChoice!=numberOfsubMenuOptions);
		
	}
	private static void addFiles(String path, String fileName) throws IOException
	{
		if (path == null || path.isEmpty() || path.isBlank())
			throw new NullPointerException("Path cannot be Empty or null");
	
		
		if (fileName == null || fileName.isEmpty() || fileName.isBlank())
			throw new NullPointerException("File Name cannot be Empty or null");
		
		
	    try{
	    	
	          File newFile = new File(path+"\\"+fileName);
	          
	          
	          if (newFile.createNewFile()) {
	             System.out.println("File " + newFile.getName()+ " created succesfully.");
	          }
	          else
	          {
	             System.out.println("File already exists.");
	          }

	     }catch (Exception e) {
	    	 System.out.println("An error occurred.");
	          e.printStackTrace();
	     }
	}
	private static void deleteFiles(String path, String fileName) throws IOException
	{
		if (path == null || path.isEmpty() || path.isBlank())
			throw new NullPointerException("Path cannot be Empty or null");
	
		
		if (fileName == null || fileName.isEmpty() || fileName.isBlank())
			throw new NullPointerException("File Name cannot be Empty or null");
		
		
	    try{
	    	
	          File newFile = new File(path+"\\"+fileName);
	          if(newFile.getName().equals(fileName) && newFile.delete())
	          {

		          System.out.println("File " + newFile.getName()+" deleted succesfully");
	          }
	          else {
	              System.out.println("Unable to delete the file. Try again \n  (Please note:Delete is case sensitive)");
	           }
	        } catch (Exception e) {
	           System.out.println("An error occurred.");
	           e.printStackTrace();
	       }
	}
	private static void searchFiles(String path, String fileName) throws IOException
	{
		if (path == null || path.isEmpty() || path.isBlank())
			throw new NullPointerException("Path cannot be Empty or null");
	
		
		if (fileName == null || fileName.isEmpty() || fileName.isBlank())
			throw new NullPointerException("File Name cannot be Empty or null");
		
	    try {
	        File newFile = new File(path);
	        File[] fileList = newFile.listFiles();
	        boolean found = false;
	        for (File file : fileList) {
	            if (file.getName().equals(fileName)) {
	                System.out.println(fileName + " found in directory");
	                found = true;
	            } else if (file.isDirectory()) {
	                searchFiles(file.getAbsolutePath(), fileName);
	            }
	        }
	        if (!found) {
	            System.out.println("File not found in directory: " + newFile + "\n\n   *Search is case sensitive*");
	            System.out.println();
	            System.out.println("Do you want to continue searching - y; any other statement will lead back to Buisness Menu");
	            String continueSearch = sc.next().toLowerCase();
	            if (continueSearch.equals("y")) {
	                System.out.println("Enter the file name: ");
	                file = sc.next();
	                fileName = file.trim();
	                searchFiles(path, fileName);
	            } else System.out.println("Returning back to Buisness Menu");
	            System.out.println();
	        }
	    } catch (InputMismatchException e) {
	        sc.nextLine();
	    } catch (Exception e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
	}
}
}
