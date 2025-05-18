package org.example;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        startProgram();
    }

    public static void startProgram(){
        try {
            new GameContainer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}