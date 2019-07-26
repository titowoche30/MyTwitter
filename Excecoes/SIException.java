package Excecoes;

//seguidor invalido

public class SIException extends Exception {
    private String usuario;
    
    public SIException (String usuario){
        super("Seguidor inválido");
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
