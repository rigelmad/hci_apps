import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainWindow {
   public static void main(String[] args) {
      createWindow();
   }

   private static void createWindow() {    
      JFrame frame = new JFrame("Welcome to Rosemary");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      createUI(frame);
      frame.setSize(860, 500);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);
   }

   private static void createUI(final JFrame frame){  
        JPanel panel = new JPanel();
        LayoutManager layout = new BorderLayout();  
        panel.setLayout(layout);       

        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = makePanel1(frame);
        tabbedPane.addTab("Home", panel1);
        JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("My Receipes", panel2);
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Browse", panel3);

      
        panel.add(tabbedPane, BorderLayout.NORTH);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
     
      
        frame.getContentPane().add(panel, BorderLayout.CENTER);    
   } 
   
   private static JComponent makePanel1(final JFrame frame) {
       JPanel panel1 = new JPanel(new GridLayout());
       JButton button = new JButton("Terms and Conditions");
       final JDialog modelDialog = createDialog(frame);
       button.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              modelDialog.setVisible(true);
           }
        });

        panel1.add(button, BorderLayout.SOUTH);
        return panel1;
   }
   
   private static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

   private static JDialog createDialog(final JFrame frame){
      final JDialog modelDialog = new JDialog(frame, "Welcome to Rosemary");
      modelDialog.setBounds(132, 132, 300, 200);
      Container dialogContainer = modelDialog.getContentPane();
      dialogContainer.setLayout(new BorderLayout());
      dialogContainer.add(new JLabel(printTermsAndConditions())
      , BorderLayout.CENTER);    
      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      JButton okButton = new JButton("Ok");
      okButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            modelDialog.setVisible(false);
         }
      });

      panel1.add(okButton);
      dialogContainer.add(panel1, BorderLayout.SOUTH);

      return modelDialog;
   }
   
   private static String printTermsAndConditions() {
       return "Terms and conditions apply. Member FDIC.";
   }
}