/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot;

import org.usfirst.frc.team3389.robot.commands.AutoAlternativeLeft;
import org.usfirst.frc.team3389.robot.commands.AutoLeft;
import org.usfirst.frc.team3389.robot.commands.AutoLine;
import org.usfirst.frc.team3389.robot.commands.AutoMiddle;
import org.usfirst.frc.team3389.robot.commands.AutoAlternativeRight;
import org.usfirst.frc.team3389.robot.commands.AutoRight;
import org.usfirst.frc.team3389.robot.commands.TestCommandGroup;
import org.usfirst.frc.team3389.robot.ioDevices.OLEDDisplay;
import org.usfirst.frc.team3389.robot.ioDevices.TimeOfFlight;
import org.usfirst.frc.team3389.robot.subsystems.Climber;
import org.usfirst.frc.team3389.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3389.robot.subsystems.Intake;
import org.usfirst.frc.team3389.robot.subsystems.Lifter;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is expected by the Java Virtual Machine on the roboRIO and is run
 * automatically. functions corresponding to each mode, as described in the
 * TimedRobot class. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 * 
 * @see TimedRobot documentation
 */

public class Robot extends TimedRobot {

	// Initialize all subsystems
	public static final Logger robotLogger = new Logger(Logger.NONE);

	// TODO Delete These
	static double leftMax = 0;
	static double rightMax = 0;
	// Don't Delete this tho
	public static String gameData;
	public static int gameDataInt;
	public static final TimeOfFlight timeOfFlight = new TimeOfFlight();
	public static final OLEDDisplay robotScreen = new OLEDDisplay();

	// this includes calibration which takes 8-12 seconds
	public static final DriveTrain driveTrain = new DriveTrain();

	public static final Intake intake = new Intake();
	public static final Lifter lifter = new Lifter();
	public static final Climber climber = new Climber();

	public static DriverStation driverStation;
	// public static final ExampleSubsystem kExampleSubsystem = new
	// ExampleSubsystem();
	public static OperatorInterface operatorControllers;

	Command m_autonomousCommand;
	SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		operatorControllers = new OperatorInterface();

		// m_chooser.addDefault("Default Auto", new ExampleCommand());

		// these strings are referenced below in autonomousInit()
		m_chooser.addDefault("Line", "Line");
		m_chooser.addObject("Left", "Left");
		m_chooser.addObject("Left Switch", "LeftSwitch");
		m_chooser.addObject("Middle", "Middle");
		m_chooser.addObject("Right", "Right");
		m_chooser.addObject("Right Switch","RightSwitch");
		m_chooser.addObject("Test Command Group", "TestCommand");

		SmartDashboard.putData("Auto mode", m_chooser);

		driverStation = DriverStation.getInstance();

		CameraServer.getInstance().startAutomaticCapture();
		
