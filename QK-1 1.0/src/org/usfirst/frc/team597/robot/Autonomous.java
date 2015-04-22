package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	Talon talonLeft;
	Talon talonRight;
	Talon talonStrafe;
	DoubleSolenoid Strafe;
	Value OMNI_ON = Value.kReverse;
	Value OMNI_OFF = Value.kForward;
	int autoState = 0;

	public Autonomous(Talon TL, Talon TR,Talon TS, DoubleSolenoid ST) {

	}

	public void autonoNo(int auto) {
				
		autoState = auto;
		
		if (autoState == 0) {
			talonLeft.set(0);
			talonRight.set(0);
			talonStrafe.set(0);
		}
		if (autoState == 1) {
			Strafe.set(OMNI_ON);
			Timer.delay(.5);

			talonStrafe.set(.5);
			Timer.delay(6);

			talonStrafe.set(0);
			Timer.delay(.01);

			Strafe.set(OMNI_OFF);
			
			autoState = 0;
		}
		if (autoState == 3) {
			
			Strafe.set(OMNI_OFF);
			Timer.delay(0.3);

			talonLeft.set(-0.6);
			talonRight.set(-0.6);
			Timer.delay(2.05);

			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(0.3);

			talonLeft.set(0.6);
			talonRight.set(0.6);
			Timer.delay(0.2);

			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(0.3);
			
			autoState = 0;

		}

	}

}
