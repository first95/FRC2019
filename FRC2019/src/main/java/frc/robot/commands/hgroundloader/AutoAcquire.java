package frc.robot.commands.hgroundloader;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI.Controller;
import frc.robot.OI.RumbleType;
import frc.robot.commands.RumbleCommand;
import frc.robot.subsystems.HatchGroundLoader;

public class AutoAcquire extends CommandGroup {
    public static final double AUTO_ACQUIRE_INTAKE_THROTTLE = 1.0;
    public static final double RUMBLE_TIME_S = 0.5;

    public AutoAcquire() {
        // Spin it 
        addSequential(new SetIntakeThrottle(AUTO_ACQUIRE_INTAKE_THROTTLE));
        // Drop it
        addSequential(new SetWristPosition(HatchGroundLoader.WRIST_DOWN_DEG, true));
        // Wait for it
        addSequential(new WaitForHatchDetected());
        // Stop it
        addSequential(new SetIntakeThrottle(0));
        // Buzz it
        addSequential(new RumbleCommand(Controller.WEAPONS, RumbleType.HIGH_PITCH, 1.0, RUMBLE_TIME_S, true));
        // Lift it
        addSequential(new SetWristPosition(HatchGroundLoader.WRIST_UP_DEG, true));
    }
}