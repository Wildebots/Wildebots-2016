package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DriveSystem extends Subsystem {

	private static DriveSystem instance = new DriveSystem();

	// Attach motors to ports
	Talon LeftFront = new Talon(PortMap.LeftFrontMotor.getPort()),
			LeftBack = new Talon(PortMap.LeftBackMotor.getPort()),
			RightFront = new Talon(PortMap.RightFrontMotor.getPort()),
			RightBack = new Talon(PortMap.RightBackMotor.getPort());

	// Attach motors to drive train
	private RobotDrive drive = new RobotDrive(LeftFront, LeftBack, RightFront, RightBack);

	/**
	 * A constant to tweak how quickly the robot will adjust back to straight.
	 */
	private final double ADJUSTMENT_SPEED_CONSTANT = 1.5;
	private final double ADJUSTMENT_SPEED_CONSTANT_TWO = 0.01;;
	private double lastAngle = 0;
	private double currentAngle = 0;
	private double correction = 0;
	
	
	public static DriveSystem getInstance() {
		return instance;
	}

	@Override
	public void execute() {
		double leftY = Input.getPrimaryInstance().getLeftYThreshold(), rightY = Input.getPrimaryInstance().getRightYThreshold();
		drive.tankDrive(-leftY, -rightY);
	}

	public synchronized void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void setLeft(double speed) {
		LeftFront.set(speed);
		LeftBack.set(speed);
	}
	
	public void setBack(double speed) {
		RightFront.set(speed);
		RightBack.set(speed);
	}
	
	public void setSpeed(double speed) {
		drive.tankDrive(speed, speed);
	}

	public void log(){
		
	}

	/**
	 * Rotates the robot on the place
	 * @param degrees (negative for clockwise, positive for counterclockwise)
	 */
	public void rotate(int degrees) {
//		Thread RotateThread = new Thread(() -> {
//			double angle = Gyrometer.getInstance().getAngle();
//			if (degrees<0)
//				while (Gyrometer.getInstance().getAngle() < angle+degrees) {
//					this.tankDrive(-0.5, 0.5);
//				}
//			else{
//				while (Gyrometer.getInstance().getAngle()> angle+degrees){
//					this.tankDrive(0.5,-0.5);
//				}
//			}
//		});
//		RotateThread.setDaemon(true);
//		RotateThread.start();
		if (Autonomous.inRange(Gyrometer.getInstance().getAngle(), 90, 0.5)) {
			drive.tankDrive(0, 0);
			return;
		}
		
		double diff = degrees-Gyrometer.getInstance().getAngle();
		
		if (diff > 180) {
			diff = diff-360;
		}
		
		diff = Math.toRadians(diff);
		
		drive.tankDrive(Math.sin(diff), Math.sin(-diff));
		
		return;
		
	}
	
	public void driveStraight(double speed){
		
		double angle = Math.toRadians(Gyrometer.getInstance().getAngle());
		
		drive.tankDrive(speed + Math.sin(angle) * ADJUSTMENT_SPEED_CONSTANT, speed - Math.sin(angle) * ADJUSTMENT_SPEED_CONSTANT);
	}
	
	public void driveStraightTwo(double speed){
		
		lastAngle = currentAngle;
		currentAngle = Math.toRadians(Gyrometer.getInstance().getAngle());
		correction+= (currentAngle - lastAngle)*ADJUSTMENT_SPEED_CONSTANT_TWO;
		if (currentAngle<0){
			drive.tankDrive(speed-correction,speed);
		}
		if (currentAngle>0){
			drive.tankDrive(speed,speed-correction);
		}
	}
}
