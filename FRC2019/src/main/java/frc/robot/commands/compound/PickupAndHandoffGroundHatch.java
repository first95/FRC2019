package frc.robot.commands.compound;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.hgroundloader.AutoAcquire;
import frc.robot.subsystems.Elevator.ElevatorHoldPoint;

/**
 * This command picks up a hatch with the ground loader, then when it detects a hatch in the loader,
 * it lifts up the loader and hands it off to the elevator.
 */
public class PickupAndHandoffGroundHatch extends CommandGroup {
    public PickupAndHandoffGroundHatch() {
        // Move the elevator to handoff position
        addSequential(new SetElevatorHeight(ElevatorHoldPoint.HATCH_HANDOFF));
        // Perform HGL auto acquire sequence
        addSequential(new AutoAcquire());
    }
}