package twitter;

public class Tweet {
    private String usuario;
    private String mensagem;

    public Tweet(String usuario,String mensagem) {
        this.usuario = usuario;
        this.mensagem = mensagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}
