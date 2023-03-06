package org.example;

import java.io.File;

public class Folder {

    public static void createFolder() {
        String homeDir2 = System.getProperty("user.home");
        File homeFolder = new File(homeDir2 + "/HOME");
        File devFolder = new File(homeDir2 + "/DEV");
        File testFolder = new File(homeDir2 + "/TEST");
        File binFolder = new File(homeDir2 + "/BIN");

        if(!homeFolder.exists()) {
            homeFolder.mkdirs();
            System.out.println("Folder HOME has been created");
        }
        if(!devFolder.exists()) {
            devFolder.mkdirs();
            System.out.println("Folder DEV has been created");
        }
        if(!testFolder.exists()) {
            testFolder.mkdirs();
            System.out.println("Folder TEST has been created");
        }
        if(!binFolder.exists()) {
            binFolder.mkdirs();
            System.out.println("Folder BIN has been created");
        }
    }
}
