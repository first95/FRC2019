package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SpeedControlHatchGroundLoader extends Command {
	private double wristSpeed;
	
	public SpeedControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
		setInterruptible(true);
	}

	@Override
	protected void execute() {
		Robot.hGroundLoader.setIntakeThrottle(Robot.oi.getHGLIntakeSpeed());
		wristSpeed = Robot.oi.getHGLWristSpeed();
		Robot.hGroundLoader.setWristPitchSpeed(wristSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}