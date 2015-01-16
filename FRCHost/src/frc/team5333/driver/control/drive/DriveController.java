package frc.team5333.driver.control.drive;

import static frc.team5333.driver.net.NetworkController.sendMessage;
import frc.team5333.NetIDs;
import frc.team5333.driver.net.NetworkController;

public class DriveController {

	private static float left = 0;
	private static float right = 0;
	
	public static void setLeft(float f) {
		left = f;
		updateLeft();
	}
	
	public static void setRight(float f) {
		right = f;
		updateRight();
	}
	
	public static void updateLeft() {
		NetworkController.sendMessage(NetIDs.DRIVE_LEFT, (float) -ThrottleScale.scale(left));
	}
	public static void updateRight() {
		NetworkController.sendMessage(NetIDs.DRIVE_RIGHT, (float) -ThrottleScale.scale(right));
	}
	
	
}
