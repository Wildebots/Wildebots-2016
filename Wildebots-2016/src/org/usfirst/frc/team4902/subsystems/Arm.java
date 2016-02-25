package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;
import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.Talon;

public class Arm extends Subsystem{
	
	private final double baseSegmentLength = 54.9; // cm
	private final double secondSegmentLength = 41.9; // cm
	
	private final double baseStartingAngle = 14.8;
	private final double secondStartingAngle = 75.2;
	
	private final double offset = 6.2; // cm
	
	private final  double SPEED_ADJUSTMENT = 0.8;
	
	private static Arm instance = new Arm();
	
	private Talon baseSegmentMotor = new Talon(PortMap.ArmBaseSegmentMotor.getPort());
	private Talon secondSegmentMotor = new Talon(PortMap.ArmSecondSegmentMotor.getPort());
	
//	private PIDController baseSegmentPID = new PIDController(0.05,0.0,0.5,Encoders.getInstance().getArmBaseSegmentEncoder(),baseSegmentMotor);
//	private PIDController secondSegmentPID = new PIDController(0.05,0.0,0.5,Encoders.getInstance().getArmSecondSegmentEncoder(),secondSegmentMotor);
	
	
	public static Arm getInstance(){
		return instance;
	}

	@Override
	public void execute() {
		double baseSegmentSpeed = Input.getSecondaryInstance().getLeftYThreshold() * SPEED_ADJUSTMENT;
		double secondSegmentSpeed = Input.getSecondaryInstance().getRightYThreshold() * SPEED_ADJUSTMENT;
		
		if (this.isInLegalPosition()){
			baseSegmentMotor.set(-baseSegmentSpeed);
			secondSegmentMotor.set(secondSegmentSpeed);
		}
		
		else{
			System.out.println("REACHED LIMIT!");
			baseSegmentMotor.set(0);
			secondSegmentMotor.set(0);
		}
	}
	
	// Will be used in autonomous portcullis crossing
	public void setBaseSegmentAngle(double angle){
		
//		double threshold = 2.5;
//		
//		double delta = angle - Encoders.getInstance().getBaseSegmentAngle();
//		
//		if (Math.abs(delta)>threshold){
//			baseSegmentMotor.set(delta/Math.abs(delta)/10);
//		}
//		else{
//			baseSegmentMotor.set(0);
//		}
		
//		baseSegmentPID.setSetpoint(angle);
		
	}
	
	public void setSecondSegmentAngle(double angle){
		
//		double threshold = 2.5;
//		
//		double delta = angle - Encoders.getInstance().getSecondSegmentAngle();
//		
//		if (Math.abs(delta)>threshold) {
//			secondSegmentMotor.set(delta/Math.abs(delta)/10);
//		}
//		else {
//			secondSegmentMotor.set(0);
//		}
		
//		secondSegmentPID.setSetpoint(angle);
		
	}
	
	public boolean isInLegalPosition(){
		
		final double MAX_EXTENSION = 38.1; // cm (15 inches (max) in centimetres)
		
		double baseSegmentAngle = Encoders.getInstance().getBaseSegmentAngle() + baseStartingAngle;
		double secondSegmentAngle = Encoders.getInstance().getSecondSegmentAngle() + secondStartingAngle;
		
		System.out.print("Base Angle: " + baseSegmentAngle);
		System.out.print(" - Second Angle: " + secondSegmentAngle);
		
		double ext =  Calculations.getArmExtension(baseSegmentLength, secondSegmentLength, baseSegmentAngle, secondSegmentAngle, offset);
		
		System.out.println(" - Extension: " + ext);
		
		if (ext > MAX_EXTENSION-2){
			return false;
		} 
		else {
			return true;
		}
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
	}
	
	public Object manipulate(Object o) {
		System.out.println(o.toString() + " was Manipulated!");
		return instance.hashCode()*43223523;
	}

}
