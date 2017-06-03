package org.usfirst.frc.team449.robot.interfaces.subsystem.Shooter.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team449.robot.interfaces.subsystem.Shooter.ShooterSubsystem;
import org.usfirst.frc.team449.robot.util.Logger;

/**
 * Turn off the shooter and feeder, using requires() to interrupt any other commands that may be telling them to
 * continue running.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class TurnAllOffWithRequires extends TurnAllOff {

	/**
	 * Default constructor
	 *
	 * @param subsystem The subsystem to execute this command on.
	 */
	@JsonCreator
	public <T extends Subsystem & ShooterSubsystem> TurnAllOffWithRequires(@JsonProperty(required = true) T subsystem) {
		super(subsystem);
		requires(subsystem);
	}
}