package twitter;
import Excecoes.MFPException;
import Excecoes.PDException;
import Excecoes.PEException;
import Excecoes.PIException;
import Excecoes.SIException;
import Excecoes.SRException;
import Excecoes.UNCException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class Menu {
    private MyTwitter myTwitter;
    
    public Menu(MyTwitter myTwitter){
        this.myTwitter = myTwitter;
    }
    
    public void main(){
        Scanner in = new Scanner(System.in,"latin1");
        byte dig = 0;
        System.out.println("--------------MY TWITTER-------------------");
        System.out.println("(1) - CADASTRAR\r\n(2) - BUSCAR\r\n(3) - ATUALIZAR\r\n(4) - VER PERFIS ATIVOS\r\n(5) - IR PARA O TWITTER");
        try{
            dig = in.nextByte();
        }catch(InputMismatchException ex){
            System.out.println("DÍGITO INVÁLIDO");
            this.main();
        }
        
        switch(dig){
            case 1:{
                     System.out.println("");
                     System.out.println("\t\t\t1) CADASTRAR");
                     this.cadastro();
                     break;
                }
            case 2:{ 
                    System.out.println("");
                    System.out.println("\t\t\t2) BUSCAR");
                    try { 
                        this.busca();
                    } catch (PIException ex) {
                        System.out.println("PERFIL " + ex.getUsuario() + " INEXISTENTE");
                        this.main();
                    }
                    break;
                }
            case 3:{ 
                    System.out.println("");
                    System.out.println("\t\t\t3) ATUALIZAR");
                    this.atualiza(); 
                    break;
                }
            case 4:{
                     System.out.println("");
                     System.out.println("\t\t\t4) VER PERFIS ATIVOS");
                     this.verPerfis();
                     this.main();
                     break;
                }
            case 5:{
                    System.out.println("");
                    System.out.println("\t\t\t5) TWITTER");
                    try {
                        this.irTwitter();
                    } catch (PIException ex) {
                        System.out.println("PERFIL " + ex.getUsuario() + " INEXISTENTE");
                        this.main();
                    }
                    break;
                }
            
            default: System.out.println("DÍGITO INVÁLIDO"); this.main(); break;
        }
    }
    
    public void cadastro() {
        Scanner in = new Scanner(System.in,"latin1");
        String usuario,pessoa,CPF,CNPJ;
        final char a = '@';
        PessoaFisica perfilFisica = new PessoaFisica("aaaa","06952894381");
        PessoaJuridica perfilJuridica = new PessoaJuridica("aaaa","132");
        System.out.println("Digite o nome de usuário: ");
        usuario=in.nextLine();
        if(a == usuario.charAt(0)){}
        else  usuario = a + usuario;
        
        System.out.println("Digite o tipo de pessoa: ");
        pessoa=in.nextLine();
        if(pessoa.equalsIgnoreCase("Fisica") || pessoa.equalsIgnoreCase("Física")){
            System.out.println("Digite o CPF: ");
            CPF=in.nextLine();
            perfilFisica.setUsuario(usuario);
            perfilFisica.setCpf(CPF);
            try {
                myTwitter.criarPerfil(perfilFisica);
                this.main();
            } catch (PEException ex) {
                System.out.println("PERFIL " + ex.getUsuario() + " JÁ EXISTENTE");
                this.main();
            }
        }else if(pessoa.equalsIgnoreCase("Juridica")|| pessoa.equalsIgnoreCase("Jurídica")){
            System.out.println("Digite o CNPJ: ");
            CNPJ=in.nextLine();
            perfilJuridica.setUsuario(usuario);
            perfilJuridica.setCnpj(CNPJ);
            try {
                myTwitter.criarPerfil(perfilJuridica);
                this.main();
            } catch (PEException ex) {
                System.out.println("PERFIL " + ex.getUsuario() + " JÁ EXISTENTE");
                this.main();
            }
        }else{
            System.out.println("TIPO INVÁLIDO");
            this.cadastro();
        }
        
    }
    
    public void busca() throws PIException{
       Scanner in = new Scanner(System.in,"latin1");
       String usuario;
       final char a = '@';
       PessoaFisica perfilFisica;
       PessoaJuridica perfilJuridica;
       Vector<Perfil> vetordePerfil = new Vector<>();
       vetordePerfil = myTwitter.getRepositorio().getVetordePerfil();
       Vector<Tweet> vetordeTweets = new Vector<>();
       System.out.println("Digite o usuario a ser buscado: ");
       usuario = in.nextLine();
       if(a == usuario.charAt(0) ){}
       else  usuario = a + usuario;
      
       
       if( myTwitter.getRepositorio().buscar(usuario)instanceof PessoaFisica ){
           perfilFisica = (PessoaFisica)myTwitter.getRepositorio().buscar(usuario);
           System.out.println("Perfil de: " + perfilFisica.getUsuario());
           
           if(perfilFisica.isAtivo())
               System.out.println("Estado: Ativo");
           else
               System.out.println("Estado: Desativado");
           
            System.out.println("Usuario: " + perfilFisica.getUsuario());
            System.out.println("Tipo de pessoa: Física");
            System.out.println("CPF: " + perfilFisica.getCpf());
            this.main();
        
       }else if(myTwitter.getRepositorio().buscar(usuario)instanceof PessoaJuridica){
           perfilJuridica = (PessoaJuridica)myTwitter.getRepositorio().buscar(usuario);
           System.out.println("Perfil de: " + perfilJuridica.getUsuario());
           
           if(perfilJuridica.isAtivo())
               System.out.println("Estado: Ativo");
           else
               System.out.println("Estado: Desativado");
           
            System.out.println("Usuario: " + perfilJuridica.getUsuario());
            System.out.println("Tipo de pessoa: Jurídica");
            System.out.println("CNPJ: " + perfilJuridica.getCnpj());
           this.main();
        } else
             throw new PIException(usuario);   
 
    }
    
    public void atualiza(){
        Scanner in = new Scanner(System.in,"latin1");
        Perfil perfil;
        final char a = '@';
        String usuario;
        System.out.println("Digite o usuario a ser atualizado");
        usuario = in.nextLine();
        if(a == usuario.charAt(0) ){}
        else  usuario = a + usuario;
       
        perfil = myTwitter.getRepositorio().buscar(usuario);
        if(perfil == null){
            System.out.println("PERFIL " + usuario + " INEXISTENTE");
            this.main();
        }
        try {
            myTwitter.getRepositorio().atualizar(perfil);
             try {
                LogTwitter.getInstance().gerarRelatorio(usuario+" atualizado");
            } catch (IOException ex) {
                System.out.println("Não foi possível gerar relatório");
            }
        } catch (UNCException ex) {
            System.out.println("USUARIO " + ex.getUsuario() + " INEXISTENTE");
            this.main();
        }
        this.main();
       
    }
    
    public void verPerfis(){
       PessoaFisica perfilFisica;
       PessoaJuridica perfilJuridica;
       Vector<Perfil> vetordePerfil;
       vetordePerfil = myTwitter.getRepositorio().getVetordePerfil();
       Vector<Perfil> vetordePerfilAtivo = new Vector<>();
       
       for (int i = 0; i <vetordePerfil.size(); i++) {
            if(vetordePerfil.get(i).isAtivo())
               vetordePerfilAtivo.add(vetordePerfil.get(i));
        }
       
       if(vetordePerfilAtivo.isEmpty()){
            System.out.println("Sem perfis cadastrados");
            System.out.println("Cadastre um perfil");
            this.main();
       }
       
        for (int i = 0; i < vetordePerfilAtivo.size(); i++) {
            if(vetordePerfilAtivo.get(i).getUsuario().charAt(0)!='@')
                vetordePerfilAtivo.get(i).setUsuario('@'+vetordePerfilAtivo.get(i).getUsuario());
            
            System.out.println("Usuário: " + vetordePerfilAtivo.get(i).getUsuario());
            if(vetordePerfilAtivo.get(i) instanceof PessoaFisica){
                System.out.println("Tipo de pessoa: Física" );
                perfilFisica = (PessoaFisica)vetordePerfilAtivo.get(i);
                System.out.println("CPF: " + perfilFisica.getCpf());
            }
            else{
                System.out.println("Tipo de pessoa: Jurídica");
                perfilJuridica= (PessoaJuridica)vetordePerfilAtivo.get(i);
                System.out.println("CNPJ: " + perfilJuridica.getCnpj());
            }
            System.out.println("\r\n");
        }
    }
    
    public void pularLinhas(){
        for (int i = 0; i < 5; i++) {
            System.out.println("");
        }
        
    }
    
    public void irTwitter() throws PIException{
        Scanner in = new Scanner(System.in,"latin1");
        Scanner in2 = new Scanner(System.in,"latin1");
        Scanner in3 = new Scanner(System.in,"latin1");
        String usuario,mensagem,seguido,seguidor;
        Vector<Tweet> vetordeTweetsdatimeline;
        Vector<Tweet> vetordeTweets;
        Vector<Perfil> vetordeSeguidores;
        Vector<Tweet> vetordeTweetsdatimeline_1 = new Vector<>();
        final char a = '@';  byte dig;
  
        System.out.println("\r\nPerfis cadastrados\r\n");
        this.verPerfis();
        System.out.println("Digite o usuario que deseja utilizar ou 0 para voltar");
        usuario = in.nextLine();
        if(usuario.equals("0"))
            this.main();
        
        if(a == usuario.charAt(0)){}
        else  usuario = a + usuario;
        Perfil perfil=myTwitter.getRepositorio().buscar(usuario);
        System.out.println("");
        
        if(perfil!=null){
            System.out.println("\t Usuário - " + usuario);
            System.out.println("(1) - CANCELAR PERFIL\r\n(2) - TWEETAR\r\n(3) - TIMELINE"
                    + "\r\n(4) - TWEETS\r\n(5) - SEGUIR\r\n(6) - SEGUIDORES\r\n(0) - VOLTAR");
            
           try{ 
               dig = in.nextByte();
               switch(dig){
                case 1:{
                    try {
                        myTwitter.cancelarPerfil(usuario);
                        System.out.println("\r\nPerfil desativado");
                        this.main();
                    }catch (PDException | PIException ex) {
                             if(ex instanceof PDException){
                                System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");
                                this.irTwitter();
                             }else{
                                 System.out.println("\r\nPERFIL " + usuario + " INEXISTENTE");
                                 this.irTwitter();
                             }
                        }
                    break;
                }    
                case 2:{
                    System.out.println("Digite seu tweet");
                    mensagem = in2.nextLine();
                    try {
                        myTwitter.tweetar(usuario,mensagem);
                        System.out.println("\r\nMensagem tweetada\r\n");
                        this.irTwitter();
                    }catch (PDException | MFPException ex) {
                            if(ex instanceof PDException){
                                System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");                //Nunca vai chegar aqui pq nem dá pra usar um desativado
                                this.irTwitter();
                            }
                            else{
                                System.out.println("\r\nMENSAGEM FORA DO PADRÃO");
                                this.irTwitter();
                            }
                    }
                    break;
                }
                case 3:{
                    try {
                         vetordeTweetsdatimeline_1 =myTwitter.timeline(perfil.getUsuario()) ;
                         
                    } catch (PDException ex) {
                         System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");                       //Nunca vai chegar aqui pq nem dá pra usar um desativado
                         this.irTwitter();
                    }
                    if(!vetordeTweetsdatimeline_1.isEmpty()){  
                     System.out.println("\r\nTimeline de " + perfil.getUsuario() + "\r\n");
                     try {
                         vetordeTweetsdatimeline=myTwitter.timeline(perfil.getUsuario());
                         for (int i = 0; i < vetordeTweetsdatimeline.size(); i++) {
                             System.out.print(vetordeTweetsdatimeline.get(i).getUsuario() + " - " );
                             System.out.println(vetordeTweetsdatimeline.get(i).getMensagem());
                             System.out.println("");
                         }
                        this.irTwitter();
                     }catch (PDException ex) {
                        System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");
                        this.irTwitter();
                    }
                   }
                    else{
                        System.out.println("\r\nTimeline do usuario " + perfil.getUsuario() + " vazia \r\n");
                        this.irTwitter();
                    }
                    break;
                }
                case 4:{
                    if(!myTwitter.tweets(perfil.getUsuario()).isEmpty()){
                      System.out.println("\r\nTweets de " + perfil.getUsuario() + "\r\n");
                      vetordeTweets=myTwitter.tweets(perfil.getUsuario());
                        for (int i = 0; i < vetordeTweets.size(); i++) {
                            System.out.print(vetordeTweets.get(i).getUsuario()+ " - ");
                            System.out.println(vetordeTweets.get(i).getMensagem() + "\r\n");
                         }
                        this.irTwitter();
                    } else{
                        System.out.println("\r\nSem Tweets do usuario " + perfil.getUsuario() +" \r\n");
                        this.irTwitter();
                    }
                    break;
                }
                case 5:{
                    System.out.println("Digite o usuario a ser seguido");
                    seguido = in3.nextLine();
                    
                    if(a == seguido.charAt(0) ){}
                    else  seguido = a + seguido;
                    
                    seguidor = perfil.getUsuario();
                    try {
                         myTwitter.seguir(seguidor,seguido);
                         System.out.println("\r\n" + seguidor + " seguiu " + seguido +"\r\n");
                    } catch (PIException | PDException | SIException | SRException ex) {
                        if(ex instanceof PDException){
                            System.out.println("\r\nPERFIL " + seguido + " DESATIVADO");
                            this.irTwitter();
                        }else if(ex instanceof SIException){
                            System.out.println("\r\nUSUÁRIO A SER SEGUIDO " + seguido + " INVÁLIDO");
                            this.irTwitter();
                        }else if(ex instanceof PIException){
                            System.out.println("\r\nPERFIL " + seguido + " INEXISTENTE");
                            this.irTwitter();
                        }
                        else{
                            System.out.println("\r\nPERFIL " + seguidor + " JÁ É SEGUIDOR DE " + seguido );
                        }
                            
                    }
                    this.irTwitter();
                    break;
                }
                case 6:{
                    System.out.println("\r\nSeguidores de " + perfil.getUsuario() +"\r\n");
                    try {
                        vetordeSeguidores = myTwitter.seguidores(usuario);
                            for (int i = 0; i < vetordeSeguidores.size(); i++) {
                                System.out.printf("Seguidor %d - ",i+1);
                                System.out.println(vetordeSeguidores.get(i).getUsuario());
                                System.out.println("");
                          }
                    }catch (PDException ex) {
                        System.out.println("\r\nPERFIL " + usuario + " DESATIVADO");
                        this.irTwitter();
                    }
                    
                    
                    this.irTwitter();
                    break;
                }

                case 0:{
                    this.irTwitter();
                    break;
                }    
                    
             }
           

            }catch(InputMismatchException ex){
               System.out.println("Dígito inválido");
               this.irTwitter();
           }
           
            }else
               throw new PIException(usuario);
    }
}
