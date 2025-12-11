import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private final int id;
    private final int eatTime;
    Fork rightFork;
    Fork leftFork;

    public Philosopher(int id, int eatTime, Fork left, Fork right) {
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
                System.out.println("[Philosopher "+ id+"] Waiting for fork "+leftFork.getId()+"...");
                leftFork.getSemaphore().acquire();
                System.out.println("[Philosopher "+ id+"] Picking up fork "+leftFork.getId());

                System.out.println("[Philosopher "+ id+"] Waiting for fork "+rightFork.getId()+"...");
                rightFork.getSemaphore().acquire();
                System.out.println("[Philosopher "+ id+"] Picking up fork "+rightFork.getId());

                //eating
                System.out.println("[Philosopher "+ id+"] Eating for "+eatTime);
                Thread.sleep(eatTime* 1000);

                //release forks
                rightFork.getSemaphore().release();
                leftFork.getSemaphore().release();
                System.out.println("[Philosopher "+ id+"] Released both forks");
            }
        } catch (InterruptedException e) {
            System.out.println("[Philosopher "+id+"] Stopping.");
        }
    }
}
