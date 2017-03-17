package com.windhaven_consulting.breezy.concurrent;

import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorServiceFactory {
    ScheduledExecutorService getScheduledExecutorService();
//    void shutdown();
}
