package frc.robot.commands.hgroundloader;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
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
        super();
        // Drop it
        addSequential(new SetWristAngle(HatchGroundLoader.COLLECT_DEG, true));
        // Spin it 
        addSequential(new SetIntakeThrottle(AUTO_ACQUIRE_INTAKE_THROTTLE));        
        // Waaaaaaaait for it
        addSequential(new WaitForHatchDetected());
        // Stop it
        addSequential(new SetIntakeThrottle(0));
        if(buzz) {
            // Buzz it
            addParallel(new RumbleCommand(Controller.WEAPONS, RumbleType.HIGH_PITCH, 1.0, RUMBLE_TIME_S, true));
            //addSequential(new RumbleCommand(Controller.DRIVER, RumbleType.LOW_PITCH, 1.0, RUMBLE_TIME_S, true));
        }
        // Lift it
        addSequential(new SetWristAngle(HatchGroundLoader.UP_DEG, true));
    }

    @Override
    public synchronized void start() {
        System.out.println("AutoAcquire.start()");
        super.start();
    }

    @Override
    protected void execute() {
        System.out.println("AutoAcquire.execute()");
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        boolean fin = super.isFinished();
        System.out.println("AutoAcquire.isFinished() - " + fin);
        return fin;
    }
    @Override
    protected void initialize() {
        System.out.println("AutoAcquire.initialize()");
        super.initialize();
    }

    @Override
    protected void end() {
        System.out.println("AutoAcquire.end()");
        super.end();
    }
    @Override
    public synchronized void cancel() {
        System.out.println("AutoAcquire.cancel()");
        super.cancel();
        Scheduler.getInstance().add(new SetWristAngle(HatchGroundLoader.UP_DEG, false));
        Scheduler.getInstance().add(new SetIntakeThrottle(0));
    }
}