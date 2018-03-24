package src;

import java.util.ArrayList;

import src.particles.*;
import src.forces.*;

public class VelocityUpdater extends Thread {
	private ArrayList<Particle> particles;
	private ArrayList<Force> forces;
	private int index; // index of the particle we will update
	private final double DELTA_TIME;

	public VelocityUpdater(ArrayList<Particle> particles, ArrayList<Force> forces, int index, double DELTA_TIME) {
		this.particles = particles;
		this.forces = forces;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public void run() {
		Particle src = particles.get(index);
		double ax = 0, ay = 0;
		for(int i = 0; i < particles.size(); i++) {
			if (i != index) {			
				Particle dst = particles.get(i);
				
				for (Force force : forces) {
					double[] delta_force = force.calculateInteraction(src, dst); 
					ax += delta_force[0] / src.mass;  
					ay += delta_force[1] / src.mass;
				}
			}
		}
		
		src.vx += ax * DELTA_TIME;
		src.vy += ay * DELTA_TIME;
	}
}
