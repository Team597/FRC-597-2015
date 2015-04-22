package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;

public class StrafeComp implements PIDOutput {

	Talon talonLeft;
	Talon talonRight;
	
	public StrafeComp(Talon TL, Talon TR){
		talonLeft = TL;
		talonRight = TR;
	}
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
		talonLeft.set(output * -1);
		talonRight.set(output );
		}

	
}