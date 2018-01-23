package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	Spark intakeLeft;
	Spark intakeRight;

	public Intake() {
		intakeLeft = new Spark(RobotMap.INTAKE_LEFT);
		intakeRight = new Spark(RobotMap.INTAKE_RIGHT);

	}

	public void driveBoth(double power) {
		intakeLeft.set(power);
		intakeRight.set(power);
	}

	public void stop() {
		driveBoth(0);
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}
}