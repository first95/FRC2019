package frc.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AnnouncingGroup extends CommandGroup {

	public AnnouncingGroup() {
        addSequential(new AnnouncingCommand());
	}
}