package org.usfirst.frc.team4902.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;


public class Gyrometer {

	private static Gyrometer instance = new Gyrometer();

	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	public static Gyrometer getInstance() {
		return instance;
	}

	// Returns angle between 0 and 360 degrees relative to calibrated angle
	public double getAngle() {
		return -gyro.getAngle();
	}

	public void reset() {
		gyro.reset();
	}
	
	public void calibrate() {
		gyro.calibrate();
	}
}
