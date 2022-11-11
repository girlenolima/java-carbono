package br.com.lenolima.control.adiantamento.diarias;

public class DiariasCredishop implements Diarias {

    @Override
    public double calcular(int dias, int valor) {

        return dias * valor;

    }

}
