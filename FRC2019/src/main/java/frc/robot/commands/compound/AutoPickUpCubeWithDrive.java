package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.AutoCloseMawOnCube;
import frc.robot.commands.collector.OpenMaw;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.TimedIngestCube;
import frc.robot.commands.drivebase.DriveAtThrottle;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.collector.SetWristAngle.WristAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPickUpCubeWithDrive extends CommandGroup {
	private static final double FORWARD_THROTTLE = 0.15;

	public AutoPickUpCubeWithDrive() {
		addSequential(new SetWristAngle(WristAngle.DOWN));
		addParallel(new DriveAtThrottle(FORWARD_THROTTLE));
		addSequential(new OpenMaw());
		//addSequential(new Pause(0.25));
		addSequential(new AutoCloseMawOnCube()); // This one waits until the cube is detected
		addParallel(new DriveAtThrottle(0)); // This only exists to cancel the last DriveAtThrottle.  It will remain active until another move takes over the drivebase.
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		addSequential(new TimedIngestCube());
	}
}
