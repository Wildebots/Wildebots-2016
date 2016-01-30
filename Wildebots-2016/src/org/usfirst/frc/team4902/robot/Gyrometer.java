package org.usfirst.frc.team4902.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Gyrometer {
	
	// Gyro pin
	private int gyroChannel = 1;
	
	private Gyro gyro;
	
	private final static Gyrometer instance = new Gyrometer();
	
	public static Gyrometer getInstance() {
		return instance;
	}
	
	public Gyrometer() {
		gyro = new AnalogGyro(gyroChannel);
		gyro.reset();
	}
	
	// Returns angle between 0 and 360 degrees relative to calibrated angle
	public double getAngle() {
		return gyro.getAngle();
	}
}
