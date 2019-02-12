package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetIntakeSpeed extends Command {
	private double intakeSpeed;
	
	public SetIntakeSpeed(double speed) {
        requires(Robot.hGroundLoader);
        intakeSpeed = speed;
	}

	@Override
	protected void execute() {
		Robot.hGroundLoader.setIntakeSpeed(intakeSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}