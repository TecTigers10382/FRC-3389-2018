/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
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

		double left = driveStick.getRawAxis(5); // TODO the axis used for drive should be defined in RobotMap
		double right = driveStick.getRawAxis(1); // TODO the axis used for drive should be defined in RobotMap
//CHANGE THIS BACK TO ONE
		if (Math.abs(left) < .1) // TODO the joystick 'deadzone' should be defined in RobotMap
			left = 0;
		if (Math.abs(right) < .1) // TODO the joystick 'deadzone' should be defined in RobotMap
			right = 0;
		
		if(Robot.lifter.getUp()==true) {
			left = left/2;
			right = right/2;
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
