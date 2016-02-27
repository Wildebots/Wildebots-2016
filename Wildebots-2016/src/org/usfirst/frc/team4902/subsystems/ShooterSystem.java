package org.usfirst.frc.team4902.subsystems;

import java.time.Duration;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.MasterTimer;
import org.usfirst.frc.team4902.robot.PortMap;
import org.usfirst.frc.team4902.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem extends Subsystem {

	public static final double LOWER_LIMIT = -75, UPPER_LIMIT = 0;

	private static ShooterSystem instance = new ShooterSystem();

	private volatile boolean isBusy = false;
	
	private static final double angleThreshold = 2;

	private Victor left = new Victor(PortMap.LeftShooter.getPort()),
			right = new Victor(PortMap.RightShooter.getPort()),
			kick = new Victor(PortMap.Kicker.getPort()),
			armMotor = new Victor(PortMap.ArmMotor.getPort());

	private PIDController shooterArm = new PIDController(0.1, 0.1, 0.1, Encoders.getInstance().getShooterEncoder(), armMotor);

	public ShooterSystem() {
		//		shooterArm.setp
		//		shooterArm.enable();
	}

	public static ShooterSystem getInstance(){
		return instance;
	}
	
	public void setAngleGUNVIR(double targetAngle) {
		if (this.isDisabled() || this.isBusy) return;
		if (targetAngle > UPPER_LIMIT || targetAngle < LOWER_LIMIT) return;
		this.isBusy = true;
		
		Thread setAngleThread = new Thread(() -> {
			boolean isDone = false;
			while (!isDone){
				double currentAngle  = Encoders.getInstance().getShooterAngle();
				double diff = targetAngle - currentAngle;
				
				if (Math.abs(diff) > angleThreshold) {
					if (diff < 0) {
						armMotor.set(0.9);
					} else if (diff > 0) {
						armMotor.set(-0.9);
					}
				} else {
					armMotor.set(0);
					isDone = true;
					isBusy = false;
				}
			}
		});
		setAngleThread.setDaemon(true);
		setAngleThread.start();
	}

	/**
	 * sets the angle of the arm to a specified value
	 * @param angle The desired angle to set the arm to
	 */
	public void setAngle(double angle) {
		if (this.isDisabled()) return;
		int count = (int) ((angle / 360.0) * 497 * 18);
		System.out.println("angle count for pid: "+count+" : angle: "+angle);
		shooterArm.setSetpoint(count);
		shooterArm.enable();
	}

	@Override
	public void execute() {
		if (this.isDisabled()) return;
		double left = Input.getSecondaryInstance().getLeftTrigger();
		double right = Input.getSecondaryInstance().getRightTrigger();

		double change = left-right;
		if (!Robot.getInstance().noShooterLimit) {
			if (change > 0 && this.isLowerLimit()) {
				Input.getSecondaryInstance().rumbleTime(0.4f, RumbleType.kRightRumble, Duration.ofMillis(1000));
				armMotor.set(-0.2);
				return;
			}
			if (change < 0 && this.isUpperLimit()) {
				Input.getSecondaryInstance().rumbleTime(0.4f, RumbleType.kRightRumble, Duration.ofMillis(1000));
				armMotor.set(0);
				return;
			}

//			System.out.println("Angle: "+Encoders.getInstance().getShooterAngle() + " lower: "+isLowerLimit()+ " upper: "+isUpperLimit());

			armMotor.set(change);
		} else {
			armMotor.set(change);
		}

	}

	public boolean isLowerLimit() {
		return Encoders.getInstance().getShooterAngle() < LOWER_LIMIT;
	}

	public boolean isUpperLimit() {
		return Encoders.getInstance().getShooterAngle() > UPPER_LIMIT;
	}

	public void stopShooterMotors() {
		this.isBusy = false;
		this.left.set(0);
		this.right.set(0);
	}

	public void shoot() {
		this.shoot(1);
	}

	public void shoot(double speed) {
		if (Robot.getInstance().isDisabled() || this.isDisabled() || isBusy) return;
		isBusy = true;
		Input.getPrimaryInstance().rumbleTime(0.5f, RumbleType.kLeftRumble, Duration.ofMillis(2000));
		Input.getSecondaryInstance().rumbleTime(0.5f, RumbleType.kLeftRumble, Duration.ofMillis(2000));
		System.out.println("Firing!");
		left.set(-speed);
		right.set(speed);
		MasterTimer.getInstance().schedule(() -> {
			System.out.println("Kick!");
			kick.set(1);
			MasterTimer.getInstance().schedule(() -> {
				System.out.println("Stop!");
				left.set(0);
				right.set(0);
				kick.set(-0.2);
				MasterTimer.getInstance().schedule(() -> {
					kick.set(0);
					isBusy = false;
				}, Duration.ofMillis(400));
			}, Duration.ofMillis(200));
		}, Duration.ofMillis(1000));
	}

	public void pickup() {
		this.pickup(0.75);
	}

	public void pickup(double speed) {
		if (Robot.getInstance().isDisabled() || this.isDisabled() || isBusy) return;
		isBusy = true;
		left.set(speed);
		right.set(-speed);
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub

	}
}
