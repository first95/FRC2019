package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.EjectCube;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreStartingCubeOnScale extends CommandGroup
{

	// This command assumes the cube starts in the maw, with the wrist up.
	// It affects when we move the elevator - we wouldn't want to lift the elevator
	// up to the scale with the wrist in a flat position.
	public ScoreStartingCubeOnScale()
	{
		//addSequential(new DriveStraightLockedGears(-18, false));
		addSequential(new ElevateCubeAndScore(ElevatorHoldPoint.SCALE_SCORE_HIGH, false));
		addSequential(new DriveStraight(-4.0));
		addSequential(new ResetElevatorAndWrist(true));
	}
}
