package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedControlHatchGroundLoader extends Command {
	private double wristSpeed;
	
	public SpeedControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
		setInterruptible(true);
	}

	@Override
	protected void execute() {
		System.out.println("Speed control active");
		Robot.hGroundLoader.setIntakeThrottle(Robot.oi.getHGLIntakeSpeed());
		wristSpeed = Robot.oi.getHGLWristSpeed();
		Robot.hGroundLoader.setWristPitchSpeed(wristSpeed);
		SmartDashboard.putNumber("HGL Wrist Input", wristSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}