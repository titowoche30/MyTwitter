package twitter;
import Excecoes.UJCException;
import Excecoes.UNCException;
import java.util.Vector;
import java.util.Scanner;

public class RepositorioUsuario implements IRepositorioUsuario {
    
    public Vector<Perfil> vetordePerfil = new Vector<>();
    private static RepositorioUsuario uniqueInstance = null;
    
    private RepositorioUsuario(){}
   
    public static RepositorioUsuario getInstance(){
       if(uniqueInstance==null)
           uniqueInstance = new RepositorioUsuario();
       
       return uniqueInstance;
   }
    
    
    @Override
    public void cadastrar(Perfil usuario) throws UJCException {
        String stringUsuario = usuario.getUsuario();
        vetordePerfil.add(usuario);
       
        for (int i = 1; i < vetordePerfil.size(); i++)                          //Se não tiver nenhum,ele nem entra no for
            if(vetordePerfil.get(i-1).getUsuario().equals(stringUsuario)){
                vetordePerfil.remove(i-1);
                throw new UJCException(stringUsuario);
            }
        
    }

    @Override
    public Perfil buscar(String usuario) {
        boolean achou = false;
        Perfil perfil = null;
        
        for(int i = 0;i<vetordePerfil.size();i++){
            if(vetordePerfil.get(i).getUsuario().equals(usuario)){
                achou = true;
                perfil = vetordePerfil.get(i);
            }     
        }
        
        if(achou)
          return perfil;  
        else
          return null;  
       
    }
    
    @Override
    public void atualizar(Perfil usuario) throws UNCException {      
        Scanner in = new Scanner(System.in,"latin1");
        final char a = '@';
        Perfil perfil = usuario;
        PessoaFisica perfilFisica;
        PessoaJuridica perfilJuridica;
        String novoUsuario,novoCPF,novoCNPJ,novaPessoa,novoEstado;
        
        if(this.buscar(perfil.getUsuario())!=null){
            
            if(perfil instanceof PessoaFisica){
                perfilFisica = (PessoaFisica) perfil;
                System.out.println("Digite o novo estado: ");
                novoEstado = in.nextLine();
                if(novoEstado.equalsIgnoreCase("True") || novoEstado.equalsIgnoreCase("Ativado"))
                  perfilFisica.setAtivo(true);
                else if(novoEstado.equalsIgnoreCase("False") || novoEstado.equalsIgnoreCase("Desativado"))
                  perfilFisica.setAtivo(false);
            
                System.out.println("Digite o novo usuario: ");
                novoUsuario = in.nextLine();
            
                if(a == novoUsuario.charAt(0) ){}
                else  novoUsuario = a + novoUsuario;
            
                perfilFisica.setUsuario(novoUsuario);
            
                System.out.println("Digite o novo CPF: ");
                novoCPF = in.nextLine();
                perfilFisica.setCpf(novoCPF);
            }else if(perfil instanceof PessoaJuridica){
                perfilJuridica = (PessoaJuridica) perfil;
                System.out.println("Digite o novo estado: ");
                novoEstado = in.nextLine();
            if(novoEstado.equals("true") || novoEstado.equals("ativado"))
                perfilJuridica.setAtivo(true);
            else if(novoEstado.equals("false") || novoEstado.equals("desativado"))
                perfilJuridica.setAtivo(false);
            
            System.out.println("Digite o novo usuario: ");
            novoUsuario = in.nextLine();
            perfilJuridica.setUsuario(novoUsuario);
            
            System.out.println("Digite o novo CNPJ: ");
            novoCNPJ = in.nextLine();
            perfilJuridica.setCnpj(novoCNPJ);
            } else
               System.out.println("Tipo de pessoa inválido");
        
        }else
            throw new UNCException(perfil.getUsuario());
        
    } 

    @Override
    public Vector<Perfil> getVetordePerfil() {
        return vetordePerfil;
    }
    
    
    
}
