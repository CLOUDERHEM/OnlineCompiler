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

    public Params() {
        //默认其他用户和用户组
        this.uid=-1;
        this.gid=-1;
    }

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
        final StringBuffer sb = new StringBuffer("Params{");
        sb.append("\nmaxCpuTime=").append(maxCpuTime);
        sb.append(", \nmaxRealTime=").append(maxRealTime);
        sb.append(", \nmaxMemory=").append(maxMemory);
        sb.append(", \nmaxProcessNumber=").append(maxProcessNumber);
        sb.append(", \nmaxOutputSize=").append(maxOutputSize);
        sb.append(", \nmaxStack=").append(maxStack);
        sb.append(", \nexePath='").append(exePath).append('\'');
        sb.append(", \ninputPath='").append(inputPath).append('\'');
        sb.append(", \noutputPath='").append(outputPath).append('\'');
        sb.append(", \nerrorPath='").append(errorPath).append('\'');
        sb.append(", \nlogPath='").append(logPath).append('\'');
        sb.append(", \nseccompRuleName='").append(seccompRuleName).append('\'');
        sb.append(", \nenv=").append(env == null ? "null" : Arrays.asList(env).toString());
        sb.append(", \nargs=").append(args == null ? "null" : Arrays.asList(args).toString());
        sb.append(", \nuid=").append(uid);
        sb.append(", \ngid=").append(gid);
        sb.append('}');
        return sb.toString();
    }
}


