package src;

import java.awt.Canvas;
import java.awt.Color;
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
 
import src.particles.*;
import src.forces.*;

public class Window extends JFrame {
 
    private ArrayList< ArrayList<Particle> > particles = new ArrayList<>();
    private ArrayList<Force> forces = new ArrayList<Force>();
    private BufferStrategy bufferstrat = null;
    private Canvas render;    
    public final static int WIDTH = 800, HEIGHT = 800, MAX_SPEED = 2000, PARTICLE_SIZE = 5;
    private final double DELTA_TIME = 1/600.0; // time interval in seconds
    
 
    public static void main(String[] args) throws InterruptedException
    {               
        Scanner sc = new Scanner(System.in); 
        System.out.println("Type in the number of particles you want: ");
        
        int n_particles = sc.nextInt() /* 2 */; 
	
        Window window = new Window("Particles: ", n_particles);
        sc.close();
        
        window.runSimulation();
    }
       
    public Window( String title, int n){
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
 
        setBounds(nWidth-(WIDTH/2), nHeight-(HEIGHT/2), WIDTH, HEIGHT);
        render.setBounds(nWidth-(WIDTH/2), nHeight-(HEIGHT/2), WIDTH, HEIGHT);
 
        add(render);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        render.createBufferStrategy(2);
        bufferstrat = render.getBufferStrategy();
        
	particles.add(new ArrayList<> ());
	particles.add(new ArrayList<> ());

        for(int i=0; i<n; i++)
        {
        	//addStaticParticle();
        	addRandomParticle();
        }
	
	forces.add(new ElectricForce());
	forces.add(new GravityForce());
    }
 

    public void runSimulation() throws InterruptedException {
	final int[] turn = new int[1];
	ExecutorService executor = Executors.newWorkStealingPool();
	Collection<PositionUpdater> positionTasks = positionUpdaterTasks(turn);
	Collection<VelocityUpdater> velocityTasks = velocityUpdaterTasks(turn);

        while(true){
            executor.invokeAll(positionTasks);
	    executor.invokeAll(velocityTasks);
            render(turn);
	    turn[0] = 1 - turn[0];
 
            try {
                Thread.sleep((long) (DELTA_TIME*10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    private Collection<PositionUpdater> positionUpdaterTasks(int[] turn) {
    	int n = particles.get(0).size();
	
    	Collection<PositionUpdater> colTasks = new ArrayList<>(n);
    	for (int i = 0; i < n; i++)
    		colTasks.add(new PositionUpdater(particles, i, DELTA_TIME, turn));
	return colTasks;
    }

    private Collection<VelocityUpdater> velocityUpdaterTasks(int[] turn) { 
    	int n = particles.get(0).size();

    	Collection<VelocityUpdater> velTasks = new ArrayList<>(n);  
    	for(int i = 0; i < n; i++)
    		velTasks.add(new VelocityUpdater(particles, forces, i, DELTA_TIME, turn));
	return velTasks;
    }
 
    private void render(int[] turn){
        do {
            do {
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.fillRect(0, 0, render.getWidth(), render.getHeight());
 
		for (int i = 0; i < particles.get(0).size(); i++) 
		    particles.get(turn[0]).get(i).render(g2d);

                g2d.dispose();
             } while (bufferstrat.contentsRestored());

              bufferstrat.show();
        } while (bufferstrat.contentsLost());
    }
 
    void addRandomParticle(){
        double x, y, vx, vy;
        x = (Math.random()*WIDTH);
        y =  (Math.random()*HEIGHT);
        vx = (2*Math.random()-1)*MAX_SPEED;
        vy = (2*Math.random()-1)*MAX_SPEED;
	
	int type = (int) (Math.random() * 2);
	type = 0;
	if (type == 0) {	 
		Particle p1 = new ElectricParticle(x,y,vx, vy, 10000, PARTICLE_SIZE, 10000);
		Particle p2 = new ElectricParticle(x,y,vx, vy, 10000, PARTICLE_SIZE, 10000);
        	particles.get(0).add(p1);
        	particles.get(1).add(p2);
	}
	else {
		Particle p1 = new Particle(x,y,vx, vy, 10000, PARTICLE_SIZE);
		Particle p2 = new Particle(x,y,vx, vy, 10000, PARTICLE_SIZE);
        	particles.get(0).add(p1);
        	particles.get(1).add(p2);
	}
    }
}
