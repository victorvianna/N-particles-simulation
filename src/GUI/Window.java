package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import particles.*;
import tasks.*;
import forces.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Particle>> particles = new ArrayList<>();
	private ArrayList<Force> forces = new ArrayList<Force>();
	private BufferStrategy bufferstrat = null;
	private Canvas render;
	public final static int WIDTH = 800, HEIGHT = 800, MAX_SPEED = 2000, PARTICLE_SIZE = 5;
	private final double DELTA_TIME = 1 / 600.0; // time interval in seconds

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Type in the number of particles you want: ");
		int n_particles = sc.nextInt();
		sc.close();
		Window window = new Window("Particles: ", n_particles);
		window.runSimulation();
	}

	public Window(String title, int n) {
		super();
		setTitle(title);
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

		particles.add(new ArrayList<>());
		particles.add(new ArrayList<>());

		for (int i = 0; i < n; i++) {
			addRandomParticle();
		}

		forces.add(new ElectricForce());
		forces.add(new GravityForce());
	}

	public void runSimulation() throws InterruptedException {
		final int[] turn = new int[1]; turn[0] =0;
		ExecutorService executor = Executors.newWorkStealingPool();
		Collection<Task> tasks = allUpdateTasks(turn);

		while (true) {
			executor.invokeAll(tasks);
			render(turn);
			turn[0] = 1 - turn[0];

			try {
				Thread.sleep((long) (DELTA_TIME * 10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Collection<Task> allUpdateTasks(int[] turn) {
		int n = particles.get(0).size();
		Collection<Task> tasks = new ArrayList<>(2 * n);
		for (int i = 0; i < n; i++)
			tasks.add(new PositionUpdate(particles, i, DELTA_TIME, turn));
		for (int i = 0; i < n; i++)
			tasks.add(new VelocityUpdate(particles, forces, i, DELTA_TIME, turn));
		return tasks;
	}

	private void render(int[] turn) {
		do {
			do {
				Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, render.getWidth(), render.getHeight());

				for (int i = 0; i < particles.get(0).size(); i++)
					renderParticle(particles.get(turn[0]).get(i),g2d);

				g2d.dispose();
			} while (bufferstrat.contentsRestored());

			bufferstrat.show();
		} while (bufferstrat.contentsLost());
	}

	public void renderParticle(Particle p, Graphics g) {
		double x = p.x, y = p.y; int radius = p.getRadius();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(p.getColor());
		g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), radius, radius);
		g2d.dispose();
	}
	
	void addRandomParticle() {
		double x, y, vx, vy, mass, radius, charge;
		x = (Math.random() * WIDTH);
		y = (Math.random() * HEIGHT);
		vx = (2 * Math.random() - 1) * MAX_SPEED;
		vy = (2 * Math.random() - 1) * MAX_SPEED;
		mass = 10000;
		charge = (Math.random()-0.5)*10000;

		int type = (int) (Math.random() * 2);
		if (type == 0) {
			Particle p1 = new ElectricParticle(x, y, vx, vy, mass , PARTICLE_SIZE, charge);
			Particle p2 = new ElectricParticle(x, y, vx, vy, mass, PARTICLE_SIZE, charge);
			particles.get(0).add(p1);
			particles.get(1).add(p2);
		} else {
			Particle p1 = new Particle(x, y, vx, vy, mass, PARTICLE_SIZE);
			Particle p2 = new Particle(x, y, vx, vy, mass, PARTICLE_SIZE);
			particles.get(0).add(p1);
			particles.get(1).add(p2);
		}
	}
}
