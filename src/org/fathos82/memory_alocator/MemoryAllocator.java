package org.fathos82.memory_alocator;
import org.fathos82.Process;
import org.fathos82.RamMemory;
public abstract class MemoryAllocator {
    public abstract void allocate(RamMemory memoryStage, Process process);
}
