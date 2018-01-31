package application;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;

import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Menu;

public class MainMenuController {
	
	ObservableList list = FXCollections.observableArrayList();
	
	ObservableList RotorPositions = FXCollections.observableArrayList();
	
	ObservableList RotorStarts = FXCollections.observableArrayList();
	
	@FXML
	private Menu RunBtn;
	@FXML
	private Menu SettingBtn;
	@FXML
	private Menu HelpBtn;
	@FXML
	private TextArea CypherTextArea;
	@FXML
	private TextArea PlainTextArea;
	@FXML
	private ChoiceBox WireA1;
	@FXML
	private ChoiceBox WireB1;
	@FXML
	private ChoiceBox WireA2;
	@FXML
	private ChoiceBox WireB2;
	@FXML
	private ChoiceBox WireC1;
	@FXML
	private ChoiceBox WireC2;
	@FXML
	private ChoiceBox WireD1;
	@FXML
	private ChoiceBox WireD2;
	@FXML
	private ChoiceBox WireE1;
	@FXML
	private ChoiceBox WireE2;
	@FXML
	private ChoiceBox WireF1;
	@FXML
	private ChoiceBox WireF2;
	@FXML
	private ChoiceBox WireG1;
	@FXML
	private ChoiceBox WireG2;
	@FXML
	private ChoiceBox WireH2;
	@FXML
	private ChoiceBox WireH1;
	@FXML
	private ChoiceBox WireI1;
	@FXML
	private ChoiceBox WireI2;
	@FXML
	private ChoiceBox WireJ2;
	@FXML
	private ChoiceBox WireJ1;
	@FXML
	private ChoiceBox Rotor1Pos;
	@FXML
	private ChoiceBox Rotor2Pos;
	@FXML
	private ChoiceBox Rotor3Pos;
	@FXML
	private ChoiceBox Rotor4Pos;
	@FXML
	private ChoiceBox Rotor5Pos;
	@FXML
	private ChoiceBox Rotor1Start;
	@FXML
	private ChoiceBox Rotor2Start;
	@FXML
	private ChoiceBox Rotor3Start;
	@FXML
	private ChoiceBox Rotor4Start;
	@FXML
	private ChoiceBox Rotor5Start;
	
	
	
	
	public void initialize() {
		LoadPlugBoard();
		LoadRotors();
	}
	
