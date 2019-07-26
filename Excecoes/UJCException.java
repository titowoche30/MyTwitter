package Excecoes;

//usuário já cadastrado

public class UJCException extends Exception {
    private String usuario;
    
    public UJCException (String usuario){
        super("Usuário já cadastrado");
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
    
}

