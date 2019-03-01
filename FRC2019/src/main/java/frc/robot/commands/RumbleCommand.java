/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * Rumble for a given amount of time
 */
public class RumbleCommand extends TimedCommand {
    private OI.Controller controller;
    private OI.RumbleType pitch;
    private double severity;
    private double duration;

    /**
     * Rumble one of the controllers
     * @param controller which controller to rumble
     * @param pitch pitch at which to rumble
     * @param severity severity at which to rumble - 0.0 to 1.0
     * @param duration how long to rumble, in seconds
     * @param finishImmediately true to start the controller rumbling and immediately move on to the next command.  false to wait until the rumble duration is over before moving onto the next command.
     */
    public RumbleCommand(OI.Controller controller, OI.RumbleType pitch, double severity, double duration, boolean finishImmediately) {
        super(finishImmediately? 0.0001 : duration);
        this.controller = controller;
        this.pitch = pitch;
        this.severity = severity;
        this.duration = duration;
    }

    // Called just before this Command runs the first time
    @Override
    public void start() {
        super.start();
        System.out.println("RumbleCommand.start()");
        Robot.oi.Rumble(controller, pitch, severity, duration); 
    }

    @Override
    protected void end() {
        System.out.println("RumbleCommand.end()");
        super.end();
    }
}
