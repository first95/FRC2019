package frc.robot.commands.hgroundloader;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI.Controller;
import frc.robot.OI.RumbleType;
import frc.robot.commands.Pause;
import frc.robot.commands.RumbleCommand;
import frc.robot.subsystems.HatchGroundLoader;

public class AutoAcquire extends CommandGroup {
    public static final double AUTO_ACQUIRE_INTAKE_THROTTLE = 1.0;
    public static final double RUMBLE_TIME_S = 0.5;
    public static final double SPINUP_DURATION_S = 0.5;

    public AutoAcquire() {
        this(true);
        //CombinedControlHGroundLoader.setInterrupt(true);
    }
    public AutoAcquire(boolean buzz) {
        // Drop it
        addSequential(new RumbleCommand(Controller.WEAPONS, RumbleType.LOW_PITCH, 1.0, RUMBLE_TIME_S, false));
        addSequential(new SetWristAngle(HatchGroundLoader.COLLECT_DEG, true));
        addSequential(new RumbleCommand(Controller.WEAPONS, RumbleType.HIGH_PITCH, 1.0, RUMBLE_TIME_S, false));
        // // Spin it 
        // addSequential(new SetIntakeThrottle(AUTO_ACQUIRE_INTAKE_THROTTLE));        
        // // Wait for it
        // addSequential(new Pause(SPINUP_DURATION_S));
        // // Waaaaaaaait for it
        // addSequential(new WaitForHatchDetected());
        // // Stop it
        // addSequential(new SetIntakeThrottle(0));
        // if(buzz) {
        //     // Buzz it
        //     addSequential(new RumbleCommand(Controller.WEAPONS, RumbleType.HIGH_PITCH, 1.0, RUMBLE_TIME_S, true));
        //     //addSequential(new RumbleCommand(Controller.DRIVER, RumbleType.LOW_PITCH, 1.0, RUMBLE_TIME_S, true));
        // }
        // // Lift it
        // addSequential(new SetWristAngle(HatchGroundLoader.UP_DEG, true));
    }
}