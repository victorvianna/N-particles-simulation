/**
 * Base class for a particle. Every particle inherits from it.
 * Every particle must have a mass, so that we can compute accelerations.
 * This is why there's no need for a class GravitationalParticle
 */
package particles;
import java.awt.Color;

public class Particle {
	public double x, y, vx, vy, mass;
	protected final int radius; // radius of rendered particle
	protected Color color; //color of rendered particle
	protected final Color defaultColor = Color.red; //color of neutral charge particle 

	public Particle(double x, double y, double vx, double vy, double mass, int radius) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;
		this.radius = radius;
		color = defaultColor;
	}
	public Particle(double x, double y, double mass, int radius) {
		this(x, y, 0, 0, mass, radius);
	}
	
	public Particle(double x, double y, double vx, double vy, double mass, int radius, Color c) {
		this(x, y, vx, vy, mass, radius);
		this.color = c;
	}
	public Color getColor() {
		return color;
	}
	
	public int getRadius() {
		return radius;
	}

	

	

	
}
