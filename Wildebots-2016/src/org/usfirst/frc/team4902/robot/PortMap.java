package org.usfirst.frc.team4902.robot;

public enum PortMap {
	
	Joystick(0),
	
	UltrasonicForward(0), UltrasonicSide(1),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	
	// TODO: Add real port numbers
	LeftDriveEncoderA(9),
	LeftDriveEncoderB(9),
	
	RightDriveEncoderA(9),
	RightDRiveEncoderB(9),
	
	ShooterEncoderA(10),
	ShooterEncoderB(10);
	
	private int port;
	
	PortMap(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}
}
