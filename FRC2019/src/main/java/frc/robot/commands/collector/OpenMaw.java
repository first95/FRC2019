package frc.robot.commands.collector;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenMaw extends Command{

	public OpenMaw() {
		requires(Robot.collector);
	}
	
	@Override
	protected void initialize() {
		Robot.collector.setMawOpen(true);
		super.initialize();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
