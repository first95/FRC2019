/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class LimeLight extends Subsystem {
  private final NetworkTable limelight_target_data;
  private double tv, ta, tx, ty, ts;


  public LimeLight() {
    limelight_target_data = NetworkTableInstance.getDefault().getTable("limelight");
  }

  @Override
  public void periodic() {
    tv = limelight_target_data.getEntry("tv").getDouble(0.0);
    tx = limelight_target_data.getEntry("tx").getDouble(0.0);
    ty = limelight_target_data.getEntry("ty").getDouble(0.0);
    ta = limelight_target_data.getEntry("ta").getDouble(0.0);
    ts = limelight_target_data.getEntry("ts").getDouble(0.0);

    SmartDashboard.putNumber("LimelightX", tx);
    SmartDashboard.putNumber("LimelightY", ty);
    SmartDashboard.putNumber("LimelightArea", ta);
    SmartDashboard.putNumber("Target Valid?", tv);
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


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
