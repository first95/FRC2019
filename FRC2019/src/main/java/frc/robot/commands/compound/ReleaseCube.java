package frc.robot.commands.compound;

import frc.robot.commands.Pause;
import frc.robot.commands.collector.CloseMaw;
import frc.robot.commands.collector.OpenMaw;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReleaseCube extends CommandGroup{

	public ReleaseCube() {
		addSequential(new OpenMaw());
		addSequential(new Pause(0.5));
		addSequential(new CloseMaw());
	}

}
