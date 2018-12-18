/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.subsystems.Lifter;
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
public class TeliOpLift extends Command {

	Joystick liftStick;
	Lifter lifter;
	
	/**
	 * Constructor gains control of the Intake subsystem of the robot.
	 * 
	 * @see org.usfirst.frc.team3389.robot.subsystems.Intake
	 */
	public TeliOpLift() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		// perform one-time setup here

		requires(Robot.lifter);
		lifter = Robot.lifter;

		liftStick = Robot.operatorControllers.getOperatorJoystick();
		
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
		// perform each-use setup here
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

		double power = liftStick.getRawAxis(5); // TODO the joystick axis should be in RobotMap

			if (Math.abs(power) < .1)
			power = -.12;
			
			if(power>0) {
				power=power/2; // used to be divided by 4
			}
			
			if(Robot.lifter.getUp()&&power<0) {
				power = -.12;
			} 
			
			if(Robot.lifter.getDown() == false &&power>0) {
				power = 0;
			}
			
			if(power<-.121) {
				power = power; // used to be divided by 1.5
			}
			
			lifter.driveLift(power);
		
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

		lifter.stop();
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Stops intake's motion if another command is ran that needs it.
	 */
	@Override
	protected void interrupted() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		end();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
}
