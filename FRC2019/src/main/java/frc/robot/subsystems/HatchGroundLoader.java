package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.ManuallyControlHatchGroundLoader;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.FakeTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGroundLoader extends Subsystem {
	
	// Motor controllers for the roller and the wrist
	private IMotorControllerEnhanced rollerDriver, wristDriver;
	
	public HatchGroundLoader(boolean realHardware) {
		super();
		
		if(realHardware) {
			rollerDriver  = new AdjustedTalon(Constants.ROLLER_HGL_DRIVER);
			wristDriver = new AdjustedTalon(Constants.WRIST_HGL_DRIVER);
		} else {
			rollerDriver  = new FakeTalon();
			wristDriver = new FakeTalon();
		}
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManuallyControlHatchGroundLoader());
	}
	
	public void log() {
	
	}	
	
	public void setRollerSpeed(double outwardThrottle) {
		rollerDriver.set(ControlMode.PercentOutput, outwardThrottle);
	}
	
	public void setWristSpeed(double outwardThrottle) {
		wristDriver.set(ControlMode.PercentOutput, outwardThrottle);
	}	
	
	public Boolean wristAtLimit() {
		return wristDriver.getOutputCurrent() >= Constants.HGL_MAX_WRIST_CURRENT_AMPS;
	}
}