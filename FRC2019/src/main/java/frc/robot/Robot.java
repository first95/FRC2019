package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.CargoHandler;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.HatchGroundLoader;
import frc.robot.subsystems.HatchScorer;
import frc.robot.subsystems.VisionCoprocessor;
import frc.robot.subsystems.DriveBase.GearShiftMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends TimedRobot {

	// private Command autonomousCommand;

	// Components of the robot
	public static DriveBase drivebase;
	public static Elevator elevator;
	public static HatchScorer hScorer;
	public static HatchGroundLoader hGroundLoader;
	public static CargoHandler cargoHandler;
	public static Climber climber;
	public static Compressor compressor;
	public static OI oi;
	public static VisionCoprocessor vision;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		// Initialize all subsystems
		drivebase = new DriveBase(true);
		elevator = new Elevator(true);
		hScorer = new HatchScorer(true);
		hGroundLoader = new HatchGroundLoader(false);
		cargoHandler = new CargoHandler(true);
		climber = new Climber(true);
		compressor = new Compressor();
		vision = new VisionCoprocessor();
		oi = new OI();

		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivebase);
		SmartDashboard.putData(elevator);
		SmartDashboard.putData(hGroundLoader);
		SmartDashboard.putData(climber);

		// Disable brakes on talons to make it
		// easier to push
		drivebase.brake(false);
		elevator.brake(false);

	}

	@Override
	public void autonomousInit() {
		// No automoves currently planned, going to use vision during sandstorm
		// autonomousCommand.start();
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
		drivebase.brake(false);
		elevator.brake(false);
	}

	public void disabledPeriodic() {
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run(); // Runs all active commands
		elevator.checkAndApplyHomingSwitch();
		drivebase.pullPidConstantsFromSmartDash();
		oi.visit();
		drivebase.visit();
		hGroundLoader.visit();

		// Depending if you want all output or just limited
		// use either debugLog() or just log()
		// debugLog();
		log();
	}

	@Override
	public void teleopInit() {

		// Unlock the auto shifter
		drivebase.setShiftMode(GearShiftMode.AUTOSHIFT);

		drivebase.brake(true);
		elevator.brake(true);

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		// if (autonomousCommand != null) {
		// 	autonomousCommand.cancel();
		// }
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		debugLog();
	}

	private void debugLog() {
		// drivebase.log();
		elevator.log();
		hGroundLoader.log();
		cargoHandler.log();
		// oi.log();
	}
}
