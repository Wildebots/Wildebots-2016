package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.Arm;
import org.usfirst.frc.team4902.subsystems.Autonomous;
import org.usfirst.frc.team4902.subsystems.Camera;
import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.Encoders;
import org.usfirst.frc.team4902.subsystems.ShooterSystem;
import org.usfirst.frc.team4902.subsystems.Ultrasonics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

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
    		
    	}, Input.getSecondaryInstance().getRightBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().pickup();
    		
    	}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		
    		ShooterSystem.getInstance().stopShooterMotors();
    		
    	}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnRelease);
    	
//    	EventSystem.getInstance().addHandler(() -> {
//    		DriveSystem.getInstance().rotate(90);
//    	}, Input.getPrimaryInstance().getButtonX(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().shoot(0.3);
    	}, Input.getSecondaryInstance().getButtonA(), HandlerType.OnPress);
    	
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().setAngle(45);
    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);
    	
    	InternalButton button = new InternalButton();
		LiveWindow.addSensor("Encoders", "Shooter Encoder", Encoders.getInstance().getShooterEncoder());
		SmartDashboard.putData("Bullshit", button);
    }
    
    public void teleopInit() {
    	
    }
    
	public void autonomousPeriodic() {
		Autonomous.getInstance().execute();
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
