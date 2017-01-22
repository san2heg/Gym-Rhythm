import java.io.*;
import java.util.*;

public class main {
	ArrayList lyrics = new ArrayList();
	int songLength = 0;

	public static void main(String [] args) {
		main gr = new main();
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

	public ArrayList getIndexesOfHooks() {
		ArrayList lineIndexList = new ArrayList();

		for (int i = 0; i < lyrics.size(); i++) {
			if (lyrics.get(i).equals("[Hook]")) {
				lineIndexList.add(i);
				//System.out.println("TRUE");
			}
		}
		return lineIndexList;
	}

	public ArrayList calculateTimes(ArrayList indexes) {
		ArrayList tempList = new ArrayList();
		double secondsPerLine = (double)songLength / lyrics.size();
		//System.out.println("indexes size = " + indexes.size());
		System.out.println("secondsPerLine = " + secondsPerLine);
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
}