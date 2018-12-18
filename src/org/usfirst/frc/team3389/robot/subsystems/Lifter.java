package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.TeliOpLift;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lifter extends Subsystem {
	public final TalonSRX lift;
	StickyFaults liftSFaults;
	Faults liftFaults;
	public Encoder enc;
	DigitalInput upSwitch;
	DigitalInput downSwitch;

	// The Pitch diameter is 1.45 inches
	private double encoderConversionFactor = (34.8125)/2782.0;

	public Lifter() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		lift = new TalonSRX(RobotMap.LIFT);

		lift.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		lift.setInverted(true);

		liftFaults = new Faults();
		liftSFaults = new StickyFaults();
		enc = new Encoder(4, 5, false, Encoder.EncodingType.k4X);

		upSwitch = new DigitalInput(RobotMap.UP_SWITCH_PIN);
		downSwitch = new DigitalInput(RobotMap.DOWN_SWITCH_PIN);

		Debug();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	public void driveLift(double power) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:\t" + power);

		// TODO the intake has a scalar for power. does the lift need one also? no
		// TODO test both ends stops as a safety measure

		lift.set(ControlMode.PercentOutput, power);
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	public void stop() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		driveLift(0);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * get's the lift's current height
	 * 
	 * @return lift's height in inches
	 */
	public double getHeight() {
		double height = encoderConversionFactor*enc.get();
		return height;
	}

	public boolean getUp() {
		return upSwitch.get();
	}

	public boolean getDown() {
		return downSwitch.get();
	}

	/*
	 * TODO add PID for encoder if the encoder is able to installed on the chain
	 * shaft, then the PID setup will resemble the motionMagicPidInit()
	 * implementation from DriveTrain.java
	 */

	private void Debug() {
		Robot.robotLogger.log(Logger.DEBUG, this, "TALON DEBUG\n==================================");
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Current");
		Robot.robotLogger.log(Logger.DEBUG, this, "lift : " + lift.getOutputCurrent());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "lift : " + lift.getMotorOutputVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Bus Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "lift : " + lift.getBusVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Percent");
		Robot.robotLogger.log(Logger.DEBUG, this, "lift: " + lift.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

		// Talon Faults
		Robot.robotLogger.log(Logger.DEBUG, this, lift.getFaults(liftFaults).toString());

		// Talon Stick Faults
		Robot.robotLogger.log(Logger.DEBUG, this, lift.getStickyFaults(liftSFaults).toString());

		clearStickyFaults();
	}

	private void clearStickyFaults() {
		lift.clearStickyFaults(0);
	}

	/**
	 * Initiliazes Lift's Default Command
	 */
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		setDefaultCommand(new TeliOpLift());

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	public void resetEnc() {
		enc.reset();
		// TODO Auto-generated method stub
		
	}
}
