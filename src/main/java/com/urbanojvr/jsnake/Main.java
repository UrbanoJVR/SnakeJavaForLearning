package com.urbanojvr.jsnake;

//https://www.youtube.com/watch?v=91a7ceECNTc
//http://zetcode.com/tutorials/javagamestutorial/snake/
//https://github.com/GrenderG/JSnake/blob/master/src/net/moralesblog/GamePanel.java
//https://www.learnjavacoding.com/definitions/snake/
//A futuro buscar LibGDX
//Buena reestructuración https://codereview.stackexchange.com/questions/129719/java-snake-game

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        new Main();
    }

    public Main(){
        JFrame frame = new JFrame();

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("UrbanoJVR java snake");
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }

}

