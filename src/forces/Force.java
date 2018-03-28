/**
 * Base class for forces. Every force inherits from it.
 */
package forces;

import particles.*;

public interface Force {
	// represents the force inflicted on particle A by particle B
	public double[] calculateInteraction(Particle a, Particle b);
}
