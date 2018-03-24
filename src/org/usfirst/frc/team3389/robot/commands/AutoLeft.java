/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLeft extends CommandGroup {
	// 19.8" for switch
	// 41.1" for scale
	/**
	 * Autonomous for left side placement that prioritizes placing in the switch
	 * over the scale
	 * 
	 * @param gameData
	 *            int that represents field layout
	 */
	public AutoLeft(int gameData) {

		// gameDataInt values
		// 0 = LL*
		// 1 = LR*
		// 2 = RL*
		// 3 = RR*

		// For LL & LR: drop cube in left plate of switch from side
		if (gameData == 0 || gameData == 1) {
			// Lift half height & drive to side of switch
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(144));
			// Turn right to face switch
			addSequential(new DriveTurn(.65, 90));
			// Drive to switch
			addSequential(new DriveDistance(20));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
		}

		// For RR: drive past auto line
		else if (gameData == 3) {
			// Drive past auto line
			addSequential(new DriveDistance(101));

			// TODO Test code to drop cube in right plate of scale, not tested:

			// Drives past switch
			// addSequential(new DriveDistance(256));

			// Turns right 90
			// addSequential(new DriveTurn(.65, 90));

			// Lift half height & drive slightly past right plate
			// addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			// addSequential(new DriveDistance(140));

			// Lift to full height
			// addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));

			// Turn left 135 to face corner of right plate
			// addSequential(new DriveTurn(.65, -135));

			// Drive forward to right plate
			// addSequential(new DriveDistance(20));

			// Expel cube
			// addSequential(new IntakeAuto(-1, 2));

			// Drive backwards
			// addSequential(new DriveDistance(-30));

			// TODO add drop lift command when finished
		}

		// For RL: drop cube in left plate of scale if switch is on right side
		else if (gameData == 2) {
			// Lift half height & drive forward to side of scale
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(298+6));
			// Lift full height
			addSequential(new LiftAuto(RobotMap.LIFT_TIME * 2.25));
			// Turn right to face scale
			addSequential(new DriveTurn(.65, 90));
			// Drive to scale plate
			addSequential(new DriveDistance(22));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
			// Drive backwards
			addSequential(new DriveDistance(-30));
			// add lower lift command when finished
		}
	}
};