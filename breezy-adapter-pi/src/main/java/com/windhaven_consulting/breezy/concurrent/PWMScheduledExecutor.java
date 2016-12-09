package com.windhaven_consulting.breezy.concurrent;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.concurrent.impl.DefaultExecutorServiceFactoryImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinBlinkStopTaskImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinBlinkTaskImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinDimmerTaskImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinPulsateTaskImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinPulseTaskImpl;
import com.windhaven_consulting.breezy.concurrent.impl.PWMOutputPinStopTaskImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class PWMScheduledExecutor {
	static final Logger LOG = LoggerFactory.getLogger(PWMScheduledExecutor.class);

    private static final ConcurrentHashMap<PWMOutputPin, ArrayList<ScheduledFuture<?>>> pinTaskQueue = new ConcurrentHashMap<>();
    private static ScheduledExecutorService scheduledExecutorService;
	private static ExecutorServiceFactory executorServiceFactory;

	public synchronized static Future<?> blink(PWMOutputPin pwmOutputPin, long delay, long duration, PWMPinState pwmPinState) {
		initialize(pwmOutputPin);
		
      // we only blink for requests with a valid delay in milliseconds
      if (delay > 0) {
          // make sure pin starts in active state
          pwmOutputPin.setState(pwmPinState);
          
          // create future job to toggle the pin state
          ScheduledFuture<?> scheduledFutureBlinkTask = scheduledExecutorService
              .scheduleAtFixedRate(new PWMOutputPinBlinkTaskImpl(pwmOutputPin), delay, delay, TimeUnit.MILLISECONDS);
              
          // get pending tasks for the current pin
          ArrayList<ScheduledFuture<?>> tasks;
          if (!pinTaskQueue.containsKey(pwmOutputPin)) {
              pinTaskQueue.put(pwmOutputPin, new ArrayList<ScheduledFuture<?>>());
          }
          tasks = pinTaskQueue.get(pwmOutputPin);

          // add the new scheduled task to the tasks collection
          tasks.add(scheduledFutureBlinkTask);

          // if a duration was defined, then schedule a future task to kill the blinker task
          if (duration > 0) {
              // create future job to stop blinking
              ScheduledFuture<?> scheduledFutureBlinkStopTask = scheduledExecutorService
                  .schedule(new PWMOutputPinBlinkStopTaskImpl(pwmOutputPin, PWMPinState.getInverseState(pwmPinState), scheduledFutureBlinkTask), duration, TimeUnit.MILLISECONDS);

              // add the new scheduled stop task to the tasks collection
              tasks.add(scheduledFutureBlinkStopTask);

              // create future task to clean up completed tasks
              createCleanupTask(duration + 500);                
          }      

          // return future task
          return scheduledFutureBlinkTask;
      }
      
      // no future task when a delay time has not been specified
      return null;
	}

    public synchronized static Future<?> pulse(PWMOutputPin pin, long duration, PWMPinState pulseState) {
        // create future return object
        ScheduledFuture<?> scheduledFuture = null; 
                
        // perform the initial startup and cleanup for this pin 
        initialize(pin);

        // we only pulse for requests with a valid duration in milliseconds
        if (duration > 0) {
            // set the active state
            pin.setState(pulseState);
            
            // create future job to return the pin to the low state
            scheduledFuture = scheduledExecutorService
                .schedule(new PWMOutputPinPulseTaskImpl(pin, PWMPinState.getInverseState(pulseState)), duration, TimeUnit.MILLISECONDS);

            // get pending tasks for the current pin
            ArrayList<ScheduledFuture<?>> tasks;
            if (!pinTaskQueue.containsKey(pin)) {
                pinTaskQueue.put(pin, new ArrayList<ScheduledFuture<?>>());
            }
            tasks = pinTaskQueue.get(pin);
            
            // add the new scheduled task to the tasks collection
            tasks.add(scheduledFuture);
            
            // create future task to clean up completed tasks
            createCleanupTask(duration + 500);
        }

        // return future task
        return scheduledFuture;
    }

    public synchronized static Future<?> pulsate(PWMOutputPin pwmOutputPin, long attack, long sustain, long release, long interval, int maxBrightness) {
        // create future return object
        ScheduledFuture<?> scheduledFuturePulsateTask = null; 
                
        // perform the initial startup and cleanup for this pin 
        initialize(pwmOutputPin);

        // we only pulse for requests with a valid duration in milliseconds
        if (attack > 0) {
            // set the active state
            pwmOutputPin.setState(PWMPinState.LOW);
            
            // create future job to return the pin to the low state
            scheduledFuturePulsateTask = scheduledExecutorService
                .scheduleAtFixedRate(new PWMOutputPinPulsateTaskImpl(pwmOutputPin, attack, sustain, release, interval, maxBrightness), 0, 1, TimeUnit.MILLISECONDS);

            // get pending tasks for the current pin
            ArrayList<ScheduledFuture<?>> tasks;
            if (!pinTaskQueue.containsKey(pwmOutputPin)) {
                pinTaskQueue.put(pwmOutputPin, new ArrayList<ScheduledFuture<?>>());
            }
            tasks = pinTaskQueue.get(pwmOutputPin);
            
            // add the new scheduled task to the tasks collection
            tasks.add(scheduledFuturePulsateTask);
            
            long totalDuration = attack + sustain + release + 1;
            
//            // create future job to stop blinking
//            ScheduledFuture<?> scheduledFuturePulsateStopTask = scheduledExecutorService
//                .schedule(new PWMOutputPinPulsateStopTaskImpl(scheduledFuturePulsateTask), totalDuration, TimeUnit.MILLISECONDS);
//
//            // add the new scheduled stop task to the tasks collection
//            tasks.add(scheduledFuturePulsateStopTask);
//
            // create future task to clean up completed tasks
            createCleanupTask(totalDuration + 500);
        }

        // return future task
        return scheduledFuturePulsateTask;
    }

	public static void stop(PWMOutputPin pwmOutputPin) {
		// TODO: look at compareto method of pin to see if doing correctly
		cancelAllTasks(pwmOutputPin);
		
		createCleanupTask(500);
	}

	public static ScheduledFuture<?> dimTo(PWMOutputPin pwmOutputPin, long attack, int brightness) {
        // create future return object
        ScheduledFuture<?> scheduledFuturePulsateTask = null; 
                
        // perform the initial startup and cleanup for this pin 
        initialize(pwmOutputPin);

        // we only pulse for requests with a valid duration in milliseconds
        if (attack > 0) {
            // create future job to return the pin to the low state
            scheduledFuturePulsateTask = scheduledExecutorService.scheduleAtFixedRate(new PWMOutputPinDimmerTaskImpl(pwmOutputPin, attack, brightness), 0, 1, TimeUnit.MILLISECONDS);

            // get pending tasks for the current pin
            ArrayList<ScheduledFuture<?>> tasks;
            if (!pinTaskQueue.containsKey(pwmOutputPin)) {
                pinTaskQueue.put(pwmOutputPin, new ArrayList<ScheduledFuture<?>>());
            }
            tasks = pinTaskQueue.get(pwmOutputPin);
            
            // add the new scheduled task to the tasks collection
            tasks.add(scheduledFuturePulsateTask);
    
			// create future job to stop dimming
			ScheduledFuture<?> scheduledDimmerStopTask = scheduledExecutorService
				.schedule(new PWMOutputPinStopTaskImpl(scheduledFuturePulsateTask), attack, TimeUnit.MILLISECONDS);
			
			// add the new scheduled stop task to the tasks collection
			tasks.add(scheduledDimmerStopTask);

            createCleanupTask(attack + 500);
        }

        // return future task
        return scheduledFuturePulsateTask;
	}

	private synchronized static ScheduledFuture<?>  createCleanupTask(long delay) {
		// create future task to clean up completed tasks
        @SuppressWarnings({"rawtypes", "unchecked"})
        ScheduledFuture<?> cleanupFutureTask = scheduledExecutorService.schedule(new Callable() {
        	
            public Object call() throws Exception {
                for (Entry<PWMOutputPin, ArrayList<ScheduledFuture<?>>> item : pinTaskQueue.entrySet()) {
                    ArrayList<ScheduledFuture<?>> remainingTasks = item.getValue();

                    // if a task is found, then cancel all pending tasks immediately and remove them
                    if (remainingTasks != null && !remainingTasks.isEmpty()) {
                        for (int index = (remainingTasks.size() - 1); index >= 0; index--) {
                            ScheduledFuture<?> remainingTask = remainingTasks.get(index);
                            if (remainingTask.isCancelled() || remainingTask.isDone()) {
                                remainingTasks.remove(index);
                            }
                        }
                        
                        // if no remaining future tasks are remaining, then remove this pin from the tasks queue
                        if (remainingTasks.isEmpty()) {
                            pinTaskQueue.remove(item.getKey());
                        }
                    }
                }
                return null;
            }
        }, delay, TimeUnit.MILLISECONDS);
        
        return cleanupFutureTask;
	}

	private synchronized static void initialize(PWMOutputPin pwmOutputPin) {
		if (scheduledExecutorService == null) {
	        scheduledExecutorService = getExecutorServiceFactory().getScheduledExecutorService();
	    }
	
		cancelAllTasks(pwmOutputPin);
	}
	
    private static void cancelAllTasks(PWMOutputPin pwmOutputPin) {
	    // determine if any existing future tasks are already scheduled for this pin
	    if (pinTaskQueue.containsKey(pwmOutputPin)) {
	        // if a task is found, then cancel all pending tasks immediately and remove them
	        ArrayList<ScheduledFuture<?>> tasks = pinTaskQueue.get(pwmOutputPin);
	        if (tasks != null && !tasks.isEmpty()) {
	            for (int index =  (tasks.size() - 1); index >= 0; index--) {
	                ScheduledFuture<?> task = tasks.get(index);
	                task.cancel(true);
	                tasks.remove(index);
	            }
	        }
	        
	        // if no remaining future tasks are remaining, then remove this pin from the tasks queue
	        if (tasks != null && tasks.isEmpty()) {
	            pinTaskQueue.remove(pwmOutputPin);
	        }
	    }
	}

	private static ExecutorServiceFactory getExecutorServiceFactory() {
        // if an executor service provider factory has not been created, then create a new default instance
        if (executorServiceFactory == null) {
            executorServiceFactory = new DefaultExecutorServiceFactoryImpl();
        }

        // return the provider instance
        return executorServiceFactory;
    }
	
}
