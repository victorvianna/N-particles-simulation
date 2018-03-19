
public abstract class Force {
	// represents the force inflicted on particle A by particle B
	Particle a, b;
	Force(Particle a, Particle b){
		this.a = a; this.b = b;
	}
	abstract public double[] calculate();
}
