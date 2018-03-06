package org.usfirst.frc.team3389.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestCommandGroup extends CommandGroup {
	public TestCommandGroup() {
		addSequential(new DriveTurn(.5,45.0));	
	}
}
