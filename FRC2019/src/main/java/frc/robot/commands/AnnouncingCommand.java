/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * A command that does nothing except announce when its methods are called
 */
public class AnnouncingCommand extends Command {
    public AnnouncingCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.vision);
        System.out.println("AnnouncingCommand.AnnouncingCommand()");
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("AnnouncingCommand.start()");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("AnnouncingCommand.initialize()");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        System.out.println("AnnouncingCommand.execute()");
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        boolean finished = false;
        System.out.println("AnnouncingCommand.isFinished(): " + finished);
        return finished;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("AnnouncingCommand.end()");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("AnnouncingCommand.interrupted()");
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
        System.out.println("AnnouncingCommand.cancel()");
    }
}
