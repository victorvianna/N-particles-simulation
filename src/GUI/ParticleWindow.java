package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import particles.*;
import tasks.*;
import forces.*;

public class ParticleWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Particle>> particles;
	private ArrayList<Force> forces = new ArrayList<Force>();
	private BufferStrategy bufferstrat = null;
	private Canvas render;
	public final static int WIDTH = 800, HEIGHT = 800, MAX_SPEED = 500, PARTICLE_SIZE = 5;
	public static boolean bouncingEnabled, initialSpeedEnabled;
	private final double DELTA_TIME = 1 / 600.0; // time interval in seconds

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("Welcome to the simulation!");
		Scanner sc = new Scanner(System.in);
		String ans;
		do {
			System.out.println("Do you wish to enable wall-bouncing?(y/n)");
			ans = sc.next();
			if (!ans.equals("y") && !ans.equals("n"))
				System.out.println("Invalid answer");
		} while (!ans.equals("y") && !ans.equals("n"));
		ParticleWindow.bouncingEnabled = ans.equals("y");

		do {
			System.out.println("Do you wish particles with initial speed?(y/n)");
			ans = sc.next();
			if (!ans.equals("y") && !ans.equals("n"))
				System.out.println("Invalid answer");
		} while (!ans.equals("y") && !ans.equals("n"));
		initialSpeedEnabled = ans.equals("y");

		ArrayList<ArrayList<Particle>> particlesRead = readParticles(sc, initialSpeedEnabled);
		
		sc.close();

		ParticleWindow window = new ParticleWindow(particlesRead);
		window.runSimulation();
	}
	
	public static ArrayList<ArrayList<Particle>> readParticles(Scanner sc, boolean initialSpeedEnabled) {
		ArrayList<ArrayList<Particle>> particles = new ArrayList<>();
		particles.add(new ArrayList<Particle>());
		particles.add(new ArrayList<Particle>());
		boolean finishedInput = false;
		while (!finishedInput) {
			String ans;
			do {
				System.out.println("Add a particle?(y/n)");
				ans = sc.next();
				if (!ans.equals("y") && !ans.equals("n"))
					System.out.println("Invalid answer");
			} while (!ans.equals("y") && !ans.equals("n"));
			if (ans.equals("y")) {
				double x, y, vx, vy, mass, charge;
				x = (Math.random() * WIDTH);
				y = (Math.random() * HEIGHT);
				if (initialSpeedEnabled) {
					vx = (Math.random() * WIDTH);
					vy = (Math.random() * HEIGHT);
				} else {
					vx = 0;
					vy = 0;
				}

				System.out.println("Mass?");
				mass = sc.nextDouble();
				System.out.println("Charge?");
				charge = sc.nextDouble();

				if (charge == 0) {
					particles.get(0).add(new Particle(x, y, vx, vy, mass, PARTICLE_SIZE));
					particles.get(1).add(new Particle(x, y, vx, vy, mass, PARTICLE_SIZE));
				} else {
					particles.get(0).add(new ElectricParticle(x, y, vx, vy, mass, PARTICLE_SIZE, charge));
					particles.get(1).add(new ElectricParticle(x, y, vx, vy, mass, PARTICLE_SIZE, charge));
				}

			} else if (particles.get(0).size() == 0)
				System.out.println("Add at least 1 particle");
			else
				finishedInput = true;
		}
		return particles;
	}

	public ParticleWindow(ArrayList<ArrayList<Particle>> readParticles) {
		super();
		particles = readParticles;
		setTitle("Simulation");
		setIgnoreRepaint(true);
		setResizable(false);

		render = new Canvas();
		render.setIgnoreRepaint(true);
		int nHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int nWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		nHeight /= 2;
		nWidth /= 2;

		setBounds(nWidth - (WIDTH / 2), nHeight - (HEIGHT / 2), WIDTH, HEIGHT);
		render.setBounds(nWidth - (WIDTH / 2), nHeight - (HEIGHT / 2), WIDTH, HEIGHT);

		add(render);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		render.createBufferStrategy(2);
		bufferstrat = render.getBufferStrategy();

		forces.add(new ElectricForce());
		forces.add(new GravityForce());
	}

	public void runSimulation() throws InterruptedException {
		final int[] turn = new int[1];
		turn[0] = 0;
		ExecutorService executor = Executors.newWorkStealingPool();
		Collection<Task> tasks = new ArrayList<Task>();
		if (!bouncingEnabled)
			addAllTasks(tasks, turn);
		else
			addPositionTasks(tasks, turn);

		while (true) {
			executor.invokeAll(tasks);
			if (bouncingEnabled) {
				// if bouncing is enabled, we need to first update positions
				// and then velocities
				addVelocityTasks(tasks, turn);
				executor.invokeAll(tasks);
			}
			render(turn);
			turn[0] = 1 - turn[0];

			try {
				Thread.sleep((long) (DELTA_TIME * 10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void addAllTasks(Collection<Task> tasks, int[] turn) {
		addPositionTasks(tasks, turn);
		addVelocityTasks(tasks, turn);
	}

	private void addPositionTasks(Collection<Task> tasks, int[] turn) {
		int n = particles.get(0).size();
		for (int i = 0; i < n; i++)
			tasks.add(new PositionUpdate(particles, i, DELTA_TIME, turn));
	}

	private void addVelocityTasks(Collection<Task> tasks, int[] turn) {
		int n = particles.get(0).size();
		for (int i = 0; i < n; i++)
			tasks.add(new VelocityUpdate(particles, forces, i, DELTA_TIME, turn));
	}

	private void render(int[] turn) {
		do {
			do {
				Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, render.getWidth(), render.getHeight());

				for (int i = 0; i < particles.get(0).size(); i++)
					renderParticle(particles.get(turn[0]).get(i), g2d);

				g2d.dispose();
			} while (bufferstrat.contentsRestored());

			bufferstrat.show();
		} while (bufferstrat.contentsLost());
	}

	public void renderParticle(Particle p, Graphics g) {
		double x = p.x, y = p.y;
		int radius = p.getRadius();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(p.getColor());
		g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), radius, radius);
		g2d.dispose();
	}

	

}
