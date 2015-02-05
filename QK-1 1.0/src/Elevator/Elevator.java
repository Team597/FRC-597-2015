package Elevator;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

public class Elevator {

	Talon talonElevator;
	Encoder encoderElevator;
	PIDController elevator;
	Joystick joystickElevator;

	public Elevator() {
		talonElevator = new Talon(3);
		joystickElevator = new Joystick(3);
		encoderElevator = new Encoder(0, 1);
		elevator = new PIDController(0, 0, 0.1, encoderElevator, talonElevator);
	}

	public void autoDrop() {
		elevator.setSetpoint(0.0);
	}

	public void autoTote1() {
		elevator.setSetpoint(1.0);
	}

	public void robotInit() {

		elevator.enable();
	}

	public void teleopPeriodic() {

		if (joystickElevator.getRawButton(1) == true) {
			elevator.setSetpoint(0.0);
		}
		if (joystickElevator.getRawButton(2) == true) {
			elevator.setSetpoint(2.0);
		}
		if (joystickElevator.getRawButton(3) == true) {
			elevator.setSetpoint(4.0);
		}
		if (joystickElevator.getRawButton(4) == true) {
			elevator.setSetpoint(6.0);
		}
		if (joystickElevator.getRawButton(5) == true) {
			elevator.setSetpoint(8.0);
		}
		if (joystickElevator.getRawButton(6) == true) {
			elevator.setSetpoint(10.0);
		}
	}
}
