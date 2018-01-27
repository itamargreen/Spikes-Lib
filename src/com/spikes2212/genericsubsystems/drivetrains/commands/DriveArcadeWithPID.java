package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

/**
 * This command turns {@link TankDrivetrain} instance with wpilib's
 * {@link PIDController} using the output from {@link PIDSource} and moves it
 * forward using {@link Supplier} and {@link TankDrivetrain#arcadeDrive}. This
 * class can be used to force the instance of {@link TankDrivetrain} move strait
 * by giving a setpoint of 0.
 *
 * @see PIDController
 * @see TankDrivetrain
 * @author Simon "C" Kharmatsky
 */
public class DriveArcadeWithPID extends OrientWithPID {

	private TankDrivetrain drivetrain;
	private PIDSource source;
	private Supplier<Double> setpointSupplier;
	private Supplier<Double> speedSupplier;
	private Supplier<Boolean> isFinishedSupplier;
	private PIDSettings settings;

	private PIDController angleController;

	/**
	 * This constructs a new {@link DriveArcadeWithPID} using {@link PIDSource},
	 * {@link Supplier<Double>}s for the setpoint and the speed, and the
	 * {@link PIDSettings} for this command
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param source
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID}'s degree
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the degree the robot has to be at
	 * @param speedSupplier
	 *            {@link Supplier<Double>} supplier of the speed for
	 *            {@link TankDrivetrain#arcadeDrive}
	 * @param isFinishedSupplier
	 *            {@link Supplier<Boolean>} that checks if the command is
	 *            finished
	 * @param settings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource source, Supplier<Double> setpointSupplier,
			Supplier<Double> speedSupplier, Supplier<Boolean> isFinishedSupplier, PIDSettings settings) {
		super(drivetrain, source, setpointSupplier, settings);
		this.setpointSupplier = setpointSupplier;
		this.speedSupplier = speedSupplier;
		this.isFinishedSupplier = isFinishedSupplier;
	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} using static values for
	 * {@link DriveArcadeWithPID#setpointSupplier} and
	 * {@link DriveArcadeWithPID#speedSupplier} instead of
	 * {@link Supplier<Double>}s
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param source
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s degree
	 * @param setpoint
	 *            static value for {@link DriveArcadeWithPID#setpointSupplier}
	 * @param speed
	 *            static value for {@link DriveArcadeWithPID#speedSupplier}
	 * @param isFinishedSupplier
	 *            {@link Supplier<Boolean>} that checks if the command is
	 *            finished
	 * @param settings
	 * @link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource source, double setpoint, double speed,
			Supplier<Boolean> isFinishedSupplier, PIDSettings settings) {
		this(drivetrain, source, () -> setpoint, () -> speed, isFinishedSupplier, settings);
	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} ignoring the
	 * {@link DriveArcadeWithPID#isFinishedSupplier}
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 *
	 * @param source
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s degree
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the degree the robot has to be at
	 * @param speedSupplier
	 *            supplier of the speed for {@link TankDrivetrain#arcadeDrive}
	 * @param settings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource source, Supplier<Double> setpointSupplier,
			Supplier<Double> speedSupplier, PIDSettings settings) {
		this(drivetrain, source, setpointSupplier, speedSupplier, () -> false, settings);

	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} ignoring the
	 * {@link DriveArcadeWithPID#isFinishedSupplier} and uses constant values
	 * for {@link DriveArcadeWithPID#setpointSupplier} and
	 * {@link DriveArcadeWithPID#speedSupplier}
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param source
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s degree
	 * @param setpoint
	 *            constant value for {@link DriveArcadeWithPID#setpointSupplier}
	 * @param speed
	 *            constant value for {@link DriveArcadeWithPID#speedSupplier}
	 * @param settings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource source, double setpoint, double speed,
			PIDSettings settings) {
		this(drivetrain, source, () -> setpoint, () -> speed, settings);
	}

	@Override
	protected void initialize() {
		this.angleController = new PIDController(settings.getKP(), settings.getKI(), settings.getKD(), source,
				(rotate) -> drivetrain.arcadeDrive(speedSupplier.get(), rotate));
		angleController.setAbsoluteTolerance(settings.getTolerance());
		angleController.setSetpoint(setpointSupplier.get());
		angleController.setOutputRange(-1.0, 1.0);
		angleController.enable();
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut() || isFinishedSupplier.get();
	}
}