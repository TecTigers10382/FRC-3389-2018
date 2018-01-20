/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3389.robot.commands.ExampleCommand;
import org.usfirst.frc.team3389.robot.subsystems.ExampleSubsystem;

import org.usfirst.frc.team3389.robot.subsystems.ioDevices.TimeOfFlight;

import org.usfirst.frc.team3389.robot.subsystems.Logger;


// TODO learn Command based programming - https://wpilib.screenstepslive.com/s/currentCS/m/java/c/88893

/**
 * This class is expected by the Java Virtual Machine on the roboRIO and is run automatically.
 * functions corresponding to each mode, as described in the TimedRobot class.
 * If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 * @see TimedRobot documentation
 */

public class Robot extends TimedRobot {
	
	//Initialize all subsystems
	public static final Logger robotLogger = new Logger(Logger.INFO);
	public static final TimeOfFlight timeOfFlight = new TimeOfFlight();

	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);

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
		robotLogger.log(Logger.DEBUG, this, "enter");

		Scheduler.getInstance().run();

		robotLogger.log(Logger.DEBUG, this, "exit");
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

		m_autonomousCommand = m_chooser.getSelected();
		robotLogger.log(Logger.INFO, this, "autonomous mode = " + m_chooser.getName() + "::" + m_autonomousCommand.getName());

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			robotLogger.log(Logger.INFO, this, "autonomous start");
			m_autonomousCommand.start();
		}

		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		Scheduler.getInstance().run();
		
		robotLogger.log(Logger.DEBUG, this, "exit");
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
		
		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		robotLogger.log(Logger.DEBUG, this, "enter");
		
		//Display on SmartDashboard
		SmartDashboard.putNumber("Distance", timeOfFlight.getDistance());
		Scheduler.getInstance().run();
		
		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		robotLogger.log(Logger.DEBUG, this, "enter");

		// any code goes between the 'enter' and the 'exit' log messages

		robotLogger.log(Logger.DEBUG, this, "exit");
	}
}
