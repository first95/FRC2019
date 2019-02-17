package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetWristAngle extends Command {
	private double targetDegrees = 0.0;
	private boolean waitForWristToReachTarget;

	public SetWristAngle(double target, boolean wait) {
		// This method is run once during robot startup
		requires(Robot.hGroundLoader);
		targetDegrees = target;
		waitForWristToReachTarget = wait;
	}

	@Override
	public synchronized void initialize() {
		Robot.hGroundLoader.setWristAngleDeg(targetDegrees);
	}
	
	@Override
	protected void execute() {
		// This method is called every iteration
		
		// Nothing needed; we did everything we needed in initialize()
	}
	
	@Override
	protected boolean isFinished() {
		boolean finished = (!waitForWristToReachTarget) || Robot.hGroundLoader.isOnTarget();
		return finished;
	}
	
}