		robotScreen.drawTextStringCentered("Hello World!", 0);

		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		// any code goes between the 'enter' and the 'exit' log messages

		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	@Override
	public void disabledPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "enter");

		Scheduler.getInstance().run();

		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		// TODO log the selected command using (String)m_chooser.getSelected()

		// m_autonomousCommand = m_chooser.getSelected();
		// robotLogger.log(Logger.INFO, this,
		// "autonomous mode = " + m_chooser.getName() + "::" +
		// m_autonomousCommand.getName());

		driveTrain.leftMaster.setSelectedSensorPosition(0, 0, 0);
		driveTrain.rightMaster.setSelectedSensorPosition(0, 0, 0);

		/*
		 * gameDataInt values
		 * 	0 = LL*
		 * 	1 = LR*
		 * 	2 = RL*
		 * 	3 = RR*
		 */

		// This pulls the FMS game data
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (!gameData.isEmpty()) {
			robotLogger.log(Logger.INFO, this, "The field configuration is " + gameData);

			// convert the gameData character string to int for use in the auto
			// commandGroups
			if (gameData.charAt(0) == 'L') {
				if (gameData.charAt(1) == 'L') {
					gameDataInt = 0;
				} else {
					gameDataInt = 1;
				}
			} else {
				if (gameData.charAt(1) == 'L') {
					gameDataInt = 2;
				} else {
					gameDataInt = 3;
				}
			}
		} else {
			// we failed to get the FMS message
			robotLogger.log(Logger.WARNING, this, "The field configuration was not received");
		}

		String value = (String) m_chooser.getSelected();
		Command cmd = null;
		if (value == null) {
			// cmd=new Nothing();
		} else if (value.equals("Left")) {
			cmd = new AutoAlternativeLeft(gameDataInt);
		} else if (value.equals("LeftSwitch")) {
			cmd = new AutoLeft(gameDataInt);
		} else if (value.equals("Middle")) {
			cmd = new AutoMiddle(gameDataInt);
		} else if (value.equals("Right")) {
			cmd = new AutoAlternativeRight(gameDataInt);
		} else if (value.equals("RightSwitch")) {
			cmd = new AutoRight(gameDataInt);
		} else if (value.equals("TestCommand")) {
			cmd = new TestCommandGroup();
		} else if (value.equals("Line")) {
			cmd = new AutoLine(gameDataInt);
		}

		Robot.driveTrain.driveGyro.resetValues();

		// TODO document why we sleep for 1 sec
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (cmd != null) {
			robotLogger.log(Logger.INFO, this, "autonomous start");
			cmd.start();
		}

		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "enter");
		robotLogger.log(Logger.DEBUG, this, "auto Periodic enter");
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("LeftPosition", driveTrain.leftMaster.getSelectedSensorPosition(0)); // TODO should the
																										// dashboard
																										// show encoder
																										// distance or
																										// physical
																										// distance
		SmartDashboard.putNumber("RightPosition", driveTrain.rightMaster.getSelectedSensorPosition(0)); // TODO should
																										// the dashboard
																										// show encoder
																										// distance or
																										// physical
																										// distance
		SmartDashboard.putNumber("gyro", driveTrain.driveGyro.getFilteredYaw());
		SmartDashboard.putNumber("PosValues", driveTrain.getPosition());
		SmartDashboard.putNumber("Lift Height", lifter.getHeight());
		robotLogger.log(Logger.DEBUG, this, "auto Periodic exit");
	}

	@Override
	public void teleopInit() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			robotLogger.log(Logger.INFO, this, "teleop cancels autonomous");
			m_autonomousCommand.cancel();
		}
		// driveTrain.resetEncoders();
		leftMax = 0;
		rightMax = 0;
		robotScreen.clear();
		robotScreen.refresh();
		driveTrain.velocityPidInit();

		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during operator control.
	 * 
	 */
	@Override
	public void teleopPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "enter");

		// robotScreen.drawTextStringCentered("Hello World!", 0);

		// it's probably confusing to mix pixel positioning and character/line
		// positioning
		 robotScreen.drawTextLine(String.format("Heading: %+5.1f", driveTrain.driveGyro.getFilteredYaw()), 5);
		// SmartDashboard.putNumber("Heading: ", angle);

		// SmartDashboard.putBoolean("Up switch", lifter.getUp());
		// SmartDashboard.putNumber("LiftPower", lifter.lift.getMotorOutputPercent());
		// SmartDashboard.putBoolean("Down Switch", lifter.getDown());
		// SmartDashboard.putNumber("LeftPercent",
		// driveTrain.leftMaster.getMotorOutputPercent());
		// SmartDashboard.putNumber("RightPercent",
		// driveTrain.rightMaster.getMotorOutputPercent());
		// SmartDashboard.putNumber("LeftSlavePercent",
		// driveTrain.leftSlave.getMotorOutputPercent());
		// SmartDashboard.putNumber("RightSlavePercent",
		// driveTrain.rightSlave.getMotorOutputPercent());
		// double lf=0,lp=0,li=0,ld=0,rf=0,rp=0,ri=0,rd=0;
		// lf=SmartDashboard.getNumber("leftF", 1);
		// lp=SmartDashboard.getNumber("leftP", 0);
		// li=SmartDashboard.getNumber("leftI", 0);
		// ld=SmartDashboard.getNumber("leftD", 0);
		// rf=SmartDashboard.getNumber("rightF", 0);
		// rp=SmartDashboard.getNumber("rightP", 0);
		// ri=SmartDashboard.getNumber("rightI", 0);
		// rd=SmartDashboard.getNumber("rightD", 0);
		// driveTrain.lPidVal(lf, lp, li, ld);
		// SmartDashboard.putString("PID", lf+" "+lp+" "+li);
		// driveTrain.rPidVal(rf, rp, ri, rd);
		//

		// if
		// (DriveTrain.leftMaster.getSelectedSensorVelocity(RobotMap.lPIDLoopIdx)>=leftMax)
		// {
		// leftMax=DriveTrain.leftMaster.getSelectedSensorVelocity(RobotMap.lPIDLoopIdx);
		// }
		// if
		// (DriveTrain.rightMaster.getSelectedSensorVelocity(RobotMap.rPIDLoopIdx)>=rightMax)
		// {
		// rightMax=DriveTrain.rightMaster.getSelectedSensorVelocity(RobotMap.rPIDLoopIdx);
		// }
		// SmartDashboard.putNumber("Left Speed",
		// DriveTrain.leftMaster.getSelectedSensorVelocity(RobotMap.lPIDLoopIdx));
		// SmartDashboard.putNumber("Right
		// Speed",DriveTrain.rightMaster.getSelectedSensorVelocity(RobotMap.rPIDLoopIdx));
		// SmartDashboard.putNumber("Left Max Speed", leftMax);
		// SmartDashboard.putNumber("Right Max Speed",rightMax);
		// SmartDashboard.putNumber("OutputVoltage LeftMaster",
		// DriveTrain.leftMaster.getMotorOutputVoltage());
		// SmartDashboard.putNumber("OutputVoltage RightMaster",
		// DriveTrain.rightMaster.getMotorOutputVoltage());
		// SmartDashboard.putNumber("OutputVoltage LeftSlave",
		// DriveTrain.leftSlave.getMotorOutputVoltage());
		// SmartDashboard.putNumber("OutputVoltage RightSlave",
		// DriveTrain.rightSlave.getMotorOutputVoltage());
		SmartDashboard.putNumber("OutputCurrent LeftMaster",
		driveTrain.leftMaster.getOutputCurrent());
		SmartDashboard.putNumber("OutputCurrent RightMaster",
		driveTrain.rightMaster.getOutputCurrent());
		SmartDashboard.putNumber("OutputCurrent LeftSlave",
		driveTrain.leftSlave.getOutputCurrent());
		SmartDashboard.putNumber("OutputCurrent RightSlave",
		driveTrain.rightSlave.getOutputCurrent());
		SmartDashboard.putNumber("gyro", driveTrain.driveGyro.getFilteredYaw());
		
		// robotScreen.updateTextLine(String.format("Heading: %06.2f", driveTrain.driveGyro.getFilteredYaw()), 6);

		SmartDashboard.putNumber("LeftPosition", driveTrain.leftMaster.getSelectedSensorPosition(0)); // TODO should the
																										// dashboard
																										// show encoder
																										// distance or
																										// physical
																										// distance
		SmartDashboard.putNumber("RightPosition", driveTrain.rightMaster.getSelectedSensorPosition(0)); // TODO should
																										// the dashboard
																										// show encoder
																										// distance or
																										// physical
																										// distance

		SmartDashboard.putBoolean("Down Switch", lifter.getDown());
		SmartDashboard.putBoolean("Up Switch", lifter.getUp());
		SmartDashboard.putNumber("Lift Height", lifter.getHeight());
		
		// robotScreen.refresh();

		// Display on SmartDashboard
		// SmartDashboard.putNumber("Distance", timeOfFlight.getDistanceMillimeters());

		// robotScreen.refresh();

		Scheduler.getInstance().run();

		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "enter");
		// any code goes between the 'enter' and the 'exit' log messages

		// quick test of drive train
		// driveTrain.tankDrive(0.5, 0.5);
		// quick test of time-of-flight sensor
		// System.out.println("range distance = " +
		// timeOfFlight.getDistanceMillimeters() + "mm");
		// robotScreen.drawTextLine("test message", 0);
		// robotScreen.drawTextLine("range = " + timeOfFlight.getDistanceMillimeters() +
		// "mm", 1);
		// robotScreen.refresh();

		// given this is called in a loop its too noisy to be of use for debugging //
		// robotLogger.log(Logger.DEBUG, this, "exit");
	}

}
