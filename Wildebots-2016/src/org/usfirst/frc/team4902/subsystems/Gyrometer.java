package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Gyrometer {
	
	private Gyro gyro;
	
	public Gyrometer() {
		gyro = new AnalogGyro(PortMap.Gyro.getPort());
		gyro.reset();
	}
	
	// Returns angle between 0 and 360 degrees relative to calibrated angle
	public double getAngle() {
		return gyro.getAngle();
	}
}
