/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * Add your docs here.
 */
import frc.robot.commands.climber.SpeedControlClimber;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.FakeTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

public class ClimberHabLevelTwo extends Subsystem {

  //Solenoid (1) from the original climber
  private Solenoid sol1;
  //Solenoid (5) for the new climber
  private Solenoid sol2;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public ClimberHabLevelTwo(boolean thisIsABoolean) {
    super();

    if(thisIsABoolean == true) {
      sol1 = new Solenoid(Constants.CLIMBER_SOL_1);
      sol2 = new Solenoid(Constants.CLIMBER_SOL_2);
    } else if(thisIsABoolean == false) {
      sol1 = null;
      sol2 = null;
    }
  }

  @Override
  public void initDefaultCommand() {
  // Set the default command for a subsystem here.
  // setDefaultCommand(new MySpecialCommand());
  }

  public void log(){
  }

  public void frontClimber(boolean deploy1) {
    if(sol2 != null) {
      sol2.set(deploy1);
    }
  }

  public void toggleFrontClimber()
  {
    if(sol2 != null) {
      sol2.set(!sol2.get());
    }
  }

  public void rearClimber(boolean deploy2) {
    if(sol1 != null) {
      sol1.set(deploy2);
    }
  }

  public void toggleRearClimber()
  {
    if(sol1 != null) {
      sol1.set(!sol1.get());
    }
  }
}
