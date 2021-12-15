package com.gr.utils.configuration;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Config {

	String configFileName();

	ConfigType type() default ConfigType.JSON;

	public enum ConfigType {
		JSON, XML
	}
}
