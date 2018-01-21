package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	private RobotDrive chassis = new RobotDrive(RobotMap.Drive_left, RobotMap.Drive_right);

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new Drive());
	}
	
	public void TeleopDrive(Joystick leftStick,Joystick rightStick){
		chassis.tankDrive(leftStick,rightStick);
	}
	
	public void Stop(){
		chassis.drive(0,0);
	}
	
}
