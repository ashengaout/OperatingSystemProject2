import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main{
    public static void main(String[] args) {
        System.out.println("=== Reading Processes for Dining Philosophers ===");

        List<ProcessEntry> processEntries = readProcesses("process.txt");
        if(processEntries.size() != 5) {
            System.out.println("Error: processes.txt must contain exactly five philosophers.");
            return;
        }

        //create fork semaphores
        Fork[] forks = new Fork[5];
        for(int i = 0; i < 5; i++) {
            forks[i] = new Fork(i);
        }

        //Create philosopher threads
        Philosopher[] philosophers = new Philosopher[5];
        for(int i = 0; i < 5; i++) {
            ProcessEntry p = processEntries.get(i);

            Fork leftFork = forks[i];
            Fork rightFork = forks[(i+1)%5];

            //deadlock avoidance: last philosopher picks up right fork first
            if(i ==4) {
                philosophers[i] = new Philosopher(p.getPid(), p.getBurstTime(), rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(p.getPid(), p.getBurstTime(), leftFork, rightFork);
            }
        }

        System.out.println("\n=== Starting Dining Philosopher Simulation ===");

        for(Philosopher ph : philosophers) {
            ph.start();
        }

        //let them run
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Stop simulation
        System.out.println("\n=== Stopping Philosophers ===");
        for(Philosopher ph: philosophers) {
            ph.interrupt();
        }

        System.out.println("=== Simulation Finished ===");
    }

    public static List<ProcessEntry> readProcesses(String filename) {
        List<ProcessEntry> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();

            while((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                int pid = Integer.parseInt(parts[0]);
                int arrival = Integer.parseInt(parts[1]);
                int burst = Integer.parseInt(parts[2]);

                list.add(new ProcessEntry(pid, arrival, burst));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


}
