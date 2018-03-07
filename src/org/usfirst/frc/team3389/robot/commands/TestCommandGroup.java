package org.usfirst.frc.team3389.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestCommandGroup extends CommandGroup {
	// private properties here
	
	public TestCommandGroup() {
		// drive a rectangle of size 10 x 2
		addSequential(new DriveDistance(10.0)); // TODO what is the unit of measure for distance ?
		addSequential(new DriveTurn(.5, 90.0));	
		addSequential(new DriveDistance(2.0)); // TODO what is the unit of measure for distance ?
		addSequential(new DriveTurn(.5, 90.0));	
		addSequential(new DriveDistance(10.0)); // TODO what is the unit of measure for distance ?
		addSequential(new DriveTurn(.5, 90.0));	
		addSequential(new DriveDistance(2.0)); // TODO what is the unit of measure for distance ?
		addSequential(new DriveTurn(.5, 90.0));	
	}
}
