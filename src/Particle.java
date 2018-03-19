import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
 
public class Particle {
 
    public double x, y, vx, vy, mass; // we are assuming m is in units such that G=1 (approx 1.5e10 kg)
    private int size; // size of rendered particle
    private Color color;
 
    
    public Particle(double x, double y, double vx, double vy, double mass, int size, Color c){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.size = size;
        this.color = c;
    }
    
    public Particle(double x, double y, double mass, int size){
    	this(x, y, 0, 0, mass, size, Color.blue);
    }
     
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        int _x = (int) x, _y = (int) y;
        g2d.fillRect(_x-(size/2), _y-(size/2), size, size);
        g2d.dispose();
    }
    
    
 
}