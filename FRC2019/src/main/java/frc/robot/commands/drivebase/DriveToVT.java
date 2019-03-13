/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands.drivebase;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.commands.vision.SetCameraMode;
import frc.robot.subsystems.VisionCoprocessor;

/**
 * Drive to the vision target that has the smallest bearing angle
 * Uses the vision co-processor and the drivebase
 */
public class DriveToVT extends Command {
	private double bearingDegrees;
	private double rangeInches;
	private LinkedList<VisionCoprocessor.VisionTargetInfo> targets;
	private final double RANGEMAXINCHES = 4;

	private double fwd;
	private final double FMIN = 0;
	private final double FMAX = 1;
	private double uncFwd;

	private double spin;
	private final double SMIN = 0;
	private final double SMAX = 1;
	private double uncSpin;

	private final double K1 = 1/36.0; // if 36" = 3' or more from target, go at full speed
	private final double K2 = 1/30.0; // if 30 degrees or more off in bearing angle, don't drive forward at all
	private final double K3 = 1/60.0; // if 60 degrees or more off in beraing angle, go at full rotational speed
	
	private boolean firstRun = true;
	private boolean shouldFinish = false;

	public DriveToVT() {
		requires(Robot.drivebase);
		requires(Robot.vision);
		this.setInterruptible(false);
	}

	// Called every time the command starts
	@Override
	public void start() {
		super.start();
		this.firstRun = true;		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(firstRun) {
			System.out.println("Starting DriveToVT");
		
			// Tell vision co-processor to put camera in vision processing mode
			Scheduler.getInstance().add(new SetCameraMode(false));
	
			// Initialize forward and spin to 0 until know otherwise
			this.fwd = 0;
			this.spin = 0;
			this.rangeInches = 1000;

			this.firstRun = false;
		}
		// Get set of targets from vision co-processor, including their bearing and range values
		this.targets = Robot.vision.getCurVisibleVisionTargets();
		// Select the target that has the smallest absolute value of bearing angle
		// For that target, update the values this.rangeInches and this.bearingDegrees
		// (These values may or may not be updated every time)
		if(this.targets.size()>0) {
			this.bearingDegrees = 90; // set to higher than realistic value
			this.rangeInches = this.RANGEMAXINCHES;
			for (VisionCoprocessor.VisionTargetInfo t : this.targets) {
				if (Math.abs(t.bearingDegrees) < Math.abs(this.bearingDegrees)) {
					this.bearingDegrees = t.bearingDegrees;
					this.rangeInches = t.rangeInches;
				}
			}
			System.out.println("Selected bearing (deg) "+this.bearingDegrees+", selected range (in) "+this.rangeInches);
	
			// Update the forward and spin arguments for arcade
			this.uncFwd = this.K1*this.rangeInches - this.K2*Math.abs(this.bearingDegrees);
			this.fwd = Math.min(Math.max(this.uncFwd,FMIN),FMAX);
	
			this.uncSpin = this.K3*this.bearingDegrees;
			this.spin = Math.min(Math.max(this.uncSpin,SMIN),SMAX);
	
			Robot.drivebase.arcade(this.fwd,this.spin);
			System.out.println("uncFwd "+this.uncFwd+", uncSpin "+this.uncSpin);
			System.out.println("Fwd "+this.fwd+", spin "+this.spin);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// If the range gets small enough, declare the command finished
		this.shouldFinish = this.rangeInches <= this.RANGEMAXINCHES;
		System.out.println("Checking finish DriveToVT: "+this.rangeInches+" vs. "+this.RANGEMAXINCHES+" and shouldFinish"+shouldFinish);
		return shouldFinish;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		System.out.println("DriveToVT was interrupted.");
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Ending DriveToVT");
		Robot.drivebase.arcade(0, 0);
	}
}
