/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Tele-Op command that continuously updates the Intakes with the values from
 * the right JoyStick.
 * 
 * @author TEC Tigers
 * @see org.usfirst.frc.team3389.robot.subsystems.Intake
 * 
 */
public class IntakeStick extends Command {

	Joystick intakeStick;

	/**
	 * Constructor gains control of the Intake subsystem of the robot.
	 * 
	 * @see org.usfirst.frc.team3389.robot.subsystems.Intake
	 */
	public IntakeStick() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		requires(Robot.intake);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Grabs the joystick from the OI object of the robot before running the
	 * command.
	 * 
	 * @see org.usfirst.frc.team3389.robot.OIs
	 */
	@Override
	protected void initialize() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		intakeStick = Robot.m_oi.getRightJoystick();

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * As the command is run, updates the joystick values and controls the
	 * Intake with them.
	 * 
	 * @see org.usfirst.frc.team3389.robot.subsystems.Intake
	 */
	@Override
	protected void execute() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		double power = intakeStick.getRawAxis(1);

		Robot.intake.driveBoth(power);

		if (Math.abs(power) < .1)
			power = 0;
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Never allows IntakeStick command to finish on its own terms.
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Stops intake's motion if command is ended with isFinished
	 * 
	 * @see org.usfirst.frc.team3389.robot.commands.Intake#isFinished()
	 */
	@Override
	protected void end() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		Robot.intake.stop();
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Stops intake's motion if another command is ran that needs it.
	 */
	@Override
	protected void interrupted() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		Robot.intake.stop();
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}
}
