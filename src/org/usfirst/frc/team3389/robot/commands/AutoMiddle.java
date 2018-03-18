/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddle extends CommandGroup {
	// private properties here
	
	public AutoMiddle(int gameData) {
		if(gameData == 0 || gameData == 1) {
			// TODO add description of this autonomous maneuver
			addSequential(new DriveDistance(20));
			addSequential(new DriveTurn(.65,90));
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(120));
			addSequential(new DriveTurn(.65,-90));
			addSequential(new DriveDistance(101-23));
			addSequential(new IntakeAuto(-1,2));
//			addSequential(new DriveDistance(-10));
		}
		else if(gameData == 2 || gameData == 3) {
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(101));
			addSequential(new IntakeAuto(-1, 2));
//			addSequential(new DriveDistance(-10));
		}
	}
}
