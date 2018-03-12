package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.TeliOpClimb;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	
	VictorSP climb;
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TeliOpClimb());
	}
	
	public Climber() {
		climb = new VictorSP(RobotMap.CLIMBER);
		climb.setInverted(true);
	}

	public void lift(double speed) {
		climb.setSpeed(-speed);
	}

	public void stop() {
		// TODO Auto-generated method stub
		lift(0);
	}
}
