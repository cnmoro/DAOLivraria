
package daolivraria;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Controle {
    
    public static void main(String[] args) {
                java.awt.EventQueue.invokeLater(new Runnable() {  
	           public void run() {  
	                try {   
	                     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
	                } catch (UnsupportedLookAndFeelException ex) {  
	                     ex.printStackTrace();  
	                } catch (IllegalAccessException ex) {  
	                     ex.printStackTrace();  
	                } catch (InstantiationException ex) {  
	                     ex.printStackTrace();  
	                } catch (ClassNotFoundException ex) {  
	                     ex.printStackTrace();  
	                }  
	                new GUI().setVisible(true);  

	           }  
	      });  
    }

}
