package org.usfirst.frc.team4902.robot;

public enum PortMap {
	
	Joystick(0),
	
	UltrasonicForward(0), UltrasonicSide(1),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	LeftBumper(5), RightBumper(6),
	
	// TODO: Add real port numbers
	LeftDriveEncoderA(9),
	LeftDriveEncoderB(9),
	
	RightDriveEncoderA(9),
	RightDRiveEncoderB(9),
	
	ShooterEncoderA(10),
	ShooterEncoderB(10),
	
	LeftShooter(4),
	RightShooter(5),
	Kicker(6),
	ArmMotor(7),
	
	LeftFrontMotor(0), LeftBackMotor(1), RightFrontMotor(2), RightBackMotor(3),
	
	Gyro(0);
	
	private int port;
	
	PortMap(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}
}
