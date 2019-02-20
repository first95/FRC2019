package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.drivebase.DriveToVT;
import frc.robot.commands.drivebase.Pivot;
import frc.robot.commands.vision.ToggleCameraMode;
import frc.robot.commands.hgroundloader.AutoAcquire;
import frc.robot.commands.hgroundloader.SetIntakeThrottle;
import frc.robot.commands.hgroundloader.SetWristAngle;
import frc.robot.commands.hgroundloader.WaitForHatchDetected;
import frc.robot.oi.XBox360Controller;
import frc.robot.subsystems.Elevator;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * One can use SmartDashboard with a controller plugged in to see which values corrospond
 * to which controls.
 */
public class OI {

	// Controllers
	private Joystick driverController = new Joystick(0);
	private Joystick weaponsController = new Joystick(1);

	// Buttons on drive controller
	public static final int CLIMB_SKIDS_BUTTON = XBox360Controller.Button.LEFT_BUMPER.Number();
	public static final int SWITCH_CAM_VIEW_BUTTON = XBox360Controller.Button.START.Number();
	// Features not presently in use - getRawButton(0) always returns false
	public static final int BUTTON_FORCE_LOW_GEAR = 0;// XBox360Controller.Button.LEFT_BUMPER.Number();
	public static final int BUTTON_FORCE_HIGH_GEAR = 0;//XBox360Controller.Button.RIGHT_BUMPER.Number();

	// Axes on drive controller
	public static final int DRIVE_FORWARD_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();
	public static final int DRIVE_TURN_AXIS = XBox360Controller.Axis.RIGHT_STICK_X.Number();
	public static final int CLIMBER_UP_AXIS = XBox360Controller.Axis.LEFT_TRIGGER.Number();
    public static final int CLIMBER_DOWN_AXIS = XBox360Controller.Axis.RIGHT_TRIGGER.Number();

	// Axes on weapons controller
	public static final int HGL_INTAKE_AXIS = XBox360Controller.Axis.LEFT_TRIGGER.Number();
	public static final int HGL_OUTSPIT_AXIS = XBox360Controller.Axis.RIGHT_TRIGGER.Number();
	public static final int HGL_WRIST_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();
	public static final int CARGO_HANDLER_INTAKE_AXIS = XBox360Controller.Axis.LEFT_TRIGGER.Number();
	public static final int CARGO_HANDLER_OUTSPIT_AXIS = XBox360Controller.Axis.RIGHT_TRIGGER.Number();
	public static final int CARGO_HANDLER_WRIST_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();

	// Buttons on weapons controller
	public static final int ELEV_PRESET_HATCH_HANDOFF = XBox360Controller.Button.A.Number();
	public static final int ELEV_PRESET_HATCH_LOW = XBox360Controller.Button.B.Number();
	public static final int ELEV_PRESET_HATCH_MID = XBox360Controller.Button.X.Number(); 
	public static final int ELEV_PRESET_HATCH_HIGH = XBox360Controller.Button.Y.Number();
	public static final int HS_OPEN_TOGGLE = XBox360Controller.Button.LEFT_BUMPER.Number();
	public static final int HS_PUSH_TOGGLE = XBox360Controller.Button.RIGHT_BUMPER.Number();
	public static final int ELEV_YOU_ARE_HOME = XBox360Controller.Button.BACK.Number();
	public static final int HGL_RETRACT_WRIST = XBox360Controller.PovDir.UP.Degrees();
	public static final int HGL_AUTO_COLLECT = XBox360Controller.Button.START.Number(); // XBox360Controller.PovDir.DOWN.Degrees();
	public static final int CH_WRIST_UP = XBox360Controller.PovDir.LEFT.Degrees();
	public static final int CH_WRIST_COLLECT = XBox360Controller.PovDir.RIGHT.Degrees();
	
	// Quickly running out of buttons and axes on weapons controller...
	// Use one direction of POV (e.g. UP) for HGL auto-collect and the other direction of POV
	// (e.g. DOWN) for CL auto-collect; then ignore other primary directions (LEFT and RIGHT) and treat
	// the intermediate directions as (e.g. UP-RIGHT, DOWN-LEFT) as the primary direction we're using
	// i.e. UP-LEFT, UP, and UP-RIGHT would all map to one UP behavior
	
