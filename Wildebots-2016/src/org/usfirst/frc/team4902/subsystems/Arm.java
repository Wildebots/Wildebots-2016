package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;
import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

public class Arm extends Subsystem{
	
	private final double baseSegmentLength = 16.5 * 2.54;
	private final double secondSegmentLength = 21.75 * 2.54;
	
	private final double offset = (1.5 - 1.0) * 2.54;
	
	private final  double SPEED_ADJUSTMENT = 0.05;
	
	private static Arm instance = new Arm();
	
	private Talon baseSegmentMotor = new Talon(PortMap.ArmBaseSegmentMotor.getPort());
	private Talon secondSegmentMotor = new Talon(PortMap.ArmSecondSegmentMotor.getPort());
	
	private PIDController baseSegmentPID = new PIDController(0.05,0.0,0.5,Encoders.getInstance().getArmBaseSegmentEncoder(),baseSegmentMotor);
	private PIDController secondSegmentPID = new PIDController(0.05,0.0,0.5,Encoders.getInstance().getArmSecondSegmentEncoder(),secondSegmentMotor);
	
	
	public Arm getInstance(){
		return instance;
	}

	@Override
	public void execute() {
		double baseSegmentSpeed = Input.getSecondaryInstance().getLeftYThreshold() * SPEED_ADJUSTMENT;
		double secondSegmentSpeed = Input.getSecondaryInstance().getRightYThreshold() * SPEED_ADJUSTMENT;
		
		if (isInLegalPosition()){
			baseSegmentMotor.set(baseSegmentSpeed);
			secondSegmentMotor.set(secondSegmentSpeed);
		}
		
		else{
			baseSegmentMotor.set(-baseSegmentSpeed);
			secondSegmentMotor.set(-secondSegmentSpeed);
		}
	}
	
	// will be used in autonomous portcullis crossing
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
		
		baseSegmentPID.setSetpoint(angle);
		
	}
	
	public void setSecondSegmentAngle(double angle){
		
//		double threshold = 2.5;
//		
//		double delta = angle - Encoders.getInstance().getSecondSegmentAngle();
//		
//		if (Math.abs(delta)>threshold){
//			secondSegmentMotor.set(delta/Math.abs(delta)/10);
//		}
//		else{
//			secondSegmentMotor.set(0);
//		}
		
		secondSegmentPID.setSetpoint(angle);
		
	}
	
	public boolean isInLegalPosition(){
		
		final double MAX_EXTENSION = 38.1; //15 inches * 2.54
		
		double baseSegmentAngle = Encoders.getInstance().getBaseSegmentAngle();
		double secondSegmentAngle = Encoders.getInstance().getSecondSegmentAngle();
		
		double ext =  Calculations.getArmExtension(baseSegmentLength, secondSegmentLength, baseSegmentAngle, secondSegmentAngle, offset);
		
		if (ext > MAX_EXTENSION-2){
			return false;
		}
		else{
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
