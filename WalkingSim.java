//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    (descriptive title of the program making use of this file)
// Course:   CS 300 Fall 2022
//
// Author:   Rohan Balachander
// Email:    rbalachander@wisc.edu
// Lecturer: Mouna Kacem
//
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:  Got help from peer mentors at CS Lab       (identify each by name and describe how they helped)
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Random;
import java.io.File;
import processing.core.PImage;
/**
 * 
 * This class creates an animated program to simulate walkers on a display.
 *
 */
public class WalkingSim {
	private static Random randGen;
	private static int bgColor;
	private static PImage[] frames;
	private static Walker[] walkers;
	/**
	 * Main method. It runs the Utility application.
	 * @param args : takes input from command prompt, but isn't used here.
	 */
	public static void main(String[] args) {
		Utility.runApplication();
	}
	/**
	 * This method initializes the array of walkers, and gives them a coordinate location, as well as filling
	 * the frames array with images of the walkers.
	 */
	public static void setup() {
		frames = new PImage[Walker.NUM_FRAMES];
		randGen = new Random();
		bgColor = randGen.nextInt();
		for(int i = 0; i < frames.length; i++) {		
			frames[i] = Utility.loadImage("images" + File.separator + "walk-" + i + ".png");
		}
		walkers = new Walker[8];
		int walkNum = randGen.nextInt(walkers.length);
		for(int i = 0; i < walkNum; i++) {
			walkers[i] = new Walker(randGen.nextInt(Utility.width()), randGen.nextInt(Utility.height()));
		}
		
	}
	/**
	 * This method draws the walkers on the screen, as well as makes them walk when clicked. 
	 * It also updates whether a walker is walking or not.
	 */
	public static void draw() {
		Utility.background(bgColor);
		//Utility.image(frames[0], walkers[0].getPositionX(), walkers[0].getPositionY());
		for(int i = 0; i < walkers.length; i++) {
			if(walkers[i] != null) {
				if(walkers[i].isWalking() == true) {
					float posX = (walkers[i].getPositionX() + 3) % Utility.width();
					walkers[i].setPositionX(posX);
				}
				Utility.image(frames[walkers[i].getCurrentFrame()], walkers[i].getPositionX(), walkers[i].getPositionY());
			}
		}
		for(int i = 0; i < walkers.length; i++) {
			if(walkers[i] != null) {
				if(walkers[i].isWalking() == true) {
					walkers[i].update();
				}
			}
		}
	}
	/**
	 * This method checks is the cursor is over the walker image in the display window.
	 * @param input: This is the input walker, the dimensions of which are taken to check if the cursor
	 * is on the image of the walker.
	 * @return The method returns true if the cursor is over the walker, and returns false if it isn't.
	 */
	public static boolean isMouseOver(Walker input) {
		float width = frames[input.getCurrentFrame()].width;
		int height  = frames[input.getCurrentFrame()].height;
		float lowerBoundWidth = input.getPositionX() - width / 2;
		if(lowerBoundWidth < 0) {
			lowerBoundWidth = 0;
		}
		float upperBoundWidth = input.getPositionX() + width / 2;
		if(upperBoundWidth > Utility.width()) {
			upperBoundWidth = Utility.width();
		}
		float lowerBoundHeight = input.getPositionY() - height / 2;
		if(lowerBoundHeight < 0) {
			lowerBoundHeight = 0;
		}
		float upperBoundHeight = input.getPositionY() + height / 2;
		if(upperBoundHeight > Utility.height()) {
			upperBoundHeight = Utility.height();
		}
		if(Utility.mouseY() > lowerBoundHeight && Utility.mouseY() < upperBoundHeight && Utility.mouseX() > lowerBoundWidth && Utility.mouseX() < upperBoundWidth) {
			return true;
		}
		else {
			return false;
		}
		
	}
	/**
	 * This method is called when the mouse clicks on the display window. If it presses
	 * on the walker, it changes the setWalking attribute to true
	 */
	public static void mousePressed() {
		for(int i = 0; i < frames.length; i++) {
			if(walkers[i] != null) {
				float width = frames[walkers[i].getCurrentFrame()].width;
				int height  = frames[walkers[i].getCurrentFrame()].height;
				float lowerBoundWidth = walkers[i].getPositionX() - width / 2;
				if(lowerBoundWidth < 0) {
					lowerBoundWidth = 0;
				}
				float upperBoundWidth = walkers[i].getPositionX() + width / 2;
				if(upperBoundWidth > Utility.width()) {
					upperBoundWidth = Utility.width();
				}
				float lowerBoundHeight = walkers[i].getPositionY() - height / 2;
				if(lowerBoundHeight < 0) {
					lowerBoundHeight = 0;
				}
				float upperBoundHeight = walkers[i].getPositionY() + height / 2;
				if(upperBoundHeight > Utility.height()) {
					upperBoundHeight = Utility.height();
				}
				if(Utility.mouseY() > lowerBoundHeight && Utility.mouseY() < upperBoundHeight && Utility.mouseX() > lowerBoundWidth && Utility.mouseX() < upperBoundWidth) {
					walkers[i].setWalking(true);
				}
			}
		}
	}
	/**
	 * This method adds a new walker to the walkers array if there is space, and the display, if the key a
	 * is pressed, and if the key s is pressed, will stop all the currently walking walkers
	 * from walking
	 * @param input takes a char input from the user, and if it is an 'a' or 's', will add another walker
	 * if there is space, and if an 's' will stop all the walking walkers from walking.
	 */
	public static void keyPressed(char input) {
		for(int i = 0; i < walkers.length; i++) {
			if(walkers[i] != null) {
				if(input == 's' || input == 'S') {
					walkers[i].setWalking(false);
				}
			}
		}
		if(input == 'a' || input == 'A') {
			int counter = 0;
			for(int i = 0; i < walkers.length; i++) {
				if(walkers[i] == null) {
					counter++;
					if(counter == 1) {
						walkers[i] = new Walker(randGen.nextInt(Utility.width()), randGen.nextInt(Utility.height()));
					}
				}
			}
		}
	}
	
}
