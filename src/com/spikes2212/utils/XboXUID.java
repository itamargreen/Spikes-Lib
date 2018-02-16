package com.spikes2212.utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

public class XboXUID extends GenericHID {

	private XboxController xboxController;
	/**
	 * Constructs a new {@link XboXUID} using the port of the USB on the driver
	 * station.
	 * 
	 * @param port
	 *            The port on the Driver Station that the joystick is plugged
	 *            into.
	 */
	public XboXUID(int port) {
		super(port);
	}

	/**
	 * Returns the green button on the joystick.
	 * 
	 * @return the green button on the joystick.
	 */
	public Button getGreenButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getAButton();
			}
		};
	}

	/**
	 * Returns the blue button on the joystick.
	 * 
	 * @return the blue button on the joystick.
	 */
	public Button getBlueButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getXButton();
			}
		};
	}

	/**
	 * Returns the red button on the joystick.
	 * 
	 * @return the red button on the joystick.
	 */
	public Button getRedButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getBButton();
			}
		};
	}

	/**
	 * Returns the yellow button on the joystick.
	 * 
	 * @return the yellow button on the joystick.
	 */
	public Button getYellowButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getYButton();
			}
		};
	}

	/**
	 * Returns the value of the right trigger on the joystick.
	 * 
	 * @return the value of the right trigger on the joystick.
	 */
	public double getRTAxis() {
		return xboxController.getTriggerAxis(Hand.kRight);
	}

	/**
	 * Returns the value of the left trigger on the joystick.
	 * 
	 * @return the value of the left trigger on the joystick.
	 */
	public double getLTAxis() {
		return xboxController.getTriggerAxis(Hand.kLeft);
	}

	/**
	 * Returns the right bumper button on the joystick.
	 * 
	 * @return the right bumper button on the joystick.
	 */
	public Button getRBButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getBumper(Hand.kRight);
			}
		};
	}

	/**
	 * Returns the left bumper button on the joystick.
	 * 
	 * @return the left bumper button on the joystick.
	 */
	public Button getLBButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getBumper(Hand.kLeft);
			}
		};
	}

	/**
	 * Returns the button on the right stick.
	 * 
	 * @return the button on the right stick.
	 */
	public Button getRightStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getStickButton(Hand.kRight);
			}
		};
	}

	/**
	 * Returns the button on the left stick.
	 * 
	 * @return the button on the left stick.
	 */
	public Button getLeftStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getStickButton(Hand.kLeft);
			}
		};
	}
	
	/**
	 * Returns the button on the left stick.
	 * 
	 * @return the button on the left stick.
	 */
	public Button getStartButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getStartButton();
			}
		};
	}

	/**
	 * Returns the button on the left stick.
	 * 
	 * @return the button on the left stick.
	 */
	public Button getBackButton() {
		return new Button() {

			@Override
			public boolean get() {
				return xboxController.getBackButton();
			}
		};
	}


	/**
	 * Get the X axis value of the right stick.
	 * 
	 * @return the X axis value of the right stick.
	 */
	public double getRightX() {
		return getX(Hand.kRight);
	}

	/**
	 * Get the Y axis value of the right stick.
	 * 
	 * @return the Y axis value of the right stick.
	 */
	public double getRightY() {
		return getY(Hand.kRight);
	}

	/**
	 * Get the X axis value of the left stick.
	 * 
	 * @return the X axis value of the left stick.
	 */
	public double getLeftX() {
		return getX(Hand.kLeft);
	}

	/**
	 * Get the Y axis value of the left stick.
	 * 
	 * @return the Y axis value of the left stick.
	 */
	public double getLeftY() {
		return getY(Hand.kLeft);
	}

	/**
	 * Returns the up arrow button.
	 * 
	 * @return the up arrow button.
	 */
	public Button getUpButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 0;
			}
		};
	}

	/**
	 * Returns the down arrow button.
	 * 
	 * @return the down arrow button.
	 */
	public Button getDownButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 180;
			}
		};
	}

	/**
	 * Returns the left arrow button.
	 * 
	 * @return the left arrow button.
	 */
	public Button getLeftButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 270;
			}
		};
	}

	/**
	 * Returns the right arrow button.
	 * 
	 * @return the right arrow button.
	 */
	public Button getRightButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 90;
			}
		};
	}

	@Override
	public double getX(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}
}