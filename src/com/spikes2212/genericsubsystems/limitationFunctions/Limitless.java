package com.spikes2212.genericsubsystems.limitationFunctions;

import java.util.function.Function;

import com.spikes2212.genericsubsystems.BasicSubsystem;

/**
 * This is a {@link Function} that always returns true. This is
 * for the constructor of a {@link BasicSubsystem} with no limits.
 * 
 * @author Omri "Riki" Cohen
 *
 * @see Function
 */
public class Limitless implements Function<Double, Boolean> {

	/**
	 * Constructs a limitless Function.
	 * 
	 */
	public Limitless() {
	}

	/**
	 * This method applies this function to a given double. Always returns true.
	 * 
	 * @param speed
	 *            the double this function get.
	 * @return always true.
	 */
	@Override
	public Boolean apply(Double speed) {
		return true;
	}

}
