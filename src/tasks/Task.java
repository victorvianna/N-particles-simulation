/**
 * Class that represents the tasks which the executor will have to run
 * (updates of position and velocity)
 */

package tasks;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import particles.Particle;

public abstract class Task implements Callable<Boolean> {
	protected final ArrayList<ArrayList<Particle>> particles;
	// particles.get(this.pastIndex()) stores particle data at time T-1
	// particles.get(this.presentIndex()) will store particle data now, at time T
	protected final int[] turn;	// wrapper to allow exchanging of present and past
	protected final int particleIndex;
	protected final double DELTA_TIME;

	Task(ArrayList<ArrayList<Particle>> particles, int particleIndex, double DELTA_TIME, int[] turn) {
		this.particles = particles;
		this.turn = turn;
		this.particleIndex = particleIndex;
		this.DELTA_TIME = DELTA_TIME;
	}

	protected abstract void runUpdate();

	public Boolean call() {
		runUpdate();
		return new Boolean(true);
	}

	protected int presentIndex() {
		return turn[0];
	}

	protected int pastIndex() {
		return 1 - turn[0];
	}

}
