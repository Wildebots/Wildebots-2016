package org.usfirst.frc.team4902.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class ShooterSystem extends Subsystem{
	
	private class EncoderPIDSource implements PIDSource{
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
		}
		@Override
		public PIDSourceType getPIDSourceType() {
			return null;
		}
		@Override
		public double pidGet() {
			return Encoders.getInstance().get();
		}
	}
	
	private class ArmPIDOutput implements PIDOutput{
		@Override
		public void pidWrite(double output) {
			armMotor.set(output);
		}
	}

	
	private static ShooterSystem instance = new ShooterSystem();
	
	private int temp = 0;

	
	private Talon leftShooterMotor = new Talon(temp), rightShooterMotor = new Talon(temp), armMotor = new Talon(temp);
	
	private EncoderPIDSource encoderPIDSource = new EncoderPIDSource();
	private ArmPIDOutput armPIDOutput = new ArmPIDOutput();
	private PIDController shooterArm = new PIDController(1,1,1,encoderPIDSource,armPIDOutput);
	
	public static ShooterSystem getInstance(){
		return instance;
	}
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * will make the shooting motors spin, launching the ball
	 */
	public void shoot(){
		leftShooterMotor.set(1);
		rightShooterMotor.set(1);
	}
	
	public void setAngle(){
	}

}
