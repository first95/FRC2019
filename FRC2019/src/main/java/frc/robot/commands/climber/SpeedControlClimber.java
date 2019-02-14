package frc.robot.commands.climber;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedControlClimber extends Command {
	
	public SpeedControlClimber() {
		requires(Robot.climber);
	}

	@Override
	protected void execute() {
		Robot.climber.setSpeed(Robot.oi.getClimberSpeed());
		Robot.climber.deploySkids(Robot.oi.isDeploySkidsButtonPressed());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}