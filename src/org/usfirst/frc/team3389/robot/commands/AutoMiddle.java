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

	/**
	 * Autonomous that places cube in either side of switch starting with right side
	 * of robot lined up with right side of switch.
	 * 
	 * @param gameData
	 *            int that represents field layout
	 */
	public AutoMiddle(int gameData) {
		// For LL & LR: drop cube in left plate of switch
		if (gameData == 0 || gameData == 1) {
			// Drive forward slightly
			addSequential(new DriveDistance(20));
			// Turn left 90
			addSequential(new DriveTurn(.65, -90));
			// Lift half height & drive forward
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(120));
			// Turn right to face switch
			addSequential(new DriveTurn(.65, 90));
			// Drive to left switch plate
			addSequential(new DriveDistance(101 - 23));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
		}

		// For RL & RR: drop cube in right plate of switch
		else if (gameData == 2 || gameData == 3) {
			// Lift half height & drive to right switch plate
			addParallel(new LiftAuto(RobotMap.LIFT_TIME * 2));
			addSequential(new DriveDistance(101));
			// Expel cube
			addSequential(new IntakeAuto(-1, 2));
		}
	}
}
