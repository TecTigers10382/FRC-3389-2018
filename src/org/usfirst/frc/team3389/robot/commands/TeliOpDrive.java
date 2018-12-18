/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Tele-Op command that continuously updates the DriveTrain with the values from
 * the left JoyStick.
 * 
 * @author TEC Tigers
 * @see org.usfirst.frc.team3389.robot.subsystems.DriveTrain
 * 
 */
public class TeliOpDrive extends Command {

	Joystick driveStick;
	DriveTrain drive;

	final double constant = 5;
	final double power = Math.log(constant * 2) / Math.log(constant);

	/**
	 * Constructor gains control of the DriveTrain subsystem of the robot.
	 * 
	 * @see org.usfirst.frc.team3389.robot.subsystems.DriveTrain
	 */
	public TeliOpDrive() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		// perform one-time setup here

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		drive = Robot.driveTrain;

		driveStick = Robot.operatorControllers.getDriverJoystick();

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * initialize is called each time the command is going to be used
	 * 
	 * @see org.usfirst.frc.team3389.robot.OIs
	 */
	@Override
	protected void initialize() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		// perform each-use setup here
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * As the command is run, updates the joystick values and controls the
	 * DriveTrain with them.
	 * 
	 * @see org.usfirst.frc.team3389.robot.subsystems.DriveTrain
	 */
	@Override
	protected void execute() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

	
		double left = -driveStick.getRawAxis(RobotMap.LEFT_DRIVE_STICK);
		double right = -driveStick.getRawAxis(RobotMap.RIGHT_DRIVE_STICK);

		if (Math.abs(left) < RobotMap.DEADZONE)
			left = 0;
		if (Math.abs(right) < RobotMap.DEADZONE)
			right = 0;

		if (Robot.lifter.getUp() == true) {
			left = left / 2;
			right = right / 2;
		}
		
		
		//turbo button forward
		if(driveStick.getRawButton(4)) {
			left = 1;
			right = 1;
		}
		//turbo button backwards
		if(driveStick.getRawButton(1)) {
			left = -1;
			right = -1;
		}
			
		// Logarithmic Drive
		
		if (left > .1) {
			left = Math.pow((((left - .1) * (1.0 / 0.9)) * constant), power) / (constant * 2);
		} else if (left < -.1) {
			left = -Math.pow((((Math.abs(left) - .1) * (1.0 / 0.9)) * constant), power) / (constant * 2);
		}

		if (right > .1) {
			right = Math.pow((((right - .1) * (1.0 / 0.9)) * constant), power) / (constant * 2);
		} else if (right < -.1) {
			right = -Math.pow((((Math.abs(right) - .1) * (1.0 / 0.9)) * constant), power) / (constant * 2);
		}
		
		

		// Slows down drive as lift goes up
		
		final double slow = .2; //used to be .5

		if (Robot.lifter.getHeight() / RobotMap.MAX_HEIGHT > 0
				&& Robot.lifter.getHeight() / RobotMap.MAX_HEIGHT <= 1.1) {
			left = left - (left * ((Robot.lifter.getHeight() / RobotMap.MAX_HEIGHT) * (1 - slow)));
			right = right - (right * ((Robot.lifter.getHeight() / RobotMap.MAX_HEIGHT) * (1 - slow)));
		}
	
		drive.tankDrive(-left, -right);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Never allows Drive command to finish on its own terms.
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Stops drivetrain's motion if command is ended with isFinished
	 * 
	 * @see org.usfirst.frc.team3389.robot.commands.Drive#isFinished()
	 */
	@Override
	protected void end() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		drive.stop();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Stops drivetrain's motion if another command is ran that needs it.
	 */
	@Override
	protected void interrupted() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		end();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
}
