package twitter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTwitter {
   private static LogTwitter uniqueInstance = null;
    
   private LogTwitter(){
   }
   
   public static LogTwitter getInstance(){
       if(uniqueInstance==null)
           uniqueInstance = new LogTwitter();
       
       return uniqueInstance;
   }
    
    public void gerarRelatorio(String operacao) throws IOException{
        File dir = new File("log");
   
        if(!dir.isDirectory())
           dir.mkdir();
        
        File arq = new File(dir,"logtwitter.txt");
        
        DateFormat dateF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date();
        
        String textoData = dateF.format(d)+ " | " + operacao;
        
        try{  
             FileWriter out = new FileWriter(arq,true);
             out.write(textoData+"\r\n");
             out.close();
            
         }catch(FileNotFoundException e){
             throw new IOException();
         }
        
       
    }
}