package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

/**
 * This command is used to orient an instance of {@link TankDrivetrain} to a specific setpoint
 * using wpilib's {@link PIDController}. This command is like
 * {@link DriveArcadeWithPID} but doesn't move the {@link TankDrivetrain}
 * forwards and ends when reaching the wanted setpoint.
 *
 * @see TankDrivetrain
 * @see PIDController
 * @see DriveArcadeWithPID
 *
 * @author Simon "C" Kharmatsky
 */
public class OrientWithPID extends DriveArcadeWithPID {

	private double lastTimeOnTarget = 0;

	/**
	 * This constructs new {@link OrientWithPID} command using
	 * {@link PIDSource}, {@link Supplier<Double>} for the setpoint and the
	 * {@link PIDSettings} for the command.
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command operates on
	 * @param PIDSource
	 *            the {@link PIDSource} that is used by the
	 *            {@link PIDController} to get feedback about the robot's
	 *            current state
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the setpoint of the
	 *            {@link PIDController}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output. For example, gyro's is 360.
	 *            Camera that has 640 px on the wanted axis has output range of
	 *            640, and one that its values range was scaled between -1 and 1 has output range
	 *            of 2 and so on.
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			PIDSettings PIDSettings, double outputRange) {
		super(drivetrain, PIDSource, setpointSupplier, () -> 0.0, PIDSettings, outputRange);
	}

	/**
	 * This constructs new {@link OrientWithPID} command with constant value for
	 * {@link OrientWithPID#setpointSupplier} using {@link PIDController},
	 * {@link Double} for the setpoint and {@link PIDController} for the command
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command operates on
	 * @param PIDSource
	 *            the {@link PIDSource} that is used by the
	 *            {@link PIDController} to get feedback about the robot's
	 *            current state
	 * @param setpoint
	 *            constant value for {@link OrientWithPID#setpointSupplier}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output. For example, gyro's is 360.
	 *            Camera that has 640 px on the wanted axis has output range of
	 *            640, and one that its values range was scaled between -1 and 1 has output range
	 *            of 2 and so on.
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, PIDSettings PIDSettings,
			double outputRange) {
		this(drivetrain, PIDSource, () -> setpoint, PIDSettings, outputRange);
	}

	@Override
	protected boolean isFinished() {
		if (!rotationController.onTarget())
			lastTimeOnTarget = Timer.getFPGATimestamp();
		return Timer.getFPGATimestamp() - lastTimeOnTarget >= PIDSettings.getWaitTime() || isTimedOut();
	}
}
