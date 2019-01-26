/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.robot.Robot;
import frc.robot.commands.Pause;

/**
 * Score a hatch assuming aligned properly vertically
 */
public class ScoreHatch extends Command {
	private double pauseDur = 1;
	
	public ScoreHatch() {
		requires(Robot.hScorer);
	}

	// Called every time the command starts
	@Override
	public void initialize() {
		System.out.println("Starting ScoreHatch");
		
		// Push scorer
		Robot.hScorer.pushHS(true);
		// Pause for 1 second
		Scheduler.getInstance().add(new Pause(this.pauseDur));
		// Close scorer
		Robot.hScorer.openHS(false);
		// Pause for 1 second
		Scheduler.getInstance().add(new Pause(this.pauseDur));
		// Retract scorer
		Robot.hScorer.pushHS(false);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Nothing here, all done in initialize
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// Not sure what we want here...
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Ending ScoreHatch");
	}
}
