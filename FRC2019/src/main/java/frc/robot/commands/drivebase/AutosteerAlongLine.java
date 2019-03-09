/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Leaves the driver in manual control of forward speed.
 * When the line is detected under a line sensor, steers the robot so it is driving parallel to the line.
 * If no line is detected, steering remains under manual control.
 */
public class AutosteerAlongLine extends Command {
	
	public AutosteerAlongLine() {
		requires(Robot.drivebase);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        Robot.drivebase.setMaxSpeed(1);
		double fwd = Robot.oi.getForwardAxis();
        double spin;
        if(Robot.drivebase.doesAnySensorSeeTheLine()) {
            spin = getLineFollowSpinRate();
        } else {
            spin = Robot.oi.getTurnAxis();
        }

		/* "Exponential" drive, where the movements are more sensitive during
		 * slow movement, permitting easier fine control
		 */
		spin = Math.pow(spin, 3);
		fwd = Math.pow(fwd, 3);
		Robot.drivebase.arcade(fwd, spin);
    }
    
    /**
     * Check the sensors and compute the appropriate turn rate
     * @return
     */
	private double getLineFollowSpinRate() {
        return 0;
    }

    // Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
        return false; // This command runs until interrupted
	}

}
