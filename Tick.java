import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;

public class Tick implements Runnable {
	private int sleepTime = 1000;
	private ClockPane clockPane;
	private boolean suspended = false;
	private boolean resume = false;
	private Lock lock = new ReentrantLock();
	private Condition stop = lock.newCondition();
	private AnnounceTimeOnSeparateThread announceTheard = new AnnounceTimeOnSeparateThread();

	// Constructor
	public Tick(ClockPane clockPane) {
		this.clockPane = clockPane;
	}

	@Override
	public void run() {
		moveClock();
	}

	// method for moving the clock every second and handling the announcements
	public void moveClock() {
		while (true) {
			lock.lock();
			if (suspended) {
				try {
					stop.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			} else {
				resume = true;
				clockPane.setCurrentTime();
				Platform.runLater(() -> clockPane.paintClock());
				try {
					if (clockPane.getSecond() == 0) {
						announceTheard.setHour(clockPane.getHour());
						announceTheard.setMinute(clockPane.getMinute());
						new Thread(announceTheard).start();
					}
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	// pause the clock
	public synchronized void pause() {
		suspended = true;
		resume = false;
	}

	// make the clock move again
	public void play() {
		if (resume)
			return;
		lock.lock();
		suspended = false;
		stop.signal();
		lock.unlock();
	}

}
