package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetWristPosition extends Command {
    private double wristPosition;
    private boolean waitForWristToReachTarget;
    
    /**
     * 
     * @param position the target position to seek
     * @param wait when true, this command waits until the wrist reaches the target before reporting completion.  When false, this command sets the setpoint and then ends immediately.
     */
	public SetWristPosition(double position, boolean wait) {
        requires(Robot.hGroundLoader);
        wristPosition = position;
        waitForWristToReachTarget = wait;
	}

	@Override
	public void start() {
		Robot.hGroundLoader.setWristRot(wristPosition);
	}
	
	@Override
	protected boolean isFinished() {
		return (!waitForWristToReachTarget) || Robot.hGroundLoader.isWristPositionOnTarget();
	}

}