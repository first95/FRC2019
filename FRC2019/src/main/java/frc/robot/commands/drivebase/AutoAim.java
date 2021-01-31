/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Constants;


public class AutoAim extends Command {

  private double kp = 1;

  public AutoAim() {
    requires(Robot.drivebase);
    requires(Robot.limelight);
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double left = 0;
    double right = 0;
    double rawCorrection = 0;
    double error = Robot.limelight.getTX();
    double targetValid = Robot.limelight.getTV();
    if (targetValid == 1) {
      if (error > Constants.VISION_ON_TARGET_DEG || error < -Constants.VISION_ON_TARGET_DEG) {
        rawCorrection = Math.max(-0.5, Math.min(0.5, (error / 54)));
        if (rawCorrection > -0.1 && rawCorrection < 0.1) {
          right = 0.1 * (rawCorrection / Math.abs(rawCorrection));
        }
        else {
          right = rawCorrection;
        }
        left = -1 * right;
        Robot.limelight.targetLightOff();
      }
      else {
        left = 0;
        right = 0;
        Robot.limelight.targetLightOn();
      }
    }
    Robot.drivebase.tank(left, right);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivebase.tank(0, 0);
    Robot.limelight.targetLightOff();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drivebase.tank(0, 0);
    Robot.limelight.targetLightOff();
  }
}
