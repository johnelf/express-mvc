package com.expressmvc.annotation;


public @interface PathVariable {
    String value() default "";
}
