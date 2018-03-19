
public class Electric extends Force{
	private final double  coulombConst = 1; // TODO calculate the proper value later  
	public Electric(Particle a, Particle b)
	{
		super(a, b);
	}
	
	public double[] calculate()
	{
		double [] forceVector = new double [2];
		double norm2 = (b.x-a.x)*(b.x-a.x)+(b.y-a.y)*(b.y-a.y);
		forceVector[0] = -coulombConst*a.charge*b.charge / (norm2*Math.sqrt(norm2)) * (b.x-a.x); 
		forceVector[1] = -coulombConst*a.charge*b.charge / (norm2*Math.sqrt(norm2)) * (b.y-a.y);
		return forceVector;
	}

}
