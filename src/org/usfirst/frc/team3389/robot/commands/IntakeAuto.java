/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * An example command. You can replace me with your own command.
 */
public class IntakeAuto extends Command {
	double motor_speed = 0.0;
	double intake_duration = 0.0;
	long endTimer;
	
	
	/**
	 * Constructor for a controlling the intake in auto
	 * @param speed
	 *            the motor speed for both intake motors
	 *            positive is intake
	 *            negative is expel
	 * @param duration
	 *            the length of time the intake runs
	 */
	public IntakeAuto(double speed, double duration) {
		// Use requires() here to declare subsystem dependencies
		// requires(Robot.kExampleSubsystem);
		requires(Robot.intake);
		motor_speed = speed;
		intake_duration = duration;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		endTimer = System.nanoTime() + (long)(intake_duration * 1000000000.0);
	// Called repeatedly when this Command is scheduled to run
	}
	@Override
	
	protected void execute() {
		Robot.intake.driveBoth(motor_speed);
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
		Robot.intake.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
