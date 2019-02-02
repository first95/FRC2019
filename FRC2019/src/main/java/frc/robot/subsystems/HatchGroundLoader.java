package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.ManuallyControlHatchGroundLoader;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.FakeTalon;
import frc.robot.components.SolenoidI;
import frc.robot.components.SolenoidWrapper;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGroundLoader extends Subsystem {
	
	// Motor controllers for the intake/expel chains
	private IMotorControllerEnhanced leftChainDriver, rightChainDriver;
	
	// The solenoids for the cylinder that operates wrist action
	private SolenoidI hatchWrist;
	
	public HatchGroundLoader(boolean realHardware) {
		super();
		
		if(realHardware) {
			leftChainDriver  = new AdjustedTalon(Constants.LEFT_HGL_DRIVER);
			rightChainDriver = new AdjustedTalon(Constants.RIGHT_HGL_DRIVER);
		} else {
			leftChainDriver  = new FakeTalon();
			rightChainDriver = new FakeTalon();
		}
		
		// False means the wrist is extended
		hatchWrist = new SolenoidWrapper(Constants.HGL_WRIST_SOLENOID_NUM);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManuallyControlHatchGroundLoader());
	}
	
	public void log() {
	
	}
	
	public void setRetracted(boolean retracted) {
		hatchWrist.set(retracted);
	}
	
	public void setIntakeSpeed(double outwardThrottle) {
		leftChainDriver.set(ControlMode.PercentOutput, -outwardThrottle);
		rightChainDriver.set(ControlMode.PercentOutput, outwardThrottle);
	}

	public boolean getRetracted() {
		return hatchWrist.get();
	}	
}