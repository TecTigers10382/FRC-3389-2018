package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.IntakeStick;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
	TalonSRX intakeLeft;
	TalonSRX intakeRight;

	/**
	 * Constructor. Initializes Sparks.
	 */
	public Intake() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		intakeLeft = new TalonSRX(RobotMap.INTAKE_LEFT);
		intakeRight = new TalonSRX(RobotMap.INTAKE_RIGHT);

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

		intakeLeft.set(ControlMode.PercentOutput,power);
		intakeRight.set(ControlMode.PercentOutput,power);
		

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