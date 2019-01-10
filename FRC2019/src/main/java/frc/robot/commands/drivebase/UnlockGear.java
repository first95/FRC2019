package frc.robot.commands.drivebase;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UnlockGear extends Command{

	@Override
	protected void initialize() {
		super.initialize();
		Robot.oi.setShiftLockValue(0);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
