package frc.robot.commands.compound;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI.RumbleType;
import frc.robot.OI.Controller;
import frc.robot.commands.RumbleCommand;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.hgroundloader.AutoAcquire;
import frc.robot.subsystems.Elevator.ElevatorHoldPoint;

/**
 * This command picks up a hatch with the ground loader, then when it detects a hatch in the loader,
 * it lifts up the loader and hands it off to the elevator.
 */
public class PickupAndHandoffGroundHatch extends CommandGroup {
    public static final double RUMBLE_TIME_S = 0.5;
    public PickupAndHandoffGroundHatch() {
        // Move the elevator to handoff position
        addSequential(new SetElevatorHeight(ElevatorHoldPoint.HATCH_HANDOFF));
        // Perform HGL auto acquire sequence
        addSequential(new AutoAcquire(false));
        // Buzz it after the HGL wrist lifts
        addSequential(new RumbleCommand(Controller.WEAPONS, RumbleType.HIGH_PITCH, 1.0, RUMBLE_TIME_S, true));
        addSequential(new RumbleCommand(Controller.DRIVER, RumbleType.LOW_PITCH, 1.0, RUMBLE_TIME_S, true));
        // Move the elevator to scoring position
        addSequential(new SetElevatorHeight(ElevatorHoldPoint.HATCH_COVER_LOW));
    }
}