package affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MyCanvas extends JComponent {

	public static ArrayList<Integer> Chiffres;
	public static int widthFrame;
	public static int widthSqaure;

	public void paintComponent(Graphics g) {
		// Dessin des lignes
		g.setColor(Color.black);
		for (int i = 0; i < 10; i++) {
			// Ligne verticale
			g.drawLine(widthSqaure * i, 0, widthSqaure * i, widthFrame);
			// Ligne horizontale
			g.drawLine(0, widthSqaure * i, widthFrame, widthSqaure * i);
			if (i % 3 == 0 && i != 0 && i != 9) {
				g.drawLine(widthSqaure * i + 1, 0, widthSqaure * i + 1, widthFrame);
				g.drawLine(0, widthSqaure * i + 1, widthFrame, widthSqaure * i + 1);
			}
		}

		int x, y;
		while (true) {
			try {
				for (int i = 0; i < Chiffres.size(); i++) {
					x = (i % 9) * widthSqaure + 1;
					y = Math.round(i / 9) * widthSqaure + 1;
					g.setColor(Color.white);
					g.fillRect(x+1, y+1, widthSqaure - 2, widthSqaure - 2);
					g.setColor(Color.BLACK);
					g.drawString(Chiffres.get(i).toString(), x + Math.round(widthSqaure / 2) - 4,
							y + Math.round(widthSqaure / 2) + 2);
				}
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(widthFrame, widthFrame);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public static void main(String args[]) {

		widthFrame = 271;
		widthSqaure = 30;
		// Initialisation chiffres
		Chiffres = new ArrayList<>();
		for (int i = 0; i < 81; i++) {
			Chiffres.add(i % 9);
		}

		// ThreadAspirateur threadAspirateur = new ThreadAspirateur();
		// threadAspirateur.start();
		
		// Affichage
		JFrame mainFrame = new JFrame("Intelligence Artificielle - Sudoku");
		MyCanvas c = new MyCanvas();
		mainFrame.getContentPane().add(c);
		mainFrame.pack();
		mainFrame.setVisible(true);

		c.paintComponent(c.getGraphics());
	}

}
