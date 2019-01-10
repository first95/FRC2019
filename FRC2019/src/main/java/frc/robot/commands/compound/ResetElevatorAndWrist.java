package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ResetElevatorAndWrist extends CommandGroup {

	public ResetElevatorAndWrist(boolean isScale) {
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		addSequential(new Pause(1.0));
		if (isScale) {
			addSequential(new SetElevatorHeight(ElevatorHoldPoint.FLOOR));
		}
		addSequential(new SetWristAngle(WristAngle.UP));
		//addSequential(new Pause(0.1));
	}

}
