package io.loot.lootsdk;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Deprecated
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface NotCancelable {

}
