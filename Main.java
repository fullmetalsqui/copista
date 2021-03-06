package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int countFiles = 0;
    private static int countFilesForCopy = 0;
    private static File directoryTo = null;
    private static int fileSizeForCopy;


    public static void main(String[] args) {

        List<File> fileList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter root directory, for example \"D:\\\\\"");
            File root = new File(br.readLine());
            System.out.println("Enter out directory, for example \"D:\\\\foto\\\"");
            directoryTo = createDirectory(br.readLine());
            System.out.println("what size files will we copy in kilobytes?");
            fileSizeForCopy = Integer.parseInt(br.readLine());
            scanner(root, fileList);
        }catch (IOException e){
            System.out.println("We have problem, Houston");
        }

        for (File file : fileList){
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("Found " + countFiles + " files.");
        System.out.println("Found " + countFilesForCopy + " files ready for copy.");
    }

    private static void scanner (File rootFile, List<File> fileList) throws IOException {
        if (rootFile.isDirectory()){
            System.out.println("searching at: " + rootFile.getAbsolutePath());
            File[] directoryFile = rootFile.listFiles();
            if (directoryFile != null){
                for (File file : directoryFile){
                    if (file.isDirectory()){
                        scanner(file, fileList);
                    }
                    else {
                        if ( file.getName().toLowerCase().endsWith(".jpg")){
                            fileList.add(file);
                            countFiles++;
                            if (file.length()/1024 > fileSizeForCopy){
                                countFilesForCopy++;
                                /*if (!directoryTo.isDirectory()){
                                    directoryTo.createNewFile();
                                }*/
                                File fileTo = new File(directoryTo.getPath()+ File.separator + file.getName());
                                copyFiles(file, fileTo);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void copyFiles(File from, File to) throws IOException {
        to.createNewFile();
        Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private static File createDirectory(String path){
        File directory = new File(path);
        if (!directory.exists()){
            if (directory.mkdirs()){
                System.out.println("Directory " + directory.getAbsolutePath()+ " created");
            }   else {
                System.out.println("Directory " + directory.getAbsolutePath()+ "didn't create");
            }
        }   else {
            System.out.println("Directory " + directory.getAbsolutePath()+ "always was");
        }
        return  directory;
    }
}
