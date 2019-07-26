package Excecoes;

public class CNPJException extends Exception {
    private String cnpj;
    
    public CNPJException (String cnpj){
       super("CNPJ Inválido");
       this.cnpj = cnpj;     
    }

    public String getCnpj() {
        return cnpj;
    }
    
    
}
