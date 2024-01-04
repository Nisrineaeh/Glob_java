import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;



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

    }
}


    

   
    


    

        





  

