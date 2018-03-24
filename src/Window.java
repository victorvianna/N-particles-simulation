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

import javax.swing.JFrame;
 
import src.particles.*;
import src.forces.*;

public class Window extends JFrame {
 
    private ArrayList<Particle> particles = new ArrayList<Particle>(500);
    private ArrayList<Force> forces = new ArrayList<Force>();
    private BufferStrategy bufferstrat = null;
    private Canvas render;    
    public final static int WIDTH = 800, HEIGHT = 800, MAX_SPEED =200, PARTICLE_SIZE = 5;
    private final double DELTA_TIME = 1/600.0; // time interval in seconds
    
 
    public static void main(String[] args) throws InterruptedException
    {               
        Scanner sc = new Scanner(System.in); 
        ///System.out.println("Type in the number of particles you want: ");
        
        int n = 2; ///sc.nextInt();
        Window window = new Window("Particles: ", n);
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
        
        for(int i=0; i<n; i++)
        {
        	//addStaticParticle();
        	addRandomParticle();
        }
	
	forces.add(new ElectricForce());
	forces.add(new GravityForce());
    }
 

    public void runSimulation() throws InterruptedException{
        while(true){
 
            update();
            render();
 
            try {
                Thread.sleep((long) (DELTA_TIME*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    private void update() throws InterruptedException{
        updatePositions();
        updateVelocities();
    }
    
    private void updatePositions() throws InterruptedException {
    	int n = particles.size();
    	PositionUpdater[] posiUpdaters = new PositionUpdater[n];  
    	for(int i=0; i<n; i++)
    		posiUpdaters[i] = new PositionUpdater(particles, i, DELTA_TIME);
    	for(int i=0; i<n; i++)
    		posiUpdaters[i].start();
    	for(int i=0; i<n; i++)
    		posiUpdaters[i].join();
    	
    }
    private void updateVelocities() throws InterruptedException { 
    	int n = particles.size();
    	VelocityUpdater[] velUpdater = new VelocityUpdater[n];  
    	for(int i=0; i<n; i++)
    		velUpdater[i] = new VelocityUpdater(particles, forces, i, DELTA_TIME);
    	for(int i=0; i<n; i++)
    		velUpdater[i].start();
    	for(int i=0; i<n; i++)
    		velUpdater[i].join();
    }
 
    private void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.fillRect(0, 0, render.getWidth(), render.getHeight());
 
                renderParticles(g2d);
 
                g2d.dispose();
             }while(bufferstrat.contentsRestored());
              bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }
 
    void renderParticles(Graphics2D g2d){
        for(int i = 0; i <= particles.size() - 1;i++){
            particles.get(i).render(g2d);
        }
    }
   

    void addStaticParticle(){
        double x, y;
        x = (Math.random()*WIDTH);
        y =  (Math.random()*HEIGHT);
 
        particles.add(new Particle(x,y, 100, PARTICLE_SIZE));
    }
    void addRandomParticle(){
        double x, y, vx, vy;
        x = (Math.random()*WIDTH);
        y =  (Math.random()*HEIGHT);
        vx = (2*Math.random()-1)*MAX_SPEED;
        vy = (2*Math.random()-1)*MAX_SPEED;
	
	int type = (int) (Math.random() * 2);
	type = 0;
	if (type == 0)	 
        	particles.add(new ElectricParticle(x,y,vx, vy,10000, PARTICLE_SIZE, 10000));
	else
		particles.add(new Particle(x,y,vx, vy,10000, PARTICLE_SIZE));
    }
}
