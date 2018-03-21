/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command. You can replace me with your own command.
 */
public class LiftAuto extends Command {
	double lift_speed;
	double half_speed;
	double lift_duration = 0.0;
	long endTimer;
	long slowTimer;
	
	
	/**
	 * Constructor for a controlling the lifter in auto
	 * @param speed
	 *            the motor speed for the lift motor
	 * @param duration
	 *            the length of time that the lift will run
	 */
	public LiftAuto(double duration) {
		// Use requires() here to declare subsystem dependencies
		// requires(Robot.kExampleSubsystem);
		requires(Robot.lifter);
		lift_speed= -0.5;
		half_speed = -0.25;
		lift_duration = duration;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		endTimer = System.nanoTime() + (long)(lift_duration * 1000000000.0);
		slowTimer = System.nanoTime() + (long)(lift_duration * 1000000000.0 * .8);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	
	protected void execute() {
		if(System.nanoTime() >= slowTimer) {
			Robot.lifter.driveLift(half_speed);
		}
		else {
			Robot.lifter.driveLift(lift_speed);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		
		if(System.nanoTime() >= endTimer ) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.lifter.driveLift(-.12);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
