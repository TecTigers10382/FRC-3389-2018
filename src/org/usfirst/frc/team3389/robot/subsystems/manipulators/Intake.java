package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.IntakeStick;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intake subsystem of robot. Intakes cubes using two rollers on springy things.
 * Default command is IntakeStick
 * 
 * @author Tec Tigers
 * @see org.usfirst.frc.team3389.robot.commands.IntakeStick
 */
public class Intake extends Subsystem {
	Spark intakeLeft1;
	Spark intakeLeft2;
	Spark intakeRight1;
	Spark intakeRight2;

	/**
	 * Constructor. Initializes Sparks.
	 */
	public Intake() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		intakeLeft1 = new Spark(RobotMap.INTAKE_LEFT1);
		intakeLeft2 = new Spark(RobotMap.INTAKE_LEFT2);
		intakeRight1 = new Spark(RobotMap.INTAKE_RIGHT1);
		intakeRight2 = new Spark(RobotMap.INTAKE_RIGHT2);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Intakes cubes by spinning motors at power
	 * 
	 * @param power
	 *            power of motor from -1.0 to 1.0
	 */
	public void driveBoth(double power) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:\t" + power);

		intakeLeft1.set(-power);
		intakeLeft2.set(-power);
		intakeRight1.set(power);
		intakeRight2.set(power);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Stops all motors on intake.
	 */
	public void stop() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		driveBoth(0);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}

	/**
	 * Intializes default command of IntakeStick.
	 */
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		setDefaultCommand(new IntakeStick());

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

	}
}