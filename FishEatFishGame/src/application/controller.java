package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//basic component to load the fxml file
	
	//load text file that store related info
	private File whoIsPlaying = new File("whoIsPlaying.txt");
	private File Hscore = new File("HscoreRecord.txt");
	private File uInfo = new File("LoginInfo.txt");
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label LoginStatus;
	@FXML
	private Label LoginSuccess;
	
	//this used to call and used method from other class
	highScoreUpdate callHsu = new highScoreUpdate();
	
	//when clicking the login button
	public void Login (ActionEvent e) throws IOException {
		
		Scanner myreader = new Scanner(uInfo);
		
		//this used to check whether the username and password is found and correct
		boolean found = false;
		String user = username.getText();
		String pass = password.getText();
		passwordHash enteredPw = new passwordHash();
		String nameOfUser="";
		while(myreader.hasNextLine()) {
			String data = myreader.nextLine();
			String [] info = data.split(",");
			String _user = info[1];
			//String _pass = info[2];
			String _pass = info[2];
			String salt = info[3];
			String Hpass = enteredPw.hashPassword(pass,salt); 
			if(_user.equals(user)&&_pass.equals(Hpass)) {
				nameOfUser = info[0];
				found=true;
				break;
			}
		}
		myreader.close();
		
		//username and password correct
		if(found) {
			

			Scanner csreaderHs = new Scanner (Hscore);
			String fileContent = "";
			int highScore = 0;
			int __highScore = 0;
			while(csreaderHs.hasNextLine()) {
				fileContent += csreaderHs.nextLine();
				String [] data = fileContent.split(",");
				String _highScore = data[2];
				/*found out the highscore of the current player
				 * if not found means the player didn't start game play yet
				 * so highscore = 0*/
				try {
					__highScore = Integer.parseInt(_highScore);
				}catch(NumberFormatException ee) {
				}
				
				if(nameOfUser.equals(data[0]) && user.equals(data[1])) {
					highScore = __highScore;
				}
				fileContent = "";
				//update the highscore ranking, it need to always be updated
				callHsu.Update();
			}
			csreaderHs.close();
			
			/*buffered writer is used to reduce the number of direct write operations
			 * data is go to the memory(buffer) first
			 * after that go to write in text file*/
			FileWriter writer = new FileWriter(whoIsPlaying);
			BufferedWriter tulis = new BufferedWriter(writer);
			tulis.write(nameOfUser+","+user+","+highScore+"\n");
			tulis.close();
			
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("LoginSuccess1.fxml"));
			root = loader3.load();
			//this is another controller, be aware of this in scenebuilder
			controllerLoginSuccess con = loader3.getController();
			con.displayName(nameOfUser);
			
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene (root);
			stage.setScene(scene);
			stage.show();
			
		}
		//username or password wrong
		else if(!found){
			LoginStatus.setText("Wrong password or username, pls try again.");
		}
	}
	
	//click this button to register
	public void Register (ActionEvent e) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScene1.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField comfirmPass;
	@FXML
	private TextField name;
	@FXML
	private TextField userName;
	@FXML
	private Label status;
	
	//click the register button again to register
	public void Register2(ActionEvent e)throws IOException {
		
		Scanner myreader = new Scanner(uInfo);
		
		FileWriter writer = new FileWriter(uInfo,true);
		BufferedWriter tulis = new BufferedWriter (writer);
		
		passwordHash pwH = new passwordHash();
		
		//to look whether the username is exist or not 
		boolean exist = false;
		
		String nama = name.getText();
		String user = userName.getText();
		String password = pass.getText();
		String comPass = comfirmPass.getText();
		
		/*user cannot use comma in their infomation
		 *  because it is use as delimiter*/
		boolean delimiter = false;
		//check whether user info contain commas
		String [] newInfo = new String[] {nama,user,password,comPass};
		for(String v:newInfo) {
			delimiter = comma(v);
			if(delimiter) {
				break;
			}
		}
		
		//check whether the field is empty
		if(nama.isEmpty() || user.isEmpty() || password.isEmpty() || comPass.isEmpty()) {
			status.setText("Pls complete your infomation!");
		}
		//check whether contain commas
		else if(delimiter) {
			status.setText("Pls remove commas in your register infomation!");
		}
		else {
			while(myreader.hasNextLine()) {
				String data = myreader.nextLine();
				String [] info = data.split(",");
				String _user = info[1];
				
				if(_user.equals(user)) {
					exist=true;
				}
			}
			myreader.close();
			
			//username already exist/taken by other user
			if(exist) {
				userName.setText("");
				status.setText("The username name alreadey exist, pls entered a new one.");
			}

			else {
				//register successful
				if(password.equals(comPass)) {
					try {
						String salt = pwH.getSalt();
						password = pwH.hashPassword(password,salt);
						tulis.write(nama+","+user+","+password+","+salt+"\n");
						tulis.close();
						}catch (IOException ex) {
							ex.printStackTrace();
						}
						
						status.setText("Registration successful! You can login now.");
						name.setText("");
						userName.setText("");
						pass.setText("");
						comfirmPass.setText("");
				}
				//register unsuccessful because password and confirm password is not same
				else {
					status.setText("Pls make sure that your password and comfirm password are equal");
					pass.setText("");
					comfirmPass.setText("");
				}
				//update highscore list because got a new player 
				callHsu.Update();
			}
		}
	}
	
	//this method use to check whether the info contains commas
	private boolean comma (String s) {
		
		boolean found = false;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==',') {
				found = true;
				break;
			}
		}
		return found;
	}
	
	//go back to the login scene
	public void GoBack (ActionEvent e) throws IOException{
		
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("LoginScene1.fxml"));
		root = loader2.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene (root);
		stage.setScene(scene);
		stage.show();
	}
	

}
