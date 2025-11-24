import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private final int id;
    private final int eatTime;
    Semaphore rightFork;
    Semaphore leftFork;

    public Philosopher(int id, int eatTime, Semaphore left, Semaphore right) {
        this.id = id;
        this.eatTime = eatTime;
        this.leftFork = left;
        this.rightFork = right;
    }

    @Override
    public void run() {
        try {
            while(true) {

                //thinking
                System.out.println("[Philosopher "+ id+"] Thinking...");
                Thread.sleep(500);

                //try to get forks
                System.out.println("[Philosopher "+ id+"] Waiting for left fork...");
                leftFork.acquire();
                System.out.println("[Philosopher "+ id+"] Picking up left fork");

                System.out.println("[Philosopher "+ id+"] Waiting for right fork...");
                rightFork.acquire();
                System.out.println("[Philosopher "+ id+"] Picking up right fork");

                //eating
                System.out.println("[Philosopher "+ id+"] Eating for "+eatTime);
                Thread.sleep(eatTime* 1000);

                //release forks
                rightFork.release();
                leftFork.release();
                System.out.println("[Philosopher "+ id+"] Released both forks");
            }
        } catch (InterruptedException e) {
            System.out.println("[Philosopher "+id+"] Stopping.");
        }
    }
}
