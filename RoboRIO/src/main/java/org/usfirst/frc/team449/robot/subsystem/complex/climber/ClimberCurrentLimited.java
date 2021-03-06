package org.usfirst.frc.team449.robot.subsystem.complex.climber;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.generalInterfaces.loggable.Loggable;
import org.usfirst.frc.team449.robot.generalInterfaces.simpleMotor.SimpleMotor;
import org.usfirst.frc.team449.robot.jacksonWrappers.FPSTalon;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlSubsystem;
import org.usfirst.frc.team449.robot.other.BufferTimer;
import org.usfirst.frc.team449.robot.subsystem.interfaces.binaryMotor.SubsystemBinaryMotor;
import org.usfirst.frc.team449.robot.subsystem.interfaces.conditional.SubsystemConditional;

/**
 * A climber subsystem that uses power monitoring to stop climbing.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class ClimberCurrentLimited extends YamlSubsystem implements Loggable, SubsystemBinaryMotor, SubsystemConditional {

	/**
	 * The CANTalon controlling one of the climber motors.
	 */
	@NotNull
	private final FPSTalon canTalonSRX;

	/**
	 * The other climber motor.
	 */
	@Nullable
	private final SimpleMotor simpleMotor;

	/**
	 * The maximum allowable power before we stop the motor.
	 */
	private final double maxPower;

	/**
	 * The bufferTimer controlling how long we can be above the power limit before we stop climbing.
	 */
	@NotNull
	private final BufferTimer powerLimitTimer;

	/**
	 * Whether or not the motor is currently spinning.
	 */
	private boolean motorSpinning;


	/**
	 * Default constructor
	 *
	 * @param talonSRX        The CANTalon controlling one of the climber motors.
	 * @param maxPower        The maximum power at which the motor won't shut off.
	 * @param simpleMotor     The other climber motor. Can be null.
	 * @param powerLimitTimer The buffer timer for the power-limited shutoff.
	 */
	@JsonCreator
	public ClimberCurrentLimited(@NotNull @JsonProperty(required = true) FPSTalon talonSRX,
	                             @JsonProperty(required = true) double maxPower,
	                             @Nullable SimpleMotor simpleMotor,
	                             @NotNull @JsonProperty(required = true) BufferTimer powerLimitTimer) {
		//Instantiate things
		this.canTalonSRX = talonSRX;
		this.maxPower = maxPower;
		this.powerLimitTimer = powerLimitTimer;
		this.simpleMotor = simpleMotor;
		this.motorSpinning = false;
	}

	/**
	 * Initialize the default command for a subsystem By default subsystems have no default command, but if they do, the
	 * default command is set with this method. It is called on all Subsystems by CommandBase in the users program after
	 * all the Subsystems are created.
	 */
	@Override
	protected void initDefaultCommand() {
		//Do nothing!
	}

	/**
	 * Set the percent voltage to be given to the motor.
	 *
	 * @param percentVbus The voltage to give the motor, from -1 to 1.
	 */
	private void setPercentVbus(double percentVbus) {
		canTalonSRX.setPercentVoltage(percentVbus);
		if (simpleMotor != null) {
			simpleMotor.setVelocity(percentVbus);
		}
	}

	/**
	 * Get the headers for the data this subsystem logs every loop.
	 *
	 * @return An N-length array of String labels for data, where N is the length of the Object[] returned by getData().
	 */
	@NotNull
	@Override
	public String[] getHeader() {
		return new String[]{"current",
				"voltage",
				"power"};
	}

	/**
	 * Get the data this subsystem logs every loop.
	 *
	 * @return An N-length array of Objects, where N is the number of labels given by getHeader.
	 */
	@NotNull
	@Override
	public Object[] getData() {
		return new Object[]{canTalonSRX.getOutputCurrent(),
				canTalonSRX.getOutputVoltage(),
				canTalonSRX.getOutputCurrent() * canTalonSRX.getOutputVoltage()};
	}

	/**
	 * Get the name of this object.
	 *
	 * @return A string that will identify this object in the log file.
	 */
	@NotNull
	@Override
	public String getName() {
		return "climber";
	}

	/**
	 * Turns the motor on, and sets it to a map-specified speed.
	 */
	@Override
	public void turnMotorOn() {
		canTalonSRX.enable();
		setPercentVbus(1);
		motorSpinning = true;
	}

	/**
	 * Turns the motor off.
	 */
	@Override
	public void turnMotorOff() {
		setPercentVbus(0);
		canTalonSRX.disable();
		motorSpinning = false;
	}

	/**
	 * @return true if the motor is on, false otherwise.
	 */
	@Override
	public boolean isMotorOn() {
		return motorSpinning;
	}

	/**
	 * @return true if the condition is met, false otherwise
	 */
	@Override
	public boolean isConditionTrue() {
		return powerLimitTimer.get(Math.abs(canTalonSRX.getOutputCurrent() * canTalonSRX.getOutputVoltage()) > maxPower);
	}
}