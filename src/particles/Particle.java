/**
 * Base class for a particle. Every particle inherits from it.
 * Every particle must have a mass, so that we can compute accelerations.
 * This is why there's no need for a class GravitationalParticle
 */
package particles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle {
	public double x, y, vx, vy, mass;
	private int radius; // radius of rendered particle
	private Color color;

	public Particle(double x, double y, double vx, double vy, double mass, int radius, Color c) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;
		this.radius = radius;
		this.color = c;
	}

	public Particle(double x, double y, double mass, int radius) {
		this(x, y, 0, 0, mass, radius, Color.blue);
	}

	public Particle(double x, double y, double vx, double vy, double mass, int radius) {
		this(x, y, vx, vy, mass, radius, Color.blue);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), radius, radius);
		g2d.dispose();
	}
}
