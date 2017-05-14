package org.usfirst.frc.team449.robot.interfaces.oi;

/**
 * An arcade-style dual joystick OI.
 */
public abstract class ArcadeOI implements WestCoastOI{
	/**
	 * @return rotational velocity component from [-1, 1], where 1 is right and -1 is left.
	 */
	public abstract double getRot();

	/**
	 * @return forward velocity component from [-1, 1], where 1 is forwards and -1 is backwards
	 */
	public abstract double getFwd();

	/**
	 * Map all buttons to commands. Should only be run after all subsystems have been instantiated.
	 */
	public abstract void mapButtons();

	/**
	 * The output to be given to the left side of the drive.
	 * @return Output to left side from [-1, 1]
	 */
	public double getLeftOutput(){
		return getFwd() + getRot();
	}

	/**
	 * The output to be given to the right side of the drive.
	 * @return Output to right side from [-1, 1]
	 */
	public double getRightOutput(){
		return getFwd() - getRot();
	}
}
