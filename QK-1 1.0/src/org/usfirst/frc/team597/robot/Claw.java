package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;

public class Claw {
	
	DoubleSolenoid claw; 
	
	boolean toggleButton = false;
	boolean toggleClaw = false;
	
	final Value CLAW_CLOSE = Value.kForward;
	final Value CLAW_OPEN = Value.kReverse;
	
	Joystick jsGamepad;
	
	public Claw (Joystick js){
	claw = new DoubleSolenoid(1, 6);
	
	jsGamepad = js;
	}

	public void teleopPeriodic (){
		
		if (toggleButton != jsGamepad.getRawButton(5)
				&& jsGamepad.getRawButton(5) == true) {
			toggleClaw = !toggleClaw;
		}

		if (toggleClaw == false) {
			claw.set(CLAW_CLOSE);
		}
		if (toggleClaw == true) {
			claw.set(CLAW_OPEN);
		}
		toggleButton = jsGamepad.getRawButton(5);
	}
	
	public void Open(){
		claw.set(CLAW_OPEN);
	}
	
	public void Close(){
		claw.set(CLAW_CLOSE);
	}
	
}
