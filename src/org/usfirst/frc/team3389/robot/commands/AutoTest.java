/*------org.usfirst.frc.team3389.robot.commands--------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command. You can replace me with your own command.
 */
public class AutoTest extends Command {
	public AutoTest() {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.kExampleSubsystem);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		DriveTrain.drivePosition(10000,10000);
		//DriveTrain.turnDrive(.5, 40);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	
//	double left = DriveTrain.getLeftTicks();
//	double right = DriveTrain.getRightTicks();
//	SmartDashboard.putNumber("LeftPIDTickVal",left);
//	SmartDashboard.putNumber("RightPIDTickVal", right);
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
