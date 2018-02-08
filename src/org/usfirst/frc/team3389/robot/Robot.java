/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot;

import org.usfirst.frc.team3389.robot.commands.ExampleCommand;
import org.usfirst.frc.team3389.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3389.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.MPU9250;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.OLEDBitmap;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.OLEDDisplay;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.QuadEncoder;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.TimeOfFlight;
import org.usfirst.frc.team3389.robot.subsystems.manipulators.Intake;
import org.usfirst.frc.team3389.robot.subsystems.manipulators.Lifter;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
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
	public static final Logger robotLogger = new Logger(Logger.DEBUG);

	public static final TimeOfFlight timeOfFlight = new TimeOfFlight();
	public static final OLEDDisplay robotScreen = new OLEDDisplay();
	public static final MPU9250 driveGyro = new MPU9250(); // this includes calibration which takes 8-12 seconds to complete
	
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final Intake intake = new Intake();
	public static final Lifter lifter= new Lifter();
	
		
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

		if (!robotScreen.init())
			robotLogger.log(Logger.ERROR, this, "failted to initialize OLED display");
		else {
			robotScreen.drawBitmap(OLEDBitmap.LOGO.getData(), OLEDBitmap.LOGO.getWidth(), OLEDBitmap.LOGO.getHeight(), 0, 0);
			robotScreen.refresh();
		}

		m_oi = new OI();
		driveGyro.startUpdatingThread();
		
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
		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "enter");

		Scheduler.getInstance().run();

		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "exit");
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
		robotLogger.log(Logger.INFO, this,
				"autonomous mode = " + m_chooser.getName() + "::" + m_autonomousCommand.getName());

		/*
		 * I would recommend instead of changing the auto command here, have the command
		 * have the conditionals We will most likely need a Left, Center, and Right auto
		 * depending on robot placement Having it be decided here will limit you to only
		 * one auto per situation.
		 */
		// This pulls the FMS game data
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.charAt(0) == 'L') {
			if (gameData.charAt(1) == 'L') {
				// Put LL auto here
			} else {
				// Put LR auto here
			}
		} else {
			if (gameData.charAt(1) == 'L') {
				// Put RL auto here
			} else {
				// Put RR auto here
			}
		}

		robotLogger.log(Logger.INFO, this, "The field configuration is " + gameData);
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
		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "enter");

		Scheduler.getInstance().run();

		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "exit");
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

		robotScreen.clear();
		robotScreen.refresh();
		
		robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "enter");
		robotScreen.drawTextStringCentered("Hello World!", 0);
		double angle = driveGyro.getFilteredYaw();
		// it's probably confusing to mix pixel positioning and character/line positioning
		//robotScreen.drawTextLine(String.format("Heading: %+5.1f", angle), 5);
		SmartDashboard.putNumber("Heading: ", angle);
		//robotScreen.refresh();
		// Display on SmartDashboard
		//SmartDashboard.putNumber("Distance", timeOfFlight.getDistanceMillimeters());

		Scheduler.getInstance().run();

		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "enter");
		// any code goes between the 'enter' and the 'exit' log messages

		// quick test of drive train
		// driveTrain.tankDrive(0.5, 0.5);

		// quick test of time-of-flight sensor
		// System.out.println("range distance = " + timeOfFlight.getDistanceMillimeters() + "mm");
		// robotScreen.drawTextLine("test message", 0);
		// robotScreen.drawTextLine("range = " + timeOfFlight.getDistanceMillimeters() + "mm", 1);
		// robotScreen.refresh();

		// given this is called in a loop its too noisy to be of use for debugging // robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
}
