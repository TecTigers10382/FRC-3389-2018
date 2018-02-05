package org.usfirst.frc.team3389.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
/**
 * 
 * @author Team4761
 *
 */
public class GyroPIDSource implements PIDSource {
	@Override
    public void setPIDSourceType(PIDSourceType pidSourceType) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
       //return gyro.getAngle();
    	return 0;
    }
}