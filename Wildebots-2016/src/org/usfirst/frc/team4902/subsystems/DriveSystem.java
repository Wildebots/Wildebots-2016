package org.usfirst.frc.team4902.subsystems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DriveSystem extends Subsystem {
	
	public static final double FRONT_ULTRASONIC_LIMIT = 20,
			SIDE_ULTRASONIC_LIMIT = 20;

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
		double leftY = -Input.getSecondaryInstance().getLeftYThreshold(), rightY = -Input.getSecondaryInstance().getRightYThreshold();
		//TODO: Review limit constants and check logic again?
		if (isFrontLimit() && (leftY > 0 && rightY > 0)) return;
		if (isSideLimit() && (leftY > 0 && rightY < 0)) return;
		
		drive.tankDrive(leftY, rightY);
	}
	
	public boolean isFrontLimit() {
		return Autonomous.inRangeVariance(Ultrasonics.getInstance().getForwardDistance(), FRONT_ULTRASONIC_LIMIT, 1);
	}
	
	public boolean isSideLimit() {
		return Autonomous.inRangeVariance(Ultrasonics.getInstance().getSideDistance(), SIDE_ULTRASONIC_LIMIT, 1);
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
		
		if (ex.isShutdown()) ex = Executors.newFixedThreadPool(1);
		
		Thread RotateThread = new Thread(() -> {
			
			isBusy = true;
			
			double angle = Gyrometer.getInstance().getAngle();
			double diff = degrees-(angle % 360);
			
			if (diff > 180) {
				diff = diff-360;
			}
			
			diff = Math.toRadians(diff/2);
			
			int count = 0;
			while (!Autonomous.inRange((Gyrometer.getInstance().getAngle() > 360) ? Gyrometer.getInstance().getAngle()%360 : Gyrometer.getInstance().getAngle(), degrees, 40)) {
				System.out.println((Gyrometer.getInstance().getAngle() > 360) ? Gyrometer.getInstance().getAngle()%360 : Gyrometer.getInstance().getAngle() + " : " + degrees);
				System.out.println("ROTATTINGGGGG" + count);
				count++;
				drive.tankDrive(Math.sin(diff)*2.0, Math.sin(-diff)*2.0);
			}
			
			drive.tankDrive(0, 0);
			
			isBusy = false;
			
		});
		
		RotateThread.setDaemon(true);
		
		Future<Boolean> future = ex.submit(RotateThread, null);
		
		return future;
		
	}
	
	private ExecutorService ex = Executors.newFixedThreadPool(1);
	
	/**
	 * Emergency or when disabled only
	 */
	public void stopRotate() {
		ex.shutdownNow();
		isBusy = false;
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
