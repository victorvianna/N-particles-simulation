/**
 * Inherits from base Force class and is associated with an ElectricParticle class. 
 * If we try to calculate the electric force with a non-electric particle,
 * the calculatedInteraction is 0, as such particle has no charge
 */
package forces;

import particles.*;

public class ElectricForce implements Force {
	private final static double COULOMB_CONST = 1;

	@Override
	public double[] calculateInteraction(Particle pa, Particle pb) {
		double[] forceVector = new double[2];

		if (!(pa instanceof ElectricParticle) || !(pb instanceof ElectricParticle))
			return forceVector;

		ElectricParticle a = (ElectricParticle) pa;
		ElectricParticle b = (ElectricParticle) pb;

		double distance = Math.hypot(a.x - b.x, a.y - b.y);
		double force = -COULOMB_CONST * a.electricCharge * b.electricCharge / (distance * distance);

		forceVector[0] = force * (b.x - a.x) / distance;
		forceVector[1] = force * (b.y - a.y) / distance;

		return forceVector;
	}

}
