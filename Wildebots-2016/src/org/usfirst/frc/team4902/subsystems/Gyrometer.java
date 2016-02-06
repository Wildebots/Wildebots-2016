package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;


public class Gyrometer {
	
	private ADXRS450_Gyro gyro;
	
	public Gyrometer() {
		gyro = new ADXRS450_Gyro();
		gyro.reset();
	}
	
	// Returns angle between 0 and 360 degrees relative to calibrated angle
	public double getAngle() {
		return gyro.getAngle();
	}
	
	public void calibrate() {
		gyro.reset();
	}
	
}
