package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetWristPosition extends Command {
	private double wristPosition;
	
	public SetWristPosition(double position) {
        requires(Robot.hGroundLoader);
        wristPosition = position;
	}

	@Override
	public void start() {
		Robot.hGroundLoader.setWristRot(wristPosition);
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.hGroundLoader.isWristPositionOnTarget();
	}

}