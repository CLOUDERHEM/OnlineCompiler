package com.lc;

import java.util.Arrays;

/**
 * the params for run process
 *
 * @author Aaron Yeung
 * @date 6/23/2022 9:56 AM
 */
public class Params {
    int maxCpuTime;
    int maxRealTime;
    int maxMemory;
    int maxProcessNumber;
    int maxOutputSize;
    int maxStack;
    String exePath;
    String inputPath;
    String outputPath;
    String errorPath;
    String logPath;
    String seccompRuleName;
    String[] env;
    String[] args;
    int uid;
    int gid;

    public int getMaxCpuTime() {
        return maxCpuTime;
    }

    public void setMaxCpuTime(int maxCpuTime) {
        this.maxCpuTime = maxCpuTime;
    }

    public int getMaxRealTime() {
        return maxRealTime;
    }

    public void setMaxRealTime(int maxRealTime) {
        this.maxRealTime = maxRealTime;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    public int getMaxProcessNumber() {
        return maxProcessNumber;
    }

    public void setMaxProcessNumber(int maxProcessNumber) {
        this.maxProcessNumber = maxProcessNumber;
    }

    public int getMaxOutputSize() {
        return maxOutputSize;
    }

    public void setMaxOutputSize(int maxOutputSize) {
        this.maxOutputSize = maxOutputSize;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public String getExePath() {
        return exePath;
    }

    public void setExePath(String exePath) {
        this.exePath = exePath;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getSeccompRuleName() {
        return seccompRuleName;
    }

    public void setSeccompRuleName(String seccompRuleName) {
        this.seccompRuleName = seccompRuleName;
    }

    public String[] getEnv() {
        return env;
    }

    public void setEnv(String[] env) {
        this.env = env;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    @Override
    public String toString() {
        return "Params{" +
                "maxCpuTime=" + maxCpuTime +
                ", maxRealTime=" + maxRealTime +
                ", maxMemory=" + maxMemory +
                ", maxProcessNumber=" + maxProcessNumber +
                ", maxOutputSize=" + maxOutputSize +
                ", maxStack=" + maxStack +
                ", exePath='" + exePath + '\'' +
                ", inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", errorPath='" + errorPath + '\'' +
                ", logPath='" + logPath + '\'' +
                ", seccompRuleName='" + seccompRuleName + '\'' +
                ", env=" + Arrays.toString(env) +
                ", args=" + Arrays.toString(args) +
                ", uid=" + uid +
                ", gid=" + gid +
                '}';
    }
}


