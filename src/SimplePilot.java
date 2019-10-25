// SimplePilot.java
//
// Demonstrator of the use of the MovePilot class
// that superceeds the DifferentialPilot previously
// used by the NXT bricks.
//
// Terry Payne
// 30th September 2017
//

import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class SimplePilot {

	MovePilot pilot;
	GraphicsLCD lcd;
	
    // Here's the main part of the program, a function that repeats two calls
    // to the pilot.
    public void  drawSquare(float length){   
        for(int i = 0; i<4 ; i++){
            pilot.travel(length);       // Drive forward
            pilot.rotate(90);           // Turn 90 degrees    
        }
    }
        
	public void introMessage() {
		
		lcd.clear();
		lcd.setFont(Font.getDefaultFont());
		lcd.drawString("Simple Pilot", lcd.getWidth()/2, 0, GraphicsLCD.HCENTER);
		lcd.setFont(Font.getSmallFont());
		 
		lcd.drawString("This demonstrates how to ", 0, 20, 0);
		lcd.drawString("use the MovePilot", 0, 30, 0);
		lcd.drawString("Up Button: Go 100cm ", 0, 50, 0);
		lcd.drawString("Left Button: Turn 90 deg ", 0, 60, 0);
		lcd.drawString("Right Button: Turn -90 deg ", 0, 70, 0);
		lcd.drawString("ENTER Button: Draw Square ", 0, 80, 0);
		lcd.drawString("ESC Button: Quit ", 0, 90, 0);
	}

	public static void main(String[] args) {
		
		// Set up the wheels by specifying the diameter of the
		// left (and right) wheels in centimeters, i.e. 4.05 cm.
		// The offset number is the distance between the centre
		// of wheel to the centre of robot (4.9 cm)
		// NOTE: this may require some trial and error to get right!!!
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 4.225).offset(-5.55);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.D, 4.225).offset(5.55); //4.3
		
		Chassis myChassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
        // Create a SimplePilot and instantiate its member pilot
        SimplePilot sp = new SimplePilot();
        sp.pilot = new MovePilot(myChassis);
        sp.lcd = LocalEV3.get().getGraphicsLCD();
        
        sp.pilot.setLinearSpeed(20); 	// Set speed to 10cm per second
        int keyPressed = Button.getButtons();

    	// Print a message on the screen and wait for one of the button presses        
        while (Button.getButtons() != Keys.ID_ESCAPE) {   
    		sp.introMessage();

            switch (keyPressed) {
            case Keys.ID_UP:
                // Move the robot forward 100cm.
        		sp.lcd.drawString("Forward 100cm", 0, 120, 0);
        		sp.pilot.travel(100);
        		break;
            case Keys.ID_LEFT:
                // Rotate the robot to the left 90 degrees.
        		sp.lcd.drawString("Rotate 90 deg", 0, 120, 0);
        		sp.pilot.rotate(90);
        		break;
            case Keys.ID_RIGHT:
                // Rotate the robot to the right 90 degrees.
        		sp.lcd.drawString("Rotate -90 deg", 0, 120, 0);
        		sp.pilot.rotate(-90);
        		break;
            case Keys.ID_ENTER:
                // Call drawSquare with the length of the side.
        		sp.lcd.drawString("Drawing Square", 0, 120, 0);
                sp.drawSquare(50);        
        		break;
            case Keys.ID_DOWN:
            	// This shows accumulated error for the lecture
        		sp.lcd.drawString("Drawing Square x 5", 0, 120, 0);
        		for (int i=1; i<5; i++) {
        			sp.drawSquare(50);    
        		}
        		break;
        	default:
        		sp.lcd.drawString("Keys: left right up enter", 0, 120, 0);
        		break;    			
            }
            Button.waitForAnyPress(); 
            keyPressed = Button.getButtons();
        }
        
	}

}