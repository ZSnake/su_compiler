/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package su_compiler;
import Ada95_Intermediate.*;
import Ada95_Codegen.*;
import java.io.*;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;

/**
 *
 * @author Sharon Montenegro
 */
public class compi extends javax.swing.JFrame {

    /**
     * Creates new form compi
     */
    public compi() {
        initComponents();
        drawCodeEditor();
        currentFile = null;
        hasChanged = false;

        
    }
    
    public void drawCodeEditor(){
        internalPanel = new JPanel();
        
        localJTextPane = new JTextPane();
        localJScrollPane = new JScrollPane(localJTextPane);
        localJScrollPane.setPreferredSize(new Dimension(850, 450));
        localTextLineNumber = new TextLineNumber(localJTextPane, 3);
        internalPanel.add(localJScrollPane);
        localTextLineNumber.setUpdateFont(true);
        localJScrollPane.setRowHeaderView(localTextLineNumber);
        container.setDefaultCloseOperation(3);
        localJTextPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                localJTextPaneKeyPressed(evt);
            }
        });
        
        container.setContentPane(internalPanel);
       
        container.setVisible(true);
        javax.swing.plaf.InternalFrameUI ifu= container.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
    }
    
    private void localJTextPaneKeyPressed(java.awt.event.KeyEvent evt) {
        if(!evt.isActionKey())
            hasChanged = true;
            
    }
    
    public void leerArchivo(){
        FileReader fr = null;
        BufferedReader br = null;
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter ff = new FileNameExtensionFilter("Archivos de ADA(*.adb)", "adb");
        fc.setFileFilter(ff);
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fc.getSelectedFile();
            try {
                fr = new FileReader (currentFile);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String linea;
                localJTextPane.setText("");
                while((linea=br.readLine())!=null)
                    localJTextPane.setText(localJTextPane.getText() + linea + "\n");
            }
            catch(Exception e){
                txtOutput.setText(e.getMessage());
            }finally{
                try{                    
                    if( null != fr ){   
                        fr.close();     
                    }                  
                }catch (Exception e2){ 
                    JOptionPane.showMessageDialog(this, "ERROR: no se puede cerrar el archivo, no se completo la escritura.", "Wach out!", 3);
                }
            }
        }
        
    }
    
    public void guardarArchivo(){
        
        FileWriter fr = null;
        PrintWriter pw;
        String path;
        try{
            path = currentFile.getAbsolutePath();
            if(currentFile.exists())
                currentFile.delete();
            currentFile = new File(path);
            fr = new FileWriter(currentFile);
            pw = new PrintWriter(fr);
            pw.println(localJTextPane.getText().replaceAll("\n", "\r\n"));
            hasChanged = false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: Error al guardar el archivo, es probable que no exista.", "Wach out!", 3);
        }
        try {
            fr.close();
            JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.", "Hey! Listen!", 1);
        } catch (IOException ex) {
            
            JOptionPane.showMessageDialog(this, "ERROR: no se puede cerrar el archivo, no se completo la escritura.", "Wach out!", 3);
        }
    }
    
    public void guardarArchivo(File currentFile, boolean existed){
        FileWriter fr = null;
        PrintWriter pw;
        try{
            if(existed){
                String path = currentFile.getAbsolutePath();
                currentFile.delete();
                currentFile = new File(path);
            }
            this.currentFile = currentFile;
            fr = new FileWriter(currentFile);
            pw = new PrintWriter(fr);
            pw.println(localJTextPane.getText().replaceAll("\n", "\r\n"));
            hasChanged = false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: no se ha podido escribir el archivo, es probable que no exista.", "Wach out!", 3);
        }
        try {
            fr.close();
            JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.", "Hey! Listen!", 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ERROR: no se puede cerrar el archivo, no se completo la escritura.", "Wach out!", 3);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btnCompile = new javax.swing.JButton();
        btnLimpiarOutput = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SU Compiler - ADA95");

        container.setBorder(javax.swing.BorderFactory.createTitledBorder("Código"));
        container.setVisible(true);

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container.getContentPane());
        container.getContentPane().setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );

        txtOutput.setBackground(new java.awt.Color(0, 0, 0));
        txtOutput.setColumns(20);
        txtOutput.setEditable(false);
        txtOutput.setFont(new java.awt.Font("OCR A Extended", 0, 16)); // NOI18N
        txtOutput.setForeground(new java.awt.Color(255, 255, 255));
        txtOutput.setLineWrap(true);
        txtOutput.setRows(5);
        txtOutput.setCaretColor(new java.awt.Color(255, 255, 255));
        txtOutput.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(txtOutput);

        jLabel1.setText("------------------------>Output<------------------------");

        btnCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/su_compiler/asd.png"))); // NOI18N
        btnCompile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCompile.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompileActionPerformed(evt);
            }
        });

        btnLimpiarOutput.setText("Limpiar Output");
        btnLimpiarOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarOutputActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Abrir Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Guardar ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Guardar como...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("About...");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jLabel1)
                        .addGap(277, 277, 277))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(container))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnCompile, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimpiarOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(container)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCompile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarOutput))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        leerArchivo();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompileActionPerformed
        if(hasChanged){
            int result = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios? ","Atención", JOptionPane.OK_CANCEL_OPTION, 2);
            if(result == 0){
                try {
                    if(currentFile == null){
                        JFileChooser fc = new JFileChooser();
                        FileNameExtensionFilter ff = new FileNameExtensionFilter("Archivo de ADA(*.adb)", "adb");
                        fc.setFileFilter(ff);
                        int returnVal = fc.showSaveDialog(this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File f = fc.getSelectedFile();
                            if(!f.exists()){
                                try {
                                    f.createNewFile();
                                    guardarArchivo(f, true);
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(this, "ERROR: no se puede guardar el archivo.", "Wach out!", 3);
                                }
                            }else{
                                guardarArchivo(f, false);
                            }

                        }
                    }else
                        guardarArchivo();
                    lexer scanner = new lexer(new FileInputStream(currentFile));
                    al = new Analizador(scanner);
                    al.parse();
                    
                    txtOutput.setText("");
                    txtOutput.append(al.imprimirErrores() + "\n ---------------------------------------\n");
                    txtOutput.append("Se han encontrado " + 6 + " errores lexicos\n");
                    txtOutput.append(scanner.erroresLexicos);
                    txtOutput.append("Fin de analisis " + "\n ---------------------------------------\n");
                    txtOutput.append("Entro aqui " + "\n ---------------------------------------\n");

                    //semantico
                    Object res;
                    lexer lss = new lexer(new FileInputStream(currentFile));
                    ls = new parserSemantic(lss);
                    res = ls.parse().value;
                    
                    
                    //asumiendo esta bueno
                    FrontEndResult parsed;
                                parsed=(FrontEndResult)res;
                                String assemblyName=currentFile.getPath().replace(".adb", ".asm");
                                ParserCodegen backend=new ParserCodegen(parsed.icode, parsed.table, false);
                                backend.assemble(assemblyName);
                } catch (FileNotFoundException ex) {
                    System.err.println(ex.getMessage());
                } catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }
        else{
            try {
                    lexer scanner = new lexer(new FileInputStream(currentFile));
                    al = new Analizador(scanner);
                    //als = new AnalizadorSemantic(scanner);
                    al.parse();
                    //als.parse();
                    
//                    if(als.action_obj.currentScope != null)
//                        JOptionPane.showMessageDialog(this, "scope dude", "Wach out!", 0);
//                    else    
//                        JOptionPane.showMessageDialog(this, "):", "Wach out!", 0);
                    txtOutput.setText("");
                    txtOutput.append(al.imprimirErrores() + "\n ---------------------------------------\n");
                    txtOutput.append("Se han encontrado " + scanner.contadorErroresLexicos + " errores lexicos\n");
                    txtOutput.append(scanner.erroresLexicos);
                    txtOutput.append("Fin de analisis " + "\n ---------------------------------------\n");
                    
                    //semantico
                    Object res;
                    LexerSemantic lss = new LexerSemantic(new FileInputStream(currentFile));
                    ls = new parserSemantic(lss);
                    res = ls.parse().value;
                    
                    //asumiendo esta bueno
                    FrontEndResult parsed;
                    parsed=(FrontEndResult)res;
                    String assemblyName=currentFile.getPath().replace(".adb", ".asm");
                    //txtOutput.append(ls.getErrores().toString());
                    
                    txtOutput.append(String.format("Generados %d cuadruplos:\n",parsed.icode.size()));
					for(int i=0; i< parsed.icode.size(); i++){
						txtOutput.append(String.format("%d\t%s\n", i, parsed.icode.get(i)));
					}
					txtOutput.append("Tabla de símbolos:\n");
					txtOutput.append(parsed.table.toString());
                    
                    ParserCodegen backend=new ParserCodegen(parsed.icode, parsed.table, false);
                    backend.assemble(assemblyName);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(this, "ERROR: El archivo que desea analizar no existe", "Wach out!", 0);
                    e.printStackTrace();
                }
        }
    }//GEN-LAST:event_btnCompileActionPerformed

    private void btnLimpiarOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarOutputActionPerformed
         txtOutput.setText("");
    }//GEN-LAST:event_btnLimpiarOutputActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(null != currentFile)
            guardarArchivo();
        else{
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter ff = new FileNameExtensionFilter("Archivos de ADA(*.adb)", "adb");
            fc.setFileFilter(ff);
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                if(!f.exists()){
                    try {
                        f.createNewFile();
                        guardarArchivo(f, true);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "ERROR: no se guardar el archivo.", "Wach out!", 3);
                    }
                }else{
                    guardarArchivo(f, false);
                }

            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter ff = new FileNameExtensionFilter("Archivos de ADA(*.adb)", "adb");
        fc.setFileFilter(ff);
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            if(!f.exists()){
                try {
                    f.createNewFile();
                    guardarArchivo(f, true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "ERROR: no se guardar el archivo.", "Wach out!", 3);
                }
            }else{
                guardarArchivo(f, false);
            }
            
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        about a = new about();
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(compi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(compi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(compi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(compi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new compi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompile;
    private javax.swing.JButton btnLimpiarOutput;
    private javax.swing.JInternalFrame container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtOutput;
    // End of variables declaration//GEN-END:variables
    private TextLineNumber tln;
    private JPanel internalPanel;
    private JTextPane localJTextPane;
    private JScrollPane localJScrollPane;
    private TextLineNumber localTextLineNumber;
    private File currentFile;
    private boolean hasChanged;
    private Analizador al;
    private parserSemantic ls;
    private FileFilter ff;

}

