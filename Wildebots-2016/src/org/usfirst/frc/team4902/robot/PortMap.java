package org.usfirst.frc.team4902.robot;

public enum PortMap {
	
	Joystick(0),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	
	//TODO: Add real port numbers
	LeftFrontMotor(2), LeftBackMotor(3), RightFrontMotor(0), RightBackMotor(1),
	
	Gyro(0);
	
	private int port;
	
	PortMap(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}

}