	// Axes on weapons controller
	public static final int CARGO_LOADER_WRIST_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();
	public static final int ELEVATOR_AXIS = XBox360Controller.Axis.RIGHT_STICK_Y.Number();
	public static final int LOADERS_OUTSPIT_AXIS = XBox360Controller.Axis.LEFT_TRIGGER.Number();
	public static final int CARGO_LOADER_INTAKE_AXIS = XBox360Controller.Axis.RIGHT_TRIGGER.Number();

	private static final double ELEVATOR_UPDOWN_DEADBAND = 0.18;

	// XBox controllers have both high-frequency and low-frequency vibrator motors.
	// The Joystick class calls these "left" and "right", and they do seem to be on those sides.
	// This is the mapping between the two.
	public enum RumbleType {
		LOW_PITCH(Joystick.RumbleType.kLeftRumble),
		HIGH_PITCH(Joystick.RumbleType.kRightRumble),
		;
		
        private final Joystick.RumbleType rumbleType;

        private RumbleType(Joystick.RumbleType value) {
            this.rumbleType = value;
        }

        public Joystick.RumbleType JoystickType() {
            return rumbleType;
        }
	}

	/** Describes which of the controlleres you're referring to */
	public enum Controller {
		DRIVER,
		WEAPONS, // Weapons operator
	}

	// System timestamps after which we want each rumbler to be turned off
	private double driverLowRumbleStopTime = 0;
	private double driverHighRumbleStopTime = 0;
	private double weaponsLowRumbleStopTime = 0;
	private double weaponsHighRumbleStopTime = 0;

	public OI() {

		// // Create some buttons
		// JoystickButton joy_dA = new JoystickButton(driverController, XBox360Controller.Button.A.Number());
		// JoystickButton joy_dB = new JoystickButton(driverController, XBox360Controller.Button.B.Number());
		// JoystickButton joy_wA = new JoystickButton(weaponsController, XBox360Controller.Button.A.Number());
		// JoystickButton joy_wB = new JoystickButton(weaponsController, XBox360Controller.Button.B.Number());
		// // Connect the buttons to commands
		// joy_dA.whenPressed(new SetWristAngle(0));
		// joy_dB.whenPressed(new SetWristAngle(-90));
		// joy_wA.whenPressed(new RumbleCommand(Controller.DRIVER, RumbleType.HIGH_PITCH, 0.5, 1.0, true));
		// joy_wB.whenPressed(new RumbleCommand(Controller.DRIVER, RumbleType.LOW_PITCH, 0.5, 1.0, true));
		JoystickButton cameraViewSwitcher = new JoystickButton(driverController, SWITCH_CAM_VIEW_BUTTON);
        cameraViewSwitcher.whenPressed(new ToggleCameraMode());
		cameraViewSwitcher.close(); // Don't need this one anymore?
		
		JoystickButton hglAutoCollect = new JoystickButton(weaponsController, HGL_AUTO_COLLECT);
		hglAutoCollect.whenPressed(new AutoAcquire());
		// hglAutoCollect.whileHeld(new SetIntakeThrottle(1.0));
		// hglAutoCollect.whenPressed(new SetWristAngle(90, true));
		//hglAutoCollect.whenPressed(new WaitForHatchDetected());
        hglAutoCollect.close(); // Don't need this one anymore?		

		// Sendable Chooser for single commands
		// These are only for testing Purposes
		// Rotations
		SmartDashboard.putData("Drive to vision target", new DriveToVT());
		SmartDashboard.putData("Pivot 90 degrees CW", new Pivot(90));
		
	}

	// There are a few things the OI wants to revisit every time around
	public void visit() {

		// Cancel joystick rumble if necessary
		if(Timer.getFPGATimestamp() > driverLowRumbleStopTime) {
			driverController.setRumble(RumbleType.LOW_PITCH.JoystickType(), 0);
		}
		if(Timer.getFPGATimestamp() > driverHighRumbleStopTime) {
			driverController.setRumble(RumbleType.HIGH_PITCH.JoystickType(), 0);
		}
		if(Timer.getFPGATimestamp() > weaponsLowRumbleStopTime) {
			weaponsController.setRumble(RumbleType.LOW_PITCH.JoystickType(), 0);
		}
		if(Timer.getFPGATimestamp() > weaponsHighRumbleStopTime) {
			weaponsController.setRumble(RumbleType.HIGH_PITCH.JoystickType(), 0);
		}
	}

