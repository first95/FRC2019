package frc.robot.commands.hscorer;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchScorer extends Command {
	
	public ManuallyControlHatchScorer() {
		requires(Robot.hScorer);
	}

	@Override
	protected void execute() {
		if (Robot.oi.isGrabHatchButtonPressed()) {
			Robot.hScorer.toggleOpenHS();
		}
		if (Robot.oi.isPushHatchButtonPressed()) {
			Robot.hScorer.togglePushHS();
		}
	}

	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}