import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
 
public class Particle {
 
    public double x, y, vx, vy, mass, charge; // we are assuming m is in units such that G=1 (approx 1.5e10 kg)
    private int radius; // radius of rendered particle
    private Color color;
 
    
    public Particle(double x, double y, double vx, double vy, double mass, double charge, int radius, Color c){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.charge = charge;
        this.radius = radius;
        this.color = c;
    }
    
    public Particle(double x, double y, double mass, int radius){
    	this(x, y, 0, 0, mass, 0, radius, Color.blue);
    }
    public Particle(double x, double y, double vx, double vy, double mass, int radius){
    	this(x, y, vx, vy, mass, 0, radius, Color.blue);
    }
     
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.fillRect(x-(radius/2), y-(radius/2), radius, radius);
        g2d.dispose();
    }
    
    
 
}
