package org.usfirst.frc.team4902.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Input {
	
	private Joystick stick = new Joystick(0);
	
	private final static Input instance = new Input();
	
	public static Input getInstance() {
		return instance;
	}
	
	public Joystick getStick() {
		return stick;
	}
	
	public double getLeftY() {
		return stick.getRawAxis(0);
	}

}