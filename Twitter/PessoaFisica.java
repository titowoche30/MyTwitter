package twitter;
import Excecoes.CPFException;
import java.util.InputMismatchException;


public class PessoaFisica extends Perfil {
    private String CPF;
 
    public PessoaFisica(String usuario,String CPF){
        super(usuario);
	this.CPF=CPF;
    }

    public String getCpf() {
        return CPF;
    }

    public void setCpf(String CPF) {
        this.CPF = CPF;
    }
    
    
    public boolean isCPF() throws CPFException {
        String CPF = this.CPF;
        
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||               // considera-se erro CPF's formados por uma sequencia de numeros iguais
             CPF.equals("22222222222") || CPF.equals("33333333333") ||
             CPF.equals("44444444444") || CPF.equals("55555555555") ||
             CPF.equals("66666666666") || CPF.equals("77777777777") ||
             CPF.equals("88888888888") || CPF.equals("99999999999") ||
             (CPF.length() != 11))
       
             return false;

        char digito_10, digito_11;
        int aux, i, dv, num, peso;

                                                                                
        try{                                                                             //Calculo do primeiro digito verificador
             aux = 0;
             peso = 10;
      
             for (i=0; i<9; i++) {                      
                 num = (int)(CPF.charAt(i) - 48);                                        //Converte o i-esimo caractere do CPF em numero        
                 aux = aux + (num * peso);
                 peso--;
            }  

            dv = 11 - (aux % 11);
      
            if ((dv == 10) || (dv == 11))
                digito_10 = '0';
            else 
                 digito_10 = (char)(dv + 48);                                            // converte no respectivo caractere numerico

      
      
            aux = 0;                                                                    //Calculo do segundo digito verificador
            peso = 11;
      
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                aux = aux + (num * peso);
                peso--;
             }

            dv = 11 - (aux % 11);
      
            if ((dv == 10) || (dv == 11))
                digito_11 = '0';
           else 
                digito_11 = (char)(dv + 48);

             if ((digito_10 == CPF.charAt(9)) && (digito_11 == CPF.charAt(10)))        // Verifica se os digitos calculados conferem com os digitos informados.
                return true;          
             else {
                 throw new CPFException(CPF);
             }
      } catch (InputMismatchException erro) {
             return false;
    }
        
  }

    public String imprimeCPF(String CPF) {
        
    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
          CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
  }
    
}
