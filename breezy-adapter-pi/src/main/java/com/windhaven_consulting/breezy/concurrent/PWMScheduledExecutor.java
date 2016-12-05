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
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class PWMScheduledExecutor {
	static final Logger LOG = LoggerFactory.getLogger(PWMScheduledExecutor.class);

    private static final ConcurrentHashMap<PWMOutputPin, ArrayList<ScheduledFuture<?>>> pinTaskQueue = new ConcurrentHashMap<>();
    private static ScheduledExecutorService scheduledExecutorService;
	private static ExecutorServiceFactory executorServiceFactory;

	public synchronized static Future<?> blink(PWMOutputPin pwmOutputPin, long delay, long duration, PWMPinState pwmPinState) {
		LOG.debug("name: " + pwmOutputPin.getName() + ", id: " + pwmOutputPin.getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pwmPinState = " + pwmPinState);

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

          LOG.debug("leaving blink with task");
          // return future task
          return scheduledFutureBlinkTask;
      }
      
      // no future task when a delay time has not been specified
      LOG.debug("leaving blink with no task");
      return null;
	}

	private synchronized static ScheduledFuture<?>  createCleanupTask(long delay) {
		LOG.debug("entering createCleanupTask, delay = " + delay );
		
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
        
        LOG.debug("leaving cleanupFutureTask");
        return cleanupFutureTask;
	}

	private synchronized static void initialize(PWMOutputPin pwmOutputPin) {
		LOG.debug("entering initialize");
		
		if (scheduledExecutorService == null) {
	        scheduledExecutorService = getExecutorServiceFactory().getScheduledExecutorService();
	    }
	
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

	    LOG.debug("leaving initialize");
	}
	
    private static ExecutorServiceFactory getExecutorServiceFactory() {
		LOG.debug("entering getExecutorServiceFactory");

        // if an executor service provider factory has not been created, then create a new default instance
        if (executorServiceFactory == null) {
            executorServiceFactory = new DefaultExecutorServiceFactoryImpl();
        }

        LOG.debug("leaving getExecutorServiceFactory");
        
        // return the provider instance
        return executorServiceFactory;
    }
	
	
//    public synchronized static Future<?> blink(GpioPinDigitalOutput pin, long delay, long duration, PinState blinkState) {
//
//        // perform the initial startup and cleanup for this pin 
//        init(pin);
//        
//        // we only blink for requests with a valid delay in milliseconds
//        if (delay > 0) {
//            // make sure pin starts in active state
//            pin.setState(blinkState);
//            
//            // create future job to toggle the pin state
//            ScheduledFuture<?> scheduledFutureBlinkTask = scheduledExecutorService
//                .scheduleAtFixedRate(new GpioBlinkTaskImpl(pin), delay, delay, TimeUnit.MILLISECONDS);
//                
//            // get pending tasks for the current pin
//            ArrayList<ScheduledFuture<?>> tasks;
//            if (!pinTaskQueue.containsKey(pin)) {
//                pinTaskQueue.put(pin, new ArrayList<ScheduledFuture<?>>());
//            }
//            tasks = pinTaskQueue.get(pin);
//
//            // add the new scheduled task to the tasks collection
//            tasks.add(scheduledFutureBlinkTask);
//
//            // if a duration was defined, then schedule a future task to kill the blinker task
//            if (duration > 0) {
//                // create future job to stop blinking
//                ScheduledFuture<?> scheduledFutureBlinkStopTask = scheduledExecutorService
//                    .schedule(new GpioBlinkStopTaskImpl(pin,PinState.getInverseState(blinkState), scheduledFutureBlinkTask), duration, TimeUnit.MILLISECONDS);
//
//                // add the new scheduled stop task to the tasks collection
//                tasks.add(scheduledFutureBlinkStopTask);
//
//                // create future task to clean up completed tasks
//                createCleanupTask(duration + 500);                
//            }      
//
//            // return future task
//            return scheduledFutureBlinkTask;
//        }
//        
//        // no future task when a delay time has not been specified
//        return null;
//    }

	
	
	
//
//	    private static final ConcurrentHashMap<GpioPinDigitalOutput, ArrayList<ScheduledFuture<?>>> pinTaskQueue = new ConcurrentHashMap<>();
//	    private static ScheduledExecutorService scheduledExecutorService;
//
//	    private synchronized static void init(GpioPinDigitalOutput pin) {
//	        if (scheduledExecutorService == null) {
//	            scheduledExecutorService = GpioFactory.getExecutorServiceFactory().getScheduledExecutorService();
//	        }
//
//	        // determine if any existing future tasks are already scheduled for this pin
//	        if (pinTaskQueue.containsKey(pin)) {
//	            // if a task is found, then cancel all pending tasks immediately and remove them
//	            ArrayList<ScheduledFuture<?>> tasks = pinTaskQueue.get(pin);
//	            if (tasks != null && !tasks.isEmpty()) {
//	                for (int index =  (tasks.size() - 1); index >= 0; index--) {
//	                    ScheduledFuture<?> task = tasks.get(index);
//	                    task.cancel(true);
//	                    tasks.remove(index);
//	                }
//	            }
//	            
//	            // if no remaining future tasks are remaining, then remove this pin from the tasks queue
//	            if (tasks != null && tasks.isEmpty()) {
//	                pinTaskQueue.remove(pin);
//	            }
//	        }
//	    }
//	    
//	    private synchronized static ScheduledFuture<?> createCleanupTask(long delay) {
//	        // create future task to clean up completed tasks
//
//	        @SuppressWarnings({"rawtypes", "unchecked"})
//	        ScheduledFuture<?> cleanupFutureTask = scheduledExecutorService.schedule(new Callable() {
//	            public Object call() throws Exception {
//	                for (Entry<GpioPinDigitalOutput, ArrayList<ScheduledFuture<?>>> item : pinTaskQueue.entrySet()) {
//	                    ArrayList<ScheduledFuture<?>> remainingTasks = item.getValue();
//
//	                    // if a task is found, then cancel all pending tasks immediately and remove them
//	                    if (remainingTasks != null && !remainingTasks.isEmpty()) {
//	                        for (int index = (remainingTasks.size() - 1); index >= 0; index--) {
//	                            ScheduledFuture<?> remainingTask = remainingTasks.get(index);
//	                            if (remainingTask.isCancelled() || remainingTask.isDone()) {
//	                                remainingTasks.remove(index);
//	                            }
//	                        }
//	                        
//	                        // if no remaining future tasks are remaining, then remove this pin from the tasks queue
//	                        if (remainingTasks.isEmpty()) {
//	                            pinTaskQueue.remove(item.getKey());
//	                        }
//	                    }
//	                }
//	                return null;
//	            }
//	        }, delay, TimeUnit.MILLISECONDS);
//	        
//	        return cleanupFutureTask;
//	    }
//
//	    public synchronized static Future<?> pulse(GpioPinDigitalOutput pin, long duration, PinState pulseState) {
//	        return pulse(pin, duration, pulseState, null);
//	    }
//
//	    public synchronized static Future<?> pulse(GpioPinDigitalOutput pin, long duration, PinState pulseState, Callable<?> callback) {
//	        
//	        // create future return object
//	        ScheduledFuture<?> scheduledFuture = null; 
//	                
//	        // perform the initial startup and cleanup for this pin 
//	        init(pin);
//
//	        // we only pulse for requests with a valid duration in milliseconds
//	        if (duration > 0) {
//	            // set the active state
//	            pin.setState(pulseState);
//	            
//	            // create future job to return the pin to the low state
//	            scheduledFuture = scheduledExecutorService
//	                .schedule(new GpioPulseTaskImpl(pin, PinState.getInverseState(pulseState), callback), duration, TimeUnit.MILLISECONDS);
//
//	            // get pending tasks for the current pin
//	            ArrayList<ScheduledFuture<?>> tasks;
//	            if (!pinTaskQueue.containsKey(pin)) {
//	                pinTaskQueue.put(pin, new ArrayList<ScheduledFuture<?>>());
//	            }
//	            tasks = pinTaskQueue.get(pin);
//	            
//	            // add the new scheduled task to the tasks collection
//	            tasks.add(scheduledFuture);
//	            
//	            // create future task to clean up completed tasks
//	            createCleanupTask(duration + 500);
//	        }
//
//	        // return future task
//	        return scheduledFuture;
//	    }
//
//	}
	
	
}
