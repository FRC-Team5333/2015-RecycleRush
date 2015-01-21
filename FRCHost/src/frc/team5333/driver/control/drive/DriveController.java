package frc.team5333.driver.control.drive;

import frc.team5333.NetIDs;
import frc.team5333.driver.net.EnumNetworkControllers;

/**
 * This class is responsible for storing data about the drive system and sending
 * said data over the network. This will keep track of drive data, allowing
 * us to store it or just purge an update if something isn't working right
 *
 * @author Jaci
 */
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
		EnumNetworkControllers.CONTROL.getController().sendMessage(NetIDs.DRIVE_LEFT, (float) -ThrottleScale.scale(left));
	}
	public static void updateRight() {
		EnumNetworkControllers.CONTROL.getController().sendMessage(NetIDs.DRIVE_RIGHT, (float) -ThrottleScale.scale(right));
	}
	
	
}
