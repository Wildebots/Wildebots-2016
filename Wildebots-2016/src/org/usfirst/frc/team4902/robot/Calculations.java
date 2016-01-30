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
				(Math.pow(BALL_SPEED, 2)+
						Math.sqrt(
								Math.pow(BALL_SPEED, 4)-
								GRAVITY*(
										GRAVITY*Math.pow(distance, 2)+
										2*HEIGHT*Math.pow(BALL_SPEED, 2)
										))) / (GRAVITY*distance)
				);
		return Math.toDegrees(angle);
	}

}
