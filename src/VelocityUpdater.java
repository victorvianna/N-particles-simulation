import java.util.ArrayList;

public class VelocityUpdater extends Thread{
	private ArrayList<Particle> particles;
	private int index; // index of the particle we will update
	private final double DELTA_TIME;
	public VelocityUpdater(ArrayList<Particle> particles, int index, double DELTA_TIME)
	{
		this.particles = particles;
		this.index = index;
		this.DELTA_TIME = DELTA_TIME;
	}
	
	public void run()
	{
		int n = particles.size();
		Particle src = particles.get(index);
		double ax = 0, ay=0;
		for(int i=0; i<n; i++)
		{
			if(i!=index)
			{
				Particle dst = particles.get(i);
				// we are assuming m is in units such that G=1 (approx 1.5e10 kg)
				
				double[] gravity = new Gravity(src, dst).calculate();
				ax += gravity[0]/src.mass;  
				ay += gravity[1]/src.mass;
				
				double[] electric = new Electric(src, dst).calculate();
				ax += electric[0]/src.mass;  
				ay += electric[1]/src.mass;
			}
		}
		
		src.vx += ax*DELTA_TIME;
		src.vy += ay*DELTA_TIME;
	}
	
	

}
