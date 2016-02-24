package org.usfirst.frc.team4902.robot;

/**
 * Contains all ports for all subsystems
 */
public enum PortMap {
	
	primaryJoystick(0), secondaryJoystick(1),
	
	UltrasonicForward(0), UltrasonicSide(1),
	
	yoloPort(-99),
	
	ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4),
	LeftBumper(5), RightBumper(6),
	
	// TODO: Add real port numbers
	
	ArmBaseSegmentEncoderA(4),
	ArmBaseSegmentEncoderB(5),
	
	ArmSecondSegmentEncoderA(6),
	ArmSecondSegmentEncoderB(7),
	
	ShooterEncoderA(8),
	ShooterEncoderB(9),
	
	LeftShooter(4),
	RightShooter(5),
	Kicker(6),
	ArmMotor(7),
	
	//TODO: Add real values
	ArmBaseSegmentMotor(8), ArmSecondSegmentMotor(9),
	
	LeftFrontMotor(0), LeftBackMotor(1), RightFrontMotor(2), RightBackMotor(3),
	
	Gyro(0);
	
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
