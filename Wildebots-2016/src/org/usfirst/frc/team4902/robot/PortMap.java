package org.usfirst.frc.team4902.robot;

/**
 * Contains all ports for all subsystems
 */
public enum PortMap {
	
	Joystick(0),
	
	UltrasonicForward(0), UltrasonicSide(1),
	
	yoloPort(-99),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	LeftBumper(5), RightBumper(6),
	
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
	
	/**
	 * @return The port on the RoboRIO for the specific system
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * 
	 * @return Exquisitely formatted factualities about the ports
	 */
	public static String getPorts() {
		String output = "PortMap -> ";
		for (PortMap port : PortMap.values()) {
			output += "\n\t PortRegistry " + port.toString() + ": " + port.getPort();  
		}
		return output;
	}
}
