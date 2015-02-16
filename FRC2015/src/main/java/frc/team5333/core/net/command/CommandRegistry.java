package frc.team5333.core.net.command;

import frc.team5333.core.monitor.PDPCommands;
import frc.team5333.core.net.NetworkedClient;

import java.util.ArrayList;

/**
 * A registry for the commands received from the Debug Console
 *
 * @author Jaci
 */
public class CommandRegistry {

    public static ArrayList<ICommand> cmds;

    public static void init() {
        cmds = new ArrayList<ICommand>();

        cmds.add(new PDPCommands());
    }

    public static void register(ICommand command) {
        cmds.add(command);
    }

    public static void parse(String command, NetworkedClient client) {
        String[] split = command.split(" ");
        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);

        for (ICommand cmd : cmds) {
            if (cmd.getCommandPrefix().equals(split[0]))
                cmd.runCommand(args, client);
        }
    }

}