	public void RunEnigma(ActionEvent event) {
		String plainText = PlainTextArea.getText();
		plainText = plainText.toUpperCase();
		
		String cypherText;
		char[] cPlainText;
		
		
		//System.out.println(plainText);
		
		//break string into Char
		cPlainText = SplitToChars(plainText); //this method will return a Char[] to be used by the cypher method
		
		//call to a method that will cypher the Char array into a cipher string
		cypherText = EnigmaCypher(cPlainText);
		
		CypherTextArea.setText(cypherText); //will set text area to cipher text area
	}
	
	
	//******************************************************
	//******************************************************
	//******************************************************
	//This method will receive an array of 
	private String EnigmaCypher(char[] cPlainText) {
		
		int rotations = 1;
		
		//##############
		char[] plugBoardConfig = {(char) WireA1.getValue(),(char) WireA2.getValue(),
								  (char) WireB1.getValue(),(char) WireB2.getValue(),
								  (char) WireC1.getValue(),(char) WireC2.getValue(),
								  (char) WireD1.getValue(),(char) WireD2.getValue(),
								  (char) WireE1.getValue(),(char) WireE2.getValue(),
								  (char) WireF1.getValue(),(char) WireF2.getValue(),
								  (char) WireG1.getValue(),(char) WireG2.getValue(),
								  (char) WireH1.getValue(),(char) WireH2.getValue(),
								  (char) WireI1.getValue(),(char) WireI2.getValue(),
								  (char) WireJ1.getValue(),(char) WireJ2.getValue()};
		//this array will store each choice box in order of wheel 1-5
		int[] rotorOrder = {(int) Rotor1Pos.getValue(), (int)Rotor1Start.getValue(),
				(int) Rotor2Pos.getValue(),(int)Rotor2Start.getValue(),
				(int) Rotor3Pos.getValue(),(int)Rotor3Start.getValue(),
				(int) Rotor4Pos.getValue(),(int)Rotor4Start.getValue(),
				(int) Rotor5Pos.getValue(),(int)Rotor5Start.getValue()};
		//##############
		//This block of code will retrieve the values selected in the choice boxes and then create a new rotor object depending on selected options
		int[] chosenInnerRotor = getConfig(1,rotorOrder);
		EnigmaRotor innerRotor = new EnigmaRotor(chosenInnerRotor);
		int[] chosenMiddleRotor = getConfig(2,rotorOrder);
		EnigmaRotor middleRotor = new EnigmaRotor(chosenMiddleRotor);
		int[] chosenOuterRotor = getConfig(3,rotorOrder);
		EnigmaRotor outerRotor = new EnigmaRotor(chosenOuterRotor);
		
		
		//This loop will change Char to another char according to the wheels
		for (int i = 0; i < cPlainText.length; i++) {
			//System.out.println(cPlainText[i]);
			char c = cPlainText[i];
			
		
			//Validates that the character being based is on the wheel to be ciphered 
			if (Character.toString(c).matches("[A-Z ]")) {
			
				//PASSES THE CHAR THROUGH THE PLUGBOARD WIRES
				c =  PlugBoardSwitch(plugBoardConfig, c);
				
				int index = innerRotor.ReturnIndexOfCharacter(c); //Inner to outer
			   
			    c = outerRotor.ReturnCharacterAtIndex(index); //Gets outer character
			    System.out.println(c);
			    index = middleRotor.ReturnIndexOfCharacter(c); //Middle to outer
			   
			    c = outerRotor.ReturnCharacterAtIndex(index); //Gets final character
			    
			    //PASSES THE CHAR THROUGH THE PLUGBOARD WIRES
			    c =  PlugBoardSwitch(plugBoardConfig, c);	
			    
			    cPlainText[i] = c;
		    
		    	//Rotation of the wheel logic
			    innerRotor.rotateWheel();
		    	rotations += 1;
		    	//28 is the number of characters that need to be passed before the middle wheel is rotated 
		    	if(rotations % 29 == 0) {
		    		 middleRotor.rotateWheel();		    		 
		    	 }
		    	//784 is the number of characters that need to be passed before the outer wheel is rotated once
		    	 if(rotations % 785 == 0) {
		    		 outerRotor.rotateWheel();
		    	 }
			}
		}
		
		
		String cypherText = new String(cPlainText);
		//System.out.println(cypherText);
		return cypherText;
	}
	//******************************************************
	//******************************************************
	//******************************************************
	//This method will capture the user inputed plain text and split it and return an array list of the individual characters 
	private char[] SplitToChars(String text) {
		String plainText = text;
		char[] splitText = plainText.toCharArray();
				
		/*for(int i = 0; i < splitText.length; i++) {
			System.out.println(splitText[i]);
		}*/
		
		return splitText;
	}
	//******************************************************
	//******************************************************
	//******************************************************
	
	//Utility method
	public int[] getConfig( int toSearch, int[] tab )
	{
		int[] config = null;
	  for( int i=0; i< tab.length ; i ++ )
	    if( tab[ i ] == toSearch && i%2 == 0) {
	    	config = new int[] {i, tab[i+1]};
	  		//System.out.println(config[0] + " " + config[1]);
	    }
	     return config ;
	}//met
	
