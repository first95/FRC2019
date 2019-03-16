/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Leaves the driver in manual control of forward speed.
 * When the line is detected under a line sensor, steers the robot so it is driving parallel to the line.
 * If no line is detected, steering remains under manual control.
 */
public class AutosteerAlongLine extends Command {
    // How fast the robot should turn when the sensor at the extreme right or left sees a line
    public static final double MAX_TURN_RATE = 0.5;
    
	public AutosteerAlongLine() {
		requires(Robot.drivebase);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        Robot.drivebase.setMaxSpeed(1);
        // Exponential response
        double fwd = Math.pow(Robot.oi.getForwardAxis(), 3);
        double turn;
        if(Robot.drivebase.doesAnySensorSeeTheLine()) {
            System.out.println("Some sensor sees a line!");
            turn = getLineFollowTurnRate();
        } else {
            // Exponential response
            System.out.println("No sensor sees a line.");
            turn = Math.pow(Robot.oi.getTurnAxis(), 3);
        }

        // Put it into action
		Robot.drivebase.arcade(fwd, turn);
    }
    
    /**
     * Check the sensors and compute the appropriate turn rate
     * @return
     */
	private double getLineFollowTurnRate() {
        // Index of the sensor in the center.  This is also the count of sensors to the right or left of center.
        int centerSensorIndex = (int)Math.floor(Robot.drivebase.getLineSensorCount() / 2.0);
        if(Robot.drivebase.doesSensorSeeLine(centerSensorIndex)) {
            // We see it in the middle one, go straight
            System.out.println("On center.");
            return 0;
        } else {
            // Loop through sensor pairs, starting with the innermost two.
            // There can be any number of sensors active at any given time, so we prioritize the center by checking it first.
            // This results in smaller turns in the case of an error.
            for(int outwardOffsetFromCenter = 1; outwardOffsetFromCenter <= centerSensorIndex; outwardOffsetFromCenter++) {
                double turnRate = ((double)outwardOffsetFromCenter / centerSensorIndex) * MAX_TURN_RATE;
                // Left of center?
                if(Robot.drivebase.doesSensorSeeLine(centerSensorIndex - outwardOffsetFromCenter)) {
                    System.out.println("Left of center, outward offset is "+outwardOffsetFromCenter);
                    return -turnRate;
                }
                // Right of center?
                if(Robot.drivebase.doesSensorSeeLine(centerSensorIndex + outwardOffsetFromCenter)) {
                    System.out.println("Right of center, outward offset is "+outwardOffsetFromCenter);
                    return turnRate;
                }
            }
        }

        // Don't see it anywhere
        return 0;
    }

    // Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
        return false; // This command runs until interrupted
	}

}
