package org.usfirst.frc.team449.robot.oi.throttles;

import com.fasterxml.jackson.annotation.*;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedJoystick;

/**
 * A throttle with a deadband.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "@class")
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class ThrottleDeadbanded extends ThrottleBasic {

	/**
	 * The value below which the joystick input is considered 0.
	 */
	protected final double deadband;

	/**
	 * The input from the joystick. Declared outside of getValue to avoid garbage collection.
	 */
	private double input;

	/**
	 * The sign of the input from the joystick. Declared outside of getValue to avoid garbage collection.
	 */
	private double sign;

	/**
	 * A basic constructor.
	 *
	 * @param stick    The Joystick object being used
	 * @param axis     The axis being used. 0 is X, 1 is Y, 2 is Z.
	 * @param deadband The deadband below which the input will be read as 0, on [0, 1]. Defaults to 0.
	 * @param inverted Whether or not to invert the joystick input. Defaults to false.
	 */
	@JsonCreator
	public ThrottleDeadbanded(@NotNull @JsonProperty(required = true) MappedJoystick stick,
	                          @JsonProperty(required = true) int axis,
	                          double deadband,
	                          boolean inverted) {
		super(stick, axis, inverted);
		this.deadband = deadband;
	}

	/**
	 * Gets the value from the joystick and deadbands it. The non-deadband values are scaled to avoid a discontinuity,
	 * so the graph of joystick input to deadbanded output looks like this:
	 * <p>
	 * _/ /
	 *
	 * @return The joystick's value, after being deadbanded.
	 */
	@Override
	public double getValue() {
		//Get the smoothed value
		input = super.getValue();

		sign = Math.signum(input);
		input = Math.abs(input);

		//apply the deadband.
		if (input < deadband) {
			return 0;
		}

		//scale so f(deadband) is 0 and f(1) is 1.
		input = (input - deadband) / (1. - deadband);

		return sign * input;
	}
}