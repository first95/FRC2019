package org.usfirst.frc.team95.robot.commands;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.followers.*;
import org.usfirst.frc.team95.robot.subsystems.DriveBase;
import org.usfirst.frc.team95.robot.Robot;

public class Pathfindercommand
{
	public int temp1 = (int) Robot.drivebase.getLeftEncoderPos();
	public int temp2 = Robot.drivebase.getLeftEncoderTicks();
	public double temp3 = Robot.drivebase.getWheelDiameter();
	
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
	Waypoint[] points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	};
	
	Trajectory trajectory = Pathfinder.generate(points, config);
	double varr = trajectory.segments[0].velocity;
	
	TankModifier modifier = new TankModifier(trajectory).modify(0.5);
	
	Trajectory leftT = modifier.getLeftTrajectory();
	Trajectory rightT = modifier.getRightTrajectory();
	
	EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
	EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());
	
	left.configureEncoder(temp1, temp2, temp3);
	left.encoder_offset;
//	right.configureEncoder(getRightEncoderPos, getRightEncoderTicks, getWheelDiameter);
	
//	left.configurePIDVA()
}
