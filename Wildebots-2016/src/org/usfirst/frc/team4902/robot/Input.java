package org.usfirst.frc.team4902.robot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public final class Input {
	
	public enum map {
		FrontLeftMotor(0), BackLeftMotor(2),
		FrontRightMotor(15), BackRightMotor(14);
		
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
	
	private final Joystick stick = new Joystick(0);
	
	private final JoystickButton A = new JoystickButton(stick, 1),
			B = new JoystickButton(stick, 2),
			X = new JoystickButton(stick, 3),
			Y = new JoystickButton(stick, 4);
	
	private static Input instance = new Input();
	
	public static Input getInstance() {
		return instance;
	}
	
	public Joystick getStick() {
		return stick;
	}
	
	public double getLeftY() {
		return stick.getRawAxis(0);
	}
	
	public double getRightY() {
		return stick.getRawAxis(5);
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