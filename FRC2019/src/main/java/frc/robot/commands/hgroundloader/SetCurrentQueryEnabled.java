package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetCurrentQueryEnabled extends Command {
	private boolean shouldBeEnabled;
	
	public SetCurrentQueryEnabled(boolean shouldBeEnabled) {
        requires(Robot.hGroundLoader);
        this.shouldBeEnabled = shouldBeEnabled;
    }
    
    @Override
    public synchronized void start() {
        super.start();
        Robot.hGroundLoader.setWristQuery(shouldBeEnabled);
        Robot.hGroundLoader.setIntakeQuery(shouldBeEnabled);
    }
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}