package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class MoveFile {
    public static void check() throws IOException, InterruptedException {

        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path destDir = Paths.get(System.getProperty("user.home"));
        Path homeDir = Paths.get(System.getProperty("user.home")+"/HOME");
        homeDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

        System.out.println("watching the HOME folder waiting for new files to be added");

        while (true) {
            WatchKey key = watcher.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    Path fileName = pathEvent.context();
                    String destiny = extension(pathEvent);

                    if(!fileName.toString().equals("count.txt")) {
                        Path source = homeDir.resolve(fileName);
                        Path destination = destDir.resolve(destiny).resolve(fileName);
                        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File moved: " + source + " to: " + destination);
                        save();
                    }
                }
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    public static void save() {

        int countTEST = getCount("TEST");
        int countDEV = getCount("DEV");
        int countBIN = getCount("BIN");

        try {
            File resultFile = new File(System.getProperty("user.home") + "/HOME/count.txt");
            FileWriter writer = new FileWriter(resultFile);
            writer.write("The number of files in the folder TEST: " + countTEST + "\n");
            writer.write("The number of files in the folder DEV: " + countDEV + "\n");
            writer.write("The number of files in the folder BIN: " + countBIN + "\n");

            writer.close();
            System.out.println("The result was written to the RESULT.txt file");
        } catch (IOException e) {
            System.out.println("An I/O error occurred: " + e.getMessage());
        }
    }


    private static int getCount(String name) {
        File folder = new File(System.getProperty("user.home") + "/"+name);
        int count = 0;
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    private static String extension(WatchEvent<Path> pathEvent) {
        String destiny;
        if(pathEvent.context().toString().endsWith(".jar")){
            if(LocalDateTime.now().getHour()%2==0){
                destiny="DEV";
            } else {
                destiny="TEST";
            }
        } else if (pathEvent.context().toString().endsWith(".xml")){
            destiny="DEV";
        } else {
            destiny="BIN";
        }
        return destiny;
    }
}
