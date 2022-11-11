/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lenolima.sup.recibos;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author girlenolima
 */
public class FormatoBRL {

    public static String forBRL(Float moeda) {

    
        Locale l = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(l); 
        return nf.format(moeda);
        
       
    }
}
