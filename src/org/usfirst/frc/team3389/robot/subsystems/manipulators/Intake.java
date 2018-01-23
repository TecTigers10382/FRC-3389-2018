package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	Spark intakeLeft;
	Spark intakeRight;

	public Intake() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		intakeLeft = new Spark(RobotMap.INTAKE_LEFT);
		intakeRight = new Spark(RobotMap.INTAKE_RIGHT);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	public void driveBoth(double power) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:\t"+power);
		
		intakeLeft.set(-power);
		intakeRight.set(power);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	public void stop() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		driveBoth(0);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		setDefaultCommand(new org.usfirst.frc.team3389.robot.commands.Intake());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}
}