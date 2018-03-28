/**
 * Adds charge property to basic Particle, so that we can compute Electric Forces
 */
package particles;

import java.awt.Color;

public class ElectricParticle extends Particle {
	public double electricCharge;
	private final Color positiveColor = Color.blue;
	private final Color negativeColor = Color.green;

	public ElectricParticle(double x, double y, double vx, double vy, double mass, int radius, double electricCharge) {
		super(x, y, vx, vy, mass, radius);
		this.electricCharge = electricCharge;
		if (electricCharge > 0)
			this.color = positiveColor;
		else if (electricCharge < 0)
			this.color = negativeColor;
	}

	public ElectricParticle(double x, double y, double mass, int radius, double electricCharge) {
		this(x, y, 0, 0, mass, radius, electricCharge);
	}

	public ElectricParticle(double x, double y, double vx, double vy, double mass, int radius, Color color,
			double electricCharge) {
		this(x, y, vx, vy, mass, radius, electricCharge);
		this.color = color;
	}

}
