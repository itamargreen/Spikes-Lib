package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link BasicSubsystem} using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>.
 * It also waits a specified amount of time after the error is within the given
 * tolerance before stopping the PID loop to make sure the
 * {@link BasicSubsystem} doesn't go past the setpoint.
 * 
 * <br>
 * <br>
 * This command will try to move the basicSubsystem until it reaches the latest
 * value supplied by setpoint. The setpoint should use values using the same
 * units as the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources</a>.
 *
 * @author Omri "Riki" and Itamar Rivkind
 * @see BasicSubsystem
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 */
public class MoveBasicSubsystemWithPID extends Command {

	protected final BasicSubsystem basicSubsystem;
	protected final PIDSettings PIDSettings;
	protected final Supplier<Double> setpoint;
	protected final PIDSource source;
	protected PIDController movmentControl;
	protected double lastTimeNotOnTarget;

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>,
	 * a setpoint, the PID coefficients this command's PID loop should have, and the
	 * tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command operates on.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 *            
	 *            <p>
 	 *            This command will try to move basicSubsystem until it reaches
 	 *            the latest value supplied by setpoint. setpoint should supply
 	 *            values using the same units as source.
 	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveBasicSubsystemWithPID(BasicSubsystem basicSubsystem, PIDSource source, Supplier<Double> setpoint,
			PIDSettings PIDSettings) {
		requires(basicSubsystem);
		this.basicSubsystem = basicSubsystem;
		this.source = source;
		this.setpoint = setpoint;
		this.PIDSettings = PIDSettings;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * given by {@link BasicSubsystem#getPIDSource()}, a setpoint, the PID
	 * coefficients this command's PID loop should have, and the tolerance for
	 * error.
	 *
	 * @param BasicSubsystem
	 *            the {@link BasicSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *            
	 *            <p>
 	 *            This command will try to move basicSubsystem until it reaches
 	 *            the setpoint. setpoint should be using the same units as
 	 *            source.
 	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveBasicSubsystemWithPID(BasicSubsystem BasicSubsystem, PIDSource source, double setpoint,
			PIDSettings PIDSettings) {
		this(BasicSubsystem, source, () -> setpoint, PIDSettings);
	}

	/**
	 * Sets the time this command will wait while within tolerance of the setpoint
	 * before ending.
	 * 
	 * <br>
	 * <br>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command will not wait after reaching the
	 * setpoint.
	 *
	 * @param waitTime
	 *            the new wait time, in seconds. Positive values only.
	 *
	 * @see PIDSettings#getWaitTime()
	 */
	public void setWaitTime(double waitTime) {
		PIDSettings.setWaitTime(waitTime);
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * 
	 * <br>
	 * <br>
	 * This tolerance defines when this PID loop ends: this command will end after
	 * the difference between the setpoint and the current position is within the
	 * tolerance for the amount of time specified by {@link #setWaitTime(double)}.
	 * 
	 * <br>
	 * <br>
	 *
	 * @param tolerance
	 *            the tolerance in the same units as the {@link #source}.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public void setTolerance(double tolerance) {
		PIDSettings.setTolerance(tolerance);
	}

	/**
	 * Gets the {@link PIDSettings} the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 * uses for this command.
	 * 
	 * @return The PIDSettings object
	 */
	public PIDSettings getPIDSetting() {
		return PIDSettings;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				basicSubsystem::move);
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double newSetpoint = setpoint.get();
		if (movmentControl.getSetpoint() != newSetpoint)
			movmentControl.setSetpoint(newSetpoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!movmentControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= PIDSettings.getWaitTime();
	}

	// Called once after isFinished returns true
	protected void end() {
		movmentControl.disable();
		basicSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}