package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot.StartPosition;
import frc.robot.commands.Nothing;
import frc.robot.commands.drivebase.AnyForward;
import frc.robot.commands.drivebase.Pivot;
import frc.robot.oi.MutableSendableChooser;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * One can use SmartDashboard with a controller plugged in to see which values corrospond
 * to which controls.
 */
public class OI {

	// Shifter Lock (Used to know what gear to lock the shifter in)
	// Default is 0, THIS MEANS ALLOW AUTOSHIFT!
	private int shiftLockValue = 0;
	
	// Axes on weapons controller
	public static final int ELEVATOR_AXIS = 5; // Right stick Y

	// Buttons on drive controller
	public static final int SHIFT_BUTTON = 5; // Left bumper
	public static final int SHIFT_STATE_BUTTON = 6; // Right bumper
	
	// Buttons on weapons controller
	public static final int ELEV_SEEK_FLOOR_BUTTON = 1; // A
	public static final int ELEV_SEEK_SWITCH_SCORE_BUTTON = 0;// 2; // B
	public static final int ELEV_SEEK_SCALE_SCORE_LOW_BUTTON = 2; // B
	public static final int ELEV_SEEK_SCALE_SCORE_MED_BUTTON = 3; // X
	public static final int ELEV_SEEK_SCALE_SCORE_HIGH_BUTTON = 4; // Y

	// POV/DPAD on the weapons controller || IT IS IN DEGREES!
	public static final int POV_NONE = -1; // No DPAD button pressed
	public static final int POV_UP = 0;
	public static final int POV_UP_RIGHT = 45;
	public static final int POV_RIGHT = 90;
	public static final int POV_RIGHT_DOWN = 135;
	public static final int POV_DOWN = 180;
	public static final int POV_DOWN_LEFT = 225;
	public static final int POV_LEFT = 270;
	public static final int POV_LEFT_UP = 315;

	// Wrist positions
	private boolean stageOneRetracted = false;
	private boolean stageTwoRetracted = false;

	// Controllers
	private Joystick driverController = new Joystick(0);
	private Joystick weaponsController = new Joystick(1);

	// Setup choosers for automoves
	SendableChooser<StartPosition> robotStartingPosition = new SendableChooser<>();
	MutableSendableChooser<Command> selectionOne = new MutableSendableChooser<>();
	MutableSendableChooser<Command> selectionTwo = new MutableSendableChooser<>();
	MutableSendableChooser<Command> selectionThree = new MutableSendableChooser<>();

	// The position that was selected last iteration
	StartPosition lastSelectedPosition = null; 

	public OI() {

		// Create some buttons
		JoystickButton joy_A = new JoystickButton(driverController, 1);

		// Connect the buttons to commands

		// Sendable Chooser for single commands
		// These are only for testing Purposes
		// Rotations
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
		
		// For the operators to indicate on which side of the field they placed the
		// robot
		robotStartingPosition.addObject("Left", StartPosition.LEFT);
		robotStartingPosition.addDefault("Center", StartPosition.CENTER);
		robotStartingPosition.addObject("Right", StartPosition.RIGHT);
		SmartDashboard.putData("Starting position", robotStartingPosition);

		// Add the move choosers, which will be populated the first call to visit()
		SmartDashboard.putData("selOne", selectionOne);
		SmartDashboard.putData("selTwo", selectionTwo);
	}

	// There are a few things the OI wants to revisit every time around
	public void visit() {
		updateWristSettings();
		updateSmartChoosers();
	}

	// If anything needs to be posted to the SmartDashboard, place it here
	public void log() {
		
	}

	public int getShiftLockValue() {
		return shiftLockValue;	
	}
	
	public void setShiftLockValue(int shifterValue) {
		shiftLockValue = shifterValue;
	}

	// Wrist controls
	// We support 4 positions:
	// Stage 1 Stage 2 POV position
	// Full up extended extended Up
	// some up extended retracted Up/right, left/up
	// some down retracted extended Right, left
	// full down retracted retracted Down , right/down, left/down
	public void updateWristSettings() {
		if (weaponsController.getPOV() != POV_NONE) {
			// Per table above, retract stage one if the POV hat is right or down
			stageOneRetracted = (weaponsController.getPOV() >= POV_RIGHT
					&& weaponsController.getPOV() <= POV_DOWN_LEFT);
			// Retract if the POV hat is up or down-ish
			stageTwoRetracted = (weaponsController.getPOV() >= POV_RIGHT_DOWN
					&& weaponsController.getPOV() <= POV_LEFT);
		} else {
			// When no D-Pad button is pressed, don't change the angle
		}
	}

