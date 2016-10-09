package com.windhaven_consulting.breezy.component.lights;

import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;


@ControlledComponent(value="Bi-color Semaphore",
	numberOfOutputs=2,
	pinNames={"Color 1", "Color 2"})
public class BiColorSemaphore extends AbstractSemaphore {
	private static final long serialVersionUID = 1L;
	
}
