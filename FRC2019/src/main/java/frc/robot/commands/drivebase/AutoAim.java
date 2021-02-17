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
import frc.robot.OI.Controller;
import frc.robot.commands.RumbleCommand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.Constants;
import frc.robot.OI;


public class AutoAim extends Command {

  private double headingLastError = 0;
  private double headingIntegral = 0;
  private double rangeLastError = 0;
  private double rangeIntegral = 0;
  private double desiredDistance = 81;

  public AutoAim(double desiredDistance) {
    requires(Robot.drivebase);
    requires(Robot.limelight);
    this.desiredDistance = desiredDistance;
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.limelight.setCamProcessing();

    double headingLeft = 0;
    double headingRight = 0;
    double rangeLeft = 0;
    double rangeRight = 0;
    double left = 0;
    double right = 0;
    double errorPercent = 0;
    double rangeErrorPercent = 0;

    boolean headingOnTarget = false;
    boolean rangeOnTarget = false;
    boolean onTarget = false;

    double headingError = Robot.limelight.getTX();
    double rangeError = Robot.limelight.getFloorDistanceToTarg() - (desiredDistance * Constants.TARGET_HEIGHT_RATIO);
    double targetValid = Robot.limelight.getTV();

    double headingProportional = 0;
    double headingDerivitive = 0;
    double headingRawCorrection = 0;
    double headingkp = SmartDashboard.getNumber("Vision Kp", 1);
    double headingki = SmartDashboard.getNumber("Vision Ki", 0);
    double headingkd = SmartDashboard.getNumber("Vision Kd", 0);

    double rangeProportional = 0;
    double rangeDerivitive = 0;
    double rangeRawCorrection = 0;
    double rangekp = SmartDashboard.getNumber("Vision range Kp", 1);
    double rangeki = SmartDashboard.getNumber("Vision range Ki", 0);
    double rangekd = SmartDashboard.getNumber("Vision range Kd", 0);

    if (targetValid == 1) {
      if (headingError > Constants.VISION_ON_TARGET_DEG || headingError < -Constants.VISION_ON_TARGET_DEG) {
        errorPercent = (headingError / Constants.VISION_CAM_FOV_X_DEG);
        headingProportional = headingkp * errorPercent;
        headingIntegral = headingki * (errorPercent + headingIntegral);
        headingDerivitive = headingkd * (errorPercent - headingLastError);
        headingRawCorrection = Math.max(-Constants.VISION_AIM_MAX_SPEED_PERCENT, Math.min(Constants.VISION_AIM_MAX_SPEED_PERCENT, (headingProportional + headingIntegral + headingDerivitive)));
        if (headingRawCorrection > -Constants.VISION_AIM_MIN_SPEED_PERCENT && headingRawCorrection < Constants.VISION_AIM_MIN_SPEED_PERCENT) {
          headingRight = Constants.VISION_AIM_MIN_SPEED_PERCENT * (headingRawCorrection / Math.abs(headingRawCorrection));
        }
        else {
          headingRight = headingRawCorrection;
        }
        headingLeft = -headingRight;
        headingOnTarget = false;
      }
      else {
        headingLeft = 0;
        headingRight = 0;
        headingOnTarget = true;
      }
      if (rangeError > Constants.VISION_ON_TARGET_INCH_RANGE || rangeError < -Constants.VISION_ON_TARGET_INCH_RANGE) {
        rangeErrorPercent = rangeError / (desiredDistance * Constants.TARGET_HEIGHT_RATIO);
        rangeProportional = -rangeErrorPercent;
        rangeIntegral = rangeErrorPercent + rangeIntegral;
        rangeDerivitive = rangeErrorPercent - rangeLastError;
        rangeRawCorrection = Math.max(-Constants.VISION_AIM_RANGE_MAX_SPEED_PERCENT, Math.min(Constants.VISION_AIM_RANGE_MAX_SPEED_PERCENT, ((rangeProportional * rangekp) + (rangeIntegral * rangeki) + (rangeDerivitive * rangekd))));
        if (rangeRawCorrection > -Constants.VISION_RANGE_MIN_SPEED_PERCENT && rangeRawCorrection < Constants.VISION_RANGE_MIN_SPEED_PERCENT) {
          rangeRight = Math.copySign(Constants.VISION_AIM_MIN_SPEED_PERCENT, rangeRawCorrection);
        }
        else {
          rangeRight = rangeRawCorrection;
        }
        rangeLeft = rangeRight;
        rangeOnTarget = false;
      }
      else {
        rangeLeft = 0;
        rangeRight = 0;
        rangeOnTarget = true;
      }
    } else {
      Robot.oi.Rumble(Controller.DRIVER, RumbleType.kLeftRumble, 1.0, 0.5);
    }
    if (headingOnTarget && rangeOnTarget) {
      Robot.limelight.targetLightOn();
    }
    left = headingLeft + rangeLeft;
    right = headingRight + rangeRight;
    headingLastError = errorPercent;
    rangeLastError = rangeErrorPercent; 
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
