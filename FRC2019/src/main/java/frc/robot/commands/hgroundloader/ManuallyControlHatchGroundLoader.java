package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchGroundLoader extends Command {
	
	public ManuallyControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
	}

	@Override
	protected void execute() {
		Robot.hGroundLoader.setIntakeSpeed(Robot.oi.getHGLIntakeSpeed());
		Robot.hGroundLoader.setWristPitchSpeed(Robot.oi.getHGLWristSpeed());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}