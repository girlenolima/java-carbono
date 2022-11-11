package br.com.lenolima.view;

import br.com.lenolima.control.PDF;
import br.com.lenolima.control.interfaceBusines;
import br.com.lenolima.control.recibo.Busines;
import br.com.lenolima.model.Converter;
import br.com.lenolima.model.UrlTableModel;
import br.com.lenolima.model.adiantamento.Adiantamento;
import br.com.lenolima.model.adiantamento.AdiantamentoTexto;
import br.com.lenolima.sup.PathSelectMultFilesURL;
import br.com.lenolima.sup.UtilitarioConverterMoeda;
import static br.com.lenolima.sup.UtilitarioValidarEntrada.validarSeEumNumero;
import br.com.lenolima.sup.recibos.CaminhoArquivoOrigem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class index2 extends javax.swing.JFrame {

    interfaceBusines interfaceB = new Busines();
    private String PLANILHA;
    private String DESTINO;

    private String[] colunas = {"URLS"};
    UrlTableModel urlTableModel = new UrlTableModel(colunas);

    public index2() {

        initComponents();
        jTable1.setModel(urlTableModel);
        barraProgresso();
        jScrollPane2.setBorder(null); // elimina as bordas da JtextArea
        jTable1.setBorder(null); // elimina as bordas da JtextArea
        jScrollPane3.setBorder(null); // elimina as bordas da JtextArea
        setResultadoAdiantamento();

    }

    // alterar depois so copiei e colei da outra classe, metodos repetidos
    //true = arquivo , false = pasta
    public void selecionaArquivoOuPasta(Boolean arquivoOuPasta) {

        String tipoMenssagem = arquivoOuPasta ? "ARQUIVO" : "PASTA";
        String menssagem = "SELECAO : " + tipoMenssagem + " CANCELADA";

        try {

            if (arquivoOuPasta == true) {
                PLANILHA = CaminhoArquivoOrigem.selecionarPath(true);
            } else {
                DESTINO = CaminhoArquivoOrigem.selecionarPath(false);
            }

            atualizarVisualizadores();
            limparAviso();

        } catch (Exception e) {
            limparVisualizadores();
            quadroAviso(menssagem);

        }

    }

    // alterar depois so copiei e colei da outra classe, metodos repetidos
    private void atualizarVisualizadores() {

        visulizadorPath.setText(PLANILHA);
        visulizadorDestinoRecibos.setText(DESTINO);
    }

    // alterar depois so copiei e colei da outra classe, metodos repetidos
    private void limparVisualizadores() {
        PLANILHA = null;
        DESTINO = null;
        atualizarVisualizadores();
    }

    // alterar depois so copiei e colei da outra classe, metodos repetidos
    private void limparAviso() {
        // quadroDeAvisos.setText("");
    }

    // alterar depois so copiei e colei da outra classe, metodos repetidos
    private void quadroAviso(String menssagem) {

        // quadroDeAvisos.setForeground(Color.WHITE);
        //  quadroDeAvisos.setText(menssagem);
    }

    private Adiantamento capturarDadosAdiantamento() {

        Double cafeDamanha = validarSeEumNumero(cafe.getText());
        Double alm = validarSeEumNumero(almoco.getText());
        Double jan = validarSeEumNumero(jantar.getText());
        Double pass = validarSeEumNumero(passagens.getText());
        Double ajudak = validarSeEumNumero(ajudakm.getText());
        Double combu = validarSeEumNumero(combustivel.getText());
        Double desloca = validarSeEumNumero(deslocamento.getText());
        Double hospe = validarSeEumNumero(hospedagem.getText());
        Double ext = validarSeEumNumero(extras.getText());
        String ref = referencia.getText();
        String ven = vencimento.getText();

        LocalDate dataIda = null; //UtilitarioFormatarData.converterStringParaDate(ida.getText());
        LocalDate dataVolta = null; //UtilitarioFormatarData.converterStringParaDate(volta.getText());

        Adiantamento adiantamento = new Adiantamento(cafeDamanha, alm, jan, pass, ajudak, combu, desloca, hospe, ext, ref, ven, dataIda, dataVolta);

        return adiantamento;

    }

    private Double somarAdiantamento(Adiantamento adiantamento) {

        return adiantamento.somarAdiantamento();

    }

    public void setResultadoAdiantamento() {

        Adiantamento adiantamento = capturarDadosAdiantamento();

        resultado.setText(UtilitarioConverterMoeda.formatarParaBRL(somarAdiantamento(adiantamento)));
        resultado.setText(AdiantamentoTexto.criarTextoBase(adiantamento));
    }

    public Boolean adcionarPDF() {

        for (File pdf : PathSelectMultFilesURL.selectFiles()) {
            urlTableModel.addRow(pdf.toString());
        }
        if (urlTableModel.getRowCount() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public void removerItensSelecionador() {

        if (jTable1.getSelectedRow() != -1) {
            urlTableModel.removeRow(jTable1.getSelectedRow());
        }
    }

    public void limparLista() {
        urlTableModel.clearList();
    }

    public void createPDFMerge() throws IOException {

        Converter converter = new PDF();

        if (urlTableModel.getRowCount() == 0 || urlTableModel.getRowCount() == 1) {
            JOptionPane.showMessageDialog(this, "É necessario selecionar pelo menos 2 itens.");
        } else {

            converter.create(urlTableModel.getDados());
            new Temporizador().start();
        }
    }

    public void barraProgresso() {
        jProgressBar1.setStringPainted(false);// printa o % no barra
        jProgressBar1.setValue(1);
        jProgressBar1.setMaximum(1000);
        jProgressBar1.setForeground(new Color(20, 200, 50));
    }

    public class Temporizador extends Thread {

        @Override
        public void run() {
            while (jProgressBar1.getValue() < 1000) {
                try {
                    sleep(100);
                    jProgressBar1.setValue(jProgressBar1.getValue() + 100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(index2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void limparBarraProgresso() {
        jProgressBar1.setValue(1);
    }

    private void adicionarSublinhadoLabel(JLabel label) {
        label.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        label.setForeground(new Color(46, 139, 87));
    }

    private void removerSublinhadoLabel(JLabel label) {
        label.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        label.setForeground(Color.BLACK);
    }

    private void adicionarEmMassaSublinhadoLabels(Component[] components) {
        //jPanel14.getComponents()
        for (Component c : components) {
            if (c instanceof JLabel) {
                ((JLabel) c).setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conteinerPrincipal = new javax.swing.JPanel();
        menuLateral = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cardConteiner = new javax.swing.JPanel();
        sprint3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        visulizadorDestinoRecibos = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        visulizadorPath = new javax.swing.JLabel();
        jProgressBar3 = new javax.swing.JProgressBar();
        jPanel11 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        sprint1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cafe = new javax.swing.JTextField();
        almoco = new javax.swing.JTextField();
        jantar = new javax.swing.JTextField();
        passagens = new javax.swing.JTextField();
        ajudakm = new javax.swing.JTextField();
        combustivel = new javax.swing.JTextField();
        deslocamento = new javax.swing.JTextField();
        hospedagem = new javax.swing.JTextField();
        extras = new javax.swing.JTextField();
        referencia = new javax.swing.JTextField();
        vencimento = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel33 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultado = new javax.swing.JTextArea();
        sprint2 = new javax.swing.JPanel();
        sprint4 = new javax.swing.JPanel();
        pB = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        pA = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        conteinerPrincipal.setMinimumSize(new java.awt.Dimension(1000, 625));
        conteinerPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuLateral.setBackground(new java.awt.Color(0, 5, 25));

        jPanel6.setBackground(new java.awt.Color(0, 5, 25));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lenolima/img/icons/home.png"))); // NOI18N
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 50));

        jLabel9.setFont(new java.awt.Font("Frutiger LT 45 Light", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Dashboard");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 50));

        jPanel7.setBackground(new java.awt.Color(0, 5, 25));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lenolima/img/icons/recibo3.png"))); // NOI18N
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 50));

        jLabel11.setFont(new java.awt.Font("Frutiger LT 45 Light", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Recibos");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 50));

        jLabel4.setFont(new java.awt.Font("Frutiger LT 45 Light", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Carbono 1.0");

        jPanel8.setBackground(new java.awt.Color(0, 5, 25));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lenolima/img/icons/emprestimo.png"))); // NOI18N
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 50));

        jLabel13.setFont(new java.awt.Font("Frutiger LT 45 Light", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Adt.Valor");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel8.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 50));

        jLabel14.setFont(new java.awt.Font("Frutiger LT 45 Light", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("girleno.info@gmail.com");

        jPanel5.setBackground(new java.awt.Color(0, 5, 25));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lenolima/img/icons/pdf.png"))); // NOI18N
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 50));

        jLabel7.setFont(new java.awt.Font("Frutiger LT 45 Light", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Juntar PDF");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lenolima/img/icons/carbonoX.png"))); // NOI18N

        javax.swing.GroupLayout menuLateralLayout = new javax.swing.GroupLayout(menuLateral);
        menuLateral.setLayout(menuLateralLayout);
        menuLateralLayout.setHorizontalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(menuLateralLayout.createSequentialGroup()
                .addGroup(menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuLateralLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel14))
                    .addGroup(menuLateralLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel4))
                    .addGroup(menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuLateralLayout.setVerticalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLateralLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(21, 21, 21))
        );

        conteinerPrincipal.add(menuLateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 625));

        cardConteiner.setBackground(new java.awt.Color(255, 51, 51));
        cardConteiner.setLayout(new java.awt.CardLayout());

        sprint3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        visulizadorDestinoRecibos.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        visulizadorDestinoRecibos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Pasta", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        visulizadorDestinoRecibos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visulizadorDestinoRecibosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                visulizadorDestinoRecibosMouseEntered(evt);
            }
        });

        jScrollPane3.setBorder(null);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("\n    A PLANILHA BASE DEVE SER NO FORMATO .XLS E TER 9 \n    COLUNAS (SEM TITULOS) NA ORDEM:\n  \n\t1 - CODIGO.\n\t2 - VALOR.\n\t3 - NOME DA EMPRESA.\n\t4 - REFERENCIA DO PAGAMENTO.\n\t5 - DATA DO PAGAMENTO.\n\t6 - NOME DO RECEBEDOR.\n\t7 - CPF.\n\t8 - ENDERECO DO RECEBEDOR.\n\t9 - ENDERECO DA EMPRESA.\n\n");
        jTextArea1.setBorder(null);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(jTextArea1);

        visulizadorPath.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        visulizadorPath.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Selecione a planilha", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        visulizadorPath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visulizadorPathMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                visulizadorPathMouseEntered(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel34.setText("Home");

        jLabel37.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel37.setText(">");

        jLabel38.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel38.setText("Gerar recibos home office");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel39.setText("Gerar recibo home office");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(visulizadorPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(visulizadorDestinoRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(221, 221, 221))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(visulizadorPath, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(visulizadorDestinoRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout sprint3Layout = new javax.swing.GroupLayout(sprint3);
        sprint3.setLayout(sprint3Layout);
        sprint3Layout.setHorizontalGroup(
            sprint3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sprint3Layout.setVerticalGroup(
            sprint3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        cardConteiner.add(sprint3, "card2");

        sprint1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        cafe.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        cafe.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Cafe da manha", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        cafe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cafeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cafeKeyReleased(evt);
            }
        });

        almoco.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        almoco.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Almoco", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        almoco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                almocoKeyReleased(evt);
            }
        });

        jantar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jantar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Jantar", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        jantar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jantarKeyReleased(evt);
            }
        });

        passagens.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        passagens.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Passagem", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        passagens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passagensKeyReleased(evt);
            }
        });

        ajudakm.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        ajudakm.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ajuda KM", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        ajudakm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ajudakmKeyReleased(evt);
            }
        });

        combustivel.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        combustivel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Combustivel", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        combustivel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combustivelKeyReleased(evt);
            }
        });

        deslocamento.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        deslocamento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Deslocamento", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        deslocamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                deslocamentoKeyReleased(evt);
            }
        });

        hospedagem.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        hospedagem.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hospedagem", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        hospedagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hospedagemKeyReleased(evt);
            }
        });

        extras.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        extras.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Extras", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        extras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                extrasKeyReleased(evt);
            }
        });

        referencia.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        referencia.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Referencia", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        referencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                referenciaKeyReleased(evt);
            }
        });

        vencimento.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        vencimento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Vencimento", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 12))); // NOI18N
        vencimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vencimentoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cafe, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(almoco, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ajudakm, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hospedagem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jantar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passagens, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(extras, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deslocamento)
                    .addComponent(vencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafe, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(almoco, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jantar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passagens, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deslocamento, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ajudakm, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hospedagem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extras, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(410, 410, 410))
        );

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel5.setText("Home");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel2.setText(">");

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel3.setText("Adiantamento de viagem");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel36.setText("Limpar lista");
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(25, 25, 25))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        resultado.setColumns(20);
        resultado.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        resultado.setRows(5);
        resultado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setViewportView(resultado);

        javax.swing.GroupLayout sprint1Layout = new javax.swing.GroupLayout(sprint1);
        sprint1.setLayout(sprint1Layout);
        sprint1Layout.setHorizontalGroup(
            sprint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sprint1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sprint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sprint1Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sprint1Layout.setVerticalGroup(
            sprint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sprint1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(sprint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sprint1Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(sprint1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cardConteiner.add(sprint1, "card2");

        sprint2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout sprint2Layout = new javax.swing.GroupLayout(sprint2);
        sprint2.setLayout(sprint2Layout);
        sprint2Layout.setHorizontalGroup(
            sprint2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 920, Short.MAX_VALUE)
        );
        sprint2Layout.setVerticalGroup(
            sprint2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );

        cardConteiner.add(sprint2, "card2");

        sprint4.setBackground(new java.awt.Color(255, 255, 255));
        sprint4.setLayout(new java.awt.CardLayout());

        pB.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel15.setText("Selecionar PDFs");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel35.setText("Home");

        jLabel40.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel40.setText(">");

        jLabel41.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel41.setText("Juntar PDFs");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(587, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pBLayout = new javax.swing.GroupLayout(pB);
        pB.setLayout(pBLayout);
        pBLayout.setHorizontalGroup(
            pBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pBLayout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBLayout.setVerticalGroup(
            pBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222)
                .addComponent(jLabel15)
                .addContainerGap(325, Short.MAX_VALUE))
        );

        sprint4.add(pB, "card4");

        pA.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setText("Adicionar PDFS");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel42MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel42MouseExited(evt);
            }
        });

        jLabel43.setText("Gerar arquivo");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel43MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel43MouseExited(evt);
            }
        });

        jLabel44.setText("Limpar lista");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel44MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
        });

        jLabel45.setText("Deletar selecionado");
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel45MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel45MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel45)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );

        jLabel46.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel46.setText("Home");

        jLabel47.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel47.setText(">");

        jLabel48.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel48.setText("Juntar PDFs");

        jLabel50.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel50.setText("Lista");

        jLabel49.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel49.setText(">");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pALayout = new javax.swing.GroupLayout(pA);
        pA.setLayout(pALayout);
        pALayout.setHorizontalGroup(
            pALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pALayout.setVerticalGroup(
            pALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pALayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        sprint4.add(pA, "card3");

        cardConteiner.add(sprint4, "card2");

        conteinerPrincipal.add(cardConteiner, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 920, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(conteinerPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1126, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(conteinerPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        cardConteiner.removeAll();
        cardConteiner.add(sprint2);
        cardConteiner.repaint();
        cardConteiner.revalidate();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

        cardConteiner.removeAll();
        cardConteiner.add(sprint3);
        cardConteiner.repaint();
        cardConteiner.revalidate();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked

        cardConteiner.removeAll();
        cardConteiner.add(sprint1);
        cardConteiner.repaint();
        cardConteiner.revalidate();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        cardConteiner.removeAll();
        cardConteiner.add(sprint4);
        cardConteiner.repaint();
        cardConteiner.revalidate();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void cafeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cafeKeyPressed
        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_cafeKeyPressed

    private void cafeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cafeKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_cafeKeyReleased

    private void almocoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_almocoKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_almocoKeyReleased

    private void jantarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jantarKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_jantarKeyReleased

    private void passagensKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passagensKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_passagensKeyReleased

    private void ajudakmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ajudakmKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_ajudakmKeyReleased

    private void combustivelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combustivelKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_combustivelKeyReleased

    private void deslocamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deslocamentoKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_deslocamentoKeyReleased

    private void hospedagemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hospedagemKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_hospedagemKeyReleased

    private void extrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_extrasKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_extrasKeyReleased

    private void referenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referenciaKeyReleased

        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_referenciaKeyReleased

    private void vencimentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimentoKeyReleased
        // TODO add your handling code here:
        somarAdiantamento(capturarDadosAdiantamento());
        setResultadoAdiantamento();
    }//GEN-LAST:event_vencimentoKeyReleased

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked

        for (int i = 0; i < getContentPane().getComponentCount(); i++) {

            Component c = getContentPane().getComponent(i);
     
            if (c instanceof JTextField) {

                System.out.println("AKI");
                //apaga os valores
                /* JTextField field = (JTextField) c;
                field.setText("");
                System.out.println("apagando campo " + i);*/
            }
        }

    }//GEN-LAST:event_jLabel36MouseClicked

    private void visulizadorDestinoRecibosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visulizadorDestinoRecibosMouseClicked

        selecionaArquivoOuPasta(false);
    }//GEN-LAST:event_visulizadorDestinoRecibosMouseClicked

    private void visulizadorDestinoRecibosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visulizadorDestinoRecibosMouseEntered

        visulizadorDestinoRecibos.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_visulizadorDestinoRecibosMouseEntered

    private void visulizadorPathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visulizadorPathMouseClicked

        selecionaArquivoOuPasta(true);
    }//GEN-LAST:event_visulizadorPathMouseClicked

    private void visulizadorPathMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visulizadorPathMouseEntered

        visulizadorPath.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_visulizadorPathMouseEntered

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        if (PLANILHA == null && DESTINO == null) {
            quadroAviso("ARQUIVO E PASTA NAO SELECIONADOS");
        } else if (PLANILHA == null) {
            quadroAviso("PLANILHA NAO SELECIONADA");
        } else if (DESTINO == null) {
            quadroAviso("PASTA NAO SELECIONADA");
        } else {
            interfaceB.CriarPDF(PLANILHA, DESTINO);
        }
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked

        removerItensSelecionador();
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked

        limparBarraProgresso();
        limparLista();
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked

        try {
            createPDFMerge();
        } catch (IOException ex) {
            Logger.getLogger(index2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        limparBarraProgresso();
        adcionarPDF();
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked

        limparBarraProgresso();

        if (adcionarPDF()) {
            cardConteiner.removeAll();
            cardConteiner.add(pA);
            cardConteiner.repaint();
            cardConteiner.revalidate();
        }


    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered

        adicionarSublinhadoLabel(jLabel15);
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        removerSublinhadoLabel(jLabel15);
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel42MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseEntered

        adicionarSublinhadoLabel(jLabel42);
    }//GEN-LAST:event_jLabel42MouseEntered

    private void jLabel42MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseExited
        removerSublinhadoLabel(jLabel42);


    }//GEN-LAST:event_jLabel42MouseExited

    private void jLabel43MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseEntered

        adicionarSublinhadoLabel(jLabel43);
    }//GEN-LAST:event_jLabel43MouseEntered

    private void jLabel43MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseExited
        removerSublinhadoLabel(jLabel43);
    }//GEN-LAST:event_jLabel43MouseExited

    private void jLabel44MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseEntered
        adicionarSublinhadoLabel(jLabel44);
    }//GEN-LAST:event_jLabel44MouseEntered

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited

        removerSublinhadoLabel(jLabel44);
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel45MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseEntered
        adicionarSublinhadoLabel(jLabel45);
    }//GEN-LAST:event_jLabel45MouseEntered

    private void jLabel45MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseExited
        removerSublinhadoLabel(jLabel45);
    }//GEN-LAST:event_jLabel45MouseExited

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {

        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new index2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ajudakm;
    private javax.swing.JTextField almoco;
    private javax.swing.JTextField cafe;
    private javax.swing.JPanel cardConteiner;
    private javax.swing.JTextField combustivel;
    private javax.swing.JPanel conteinerPrincipal;
    private javax.swing.JTextField deslocamento;
    private javax.swing.JTextField extras;
    private javax.swing.JTextField hospedagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JProgressBar jProgressBar3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jantar;
    private javax.swing.JPanel menuLateral;
    private javax.swing.JPanel pA;
    private javax.swing.JPanel pB;
    private javax.swing.JTextField passagens;
    private javax.swing.JTextField referencia;
    private javax.swing.JTextArea resultado;
    private javax.swing.JPanel sprint1;
    private javax.swing.JPanel sprint2;
    private javax.swing.JPanel sprint3;
    private javax.swing.JPanel sprint4;
    private javax.swing.JTextField vencimento;
    private javax.swing.JLabel visulizadorDestinoRecibos;
    private javax.swing.JLabel visulizadorPath;
    // End of variables declaration//GEN-END:variables
}
