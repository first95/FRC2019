package frc.robot.commands.elevator;

import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorHoldPoint;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManuallyControlElevator extends Command {
	// Note that a different set of constants is used in autonomous control
	public static double FLOOR_HEIGHT_FEET = 0.0 - 0.01;
	public static double SWITCH_SCORE_HEIGHT_FEET = 2.0;
	public static double SCALE_SCORE_LOW_HEIGHT_FEET = 4.5 - 0.25;
	public static double SCALE_SCORE_MED_HEIGHT_FEET = 5 - 0.25;
	public static double SCALE_SCORE_HIGH_HEIGHT_FEET = 5.5; //-0.25 removed for testing

	private boolean wasHoldingPresentPositionLastIteration = false;

	public ManuallyControlElevator() {
		// This method is run once during robot startup
		requires(Robot.elevator);
	}

	@Override
	public synchronized void start() {
		super.start();
		// This method is called once when the command is activated
		Robot.elevator.seekHoldPoint(ElevatorHoldPoint.HERE);
		wasHoldingPresentPositionLastIteration = true;
	}

	@Override
	protected void execute() {
		// This method is called every iteration

		final String ELEV_MODE = "Elevator mode";
		
		// First priority: Is the user holding down one of the seek buttons?
		if(Robot.oi.getCommandedHoldPoint() != ElevatorHoldPoint.NONE) {
			Robot.elevator.seekHoldPoint(Robot.oi.getCommandedHoldPoint());
			wasHoldingPresentPositionLastIteration = false;
			SmartDashboard.putString(ELEV_MODE, "Hold position " + Robot.oi.getCommandedHoldPoint().toString());
		} else {
			// Second priority: Is the stick outside the deadband?
			if (Math.abs(Robot.oi.getElevatorSpeed()) > 0) {
				SmartDashboard.putString(ELEV_MODE, "Set speed");
				Robot.elevator.setElevatorSpeed(Robot.oi.getElevatorSpeed());
				wasHoldingPresentPositionLastIteration = false;
			} else {
				// Third priority: hold the present position
				if (!wasHoldingPresentPositionLastIteration) {
					Robot.elevator.seekHoldPoint(ElevatorHoldPoint.HERE);
					wasHoldingPresentPositionLastIteration = true;
				} else {
					// We already commanded the elevator to hold its present
					// position, so we don't need to command it to do so again.
				}
				SmartDashboard.putString(ELEV_MODE, "Hold present position");
			}
		}
	}

	@Override
	public synchronized void cancel() {
		// Cancel any position seeking
		Robot.elevator.stopMotor();
	}

	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}
