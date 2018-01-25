package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDSource;

public class TurnToGlobalAngleWithPID extends TurnToRelativeAngleWithPID{

	public TurnToGlobalAngleWithPID(TankDrivetrain drivetrain, PIDSource source, Supplier<Double> setpointSupplier,
			PIDSettings settings) {
		super(drivetrain, source, () -> setpointSupplier.get() - source.pidGet(), settings);
	}
	
	public TurnToGlobalAngleWithPID(TankDrivetrain drivetrain, PIDSource source, double setpoint,
			PIDSettings settings) {
		this(drivetrain, source, () -> setpoint, settings);
	}

}