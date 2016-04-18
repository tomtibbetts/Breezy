package com.windhaven_consulting.breezy.component.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface ControlledParameter {

	String name() default "";
	
	boolean required() default false;
	
	ParameterFieldType parameterFieldType() default ParameterFieldType.STRING;
}
