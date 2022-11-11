package br.com.lenolima.model.recibos;

public class Recibo {

    private String codigo;
    private Float valor;
    private String origem;
    private String referencia;
    private String data;
    private String Nome;
    private String CPF;
    private String endereco = ".";
    private String enderecoEmpresa;

    @Override
    public String toString() {
        return "Recibo{" + "codigo=" + codigo + ", valor=" + valor + ", origem=" + origem + ", referencia=" + referencia + ", data=" + data + ", Nome=" + Nome + ", CPF=" + CPF + ", endereco=" + endereco + ", enderecoEmpresa=" + enderecoEmpresa + '}';
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoEmpresa() {
        return enderecoEmpresa;
    }

    public void setEnderecoEmpresa(String enderecoEmpresa) {
        this.enderecoEmpresa = enderecoEmpresa;
    }

}
