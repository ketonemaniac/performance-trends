package com.ullink.instrumentation;

import com.ullink.duration.logging.FastLogger;

import java.lang.instrument.Instrumentation;

public class DurationAgent
{

    public static void premain(String agentArgs, Instrumentation inst)
    {
        System.out.println("Ullink profiler agent attached!");

        /**
         * Agent args can be passed in like this: -javaagent:d:\Data\profiler-agent-1.0-SNAPSHOT.jar=d:\Data\method-selection.properties,d:\logs,EDMA
         * Simple convention:
         * - first arg is the path to the properties file in which the method selector is configured (mandatory)
         * - second arg is the location where the duration logs are generated (optional, default location being the java temp dir's durations folder)
         * - third arg is a tag we can use to filter the measurements (e.g. filter results per EDMA, per SMART, per MONITORING etc)
         */
        String tag = null;
        if (agentArgs != null)
        {
            String[] args = agentArgs.split(",");
            if (args.length >= 2) {
                String durationLogsPath = args[1];
                FastLogger.initLogPath(durationLogsPath);
                System.out.println("Log output directory is: " + durationLogsPath);
            }
            if(args.length >= 3)
            {
                tag = args[2];
            }

        }

        inst.addTransformer(new DurationTransformer(tag));
    }
}