package org.usfirst.frc.team4902.robot;

public enum PortMap {
	
	Joystick(0),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	
	UltrasonicForward(2), UltrasonicSide(3);
	
	private int port;
	
	PortMap(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}

}
