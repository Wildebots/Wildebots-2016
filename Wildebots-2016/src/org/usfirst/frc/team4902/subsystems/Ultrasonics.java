package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Manages ultrasonic sensors and provides methods for interaction
 */
public class Ultrasonics {
	
	// Constant for conversion from voltage to distance
	private static final double VOLTS_PER_CENTIMETRE = 0.0049 * (4.8 / 5.0);
	
	private static Ultrasonics ultrasonic = new Ultrasonics();
	
	/**
	 * @return Instance of Ultrasonics class
	 */
	public static Ultrasonics getInstance(){
		return ultrasonic;
	}
	
	/**
	 * Maps the input from the ultrasonic sensor
	 */
	private AnalogInput forwardSensorIn = new AnalogInput(PortMap.UltrasonicForward.getPort());
	private AnalogInput sideSensorIn = new AnalogInput(PortMap.UltrasonicSide.getPort());
	
	/**
	 * Interprets the analog output from the ultrasonic and calculates distance from it
	 * @return distance in metres
	 */
	public double getSideDistance(){
		return sideSensorIn.getVoltage() / VOLTS_PER_CENTIMETRE;
	}
	
	/**
	 * Interprets the analog output from the ultrasonic and calculates distance from it
	 * @return distance in metres
	 */
	public double getForwardDistance(){
		return forwardSensorIn.getVoltage() / VOLTS_PER_CENTIMETRE;
	}
}
