package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class controllerLoginSuccess {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//basic component to load fxml file
	
	File myobj = new File("LoginInfo.txt");
	File gameHis = new File("gameHistory.txt");
	File Hscore = new File("HscoreRecord.txt");
	File whoIsPlaying = new File("whoIsPlaying.txt");
	File outfitRe = new File("outfitRecord.txt");
	File bgmCon = new File("bgmControl.txt");
	File seCon = new File("seControl.txt");
	
	//these are IMPORTANT info of the current player
	private String currentName = "";
	private String currentUserName = "";
	private int currentOutfit = 4;
	
	@FXML
	Label LoginSuccess;
	
	//these are the preparation to load the data into the table
	//required to create an object class -> player
	@FXML
	private TableView<player> table;
	@FXML
	private TableColumn<player, Integer> rankingColumn; //getRanking()
	@FXML
	private TableColumn<player, String> nameColumn; //getName()
	@FXML
	private TableColumn<player, Integer> highScoreColumn; //getHighScore()
	private ObservableList <player> leaderboardData = FXCollections.observableArrayList();
	/*observableList is for javafx, if the data change in the system, 
	 * the data in the table will also change, 
	 * the data in observable list not able to print in system, 
	 * but data in array list able*/
	
	@FXML
	private Pane gameHistoryPane;
	
	//these are the preparation to load the data into the table
		//required to create an object class -> player
	@FXML
	private TableView<gameHis> table2;
	@FXML
	private TableColumn<gameHis, Integer> numColumn; //getNum()
	@FXML
	private TableColumn<gameHis, String> dateColumn; //getDate()
	@FXML
	private TableColumn<gameHis, String> timeColumn; //getTime()
	@FXML
	private TableColumn<gameHis, String> scoreColumn; //getScore()
	@FXML
	private TableColumn<gameHis, String> levelColumn; //getLevel()
	@FXML
	private TableColumn<gameHis, String> fishColumn; //getFish()
	private ObservableList <gameHis> gameHistoryData = FXCollections.observableArrayList();
	
	@FXML
	private Button GhButton; //gameHistory button
	@FXML
	private Button OfButton; //outfit button
	
	@FXML
	private Pane outfitPane;
	@FXML
	private Button outfit1;
	@FXML
	private Button outfit2;
	@FXML
	private Button outfit3;
	@FXML
	private Button outfit4;
	@FXML
	private Button outfit5;
	
	@FXML
	private ImageView IMoutfit0;
	@FXML
	private ImageView IMoutfit1;
	@FXML
	private ImageView IMoutfit2;
	@FXML
	private ImageView IMoutfit3;
	@FXML
	private ImageView IMoutfit4;
	
	private boolean updated = false;
	private boolean preOutfitReaded = false;
	
	//to call other method from other class
	userCount callUc = new userCount();
	highScoreUpdate callHsu = new highScoreUpdate();
	
	@FXML
	private Slider sliderBGM;
	@FXML
	private Slider sliderSE;
	@FXML
	private Pane soundPane;
	@FXML
	private Button soundButton;
	
	private Media m = new Media(getClass().getResource("game bgm.mp3").toExternalForm());
	private MediaPlayer player = new MediaPlayer(m);
	
	private double preBgmVol = 0;
	private double preSeVol = 0;
	
	public void displayName (String user){
		LoginSuccess.setText("Welcome,"+user);
		gameHistoryPane.setVisible(false);
		outfitPane.setVisible(false);
		callHsu.Update();
	}
	
	//click to start the game
	public void Start (ActionEvent e) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameFish.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		callHsu.Update();
		//close all the overlay pane before the game play start
		gameHistoryPane.setVisible(false);
		outfitPane.setVisible(false);
		soundPane.setVisible(false);
		//stop playing the music
		player.stop();
	}
	
	//click to log out
	public void LogOut (ActionEvent e) throws IOException{
		
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("LoginScene1.fxml"));
		root = loader2.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene (root);
		stage.setScene(scene);
		stage.show();
		player.stop();
	}
	
	//click to show game history pane
	public void showGameHistory (ActionEvent e) throws IOException{
		
		//so that the ranking don't show twice or more than one time
		if(!updated) {
			//update the data of player game history
			UpdateGameHistory();
			updated = true;
		}
		
		gameHistoryPane.setVisible(true);
		GhButton.setDisable(true);
		
		outfitPane.setVisible(false);
		OfButton.setDisable(false);
		soundPane.setVisible(false);
		soundButton.setDisable(false);
	}
	
	//click to close the pane
	public void closeGameHistory (ActionEvent e) throws IOException{
		
		gameHistoryPane.setVisible(false);
		GhButton.setDisable(false);
	}

	//this method start to run automatically
	@FXML
	public void initialize() {
		
		//to get previous game play information
		previousInfo();
		
		/*property value should be exactly same as the variable name, 
		 * if not the column can't found the respective value/variable to set in*/
		rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
		/*
		 * 											|  ranking   |  name   |  highScore   |
		 * 											|			 |		   |			  |
		 * 											|		^	 |	^	   |  ^		      |
		 * 			table.setItems(leaderboardData)	|  		|	 |	|	   |  |		  	  |
		 * (observableList) leaderboardData.set(new player(ranking,name,highScore));
		 * */
		/*after the data is all load to observable list line by line, 
		 * then set them all to the table follow their category*/
		
		//data in txt file --> obervable list --> table
		//load data to observable list
		loadLeaderboard();
		//load observable list to the table
		table.setItems(leaderboardData);
		
		playMusic();
		//also load previous setting of sound effect, so user can adjust volume of sound effect before game start
		soundEffectControl();
	}
	
	
	
	/*get to know who is playing, name, 
	 * username and highscore of current player*/
	private void previousInfo() {
		/*file content set to be empty first
		 * later we scan and add the readed line into the file content string
		 * so that the value can be loaded out successfully*/
		String fileContent = "";
		try {
			//reader whoIsPlaying
			Scanner csreaderWip = new Scanner(whoIsPlaying);
			while(csreaderWip.hasNextLine()) {
				fileContent += csreaderWip.nextLine();
				String [] data = fileContent.split(",");
				currentName = data[0];
				currentUserName = data[1];
				fileContent = "";
			}
			csreaderWip.close();
		}catch(IOException e) {
		}
	}
	
	//this method use to load out the game history of current player when game history pane is show
	public void UpdateGameHistory() {
		
		/*Property value need to be exactly the SAME as the variable name in the gameHis class*/
		numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		fishColumn.setCellValueFactory(new PropertyValueFactory<>("fish"));
		//load the data line by line into observable list
		loadGameHistory();
		table2.setItems(gameHistoryData);
		
	}
	
	//this method use to load the value from txt file to observable list
	public void loadLeaderboard() {
		
		userCount n = new userCount();
		int userNum = n.getNum();
		//clear before every loading so that no repeated content
		leaderboardData.clear();
		
		try {
			//read hscore history
			Scanner csreader = new Scanner(Hscore);
			String fileContent = "";
			int ranking = 0;
			while(csreader.hasNextLine()) {
				fileContent += csreader.nextLine();
				String [] data = fileContent.split(",");
				String name = data[0];
				String _HScore = data[2];
				int highScore=0;
				fileContent = "";
				try {
					highScore = Integer.parseInt(_HScore);
				}catch(NumberFormatException ee) {
					ee.printStackTrace();
				}
				if(ranking<userNum) {
					ranking++;
				}
				
				//load in to the observable list
				leaderboardData.add(new player(ranking,name,highScore));
									//new line of data
			}
			csreader.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//load txt file to observable list
	public void loadGameHistory() {
		
		String fileContent = "";
		String userPlaying = "";
		int matchNum = 0;
		gameHistoryData.clear();
		
		try {
			
			//get to know who is playing
			Scanner csreaderWip = new Scanner(whoIsPlaying);
			while(csreaderWip.hasNextLine()) {
				fileContent += csreaderWip.nextLine();
				String [] data = fileContent.split(",");
				userPlaying = data[1];
				fileContent = "";
			}
			csreaderWip.close();
			
			//read gameHistory twice
			//1st time to calculate the total match of user
			//2nd time to load them into a observable list
			Scanner csreaderGh2 = new Scanner(gameHis);
			
			while(csreaderGh2.hasNextLine()) {
				fileContent += csreaderGh2.nextLine();
				String [] data = fileContent.split(",");
				if(userPlaying.equals(data[1])) {
					matchNum++;
				}
				fileContent="";
			}
			csreaderGh2.close();
			
			Scanner csreaderGh = new Scanner(gameHis);
			
			String [] _score = new String [matchNum];
			String [] _level = new String [matchNum];
			String [] _date = new String [matchNum];
			String [] _time = new String [matchNum];
			String [] _fish = new String [matchNum];
			
			int i = 0;
			while(csreaderGh.hasNextLine()) {
				fileContent += csreaderGh.nextLine();
				String [] data = fileContent.split(",");
				if(userPlaying.equals(data[1])) {
					_score[i] = data[2];
					_level[i] = data[3];
					_date[i] = data[4];
					_time[i] = data[5];
					_fish[i] = data[6];
					if(i<matchNum-1) {
						i++;
					}
				}
				fileContent="";
			}
			csreaderGh.close();
			
			for(int j=matchNum-1;j>=0;j--) {
				gameHistoryData.add(new gameHis(matchNum,_date[j],_time[j],_score[j],_level[j],_fish[j]));
				matchNum--;;
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//click to show outfit pane
	public void showOutfitPane(ActionEvent e)throws IOException {
		
		outfitPane.setVisible(true);
		Button [] outfitSelection = new Button[] {outfit1,outfit2,outfit3,outfit4,outfit5};
		
		IMoutfit0.setImage(new Image(gameController.class.getResourceAsStream("outfit0.png")));
		IMoutfit1.setImage(new Image(gameController.class.getResourceAsStream("outfit1.png")));
		IMoutfit2.setImage(new Image(gameController.class.getResourceAsStream("outfit2.png")));
		IMoutfit3.setImage(new Image(gameController.class.getResourceAsStream("outfit3.png")));
		IMoutfit4.setImage(new Image(gameController.class.getResourceAsStream("outfit4.png")));
		
		if(!preOutfitReaded) {
			previousOutfit();
			outfitSelection[currentOutfit].setDisable(true);
			preOutfitReaded = true;
		}
		
		OfButton.setDisable(true);
		gameHistoryPane.setVisible(false);
		GhButton.setDisable(false);
		soundPane.setVisible(false);
		soundButton.setDisable(false);
	}
	
	//click to close pane
	public void closeOutfitPane(ActionEvent e)throws IOException {
		outfitPane.setVisible(false);
		OfButton.setDisable(false);
		GhButton.setDisable(false);
	}
	
	//this method use to record the current outfit chosen by player
	private void selectOutfit(int num) {
		
		try {
			FileWriter writer = new FileWriter(outfitRe,true);
			BufferedWriter tulis = new BufferedWriter(writer);
			tulis.write(currentName+","+currentUserName+","+num+"\n");
			tulis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//this method used to get the latest outfit chosen by player from last game play/login
	public void previousOutfit() {
		String fileContent ="";
		
		try {
			//readGameHistory
			Scanner csreaderGh = new Scanner(outfitRe);
			while(csreaderGh.hasNextLine()) {
				fileContent += csreaderGh.nextLine();
				String [] data = fileContent.split(",");
				fileContent = "";
				if(currentName.equals(data[0]) && currentUserName.equals(data[1])) {
					try {
						currentOutfit = Integer.parseInt(data[2]);
					}catch(NumberFormatException e) {}
				}
			}
			csreaderGh.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		preOutfitReaded = true;
	}
	
	/*there are 5 buttons because there are 5 types of outfit
	 * this method will found out that which button us clicked
	 * then load the related outfit*/
	public void chooseOutfit(ActionEvent e)throws IOException {
		//get to know which button is clicked
		Button clicked = (Button) e.getSource();
		//get to know the button id, to find out which outfit to load
		String buttonId = clicked.getId();
		
		//assign all the button into a array
		Button [] outfitSelection = new Button[] {outfit1,outfit2,outfit3,outfit4,outfit5};
		for(int i=0;i<5;i++) {
			//we made all the button able to be click first
			outfitSelection[i].setDisable(false);
		}
		int of=5;
		
		//then we made the clicked button (chosen outfit) to be disable/cannot be click
		switch(buttonId) {
		case "outfit1":
			of=0;
			outfit1.setDisable(true);
			break;
		case "outfit2":
			of=1;
			outfit2.setDisable(true);
			break;
		case "outfit3":
			of=2;
			outfit3.setDisable(true);
			break;
		case "outfit4":
			of=3;
			outfit4.setDisable(true);
			break;
		case "outfit5":
			of=4;
			outfit5.setDisable(true);
			break;
		default:
			break;
		}
		//call this method to record the latest chosen outfit to the text file
		selectOutfit(of);
	}
	
	//click to show sound pane to adjust the volume
	public void showSoundPane (ActionEvent e) throws IOException{
		soundPane.setVisible(true);
		soundButton.setDisable(true);
		
		gameHistoryPane.setVisible(false);
		GhButton.setDisable(false);
		outfitPane.setVisible(false);
		OfButton.setDisable(false);
	}
	
	//click to close sound pane
	public void closeSoundPane (ActionEvent e) throws IOException{
		soundPane.setVisible(false);
		soundButton.setDisable(false);
	}
	
	private void playMusic () {
		//set to the previous volume from last game first
		preBgmVol = preSetting(bgmCon);
		sliderBGM.setValue(preBgmVol);
		player.setVolume(preBgmVol);
		//the volume will be updated once user adjust the slider 
		sliderBGM.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable,Number preV,Number newV) {
				player.setVolume(newV.doubleValue());
				try {
					//record the latest volume
					FileWriter write = new FileWriter(bgmCon,true);
					BufferedWriter tulis = new BufferedWriter(write);
					tulis.write(currentUserName+","+Double.toString(newV.doubleValue())+"\n");
					tulis.close();
				}catch(IOException e) {}
				System.out.println("music playing at vol: "+newV.doubleValue());
			}
		});
		//bgm is played repeatedly
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.play();
	}

	public void soundEffectControl() {
		/*get the previous sound effect volume and set it on the slider, 
		 * so that user can adjust the volume before the game start*/
		preSeVol = preSetting(seCon);
		sliderSE.setValue(preSeVol);
		sliderSE.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> observable,Number preV,Number newV) {
				try {
					FileWriter write = new FileWriter (seCon,true);
					BufferedWriter tulis = new BufferedWriter (write);
					tulis.write(currentUserName+","+Double.toString(newV.doubleValue())+"\n");
					tulis.close();
				}catch(IOException e) {}
			}
		});
	}
	
	//get the previous volume from the last game play/login
	public double preSetting(File f) {
		String fileContent="";
		double vol=0.5;
		try {
			Scanner scan = new Scanner (f);
			while(scan.hasNextLine()) {
				fileContent += scan.nextLine();
				String [] data = fileContent.split(",");
				if(data[0].equals(currentUserName)) {
					vol = Double.parseDouble(data[1]);
					System.out.println("previous volume:"+vol);
				}
				fileContent="";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("filenotfound");
		}
		return vol;
	}
	
}
