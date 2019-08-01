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
    public static final int DEFAULT_XCOOR = 0;
    public static final int DEFAULT_YCOOR = 25;
    public static final int DEFAULT_SNAKE_SIZE = 5;

    private Thread thread;
    private boolean running;

    private Direction direction = Direction.RIGHT;

    private BodyPart bodyPart;
    private ArrayList<BodyPart> snake;
    private Apple apple = null;

    private Random random;

    private int xCoor = DEFAULT_XCOOR;
    private int yCoor = DEFAULT_YCOOR;
    private int size = DEFAULT_SNAKE_SIZE;

    private int ticks = 0;

    public GamePanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        snake = new ArrayList<BodyPart>();

        random = new Random();

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

            if(checkCollision() || checkEatItself()){
                gameOver();
            }

            ticks = 0;

            bodyPart = new BodyPart(xCoor, yCoor, 10);
            snake.add(bodyPart);

            if(snake.size() > size){
                snake.remove(0);
            }

            if(apple == null){
                int x = random.nextInt(49);
                int y = random.nextInt(49);

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

    private boolean checkCollision() {
        boolean collision = false;

        if(xCoor > 49 || xCoor < 0 || yCoor > 49 || yCoor < 0){
            collision = true;
        }

        return collision;
    }

    private boolean checkEatItself(){
        for(BodyPart part : snake){
            if(part.getxCoor() == xCoor && part.getyCoor() == yCoor){
                return true;
            }
        }
        return false;
    }

    private void gameOver(){
        direction = Direction.RIGHT;
        snake = new ArrayList<BodyPart>();
        apple = null;
        xCoor = DEFAULT_XCOOR;
        yCoor = DEFAULT_YCOOR;
        size = DEFAULT_SNAKE_SIZE;
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
