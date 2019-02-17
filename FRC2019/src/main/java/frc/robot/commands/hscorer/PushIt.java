package frc.robot.commands.hscorer;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

/**
 * Set the hatch scorer to open or closed, then wait a short duration for it to actuate
 */
public class PushIt extends TimedCommand {
    private static final double DEFAULT_DURATION_S = 0.3;
    private boolean push;
	
	public PushIt(boolean push, double durationS) {
        super(durationS);
        requires(Robot.hScorer);
        this.push = push;
	}
	public PushIt(boolean push) {
        this(push, DEFAULT_DURATION_S);
	}

	@Override
	public void start() {
		super.start();
		Robot.hScorer.pushHS(push);
	}
}
