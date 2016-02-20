package org.usfirst.frc.team4902.subsystems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	
	private volatile boolean isBusy = false;
	
	public static DriveSystem getInstance() {
		return instance;
	}

	@Override
	public void execute() {
		if (isBusy) return;
		double leftY = Input.getInstance().getLeftYThreshold(), rightY = Input.getInstance().getRightYThreshold();
		drive.tankDrive(-leftY, -rightY);
	}

	public synchronized void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void setLeft(double speed) {
		if (isBusy) return;
		LeftFront.set(speed);
		LeftBack.set(speed);
	}
	
	public void setBack(double speed) {
		if (isBusy) return;
		RightFront.set(speed);
		RightBack.set(speed);
	}
	
	public void setSpeed(double speed) {
		if (isBusy) return;
		drive.tankDrive(speed, speed);
	}

	public void log(){
		
	}

	/**
	 * Rotates the robot on the place
	 * @param degrees (negative for clockwise, positive for counterclockwise)
	 */
	public Future<Boolean> rotate(int degrees) {
		if (isBusy) return null;
		Thread RotateThread = new Thread(() -> {
			
			isBusy = true;
			
			double angle = Gyrometer.getInstance().getAngle();
			double diff = degrees-(angle%360);
			
			if (diff > 180) {
				diff = diff-360;
			}
			
			diff = Math.toRadians(diff/2);
			
			while (!Autonomous.inRange(Gyrometer.getInstance().getAngle(), degrees, 1)) {
				drive.tankDrive(Math.sin(diff), Math.sin(-diff));
			}
			
			drive.tankDrive(0, 0);
			
			isBusy = false;
			
		});
		
		RotateThread.setDaemon(true);
		
		ExecutorService ex = Executors.newFixedThreadPool(1);
		
		Future<Boolean> future = ex.submit(RotateThread, null);
		
		return future;
		
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
