/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoAlternativeRight extends CommandGroup {
	// private properties here
	/*
	 * This auto command is if we are in left position and in the case of LL where
	 * the other team can score the switch and we want to score the scale
	 */

	public AutoAlternativeRight(int gameData) {
		// For LL: drop cube in left plate of scale
		// Numbers need to be adjusted for opposite scale
		if (gameData == 0) {
			// Drives past switch
			addSequential(new DriveDistance(256));
			// Turns left 90
			addSequential(new DriveTurn(.65, -90));
			// Lift half height & drive slightly past left plate
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(140));
			// Lift to full height
			addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));
			// Turn right 135 to face corner of left plate
			addSequential(new DriveTurn(.65, 135));
			// Drive forward to left plate
			addSequential(new DriveDistance(20));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
			// Drive backwards
			addSequential(new DriveDistance(-30));
			// add drop lift command when finished
		}

		// For RL: drop cube in right plate of switch from side
		else if (gameData == 2) {
			// Lift half height & drive to side of switch
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(144));
			// Turn left to face switch
			addSequential(new DriveTurn(.65, -90));
			// Drive to switch
			addSequential(new DriveDistance(20));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
		}

		// For LR && RR: drop cube in right plate of scale if switch is on left side
		else if (gameData == 1 || gameData == 3) {
			// Lift half height & drive forward to side of scale
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(298));
			// Lift full height
			addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));
			// Turn left to face scale
			addSequential(new DriveTurn(.65, -90));
			// Drive to scale plate
			addSequential(new DriveDistance(22));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
			// Drive backwards
			addSequential(new DriveDistance(-30));
			// add drop lift command when finished
		}
	}
}
