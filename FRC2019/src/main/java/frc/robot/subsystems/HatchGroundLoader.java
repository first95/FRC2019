package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.SpeedControlHatchGroundLoader;
import frc.robot.components.AdjustedTalon;
import frc.robot.components.FakeTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGroundLoader extends Subsystem {
	
	// Motor controller for the intake rollers
	private IMotorControllerEnhanced intakeDriver;
	// Pitch up/down motor
	private IMotorControllerEnhanced wristDriver;
	
	// Wrist variables
	private double wristCur = 0;
	private double wristDTMs = 0;
	private boolean wristLast = false;
	private boolean wristDet = false;

	// Encoder and position info for wristDriver
	private static final double DEGREES_FULL_RANGE = 90; // How many degrees the wrist can move; needs to be measured
	private static final double ENCODER_TICKS_FULL_RANGE = 78400.0; // How many encoder ticks the wrist can move; needs to be measured
	private static final double TICKS_PER_DEG = ENCODER_TICKS_FULL_RANGE / DEGREES_FULL_RANGE;
	private static double wristUp = 90;
	private static double wristDown = 0;

	public HatchGroundLoader(boolean realHardware) {
		super();
		
		if(realHardware) {
			intakeDriver = new AdjustedTalon(Constants.HGL_INTAKE);
			wristDriver  = new AdjustedTalon(Constants.HGL_WRIST);
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
	
	public void visit() {
		updateCurrents();
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
	 * Update the currents for the wrist and intake
	 */		
	public void updateCurrents() {
		wristCur = wristDriver.getOutputCurrent();
		if (wristCur >= Constants.HGL_MAX_WRIST_CURRENT_AMPS) {
			// Went from below to above threshold so set time
			if (!wristLast) { wristDTMs = System.nanoTime()*1000; }
			if (!wristDet) {
				// Check if detection time has elapsed
				if (System.nanoTime()*1000 - wristDTMs >= Constants.HGL_MAX_WRIST_CURRENT_DURATION_MS) {
					wristDet = true;
				}
			} 
			wristLast = true;
		} else {
			// Current is below detection level so re-set wristDet and wristDTMs
			if (wristLast) { wristDet = false; }
			wristLast = false;
		}
		SmartDashboard.putNumber("HGL Wrist Current (A)",wristCur);
		SmartDashboard.putNumber("HGL Wrist High Current Detected",wristDet ? 1: 0);
	}
}