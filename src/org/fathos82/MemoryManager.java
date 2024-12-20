package org.fathos82;

import org.fathos82.memory_alocator.*;

import java.util.*;

import static org.fathos82.memory_alocator.MemoryAllocatorType.*;

public class MemoryManager {
    private Map<MemoryAllocatorType,MemoryAllocator> allocators;
    private RamMemory ramMemory;
    private Set<Integer> allocatedProcees = new HashSet<>();

    public MemoryManager(int capacity) {
        ramMemory = new RamMemory(capacity);
        initializeMemoryAllocators();
    }

    private void initializeMemoryAllocators() {
        allocators = new HashMap<>();
        allocators.put(BEST_FIT, new BestFitAllocator());
        allocators.put(FIRST_FIT, new FirstFitAllocator());
        allocators.put(NEXT_FIT, new NextFitAllocator());
        allocators.put(WORST_FIT, new WorstFit());

    }

    public void allocateMemory(MemoryAllocatorType memoryAllocatorType,Process process) {
        MemoryAllocator memoryAllocator = allocators.get(memoryAllocatorType);
        if (process.memoryUsageInBlocks > ramMemory.capacity()  || process.memoryUsageInBlocks > ramMemory.freeMemory()) {
            throw new RuntimeException("Memory allocation limit exceeded");
        }

        if (!allocatedProcees.contains(process.pid())){
            allocatedProcees.add(process.pid());
            memoryAllocator.allocate(ramMemory, process);
        }
        else {
            throw new RuntimeException("Already allocated process");
        }

}

    public void deallocateProcess(Process process){
        ramMemory.deallocate(process.startProcessPointer, process.startProcessPointer + process.memoryUsageInBlocks);

    }

    public int calculateExternalFragmentation(List<Process> pendingProcesses) {
        int totalFreeBlocks = ramMemory.freeMemory();
        int blockedProcesses = 0;

        for (Process process : pendingProcesses) {
            boolean canAllocate = false;

            // Verifica se algum fragmento livre consegue acomodar o processo
            int currentFree = 0;
            for (int bit : ramMemory.bitMap) {
                if (bit == 0) {
                    currentFree++;
                    if (currentFree >= process.memoryUsageInBlocks) {
                        canAllocate = true;
                        break;
                    }
                } else {
                    currentFree = 0;
                }
            }

            if (!canAllocate) {
                blockedProcesses++;
            }
        }

        // Fragmentação externa: proporção de processos bloqueados pelo total de blocos livres
        return blockedProcesses;
    }

     
    public void printMemoryStage() {
        int capacity = ramMemory.bitMap.length; // Tamanho da memória


        // Definindo cores ANSI
        String colorOccupied = "\033[48;5;34m"; // Verde para ocupado
        String colorFree = "\033[48;5;240m"; // Cinza para livre
        String resetColor = "\033[0m"; // Reset de cor

        // Criando a barra de progresso com borda ajustada
        StringBuilder progressBar = new StringBuilder("╭");

        // Adicionar linha superior com base no tamanho do array
        for (int i = 0; i < capacity * 2 + 2; i++) {
            progressBar.append("─");
        }
        progressBar.append("╮\n");

        // Linha com informações de uso
        progressBar.append(String.format("│ Memory Usage: %d/%d │\n", ramMemory.memoryUsage(), capacity));
        progressBar.append("│ ");

        // Adicionando os blocos à barra
        for (int bit : ramMemory.bitMap) {
            if (bit == 1) {
                progressBar.append(colorOccupied + "██" + resetColor); // Memória ocupada
            } else {
                progressBar.append(colorFree + "██" + resetColor); // Memória livre
            }
        }

        progressBar.append(" │\n");
        progressBar.append("╰");

        // Adicionar linha inferior com base no tamanho do array
        for (int i = 0; i < capacity * 2 + 2; i++) {
            progressBar.append("─");
        }
        progressBar.append("╯");

        // Imprimindo a barra de progresso estilizada
        System.out.println(progressBar.toString());
    }


}
