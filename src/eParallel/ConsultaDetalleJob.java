package eParallel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.Element;
import javax.swing.JComponent;
import javax.swing.JEditorPane;


public class ConsultaDetalleJob extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaDetalleJob frame = new ConsultaDetalleJob();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConsultaDetalleJob() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1028, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		//contentPane.setLayout(null);
		add(new MyCanvas(), BorderLayout.CENTER);
        contentPane.setVisible(true);
        setVisible(true);
        
		final MyComponent component = new MyComponent(400, 400);
        component.setOriginAndSize(10, 10, 100, 100);
        component.paint(getGraphics());

       // contentPane.add(component, BorderLayout.CENTER);
        //pack();
        //contentPane.setVisible(true);
//        //editorPane.add(component);
//        contentPane.add(new JComponent() {  // Not a typo - this is some advanced magic called an "anonymous class".
//            Rectangle myBox = box;    // Give the component a reference to our box object.
//            public void paint(Graphics g) {
//                g.drawRect(myBox.x, myBox.y, myBox.width, myBox.height);
//            }
//        });
        
   	}
	
	class MyComponent extends JComponent {
	    private int x;
	    private int y;
	    private int width;
	    private int height;
	    private int compWidth;
	    private int compHeight;

	    MyComponent(int w, int h) {
	        this.compWidth = w;
	        this.compHeight = h;
	    }

	    public void setOriginAndSize(int x, int y, int w, int h) {
	        this.x = x;
	        this.y = y;
	        this.width = w;
	        this.height = h;
	        repaint();
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return (new Dimension(compWidth, compHeight));
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(getBackground());
	        g.fillRect(x, y, width, height);
	        //g.setColor(Color.RED);
	        //g.drawOval(x, y, width, height);
	        //g.setColor(Color.MAGENTA);
	        //g.fillOval(x, y, width, height);
	    }
	}
	
	class MyCanvas extends JComponent {

	    public void paint(Graphics g) {
	    	g.setColor(Color.RED);
	        g.fillRect(105, 15, 10, 10);
	    	g.drawRect (100, 10, 20, 20);
	    }
	}
}
