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
  private double tv, ta, tx, ty, x, y, z, normalizer, conversion, distance, floorDistance, tvert;
  private IMotorControllerEnhanced targetLight;
  public int camMode = 2;


  public LimeLight() {
    limelight_target_data = NetworkTableInstance.getDefault().getTable("limelight");
    targetLight = new AdjustedTalon(Constants.CLIMBER_DRIVER);
    setCamDriver();
  }

  @Override
  public void periodic() {
    tv = limelight_target_data.getEntry("tv").getDouble(0.0);
    tx = limelight_target_data.getEntry("tx").getDouble(0.0);
    ty = limelight_target_data.getEntry("ty").getDouble(0.0);
    ta = limelight_target_data.getEntry("ta").getDouble(0.0);
    tvert = limelight_target_data.getEntry("tvert").getDouble(0.0);

   /* normalizer = Math.sqrt(Math.pow(Math.tan(Math.toRadians(tx)), 2) + Math.pow(Math.tan(Math.toRadians(ty)), 2) + 1);
    x = Math.tan(Math.toRadians(tx)) / normalizer;
    y = Math.tan(Math.toRadians(ty)) / normalizer;
    z = 1 / normalizer;
    conversion = Constants.HEIGHT_DIFFERENCE / y;
    
    distance = Math.hypot(x, z) * conversion; */

    distance = (Constants.TEST_TARGET_TALLNESS_INCHES / 2) / Math.tan(Math.toRadians(tvert * Constants.DEGREES_PER_PIXEL) / 2);
    floorDistance = Math.sqrt(Math.pow(distance, 2) - Math.pow(Constants.HEIGHT_DIFFERENCE, 2));

    SmartDashboard.putNumber("Bearing", tx);
    SmartDashboard.putNumber("LimelightY", ty);
    SmartDashboard.putNumber("LimelightArea", ta);
    SmartDashboard.putNumber("Target Valid?", tv);
    SmartDashboard.putNumber("Range (in)", distance);
    SmartDashboard.putNumber("Horiz. Range", floorDistance);
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
  public double getFloorDistanceToTarg() {
    return floorDistance;
  }

  public void targetLightOn() {
    targetLight.set(ControlMode.PercentOutput, 1);
  }
  public void targetLightOff() {
    targetLight.set(ControlMode.PercentOutput, 0);
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
