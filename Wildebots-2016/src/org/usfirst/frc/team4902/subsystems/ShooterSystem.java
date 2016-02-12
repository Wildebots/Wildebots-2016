package org.usfirst.frc.team4902.subsystems;

import java.time.Duration;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.MasterTimer;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem extends Subsystem {
	
	private static ShooterSystem instance = new ShooterSystem();
	
	private Victor left = new Victor(PortMap.LeftShooter.getPort()),
	right = new Victor(PortMap.RightShooter.getPort()),
	kick = new Victor(PortMap.Kicker.getPort());
	
//	private PIDController shooterArm = new PIDController(0.05, 0.0, 0.5, Encoders.getInstance().getShooterEncoder(), armMotor); // TODO: Get actual PID values
	
	public static ShooterSystem getInstance(){
		return instance;
	}
	
	/**
	 * sets the angle of the arm to a specified value
	 * @param angle The desired angle to set the arm to
	 */
//	public void setAngle(double angle){
//		shooterArm.setSetpoint(angle);
//	}

	@Override
	public void execute() {
		System.out.println("Firing!");
		left.set(0.9);
		right.set(-0.9);
		MasterTimer.getInstance().schedule(() -> {
			System.out.println("Kick!");
			kick.set(-1);
			MasterTimer.getInstance().schedule(() -> {
				System.out.println("Stop!");
				left.set(0);
				right.set(0);
				kick.set(0.2);
				MasterTimer.getInstance().schedule(() -> {
					kick.set(0);
				}, Duration.ofMillis(400));
			}, Duration.ofMillis(200));
		}, Duration.ofMillis(1000));
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}

}
