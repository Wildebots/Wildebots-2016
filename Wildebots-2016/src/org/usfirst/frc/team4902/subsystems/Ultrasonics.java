package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonics {
	
	private static final double VOLTS_PER_CENTIMETRE = .0049;
	
	private static Ultrasonics ultrasonic = new Ultrasonics();
	
	public static Ultrasonics getInstance(){
		return ultrasonic;
	}
	
	/**
	 * maps the input from the ultrasonic sensor
	 */
	private AnalogInput forwardSensorIn = new AnalogInput(PortMap.UltrasonicForward.getPort());
	private AnalogInput sideSensorIn = new AnalogInput(PortMap.UltrasonicSide.getPort());
	
	/**
	 * interprets the analog output from the ultrasonic and calculates distance from it
	 * @return distance in metres
	 */
	public double getSideDistance(){
		return sideSensorIn.getVoltage() / VOLTS_PER_CENTIMETRE;
	}
	
	/**
	 * interprets the analog output from the ultrasonic and calculates distance from it
	 * @return distance in metres
	 */
	public double getForwardDistance(){
		return forwardSensorIn.getVoltage() / VOLTS_PER_CENTIMETRE;
	}

}
