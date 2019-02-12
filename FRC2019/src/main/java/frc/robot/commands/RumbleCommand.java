/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * Rumble for a given amount of time
 */
public class RumbleCommand extends Command {
    private OI.Controller controller;
    private OI.RumbleType pitch;
    private double severity;
    private double duration;
    private boolean finishImmediately;

    private double endTime;

    /**
     * Rumble one of the controllers
     * @param controller which controller to rumble
     * @param pitch pitch at which to rumble
     * @param severity severity at which to rumble - 0.0 to 1.0
     * @param duration how long to rumble, in seconds
     * @param finishImmediately true to start the controller rumbling and immediately move on to the next command.  false to wait until the rumble duration is over before moving onto the next command.
     */
    public RumbleCommand(OI.Controller controller, OI.RumbleType pitch, double severity, double duration, boolean finishImmediately) {
        this.controller = controller;
        this.pitch = pitch;
        this.severity = severity;
        this.duration = duration;
        this.finishImmediately = finishImmediately;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.oi.Rumble(controller, pitch, severity, duration); 
        endTime = Timer.getFPGATimestamp() + duration;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return finishImmediately || (Timer.getFPGATimestamp() > endTime);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
