package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.cargohandler.SpeedControlCargoHandler;
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
		setDefaultCommand(new SpeedControlCargoHandler());
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
	
}