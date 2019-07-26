package Excecoes;


public class CPFException extends Exception {
    private String cpf;
    
    public CPFException (String cpf){
       super("CPF Inválido");
       this.cpf = cpf;     
    }

    public String getCpf() {
        return cpf;
    }
    
    
}
