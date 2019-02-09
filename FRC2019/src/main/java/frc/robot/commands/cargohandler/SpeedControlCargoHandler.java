package frc.robot.commands.cargohandler;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedControlCargoHandler extends Command {
	private double wristSpeed;
	
	public SpeedControlCargoHandler() {
		requires(Robot.cargoHandler);
	}

	@Override
	protected void execute() {
		Robot.cargoHandler.setIntakeSpeed(Robot.oi.getHGLIntakeSpeed());
		wristSpeed = Robot.oi.getHGLWristSpeed();
		Robot.cargoHandler.setWristPitchSpeed(wristSpeed);
		SmartDashboard.putNumber("HGL Wrist Input", wristSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}