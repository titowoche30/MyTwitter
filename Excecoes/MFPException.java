package Excecoes;

//mensagem fora do padrão

public class MFPException extends Exception{
    private String mensagem;
    
    public MFPException(String mensagem){
        super("Mensagem fora do padrão");
        this.mensagem = mensagem;
    }

    public String Mensagem() {
        return mensagem;
    }
    
    
    
    
}
