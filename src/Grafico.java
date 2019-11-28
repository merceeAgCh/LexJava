//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Grafico extends JFrame {
    String filename;
    Lexico l;
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JTextField jTextField1;

    public Grafico() {
        this.initComponents();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jButton1 = new JButton();
        this.jTextField1 = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.jTextArea1 = new JTextArea();
        this.jButton2 = new JButton();
        this.setDefaultCloseOperation(3);
        this.jLabel1.setText("Analizador Lexico By MAC");
        this.jButton1.setText("Buscar Archivo");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Grafico.this.jButton1ActionPerformed(evt);
            }
        });
        this.jTextField1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Grafico.this.jTextField1ActionPerformed(evt);
            }
        });
        this.jTextArea1.setColumns(20);
        this.jTextArea1.setRows(5);
        this.jScrollPane1.setViewportView(this.jTextArea1);
        this.jButton2.setText("Analizar");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Grafico.this.jButton2ActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 341, 32767)).addGroup(layout.createSequentialGroup().addGap(66, 66, 66).addComponent(this.jTextField1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton1))).addGap(31, 31, 31)).addGroup(layout.createSequentialGroup().addGap(99, 99, 99).addComponent(this.jLabel1, -2, 140, -2).addContainerGap(-1, 32767)).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jButton2).addGap(15, 15, 15)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(23, 23, 23).addComponent(this.jLabel1).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jButton1).addComponent(this.jTextField1, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jButton2).addContainerGap(66, 32767)));
        this.pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog((Component)null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        try {
            FileReader reader = new FileReader(filename);
            BufferedReader br = new BufferedReader(reader);
            this.jTextArea1.read(br, (Object)null);
            br.close();
            this.jTextArea1.requestFocus();
            this.l = new Lexico(filename);
        } catch (Exception var7) {
            System.out.println(var7);
        }

    }

    private void jTextField1ActionPerformed(ActionEvent evt) {
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        String valor = "";

        try {
            FileReader reader = new FileReader("/home/MAC/git/LexicoJava/tiraTokens.txt");
            BufferedReader br = new BufferedReader(reader);

            while(true) {
                if ((valor = br.readLine()) == null) {
                    br.close();
                    this.l.analisis(valor);
                    break;
                }
            }
        } catch (Exception var5) {
            JOptionPane.showMessageDialog((Component)null, var5);
        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new Grafico()).setVisible(true);
            }
        });
    }
}

