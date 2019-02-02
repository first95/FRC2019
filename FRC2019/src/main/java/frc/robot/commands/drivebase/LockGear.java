package frc.robot.commands.drivebase;

import frc.robot.Robot;
import frc.robot.Constants.GearShiftMode;
import edu.wpi.first.wpilibj.command.Command;

public class LockGear extends Command {

	private boolean m_lockGear;

	public LockGear(boolean lockHighGear) {
		requires(Robot.drivebase);
		m_lockGear = lockHighGear;
	}

	@Override
	protected void initialize() {
		super.initialize();

		if (m_lockGear) {
			Robot.oi.setShiftMode(GearShiftMode.LOCK_HIGH_GEAR);
		} else {
			Robot.oi.setShiftMode(GearShiftMode.LOCK_LOW_GEAR);
		}

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
