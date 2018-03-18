/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/


package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRightSwitch extends CommandGroup {
	// private properties here
	
	public AutoRightSwitch(int gameData) {
		if(gameData == 0 ) {
			// TODO add description of this autonomous maneuver
			addSequential(new DriveDistance(256));
			addSequential(new DriveTurn(.5, 90));
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(140));
			addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));
			addSequential(new DriveTurn(.5, -135));
			addSequential(new DriveDistance(20));
			addSequential(new IntakeAuto(-1, 2));
			// add drop lift command when finished
		}
		else if(gameData == 2 || gameData == 3) {
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(144));
			addSequential(new DriveTurn(.65,90));
			addSequential(new DriveDistance(20));
			addSequential(new IntakeAuto(-1,2));
		}
		else if(gameData == 1) {
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(298));
			addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));
			addSequential(new DriveTurn(.65, 90));
			addSequential(new DriveDistance(22));
			addSequential(new IntakeAuto(-1, 2));
			addSequential(new DriveDistance(-30));
		//	addSequential(new LiftAuto(RobotMap.LIFT_TIME * 4));
		//  LiftAuto needs to be refined to allow for going down
		}
	}
}
