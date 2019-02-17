package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedControlHatchGroundLoader extends Command {
	private double intakeSpeed;
	private double wristSpeed;
	
	public SpeedControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
		setInterruptible(true);
	}

	@Override
	protected void execute() {
		intakeSpeed = Robot.oi.getHGLIntakeSpeed();
		Robot.hGroundLoader.setIntakeThrottle(intakeSpeed);
		SmartDashboard.putNumber("HGL Intake Input", intakeSpeed);

		wristSpeed = Robot.oi.getHGLWristSpeed();
		Robot.hGroundLoader.setWristPitchSpeed(wristSpeed);
		SmartDashboard.putNumber("HGL Wrist Input", wristSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}