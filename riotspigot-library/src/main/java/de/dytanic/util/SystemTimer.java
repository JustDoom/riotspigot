/*
 * Copyright (c) Tarek Hosni El Alaoui 2017
 */

package de.dytanic.util;

import de.dytanic.$;

import java.lang.management.ManagementFactory;

/**
 * Created by Tareko on 23.09.2017.
 */
public class SystemTimer implements Runnable {

    public SystemTimer()
    {
    }

    @Override
    public void run()
    {
        System.out.println("Memory [\""
                + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1024)
                + "KB\"] | CPU Programm [\"" + $.DECIMAL_FORMAT.format($.internalCpuUsage()) + "\"] | CPU System [\""
                + $.DECIMAL_FORMAT.format($.cpuUsage()) + "\"]");
    }
}