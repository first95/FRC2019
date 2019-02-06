package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.drivebase.DriveToVT;
import frc.robot.commands.drivebase.Pivot;
import frc.robot.oi.XBox360Controller;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * One can use SmartDashboard with a controller plugged in to see which values corrospond
 * to which controls.
 */
public class OI {

	private static final double ELEVATOR_UPDOWN_DEADBAND = 0.18;
	// Axes on drive controller
	public static final int DRIVE_FORWARD_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();
	public static final int DRIVE_TURN_AXIS = XBox360Controller.Axis.RIGHT_STICK_X.Number();
	
	// Axes on weapons controller
	public static final int HGL_INTAKE_AXIS = XBox360Controller.Axis.LEFT_TRIGGER.Number();
	public static final int HGL_OUTSPIT_AXIS = XBox360Controller.Axis.RIGHT_TRIGGER.Number();
	public static final int HGL_WRIST_AXIS = XBox360Controller.Axis.LEFT_STICK_Y.Number();
	public static final int ELEVATOR_AXIS = XBox360Controller.Axis.RIGHT_STICK_Y.Number();

	// Buttons on drive controller
	public static final int BUTTON_FORCE_LOW_GEAR = XBox360Controller.Button.LEFT_BUMPER.Number();
	public static final int BUTTON_FORCE_HIGH_GEAR = XBox360Controller.Button.RIGHT_BUMPER.Number();
	
	// Buttons on weapons controller
	public static final int ELEV_SEEK_FLOOR_BUTTON = XBox360Controller.Button.A.Number();
	public static final int ELEV_SEEK_SWITCH_SCORE_BUTTON = 0;
	public static final int ELEV_SEEK_SCALE_SCORE_LOW_BUTTON = XBox360Controller.Button.A.Number(); 
	public static final int ELEV_SEEK_SCALE_SCORE_MED_BUTTON = XBox360Controller.Button.X.Number();
	public static final int ELEV_SEEK_SCALE_SCORE_HIGH_BUTTON = XBox360Controller.Button.Y.Number();
	public static final int HS_OPEN_HOLD = XBox360Controller.Button.LEFT_BUMPER.Number();
	public static final int HS_PUSH_HOLD = XBox360Controller.Button.RIGHT_BUMPER.Number();

	// Controllers
	private Joystick driverController = new Joystick(0);
	private Joystick weaponsController = new Joystick(1);

	public OI() {

		// Create some buttons
		// JoystickButton joy_A = new JoystickButton(driverController, 1);

		// Connect the buttons to commands

		// Sendable Chooser for single commands
		// These are only for testing Purposes
		// Rotations
		SmartDashboard.putData("Drive to vision target", new DriveToVT());
		SmartDashboard.putData("Pivot 90 degrees CW", new Pivot(90));
		// SmartDashboard.putData("Pivot 90 degrees CCW", new Pivot(-90));
		// SmartDashboard.putData("Pivot 180 degrees CW", new Pivot(180));
		// SmartDashboard.putData("Pivot 180 degrees CCW", new Pivot(-180));
		// SmartDashboard.putData("Pivot 360 degrees CW", new Pivot(360));
		// SmartDashboard.putData("Pivot 360 degrees CCW", new Pivot(-360));

		// Forward and Backwards
		// SmartDashboard.putData("One Foot Forward", new DriveStraight(12));
		// SmartDashboard.putData("Two Feet Forward", new DriveStraight(24));
		// SmartDashboard.putData("Three Feet Forward", new DriveStraight(36));
		// SmartDashboard.putData("Six Feet Forward", new DriveStraight(12*6));
		// SmartDashboard.putData("One Foot Backward", new DriveStraight(-12));
		// SmartDashboard.putData("Two Feet Backward", new DriveStraight(-24));
		// SmartDashboard.putData("Three Feet Backward", new DriveStraight(-36));
		// SmartDashboard.putData("Six Feet Backward", new DriveStraight(-12*6));
		
		// Gear Shifting
		// SmartDashboard.putData("Lock High Gear", new LockGear(true));
		// SmartDashboard.putData("Lock Low Gear", new LockGear(false));
		// SmartDashboard.putData("Unlock Gear", new UnlockGear());
		// SmartDashboard.putData("LOCK DRIVE UNLOCK", new DriveStraightLockedGears(12*4, true));
	}

	// There are a few things the OI wants to revisit every time around
	public void visit() {

	}

	// If anything needs to be posted to the SmartDashboard, place it here
	public void log() {
		
	}


	// Hatch scorer
	public boolean isGrabHatchButtonPressed() {
		return weaponsController.getRawButton(HS_OPEN_HOLD);
	}

	public boolean isPushHatchButtonPressed() {
		return weaponsController.getRawButton(HS_PUSH_HOLD);
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

	// Elevator controls
	public double getElevatorSpeed() {

		double elevatorSpeed = 0;

		if ((weaponsController.getRawAxis(ELEVATOR_AXIS) > ELEVATOR_UPDOWN_DEADBAND)
				|| (weaponsController.getRawAxis(ELEVATOR_AXIS) < -ELEVATOR_UPDOWN_DEADBAND)) {
			elevatorSpeed = weaponsController.getRawAxis(ELEVATOR_AXIS);
		}

		// The Y axis on thet controller is reversed, so that positive is down
		return -elevatorSpeed;
	}

	public boolean isElevatorFloorButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_FLOOR_BUTTON);
	}

	public boolean isElevatorSwitchScoreButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_SWITCH_SCORE_BUTTON);
	}

	public boolean isElevatorScaleScoreLowButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_SCALE_SCORE_LOW_BUTTON);
	}

	public boolean isElevatorScaleScoreMedButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_SCALE_SCORE_MED_BUTTON);
	}

	public boolean isElevatorScaleScoreHighButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_SCALE_SCORE_HIGH_BUTTON);
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
}