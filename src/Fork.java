import java.util.concurrent.Semaphore;

public class Fork {
    private int id;
    private Semaphore semaphore;

        public Fork(int id) {
            this.id = id;
            this.semaphore = new Semaphore(1);
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
}
