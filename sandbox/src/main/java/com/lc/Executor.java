package com.lc;

/**
 * @author Aaron Yeung
 */
public class Executor {

    private static final String[] ENV = new String[]{"I am useless", "PATH=" + System.getenv("PATH")};

    private native static Result run(Params params);

    public static Result exec(Params params) {
        params.setEnv(ENV);
        // System.out.println(params);
        return run(params);
    }

    static {
        // before loading it, make sure had installed it
        System.load("/usr/lib/sandbox/sandbox.so");
    }
}