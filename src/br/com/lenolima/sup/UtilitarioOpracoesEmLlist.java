package br.com.lenolima.sup;


import java.util.List;

public class UtilitarioOpracoesEmLlist {

    public static double somar(List<Double> args) {
        
        return args.stream().reduce(Double::sum).get();  
                
      /*return  Arrays.stream(args) 
                .mapToDouble(i -> i.intValue())
                .sum();*/

    }


}
