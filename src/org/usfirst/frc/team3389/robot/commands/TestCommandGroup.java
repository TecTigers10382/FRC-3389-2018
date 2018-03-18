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
		// drive a rectangle of size 10 x 2
//		addSequential(new DriveTurn(.5, -90));
//		addSequential(new IntakeAuto(.5, 2));
//		addSequential(new LiftAuto(1.5));
		
		addParallel(new LiftAuto(RobotMap.LIFT_TIME*2));
		addSequential(new DriveDistance(101));
		addSequential(new IntakeAuto(-1, 2));
	}
}
