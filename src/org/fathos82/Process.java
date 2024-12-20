package org.fathos82;

public class Process{
    public int pid;
    public  int memoryUsageInBlocks;
    public int startProcessPointer;

    public Process(int pid, int memoryUsageInBlocks){
        this.pid = pid;
        this.memoryUsageInBlocks = memoryUsageInBlocks;
    }

    public void setStartProcessPointer(int startProcessPointer) {
        this.startProcessPointer = startProcessPointer;
    }

    public int memoryUsageInBlocks() {
        return memoryUsageInBlocks;
    }

    public Integer pid() {
        return pid;
    }
}
