package frc.robot.commands.drivebase;

import frc.robot.Robot;
import frc.robot.Constants.GearShiftMode;
import edu.wpi.first.wpilibj.command.Command;

public class UnlockGear extends Command{

	@Override
	protected void initialize() {
		super.initialize();
		Robot.oi.setShiftMode(GearShiftMode.AUTOSHIFT);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
