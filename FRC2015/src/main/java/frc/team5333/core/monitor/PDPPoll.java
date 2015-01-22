package frc.team5333.core.monitor;

public class PDPPoll {

    public double[] current = new double[15];
    public double totalDraw;
    double highest;
    public int highestIndex = 0;

    public double batteryVoltage;

    public PDPPoll(double voltage, double... currents) {
        for (int i = 0; i < currents.length; i++) {
            current[i] = currents[i];
            totalDraw += currents[i];

            if (currents[i] > highest)
                highestIndex = i;
        }
        batteryVoltage = voltage;
    }

}
