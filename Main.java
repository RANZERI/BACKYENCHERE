import java.util.*;
import java.io.*; 
import java.nio.file.Files;
public class Main {

    public static void main(String[] args) {
        try{
            File b =new File("serveur/");
            File[] tab=b.listFiles();
            byte[][] pae=new byte[tab.length][];
            List<String> encodedString=new ArrayList<>();
            for(int i=0;i<tab.length;i++){
                pae[i] = Files.readAllBytes(tab[i].toPath());   
                System.out.println(Base64.getEncoder().encodeToString(pae[i]));
                System.out.println("");
                System.out.println("");
            }
        }
        catch(Exception aza){
            aza.printStackTrace();

        }
    }

}
