package application;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class userCount {
	
	private File myobj = new File("LoginInfo.txt");
	private int userNum = 0;
	
	public userCount() {
			
		try {
			Scanner csreader2 = new Scanner(myobj);
				
			//count how many user
			//reading login info
			while(csreader2.hasNextLine()) {
				csreader2.nextLine();
				userNum++;
			}
			csreader2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNum() {
		return userNum;
	}

}
