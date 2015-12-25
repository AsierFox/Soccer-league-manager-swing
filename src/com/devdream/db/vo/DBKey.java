package com.devdream.db.vo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define the key type of the
 * columns on the database table (In Value Object).
 * 
 * @author Asier Gonzalez
 */
@Retention(RetentionPolicy.RUNTIME)
public abstract @interface DBKey {
	enum Key { PRIMARY, FOREIGN }
	Key key();
	String REFERENCES() default "";
	String ON() default "";
}
