package com.urbanojvr.jsnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Thread thread;
    private boolean running;

    private Direction direction = Direction.RIGHT;

    private BodyPart bodyPart;
    private ArrayList<BodyPart> snake;
    private Apple apple = null;

    private Random r;

    private int xCoor = 10, yCoor = 10, size = 5;

    private int ticks = 0;

    public GamePanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        snake = new ArrayList<BodyPart>();

        r = new Random();

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
            switch (direction){
                case RIGHT:
                    xCoor ++;
                    break;
                case LEFT:
                    xCoor --;
                    break;
                case UP:
                    yCoor --;
                    break;
                case DOWN:
                    yCoor ++;
            }

            ticks = 0;

            bodyPart = new BodyPart(xCoor, yCoor, 10);
            snake.add(bodyPart);

            if(snake.size() > size){
                snake.remove(0);
            }

            if(apple == null){
                int x = r.nextInt(49);
                int y = r.nextInt(49);

                apple = new Apple(x, y, 10);
            }

            if(apple.getxCoor() == this.xCoor && apple.getyCoor() == this.yCoor){
                size ++;
                apple = null;
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

        for (BodyPart part : snake) {
            part.draw(graphics);
        }

        if(apple != null) apple.draw(graphics);
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

        if(key == KeyEvent.VK_RIGHT && direction != Direction.LEFT){
            direction = Direction.RIGHT;
        }

        if(key == KeyEvent.VK_LEFT && direction != Direction.RIGHT){
            direction = Direction.LEFT;
        }

        if(key == KeyEvent.VK_UP && direction != Direction.DOWN){
            direction = Direction.UP;
        }

        if(key == KeyEvent.VK_DOWN && direction != Direction.UP){
            direction = Direction.DOWN;
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
