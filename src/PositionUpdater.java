package src;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import src.particles.*;

public class PositionUpdater implements Callable<Boolean> {
	private final ArrayList< ArrayList<Particle> > particles;
	private final int[] turn;
	private final int index; // index of the particle we will update
	private final double DELTA_TIME;

	public PositionUpdater(ArrayList< ArrayList<Particle> > particles, int index, double DELTA_TIME, int[] turn)
	{
		this.particles = particles;
		this.turn = turn;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public Boolean call()
	{
	        int cur = this.turn[0];
		int past = 1 - cur;

		Particle particle_past = particles.get(past).get(index);
		Particle particle_cur = particles.get(cur).get(index);

		double x = particle_past.x;
		double y = particle_past.y;
		double vx = particle_past.vx;
		double vy = particle_past.vy;

		x = x + vx * DELTA_TIME;
		y = y + vy * DELTA_TIME;

		if (x < 0) {
			x = -x;
			vx = -vx;
		} else if (x > Window.WIDTH) {
			x = Window.WIDTH - (x - Window.WIDTH);
			vx = -vx;
		}
		if (y < 0) {
			y = -y;
			vy = -vy;
		} else if (y > Window.HEIGHT) {
			y = Window.HEIGHT - (y - Window.HEIGHT);
			vy = -vy;
		}

		particle_cur.x = x;
		particle_cur.y = y;
		particle_cur.vx = vx;
		particle_cur.vy = vy;

		return new Boolean(true);
	}
}
