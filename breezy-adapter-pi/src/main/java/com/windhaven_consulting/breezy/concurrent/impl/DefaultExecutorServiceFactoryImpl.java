package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.concurrent.ScheduledExecutorServiceWrapper;
import com.windhaven_consulting.breezy.concurrent.ExecutorServiceFactory;

public class DefaultExecutorServiceFactoryImpl implements ExecutorServiceFactory {
	
	static final Logger LOG = LoggerFactory.getLogger(DefaultExecutorServiceFactoryImpl.class);

	private static int MAX_THREADS_IN_POOL = 25;

//	private static List<ExecutorService> singleThreadExecutorServices = new ArrayList<>();

	@Override
    /**
     * return an instance to the scheduled executor service (wrapper)
     */
	public ScheduledExecutorService getScheduledExecutorService() {
        // we return the protected wrapper to prevent any consumers from 
        // being able to shutdown the scheduled executor service
        return getScheduledExecutorServiceWrapper();
	}

	private ScheduledExecutorService getScheduledExecutorServiceWrapper() {
        return ScheduledExecutorServiceWrapperHolder.wrappedService;
	}

//	@Override
//    /**
//     * shutdown executor threads
//     */
//    public void shutdown() {
//        // shutdown each single thread executor in the managed collection
//        for (ExecutorService singleThreadExecutorService : singleThreadExecutorServices) {
//            shutdownExecutor(singleThreadExecutorService);
//        }
//
//        // shutdown scheduled executor instance
//        shutdownExecutor(getInternalScheduledExecutorService());
//    }
//
//    private void shutdownExecutor(ExecutorService executor) {
//        if (executor != null) {
//            if (!executor.isShutdown()) {
//                // this is a forceful shutdown;
//                // don't wait for the scheduled tasks to complete
//                executor.shutdownNow();
//            }
//        }
//    }

	private static ScheduledExecutorService getInternalScheduledExecutorService() {
        return ScheduledExecutorServiceHolder.heldExecutor;
    }

    /**
     * return an instance to the thread factory used to create new executor services
     */
    private static ThreadFactory getThreadFactory(final String nameFormat) {
        final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
        
        return new ThreadFactory() {
            final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = defaultThreadFactory.newThread(runnable);
                if (nameFormat != null) {
                    thread.setName(String.format(nameFormat, count.getAndIncrement()));

                    LOG.debug("ThreadFactory - create new factory: " + String.format(nameFormat, count.get()));
                }
                return thread;
            }
        };
    }

	private static class ScheduledExecutorServiceHolder {
        static final ScheduledExecutorService heldExecutor = Executors.newScheduledThreadPool(MAX_THREADS_IN_POOL, getThreadFactory("Breezy4Pi-scheduled-executor-%d"));
    }

	private static class ScheduledExecutorServiceWrapperHolder {
        static final ScheduledExecutorServiceWrapper wrappedService = new ScheduledExecutorServiceWrapper(getInternalScheduledExecutorService());
    }
}
