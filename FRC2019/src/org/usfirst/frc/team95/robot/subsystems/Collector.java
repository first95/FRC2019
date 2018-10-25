package org.usfirst.frc.team95.robot.subsystems;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.Robot;
import org.usfirst.frc.team95.robot.commands.collector.ManuallyControlCollector;
import org.usfirst.frc.team95.robot.components.AdjustedTalon;
import org.usfirst.frc.team95.robot.components.SolenoidI;
import org.usfirst.frc.team95.robot.components.SolenoidWrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Collector extends Subsystem {
	// We can wire the Banner photoelectric sensors in a few ways.
	// Here we specify the Digital IO value that will be returned
	// by DigitalInput.get() when a reflective object is present.
	// (we're using Banner part number QS18VN6LV)
	private static final boolean DIO_VALUE_FOR_DETECTION = true;
	
	// Motor controllers for the intake/expel chains
	private IMotorControllerEnhanced intakeDriver;
	
	private SolenoidI upDown;
	
	public Collector() {
		super();
		intakeDriver  = new AdjustedTalon(Constants.INTAKE_DRIVER);
		upDown = new SolenoidWrapper(Constants.COLLECTOR_SOLENOID_NUM);
	}

	
	public void log() {
		SmartDashboard.putBoolean("Up/down?" , Robot.oi.getCollectorOpen());
	}

	public void setUpDown(boolean up) {
		upDown.set(up);
	}
	
	public void setIntakeSpeed(double inwardThrottle) {
		intakeDriver.set(ControlMode.PercentOutput, inwardThrottle);
	}


	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new ManuallyControlCollector());
	}
	
}
