package org.fathos82.memory_alocator;

import org.fathos82.Process;
import org.fathos82.RamMemory;

public class NextFitAllocator extends MemoryAllocator{
    private int currentIndex = 0;

    @Override
    public void allocate(RamMemory memoryStage, Process process) {
        int start = -1;
        int end;
        for (int count = 0; count < memoryStage.bitMap.length; count++) {


            if (memoryStage.bitMap[currentIndex] == 0) {
                if (start == -1) {
                    start = currentIndex;
                    end = currentIndex + 1;
                }
                else {
                    end = currentIndex + 1;
                }

                if (end - start  == process.memoryUsageInBlocks()) {
                    System.out.println(start);
                    System.out.println(end);
                    memoryStage.allocate(start, end);
                    process.startProcessPointer = start;
                    break;
                }
            }
            else{
                start = -1;
            }
            currentIndex = (currentIndex + 1) % memoryStage.bitMap.length;
            count++;
        }


    }

}
