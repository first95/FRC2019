package frc.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AnnouncingGroup extends CommandGroup {

	public AnnouncingGroup() {
        super();

        addSequential(new AnnouncingCommand());
	}
}