	public void setWristStageOneRectractedStatus(boolean retracted) {
		stageOneRetracted = retracted;
	}

	public void setWristStageTwoRetractedStatus(boolean retracted) {
		stageTwoRetracted = retracted;
	}

	public boolean getWristStageOneRetracted() {
		return stageOneRetracted;
	}

	public boolean getWristStageTwoRetracted() {
		return stageTwoRetracted;
	}

	// Elevator controls
	public double getElevatorSpeed() {

		double elevatorSpeed = 0;

		if ((weaponsController.getRawAxis(ELEVATOR_AXIS) > .18)
				|| (weaponsController.getRawAxis(ELEVATOR_AXIS) < -.18)) {
			elevatorSpeed = weaponsController.getRawAxis(ELEVATOR_AXIS);
		}

		// The Y axis is reversed, so that positive is down
		return -elevatorSpeed;
	}

	public boolean isElevatorFloorButtonPressed() {
		return weaponsController.getRawButton(ELEV_SEEK_FLOOR_BUTTON);
	}

	public boolean isElevatorSwitchScoreButtonPressed() {
		return false; // Not currently in use
		// return weaponsController.getRawButton(ELEV_SEEK_SWITCH_SCORE_BUTTON);
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
		return driverController.getY();
	}

	public double getTurnAxis() {
		return driverController.getRawAxis(4);
	}

	public boolean getHighGear() {
		return driverController.getRawButton(SHIFT_BUTTON);
	}

	// This feature has been put on hold
	// It's goal was to allow the driver
	// to stop the robot from shifting into high gear
	// public boolean getShiftOverrided() {
	// return driverController.getRawButton(SHIFT_STATE_BUTTON);
	// }

	
	// Other Operations
	public StartPosition getRobotStartPosition() {
		return robotStartingPosition.getSelected();
	}
	
	// We've got some SendableChooserse that need updating based on the selected
	// robot position
	public void updateSmartChoosers() {
		StartPosition curPos = robotStartingPosition.getSelected();

		if (curPos != lastSelectedPosition) {
			System.out.println("Updating auto move choices list");
			updateS1AutoMoveChooser(curPos);
			updateS2AutoMoveChooser(curPos);
			updateS3AutoMoveChooser(curPos);
		}
		lastSelectedPosition = curPos;
	}

	// Get the current selection of commands, possibly change to three selectors?
	public Command getSelectedCommand() {
		return new Nothing();
	}
	
	
	// AUTO MOVE CHOOSERS
	private void updateS1AutoMoveChooser(StartPosition robotStartPosition) {
		// Clear chooser before updating
		selectionOne.clear();

		// Default move || The closest thing we have to a label
		selectionOne.addDefault("Nothing", new Nothing());

		switch (robotStartPosition) {
		case LEFT:
			selectionOne.addObject("Forward to auto line", new AnyForward());
			break;
		case CENTER:
			selectionOne.addObject("Nothing", new Nothing());
			break;
		case RIGHT:
			selectionOne.addObject("Forward to auto line", new AnyForward());
			break;
		default:
			break;
		}
	}

	private void updateS2AutoMoveChooser(StartPosition robotStartPosition) {
		// Clear chooser before updating
		selectionTwo.clear();

		// Default move || The closest thing we have to a label
		selectionTwo.addDefault("Nothing", new Nothing());

		switch (robotStartPosition) {
		case LEFT:
			selectionTwo.addObject("Forward to auto line", new AnyForward());
			break;
		case CENTER:
			selectionTwo.addObject("Nothing", new Nothing());
			break;
		case RIGHT:
			selectionTwo.addObject("Forward to auto line", new AnyForward());
			break;
		default:
			break;
		}
	}

	private void updateS3AutoMoveChooser(StartPosition robotStartPosition) {
		// Clear chooser before updating
		selectionThree.clear();

		// Default move || The closest thing we have to a label
		selectionThree.addDefault("Nothing", new Nothing());

		switch (robotStartPosition) {
		case LEFT:
			selectionThree.addObject("Forward to auto line", new AnyForward());
			break;
		case CENTER:
			selectionThree.addObject("Nothing", new Nothing());
			break;
		case RIGHT:
			selectionThree.addObject("Forward to auto line", new AnyForward());
			break;
		default:
			break;
		}
	}
}