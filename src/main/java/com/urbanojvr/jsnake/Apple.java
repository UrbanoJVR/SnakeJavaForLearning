package com.urbanojvr.jsnake;

import java.awt.*;

public class Apple {

    private int xCoor, yCoor, width, height;

    public Apple(int xCoor, int yCoor, int tileSize){
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.width = this.height = tileSize;
    }

    public void draw(Graphics graphics){
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(xCoor * width, yCoor * height, width, height);
    }

    public int getxCoor() {
        return xCoor;
    }

    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
