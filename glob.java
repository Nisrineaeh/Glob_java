import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class glob{

    //Glob est un modèle utilisant des caractères génériques pour spécifier les noms des fichiers


       public static void main(String[] args) {
        String exec = null;
        String rootPath = "/mon-dossier";
        boolean rm= false;
        String exclude = null;
        List<String> fileExtensions = Arrays.asList(".js", ".mjs", ".cjs");
        // as List renvoie une liste de taille fixe soutenue par un tableau


        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--exec":
                case "-e":
                    if (i + 1 < args.length) {
                        exec = args[++i];
                    }
                    break;
                case "--root":
                case "-r":
                    if (i + 1 < args.length) {
                        rootPath = args[++i];
                    }
                    break;
                case "--rm":
                    rm= true;
                    break;
                case "--exclude":
                    if (i + 1 < args.length) {
                        exclude = args[++i];
                    }
                    break;
            }
        }

          Path startingDir = Paths.get(rootPath);
        // Path interface qui permet de gérer le chemin des fichiers (donne accès à des class et elle à des méthodes inetéressant !)
        try {

            //Files class composait de méthodes statiques qui opèrent sur les fichiers ou répertoires.
            //walkFileTree permet de parcourir larborescence des fichiers 
            //SimpleFileVisitor visite tout les fichiers d'un répertoire

            Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {

                //méthode qui hérite de la classe parent
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileString = file.toString().toLowerCase();
                    //anyMatch renvoie true si des element correspond au prédicat 
                    if (fileExtensions.stream().anyMatch(fileString::endsWith)) {
                        System.out.println(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            //méthode qui gére les exceptions et les erreurs, elle localisera la ligne exacte dans laquelle la méthode a levé l'exception.
            e.printStackTrace();
        }

        try {
            Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().toLowerCase().endsWith(".js")) {
                        System.out.println(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        personalFolder(args);
        delete(args);
        copy(args);
    }

    public static void personalFolder(String[] args){
        
        String rootPath = "/home/rnzaou";  

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--root":
                case "-r":
                    if (i + 1 < args.length) {
                        rootPath = args[++i];
                    }
                    break;
              
            }
        }

        Path startingDir = Paths.get(rootPath);
        try {
            Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    

     public static void delete(String[] args) {

        
        String targetFile = "/tmp";
        //HashSet permet de crée une collection d'objets
        Set<String> filesToExclude = new HashSet<>();
        filesToExclude.add("/tmp/important.txt");
        filesToExclude.add("/tmp/tres-important.txt");

        Path startingDir = Paths.get(targetFile);
        try {
            Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!filesToExclude.contains(file.toString())) {
                        Files.delete(file);
                        System.out.println("Supprimé: " + file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static void copy(String[] args){
        
          String sourceDirectory = "/home/rnzaou/";
        String targetDirectory = "/var/backups/filename.bak"; 

        Path sourceDir = Paths.get(sourceDirectory);
        Path targetDir = Paths.get(targetDirectory);
        try {
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path destination = targetDir.resolve(sourceDir.relativize(file));
                    Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copié: " + file + " -> " + destination);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   

}