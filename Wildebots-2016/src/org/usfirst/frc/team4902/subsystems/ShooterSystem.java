package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class ShooterSystem extends Subsystem{
	
	/**
	 * This class converts the encoder's value into a form the PIDController can take as an input
	 *
	 *
	 */
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
			return Encoders.getInstance().getArmMotorEncoder(); //TODO: this is probably not the right input...
		}
	}
	
	/**
	 * 
	 * This class will take the values processed by the PIDController and interpret it as an output in motor movement
	 *
	 */
	private class ArmPIDOutput implements PIDOutput{
		@Override
		public void pidWrite(double output) {
			armMotor.set(output);
		}
	}

	
	private static ShooterSystem instance = new ShooterSystem();
	
	private int temp = 0; // TODO: add entries in PortMap

	
	private Talon leftShooterMotor = new Talon(temp), rightShooterMotor = new Talon(temp), armMotor = new Talon(temp);
	
	private EncoderPIDSource encoderPIDSource = new EncoderPIDSource();
	private ArmPIDOutput armPIDOutput = new ArmPIDOutput();
	private PIDController shooterArm = new PIDController(1,1,1,encoderPIDSource,armPIDOutput); // TODO: Get actual PID values
	
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
