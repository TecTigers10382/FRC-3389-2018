/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/


package org.usfirst.frc.team3389.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddle extends CommandGroup {
	// private properties here
	
	public AutoMiddle(int gameData) {
		if(gameData == 0 || gameData == 1) {
			// TODO add description of this autonomous maneuver
			addSequential(new DriveDistance(46.5));
			addSequential(new DriveTurn(.5, -45));
			addParallel(new LiftAuto(3));
			addSequential(new DriveDistance(85.4));
			addSequential(new DriveTurn(.5, 45));
			addSequential(new DriveDistance(19.9));
			addSequential(new IntakeAuto(-1, 2)); // TODO this could move outside if/then
		}
		else if(gameData == 2 || gameData == 3) {
			// TODO add description of this autonomous maneuver
			addSequential(new DriveDistance(46.5));
			addSequential(new DriveTurn(.5, 45));
			addParallel(new LiftAuto(3));
			addSequential(new DriveDistance(67.3));
			addSequential(new DriveTurn(.5, -45));
			addSequential(new DriveDistance(39));
			addSequential(new IntakeAuto(-1, 2)); // TODO this could move outside if/then
		}
	}
}
