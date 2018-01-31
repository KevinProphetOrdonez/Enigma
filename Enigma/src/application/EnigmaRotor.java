package application;

import java.util.ArrayList;
import java.util.Arrays;

public class EnigmaRotor {

		ArrayList<Character> config;
		int odometer;
		int wheel;
		int start;
	public EnigmaRotor(int[] configSettings) {
		wheel = configSettings[0];
		start = configSettings[1];
		
		populateRotor(wheel);
		
		rotateWheelToStart(start);
	}

	private void populateRotor(int position) {
		
		switch(position) {
		case 0:
			
			config = new ArrayList<>(Arrays.asList('G','N','U','A','H','O','V','B','I','P','W','C','J','Q','X','D','K','R','Y',' ','E','L','S','Z','F','M','T'));
			break;
		case 2:
			config = new ArrayList<>(Arrays.asList('E','J',' ','O','T','Y','C','H','M','R','W','A','F','K','P','U','Z','D','I','N','S','X','B','G','L','Q','V'));
			break;
		case 4:
			config = new ArrayList<>(Arrays.asList('B','D','F','H','J','L','N','P','R','T','V','X','Z','A','C','E','G','I',' ','K','M','O','Q','S','U','W','Y'));
			break;
		case 6:
			config = new ArrayList<>(Arrays.asList('K','P','H','D','E','A','C',' ','V','T','W','Q','M','Y','N','L','X','S','U','R','Z','O','J','F','B','G','I'));
			break;
		case 8:																			
			config = new ArrayList<>(Arrays.asList('N','D','Y','G','L','Q','I','C','V','E','Z','R','P','T','A','O','X','W','B','M','J','S','U','H','K',' ','F'));
			break;
		}
		
	}
	
	//Method will be used to rotate the wheel forward
	public void rotateWheel() {
		config.add(0, config.remove(26));
	}
	
	//Method will be used to rotate the wheel to it's starting position chosen by the user
	public void rotateWheelToStart(int start) {
		for(int i = 1; i < start; i++) {
			rotateWheel();
		}
	}
	
	//This method will be used to return the character in the wheel given the index
	//THIS METHOD IS SUPPOSE TO BE USED WITH ONLY THE OUTER WHEEL
	public Character ReturnCharacterAtIndex(int index) {
		Character c;
		
		c = config.get(index);
	
		return c;
	}
	
	//This method will be used to return the index of a given character
	//THIS METHOD IS SUPPOPSE TO BE USED BY THE INNER NAD MIDDLE WHEELS 
	public int ReturnIndexOfCharacter(char c) {
		int index;
		
		index = config.indexOf(c);
		
		System.out.println(c + " is located at: " + index);
		
		return index;
	}
	
	//Utility method to print the configuration of the wheel
	public void printWheel() {
		for (Character obj:config) {
			System.out.print(obj);
		}
		System.out.println(" ");
	}
}
