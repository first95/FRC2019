package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.EjectCube;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevateCubeAndScore extends CommandGroup {

	public ElevateCubeAndScore(ElevatorHoldPoint position, boolean isSwitch) {
		addSequential(new SetElevatorHeight(position));
		addSequential(new SetWristAngle(WristAngle.MID_DOWN));
		addSequential(new Pause(1.0));
		if(isSwitch) {
		addSequential(new ReleaseCube());
		}else {
			addSequential(new EjectCube());
		}
	}

}
