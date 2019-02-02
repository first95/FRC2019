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
	
	// Encoder and position info for wristDriver
	private static final double DEGREES_FULL_RANGE = 90; // How many degrees the wrist can move; needs to be measured
	private static final double ENCODER_TICKS_FULL_RANGE = 78400.0; // How many encoder ticks the wrist can move; needs to be measured
	private static final double TICKS_PER_DEG = ENCODER_TICKS_FULL_RANGE / DEGREES_FULL_RANGE;
	private static double wristUp = 90;
	private static double wristDown = 0;

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
	
	public void lowerWrist() {
		wristDriver.set(ControlMode.PercentOutput, wristDown*TICKS_PER_DEG);
	}

	public void raiseWrist() {
		wristDriver.set(ControlMode.PercentOutput, wristUp*TICKS_PER_DEG);
	}

	public Boolean wristAtLimit() {
		return wristDriver.getOutputCurrent() >= Constants.HGL_MAX_WRIST_CURRENT_AMPS;
	}
}