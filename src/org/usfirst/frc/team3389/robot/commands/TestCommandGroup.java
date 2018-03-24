/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/


package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestCommandGroup extends CommandGroup {
	// private properties here
	
	public TestCommandGroup() {
		// Drives past switch
					 addSequential(new DriveDistance(210));

					// Turns left 90
					 addSequential(new DriveTurn(.65, -90));

					// Lift half height & drive slightly past left plate
					 addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
					 addSequential(new DriveDistance(190));

					// Lift to full height
					 addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));

					// Turn right 135 to face corner of left plate
					 addSequential(new DriveTurn(.65, 90));

					// Drive forward to left plate
					 addSequential(new DriveDistance(36));

					// Expel cube
					 addSequential(new IntakeAuto(-1, 2));

					// Drive backwards
					 addSequential(new DriveDistance(-30));
	}
}
