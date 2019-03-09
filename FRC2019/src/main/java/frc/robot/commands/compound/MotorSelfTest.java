/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.compound;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.elevator.SetElevatorHeight;
import frc.robot.commands.hgroundloader.SetIntakeThrottle;
import frc.robot.commands.cargohandler.SetWristAngle;
import frc.robot.commands.cargohandler.TestForCargoIntakeMotor;

public class MotorSelfTest extends CommandGroup {
  /**
   * This is a command that would be called in auto mode, or "sandstorm" mode, while in the pits to run a quick
   * and simple test of all the motors that are supposed to be connected at the time of the test.
   */
  public MotorSelfTest() {
    //test for the drivebase
    addSequential(new DriveStraight(0.001));

    //test for the elevator
    addSequential(new SetElevatorHeight(0.001));

    //test for the cargo handler wrist
    addSequential(new SetWristAngle(0.001));

    //test for the cargo handler intake
    //addSequential(new TestForCargoIntakeMotor(0.001));

    //test for the hatch ground loader
    addSequential(new PickupAndHandoffGroundHatch());
  }
}
