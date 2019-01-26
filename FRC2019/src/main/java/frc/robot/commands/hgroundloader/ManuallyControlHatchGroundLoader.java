package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchGroundLoader extends Command {
	
	public ManuallyControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
	}

	@Override
	protected void execute() {
		Robot.hGroundLoader.setIntakeSpeed(Robot.oi.getHGLSpeed());
		Robot.hGroundLoader.setRetracted(Robot.oi.getHGLWristRectracted());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}