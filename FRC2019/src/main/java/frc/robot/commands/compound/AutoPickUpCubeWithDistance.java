package frc.robot.commands.compound;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Pause;
import frc.robot.commands.collector.CloseMaw;
import frc.robot.commands.collector.OpenMaw;
import frc.robot.commands.collector.RunChains;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import frc.robot.commands.collector.TimedIngestCube;

public class AutoPickUpCubeWithDistance extends CommandGroup {

	public AutoPickUpCubeWithDistance(double distanceToDrive) {
		addSequential(new SetWristAngle(WristAngle.DOWN));
		addParallel(new OpenMaw());
		addSequential(new Pause(0.25));
		addSequential(new DriveStraightAtSpeedLockedGears(distanceToDrive, false, 0.5));
		addParallel(new RunChains(-1.0));
		addSequential(new Pause(1.0));
		addSequential(new CloseMaw());
		addParallel(new RunChains(-1.0));
		addSequential(new Pause(1.0));
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		addSequential(new TimedIngestCube());
	}
}
