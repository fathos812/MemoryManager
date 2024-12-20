package org.fathos82.memory_alocator;

import org.fathos82.Process;
import org.fathos82.RamMemory;

public class WorstFit extends MemoryAllocator {
    @Override
    public void allocate(RamMemory memoryStage, Process process) {
        int startFreeMemory = -1;
        int endFreeMemory = -1;

        int worstStartFreeMemory = -1;
        int worstEndFreeMemory = -1;
        for (int i = 0; i < memoryStage.bitMap.length; i++) {
            if (memoryStage.bitMap[i] == 0) {
                if (startFreeMemory == -1) {
                    startFreeMemory = i;
                    endFreeMemory = i + 1;
                }
                else {
                    endFreeMemory = i + 1;
                }

//                if (freeMemoryRange[1] - freeMemoryRange[0]  == process.memoryUsageInBlocks()) {
//                    memoryStage.allocate(freeMemoryRange[0], freeMemoryRange[1]);
//                }
            }
            if ((memoryStage.bitMap[i] == 1  || i == memoryStage.bitMap.length - 1) && startFreeMemory != -1){
                if (i == memoryStage.bitMap.length - 1) {
                    endFreeMemory = memoryStage.bitMap.length;
                }
                int currentRangeDiff = endFreeMemory - startFreeMemory ;

                if (currentRangeDiff >= process.memoryUsageInBlocks){
                    if (worstStartFreeMemory == -1){
                        worstStartFreeMemory = startFreeMemory;
                        worstEndFreeMemory = endFreeMemory;
                        continue;
                    }
                    int worstRangeDiff = worstEndFreeMemory - worstStartFreeMemory;
                    if (worstRangeDiff < currentRangeDiff ){
                        worstStartFreeMemory = startFreeMemory;
                        worstEndFreeMemory = endFreeMemory;
                    }
                }
                startFreeMemory= -1;
            }
        }
        memoryStage.allocate(worstStartFreeMemory, worstStartFreeMemory + process.memoryUsageInBlocks);


    }
}
