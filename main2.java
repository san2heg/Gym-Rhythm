// Finds Hook automatically based on repeated patterns in lyrics

import java.io.*;
import java.util.*;

public class main2 {
	ArrayList<String> lyrics = new ArrayList<String>();
	int songLength = 0;

	public static void main(String [] args) {
		main2 gr = new main2();
		gr.run();
	}

	public void run() {
		readLyrics();
		ArrayList timeList = calculateTimes(getIndexesOfHooks());
		//printLyrics();
		System.out.println();
		System.out.println("Hook occurs at: ");
		for (int i = 0; i < timeList.size(); i++) {
			System.out.println(getMinuteCountFromSeconds((Integer)(timeList.get(i))-7));
		}
	}

	public void readLyrics() {
		        // The name of the file to open.
        String fileName = "lyrics.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            int i = 0;

            while((line = bufferedReader.readLine()) != null) {
            	if (i == 0) {
            		int min = Integer.parseInt(line.substring(0, line.indexOf(':')));
            		int sec = Integer.parseInt(line.substring(line.indexOf(':')+1));
            		songLength = (min*60) + sec;
            	}
            	else
            		lyrics.add(line);
            	i++;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}

	public void printLyrics() {
		for (int i = 0; i < lyrics.size(); i++) {
			System.out.println(lyrics.get(i));
		}
		System.out.println();
		System.out.println("Song Length: " + songLength);
	}

	public ArrayList calculateTimes(ArrayList indexes) {
		ArrayList tempList = new ArrayList();
		double secondsPerLine = (double)songLength / lyrics.size();
		//System.out.println("indexes size = " + indexes.size());
		//System.out.println("secondsPerLine = " + secondsPerLine);
		for (int i = 0; i < indexes.size(); i++) {
			//System.out.println(secondsPerLine * (Integer)indexes.get(i));
			tempList.add((int)(secondsPerLine * (Integer)indexes.get(i)));
		}
		return tempList;
	}

	public String getMinuteCountFromSeconds(Integer s) {
		int leftoverSeconds = s % 60;
		int minutes = (int)(s / 60);
		String str = "" + leftoverSeconds;
		if (leftoverSeconds < 10) {
			str = "" + "0" + leftoverSeconds;
		}
		return "" + minutes + ":" + str;
	}

	public ArrayList getIndexesOfHooks() {
		ArrayList lineIndexList = new ArrayList();
		int count = 0;
		for (int i = 0; i < lyrics.size()-1; i++) {
			// if the current lyrics matches the next lyric
			System.out.println("--");
			System.out.println(lyrics.get(i+1).substring(0,indexOfFirstThreeWords((lyrics.get(i+1)))) + " | i = " + indexOfFirstThreeWords((lyrics.get(i+1))));
			System.out.println(lyrics.get(i).substring(0,indexOfFirstThreeWords(lyrics.get(i))));
			System.out.println("--");
            if (lyrics.get(i+1).substring(0,indexOfFirstThreeWords((lyrics.get(i+1)))).equals(lyrics.get(i).substring(0,indexOfFirstThreeWords(lyrics.get(i)))))
				count++;
			else
				count = 0;
                
            if (count == 3) {
            	System.out.println("Line: " + (i+1));
				lineIndexList.add(i);
			}
		}
		return lineIndexList;
	}

	public int indexOfFirstThreeWords(String str){
		int words = 0;
		int i;
		//System.out.println(str);
		for (i = 0; i < str.length(); i++){
			if (str.charAt(i) == ' ')
				words++;
			if (words >= 3)
				break;
		}
		//System.out.println(i);
		return i;
	}
}
