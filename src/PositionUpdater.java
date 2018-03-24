package src;

import java.util.ArrayList;

import src.particles.*;

public class PositionUpdater extends Thread{
	private ArrayList<Particle> particles;
	private int index; // index of the particle we will update
	private final double DELTA_TIME;
	public PositionUpdater(ArrayList<Particle> particles, int index, double DELTA_TIME)
	{
		this.particles = particles;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public void run()
	{
		Particle particle = particles.get(index);

		double x = particle.x;
		double y = particle.y;
		double vx = particle.vx;
		double vy = particle.vy;

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

		particle.x = x;
		particle.y = y;
		particle.vx = vx;
		particle.vy = vy;
	}
}
