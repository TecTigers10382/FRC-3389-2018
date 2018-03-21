package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.TeliOpDrive;
import org.usfirst.frc.team3389.robot.ioDevices.MPU9250;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the drive train of the robot. Contains the 4 Talons necessary
 * for the operation of all the motors in order to move the robot.
 * 
 * @author TEC Tigers
 *
 */
public class DriveTrain extends Subsystem {
	public MPU9250 driveGyro;
	public static TalonSRX leftMaster;
	public TalonSRX leftSlave;
	public TalonSRX rightSlave;
	public static TalonSRX rightMaster;
	StickyFaults LFsFaults = new StickyFaults();
	StickyFaults RFsFaults = new StickyFaults();
	StickyFaults LBsFaults = new StickyFaults();
	StickyFaults RBsFaults = new StickyFaults();
	Faults LFFaults = new Faults();
	Faults RFFaults = new Faults();
	Faults LBFaults = new Faults();
	Faults RBFaults = new Faults();

	static double rightTicks; // The number of ticks the motor must travel
	static double leftTicks; // The number of ticks the motor must travel
	// Encoder leftEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	// Encoder rightEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

	/**
	 * Creates the Drive Train with 4 TalonSRX motor controllers over CAN.
	 */
	public DriveTrain() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		leftMaster = new TalonSRX(RobotMap.DRIVE_LEFTMASTER);
		leftSlave = new TalonSRX(RobotMap.DRIVE_LEFTSLAVE);
		rightSlave = new TalonSRX(RobotMap.DRIVE_RIGHTSLAVE);
		rightMaster = new TalonSRX(RobotMap.DRIVE_RIGHTMASTER);
		rightSlave.follow(rightMaster);
		leftSlave.follow(leftMaster);

		// encoderInit();
		motionMagicPidInit();

		// FIXME This will overwrite your motionmagic profile
		// velocityPidInit();

		driveGyro = new MPU9250(); // this will take approximately 10 seconds to initialize and calibrate
		driveGyro.startUpdatingThread();

		leftMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		rightMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);

		rightMaster.setInverted(true);
		rightSlave.setInverted(true);

		leftMaster.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 5);
		rightMaster.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 5);
		leftSlave.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 5);
		rightSlave.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 5);

		// leftMaster.configPeakCurrentDuration(5, 5);
		// rightMaster.configPeakCurrentDuration(5, 5);
		// rightSlave.configPeakCurrentDuration(5, 5);
		// leftSlave.configPeakCurrentDuration(5, 5);
		//
		// leftMaster.enableCurrentLimit(true);
		// rightMaster.enableCurrentLimit(true);
		// leftSlave.enableCurrentLimit(true);
		// rightSlave.enableCurrentLimit(true);

		Debug();
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		// Outputs Talon debug info

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
		driveVelocity(leftPower, rightPower);
		// leftMaster.set(ControlMode.Current, leftPower*35);
		// rightMaster.set(ControlMode.Current, rightPower*35);
		Robot.robotLogger.log(Logger.DEBUG, this, "exit" + leftMaster.getMotorOutputPercent());
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

	public void driveVelocity(double leftVelocity, double rightVelocity) {

		double rightVelo = rightVelocity * 4096 * 500 / 600;
		double leftVelo = leftVelocity * 4096 * 500 / 600;
		rightMaster.set(ControlMode.Velocity, rightVelo / 1.5);
		leftMaster.set(ControlMode.Velocity, leftVelo / 1.5);
	}

	/**
	 * Drives robot certian distance
	 * 
	 * @param leftPosition
	 *            left distance in inches
	 * @param rightPosition
	 *            right distance in inches
	 */
	public void drivePosition(double leftPosition, double rightPosition) {
		rightTicks = RobotMap.convRatio * rightPosition;
		leftTicks = RobotMap.convRatio * leftPosition;
		rightMaster.set(ControlMode.MotionMagic, rightTicks);
		leftMaster.set(ControlMode.MotionMagic, leftTicks);
		SmartDashboard.putNumber("TickNumber", rightTicks);
	}

	public void drivePercent(double leftPower, double rightPower) {
		leftMaster.set(ControlMode.PercentOutput, leftPower);
		rightMaster.set(ControlMode.PercentOutput, rightPower);
	}

	/**
	 * Returns position in inches from Talons
	 * 
	 * @return position in inches
	 */
	public double getPosition() {
		/*
		 * TODO find more accurate metric for position since 'position' methods assume
		 * left and right are moving to the same target value, then one possible
		 * solution is to return the lesser of absolute value of the left and right
		 * position values. This would allow the both left and right to settle on their
		 * target values.
		 */

		if (Math.abs(leftMaster.getSelectedSensorPosition(0))
				/ RobotMap.convRatio < Math.abs(rightMaster.getSelectedSensorPosition(0)) / RobotMap.convRatio) {
			return ((double) leftMaster.getSelectedSensorPosition(0) / RobotMap.convRatio);
		} else {
			return ((double) rightMaster.getSelectedSensorPosition(0) / RobotMap.convRatio);
		}
	}

	public double getLeftTicks() {
		return leftTicks;
	}

	public double getRightTicks() {
		return rightTicks;
	}

	private void clearStickyFaults() {
		leftMaster.clearStickyFaults(0);
		rightSlave.clearStickyFaults(0);
		leftSlave.clearStickyFaults(0);
		rightMaster.clearStickyFaults(0);
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
	}

	private void motionMagicPidInit() {
		// @see
		// https://github.com/Team4761/2018-Robot-Code/blob/master/src/org/robockets/robot/drivetrain/Drivetrain.java

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.lPIDLoopIdx,
				RobotMap.lTimeoutMs); /* PIDLoop=0,timeoutMs=0 */
		// leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /*
		// PIDLoop=0,timeoutMs=0 */
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.rPIDLoopIdx,
				RobotMap.rTimeoutMs); /* PIDLoop=1,timeoutMs=0 */
		// rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /*
		// PIDLoop=0,timeoutMs=0 */

		leftMaster.setSensorPhase(true);
		rightMaster.setSensorPhase(true);
		leftMaster.setInverted(false);
		rightMaster.setInverted(false);

		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.lTimeoutMs);
		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.lTimeoutMs);

		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.rTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.rTimeoutMs);

		leftMaster.configNominalOutputForward(0, RobotMap.lTimeoutMs);
		leftMaster.configNominalOutputReverse(0, RobotMap.lTimeoutMs);
		leftMaster.configPeakOutputForward(1, RobotMap.lTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, RobotMap.lTimeoutMs);

		rightMaster.configNominalOutputForward(0, RobotMap.rTimeoutMs);
		rightMaster.configNominalOutputReverse(0, RobotMap.rTimeoutMs);
		rightMaster.configPeakOutputForward(1, RobotMap.rTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, RobotMap.rTimeoutMs);

		/*
		 * TODO tune PID to solve acceleration and robot reaching final position
		 * 
		 * @see example: https://youtu.be/jI7SnhuVXg4?t=2m17s use the dashboard to view
		 * desired position vs actual position
		 */

		/*
		 * TODO watch this: https://www.youtube.com/watch?v=_bWvXn4ilrY F = basically
		 * adds a delay, smoothes the input (maybe disable from velocity?) P = how fast
		 * it goes I = how much it tries to correct D = how much it slows as it nears
		 * the target
		 */

		leftMaster.selectProfileSlot(RobotMap.lSlotIdx, RobotMap.lPIDLoopIdx);
		leftMaster.config_kF(0, 0.2, RobotMap.lTimeoutMs);
		leftMaster.config_kP(0, 0.2, RobotMap.lTimeoutMs);
		leftMaster.config_kI(0, 0, RobotMap.lTimeoutMs);
		leftMaster.config_kD(0, 0, RobotMap.lTimeoutMs);

		rightMaster.selectProfileSlot(RobotMap.rSlotIdx, RobotMap.rPIDLoopIdx);
		rightMaster.config_kF(RobotMap.rSlotIdx, 0.2, RobotMap.rTimeoutMs);
		rightMaster.config_kP(RobotMap.rSlotIdx, 0.2, RobotMap.rTimeoutMs);
		rightMaster.config_kI(RobotMap.rSlotIdx, 0, RobotMap.rTimeoutMs);
		rightMaster.config_kD(RobotMap.rSlotIdx, 0, RobotMap.rTimeoutMs);

		leftMaster.configMotionCruiseVelocity(RobotMap.cruiseVelocity, RobotMap.lTimeoutMs);
		leftMaster.configMotionAcceleration(RobotMap.accel * 2, RobotMap.lTimeoutMs);

		rightMaster.configMotionCruiseVelocity(RobotMap.cruiseVelocity, RobotMap.rTimeoutMs);
		rightMaster.configMotionAcceleration(RobotMap.accel, RobotMap.rTimeoutMs);

		leftMaster.setSelectedSensorPosition(0, RobotMap.lPIDLoopIdx, RobotMap.lTimeoutMs);
		rightMaster.setSelectedSensorPosition(0, RobotMap.rPIDLoopIdx, RobotMap.rTimeoutMs);
	}

	public void velocityPidInit() {
		// @see
		// https://github.com/Team4761/2018-Robot-Code/blob/master/src/org/robockets/robot/drivetrain/Drivetrain.java

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.lPIDLoopIdx,
				RobotMap.lTimeoutMs); /* PIDLoop=0,timeoutMs=0 */
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.rPIDLoopIdx,
				RobotMap.rTimeoutMs); /* PIDLoop=1,timeoutMs=0 */

		leftMaster.setSensorPhase(true);
		rightMaster.setSensorPhase(true);

		leftMaster.configNominalOutputForward(0, RobotMap.lTimeoutMs);
		leftMaster.configNominalOutputReverse(0, RobotMap.lTimeoutMs);
		leftMaster.configPeakOutputForward(1, RobotMap.lTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, RobotMap.lTimeoutMs);

		rightMaster.configNominalOutputForward(0, RobotMap.rTimeoutMs);
		rightMaster.configNominalOutputReverse(0, RobotMap.rTimeoutMs);
		rightMaster.configPeakOutputForward(1, RobotMap.rTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, RobotMap.rTimeoutMs);

		leftMaster.configMotionCruiseVelocity(RobotMap.cruiseVelocity, RobotMap.lTimeoutMs);
		leftMaster.configMotionAcceleration(RobotMap.accel, RobotMap.lTimeoutMs);

		rightMaster.configMotionCruiseVelocity(RobotMap.cruiseVelocity, RobotMap.rTimeoutMs);
		rightMaster.configMotionAcceleration(RobotMap.accel, RobotMap.rTimeoutMs);

		/*
		 * TODO tune PID to solve acceleration and robot accuracy
		 * 
		 * @see example: https://youtu.be/jI7SnhuVXg4?t=2m17s use the dashboard to view
		 * desired position vs actual position
		 */

		leftMaster.config_kF(0, .24, RobotMap.lTimeoutMs);
		leftMaster.config_kP(0, .2, RobotMap.lTimeoutMs);// .9 .025
		leftMaster.config_kI(0, 0, RobotMap.lTimeoutMs);
		leftMaster.config_kD(0, 0, RobotMap.lTimeoutMs);
		leftMaster.selectProfileSlot(RobotMap.lSlotIdx, RobotMap.lPIDLoopIdx);

		rightMaster.config_kF(RobotMap.rSlotIdx, .24, RobotMap.rTimeoutMs);// .24
		rightMaster.config_kP(RobotMap.rSlotIdx, .2, RobotMap.rTimeoutMs);// .2
		rightMaster.config_kI(RobotMap.rSlotIdx, 0, RobotMap.rTimeoutMs);
		rightMaster.config_kD(RobotMap.rSlotIdx, 0, RobotMap.rTimeoutMs);
		rightMaster.selectProfileSlot(RobotMap.rSlotIdx, RobotMap.rPIDLoopIdx);

	}

	/**
	 * Outputs all data from Talons to robotLogger.
	 */
	private void Debug() {
		Robot.robotLogger.log(Logger.DEBUG, this, "TALON DEBUG\n==================================");
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Current");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftMaster.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightSlave.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftSlave.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightMaster.getOutputCurrent());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftMaster.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightSlave.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftSlave.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightMaster.getMotorOutputVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Bus Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftMaster.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightSlave.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftSlave.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightMaster.getBusVoltage());

		Robot.robotLogger.log(Logger.DEBUG, this, "Output Percent");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : " + leftMaster.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: " + rightSlave.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : " + leftSlave.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : " + rightMaster.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");

		// Talon Faults
		Robot.robotLogger.log(Logger.DEBUG, this, leftMaster.getFaults(LFFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightSlave.getFaults(RFFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, leftSlave.getFaults(LBFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightMaster.getFaults(RBFaults).toString());
		// Talon Stick Faults
		Robot.robotLogger.log(Logger.DEBUG, this, leftMaster.getStickyFaults(LFsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightSlave.getStickyFaults(RFsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, leftSlave.getStickyFaults(LBsFaults).toString());
		Robot.robotLogger.log(Logger.DEBUG, this, rightMaster.getStickyFaults(RBsFaults).toString());

		clearStickyFaults();
	}

	public void lPidVal(double f, double p, double i, double d) {
		leftMaster.config_kF(RobotMap.lSlotIdx, f, RobotMap.lTimeoutMs);
		leftMaster.config_kP(RobotMap.lSlotIdx, p, RobotMap.lTimeoutMs);
		leftMaster.config_kI(RobotMap.lSlotIdx, i, RobotMap.lTimeoutMs);
		leftMaster.config_kD(RobotMap.lSlotIdx, d, RobotMap.lTimeoutMs);
	}

	public void rPidVal(double f, double p, double i, double d) {
		rightMaster.config_kF(RobotMap.rSlotIdx, f, RobotMap.rTimeoutMs);
		rightMaster.config_kP(RobotMap.rSlotIdx, p, RobotMap.rTimeoutMs);
		rightMaster.config_kI(RobotMap.rSlotIdx, i, RobotMap.rTimeoutMs);
		rightMaster.config_kD(RobotMap.rSlotIdx, d, RobotMap.rTimeoutMs);
	}

	/**
	 * Initializes the DriveTrain's default command to the Drive command. The
	 * default for this subsystem is the associated teliop command.
	 * 
	 * @see org.usfirst.frc.team3389.robot.commands.Drive
	 */
	@Override
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		setDefaultCommand(new TeliOpDrive());
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
}
