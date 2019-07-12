package com.urbanojvr.jsnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Thread thread;
    private boolean running;

    private boolean rigth = true, left = false, up = false, down = false;

    private BodyPart bodyPart;
    private ArrayList<BodyPart> snake;

    private int xCoor = 10, yCoor = 10, size = 5;

    private int ticks = 0;

    public GamePanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        snake = new ArrayList<BodyPart>();

        start();
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() throws InterruptedException {
        running = false;
        thread.join();
    }

    public void tick() {
        if(snake.size() == 0){
            bodyPart = new BodyPart(xCoor, yCoor, 10);
            snake.add(bodyPart);
        }
        ticks++;
        if(ticks > 2500000){
            if(rigth) xCoor++;
            if(left) xCoor--;
            if(up) yCoor--;
            if(down) yCoor++;

            ticks = 0;

            bodyPart = new BodyPart(xCoor, yCoor, 10);
            snake.add(bodyPart);

            if(snake.size() > size){
                snake.remove(0);
            }
        }
    }

    public void paint(Graphics graphics) {
        graphics.clearRect(0, 0, WIDTH, HEIGHT);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < WIDTH / 10; i++) {
            graphics.drawLine(i * 10, 0, i * 10, HEIGHT);
        }

        for (int i = 0; i < HEIGHT / 10; i++) {
            graphics.drawLine(0, i * 10, HEIGHT, i * 10);
        }

        for(int i = 0; i < snake.size(); i++){
            snake.get(i).draw(graphics);
        }

    }

    public void run() {
        while(running){
            tick();
            repaint();
        }
    }

    public void keyTyped(KeyEvent e) {
        while (running){
            tick();
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT && !left){
            rigth = true;
            up = false;
            down = false;
        }

        if(key == KeyEvent.VK_LEFT && !rigth){
            left = true;
            up = false;
            down = false;
        }

        if(key == KeyEvent.VK_UP && !up){
            up = true;
            rigth = false;
            left = false;
        }

        if(key == KeyEvent.VK_DOWN && !down){
            down = true;
            rigth = false;
            left = false;

        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
