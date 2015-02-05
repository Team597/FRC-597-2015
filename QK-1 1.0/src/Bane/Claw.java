package Bane;

import Support.ToggleButton;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Claw {
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	Joystick joystickLeft;
	ToggleButton buttonToggleClaw;

	public Claw(Joystick JL, DoubleSolenoid SCL, DoubleSolenoid SCR) {
		
		solenoidClawLeft = SCL;
		solenoidClawRight = SCR;
		joystickLeft = JL;

		buttonToggleClaw = new ToggleButton(true); // creates a new toggle
	}

	public void teleopPeriodic() {
		buttonToggleClaw.setCurrentState(joystickLeft.getRawButton(1)); // Changes the currentState to true or false
		if (buttonToggleClaw.getCurrentState() == false) { // if the value of the currentState is false make the piston 
			solenoidClawLeft.set(Value.kForward);		   // shoot forward
			solenoidClawRight.set(Value.kForward);		   
		} else if (buttonToggleClaw.getCurrentState() == true) {
			solenoidClawLeft.set(Value.kReverse); 		   // if the value of currentState is true make the  piston
			solenoidClawRight.set(Value.kReverse);		   //retract
		}
	}

	public void clawOpen() {
		buttonToggleClaw.setInternalToggleState(false);
	}

	public void clawClose() {
		buttonToggleClaw.setInternalToggleState(true);
	}

}
