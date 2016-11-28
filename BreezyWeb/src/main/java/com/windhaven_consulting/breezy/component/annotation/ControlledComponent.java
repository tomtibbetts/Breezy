package com.windhaven_consulting.breezy.component.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;



@Retention(RUNTIME)
@Target({TYPE})
public @interface ControlledComponent {

	String value() default "";
	
	int numberOfOutputs() default 1;
	
	String[] pinNames() default {};
	
	OutputType outputType() default OutputType.DIGITAL_OUTPUT; 
}
