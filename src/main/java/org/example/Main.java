package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Folder.createFolder();
        MoveFile.save();

        try {
            MoveFile.check();
        } catch (IOException e) {
            System.out.println("An I/O error occurred: : " + e.getMessage());
        } catch (InterruptedException e){
            System.out.println("Thread has been interrupted: " + e.getMessage());
        }

    }
}