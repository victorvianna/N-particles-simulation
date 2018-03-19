import java.util.ArrayList;

public class PositionUpdater extends Thread{
	private ArrayList<Particle> particles;
	private int index; // index of the particle we will update
	private final double DELTA_TIME;
	public PositionUpdater(ArrayList<Particle> particles, int index, double DELTA_TIME)
	{
		this.particles = particles;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public void run()
	{
		particles.get(index).x += particles.get(index).vx*DELTA_TIME; 
		particles.get(index).y += particles.get(index).vy*DELTA_TIME;
	}
	
	

}
