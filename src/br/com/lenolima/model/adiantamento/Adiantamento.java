package br.com.lenolima.model.adiantamento;

import br.com.lenolima.sup.UtilitarioOpracoesEmLlist;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Adiantamento {

    private Double cafeDaManha;
    private Double almoco;
    private Double jantar;
    private Double passagens;
    private Double ajudaKm;
    private Double combustibel;
    private Double deslocamento;
    private Double hospedagem;
    private Double extras;
    private String referencia;
    private String vencimento;
    private LocalDate dataIda;
    private LocalDate dataVolta;

    public Adiantamento(Double cafeDaManha, Double almoco, Double jantar, Double passagens, Double ajudaKm, Double combustibel, Double deslocamento, Double hospedagem, Double extras, String referencia, String vencimento, LocalDate dataIda, LocalDate dataVolta) {
        this.cafeDaManha = cafeDaManha;
        this.almoco = almoco;
        this.jantar = jantar;
        this.passagens = passagens;
        this.ajudaKm = ajudaKm;
        this.combustibel = combustibel;
        this.deslocamento = deslocamento;
        this.hospedagem = hospedagem;
        this.extras = extras;
        this.referencia = referencia;
        this.vencimento = vencimento;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cafeDaManha);
        hash = 53 * hash + Objects.hashCode(this.almoco);
        hash = 53 * hash + Objects.hashCode(this.jantar);
        hash = 53 * hash + Objects.hashCode(this.passagens);
        hash = 53 * hash + Objects.hashCode(this.ajudaKm);
        hash = 53 * hash + Objects.hashCode(this.combustibel);
        hash = 53 * hash + Objects.hashCode(this.deslocamento);
        hash = 53 * hash + Objects.hashCode(this.hospedagem);
        hash = 53 * hash + Objects.hashCode(this.extras);
        hash = 53 * hash + Objects.hashCode(this.referencia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Adiantamento other = (Adiantamento) obj;
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        if (!Objects.equals(this.cafeDaManha, other.cafeDaManha)) {
            return false;
        }
        if (!Objects.equals(this.almoco, other.almoco)) {
            return false;
        }
        if (!Objects.equals(this.jantar, other.jantar)) {
            return false;
        }
        if (!Objects.equals(this.passagens, other.passagens)) {
            return false;
        }
        if (!Objects.equals(this.ajudaKm, other.ajudaKm)) {
            return false;
        }
        if (!Objects.equals(this.combustibel, other.combustibel)) {
            return false;
        }
        if (!Objects.equals(this.deslocamento, other.deslocamento)) {
            return false;
        }
        if (!Objects.equals(this.hospedagem, other.hospedagem)) {
            return false;
        }
        return Objects.equals(this.extras, other.extras);
    }

    @Override
    public String toString() {
        return "Adiantamento{" + "cafeDaManha=" + cafeDaManha + ", almoco=" + almoco + ", jantar=" + jantar + ", passagens=" + passagens + ", ajudaKm=" + ajudaKm + ", combustibel=" + combustibel + ", deslocamento=" + deslocamento + ", hospedagem=" + hospedagem + ", extras=" + extras + ", referencia=" + referencia + '}';
    }

    public Double getCafeDaManha() {
        return cafeDaManha;
    }

    public Double getAlmoco() {
        return almoco;
    }

    public Double getJantar() {
        return jantar;
    }

    public Double getPassagens() {
        return passagens;
    }

    public Double getAjudaKm() {
        return ajudaKm;
    }

    public Double getCombustibel() {
        return combustibel;
    }

    public Double getDeslocamento() {
        return deslocamento;
    }

    public Double getHospedagem() {
        return hospedagem;
    }

    public Double getExtras() {
        return extras;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setCafeDaManha(Double cafeDaManha) {
        this.cafeDaManha = cafeDaManha;
    }

    public void setAlmoco(Double almoco) {
        this.almoco = almoco;
    }

    public void setJantar(Double jantar) {
        this.jantar = jantar;
    }

    public void setPassagens(Double passagens) {
        this.passagens = passagens;
    }

    public void setAjudaKm(Double ajudaKm) {
        this.ajudaKm = ajudaKm;
    }

    public void setCombustibel(Double combustibel) {
        this.combustibel = combustibel;
    }

    public void setDeslocamento(Double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setHospedagem(Double hospedagem) {
        this.hospedagem = hospedagem;
    }

    public void setExtras(Double extras) {
        this.extras = extras;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDate getDataIda() {
        return dataIda;
    }

    public LocalDate getDataVolta() {
        return dataVolta;
    }

    public void setDataIda(LocalDate dataIda) {
        this.dataIda = dataIda;
    }

    public void setDataVolta(LocalDate dataVolta) {
        this.dataVolta = dataVolta;
    }

    public Double somarAdiantamento() {

        List<Double> lista = Arrays.asList(cafeDaManha,
                almoco,
                jantar,
                passagens,
                ajudaKm,
                combustibel,
                deslocamento,
                hospedagem,
                extras);

        return UtilitarioOpracoesEmLlist.somar(lista);

    }

}
