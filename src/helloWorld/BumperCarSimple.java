// BumperCarSimple.java
// 
// A simple application that uses the Subsumption architecture to create a
// bumper car, that drives forward, and changes direction given a collision.
//
// Terry Payne
// 1st October 2018
//
package helloWorld;
import lejos.hardware.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BumperCarSimple {

	public static void main(String[] args) {
		PilotRobot me = new PilotRobot();		
		PilotMonitor myMonitor = new PilotMonitor(me, 400);	

		// Set up the behaviours for the Arbitrator and construct it.
		Behavior b1 = new DriveForward(me);
		Behavior b2 = new BackUp(me);
		Behavior [] bArray = {b1, b2};
		Arbitrator arby = new Arbitrator(bArray);

		// Note that in the Arbritrator constructor, a message is sent
		// to stdout.  The following prints eight black lines to clear
		// the message from the screen
        for (int i=0; i<8; i++)
        	System.out.println("");

        // Start the Pilot Monitor
		myMonitor.start();

		// Tell the user to start
		myMonitor.setMessage("Press a key to start");				
        Button.waitForAnyPress();
		me.getPilot().travel(25);
		me.turnMotors(90);
		me.getPilot().travel(25);
        // Start the Arbitrator
		arby.go();
	}
}