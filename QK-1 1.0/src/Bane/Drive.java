package Bane;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Drive {
	//Import joysticks and talons from Robot.java
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	
	int driveState = 1;

	public Drive(Joystick JL, Joystick JR, Talon TL, Talon TR, Talon OT, 
			DoubleSolenoid OD) {
		talonLeft = TL;
		talonRight = TR;
		omniTalon = OT;
		omniDirection = OD;
		joystickLeft = JL;
		joystickRight = JR;

	}

	public void robotInit() {
		driveState = 1;

	}

	public void autonomousPeriodic() {
		
	}

	public void teleopPeriodic() {

		if (joystickRight.getRawButton(1) == false) {
			driveState = 1; // changes driveState to 1 and enables robot to
							// utilize tank drive.
		}

		else if

		(joystickRight.getRawButton(1)) {
			driveState = 2; // changes driveState to 2 and enables robot to move
							// left to right.
		}

		if (driveState == 1) { // when driveState is one utilize tank drive.
			talonLeft.set(joystickLeft.getY());
			talonRight.set(joystickRight.getY());
			omniDirection.set(Value.kReverse);
			omniTalon.set(0);
		}

		else if (driveState == 2) { // when driveState is 2 allows robot to move
									// from left to right.
			
			talonLeft.set(0);
			talonRight.set(0);
			omniDirection.set(Value.kForward);
			omniTalon.set(joystickRight.getX());
		}

		else {// when not in driveState 1 or 2 stop all driving function.
			talonLeft.set(0);
			talonRight.set(0);
			omniDirection.set(Value.kReverse);
			omniTalon.set(0);
		}

	}
	
	
}
