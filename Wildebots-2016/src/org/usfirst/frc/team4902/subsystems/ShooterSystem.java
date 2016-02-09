package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class ShooterSystem {
	
	private static ShooterSystem instance = new ShooterSystem();
	
	private int temp = 0; // TODO: add entries in PortMap
	
	private Talon leftShooterMotor = new Talon(temp), rightShooterMotor = new Talon(temp), armMotor = new Talon(temp);
	
	private PIDController shooterArm = new PIDController(0.05, 0.0, 0.5, Encoders.getInstance().getShooterEncoder(), armMotor); // TODO: Get actual PID values
	
	public static ShooterSystem getInstance(){
		return instance;
	}
	
	/**
	 * will make the shooting motors spin, launching the ball
	 */
	public void shoot(){ //TODO: make this pulse, rather than just start up.
		leftShooterMotor.set(1);
		rightShooterMotor.set(1);
	}
	
	/**
	 * sets the angle of the arm to a specified value
	 * @param angle The desired angle to set the arm to
	 */
	public void setAngle(double angle){
		shooterArm.setSetpoint(angle);
	}

}
