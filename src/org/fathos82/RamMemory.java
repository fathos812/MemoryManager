package org.fathos82;

import java.util.Arrays;

public class RamMemory {
    public int[] bitMap;
    private final int capacity;
    private int currentMemoryUsage;
    public RamMemory(int capacity) {
        this.capacity = capacity;
        bitMap = new int[capacity];
        Arrays.fill(bitMap, 0);
    }
    public void allocate(int start, int end){
        for (int i = start; i < end; i++) {
            bitMap[i] = 1;
        }
        currentMemoryUsage += end - start;
    }
    public void deallocate(int start, int end) {
        for (int i = start; i < end; i++) {
            bitMap[i] = 0;
        }
        currentMemoryUsage -= end - start;
    }


    public int capacity() {
        return capacity;
    }

    public int freeMemory() {
        return capacity - currentMemoryUsage;
    }


    public int memoryUsage() {
        return currentMemoryUsage;
    }
}
