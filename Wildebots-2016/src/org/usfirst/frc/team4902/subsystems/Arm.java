package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;
import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.Talon;

public class Arm extends Subsystem{
	
	private final double baseSegmentLength = 16.5 * 2.54;
	private final double secondSegmentLength = 21.75 * 2.54;
	
	private double offset = (1.5 - 1.0) * 2.54;
	
	
	
	private static Arm instance = new Arm();
	
	private Talon baseSegmentMotor = new Talon(PortMap.ArmBaseSegmentMotor.getPort());
	private Talon secondSegmentMotor = new Talon(PortMap.ArmSecondSegmentMotor.getPort());
	
	public Arm getInstance(){
		return instance;
	}

	@Override
	public void execute() {
		double baseSegmentSpeed = Input.getSecondaryInstance().getLeftYThreshold();
		double secondSegmentSpeed = Input.getSecondaryInstance().getRightYThreshold();
		
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
	public void setBaseSegmentAngle(){
		
	}
	
	public void setSecondSegmentAngle(){
		
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

}
