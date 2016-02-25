package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.subsystems.Arm;
import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.ShooterSystem;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main robot class
 * Starting point of program
 */
public class Robot extends IterativeRobot {
		
	private static Robot instance;
	
	public static Robot getInstance() {
		return instance;
	}
	
    public void robotInit(){
    	instance = this;
    }
    
    public void teleopInit() {
    	
    }
    
	public void autonomousPeriodic() {
		
	}

	public void teleopPeriodic() {
		ShooterSystem.getInstance().execute();
		DriveSystem.getInstance().execute();
		Arm.getInstance().execute();
	}

	@Override
	public void testInit() {
		
	}
    
    public void autonomousInit() {
    	
    }
}
