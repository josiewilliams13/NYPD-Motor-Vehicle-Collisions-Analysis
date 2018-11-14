package project5;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CollisionInfo {
	public static void main (String[] args) throws IOException{
		String quit = "quit";
		String zip = "";
		Date date1;
		Date date2;
		
		//Use try block to catch that the file is valid from command line
		try {
			Scanner file = new Scanner(new File(args[0]));
			Scanner input = new Scanner(System.in);
			
			//create new CollisionData object to store elements
			CollisionsData collisions = new CollisionsData();
			
			//read each line in file and store in new collision object
			while (file.hasNextLine()) {				

			try {	
				Collision singleCollision = new Collision (splitCSVLine(file.nextLine()));
				collisions.add(singleCollision);
				
			}catch (IllegalArgumentException ex) {
				continue;
			}
		}
			
			do {
				try {
					System.out.print("Please enter a zip code ('quit' to exit): ");
					String tempZip = input.next();
					if (tempZip.equals(quit)) {
						break;
					}
					
					//zipcode check
					if (isValidZip(tempZip))
						zip = tempZip;
					else
						throw new IllegalArgumentException();
					
					//date validity is checked inside of Date class
					System.out.print("Please enter the start date: ");					
					date1 = new Date(input.next());
					
					System.out.print("Please enter the end date: ");
					date2 = new Date (input.next());
					
					collisions.getReport(zip, date1, date2);
					
				} catch (IllegalArgumentException ex) { 
						System.err.println("This is not a valid zip code.");
				}
				
			} while (!(zip.equals(quit)));
			
		} catch (FileNotFoundException | IllegalArgumentException ex) {
			if (args[0].isEmpty()) {
				System.err.println ("Usage Error: The program expects file name as an argument.\n");
				System.err.flush();
			}
			else {
				System.err.println ("Error: The file cannot be opened.\n");
				System.err.flush();
			}
		}
	}
	
	//Zipcode validity check. 
	private static boolean isValidZip (String zipcode) {
		if (zipcode.length() > 5 || zipcode.length() < 5)
			return false;
		for (int i = 0; i < zipcode.length(); i++) {
			if (!(Character.isDigit(zipcode.charAt(i))))
				return false;
		}
		return true;
	}

	// Date validity check. Unnecessary because of Date class.
	private static boolean isValidDate(String date){	
		String month = date.substring(0,2);
		String day = date.substring(3,5);
		String year = date.substring(6, date.length());
		
		if (month.length() > 2 || day.length() > 2 || month.length() < 2 || day.length() < 2)
			return false;
		
		if (year.length() > 4 || year.length() < 4)
			return false;
		
		int monthNum = Integer.parseInt(month);
		int dayNum = Integer.parseInt(day);
		int yearNum = Integer.parseInt(year);
		
		if (monthNum > 31 || monthNum < 1)
			return false;
		if (dayNum > 31 || dayNum < 1)
			return false;
		if (yearNum > 2020 || yearNum < 1990)
			return false;
		
		return true;
	}

/**
 * Splits the given line of a CSV file according to commas and double quotes
 * (double quotes are used to surround multi-word entries so that they may contain commas)
 * @author Joanna Klukowska
 * @param textLine	a line of text to be passed
 * @return an Arraylist object containing all individual entries found on that line
 */

	public static ArrayList<String> splitCSVLine(String textLine){
	
		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if(nextChar == '"' || nextChar == '\u201c' || nextChar == '\u201D') {					
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} 
				else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
				// add it to the current entry 
					nextWord.append( nextChar );
				}else { // skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar); 
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 
			
		}
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
	
		return entries;
		}
	}


