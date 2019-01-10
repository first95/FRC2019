package frc.robot.commands.compound;

import frc.robot.commands.collector.EjectCube;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreStartingCubeOnSwitch extends CommandGroup {

	// This command assumes the cube starts in the maw, with the wrist up
	public ScoreStartingCubeOnSwitch() {
		//addSequential(new ElevateCubeAndScore(ElevatorHoldPoint.SWITCH_SCORE, true));
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		
		// If the elevator is raised up during this move then use ReleaseCube
		// However, if the elevator is not used then EjectCube is better
		addSequential(new EjectCube());
		addSequential(new ResetElevatorAndWrist(false));
		addSequential(new DriveStraight(-20.0));
	}
}
