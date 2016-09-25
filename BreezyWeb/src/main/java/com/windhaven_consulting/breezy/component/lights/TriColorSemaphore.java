package com.windhaven_consulting.breezy.component.lights;

import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;


@ControlledComponent(value="Tri-color Semaphore",
	numberOfOutputs=3,
	pinNames= {"Color 1", "Color 2", "Color 3"})
public class TriColorSemaphore extends AbstractSemaphore {
	private static final long serialVersionUID = 1L;

}
