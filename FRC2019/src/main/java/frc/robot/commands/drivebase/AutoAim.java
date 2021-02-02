/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.LimeLight;
import frc.robot.Constants;


public class AutoAim extends Command {

  private double lastError = 0;
  private double integral = 0;

  public AutoAim() {
    requires(Robot.drivebase);
    requires(Robot.limelight);
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.limelight.setCamProcessing();
    double left = 0;
    double right = 0;
    double errorPercent = 0;
    double error = Robot.limelight.getTX();
    double targetValid = Robot.limelight.getTV();
    double proportional = 0;
    double derivitive = 0;
    double rawCorrection = 0;
    double kp = SmartDashboard.getNumber("Vision Kp", 1);
    double ki = SmartDashboard.getNumber("Vision Ki", 0);
    double kd = SmartDashboard.getNumber("Vision Kd", 0);
    if (targetValid == 1) {
      if (error > Constants.VISION_ON_TARGET_DEG || error < -Constants.VISION_ON_TARGET_DEG) {
        errorPercent = (error / 54);
        proportional = kp * errorPercent;
        integral = ki * (errorPercent + integral);
        derivitive = kd * (errorPercent - lastError);
        rawCorrection = Math.max(-Constants.VISION_AIM_MAX_SPEED_PERCENT, Math.min(Constants.VISION_AIM_MAX_SPEED_PERCENT, (proportional + integral + derivitive)));
        if (rawCorrection > -Constants.VISION_AIM_MIN_SPEED_PERCENT && rawCorrection < Constants.VISION_AIM_MIN_SPEED_PERCENT) {
          right = Constants.VISION_AIM_MIN_SPEED_PERCENT * (rawCorrection / Math.abs(rawCorrection));
        }
        else {
          right = rawCorrection;
        }
        left = -right;
        Robot.limelight.targetLightOff();
      }
      else {
        left = 0;
        right = 0;
        Robot.limelight.targetLightOn();
      }
    }
    lastError = errorPercent; 
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
    Robot.limelight.setCamDriver();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drivebase.tank(0, 0);
    Robot.limelight.targetLightOff();
    Robot.limelight.setCamDriver();
  }
}
