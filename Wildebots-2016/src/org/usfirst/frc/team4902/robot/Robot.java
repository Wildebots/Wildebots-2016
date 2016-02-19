package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.Camera;
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
    	
    	Camera.startCamera();
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().shoot();
    		
    	}, Input.getInstance().getButtonA(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().pickup();
    		
    	}, Input.getInstance().getRightBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().stopShooterMotors();
    		
    	}, Input.getInstance().getRightBumper(), HandlerType.OnRelease);
    	
    }
    
    public void teleopInit() {
    	
    }
    
	public void autonomousPeriodic() {

	}

	public void teleopPeriodic() {
		ShooterSystem.getInstance().execute();
		DriveSystem.getInstance().execute();
	}

	@Override
	public void testInit() {
		
	}
    
    public void autonomousInit() {
    	
    }
}
