package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;

public class Claw {

	DoubleSolenoid claw;
	Joystick jsGamepad;
	DoubleSolenoid brake;
	Talon talonElev;
	PIDController elev;
	
	boolean toggleButton = false;
	boolean toggleClaw = false;
	
	final Value BRAKE_ON = Value.kReverse;
	final Value BRAKE_OFF = Value.kForward;
	final Value CLAW_CLOSE = Value.kForward;
	final Value CLAW_OPEN = Value.kReverse;

	public Claw(Joystick js, DoubleSolenoid b, Talon te, PIDController e) {
		claw = new DoubleSolenoid(1, 6);
		jsGamepad = js;
		brake = b;
		talonElev = te;
		elev = e;
	}

	public void teleopPeriodic() {
		// Toggle Button
		if (toggleButton != jsGamepad.getRawButton(5)
				&& jsGamepad.getRawButton(5) == true) {
			toggleClaw = !toggleClaw;
		}
		toggleButton = jsGamepad.getRawButton(5);

		if (toggleClaw == false) {
			claw.set(CLAW_CLOSE);
		}
		if (toggleClaw == true) {
			claw.set(CLAW_OPEN);
		}

		/*
		 * Manee's version of toggle button if (toggleButton !=
		 * jsGamepad.getRawButton(5)) { toggleClaw = !toggleClaw; } if
		 * (toggleClaw == false) { claw.set(CLAW_CLOSE); } if (toggleClaw ==
		 * true) { claw.set(CLAW_OPEN); }
		 */

		// Manual Control for Elevator
		if (jsGamepad.getRawAxis(3) > 0) {
			if (jsGamepad.getPOV() == 180) {
				elev.disable();
				brake.set(BRAKE_OFF);
				talonElev.set(0.5);
			} else if (jsGamepad.getPOV() == 0) {
				elev.disable();
				brake.set(BRAKE_OFF);
				talonElev.set(-0.5);

			} else if (jsGamepad.getPOV() == -1) {
				talonElev.set(0);
				brake.set(BRAKE_ON);
			}
		}
		
	}

	public void Open() {
		claw.set(CLAW_OPEN);
	}

	public void Close() {
		claw.set(CLAW_CLOSE);
	}

}
