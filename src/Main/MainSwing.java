package Main;

import WebVisuClass.AppletParameter;
import WebVisuClass.CreateConf;
import WebVisuClass.HTTP_HTTPs;
import WebVisuClass.NetworkAddress;
import WebVisuClass.LocalFiles;
import WebVisuClass.ParseApplet;
import WebVisuClass.Ping;
import WebVisuClass.RunJavaWebVisu;
import WebVisuClass.Version;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

public class MainSwing extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(MainSwing.class.getName());

    private static final String WEBVISU_JAR = "webvisu.jar";
    private static final String MINML_JAR = "minml.jar";
    
    /**
     * Creates new form MainSwing
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MainSwing() {
        java.net.URL url = ClassLoader.getSystemResource("Resources/icons8-applet-96.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setTitle("WebVisu Starter " + Version.version());
        this.setIconImage(img);
        
        url = ClassLoader.getSystemResource("Resources/icons8-unchecked-checkbox-32.png");
        ImageIcon Image_1 = new ImageIcon(url);
        url = ClassLoader.getSystemResource("Resources/icons8-checked-checkbox-32.png");
        ImageIcon Image_2 = new ImageIcon(url);

        initComponents();
        IP_Address_TextField.setText("");
        Submit_Button.setEnabled(true);
        Action_Status_Text.setText("");
        Action_Status_Progress.setVisible(false);
        No_Ping_CheckBox.setIcon(Image_1);
        No_Ping_CheckBox.setSelectedIcon(Image_2);
        IP_Address_TextField.setBorder(BorderFactory.createCompoundBorder(IP_Address_TextField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        IP_Address_TextField = new javax.swing.JTextField();
        Submit_Button = new javax.swing.JButton();
        Action_Status_Text = new javax.swing.JLabel();
        Action_Status_Progress = new javax.swing.JProgressBar();
        No_Ping_CheckBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(33, 150, 243));
        jPanel1.setName(""); // NOI18N

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(60, 63, 65));
        jLabel1.setText("IP Address:");
        jLabel1.setMaximumSize(new java.awt.Dimension(67, 25));
        jLabel1.setMinimumSize(new java.awt.Dimension(67, 25));
        jLabel1.setPreferredSize(new java.awt.Dimension(67, 25));

        IP_Address_TextField.setBackground(new java.awt.Color(255, 255, 255));
        IP_Address_TextField.setForeground(new java.awt.Color(33, 150, 243));
        IP_Address_TextField.setToolTipText("URL of the WebVisu, update it with the IP address of the controller");
        IP_Address_TextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(60, 63, 65), 1, true));
        IP_Address_TextField.setMaximumSize(new java.awt.Dimension(150, 25));
        IP_Address_TextField.setMinimumSize(new java.awt.Dimension(150, 25));
        IP_Address_TextField.setPreferredSize(new java.awt.Dimension(150, 25));

        Submit_Button.setBackground(new java.awt.Color(255, 255, 255));
        Submit_Button.setForeground(new java.awt.Color(60, 63, 65));
        Submit_Button.setText("Start");
        Submit_Button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(60, 63, 65), 1, true));
        Submit_Button.setMaximumSize(new java.awt.Dimension(65, 25));
        Submit_Button.setMinimumSize(new java.awt.Dimension(65, 25));
        Submit_Button.setPreferredSize(new java.awt.Dimension(65, 25));
        Submit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submit_ButtonActionPerformed(evt);
            }
        });

        Action_Status_Text.setBackground(new java.awt.Color(255, 255, 255));
        Action_Status_Text.setForeground(new java.awt.Color(33, 150, 243));
        Action_Status_Text.setMaximumSize(new java.awt.Dimension(34, 25));
        Action_Status_Text.setMinimumSize(new java.awt.Dimension(34, 25));
        Action_Status_Text.setPreferredSize(new java.awt.Dimension(34, 25));

        Action_Status_Progress.setBackground(new java.awt.Color(255, 255, 255));
        Action_Status_Progress.setForeground(new java.awt.Color(60, 63, 65));
        Action_Status_Progress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(60, 63, 65), 1, true));

        No_Ping_CheckBox.setBackground(new java.awt.Color(255, 255, 255));
        No_Ping_CheckBox.setForeground(new java.awt.Color(60, 63, 65));
        No_Ping_CheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        No_Ping_CheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        No_Ping_CheckBox.setMaximumSize(new java.awt.Dimension(25, 25));
        No_Ping_CheckBox.setMinimumSize(new java.awt.Dimension(25, 25));
        No_Ping_CheckBox.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(60, 63, 65));
        jLabel2.setText("Ping Req.:");
        jLabel2.setMaximumSize(new java.awt.Dimension(67, 25));
        jLabel2.setMinimumSize(new java.awt.Dimension(67, 25));
        jLabel2.setPreferredSize(new java.awt.Dimension(67, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Action_Status_Text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_Status_Progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IP_Address_TextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(No_Ping_CheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Submit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(IP_Address_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Submit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(No_Ping_CheckBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Action_Status_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Action_Status_Progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Submit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Submit_ButtonActionPerformed
        // TODO add your handling code here:
        
        SwingWorker backgroundTask = new SwingWorker() {
            @Override
            protected Void doInBackground() {
                
                Submit_Button.setEnabled(false);
                // Set progress visible
                Action_Status_Progress.setVisible(true);
                // Reset progress
                Action_Status_Progress.setIndeterminate(true);
                // Unbind text property.
                Action_Status_Text.setText("");
                
                String _sAddress = IP_Address_TextField.getText();
                Boolean _xPing = No_Ping_CheckBox.isSelected();

                String _sPath = "/PLC/";

                String _sIP;
                _sIP = NetworkAddress.addressResolver(_sAddress);
                if (NetworkAddress.addressChecker(_sIP)) {
                    Integer _iTimeout = 0;
                    Action_Status_Text.setText("Testing communication.");
                    if (!(_xPing) || Ping.isReachable(_sIP, _iTimeout)) {
                        Boolean _xHTTPs;
                        Action_Status_Text.setText("Detecting protocol.");
                        _xHTTPs = HTTP_HTTPs.checkHTTPs(_sIP);
                        if (_xHTTPs != null) {
                            Integer _iWebvisu;
                            Action_Status_Text.setText("Downloading \"webvisu.htm\".");
                            _iWebvisu = HTTP_HTTPs.downloadWebVisu(_xHTTPs, _sIP);
                            switch (_iWebvisu) {
                                case -1: // the webvisu is a HTML5 webvisu)
                                    LOGGER.log(Level.WARNING, "HTML5 webvisu detected at ''{0}'' ({1})!", new Object[]{_sAddress, _sIP});
                                    Action_Status_Text.setText("HTML5 webvisu detected!");
                                    return null;
                                case 0: // the webvisu is direct (port 80, path "/")
                                    LOGGER.log(Level.WARNING, "No webvisu at ''{0}'' ({1})!", new Object[]{_sAddress, _sIP});
                                    Action_Status_Text.setText("No WebVisu to display!");
                                    return null;
                                case 1: // the webvisu is direct (path "/")
                                    _sPath = "/";
                                    break;
                                case 2: // the webvisu is for a 750-88x (path "/plc/")
                                    _sPath = "/PLC/";
                                    break;
                                case 3: // the webvisu is for an PFC (path "/webvisu/")
                                    _sPath = "/webvisu/";
                                    break;
                                case 4: // the webvisu is for an IPC (path ":8080/")
                                    _sPath = ":8080/";
                                    break;
                            }
                            if (!LocalFiles.checkFile(WEBVISU_JAR)) {
                                Action_Status_Text.setText("Downloading \"" + WEBVISU_JAR + "\".");
                                HTTP_HTTPs.downloadURL(_xHTTPs, _sIP, _sPath, WEBVISU_JAR);
                            }
                            if (!LocalFiles.checkFile(MINML_JAR)) {
                                Action_Status_Text.setText("Downloading \"" + MINML_JAR + "\".");
                                HTTP_HTTPs.downloadURL(_xHTTPs, _sIP, _sPath, MINML_JAR);
                            }

                            //Create a new list of station to be filled by CSV file data 
                            ArrayList<AppletParameter> alAppletParameter;

                            alAppletParameter = ParseApplet.parseHtmFile();

                            Action_Status_Text.setText("Creating \"webclient_conf.ini\".");
                            CreateConf.writeConfFile(_xHTTPs, _sIP, _sPath, null, alAppletParameter);

                            Action_Status_Text.setText("Starting the WebVisu.");
                            
                            LocalFiles.cleanFiles("webvisu.htm");
                            
                            RunJavaWebVisu.runJavaWebVisu(_sIP);

                            Action_Status_Text.setText("Done.");
                        } else {
                            LOGGER.log(Level.WARNING, "HTTP/HTTPs not supported at ''{0}'' ({1})!", new Object[]{_sAddress, _sIP});
                            Action_Status_Text.setText("HTTP/HTTPs not supported!");
                        }
                    } else {
                        LOGGER.log(Level.WARNING, "Network Address ''{0}'' ({1}) unreachable!!", new Object[]{_sAddress, _sIP});
                        Action_Status_Text.setText("Network Address unreachable!");
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Network Address ''{0}'' incorrect!", _sAddress);
                    Action_Status_Text.setText("Incorrect Network Address!");
                }
                return null;
            }
            
            protected void process(String Test) {
                Action_Status_Text.setText(Test);
            }

            @Override
            public void done() {
                Submit_Button.setEnabled(true);
                Action_Status_Progress.setVisible(false);
            }
        };
        new Thread(backgroundTask).start();
        
    }//GEN-LAST:event_Submit_ButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainSwing().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Action_Status_Progress;
    private javax.swing.JLabel Action_Status_Text;
    private javax.swing.JTextField IP_Address_TextField;
    private javax.swing.JCheckBox No_Ping_CheckBox;
    private javax.swing.JButton Submit_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
