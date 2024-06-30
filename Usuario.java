public class Usuario {
    private String nome;
    private double saldo;
    private String origem;
    private String destino;
    private int estrelasMinimas;
    private int duracao;

    public Usuario(String nome, double saldo, String origem, String destino, int estrelasMinimas, int duracao) {
        this.nome = nome;
        this.saldo = saldo;
        this.origem = origem;
        this.destino = destino;
        this.estrelasMinimas = estrelasMinimas;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getEstrelasMinimas() {
        return estrelasMinimas;
    }

    public void setEstrelasMinimas(int estrelasMinimas) {
        this.estrelasMinimas = estrelasMinimas;
    }

    public int getDuracao() {
        return duracao;
    }
}
