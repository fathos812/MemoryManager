package org.fathos82.memory_alocator;

import org.fathos82.Process;
import org.fathos82.RamMemory;

import java.util.ArrayList;
import java.util.List;

public class BestFitAllocator extends MemoryAllocator{
    @Override
    public void allocate(RamMemory memoryStage, Process process) {
        int startFreeMemory = -1;
        int endFreeMemory = -1;

        int bestStartFreeMemory = -1;
        int bestEndFreeMemory = -1;
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
                    if (bestStartFreeMemory == -1){
                        bestStartFreeMemory = startFreeMemory;
                        bestEndFreeMemory = endFreeMemory;
                        continue;
                    }
                    int bestRangeDiff = bestEndFreeMemory - bestStartFreeMemory;
                    if (currentRangeDiff < bestRangeDiff){
                        bestStartFreeMemory = startFreeMemory;
                        bestEndFreeMemory = endFreeMemory;
                    }
                }
                startFreeMemory= -1;
            }
        }
        memoryStage.allocate(bestStartFreeMemory, bestStartFreeMemory + process.memoryUsageInBlocks);
    }
}
