package org.usfirst.frc.team4902.robot;

public enum PortMap {
	
	Joystick(0),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	
	//TODO: Add real port numbers
	LeftFrontMotor(0), LeftBackMotor(1), RightFrontMotor(2), RightBackMotor(3);
	
	private int port;
	
	PortMap(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}

}
