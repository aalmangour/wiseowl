package indexGUI;


import com.wiseowl.WiseOwl.indexing.FeedData;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import static java.lang.System.exit;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import org.apache.solr.client.solrj.SolrServerException;

public class stop_pause extends javax.swing.JFrame {


        JFileChooser fc;
    File myfile;
    Thread t;
    RandomAccessFile rand = null;
    Writer out = null;
    FeedData feed=null;
    Boolean isPaused=false;
    int skip=0;
    public static int comp = 0;
    long now = 0;
     void bar()
     {
                   t=new Thread(new Runnable(){
                    public void run()
                    {   while(FeedData.running){
                        int i=0,flag=-1;
                        while(FeedData.running)
                        {
                     if(i==pb.getMaximum() || i==0)
                        flag*=-1;
                     // Update value
                     i+=flag;
                     pb.setValue(i);
                        try
                     {
                     // Get the effect
                     Thread.sleep(30);
                     }catch(Exception e){}
                     }  
                     }
                    return;
                    }
                   });
                   // Start thread
                   t.start();

     }
    public void resize(boolean flag)
    {
    	if(flag)
    	this.setBounds(350,200,600,370);
    	else
    		this.setBounds(350,200,600,240);
    }
    public stop_pause() {
        initComponents();
         bar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	//JOptionPane.showMessageDialog(rootPane, "Init Comps ");
        more = new javax.swing.JButton();
        pb = new javax.swing.JProgressBar();
        back = new javax.swing.JButton();
        start = new javax.swing.JButton();
        area = new javax.swing.JTextArea("",80,90);
        pane = new javax.swing.JScrollPane(area);
        getContentPane().add(pane);
        pane.setBounds(70, 150, 380, 160);
        pane.setVisible(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        back.setText("Index Another File");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        getContentPane().add(back);
        back.setBounds(20, 30, 140, 30);

        getContentPane().add(pb);
        pb.setBounds(70, 100, 380, 30);
        
        //getContentPane().add(area);
        
        more.setText("MORE");
        more.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreActionPerformed(evt);
            }
        });
        getContentPane().add(more);
        more.setBounds(70, 130, 70, 20);

        start.setText("STOP");
        //start.setEnabled(false);
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        getContentPane().add(start);
        start.setBounds(460, 100, 70, 30);
        setBounds(350, 200, 600, 240);
        
    }

    private void moreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
    	if(more.getText().equalsIgnoreCase("MORE")){
    		resize(true);
    		pane.setVisible(true);
    		more.setText("LESS");
    	}
    	else if(more.getText().equalsIgnoreCase("LESS")){
    		pane.setVisible(false);
    		resize(false);
    		more.setText("MORE");
    	}
        } 

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        FeedData.running = false;
        this.setVisible(false);
        FeedData.reset();
        indexing in=new indexing();
        in.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stopActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        if(start.getText().equalsIgnoreCase("START"))
        {
        	start.setText("STOP");
    	System.out.println("value of skip : "+FeedData.skip);
    	//start.setEnabled(false);
    	FeedData.running=true;
    	FeedData.isPaused=false;
    	bar();
        if((FeedData.skip)==-1) 
        {
            //completed
        	JOptionPane.showMessageDialog(rootPane, "File Already Indexed");
        }
        else if((FeedData.skip)==-2)
		{
        	try {
        		FeedData.skip=0;
				FeedData.addData(FeedData.randomAccessFile.length());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        Thread t1=new Thread(new Runnable(){
            public void run()
            {   
            	try {feed = new FeedData();
					feed.index();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SolrServerException e){
					e.printStackTrace();
				}
            	return;
            }});
           // Start thread
           t1.start();
     }
     else if(start.getText().equalsIgnoreCase("STOP"))
     {
    	 FeedData.running = false;
         FeedData.isPaused=true;
         start.setText("START");
     }
    }
    
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(stop_pause.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stop_pause.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stop_pause.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stop_pause.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stop_pause().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton more;
    private javax.swing.JProgressBar pb;
    private javax.swing.JButton start;
    private javax.swing.JButton back;
    public static javax.swing.JTextArea area;
    private javax.swing.JScrollPane pane;
    // End of variables declaration//GEN-END:variables
}