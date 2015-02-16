package frc.team5333.core.net.command;

import frc.team5333.core.net.NetworkDispatcher;
import frc.team5333.core.net.NetworkedClient;

/**
 * Used in the debug console to parse commands
 *
 * @author Jaci
 */
public interface ICommand {

    public String getCommandPrefix();

    public void runCommand(String[] args, NetworkedClient client);

}
