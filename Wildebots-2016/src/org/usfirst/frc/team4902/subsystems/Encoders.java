package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;
import edu.wpi.first.wpilibj.Encoder;

public class Encoders {
	
	private static Encoders instance = new Encoders();
	
	private Encoder leftDriveEncoder, rightDriveEncoder, shooterEncoder;
	
	public static Encoders getInstance() {
		return instance;
	}
	
	public void initialize() {
		leftDriveEncoder = new Encoder(PortMap.LeftDriveEncoderA.getPort(), PortMap.LeftDriveEncoderB.getPort());
		rightDriveEncoder = new Encoder(PortMap.RightDriveEncoderA.getPort(), PortMap.RightDRiveEncoderB.getPort());
		
		shooterEncoder = new Encoder(PortMap.ShooterEncoderA.getPort(), PortMap.ShooterEncoderB.getPort());
	}
	
	public void resetDriveEncoders() {
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}
	
	public void resetShooterEncoder() {
		shooterEncoder.reset();
	}
	
	public int getLeftDriveCount() {
		return leftDriveEncoder.get();
	}
	
	public int getRightDriveCount() {
		return rightDriveEncoder.get();
	}
	
	public double getLeftDriveDistance() {
		return leftDriveEncoder.getDistance();
	}
	
	public double getRightDriveDistance() {
		return rightDriveEncoder.getDistance();
	}
	
	public int getShooterCount() {
		return shooterEncoder.get();
	}
}
