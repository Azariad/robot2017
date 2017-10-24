package org.usfirst.frc.team449.robot.subsystem.interfaces.navX;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kauailabs.navx.frc.AHRS;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedAHRS;

/**
 * A subsystem that has a navX on it.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public interface SubsystemNavX {

	/**
	 * Get the robot's heading using the navX
	 *
	 * @return robot heading, in degrees, on [-180, 180]
	 */
	double getGyroHeading();

	/**
	 * @return true if the navX is currently overriden, false otherwise.
	 */
	boolean getOverrideNavX();

	/**
	 * @param override true to override the navX, false to un-override it.
	 */
	void setOverrideNavX(boolean override);

	/**
	 * @param headingDegrees The angle, in degrees from [-180, 180], to set the NavX's heading to.
	 */
	void setHeading(double headingDegrees);

	/**
	 * @return An AHRS object representing this subsystem's navX.
	 */
	@NotNull
	MappedAHRS getNavX();
}
