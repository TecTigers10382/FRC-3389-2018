package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.DrivePIDOutput;
import org.usfirst.frc.team3389.robot.EncoderPIDSource;
import org.usfirst.frc.team3389.robot.GyroPIDSource;
import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the drive train of the robot. Contains the 4 Talons neccessary
 * for the operation of all the motors in order to move the robot.
 * 
 * @author Tec Tigers
 *
 */
public class DriveTrain extends Subsystem {

	public static TalonSRX leftFront;
	public static TalonSRX leftBack;
	public static TalonSRX rightFront;
	public static TalonSRX rightBack;
	StickyFaults LFsFaults = new StickyFaults();
	StickyFaults RFsFaults = new StickyFaults();
	StickyFaults LBsFaults = new StickyFaults();
	StickyFaults RBsFaults = new StickyFaults();
	Faults LFFaults = new Faults();
	Faults RFFaults = new Faults();
	Faults LBFaults = new Faults();
	Faults RBFaults = new Faults();
	Encoder leftEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder rightEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		


	/**
	 * Creates the Drive Train with 4 TalonSRX motor controllers over CAN.
	 */
	public DriveTrain() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		leftFront = new TalonSRX(RobotMap.DRIVE_LEFTFRONT);
		leftBack = new TalonSRX(RobotMap.DRIVE_LEFTBACK);
		rightFront = new TalonSRX(RobotMap.DRIVE_RIGHTFRONT);
		rightBack = new TalonSRX(RobotMap.DRIVE_RIGHTBACK);
		encoderInit();
		pidInit();
		
		leftFront.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		leftBack.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		rightFront.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		rightBack.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		
        
		// TODO for PID example @see https://github.com/Team4761/2018-Robot-Code/blob/master/src/org/robockets/robot/drivetrain/Drivetrain.java

		// TODO for PID example @see
		Debug();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		// Outputs Talon debug info

	}

	/**
	 * Initializes the DriveTrain's default command to the Drive command.
	 * 
	 * @see org.usfirst.frc.team3389.robot.commands.Drive
	 */
	@Override
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		setDefaultCommand(new Drive());

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Sets the power of the 4 TalonSRX's in percent output mode.
	 * 
	 * @param leftPower
	 *            the power from -1.0 to 1.0 that the left Talons output
	 * @param rightPower
	 *            the power from -1.0 to 1.0 that the right Talons output
	 */
	private void rawDrive(double leftPower, double rightPower) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:" + leftPower + ", " + rightPower);

		leftFront.set(ControlMode.PercentOutput, leftPower);
		leftBack.set(ControlMode.PercentOutput, leftPower);
		rightFront.set(ControlMode.PercentOutput, rightPower);
		rightBack.set(ControlMode.PercentOutput, rightPower);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit" + leftFront.getMotorOutputPercent());
		SmartDashboard.putNumber("encoder1", leftEnc.getDistance());
		SmartDashboard.putNumber("encoder2", rightEnc.getDistance());

		Robot.robotLogger.log(Logger.INFO, this, "encoderVal" + leftEnc.getDistance());
		Robot.robotLogger.log(Logger.INFO, this, "encoderVal" + rightEnc.getDistance());

	}

	/**
	 * Drives the Drive Train with 2 analog stick's y values like a tank.
	 * 
	 * @param left
	 *            value of left stick from -1.0 to 1.0
	 * @param right
	 *            value of right stick from -1.0 to 1.0
	 */
	public void tankDrive(double left, double right) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		rawDrive(left, right);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Stops all Talons.
	 */
	public void stop() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

		rawDrive(0, 0);

		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Outputs all data from Talons to robotLogger.
	 */
	private void Debug() {
		Robot.robotLogger.log(Logger.DEBUG, this, "TALON DEBUG\n==================================");
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Current");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftFront.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightFront.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftBack.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightBack.getOutputCurrent());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftFront.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightFront.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftBack.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightBack.getMotorOutputVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Bus Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftFront.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightFront.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftBack.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightBack.getBusVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Percent");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftFront.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightFront.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftBack.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightBack.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

		// Talon Faults
		Robot.robotLogger.log(Logger.DEBUG, this, leftFront.getFaults(LFFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightFront.getFaults(RFFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, leftBack.getFaults(LBFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightBack.getFaults(RBFaults).toString());
		// Talon Stick Faults
		Robot.robotLogger.log(Logger.DEBUG, this, leftFront.getStickyFaults(LFsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightFront.getStickyFaults(RFsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, leftBack.getStickyFaults(LBsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightBack.getStickyFaults(RBsFaults).toString());

		clearStickyFaults();
	}

	private void clearStickyFaults() {
		leftFront.clearStickyFaults(0);
		rightFront.clearStickyFaults(0);
		leftBack.clearStickyFaults(0);
		rightBack.clearStickyFaults(0);
	}

	public void resetEncoders() {
		leftEnc.reset();
		rightEnc.reset();
	}

	public void encoderInit() {
			leftEnc.setMaxPeriod(1);
			leftEnc.setMinRate(10);
			leftEnc.setDistancePerPulse(5);
			leftEnc.setReverseDirection(true);
			leftEnc.setSamplesToAverage(7);
			rightEnc.setMaxPeriod(1);
			rightEnc.setMinRate(10);
			rightEnc.setDistancePerPulse(5);
			rightEnc.setReverseDirection(true);
			rightEnc.setSamplesToAverage(7);
		}
	
	private void pidInit() {
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		
		leftFront.config_kD(0, 0.05, 0);
		leftFront.config_kF(0, 0.05, 0);
		leftFront.config_kI(0, 0.05, 0);
		leftFront.config_kP(0, 0.05, 0);
		
		leftBack.config_kD(0, 0.05, 0);
		leftBack.config_kF(0, 0.05, 0);
		leftBack.config_kI(0, 0.05, 0);
		leftBack.config_kP(0, 0.05, 0);
		
		rightFront.config_kD(0, 0.05, 0);
		rightFront.config_kF(0, 0.05, 0);
		rightFront.config_kI(0, 0.05, 0);
		rightFront.config_kP(0, 0.05, 0);
		
		rightBack.config_kD(0, 0.05, 0);
		rightBack.config_kF(0, 0.05, 0);
		rightBack.config_kI(0, 0.05, 0);
		rightBack.config_kP(0, 0.05, 0);		
	}
	
	public static void driveVelocity(int leftVelocity, int rightVelocity){
		
		rightFront.set(ControlMode.Velocity, rightVelocity);
		rightBack.set(ControlMode.Follower, RobotMap.DRIVE_RIGHTFRONT);
		leftFront.set(ControlMode.Velocity, leftVelocity);
		leftBack.set(ControlMode.Velocity, RobotMap.DRIVE_LEFTFRONT);
		
	}
	
	public static void drivePosition(int leftPosition, int rightPosition){
		final double convRatio = 1; //The constant which maps the distance in inches needed to ticks.
		double rightTicks; //The number of ticks the motor must travel
		double leftTicks; //The number of ticks the motor must travel
		
		rightTicks=convRatio*rightPosition;
		leftTicks=convRatio*rightPosition;
		SmartDashboard.putNumber("LeftPIDTickVal", leftTicks);
		SmartDashboard.putNumber("RightPIDTickVal", rightTicks);
		rightFront.set(ControlMode.Position, rightTicks);
		rightBack.set(ControlMode.Follower, RobotMap.DRIVE_RIGHTFRONT);
		leftFront.set(ControlMode.Position, leftTicks);
		leftBack.set(ControlMode.Follower, RobotMap.DRIVE_LEFTFRONT);
	}
}