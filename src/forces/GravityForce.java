package forces;

import particles.*;

public class GravityForce implements Force {
	private final static double NEWTON_CONST = 1;

	@Override
	public double[] calculateInteraction(Particle a, Particle b) {
		double[] forceVector = new double[2];

		double distance = Math.hypot(a.x - b.x, a.y - b.y);
		double force = NEWTON_CONST * a.mass * b.mass / (distance * distance);

		forceVector[0] = force * (b.x - a.x) / distance;
		forceVector[1] = force * (b.y - a.y) / distance;

		return forceVector;
	}

}
