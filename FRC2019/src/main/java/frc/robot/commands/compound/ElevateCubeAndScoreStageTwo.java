package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.EjectCube;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevateCubeAndScoreStageTwo extends CommandGroup {

	public ElevateCubeAndScoreStageTwo(ElevatorHoldPoint position, boolean isSwitch, double timeToWait) {
		addSequential(new SetElevatorHeight(position));
		addSequential(new SetWristAngle(WristAngle.MID_DOWN));
		addSequential(new Pause(timeToWait));
		if(isSwitch) {
		addSequential(new ReleaseCube());
		}else {
			addSequential(new EjectCube());
		}
	}

}
