package frc.robot.commands.hscorer;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchScorer extends Command {
	
	public ManuallyControlHatchScorer() {
		requires(Robot.hScorer);
	}

	@Override
	protected void execute() {
		Robot.hScorer.openHS(Robot.oi.isGrabHatchButtonPressed());
		Robot.hScorer.pushHS(Robot.oi.isPushHatchButtonPressed());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}