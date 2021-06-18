package de.dytanic;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

/**
 * Created by Tareko on 06.02.2018.
 */
public final class $ {

    private $() {}

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");

    public static final String EMPTY_STRING = "", SPACE_STRING = " ", SLASH_STRING = "/";

    public static double cpuUsage()
    {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad() * 100;
    }

    public static double internalCpuUsage()
    {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getProcessCpuLoad() * 100;
    }

    public static long systemMemory()
    {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }

    public static void sleep(long millis)
    {
        sleep(millis, 0);
    }

    public static void sleep(long millis, int nanos)
    {
        try
        {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException e)
        {
        }
    }

}