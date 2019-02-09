package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hgroundloader.SpeedControlHatchGroundLoader;
import frc.robot.components.CurrentDetectedTalon;
import frc.robot.components.FakeTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGroundLoader extends Subsystem {
	
	// Motor controller for the intake rollers
	private IMotorControllerEnhanced intakeDriver;
	private CurrentDetectedTalon intD;
	// Pitch up/down motor
	private IMotorControllerEnhanced wristDriver;
	private CurrentDetectedTalon wrtD;

	// Real hardware?
	private boolean realHW;

	// Encoder and position info for wristDriver
	private static final double DEGREES_FULL_RANGE = 90; // How many degrees the wrist can move; needs to be measured
	private static final double ENCODER_TICKS_FULL_RANGE = 78400.0; // How many encoder ticks the wrist can move; needs to be measured
	private static final double TICKS_PER_DEG = ENCODER_TICKS_FULL_RANGE / DEGREES_FULL_RANGE;

	private static final double K_F = 0.0; // Don't use in position mode.
	private double K_P = 0.4 * 1023.0 / 900.0; // Respond to an error of 900 with 40% throttle
	private double K_I = 0.01 * K_P;
	private double K_D = 0; // 40.0 * K_P;
	private static final int I_ZONE = 200; // In closed loop error units
	private final String pLabel = "HGL Wrist P";
	private final String iLabel = "HGL Wrist I";
	private final String dLabel = "HGL Wrist D";

	public HatchGroundLoader(boolean realHardware) {
		super();
		realHW = realHardware;
		
		if(realHardware) {
			intakeDriver = new CurrentDetectedTalon(Constants.HGL_INTAKE,
								Constants.HGL_MAX_INTAKE_CURRENT_AMPS, Constants.HGL_MAX_INTAKE_CURRENT_DURATION_MS);
			intD = (CurrentDetectedTalon) intakeDriver;
			wristDriver  = new CurrentDetectedTalon(Constants.HGL_WRIST,
								Constants.HGL_MAX_WRIST_CURRENT_AMPS, Constants.HGL_MAX_WRIST_CURRENT_DURATION_MS);
			wrtD = (CurrentDetectedTalon) wristDriver;
		} else {
			intakeDriver = new FakeTalon();
			wristDriver  = new FakeTalon();
		}
		
		// Configure the wrist for closed-loop control
		wristDriver.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.PID_IDX,
				Constants.CAN_TIMEOUT_MS);
		wristDriver.setSensorPhase(true);
		wristDriver.config_kF(Constants.PID_IDX, K_F, Constants.CAN_TIMEOUT_MS);
		wristDriver.config_kP(Constants.PID_IDX, K_P, Constants.CAN_TIMEOUT_MS);
		wristDriver.config_kI(Constants.PID_IDX, K_I, Constants.CAN_TIMEOUT_MS);
		wristDriver.config_kD(Constants.PID_IDX, K_D, Constants.CAN_TIMEOUT_MS);
		// Prevent Integral Windup.
		// Whenever the control loop error is outside this zone, zero out the I term
		// accumulator.
		wristDriver.config_IntegralZone(Constants.PID_IDX, I_ZONE, Constants.CAN_TIMEOUT_MS);
		wristDriver.set(ControlMode.PercentOutput, 0);
		// Assume wrist starts up (~90 degrees)
		wristDriver.setSelectedSensorPosition(90, Constants.PID_IDX, Constants.CAN_TIMEOUT_MS);

		intakeDriver.set(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new SpeedControlHatchGroundLoader());
	}
	
	public void log() {
	
	}	
	
	public void visit () {
		if (realHW) {
			wrtD.visit();
			intD.visit();
		}
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
	 * Command the wrist to a specific angle
	 * 
	 * @param degrees
	 *            - the target in degrees from lowest possible rotation (at floor)
	 */
	public void setWristRot(double degrees) {
		wristDriver.set(ControlMode.Position, degrees * TICKS_PER_DEG);
	}

	/**
	 * Set query status of wrist
	 */
	public void setWristQuery(boolean q) {
		if (realHW) { wrtD.query = q;}
	}

	/**
	 * Get wrist detection status
	 */
	public boolean getWristDetect() {
		if (realHW) { return wrtD.getDetect(); } else { return false; }
	}	

	/**
	 * Set query status of intake
	 */
	public void setIntakeQuery(boolean q) {
		if (realHW) { intD.query = q;}
	}

	/**
	 * Get intake detection status
	 */
	public boolean getIntakeDetect() {
		if (realHW) { return intD.getDetect(); } else { return false; }
	}		
}