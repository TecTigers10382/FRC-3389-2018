/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves lift to given height using PID loop
 */
public class LiftPIDAuto extends Command {

	int targetHeight;
	final double kP = 0, kI = 0, kD = 0;
	double error, integral, derivative;
	long lastTime;

	public LiftPIDAuto(int height) {
		// Use requires() here to declare subsystem dependencies
		// requires(Robot.kExampleSubsystem);
		requires(Robot.lifter);
		targetHeight = height;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override

	protected void execute() {
		long currentTime = System.currentTimeMillis();
		long timeElapsed = currentTime - lastTime;
		lastTime = currentTime;

		double current = Robot.lifter.getHeight();

		double lastError = error;
		error = targetHeight - current;

		integral += error * timeElapsed;
		derivative = (error - lastError) / timeElapsed;

		double power = kP * error + kI * integral + kD * derivative;

		Robot.lifter.driveLift(power);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(Math.abs(Robot.lifter.getHeight()-targetHeight)<.1) {
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
		end();
	}
}
