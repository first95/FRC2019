package frc.robot.commands.compound;

import frc.robot.commands.Nothing;
import frc.robot.commands.Pause;
import frc.robot.commands.collector.AutoCloseMawOnCube;
import frc.robot.commands.collector.OpenMaw;
import frc.robot.commands.collector.RunChains;
import frc.robot.commands.collector.SetWristAngle;
import frc.robot.commands.collector.TimedIngestCube;
import frc.robot.commands.collector.SetWristAngle.WristAngle;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 *
 */
public class AutoPickUpCubeManualDrive extends CommandGroup {
	public AutoPickUpCubeManualDrive() {
		addSequential(new RunChains(AutoCloseMawOnCube.INTAKE_THROTTLE)); // Start the chains running before we start moving the pneumatics
		addSequential(new SetWristAngle(WristAngle.DOWN));
		addSequential(new OpenMaw());
		addSequential(new Pause(0.5));
		addSequential(new AutoCloseMawOnCube()); // This one waits until the cube is detected
		addSequential(new TimedIngestCube()); // This will stop the chains once it's done
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		addSequential(new Nothing()); // Without this, holding the button will restart the move after it completes
	}
	
	@Override
	protected void end() {
		addSequential(new SetWristAngle(WristAngle.MID_UP));
		super.end();
	}
	
}
