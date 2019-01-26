package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManuallyControlHatchGroundLoader extends Command {
	
	public ManuallyControlHatchGroundLoader() {
		requires(Robot.hGroundLoader);
	}

	@Override
	protected void execute() {
		// Reset Wrists operator is located within the OI class
		Robot.hGroundLoader.setIntakeSpeed(Robot.oi.getHGroundLoaderSpeed());
		Robot.hGroundLoader.setHGroundLoaderRetracted(Robot.oi.getHGroundLoaderRectracted());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // until interrupted
	}

}