/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/**
	 * CAN IDs of all Talon SRXs used for drive train
	 */
	public final static int
		DRIVE_LEFTMASTER = 0,
		DRIVE_LEFTSLAVE = 1,
		DRIVE_RIGHTSLAVE = 3,
		DRIVE_RIGHTMASTER = 2;
	
	public final static int
		LIFT = 4;
		
	/**
	 * PWM IDs for intake motor controllers
	 */
	public final static int
		INTAKE_LEFT = 5,
		INTAKE_RIGHT = 6;
	
	public final static int
		UP_SWITCH_PIN = 6,
		DOWN_SWITCH_PIN = 7;
	
	public final static int
		lTimeoutMs = 10,
		rTimeoutMs = 10,
		lSlotIdx   = 0,
		rSlotIdx   = 0,
		lPIDLoopIdx= 0,
		rPIDLoopIdx= 0,
		cruiseVelocity = 500,
		accel = 50000;
	
	public static double convRatio = (17148.0)/(108.0);
	
	// TODO create constants for scalars for motors used on various subsystems such as the lifter, intake, etc

	public final static int CURRENT_LIMIT = 60;
	
	public final static double LIFT_TIME = 15.5/8;
	//public static DrivePIDOutput leftDriveOutput = new DrivePIDOutput(DriveTrain.leftFront);	
	//public static DrivePIDOutput rightDriveOutput = new DrivePIDOutput(DriveTrain.rightFront);

	public static final int CLIMBER = 0;
	
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your DriveTrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
