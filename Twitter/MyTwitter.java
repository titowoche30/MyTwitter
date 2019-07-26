package twitter;
import Excecoes.MFPException;
import Excecoes.PDException;
import Excecoes.PEException;
import Excecoes.PIException;
import Excecoes.SIException;
import Excecoes.SRException;
import Excecoes.UJCException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class MyTwitter implements ITwitter {
    private IRepositorioUsuario repositorio;
    
    public MyTwitter(IRepositorioUsuario repositorio){
        this.repositorio = repositorio;
    }
    
    @Override
    public void criarPerfil(Perfil usuario) throws PEException{
        String usuariodoPerfil = usuario.getUsuario();
          try{
             repositorio.cadastrar(usuario);
             try {
                LogTwitter.getInstance().gerarRelatorio(usuariodoPerfil + " cadastrado");
             }catch (IOException ex) {
                System.out.println("Não foi possível gerar relatório");
             }
         }catch (UJCException ex) {
            System.out.println("USUÁRIO " + ex.getUsuario() + " JÁ CADASTRADO");
            throw new PEException(usuariodoPerfil);
          }
      
    }

    @Override
    public void cancelarPerfil(String usuario) throws PDException,PIException {
        Perfil perfil = repositorio.buscar(usuario);
        if(perfil!=null){
            if(perfil.isAtivo()){
                perfil.setAtivo(false);
                try {
                    LogTwitter.getInstance().gerarRelatorio(usuario + " cancelado");
                } catch (IOException ex) {
                    System.out.println("Não foi possível gerar relatório");
                }
            }else
                throw new PDException(usuario);
        }else
            throw new PIException(usuario);
        
    }

    @Override
    public void tweetar(String usuario, String mensagem) throws PIException,PDException,MFPException {
        int numSeguidoresAtivos;
        Perfil perfil;    
        Vector<String> vetorSeguidores = new Vector<>(); 
        Vector<Perfil> vetorPerfildeSeguidores = new Vector<>();
        Vector<Perfil> vetorPerfildeSeguidoresAtivos = new Vector<>();
        
        DateFormat dateF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        
        Tweet tweet = new Tweet(usuario,dateF.format(date) + " | " + mensagem );
        perfil = repositorio.buscar(usuario);
        
        if(mensagem.length()>0 && mensagem.length() <=140){
          if(perfil!=null){
            if(perfil.isAtivo()){
               perfil.addTweet(tweet);                                                                                    //Adicionando na timeline do usuario
               vetorPerfildeSeguidoresAtivos = this.seguidores(usuario);
               numSeguidoresAtivos = this.numeroSeguidores(usuario);
               
               try {
                   LogTwitter.getInstance().gerarRelatorio("Tweet efetuado na timeline do usuario "+perfil.getUsuario() );
                }catch (IOException ex) {
                   System.out.println("Não foi possível gerar relatório");
                }
        
               for (int i = 0; i < numSeguidoresAtivos; i++){                                                                                   
                   vetorPerfildeSeguidoresAtivos.get(i).addTweet(new Tweet(usuario,dateF.format(date) + " | "  + mensagem));    //Adicionando na timeline dos seguidores;
                  try {
                      LogTwitter.getInstance().gerarRelatorio("Tweet efetuado na timeline do usuario: " + vetorPerfildeSeguidoresAtivos.get(i).getUsuario());
                  } catch (IOException ex) {
                      System.out.println("Não foi possível gerar relatório");
                    }
                  }
           
               }else
                    throw new PDException(usuario);
            }else
                 throw new PIException(usuario);
        }else
             throw new MFPException(mensagem); 
    }
   

    
    @Override
    public Vector<Tweet> timeline(String usuario) throws PIException,PDException {
        Perfil perfil = repositorio.buscar(usuario);
        Vector<Tweet> vetordeTweets;
        Vector<Perfil> vetordePerfildosTweets = new Vector<>();
        Vector<Tweet> vetordeTweetsAtivos = new Vector<>();
        
        if(perfil!=null){
           if(perfil.isAtivo()){
               vetordeTweets = perfil.getTimeline();
               
               for (int i = 0; i < vetordeTweets.size(); i++) {                                                 //Tira os tweets dos usuario desativados
                   vetordePerfildosTweets.add(repositorio.buscar(vetordeTweets.get(i).getUsuario()));
                   if(!vetordePerfildosTweets.get(i).isAtivo())
                      vetordeTweets.remove(i);             
               }
               return vetordeTweets;
           }else
               throw new PDException("usuario");
        }else 
           throw new PIException("usuario");
        
       
        
    }

    @Override
    public Vector<Tweet> tweets(String usuario){
        Vector<Tweet> vetorTimeLine = new Vector<>(); 
        try {
            vetorTimeLine=this.timeline(usuario);                                       //Pega a timeline inteira do usuario
        } catch (PIException | PDException ex) {
            if(ex instanceof PIException){
                System.out.println("Perfil " + usuario + " inexistente");
            }else
                System.out.println("Perfil " + usuario + "desativado");
        }
        
        if(vetorTimeLine.isEmpty())
            return vetorTimeLine;
        
        Vector<Tweet> vetordeTweet = new Vector<>();
        
        for (int i = 0; i <vetorTimeLine.size(); i++) 
            if (vetorTimeLine.get(i).getUsuario().equals(usuario))                     //Roda a timeline e verifica quais tweets são do usuario
                vetordeTweet.add(vetorTimeLine.get(i));
        
        return vetordeTweet; 
        
    }

    @Override
    public void seguir(String seguidor, String seguido) throws PIException,PDException,SIException,SRException {
        Perfil perfilSeguidor;
        Perfil perfilSeguido;
        
        perfilSeguidor=repositorio.buscar(seguidor);
        perfilSeguido=repositorio.buscar(seguido);
        
        if(perfilSeguidor == null){
            throw new PIException(seguidor);
        } 

        if(perfilSeguido == null){
            throw new PIException(seguido);
        } 
        
        
        if(perfilSeguidor.isAtivo() && perfilSeguido.isAtivo()){
            if(perfilSeguidor.getUsuario().equals(perfilSeguido.getUsuario()))
                throw new SIException("seguidor");
            else if(!perfilSeguido.getSeguidores().contains(seguidor))
                   perfilSeguido.addSeguidor(seguidor);
                 else
                   throw new SRException(seguidor);
            
        }else if(perfilSeguidor.isAtivo())
                throw new PDException("seguido");
              else
                throw new PDException("seguidor");
        
        try {
            LogTwitter.getInstance().gerarRelatorio(seguidor +" seguiu " + seguido);
        } catch (IOException ex) {
          System.out.println("Não foi possível gerar relatório");
        }
        
    }

    @Override
    public int numeroSeguidores(String usuario) {
        Vector<Perfil> vetordeSeguidoresAtivos;
        try {
            vetordeSeguidoresAtivos = this.seguidores(usuario);
            return vetordeSeguidoresAtivos.size();
        }catch (PIException | PDException ex) {
            if(ex instanceof PIException)
                System.out.println("\r\nPERFIL " + usuario + " INEXISTENTE");  
            else
                System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");
            
            return 0;
        }
        
    }

    @Override
    public Vector<Perfil> seguidores(String usuario) throws PIException,PDException {
        Perfil perfil;
        Vector<String> vetorSeguidores = new Vector<>();
        Vector<Perfil> vetorPerfildeSeguidores = new Vector<>();
        Vector<Perfil> vetorPerfildeSeguidoresAtivos = new Vector<>();
        
        perfil = repositorio.buscar(usuario);
             
        if(perfil!=null){
           if(perfil.isAtivo()){
              vetorSeguidores = perfil.getSeguidores();
              for (int i = 0; i < vetorSeguidores.size(); i++){                                           //Se não tiver seguidores,nem entra no for
                  vetorPerfildeSeguidores.add(repositorio.buscar(vetorSeguidores.get(i)));
                  if(vetorPerfildeSeguidores.get(i).isAtivo())
                     vetorPerfildeSeguidoresAtivos.add(vetorPerfildeSeguidores.get(i)); 
                }
              
              return vetorPerfildeSeguidoresAtivos;
           
            }else
                throw new PDException(usuario);
       }else
            throw new PIException(usuario);
        
    }
    

    public IRepositorioUsuario getRepositorio() {
        return repositorio;
    }
    
}
