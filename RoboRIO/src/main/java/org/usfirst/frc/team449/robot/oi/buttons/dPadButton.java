package org.usfirst.frc.team449.robot.oi.buttons;

import edu.wpi.first.wpilibj.Joystick;
import org.jetbrains.annotations.NotNull;

/**
 * A Button triggered by pushing the D-pad to a specific angle.
 */
public class dPadButton extends FactoryButton {

	/**
	 * The angle that the D-pad must be pushed to to trigger this button.
	 */
	private final int angle;

	/**
	 * The joystick with the relevant D-pad on it.
	 */
	@NotNull
	private final Joystick joystick;

	/**
	 * Explicit argument constructor.
	 *
	 * @param joystick The joystick with the D-pad.
	 * @param angle    The angle that the D-pad must be pushed to to trigger this button.
	 */
	dPadButton(@NotNull Joystick joystick, int angle) {
		this.angle = angle;
		this.joystick = joystick;
	}

	/**
	 * Get whether this button is pressed
	 *
	 * @return true if the joystick's D-pad is pressed to the given angle, false otherwise.
	 */
	@Override
	public boolean get() {
		return joystick.getPOV() == angle;
	}
}
