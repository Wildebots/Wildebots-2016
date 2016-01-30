package org.usfirst.frc.team4902.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Handles all driver input. Acess using Input.getInstace()
 *
 */
public final class Input {
	
	public enum map {
		
		Joystick(0),
		
		ButtonA(1), ButtonB(2), ButtonX(3), ButtonY(4);
		
		private int port;
		
		map(int port) {
			this.port = port;
		}
		
		public int getPort() {
			return this.port;
		}
		
	}
	
	private final Joystick stick = new Joystick(map.Joystick.getPort());
	
	
	private final JoystickButton A = new JoystickButton(stick, map.ButtonA.getPort()),
			B = new JoystickButton(stick, map.ButtonB.getPort()),
			X = new JoystickButton(stick, map.ButtonX.getPort()),
			Y = new JoystickButton(stick, map.ButtonY.getPort());
	
	private static Input instance = new Input();
	
	public static Input getInstance() {
		return instance;
	}
	
	public Joystick getStick() {
		return stick;
	}
	
	public double getLeftX(){
		return stick.getRawAxis(0);
	}
	
	public double getLeftY() {
		return stick.getRawAxis(1);
		
	}
	
	public double getRightX() {
		return stick.getRawAxis(4);
	}

	
	public double getRightY() {
		return stick.getRawAxis(5);
	}
	
	public double getLeftTrigger(){
		return stick.getRawAxis(2);
	}
	
	public double getRightTrigger(){
		return stick.getRawAxis(3);
	}
	

	public JoystickButton getButtonA() {
		return A;
	}

	public JoystickButton getButtonB() {
		return B;
	}

	public JoystickButton getButtonX() {
		return X;
	}

	public JoystickButton getButtonY() {
		return Y;
	}
	
	public String getButtonName(JoystickButton button) {
		String out = null;
		if (button.equals(A)) out = "A";
		else if (button.equals(B))  out = "B";
		else if (button.equals(X)) out = "X";
		else if (button.equals(Y)) out = "Y";
		else System.err.println("Unrecognized button passed to Input.getButtonName! Returning null!");
		return out;
	}
	
	public ArrayList<JoystickButton> getButtons() {
		ArrayList<JoystickButton> buttons = new ArrayList<>();
		buttons.addAll(Arrays.asList(A,B,X,Y));
		return buttons;
	}

}