	// If anything needs to be posted to the SmartDashboard, place it here
	public void log() {
		
	}


	// Hatch scorer
	/**
	 * Check if the Open Hatch Grabber button was pressed since last check
	 * @return true if the Open Hatch Grabber button was pressed since last check
	 */
	public boolean isGrabHatchButtonPressed() {
		return weaponsController.getRawButtonPressed(HS_OPEN_TOGGLE);
	}
	/**
	 * Check if the Push Hatch Grabber button was pressed since last check
	 * @return true if the Push Hatch Grabber button was pressed since last check
	 */
	public boolean isPushHatchButtonPressed() {
		return weaponsController.getRawButtonPressed(HS_PUSH_TOGGLE);
	}	

	/**
	 * Get speed at which the intake rollers of the hatch ground loader should run
	 * @return -1.0 for fully outward, 1.0 for fully inward, 0.0 for stationary
	 */
	public double getHGLIntakeSpeed() {
		return weaponsController.getRawAxis(HGL_INTAKE_AXIS) - weaponsController.getRawAxis(HGL_OUTSPIT_AXIS);
	}

	/**
	 * Get speed at which the wrist of of the hatch ground loader should turn
	 * @return -1.0 for fully downward, 1.0 for fully upward, 0.0 for stationary
	 */
	public double getHGLWristSpeed() {
		return weaponsController.getRawAxis(HGL_WRIST_AXIS);
	}

	/**
	 * Get whether HGL wrist UP button is pressed
	 * @return true to bring HGL wrist up, false otherwise
	 */	
	public boolean isHGLWristUpButtonPressed() {
		return weaponsController.getPOV() == HGL_RETRACT_WRIST;
	}	

	/**
	 * Get speed at which the motor of the climber should move
	 * @return -1.0 for fully downward, 1.0 for fully upward, 0.0 for stationary
	 */
	public double getClimberSpeed() {
		return driverController.getRawAxis(CLIMBER_UP_AXIS) - driverController.getRawAxis(CLIMBER_DOWN_AXIS);
	}

	/**
	 * Get deploy state for skids
	 * @return true to deploy and false to retract
	 */	
	public boolean isDeploySkidsToggled() {
		return driverController.getRawButtonPressed(CLIMB_SKIDS_BUTTON);
	}

	/**
	 * Get speed at which the intake rollers of the cargo handler should run
	 * @return -1.0 for fully outward, 1.0 for fully inward, 0.0 for stationary
	 */
	public double getCargoHandlerIntakeSpeed() {
		return weaponsController.getRawAxis(CARGO_HANDLER_INTAKE_AXIS) - weaponsController.getRawAxis(CARGO_HANDLER_OUTSPIT_AXIS);
	}

	/**
	 * Get speed at which the wrist of of the cargo handler  should turn
	 * @return -1.0 for fully downward, 1.0 for fully upward, 0.0 for stationary
	 */
	public double getCargoHandlerWristSpeed() {
		return weaponsController.getRawAxis(CARGO_HANDLER_WRIST_AXIS);
	}

	/**
	 * Get whether cargo handler wrist UP button is pressed
	 * @return true to bring cargo handler wrist up, false otherwise
	 */	
	public boolean isCHWristUpButtonPressed() {
		return weaponsController.getPOV() == CH_WRIST_UP;
	}

	/**
	 * Get whether cargo handler wrist COLLECT button is pressed
	 * @return true to bring cargo handler wrist to collect position, false otherwise
	 */	
	public boolean isCHWristCollectButtonPressed() {
		return weaponsController.getPOV() == CH_WRIST_COLLECT;
	}	

	// Elevator controls
	public double getElevatorSpeed() {

		double elevatorCtrl = weaponsController.getRawAxis(ELEVATOR_AXIS);
		double elevatorSpeed = 0;

		if ((elevatorCtrl > ELEVATOR_UPDOWN_DEADBAND)
				|| (elevatorCtrl < -ELEVATOR_UPDOWN_DEADBAND)) {
			elevatorSpeed = elevatorCtrl;
		}

		// The Y axis on thet controller is reversed, so that positive is down
		SmartDashboard.putNumber("Elevator axis value",elevatorCtrl);
		SmartDashboard.putNumber("Elevator speed throttle",-elevatorSpeed);
		return -elevatorSpeed * 1.0;
	}

