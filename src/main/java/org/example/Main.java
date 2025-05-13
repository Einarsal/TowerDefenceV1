package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        startProgram("Pe1nabDefence");
    }

    public static void startProgram(String title){
        Runnable thread = () ->{
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
            }
        };
        thread.run();
    }
}