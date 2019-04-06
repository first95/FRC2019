package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.AnnouncingCommand;

public class AnnouncingSubsystem extends Subsystem {

    public AnnouncingSubsystem() {
      super();
      System.out.println("AnnouncingSubsystem.AnnouncingSubsystem()");
    }
  
    @Override   
    public void initDefaultCommand() {
        System.out.println("AnnouncingSubsystem.initDefaultCommand()");
        setDefaultCommand(new AnnouncingCommand());
    }
}  