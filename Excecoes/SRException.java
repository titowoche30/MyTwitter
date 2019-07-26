package Excecoes;

//seguidor repetido

public class SRException extends Exception {
    private String usuario;
    
    public SRException (String usuario){
        super("Seguidor repetido");
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
