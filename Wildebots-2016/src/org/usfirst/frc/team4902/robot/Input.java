package org.usfirst.frc.team4902.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Handles all driver input. Access using Input.getInstance()
 *
 */
public final class Input {
	
	/**
	 * Joystick object mapped to the port of the xbox controller
	 */
	private final Joystick stick = new Joystick(PortMap.Joystick.getPort());
	
	private final JoystickButton A = new JoystickButton(stick, PortMap.ButtonA.getPort()),
			B = new JoystickButton(stick, PortMap.ButtonB.getPort()),
			X = new JoystickButton(stick, PortMap.ButtonX.getPort()),
			Y = new JoystickButton(stick, PortMap.ButtonY.getPort()),
			LeftBumper = new JoystickButton(stick, PortMap.LeftBumper.getPort()),
			RightBumper = new JoystickButton(stick, PortMap.RightBumper.getPort());
	
	private static Input instance = new Input();
	
	public static Input getInstance() {
		return instance;
	}
	
	public Joystick getjoystick() {
		return stick;
	}
	
	public double getLeftX(){
		return stick.getRawAxis(0);
	}
	
	public double getLeftY() {
		return stick.getRawAxis(1);
		
	}
	
	public double getLeftYThreshold() {
		double threshold = 0.18;
		if (Math.abs(getLeftY()) < threshold) {
			return 0;
		} else {
			return getLeftY();
		}
	}
	
	public double getRightYThreshold() {
		double threshold = 0.18;
		if (Math.abs(getRightY()) < threshold) {
			return 0;
		} else {
			return getRightY();
		}
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
	
	public JoystickButton getLeftBumper() {
		return LeftBumper;
	}
	
	public JoystickButton getRightBumper() {
		return RightBumper;
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
	
	/**
	 * 
	 * @param button The name of the button in which you wish to inquire the nomenclatural information of.
	 * @return Name of the button 
	 */
	public String getButtonName(JoystickButton button) {
		String out = null;
		if (button.equals(A)) out = "A";
		else if (button.equals(B))  out = "B";
		else if (button.equals(X)) out = "X";
		else if (button.equals(Y)) out = "Y";
		else System.err.println("Unrecognized button passed to Input.getButtonName! Returning null!");
		return out;
	}
	/**
	 * if for some reason you ever need to get an array list of all the buttons (???) George's got you covered lol
	 * @return an ArrayList holding all the buttons mapped to the controller
	 */
	public ArrayList<JoystickButton> getButtons() {
		ArrayList<JoystickButton> buttons = new ArrayList<>();
		buttons.addAll(Arrays.asList(A,B,X,Y,LeftBumper,RightBumper));
		return buttons;
	}

}