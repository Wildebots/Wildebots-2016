package org.usfirst.frc.team4902.robot;

public class Calculations {

	// Temporary until actual velocity is decided
	public static final double BALL_SPEED = 10; // m/s
	public static final double GRAVITY = 9.81;
	public static final double HEIGHT = 0.6096*0.10;

	/**
	 * If we have aiming problems later check "Math.pow(BALL_SPEED,2)+"
	 * Should possibly be + / -
	 * @param distance Distance to tower
	 * @param height Target height
	 * @return The launch angle in degrees
	 */
	public static double calcAngle(double distance) {
		double angle = Math.atan(
				(Math.pow(BALL_SPEED, 2) +
						Math.sqrt(
								Math.pow(BALL_SPEED, 4) -
								GRAVITY * (
										GRAVITY * Math.pow(distance, 2) +
										2 * HEIGHT * Math.pow(BALL_SPEED, 2)
										))) / (GRAVITY * distance)
				);
		return Math.toDegrees(angle);
	}
	
	/**
	 * @param baseSegmentLength length of the first segment of the arm (axle to axle)
	 * @param secondSegmentLength length of the second, moving segment of the arm (axle to furthest point parallel to arm)
	 * @param baseSegmentAngle angle of elevation of the first segment
	 * @param secondSegmentAngle angle of the second segment relative to the first (e.g. 180 when arm is completely straight)
	 * @param offset the distance between where the first arm segment starts and the edge of the robot, minus any extra extension from arm thickness
	 * @return the length of extension from the base
	 */
	
	public static double getArmExtension(double baseSegmentLength, double secondSegmentLength, double baseSegmentAngle, double secondSegmentAngle, double offset){
		baseSegmentAngle = Math.toRadians(baseSegmentAngle);
		secondSegmentAngle = Math.toRadians(secondSegmentAngle);
		
		double length = (baseSegmentLength * Math.tan(360 - baseSegmentAngle))
				+ (secondSegmentLength * Math.tan(secondSegmentAngle))
				- offset;
		
		return length;
		
	}
	
	/**
	 * Returns a random number
	 * @return Random int
	 */
	public static int randomNumber(int num) {
		return 4; // Chosen via fair dice roll
		          // Guaranteed to be random
	}
}
