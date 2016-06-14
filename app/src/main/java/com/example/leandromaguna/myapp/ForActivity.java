package com.example.leandromaguna.myapp;

/**
 * Created by thecocacolauser on 6/14/16.
 */
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface ForActivity {
}