package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw {
	Joystick jsClaw;
	Talon talonElev;
	DoubleSolenoid claw;

	boolean toggleClaw = false;
	boolean toggleButton = false;
	boolean toggleButton1 = false;
	boolean toggleButton2 = false;
	boolean toggleButton3 = false;
	String clawState = "Close";

	public Claw() {

		jsClaw = new Joystick(2);
		talonElev = new Talon(3);
		claw = new DoubleSolenoid(1, 6);

	}

	public void teleopPeriodic() {
		
		SmartDashboard.putString("Claw State: ", clawState);
		
		if (jsClaw.getRawButton(1) == true) {
			talonElev.set(jsClaw.getY());
		} else {
			talonElev.set(0);
		}

		if (toggleButton != jsClaw.getRawButton(6)
				&& jsClaw.getRawButton(6) == true) {
			toggleClaw = !toggleClaw;
		}
		toggleButton = jsClaw.getRawButton(6);

		if (toggleButton1 != jsClaw.getRawButton(7)
				&& jsClaw.getRawButton(7) == true) {
			toggleClaw = !toggleClaw;
		}
		toggleButton1 = jsClaw.getRawButton(7);

		if (toggleButton2 != jsClaw.getRawButton(10)
				&& jsClaw.getRawButton(10) == true) {
			toggleClaw = !toggleClaw;
		}
		toggleButton2 = jsClaw.getRawButton(10);

		if (toggleButton3 != jsClaw.getRawButton(11)
				&& jsClaw.getRawButton(11) == true) {
			toggleClaw = !toggleClaw;
		}
		toggleButton3 = jsClaw.getRawButton(11);

	}

	public void clawControl() {
		if (toggleClaw == false) {
			clawState = "Close";
			claw.set(Value.kForward);
		} else if (toggleClaw == true) {
			clawState = "Open";
			claw.set(Value.kReverse);
		}

	}
}