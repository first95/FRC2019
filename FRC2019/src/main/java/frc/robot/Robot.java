package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
	private Joystick driverController = new Joystick(0);

	private TalonSRX leftLeader;
	private TalonSRX leftFollower1;
	private TalonSRX leftFollower2;
	private TalonSRX rightLeader;
	private TalonSRX rightFollower1;
	private TalonSRX rightFollower2;

	
	// Indices for Talons
	// Drive base
	public static final int LEFT_LEAD = 10;
	public static final int LEFT_F1 = 11;
	public static final int LEFT_F2 = 12;
	public static final int RIGHT_LEAD = 20;
	public static final int RIGHT_F1 = 21;
	public static final int RIGHT_F2 = 22;

	private PowerDistributionPanel pdp;
	private Compressor compressor;

	private TalonSRX nxTalon;
	
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		leftLeader = new TalonSRX(LEFT_LEAD);
		leftFollower1 = new TalonSRX(LEFT_F1);
		leftFollower2 = new TalonSRX(LEFT_F2);
		rightLeader = new TalonSRX(RIGHT_LEAD);
		rightFollower1 = new TalonSRX(RIGHT_F1);
		rightFollower2 = new TalonSRX(RIGHT_F2);

		leftLeader.set(ControlMode.PercentOutput, 0);
		leftFollower1.set(ControlMode.Follower, LEFT_LEAD);
		leftFollower2.set(ControlMode.Follower, LEFT_LEAD);
		rightLeader.set(ControlMode.PercentOutput, 0);
		rightFollower1.set(ControlMode.Follower, RIGHT_LEAD);
		rightFollower2.set(ControlMode.Follower, RIGHT_LEAD);

		pdp = new PowerDistributionPanel();
		pdp.clearStickyFaults();

		compressor = new Compressor();

		nxTalon = new TalonSRX(50);
	}

	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {	
	}
	

	@Override
	public void teleopInit() {
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double forward = driverController.getY();
		double spin = driverController.getRawAxis(4);

		double leftSpeed = forward - spin;
		double rightSpeed = forward + spin;

		leftSpeed = -Math.min(1, Math.max(-1, leftSpeed));
		rightSpeed = Math.min(1, Math.max(-1, rightSpeed));

		leftLeader.set(ControlMode.PercentOutput, leftSpeed);
		rightLeader.set(ControlMode.PercentOutput, rightSpeed);

		nxTalon.set(ControlMode.PercentOutput, 0);
	}

}
