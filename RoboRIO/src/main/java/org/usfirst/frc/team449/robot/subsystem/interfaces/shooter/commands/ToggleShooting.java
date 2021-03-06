package org.usfirst.frc.team449.robot.subsystem.interfaces.shooter.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlCommandGroupWrapper;
import org.usfirst.frc.team449.robot.subsystem.interfaces.shooter.SubsystemShooter;

/**
 * Toggle whether or not the subsystem is firing.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class ToggleShooting extends YamlCommandGroupWrapper {


	/**
	 * Default constructor.
	 *
	 * @param subsystem The subsystem to execute this command on.
	 */
	@JsonCreator
	public ToggleShooting(@NotNull @JsonProperty(required = true) SubsystemShooter subsystem) {
		switch (subsystem.getShooterState()) {
			case OFF:
				addSequential(new SpinUpThenShoot(subsystem));
				break;
			case SHOOTING:
				addSequential(new TurnAllOff(subsystem));
				break;
			case SPINNING_UP:
				addSequential(new TurnAllOn(subsystem));
		}
	}
}
