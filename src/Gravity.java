
public class Gravity extends Force{
	private final double newtonConst = 1;
	public Gravity(Particle a, Particle b)
	{
		super(a, b);
	}
	public double[] calculate()
	{
		double [] forceVector = new double[2];	
		double norm2 = (b.x-a.x)*(b.x-a.x)+(b.y-a.y)*(b.y-a.y);
		forceVector[0] = newtonConst*a.mass*b.mass / (norm2*Math.sqrt(norm2)) * (b.x-a.x); 
		forceVector[1] = newtonConst*a.mass*b.mass / (norm2*Math.sqrt(norm2)) * (b.y-a.y);
		return forceVector;
	}
}
