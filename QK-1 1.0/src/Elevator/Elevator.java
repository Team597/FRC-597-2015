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
	public void autoLift(){
		elevator.setSetpoint(2.3);
	}

	public void robotInit() {

		elevator.enable();
	}

	public void teleopPeriodic() {

		if (joystickElevator.getRawButton(1) == true) {
			elevator.setSetpoint(0.0); // sets pid to move to base position
		}
		if (joystickElevator.getRawButton(2) == true) {
			elevator.setSetpoint(3.0); // sets pid to move to tote 1 position

		}
		if (joystickElevator.getRawButton(3) == true) {
			elevator.setSetpoint(5.0); // sets pid to move to tote 2 position
		}
		if (joystickElevator.getRawButton(4) == true) {
			elevator.setSetpoint(7.0); // sets pid to move to tote 3 position

		}
		if (joystickElevator.getRawButton(10) == true) {
			elevator.setSetpoint(9.0); // sets pid to move to tote 4 position
		}
		if (joystickElevator.getRawButton(11) == true) {
			elevator.setSetpoint(11.0); // sets pid to move to top position

		}
		
	}
}
