/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.SetCameraMode;

/**
 * A subsystem to read from the vision coprocessor
 */
public class VisionCoprocessor extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new SetCameraMode());
    }

    public class VisionTargetInfo {
        public double bearingDegrees;
        public double rangeInches;
    }

    public LinkedList<VisionTargetInfo> getCurVisibleVisionTargets() {
        return new LinkedList<VisionTargetInfo>();
    }

    public boolean isCameraHumanVision() {
        return false;
    }

    public void setCameraIsHumanVisible(boolean isHumanVisible) {

    }
}
