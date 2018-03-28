package tasks;

import java.util.ArrayList;

import particles.*;
import forces.*;

public class VelocityUpdate extends Task {
	protected final ArrayList<Force> forces;

	public VelocityUpdate(ArrayList<ArrayList<Particle>> particles, ArrayList<Force> forces, int particleIndex,
			double DELTA_TIME, int[] turn) {
		super(particles, particleIndex, DELTA_TIME, turn);
		this.forces = forces;
	}

	@Override
	public void runUpdate() {
		int cur = this.turn[0];
		int past = 1 - cur;
		Particle src_past = particles.get(past).get(particleIndex);

		double ax = 0, ay = 0;
		for (int i = 0; i < particles.size(); i++) {
			if (i != particleIndex) {
				Particle dst = particles.get(past).get(i);

				for (Force force : forces) {
					double[] delta_force = force.calculateInteraction(src_past, dst);
					ax += delta_force[0] / src_past.mass;
					ay += delta_force[1] / src_past.mass;
				}
			}
		}

		Particle src_cur = particles.get(cur).get(particleIndex);
		// Here we have src_cur on the right side because we have already
		// adjusted the velocity of the particle after an eventual collision
		// with the border of the window
		src_cur.vx = src_cur.vx + ax * DELTA_TIME;
		src_cur.vy = src_cur.vy + ay * DELTA_TIME;
	}
}
