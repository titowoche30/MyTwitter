package Excecoes;

//perfil inexistente

public class PIException extends Exception{
    private String usuario;                                                                                                      
    
    public PIException (String usuario){
        super("Perfil inexistente");
        this.usuario = usuario;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
   
    
}
