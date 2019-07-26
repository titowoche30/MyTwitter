package Excecoes;

//perfil já existente

public class PEException extends Exception{
     private String usuario;
    
    public PEException (String usuario){
        super("Perfil já existente");
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}

    
