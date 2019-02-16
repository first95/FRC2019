package frc.robot.commands.climber;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SpeedControlClimber extends Command {
	
	public SpeedControlClimber() {
		requires(Robot.climber);
	}

	@Override
	protected void execute() {
		Robot.climber.setSpeed(Robot.oi.getClimberSpeed());
		Robot.climber.deploySkids(Robot.oi.isDeploySkidsButtonPressed());
		// Prefer toggle eventually, but right now this would probably toggle too quickly
		// because would loop through multiple times for one human press
		//if (Robot.oi.isDeploySkidsButtonPressed()) { Robot.climber.toggleSkids(); }
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}