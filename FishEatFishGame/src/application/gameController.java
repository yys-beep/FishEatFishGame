package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class gameController implements Initializable{

	//stge, scene and root2 is use to load the fxml file
	private Stage stage;
	private Scene scene;
	private Parent root2;
	
	//this root is used for the game scene for player fish movement/other features
	@FXML
	private AnchorPane root;

	//load the image view of all types of ENEMY and PLAYER
	
	//player fish
	@FXML
	private ImageView playerFish;
	
	//level1 enemy
	@FXML
	private ImageView enemyFish;
	@FXML
	private ImageView enemyFish2;
	@FXML
	private ImageView enemyFish3;
	@FXML
	private ImageView enemyFish4;
	@FXML
	private ImageView enemyFish5;
	@FXML
	private ImageView enemyFish6;
	@FXML
	private ImageView enemyFish7;
	@FXML
	private ImageView enemyFish8;
	@FXML
	private ImageView enemyFish9;
	@FXML
	private ImageView enemyFish10;
	@FXML
	private ImageView enemyFish11;
	@FXML
	private ImageView enemyFish12;
	
	//create array to store level 1 enemy 
	@FXML
	private ImageView [] enemy;
	
	//level 2 enemy
	@FXML
	private ImageView enemy2Fish;
	@FXML
	private ImageView enemy2Fish2;
	@FXML
	private ImageView enemy2Fish3;
	@FXML
	private ImageView enemy2Fish4;
	@FXML
	private ImageView enemy2Fish5;
	@FXML
	private ImageView enemy2Fish6;
	@FXML
	private ImageView enemy2Fish7;
	@FXML
	private ImageView enemy2Fish8;
	
	//create array to store level 2 enemy
	@FXML
	private ImageView [] enemy2;
	
	//level 3 enemy
	@FXML
	private ImageView enemy3Fish;
	@FXML
	private ImageView enemy3Fish2;
	@FXML
	private ImageView enemy3Fish3;
	@FXML
	private ImageView enemy3Fish4;
	@FXML
	private ImageView enemy3Fish5;
	@FXML
	private ImageView enemy3Fish6;
	@FXML
	private ImageView enemy3Fish7;
	@FXML
	private ImageView enemy3Fish8;
	
	//create array to store level 3 enemy
	@FXML
	private ImageView [] enemy3;
	
	//level 4 enemy
	@FXML
	private ImageView enemyBoss;
	@FXML
	private ImageView enemyBoss2;
	@FXML
	private ImageView enemyBoss3;
	@FXML
	private ImageView enemyBoss4;
	@FXML
	private ImageView enemyBoss5;
	@FXML
	private ImageView enemyBoss6;
	@FXML
	private ImageView enemyBoss7;
	
	//create array to store level 4 enemy
	@FXML
	private ImageView [] enemyB;
	
	//this 2 enemy is the finel boos of the game, which the player will not ever grow bigger than it.
	@FXML
	private ImageView killingBoss;
	
	//create array to store killing enemy
	@FXML
	private ImageView [] killingB;
	
	//create array to store all the enemy
	@FXML
	private ImageView [] allEnemy;
	
	//used for the size of fish (when growing)
	double width;
	double height;
	double preH;
	
	//content displayed during the game play
	@FXML
	private Label scoreDisplay;
	@FXML
	private Label levelDisplay;
	@FXML
	private Label fishDisplay;
	@FXML
	private Label motivationScoreDisplay;
	@FXML
	private Button pauseButton;
	
	@FXML
	private Pane gameOverPane;
	//content displayed on the game over pane
	@FXML
	private Label finalScoreDisplay;
	@FXML
	private Label highScoreDisplay;
	@FXML
	private Label finalFishDisplay;
	@FXML
	private Label congratLabel;
	@FXML
	private Label finalLevel;
	
	//pause pane show when pause button is clicked
	@FXML
	private Pane pausePane;

	private File whoIsPLaying = new File ("whoIsPlaying.txt");
	private File gameHis = new File ("gameHistory.txt");
	private File outfitRe = new File ("outfitRecord.txt");
	
	//important user infomation
	private String currentName;
	private String currentUserName;
	private String Hscore;
	private int currentOutfit = 4;
	
	//boolean to track the game proccess
	private boolean gameRunning = true;
	private boolean gameEnd = false;
	private boolean level1Run = true;
	private boolean levelOne2Run= false;
	private boolean level2Run = false;
	private boolean levelTwo3Run = false;
	private boolean level3Run = false;
	private boolean levelThree4Run = false;
	private boolean level4Run = false;
	private boolean killingLevelRun = false;
	
	//detail displayed during game play
	private int score = 0 ;
	private int fish = 0;
	private int level = 1;
	
	//for the player fish movement if using arrowkey or AWSD
	private static final double STEP = 30;
	
	//for the player fish direction if using mouse to move player fish
	private double offsetX;
	private double offsetY;
	
	//to call the method from other class
	highScoreUpdate callHsu = new highScoreUpdate();
	controllerLoginSuccess callCon = new controllerLoginSuccess();
	
	//starting point and the ending point of the every enemy swim
	Random rand = new Random();
	double startY = 0;
	double endY = 0;
	
	//scale of the player fish
	double magnitude = 1; //scale is 1 in the begining of the game play
	double sy = 1;
	double sx = 1;//the scale change as the fish grow
	
	/*array list used to store the active timeline and transition
	 * why use array list ??
	 * 		because we don't know the range of the array, initialize an array need a range/length/size
	 * 		but array list don't required to initialize a size to start*/
	//if the game stop, all of them will stop together
	private List<TranslateTransition> activeTransitions = new ArrayList<>();
	private List<Timeline> activeTimeline = new ArrayList<>();
	
	//bgm is long so we used media player
	//load the background music file and create the player
	private File bgmCon = new File("bgmControl.txt");
	Media swimBgm = new Media (getClass().getResource("swimming bgm.mp3").toExternalForm());
	double preBgmVol = 0.5;
	MediaPlayer playMusic = new MediaPlayer(swimBgm);
	@FXML
	private Slider sliderBGM;
	
	//sound effect is very short only, so we use audio clip
	//load the sound effect file;
	/*
	 level up sound effect
	 collision sound effect (exist when only collide with larger fish)
	 eating sound effect*/
	private File seCon = new File("seControl.txt");
	AudioClip mpG = new AudioClip (getClass().getResource("eating sound.mp3").toExternalForm());
	AudioClip coll = new AudioClip (getClass().getResource("collision.mp3").toExternalForm());
	AudioClip lu = new AudioClip (getClass().getResource("levelUp.mp3").toExternalForm());
	double preSeVol = 0.5;
	@FXML
	private Slider sliderSE;
	//slider is use to change the volume of sound effect and bgm
	
	//to check whether previous sound effect volume is read ady or not
	boolean preVolReaded = false;
	
	
	/*automatically run method, initialize will run once we run the program(
	 	just like the public static void main (String [] args{})*/
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//to know current player info
		knowPlayer();
		//load the latest outfit from previous game
		loadOutfit();
		//get the beginning size of the player fish
		width = playerFish.getFitWidth();
		height = playerFish.getFitHeight();
		preH = playerFish.getFitHeight();
		
//		System.out.println(playerFish.getFitHeight());
//		System.out.println(playerFish.getFitWidth());
		
		//this method used to load all the enemyFish of different level into their own array
		arrayForEnemy();
		
		//load enemy IMAGE using each of their array category
		for(ImageView enemyF:enemy) {
			enemyF.setImage(new Image(gameController.class.getResourceAsStream("enemy1.png")));
			enemyF.setVisible(false);
		}
		for(ImageView enemyF:enemy2) {
			enemyF.setImage(new Image(gameController.class.getResourceAsStream("enemy2.png")));
			enemyF.setVisible(false);
		}
		for(ImageView enemyF:enemy3) {
			enemyF.setImage(new Image(gameController.class.getResourceAsStream("enemy3.png")));
			enemyF.setVisible(false);
		}
		for(ImageView enemyF:enemyB) {
			enemyF.setImage(new Image(gameController.class.getResourceAsStream("enemy4.png")));
			enemyF.setVisible(false);
		}
		for(ImageView enemyF:killingB) {
			enemyF.setImage(new Image(gameController.class.getResourceAsStream("shark.png")));
			enemyF.setVisible(false);
		}
		
		//load all the enemy fish into the big array
		//use to check collision between player and enemy
		allEnemy = new ImageView [] {
				enemyFish, enemyFish2, enemyFish3, enemyFish4, 
				enemyFish5, enemyFish6, enemyFish7, enemyFish8,
				enemyFish9, enemyFish10, enemyFish11, enemyFish12,
				enemy2Fish, enemy2Fish2, enemy2Fish3, enemy2Fish4, 
				enemy2Fish5, enemy2Fish6, enemy2Fish7, enemy2Fish8,
				enemy3Fish, enemy3Fish2, enemy3Fish3, enemy3Fish4, 
				enemy3Fish5, enemy3Fish6, enemy3Fish7, enemy3Fish8,
				enemyBoss, enemyBoss2, enemyBoss3, enemyBoss4, 
				enemyBoss5, enemyBoss6, enemyBoss7,
				killingBoss
				};
		
		//let the overlay pane to be invisible 
		gameOverPane.setVisible(false);
		pausePane.setVisible(false);
		
		//initialize those value to 0 and 1 because the game haven't start yet
		scoreDisplay.setText("0");
		fishDisplay.setText("0");
		levelDisplay.setText("1");
		//display the highscore of user
		motivationScoreDisplay.setText(Hscore);
		
		//game play start from level 1
		level1();
		
		//for character movement
		//using WASD, arrow key and mouse
		updateMag(1);
		
		//timelime for collision check
		//set a duration for the timeline, d, this will repeatly check the collision very d millis seconds
		Timeline collCheck = new Timeline(new KeyFrame(Duration.millis(10), event -> {
			if(gameRunning) { //check only when the game running
				checkColl2(allEnemy);
			}
		}));
		collCheck.setCycleCount(Animation.INDEFINITE); //INDEFINITE - timeline play until the game end
		collCheck.play();
	
		//play the background music and sound effect
		playBGM();
		soundEffect();
	}
	
	/*this method is used to play the bgm,
	 set the initial volume based on the volume adjusted in last game play
	 adjust volume
	 record the volume after adjusted */
	private void playBGM() {
		
		preBgmVol = preSetting(bgmCon);
		playMusic.setVolume(preBgmVol);
		sliderBGM.setValue(preBgmVol);
		sliderBGM.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed (ObservableValue<? extends Number>observable,Number preV,Number newV) {
				playMusic.setVolume(newV.doubleValue());
				try {
					FileWriter write = new FileWriter (bgmCon,true);
					BufferedWriter tulis = new BufferedWriter (write);
					tulis.write(currentUserName+","+Double.toString(newV.doubleValue())+"\n");
					tulis.close();
				}catch(IOException e) {}
			}
		});
		playMusic.setCycleCount(MediaPlayer.INDEFINITE);
		playMusic.play();
	}
	
	/*this method is used to adjustment of sound effect
	 * (sound effect will play by themselves based on different situation)
	 set the initial volume based on the volume adjusted in last game play
	 adjust volume
	 record the volume after adjusted */
	private void soundEffect() {
		
		if(!preVolReaded) {
			preSeVol = preSetting(seCon);
			mpG.setVolume(preSeVol);
			coll.setVolume(preSeVol);
			lu.setVolume(preSeVol);
			sliderSE.setValue(preSeVol);
			preVolReaded = false;
		}
		sliderSE.valueProperty().addListener(new ChangeListener <Number>() {
			public void changed(ObservableValue<? extends Number>observable, Number preV, Number newV) {
				mpG.setVolume(newV.doubleValue());
				coll.setVolume(newV.doubleValue());
				lu.setVolume(newV.doubleValue());
				try {
					FileWriter write = new FileWriter (seCon,true);
					BufferedWriter tulis = new BufferedWriter (write);
					tulis.write(currentUserName+","+Double.toString(newV.doubleValue())+"\n");
					tulis.close();
				}catch(IOException e) {}
			}
		});
	}
	
	/*this method used to read the previous setting/adjustment of the bgm and sound effect*/
	private double preSetting(File f) {
		
		double vol = 0.5;
		try {
			Scanner scan = new Scanner (f);
			while(scan.hasNextLine()) {
				String fileContent = "";
				fileContent+=scan.nextLine();
				String [] data = fileContent.split(",");
				if(data[0].equals(currentUserName)) {
					vol = Double.parseDouble(data[1]);
				}
			}
			scan.close();
		}catch(IOException e) {}
		return vol;
	}
	
	//image need to be paste inside the apllication file directly in the IDE
	private void loadOutfit() {
		knowPlayer();
//		System.out.println(currentOutfit);
		//each outfit has their different index number
		switch(currentOutfit) {
		case 0:
			playerFish.setImage(new Image(gameController.class.getResourceAsStream("outfit0.png")));
			break;
		case 1:
			playerFish.setImage(new Image(gameController.class.getResourceAsStream("outfit1.png")));
			break;
		case 2:
			playerFish.setImage(new Image(gameController.class.getResourceAsStream("outfit2.png")));
			break;
		case 3:
			playerFish.setImage(new Image(gameController.class.getResourceAsStream("outfit3.png")));
			break;
		case 4:
			playerFish.setImage(new Image(gameController.class.getResourceAsStream("outfit4.png")));
			break;
		default:
			break;
		}
		playerFish.setVisible(true);
	}
	
	//this method used for player movement (arrow key and WASD)
	private void keyPressed (KeyCode code) {
		
		if(!gameRunning) {
			return;
		}
		
		TranslateTransition move = new TranslateTransition(Duration.millis(1),playerFish);
		
		switch(code) {
			case UP:
			case W:
				move.setByY(-STEP);
				break;
			case DOWN:
			case S:
				move.setByY(STEP);
				break;
			case LEFT:
			case A:
				move.setByX(-STEP);
				playerFish.setScaleX(-magnitude);
				break;
			case RIGHT:
			case D:
				move.setByX(STEP);
				playerFish.setScaleX(magnitude);
				break;
			default:
				break;
		}
		
		move.play();
		checkColl2(allEnemy);
	}
		
	//this method is used for player movement using mouse
	private void mousePressed (MouseEvent event) {
		if(!gameRunning) {
			return;
		}
		offsetX = event.getSceneX() - playerFish.getX();
		offsetY = event.getSceneY() - playerFish.getY();
		
		//used to set the direction of the player character
		if(playerFish.getX() <0) {
			playerFish.setScaleX(-magnitude);
		}
		else {
			playerFish.setScaleX(magnitude);
		}
		checkColl2(allEnemy);
	}
		
	private void mouseDragged (MouseEvent event) {
		
		if(!gameRunning) {
			return;
		}
		else {
			//used to set the direction of the player character
			if(playerFish.getX() <0) {
				playerFish.setScaleX(-magnitude);
			}
			else {
				playerFish.setScaleX(magnitude);
			}
			
			playerFish.setX(event.getSceneX() - offsetX);
			playerFish.setY(event.getSceneY() - offsetY);
			checkColl2(allEnemy);
		}
			
	}
	
	//this method used to set the player character to be in the middle of the scene using 2 right click
	private void mouse2click (MouseEvent event) {
		if(!gameRunning) {
			return;
		}
		else { //call the fish back to the previous location (only for using mouse clicked)
			if(event.getClickCount() == 2) {
				playerFish.setX(root.getWidth()/64);
				playerFish.setY(root.getHeight()/64);
			}
			checkColl2(allEnemy);
		}
			
	}
	
	//this method contain all the movement method and the magnitude of the character will be updated once level up
	private void updateMag(double x) {
		magnitude = x;
		root.setFocusTraversable(true);
		root.setOnKeyPressed(event -> {
			keyPressed(event.getCode());
		});
		root.setOnKeyReleased(event -> {
			keyPressed(event.getCode());
		});
		playerFish.setOnMousePressed(event -> mousePressed(event));
		playerFish.setOnMouseDragged(event -> mouseDragged(event));
		root.setOnMouseClicked(event -> mouse2click(event));
		
		// request focus after a slight delay 
		Platform.runLater(() -> { 
			root.requestFocus(); 
			});
	}
	
	//use to group the enemy into each of their array
	private void arrayForEnemy() {
		enemy = new ImageView [] {
				enemyFish, enemyFish2, enemyFish3, enemyFish4, 
				enemyFish5, enemyFish6, enemyFish7, enemyFish8,
				enemyFish9, enemyFish10, enemyFish11, enemyFish12
				};
		
		enemy2 = new ImageView [] {
				enemy2Fish, enemy2Fish2, enemy2Fish3, enemy2Fish4, 
				enemy2Fish5, enemy2Fish6, enemy2Fish7, enemy2Fish8
				};
		
		enemy3 = new ImageView [] {
				enemy3Fish, enemy3Fish2, enemy3Fish3, enemy3Fish4, 
				enemy3Fish5, enemy3Fish6, enemy3Fish7, enemy3Fish8
				};
		
		enemyB = new ImageView [] {
				enemyBoss, enemyBoss2, enemyBoss3, enemyBoss4, 
				enemyBoss5, enemyBoss6, enemyBoss7
				};
		killingB = new ImageView [] {
				killingBoss
				};
	}
	
	//those method is used to let the enemy fish swim, they will swim once their boolean become true
	//for each level and half of the level, their enemy will swim at different time
	//since that there are different types and sizes of enemy, so their swimming conditions is different
	
	private void level1() {
		Random rand = new Random();
		double [] sec = new double[12];
		for(int i=0;i<4;i++) {
			sec[i] = rand.nextDouble(0,0.2);
			enemySwim(enemy[i], 
					Duration.seconds(sec[i]),sec[i], 6, 
					level1Run,1600,200,100,-200);
		}
		for(int i=4;i<12;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemy[i], 
					Duration.seconds(sec[i]),sec[i], 6, 
					level1Run,1600,200,100,-200);
		}
		
	}
	
	private void levelOne2() {
		Random rand = new Random();
		double [] sec = new double[4];
		for(int i=0;i<4;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemy2[i], 
					Duration.seconds(sec[i]),sec[i], 4.5, 
					levelOne2Run,1700,200,0,-400);
		}
	}
	
	private void level2() {
		Random rand = new Random();
		double [] sec = new double[8];
		for(int i=4;i<8;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemy2[i], 
					Duration.seconds(sec[i]),sec[i], 4.5, 
					level2Run,1700,200,0,-400);
		}
	}
	
	private void levelTwo3() {
		Random rand = new Random();
		double [] sec = new double[8];
		for(int i=0;i<4;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemy3[i], 
					Duration.seconds(sec[i]),sec[i], 3, 
					levelTwo3Run,2100,300,0,-400);
		}
	}
	
	private void level3() {
		Random rand = new Random();
		double [] sec = new double[8];
		for(int i=4;i<8;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemy3[i], 
					Duration.seconds(sec[i]),sec[i], 3, 
					level3Run,2100,300,0,-400);
		}
	}

	private void levelThree4() {
		Random rand = new Random();
		double [] sec = new double[8];
		for(int i=0;i<4;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemyB[i], 
					Duration.seconds(sec[i]),sec[i], 2, 
					levelThree4Run,2500,250,0,-500);	
		}
	}
	
	private void level4() {
		Random rand = new Random();
		double [] sec = new double[8];
		for(int i=4;i<7;i++) {
			sec[i] = rand.nextDouble(0,2);
			enemySwim(enemyB[i], 
					Duration.seconds(sec[i]),sec[i], 2, 
					level4Run,2500,250,0,-500);	
		}
	}
	
	private void killingLevel() {
		//Random rand = new Random();
		//double [] sec = new double[2];
		//for(int i=0;i<2;i++) {
			//sec[i] = rand.nextDouble(1,5);
			enemySwim(killingBoss,
					Duration.seconds(5),5, 1,
					killingLevelRun,3500,350,0,-500);
		//}
	}
	
	private void enemySwim (ImageView enemyF, 
			Duration delay, double s, double ori, 
			boolean run, double d, double x, int max, int min) {

		Random rand = new Random();
		
		TranslateTransition translate = new TranslateTransition(Duration.seconds(ori),enemyF);
		activeTransitions.add(translate);
		
		enemyF.setLayoutX(-x);
		enemyF.setVisible(true);
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(ori+s),event -> {
		
			if(!gameRunning || !run) {
				enemyF.setVisible(false);
				translate.pause();
				return;
			}
			else if(run){
			
				int direct;
				double startX;
				double distance;
				if(rand.nextInt(1,3)==1) {
					direct = 1;
					startX = -x;
					distance = d;
				}
				else {
					direct = -1;
					startX = 1150-(-x);
					distance = -d;
				}
				enemyF.setVisible(true);
				double startY = rand.nextDouble(min,max);
				double endY = rand.nextDouble(min,max);
				enemyF.setY(startY);
				enemyF.setScaleX(direct);
				translate.setFromY(startY);
				translate.setToY(endY);
				translate.setCycleCount(1);
				translate.setFromX(startX);
				translate.setByX(distance);
				translate.setDelay(delay);
				translate.playFromStart();
				
			}
			
			}));
		activeTimeline.add(tl);
		
		tl.setCycleCount(Animation.INDEFINITE);
		if(!gameRunning || !run) {
			tl.pause();
		}
		else if(run) {
			tl.play();
		}
	}
	
	//the game pause
	private void pauseGame() {
		//all the enemy fish will stop swimming and remain at their current position
		for(TranslateTransition trans : activeTransitions) {
			trans.pause();
		}
		for(Timeline line : activeTimeline) {
			line.pause();
		}
		playMusic.pause();
		gameRunning = false;
	}
	
	//the game resume
	private void resumeGame() {
		//all the enemy continue to swim again
		for(TranslateTransition trans : activeTransitions) {
			trans.play();
		}
		for(Timeline line : activeTimeline) {
			line.play();
		}
		playMusic.play();
		gameRunning = true;
	}
	
	//check collision between playerFish and enemyFish
	private void checkColl2 (ImageView [] iv) {
		Bounds sceneBounds = new BoundingBox(0, 0, 1000, 700); 
		// bounds of the scene, to determine whether the enemyFish is swimming on the screen
		for(ImageView enemyF:allEnemy) {
			Bounds enemyBound = enemyF.getBoundsInParent();
			/*after they collide, we need to found out the level of enemy fish, 
			 * each level enemy contains different score value*/
			if (sceneBounds.intersects(enemyBound)) {
				if(playerFish.getBoundsInParent().intersects(enemyBound)) {
					if(isColliding(playerFish,enemyF)) {
						double index = 0;
						for(ImageView one:enemy) {
							if(enemyF == one) {
								index = 0.1;
							}
						}
						for(ImageView two:enemy2) {
							if(enemyF == two) {
								index = 0.2;
							}
						}
						for(ImageView three:enemy3) {
							if(enemyF == three) {
								index = 0.3;
							}
						}
						for(ImageView four:enemyB) {
							if(enemyF == four) {
								index = 0.4;
							}
						}
						
						handleColl(enemyF,index);
						//call this method to proceed to the next step after collision
					}
				}
			}
		}
	}
	
	// check collision by seeing whether the intesect part is transparent or not
	public boolean isColliding(ImageView player, ImageView enemy) {
		
		//get the local bounds and convert to scene coodinates
		Bounds playerBounds = player.localToScene(player.getBoundsInLocal()); 
		Bounds enemyBounds = enemy.localToScene(enemy.getBoundsInLocal());
		
		//convert ImageView to image
		Image playerImage = player.getImage();
		Image enemyImage = enemy.getImage();
		
		//get pixel data which use to determine the overlap area and collision
		PixelReader playerReader = playerImage.getPixelReader(); 
		PixelReader enemyReader = enemyImage.getPixelReader(); 
		
		int width = (int) playerImage.getWidth(); 
		int height = (int) playerImage.getHeight();
		int enemyWidth = (int) enemyImage.getWidth(); 
		int enemyHeight = (int) enemyImage.getHeight();
		
		double overlapX1 = Math.max(playerBounds.getMinX(), enemyBounds.getMinX()); // get the length of left boundary of the overlap are
		double overlapY1 = Math.max(playerBounds.getMinY(), enemyBounds.getMinY()); //top boundary
		double overlapX2 = Math.min(playerBounds.getMaxX(), enemyBounds.getMaxX()); //right boundary
		double overlapY2 = Math.min(playerBounds.getMaxY(), enemyBounds.getMaxY()); //bottom boundary
		
		if (overlapX1 < overlapX2 && overlapY1 < overlapY2) { 
			for (int y = 0; y < enemyHeight; y++) { 
				for (int x = 0; x < enemyWidth; x++) { 
					//calculate out the coordinate that we need to check whether they are transparent
					double playerPixelX = overlapX1 - playerBounds.getMinX() + x; 
					double playerPixelY = overlapY1 - playerBounds.getMinY() + y; 
					double enemyPixelX = playerPixelX + playerBounds.getMinX() - enemyBounds.getMinX(); 
					double enemyPixelY = playerPixelY + playerBounds.getMinY() - enemyBounds.getMinY();
					
					//if both enemyPixel and playerPixel are overlap and non transparent, means they collide
					if (enemyPixelX >= 0 && enemyPixelY >= 0 && enemyPixelX < enemyWidth && enemyPixelY < enemyHeight) { 
						if (!isPixelTransparent((int)playerPixelX, (int)playerPixelY, playerReader, width, height) && 
								!isPixelTransparent((int)enemyPixelX, (int)enemyPixelY, enemyReader, enemyWidth, enemyHeight)) { 
							return true; 
						} 
					} 
				} 
			}
		}
		return false; 
	} 
	
	//use to check whether the pixel is transparent or not
	public boolean isPixelTransparent(int x, int y, PixelReader pixelReader, int width, int height) { 
	
		if (x < 0 || y < 0 || x >= width || y >= height) { 
			return true; // Treat out-of-bounds pixels as transparent 
		} 
		boolean transparent = pixelReader.getColor(x, y).getOpacity() == 0.0; 
		return transparent;
	}

	private void handleColl(ImageView enemyF, double num) {
		if(!enemyF.isVisible()) {
			return;
		}
		
		//we compare the height of enemy and player fish only
		//if enemy is smaller than or equal to player, game continue
		if(enemyF.getFitHeight() <= height) {
			gameRunning = true;
			enemyF.setVisible(false);
			mpG.play(); //play the eating sound effect
			//the size of the player grow secretly,the player will have significant grow once level up
//			width += num*2;
//			height += num*2;
			scoreSystem(num*4);
		}
		else {
			//play the collision sound effect if enemy fish larger than player fish
			coll.play();
			gameOver(enemyF);
		}
		
		System.out.println("Handling collision with " + enemyF.getId());
//		System.out.println(score);
//		System.out.println(width);
//		System.out.println(height);
		
	}
	
	//this method used to perform the grow of player and sound effect of level up
	private void levelUp(double preHeight) {
		ScaleTransition enlarge = new ScaleTransition(Duration.seconds(2),playerFish); 
		sy *= (height/preHeight); 
		sx *= (height/preHeight);
		System.out.println(height+" "+preHeight);
		enlarge.setToX(sx);
		enlarge.setToY(sy);
		enlarge.setAutoReverse(false);
		System.out.println("x:"+sx+" y:"+sy); 
		enlarge.setCycleCount(1); 
		enlarge.play();
		enlarge.setOnFinished(event->{
			playerFish.setFitHeight(height);
		});
		lu.play(); //play level up sound effect
		updateMag(sx); //update the magnitude of the player, because the scale change once the player level up and grow
	}
	
	private void scoreSystem(double num) {
		score += num*100;
		fish++;
		
		//this used to control the game process, as a indicator to let the next level enemy swim
		if (score>1000 && !levelOne2Run) {
			levelOne2Run = true;
			levelOne2();
		}
		if (score>2000 && !level2Run) {
			level2Run = true;
			level2();
			level = 2;
			height = 58;
			levelUp(preH);
			preH = height;
		}
		if (score>3500 && !levelTwo3Run) {
			levelTwo3Run = true;
			levelTwo3();
		}
		if (score>5000 && !level3Run) {
			level3Run = true;
			level3();
			level = 3;
			height = 86;
			levelUp(preH);
			preH = height;
		}
		if (score>7000 && !levelThree4Run) {
			levelThree4Run = true;
			levelThree4();
		}
		if (score>9000 && !level4Run) {
			level4Run = true;
			level4();
			level = 4;
			height = 112;
			levelUp(preH);
			preH = height;
		}
		if(score>11000 && !killingLevelRun) {
			killingLevelRun = true;
			killingLevel();
		}
		
		knowPlayer();
		//repeatly update the important value that display
		scoreDisplay.setText(Integer.toString(score));
		fishDisplay.setText(Integer.toString(fish));
		levelDisplay.setText(Integer.toString(level));
		motivationScoreDisplay.setText(Hscore);
	}
	
	//this method used to perform the situation of player fish when game over
	private void fishDied(ImageView playerF) {
		ColorAdjust grayscale = new ColorAdjust();
		grayscale.setSaturation(-1);
		playerF.setEffect(grayscale);
		playerF.setScaleY(-magnitude);
	}

	/*this method use to perform the action of game over
	 * show game over pane
	 * disable pause button
	 * display final result*/
	public void gameOver(ImageView enemyF) {
		
		//game stop running, all the transition will stop
		gameRunning = false;
		pauseGame();
		
		//show game over pane
		gameOverPane.setVisible(true);
		gameEnd = true;
		
		if (pauseButton != null) { 
			pauseButton.setDisable(true); 
		}
		
		//record the game result into the game history file
		gameHistoryRecord(score,level,fish);
		//perform fish died situation
		fishDied(playerFish);
		
//		System.out.println("game over because collide with "+enemyF.getId());
		// display the final value of each category
		finalScoreDisplay.setText(Integer.toString(score));
		highScoreDisplay.setText("Highscore : "+Hscore);
		finalFishDisplay.setText("You've eaten " + fish + " fish !");
		finalLevel.setText("LEVEL "+level);
		
	}
	
	/*this method is used for when user exit the game in the middle of game play, 
	 * instead of collide with larger enemy*/
	private void gameOverWithExitGame() {
		gameRunning = false;
		gameOverPane.setVisible(true);
		gameEnd = true;
		finalScoreDisplay.setText(Integer.toString(score));
		gameHistoryRecord(score,level,fish);
//		highScoreDisplay.setText(Hscore);
	}
	
	/*to know the current player infomation
	 * name
	 * username
	 * highscore
	 * outfit*/
	private void knowPlayer() {
		
		try {
			Scanner csreader = new Scanner (whoIsPLaying);
			String fileContent = "";
			while(csreader.hasNextLine()) {
				fileContent += csreader.nextLine();
				String [] data = fileContent.split(",");
				currentName = data[0];
				currentUserName = data[1];
				Hscore = data[2];

				fileContent = "";
			}
			csreader.close();
			
			Scanner csreaderOr = new Scanner (outfitRe);
			while(csreaderOr.hasNextLine()) {
				fileContent += csreaderOr.nextLine();
				String [] data = fileContent.split(",");
				if(currentName.equals(data[0]) && currentUserName.equals(data[1])) {
					try {
						currentOutfit = Integer.parseInt(data[2]);
					}catch(NumberFormatException e) {}
				}
				fileContent = "";
			}
			csreaderOr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*this method used to record the new highscore
	 * IF THE FINAL SCORE IS HIGHER THAN USER HIGHSCORE*/
	private void updateHscoreWhoIsPlaying() {
		try {
			FileWriter writer = new FileWriter(whoIsPLaying);
			BufferedWriter tulis = new BufferedWriter(writer);
			tulis.write(currentName+","+currentUserName+","+Hscore);
			tulis.close();
		} catch (IOException e) {
			e.printStackTrace();
			//used to know where's the error of code
		}
	}
	
	//this method used to record the game result to a txt file
	private void gameHistoryRecord (int finalScore, int level, int fish) {
		
		//get current time and date
		LocalDateTime now = LocalDateTime.now();
		//format date and time "date,time"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss");
		//generate a string that contain date and time in the correct format
		String dateTime = now.format(formatter);
		
		knowPlayer();
		
		try {
			
			FileWriter writer = new FileWriter(gameHis,true);
			BufferedWriter tulis = new BufferedWriter(writer);
			
			tulis.write(currentName+","+currentUserName+","+finalScore+","+level+","+dateTime+","+fish);
			tulis.newLine();
			tulis.close();
			
			if(Integer.parseInt(Hscore) < finalScore) {
				//set the highscore label to the new highscore
				Hscore = Integer.toString(finalScore);
				//display a congrat message to the user
				congratLabel.setVisible(true);
				//update the highscore of current player
				updateHscoreWhoIsPlaying();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
	
	//method for pause button
	public void pause (ActionEvent e) throws IOException {
		
		pauseGame();
		pausePane.setVisible(true);
		gameRunning = false;
	}
	
	//method for resume button
	public void resume (ActionEvent e) throws IOException{
		
		resumeGame();
		pausePane.setVisible(false);
		gameRunning = true;
	}
	
	//method for try again button
	public void Restart (ActionEvent e) throws IOException {
		
		FXMLLoader loader2 = new FXMLLoader (getClass().getResource("gameFish.fxml"));
		root2 = loader2.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();
	}
	
	//method for back to main menu
	public void MainMenu (ActionEvent e) throws IOException {
		
		if(gameEnd) {
			gameEnd = true;
		}
		else {
			gameEnd = true;
			gameOverWithExitGame();
		}
		
		FXMLLoader loader2 = new FXMLLoader (getClass().getResource("LoginSuccess1.fxml"));
		root2 = loader2.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene (root2);
		stage.setScene(scene);
		stage.show();
		controllerLoginSuccess con = loader2.getController();
		con.displayName(currentName);
		con.initialize();
		callHsu.Update();
		con.UpdateGameHistory();
	}
	
	
}