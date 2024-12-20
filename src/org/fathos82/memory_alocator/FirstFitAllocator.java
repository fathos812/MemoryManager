package org.fathos82.memory_alocator;

import org.fathos82.Process;
import org.fathos82.RamMemory;

import java.util.Arrays;

public class FirstFitAllocator extends MemoryAllocator {
    @Override
    public void allocate(RamMemory memoryStage, Process process) {
        int start = -1;
        int end;
        for (int i = 0; i < memoryStage.bitMap.length; i++) {
            if (memoryStage.bitMap[i] == 0) {
                if (start == -1) {
                    start = i;
                    end = i + 1;
                }
                else {
                    end = i + 1;
                }

                if (end - start  == process.memoryUsageInBlocks()) {
                    memoryStage.allocate(start, end);
                    process.startProcessPointer = start;
                    break;
                }
            }
            else{
                start = -1;
            }
        }

    }
}
