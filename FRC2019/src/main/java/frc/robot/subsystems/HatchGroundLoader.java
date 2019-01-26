package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.ManuallyControlHatchGroundLoader;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.SolenoidI;
import frc.robot.components.SolenoidWrapper;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGroundLoader extends Subsystem {
	
	// Motor controllers for the intake/expel chains
	private IMotorControllerEnhanced leftChainDriver, rightChainDriver;
	
	// The solenoids for the cylinder that opens the maw, and operates the wrist
	private SolenoidI hatchWrist;
	
	public HatchGroundLoader() {
		super();
		leftChainDriver  = new AdjustedTalon(Constants.LEFT_H_LOADER_DRIVER);
		rightChainDriver = new AdjustedTalon(Constants.RIGHT_H_LOADER_DRIVER);
		
		// False means it is extended
		hatchWrist = new SolenoidWrapper(Constants.HATCH_GROUND_LOADER);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManuallyControlHatchGroundLoader());
	}
	
	public void log() {
	
	}
	
	public void setHGroundLoaderRetracted(boolean retracted) {
		hatchWrist.set(retracted);
	}

	public boolean getHGroundLoaderRetracted() {
		return hatchWrist.get();
	}	
	
	public void setIntakeSpeed(double outwardThrottle) {
		leftChainDriver.set(ControlMode.PercentOutput, -outwardThrottle);
		rightChainDriver.set(ControlMode.PercentOutput, outwardThrottle);
	}
}