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
public class LiftEncoderAuto extends Command {
	int lift_height;
	double current;
	
	
	public LiftEncoderAuto(int height) {
		// Use requires() here to declare subsystem dependencies
		// requires(Robot.kExampleSubsystem);
		requires(Robot.lifter);
		lift_height= height;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	
	protected void execute() {
		current = Robot.lifter.getHeight();
		Robot.lifter.gotoHeight(lift_height);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(current >= lift_height) {
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
