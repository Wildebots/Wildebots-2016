package org.usfirst.frc.team4902.subsystems;

import java.time.Duration;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.MasterTimer;
import org.usfirst.frc.team4902.robot.PortMap;
import org.usfirst.frc.team4902.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem extends Subsystem {
	
	public static final double LOWER_LIMIT = -85, UPPER_LIMIT = -5;

	private static ShooterSystem instance = new ShooterSystem();

	private volatile boolean isBusy = false;

	private Victor left = new Victor(PortMap.LeftShooter.getPort()),
			right = new Victor(PortMap.RightShooter.getPort()),
			kick = new Victor(PortMap.Kicker.getPort()),
			armMotor = new Victor(PortMap.ArmMotor.getPort());

	private PIDController shooterArm = new PIDController(0.05, 0.0, 0.5, Encoders.getInstance().getShooterEncoder(), armMotor); // TODO: Get actual PID values

	public static ShooterSystem getInstance(){
		return instance;
	}

	/**
	 * sets the angle of the arm to a specified value
	 * @param angle The desired angle to set the arm to
	 */
	public void setAngle(double angle) {
		int count = (int) ((angle / 360.0) * 497 * 18);
		System.out.println("angle count for pid: "+count+" : angle: "+angle);
		shooterArm.setSetpoint(count);
	}

	@Override
	public void execute() {
		double left = Input.getPrimaryInstance().getLeftTrigger();
		double right = Input.getPrimaryInstance().getRightTrigger();

		double change = left-right;
		
		if (change < 0 && this.isLowerLimit()) return;
		if (change > 0 && this.isUpperLimit()) return;
		
		System.out.println("Angle: "+Encoders.getInstance().getShooterAngle() + " lower: "+isLowerLimit()+ " upper: "+isUpperLimit());
		
		armMotor.set(left-right);

		//		if (left > right) {
		//			armMotor.set(-left);
		//		} else if (right > left) {
		//			armMotor.set(right);
		//		} else if (left == 0 && right == 0) {
		//			armMotor.set(0);
		//		}

	}
	 
	public boolean isLowerLimit() {
		return Autonomous.inRangeVariance(Encoders.getInstance().getShooterAngle(), LOWER_LIMIT, 1);
	}
	
	public boolean isUpperLimit() {
		return Autonomous.inRangeVariance(Encoders.getInstance().getShooterAngle(), UPPER_LIMIT, 1);
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
		if (Robot.getInstance().isDisabled() || isBusy) return;
		isBusy = true;
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
		if (Robot.getInstance().isDisabled() || isBusy) return;
		isBusy = true;
		left.set(speed);
		right.set(-speed);
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub

	}
}
