package com.lc;

/**
 * @author Aaron Yeung
 */
public class Executor {

    /**
     * DEFAULT_ENV only init once
     */
    private static final String[] DEFAULT_ENV = {"eg=I_am_useless", "PATH=" + System.getenv("PATH")};

    private native static Result run(Params params);

    public static Result exec(Params params) {
        // get the final env[] with extras in param
        params.setEnv(StringUtils.concat(DEFAULT_ENV, params.getEnv()));

        return run(params);
    }

    static {
        // before loading it, make sure had installed it
        System.load("/usr/lib/sandbox/sandbox.so");
    }
}