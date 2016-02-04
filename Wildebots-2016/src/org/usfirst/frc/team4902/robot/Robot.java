
package org.usfirst.frc.team4902.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.util.AllocationException;

public class Robot extends IterativeRobot{
	
	DigitalOutput sanicOut;
	DigitalInput sanicIn;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() throws AllocationException{
    	
    	
    	sanicOut = new DigitalOutput(2);
    	sanicIn = new DigitalInput(2);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() throws AllocationException{
    	
    	sanicOut.set(true);
    	Timer.delay(0.000005);
    	sanicOut.set(false);
    	
    	
    	long startTime = 0;
    	long duration = 0;
    	boolean started = false;
    	boolean running = true;
    	while(running){
    		if(sanicIn.get() && !started){
    			startTime = System.nanoTime();
    			started = true;
    		}
    		if(!sanicIn.get() && started){
    			duration = System.nanoTime()-startTime;
    			running = false;
    		}
    		
    	}
    	
    	
    	System.out.println(duration);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}
