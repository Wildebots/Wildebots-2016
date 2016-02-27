package org.usfirst.frc.team4902.robot;

import java.time.Duration;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.Arm;
import org.usfirst.frc.team4902.subsystems.Camera;
import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.Encoders;
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
	
	public volatile boolean noShooterLimit = false;
	
    public void robotInit() {
    	
    	instance = this;
    	
    	Camera.startCamera();
    	
    	// Disable subsystems here
    	    	
    	EventSystem.getInstance().addHandler(() -> {
    		noShooterLimit = true;
    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		noShooterLimit = false;
    		Encoders.getInstance().resetShooterEncoder();
    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnRelease);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().shoot();
    	}, Input.getSecondaryInstance().getRightBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().pickup();
    	}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().stopShooterMotors();
    	}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnRelease);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().shoot(0.4);
    	}, Input.getSecondaryInstance().getButtonA(), HandlerType.OnPress);
    	
//    	~~ DO NOT ENABLE! (Ben, Quinton, Victor, this means you) ~~
    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		ShooterSystem.getInstance().setAngleGUNVIR(-45);
//    	}, Input.getSecondaryInstance().getButtonX(), HandlerType.OnPress);

//    	EventSystem.getInstance().addHandler(() -> {
//    		ShooterSystem.getInstance().setAngleGUNVIR(0);
//    	}, Input.getSecondaryInstance().getButtonY(), HandlerType.OnPress);

//    	EventSystem.getInstance().addHandler(() -> {
//    		DriveSystem.getInstance().rotate(45);
//    	}, Input.getPrimaryInstance().getButtonX(), HandlerType.OnPress);
    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		ShooterSystem.getInstance().setAngle(-60);
//    	}, Input.getPrimaryInstance().getButtonA(), HandlerType.OnPress);
    	    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		ShooterSystem.getInstance().setAngle(45);
//    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);
    	    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		ShooterSystem.getInstance().setAngle(-80);
//    	}, Input.getSecondaryInstance().getButtonX(), HandlerType.OnPress);
    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		double angle = Encoders.getInstance().getShooterAngle();
//    		ShooterSystem.getInstance().setAngle(-80);
//    		DriveSystem.getInstance().driveStraight(0.7);
//    		ShooterSystem.getInstance().pickup();
//    		MasterTimer.getInstance().schedule(() -> {
//    			ShooterSystem.getInstance().setAngle(angle);
//    			DriveSystem.getInstance().stopRotate();
//    			ShooterSystem.getInstance().stopShooterMotors();
//    		}, Duration.ofSeconds(4));
//    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);
    	
    }
    
    volatile boolean stop = false;
        
	public void autonomousPeriodic() {
		while (!stop) {
			DriveSystem.getInstance().setSpeed(0.8);
		}
		DriveSystem.getInstance().setSpeed(0);
	}
	
	public void teleopPeriodic() {
		ShooterSystem.getInstance().execute();
		DriveSystem.getInstance().execute();
//		Arm.getInstance().execute(); // DO NOT ENABLE WITHOUT PERMISSION
		Arm.getInstance().singleArmExecute(); // Use for single base arm
	}

	@Override
	public void testInit() {
		
	}
    
    public void autonomousInit() {
    	MasterTimer.getInstance().schedule(() -> {
    		stop = true;
    	}, Duration.ofSeconds(2));
    }
    
    @Override
    public void testPeriodic() {
    	
    }
    
    @Override
    public void disabledInit() {
    	Encoders.getInstance().resetArmEncoders();
    	Encoders.getInstance().resetShooterEncoder();
    }
    
}
