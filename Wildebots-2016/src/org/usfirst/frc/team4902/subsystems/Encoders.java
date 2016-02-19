package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Manages all Encoders
 */
public class Encoders {
	
	private static Encoders instance = new Encoders();
	
	//Initializes encoders
	private Encoder armBaseSegmentEncoder = new Encoder(PortMap.ArmBaseSegmentEncoderA.getPort(), PortMap.ArmBaseSegmentEncoderB.getPort()),
			armSecondSegmentEncoder = new Encoder(PortMap.ArmSecondSegmentEncoderA.getPort(), PortMap.ArmSecondSegmentEncoderB.getPort()),
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
		armBaseSegmentEncoder.reset();
		armSecondSegmentEncoder.reset();
	}
	
	/**
	 * Resets the shooter encoder
	 */
	public void resetShooterEncoder() {
		shooterEncoder.reset();
	}
	
//	/**
//	 * @return Left drive wheel encoder count
//	 */
//	public int getLeftDriveCount() {
//		return armBaseSegmentEncoder.get();
//	}
//	
//	/**
//	 * @return Right drive wheel encoder count
//	 */
//	public int getRightDriveCount() {
//		return armSecondSegmentEncoder.get();
//	}
//	
//	/**
//	 * @return Left drive wheel encoder distance
//	 */
//	public double getLeftDriveDistance() {
//		return armBaseSegmentEncoder.getDistance();
//	}
//	
//	/**
//	 * @return Right drive wheel encoder distance
//	 */
//	public double getRightDriveDistance() {
//		return armSecondSegmentEncoder.getDistance();
//	}
	
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
		return (shooterEncoder.get()/497)*360;
	}
	
	//TODO: get actual count
	public double getBaseSegmentAngle(){
		final double COUNTS_PER_REV = 360;
		return (armBaseSegmentEncoder.get() / COUNTS_PER_REV) * 360;
		
	}
	
	//TODO: get actual count
	public double getSecondSegmentAngle(){
		final double COUNTS_PER_REV = 360;
		return (armSecondSegmentEncoder.get() / COUNTS_PER_REV) * 360;
		
	}
	
	public Encoder getShooterEncoder(){
		return shooterEncoder;
	}
	
	public Encoder getArmBaseSegmentEncoder(){
		return armBaseSegmentEncoder;
	}
	
	public Encoder getArmSecondSegmentEncoder(){
		return armSecondSegmentEncoder;
	}
}
