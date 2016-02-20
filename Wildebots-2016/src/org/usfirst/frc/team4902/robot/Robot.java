package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.Arm;
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
    		
    	}, Input.getSecondaryInstance().getButtonA(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().pickup();
    		
    	}, Input.getSecondaryInstance().getRightBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().stopShooterMotors();
    		
    	}, Input.getSecondaryInstance().getRightBumper(), HandlerType.OnRelease);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		DriveSystem.getInstance().rotate(90);
    	}, Input.getPrimaryInstance().getButtonX(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		System.out.println("Alex is important for testing too.....");
    	}, Input.getSecondaryInstance().getButtonA(), HandlerType.OnPress);
    	
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
