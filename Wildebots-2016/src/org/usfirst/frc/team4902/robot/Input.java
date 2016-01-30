package org.usfirst.frc.team4902.robot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Handles all driver input. Acess using Input.getInstace()
 *
 */
public final class Input {
	
	public enum map {
		FrontLeftMotor(0), BackLeftMotor(2),
		FrontRightMotor(15), BackRightMotor(14),
		
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
	
	public enum ActionMethod {
		WhenActive, WhenInactive, WhenPressed, WhenReleased;
		
		private Method method;
		
		ActionMethod() {
			try {
				DriverStation.reportError(this.toString().replace(this.toString().charAt(0), Character.toLowerCase(this.toString().charAt(0))), false);
				this.method = JoystickButton.class.getMethod(this.toString().replace(this.toString().charAt(0), Character.toLowerCase(this.toString().charAt(0))), Command.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				System.out.println("Exception in attaching ActionMethod enums to respective methods");
				System.out.println("Offending enum: " + this.name());
			}
		}
		
		public Method getMethod() {
			return this.method;
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
	
	public void setOn(Runnable r, JoystickButton b, ActionMethod m) {
		class newCommand extends Command {
			
			private Runnable r;
			
			private boolean finish = false;
			
			public newCommand(Runnable r) {
				this.r = r;
			}

			@Override
			protected void initialize() {}

			@Override
			protected void execute() {
				r.run();
				this.finish = true;
			}

			@Override
			protected boolean isFinished() {
				return this.finish;
			}

			@Override
			protected void end() {}

			@Override
			protected void interrupted() {}
		}
		newCommand command = new newCommand(r);
		try {
			m.getMethod().invoke(b, command);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("Exception attaching Runnable to JoystickButton method: " + m.name());
		}
	}

}