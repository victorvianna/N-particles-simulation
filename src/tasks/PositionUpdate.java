package tasks;

import java.util.ArrayList;

import GUI.*;
import particles.*;

public class PositionUpdate extends Task {

	public PositionUpdate(ArrayList<ArrayList<Particle>> particles, int particleIndex, double DELTA_TIME, int[] turn) {
		super(particles, particleIndex, DELTA_TIME, turn);
	}

	@Override
	protected void runUpdate() {
		Particle particle_past = particles.get(this.pastIndex()).get(particleIndex);
		Particle particle_cur = particles.get(this.presentIndex()).get(particleIndex);

		double x = particle_past.x;
		double y = particle_past.y;
		double vx = particle_past.vx;
		double vy = particle_past.vy;

		x = x + vx * DELTA_TIME;
		y = y + vy * DELTA_TIME;

		if(ParticleWindow.bouncingEnabled)
		{
			if (x < 0) {
				x = -x;
				vx = -vx;
			} else if (x > ParticleWindow.WIDTH) {
				x = ParticleWindow.WIDTH - (x - ParticleWindow.WIDTH);
				vx = -vx;
			}
			if (y < 0) {
				y = -y;
				vy = -vy;
			} else if (y > ParticleWindow.HEIGHT) {
				y = ParticleWindow.HEIGHT - (y - ParticleWindow.HEIGHT);
				vy = -vy;
			}

		}
		
		particle_cur.x = x;
		particle_cur.y = y;
		particle_cur.vx = vx;
		particle_cur.vy = vy;

	}
}
