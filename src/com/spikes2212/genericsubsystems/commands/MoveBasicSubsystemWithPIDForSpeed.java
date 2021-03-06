package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This command makes a {@link BasicSubsystem} using wpilib's
 * {@link PIDController} with a given speed and not a given voltage. After
 * reaching the wanted speed it sticks to this speed until this command is
 * stoped.
 *
 * @author Omri "Riki"
 * @see BasicSubsystem
 * @see MoveBasicSubsystemWithPID
 * @see PIDController
 * @see PIDSettings
 */
public class MoveBasicSubsystemWithPIDForSpeed extends MoveBasicSubsystemWithPID {

	private double acceleration;

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPIDForSpeed} using a
	 * {@link PIDSource}, the wanted speed, the PID coefficients this command's
	 * PID loop should have, and the tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param wantedSpeed
	 *            a supplier supplying the speed the {@link BasicSubsystem}
	 *            should move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */
	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source,
			Supplier<Double> wantedSpeed, PIDSettings PIDSettings, double acceleration) {
		super(basicSubsystem, source, wantedSpeed, PIDSettings);
		this.acceleration = acceleration;
		this.source.setPIDSourceType(PIDSourceType.kRate);
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPIDForSpeed} using a
	 * {@link PIDSource}, the wanted speed, the PID coefficients this command's
	 * PID loop should have, and the tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param wantedSpeed
	 *            the speed the {@link BasicSubsystem} should move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */
	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source, double wantedSpeed,
			PIDSettings PIDSettings, double acceleration) {
		this(basicSubsystem, source, () -> wantedSpeed, PIDSettings, acceleration);
	}

	@Override
	protected void initialize() {
		/*
		 * in PID for speed instead of the PIDController changing the speed to
		 * get closer to the wanted location, the PIDController is changing the
		 * voltage to get closer to the wanted speed. It does that by adding to
		 * the current voltage to get to the wanted speed.
		 */
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				(additionalSpeed) -> basicSubsystem.move(basicSubsystem.getSpeed() + additionalSpeed * acceleration));
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	@Override
	protected boolean isFinished() {
		return false; // The subsystem should not stop when reaching the wanted
						// speed.
	}

}
