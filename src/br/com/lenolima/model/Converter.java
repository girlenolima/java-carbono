package br.com.lenolima.model;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Converter {

    void display(String url);

    void create(List<String> file);

    void merge(List<InputStream> streamOfPDFFiles,OutputStream outputStream,boolean paginate);

}
