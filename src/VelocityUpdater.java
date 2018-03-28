package src;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import src.particles.*;
import src.forces.*;

public class VelocityUpdater implements Callable<Boolean> {
	private final ArrayList< ArrayList<Particle> >  particles;
	private final int[] turn;
	private final ArrayList<Force> forces;
	private int index; // index of the particle we will update
	private final double DELTA_TIME;

	public VelocityUpdater(ArrayList< ArrayList<Particle> > particles, ArrayList<Force> forces, int index, double DELTA_TIME, int[] turn) {
		this.particles = particles;
		this.turn = turn;
		this.forces = forces;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public Boolean call() {
		int cur = this.turn[0];
		int past = 1 - cur;
		Particle src_past = particles.get(past).get(index);

		double ax = 0, ay = 0;
		for(int i = 0; i < particles.size(); i++) {
			if (i != index) {			
				Particle dst = particles.get(past).get(i);
				
				for (Force force : forces) {
					double[] delta_force = force.calculateInteraction(src_past, dst); 
					ax += delta_force[0] / src_past.mass;  
					ay += delta_force[1] / src_past.mass;
				}
			}
		}
		
		Particle src_cur = particles.get(cur).get(index);
		// Here he have to src_cur in the right side because we have already
		// adjusted the velocity of the particle after an eventual collison
		// with the border
		src_cur.vx = src_cur.vx + ax * DELTA_TIME;
		src_cur.vy = src_cur.vy + ay * DELTA_TIME;

		return new Boolean(true);
	}
}
