package br.com.lenolima.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UrlTableModel extends AbstractTableModel {

    private final List<String> dados = new ArrayList<>();
    private String[] colunas = {"URLS"};

    public UrlTableModel(String[] colunas) {

        this.colunas = colunas;
        
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {

        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {

        switch (coluna) {
            case 0:
                return dados.get(linha);
        }

        return null;
    }

    public void addRow(String p) {

        this.dados.add(p);
        this.fireTableDataChanged();

    }

    public void removeRow(int linha) {

        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha);

    }

    public void clearList() {

        dados.clear();
        this.fireTableDataChanged();

    }

    public List<String> getDados() {
        return dados;
    }

}
