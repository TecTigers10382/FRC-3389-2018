/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
	
	Joystick driveStick;
	
	
	public Drive() {
		// Use requires() here to declare subsystem dependencies
		// requires(Robot.kExampleSubsystem);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveStick = Robot.m_oi.getLeftJoystick();
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double left = -driveStick.getRawAxis(1);
		double right = -driveStick.getRawAxis(5);
		
		Robot.driveTrain.tankDrive(left, right);
		
		if(Math.abs(left) < .1) 
			left = 0;
		if(Math.abs(right) < .1) 
			left = 0;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
