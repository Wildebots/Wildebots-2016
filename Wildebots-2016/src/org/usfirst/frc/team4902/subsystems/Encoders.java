package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Manages all Encoders
 */
public class Encoders {
	
	private static Encoders instance = new Encoders();
	
	//Initializes encoders
	private Encoder leftDriveEncoder = new Encoder(PortMap.LeftDriveEncoderA.getPort(), PortMap.LeftDriveEncoderB.getPort()),
			rightDriveEncoder = new Encoder(PortMap.RightDriveEncoderA.getPort(), PortMap.RightDRiveEncoderB.getPort()),
			shooterEncoder  = new Encoder(PortMap.ShooterEncoderA.getPort(), PortMap.ShooterEncoderB.getPort());
	
	/**
	 * Use this to access Encoder methods
	 * @return Instance of Encoders class
	 */
	public static Encoders getInstance() {
		return instance;
	}
	
	/**
	 * Resets left and right drive wheel encoders
	 */
	public void resetDriveEncoders() {
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}
	
	/**
	 * Resets the shooter encoder
	 */
	public void resetShooterEncoder() {
		shooterEncoder.reset();
	}
	
	/**
	 * @return Left drive wheel encoder count
	 */
	public int getLeftDriveCount() {
		return leftDriveEncoder.get();
	}
	
	/**
	 * @return Right drive wheel encoder count
	 */
	public int getRightDriveCount() {
		return rightDriveEncoder.get();
	}
	
	/**
	 * @return Left drive wheel encoder distance
	 */
	public double getLeftDriveDistance() {
		return leftDriveEncoder.getDistance();
	}
	
	/**
	 * @return Right drive wheel encoder distance
	 */
	public double getRightDriveDistance() {
		return rightDriveEncoder.getDistance();
	}
	
	/**
	 * @return Shooter encoder count
	 */
	public int getShooterCount() {
		return shooterEncoder.get();
	}
	
	/**
	 * Gets angle of the shooter encoder
	 * @return Angle
	 */
	public double getShooterAngle() {
		return (shooterEncoder.get()/(497*18))*360;
	}
	
	public Encoder getShooterEncoder(){
		return shooterEncoder;
	}
	
	public Encoder getLeftDriveEncoder(){
		return leftDriveEncoder;
	}
	
	public Encoder getRightDriveEncoder(){
		return rightDriveEncoder;
	}
}
