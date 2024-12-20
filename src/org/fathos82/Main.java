package org.fathos82;

import org.fathos82.memory_alocator.MemoryAllocatorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.fathos82.memory_alocator.MemoryAllocatorType.*;

public class Main {
    public static void main(String[] args) {
        // Configuração inicial
        List<Process> processes = List.of(
                new Process(1, 5), new Process(2, 4), new Process(3, 2),
                new Process(4, 5), new Process(5, 8), new Process(6, 3),
                new Process(7, 5), new Process(8, 8), new Process(9, 2),
                new Process(10, 6)
        );

        // Inicialização da memória
        MemoryManager memoryManager = new MemoryManager(32);

        // Execução dos algoritmos de alocação
        System.out.println("### Executando com First Fit ###");
        executeAlgorithm(memoryManager, processes, FIRST_FIT);

        System.out.println("\n### Executando com Best Fit ###");
        memoryManager = new MemoryManager(32); // Reinicia a memória
        executeAlgorithm(memoryManager, processes, BEST_FIT);

        System.out.println("\n### Executando com Worst Fit ###");
        memoryManager = new MemoryManager(32); // Reinicia a memória
        executeAlgorithm(memoryManager, processes, WORST_FIT);
    }

    private static void executeAlgorithm(MemoryManager memoryManager, List<Process> processes, MemoryAllocatorType algorithm) {
        Random random = new Random();
        List<Process> allocatedProcesses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Process selectedProcess = processes.get(random.nextInt(processes.size()));

            System.out.println("\nSorteado: Processo " + selectedProcess.pid() + " (" + selectedProcess.memoryUsageInBlocks() + " blocos)");

            try {
                // Verifica se o processo já está na memória
                if (allocatedProcesses.contains(selectedProcess)) {
                    // Desaloca se estiver na memória
                    memoryManager.deallocateProcess(selectedProcess);
                    allocatedProcesses.remove(selectedProcess);
                    System.out.println("Desalocado: Processo " + selectedProcess.pid());
                } else {
                    // Tenta alocar se não estiver
                    memoryManager.allocateMemory(algorithm, selectedProcess);
                    allocatedProcesses.add(selectedProcess);
                    System.out.println("Alocado: Processo " + selectedProcess.pid());
                }
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }


            // Exibe o estado da memória após cada operação
            List<Process>  peding = new ArrayList<>(processes);

            peding.removeAll(allocatedProcesses);
            memoryManager.printMemoryStage();
            int v = memoryManager.calculateExternalFragmentation(peding);
            System.out.println("Externa de fragmentation: " + v);
        }
    }
}
