package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.SpeedControlHatchGroundLoader;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.FakeTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoHandler extends Subsystem {
	
	// Motor controller for the intake rollers
	private IMotorControllerEnhanced intakeDriver;
	// Pitch up/down motor
	private IMotorControllerEnhanced wristDriver;
	
	// Encoder and position info for wristDriver
	private static final double DEGREES_FULL_RANGE = 90; // How many degrees the wrist can move; needs to be measured
	private static final double ENCODER_TICKS_FULL_RANGE = 78400.0; // How many encoder ticks the wrist can move; needs to be measured
	private static final double TICKS_PER_DEG = ENCODER_TICKS_FULL_RANGE / DEGREES_FULL_RANGE;
	private static double wristUp = 90;
	private static double wristDown = 0;

	public CargoHandler(boolean realHardware) {
		super();
		
		if(realHardware) {
			intakeDriver = new AdjustedTalon(Constants.CARGO_HANDLER_INTAKE);
			wristDriver  = new AdjustedTalon(Constants.CARGO_HANDLER_WRIST);
		} else {
			intakeDriver = new FakeTalon();
			wristDriver  = new FakeTalon();
		}
		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new SpeedControlHatchGroundLoader());
	}
	
	public void log() {
	
	}	
	

	/**
	 * Set speed of the intake rollers
	 * @param inwardThrottle 1.0 for fully inward, -1.0 for fully outward, 0.0 for stationary
	 */
	public void setIntakeSpeed(double inwardThrottle) {
		intakeDriver.set(ControlMode.PercentOutput, inwardThrottle);
	}

	/**
	 * Set speed of the wrist movement
	 * @param upwardSpeed 1.0 for fully upward, -1.0 for fully downward, 0.0 for stationary
	 */
	public void setWristPitchSpeed(double upwardSpeed) {
		// Slow it way the hell down for starters
		// and reverse the direction so up is up and down is down
		wristDriver.set(ControlMode.PercentOutput, -upwardSpeed * 0.2);	
	}
	
	/**
	 * Lower the wrist to the wristDown position using position control
	 */	
	public void lowerWrist() {
		wristDriver.set(ControlMode.Position, wristDown*TICKS_PER_DEG);
	}

	/**
	 * Raise the wrist to the wristUp position using position control
	 */	
	public void raiseWrist() {
		wristDriver.set(ControlMode.Position, wristUp*TICKS_PER_DEG);
	}

	/**
	 * Check if the wrist is at its limit based on the current being sufficiently large
	 */		
	public Boolean wristAtLimit() {
		return wristDriver.getOutputCurrent() >= Constants.HGL_MAX_WRIST_CURRENT_AMPS;
	}
}