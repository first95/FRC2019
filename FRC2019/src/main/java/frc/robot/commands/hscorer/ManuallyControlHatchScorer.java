package frc.robot.commands.hscorer;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchScorer extends Command {
	
	public ManuallyControlHatchScorer() {
		requires(Robot.hScorer);
	}

	@Override
	protected void execute() {
		// First priority: Is the user holding down the OPEN button?
		if (Robot.oi.isToggleHSOpenButtonPressed()) {
			Robot.hScorer.toggleOpenHS();
		} 
			
		// Next priority: Is the user holding down the PUSH button?
		if (Robot.oi.isToggleHSPushButtonPressed()) {
			Robot.hScorer.togglePushHS();
		} 		
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}