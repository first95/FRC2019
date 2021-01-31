/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.components.AdjustedTalon;

/**
 * Currently just gets numbers from the limelight
 */
public class LimeLight extends Subsystem {
  private final NetworkTable limelight_target_data;
  private double tv, ta, tx, ty, ts, distance;
  private IMotorControllerEnhanced driver;
  public int camMode = 2;


  public LimeLight() {
    limelight_target_data = NetworkTableInstance.getDefault().getTable("limelight");
    driver = new AdjustedTalon(Constants.CLIMBER_DRIVER);
    setCamDriver();
  }

  @Override
  public void periodic() {
    tv = limelight_target_data.getEntry("tv").getDouble(0.0);
    tx = limelight_target_data.getEntry("tx").getDouble(0.0);
    ty = limelight_target_data.getEntry("ty").getDouble(0.0);
    ta = limelight_target_data.getEntry("ta").getDouble(0.0);
    ts = limelight_target_data.getEntry("ts").getDouble(0.0);

    distance = (Constants.TEST_TARGET_HEIGHT_INCHES - Constants.CAM_HEIGHT_INCHES) / Math.tan(Math.toRadians(ty));

    SmartDashboard.putNumber("Bearing", tx);
    SmartDashboard.putNumber("LimelightY", ty);
    SmartDashboard.putNumber("LimelightArea", ta);
    SmartDashboard.putNumber("Target Valid?", tv);
    SmartDashboard.putNumber("Range (in)", distance);
  }

  public double getTX() {
    return tx;
  }
  public double getTY() {
    return ty;
  }
  public double getTA() {
    return ta;
  }
  public double getTV() {
    return tv;
  }
  public double getDistanceToTarg() {
    return distance;
  }

  public void targetLightOn() {
    driver.set(ControlMode.PercentOutput, 1);
  }
  public void targetLightOff() {
    driver.set(ControlMode.PercentOutput, 0);
  }

  public void setCamProcessing() {
    limelight_target_data.getEntry("stream").setNumber(1);
    camMode = 1;
  }
  public void setCamDriver() {
    limelight_target_data.getEntry("stream").setNumber(2);
    camMode = 2;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