	private char PlugBoardSwitch(char[] config,char c) {
		
		int index = new String(config).indexOf(c);
		
		if ( index != -1 && index % 2 == 0) {
			//System.out.println("Switched with: " + config[index + 1]);
			return config[index+1];
			
		}
		else if (index != -1 && index % 2 == 1) {
			System.out.println("Switched with: " + config[index - 1]);
			return config[index -1];
			
		}
		
		//IF IT'S NOT IN THE PLUGBOARD DON'T SWITCH IT
		return c;
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	private void LoadPlugBoard() {
		list.removeAll(list);
		list.addAll('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' ');
		
		WireA1.getItems().addAll(list);
		WireA1.getSelectionModel().select(0);	
		WireA2.getItems().addAll(list);
		WireA2.getSelectionModel().select(26);
		
		WireB1.getItems().addAll(list);
		WireB1.getSelectionModel().select(1);
		WireB2.getItems().addAll(list);
		WireB2.getSelectionModel().select(25);
		
		WireC1.getItems().addAll(list);
		WireC1.getSelectionModel().select(2);
		WireC2.getItems().addAll(list);
		WireC2.getSelectionModel().select(24);
		
		WireD1.getItems().addAll(list);
		WireD1.getSelectionModel().select(3);
		WireD2.getItems().addAll(list);
		WireD2.getSelectionModel().select(23);
		
		WireE1.getItems().addAll(list);
		WireE1.getSelectionModel().select(4);
		WireE2.getItems().addAll(list);
		WireE2.getSelectionModel().select(22);
		
		WireF1.getItems().addAll(list);
		WireF1.getSelectionModel().select(5);
		WireF2.getItems().addAll(list);
		WireF2.getSelectionModel().select(21);
		
		WireG1.getItems().addAll(list);
		WireG1.getSelectionModel().select(6);
		WireG2.getItems().addAll(list);
		WireG2.getSelectionModel().select(20);
		
		WireH1.getItems().addAll(list);
		WireH1.getSelectionModel().select(7);
		WireH2.getItems().addAll(list);
		WireH2.getSelectionModel().select(19);
		
		WireI1.getItems().addAll(list);
		WireI1.getSelectionModel().select(8);
		WireI2.getItems().addAll(list);
		WireI2.getSelectionModel().select(18);
		
		WireJ1.getItems().addAll(list);
		WireJ1.getSelectionModel().select(9);
		WireJ2.getItems().addAll(list);
		WireJ2.getSelectionModel().select(17);
		
	}
	@SuppressWarnings("unchecked")
	private void LoadRotors() {
		RotorPositions.removeAll(RotorPositions);
		RotorStarts.removeAll(RotorStarts);
		
		RotorPositions.addAll(1,2,3,0);
		RotorStarts.addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26);
		
		//Populate choice box with choices and then select default choice
		Rotor1Pos.getItems().addAll(RotorPositions);
		Rotor1Pos.getSelectionModel().select(0);
		//Populate choice box with choices and then select default choice
		Rotor2Pos.getItems().addAll(RotorPositions);
		Rotor2Pos.getSelectionModel().select(1);
		//Populate choice box with choices and then select default choice
		Rotor3Pos.getItems().addAll(RotorPositions);
		Rotor3Pos.getSelectionModel().select(2);
		//Populate choice box with choices and then select default choice
		Rotor4Pos.getItems().addAll(RotorPositions);
		Rotor4Pos.getSelectionModel().select(3);
		//Populate choice box with choices and then select default choice
		Rotor5Pos.getItems().addAll(RotorPositions);
		Rotor5Pos.getSelectionModel().select(3);
		
		//Populate choice box with choices and then select default choice
		Rotor1Start.getItems().addAll(RotorStarts);
		Rotor1Start.getSelectionModel().select(0);
		//Populate choice box with choices and then select default choice
		Rotor2Start.getItems().addAll(RotorStarts);
		Rotor2Start.getSelectionModel().select(0);
		//Populate choice box with choices and then select default choice
		Rotor3Start.getItems().addAll(RotorStarts);
		Rotor3Start.getSelectionModel().select(0);
		//Populate choice box with choices and then select default choice
		Rotor4Start.getItems().addAll(RotorStarts);
		Rotor4Start.getSelectionModel().select(0);
		//Populate choice box with choices and then select default choice
		Rotor5Start.getItems().addAll(RotorStarts);
		Rotor5Start.getSelectionModel().select(0);
	}
}
