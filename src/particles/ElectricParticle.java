package src.particles;

import java.awt.Color;

public class ElectricParticle extends Particle {
	public double electricCharge;
	
	public ElectricParticle (double x, double y, double vx, double vy, double mass, int radius, Color color, double electricCharge) {
		super(x, y, vx, vy, mass, radius, color);
		this.electricCharge = electricCharge;
	}

	public ElectricParticle (double x, double y, double mass, int radius, double electricCharge) {
		this(x, y, 0, 0, mass, radius, Color.blue, electricCharge);
	}

	public ElectricParticle (double x, double y, double vx, double vy, double mass, int radius, double electricCharge) {
		this(x, y, vx, vy, mass, radius, Color.blue, electricCharge);
	}
}
