package com.lc;

/**
 * @author Aaron Yeung
 */
public class Executor {

    public native static Result run(Params params);

    static {
        // before loading it, make sure had installed it
        System.load("/usr/lib/sandbox/sandbox.so");
    }
}