	public Elevator.ElevatorHoldPoint getCommandedHoldPoint() {
		// Prioritize lower setpoints if the user holds more than one button
		if(weaponsController.getRawButton(ELEV_PRESET_HATCH_HANDOFF)) {
			return Elevator.ElevatorHoldPoint.HATCH_HANDOFF;
		} else if(weaponsController.getRawButton(ELEV_PRESET_HATCH_LOW)) {
			return Elevator.ElevatorHoldPoint.HATCH_COVER_LOW;
		} else if(weaponsController.getRawButton(ELEV_PRESET_HATCH_MID)) {
			return Elevator.ElevatorHoldPoint.HATCH_COVER_MID;
		} else if(weaponsController.getRawButton(ELEV_PRESET_HATCH_HIGH)) {
			return Elevator.ElevatorHoldPoint.HATCH_COVER_HIGH;
		} else {
			return Elevator.ElevatorHoldPoint.NONE;
		}
	}

	public boolean getElevatorHomeButtonPressed() {
		return weaponsController.getRawButton(ELEV_YOU_ARE_HOME);
	}

	// Drive base controls
	public double getForwardAxis() {
		return driverController.getRawAxis(DRIVE_FORWARD_AXIS);
	}

	public double getTurnAxis() {
		return driverController.getRawAxis(DRIVE_TURN_AXIS);
	}

	/**
	 * Ask if the driver wants the robot to be in high gear
	 * @return
	 */
	public boolean getHighGear() {
		return driverController.getRawButton(BUTTON_FORCE_HIGH_GEAR);
	}

	/**
	 * Ask if the driver wants the robot to be in low gear
	 * @return
	 */
	public boolean getLowGear() {
		return driverController.getRawButton(BUTTON_FORCE_LOW_GEAR);
	}

	/**
	 * Rumble a controller.
	 * Note that you may have overlapping low- and high-pitched rumbles
	 * @param controller which controller to rumble
	 * @param pitch low-pitched or high-pitched rumbler
	 * @param severity how strongly to rumble, between 0.0 and 1.0
	 * @param duration how long, in seconds, the rumble should last
	 */
	public void Rumble(Controller controller, RumbleType pitch, double severity, double duration) {
		Joystick stick = null;
		switch(controller) {
			case DRIVER: 
				stick = driverController; 
				switch(pitch) {
					case HIGH_PITCH:
						driverHighRumbleStopTime = Timer.getFPGATimestamp() + duration;
						break;
					case LOW_PITCH:
						driverLowRumbleStopTime = Timer.getFPGATimestamp() + duration;
						break;
				}
				break;
			case WEAPONS: 
				stick = weaponsController;
				switch(pitch) {
					case HIGH_PITCH:
						weaponsHighRumbleStopTime = Timer.getFPGATimestamp() + duration;
						break;
					case LOW_PITCH:
						weaponsLowRumbleStopTime = Timer.getFPGATimestamp() + duration;
						break;
				}
				break;
		}

		stick.setRumble(pitch.JoystickType(), severity);
	}
	/**
	 * Cease all rumbling
	 */
	public void CancelRumble() {
		CancelRumble(Controller.DRIVER);
		CancelRumble(Controller.WEAPONS);
	}
	/**
	 * Cease all rumbling on a controller
	 */
	public void CancelRumble(Controller controller) {
		Joystick stick = null;
		switch(controller) {
			case DRIVER: 
				stick = driverController; 
				driverHighRumbleStopTime = Timer.getFPGATimestamp() - 1;
				driverLowRumbleStopTime = Timer.getFPGATimestamp() - 1;
				break;
			case WEAPONS: 
				stick = weaponsController;
				weaponsHighRumbleStopTime = Timer.getFPGATimestamp() - 1;
				weaponsLowRumbleStopTime = Timer.getFPGATimestamp() - 1;
				break;
		}

		stick.setRumble(RumbleType.LOW_PITCH.JoystickType(), 0);
		stick.setRumble(RumbleType.HIGH_PITCH.JoystickType(), 0);
    }
}
