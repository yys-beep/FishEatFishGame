package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class highScoreUpdate {

	private File myobj = new File("LoginInfo.txt");
	private File gameHis = new File("gameHistory.txt");
	
	public void Update(){
		
		userCount n = new userCount();
		int userNum = n.getNum();
		
		try {
			Scanner csreader = new Scanner(myobj);
			
			Scanner csreaderGh = new Scanner(gameHis);
			
			//assign name and username to the array
			//reading login info
			// split using dash
			String fileContent = "";
			String [] userName = new String [userNum];
			String [] name = new String [userNum];
			int i = 0;
			while(csreader.hasNextLine()) {
				fileContent += csreader.nextLine();
				String [] data = fileContent.split(",");
				name[i] = data[0];
				userName[i] = data[1];
				if(i<userNum-1) {
					i++;
				}
				fileContent="";
			}
			csreader.close();
			
			//initialize all the highscore as 0
			int [] highScore = new int [userNum];
			for(int j=0;j<userNum;j++) {
				highScore[j]=0;
			}
			
			//search for highScore for each person
			//reading game history
			int Hscore = 0;
			fileContent = "";
			while(csreaderGh.hasNextLine()) {
				fileContent += csreaderGh.nextLine();
				String [] info = fileContent.split(",");
				String _name = info[0];
				String _userName = info[1];
				String _Hscore = info[2];
				
				try {
					Hscore = Integer.parseInt(_Hscore);
				}catch(NumberFormatException eee ) {
					
				}
				
				for(int j=0;j<userNum;j++) {
					if(name[j].equals(_name) && userName[j].equals(_userName)) {
						if(Hscore>highScore[j]) {
							highScore[j] = Hscore;
						}
					}
				}
				
				fileContent = "";
			}
			csreaderGh.close();
			
			for(int j=0;j<userNum;j++) {
				for(int k=0;k<userNum;k++) {
					if(highScore[j]>highScore[k]) {
						
						int hold = highScore[j];
						highScore[j] = highScore[k];
						highScore[k] = hold;
						
						String temp1 = name[j];
						name[j] = name[k];
						name[k] = temp1;
						
						String temp2 = userName[j];
						userName[j] = userName[k];
						userName[k] = temp2;
					}
				}
			}
			
			FileWriter writer = new FileWriter ("HscoreRecord.txt");
			BufferedWriter tulis = new BufferedWriter (writer);
			
			for(int j=0;j<userNum;j++) {
				tulis.write(name[j]+","+userName[j]+","+highScore[j]+"\n");
			}
			tulis.close();
			
		}catch (IOException ee) {
			ee.printStackTrace();
		}
		
		System.out.println("leaderboard is uptodate");
	}
}
