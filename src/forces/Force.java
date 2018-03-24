package src.forces;

import src.particles.*;

public interface Force {
	// represents the force inflicted on particle A by particle B
	public double[] calculateInteraction(Particle a, Particle b);
}
