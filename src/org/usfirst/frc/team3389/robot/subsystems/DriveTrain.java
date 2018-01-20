package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new Drive());
	}

}
