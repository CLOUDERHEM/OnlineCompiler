package com.lc;

/**
 * result after run the process
 *
 * @author Aaron Yeung
 * @date 6/23/2022 9:51 AM
 */
public class Result {

    private int cpuTime;
    private int signal;
    private int memory;
    private int exitCode;
    private int result;
    private int error;
    private int realTime;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getRealTime() {
        return realTime;
    }

    public void setRealTime(int realTime) {
        this.realTime = realTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "cpuTime=" + cpuTime +
                ", signal=" + signal +
                ", memory=" + memory +
                ", exitCode=" + exitCode +
                ", result=" + result +
                ", error=" + error +
                ", realTime=" + realTime +
                '}';
    }
}

