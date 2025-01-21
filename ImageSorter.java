import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSorter extends JFrame{

    public BufferedImage image;
    public double [][] imageVals;
    public int delay=10;
    public Timer t;
    public boolean isSorted = false;


    public ImageSorter()
    {
        imageVals = new double[10000][10000];
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        loadImage("kuzy.jpeg");
        this.setSize(new Dimension(image.getWidth(),image.getHeight()));
        this.addKeyListener(new SpeedListener());
        displayImage();
        startAnimatedBubbleSort();
    }

    public void loadImage(String fileName)
    {
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayImage()
    {       JPanel pn = new JPanel();
            pn.add(new JLabel(new ImageIcon(image)));
            this.add(pn);
            this.repaint();
    }

    public static double rgbConverter(int pixel)
    {

            Color color = new Color(pixel, true);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            double rgbVal = 0.2126 * red + 0.7152 * green + 0.0722 * blue;
            return rgbVal;
    }

    public void horizontalStep()
    {
        isSorted = true;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth()-1; x++) {
               int pixel1 = image.getRGB(x,y);
               int pixel2 = image.getRGB(x+1,y);
               if(rgbConverter(pixel2)>rgbConverter(pixel1))
               {
                isSorted=false;
                image.setRGB(x, y, pixel2);
                image.setRGB(x+1, y, pixel1);

               }

            }}
    }

    class SpeedListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
          if(e.getKeyCode()==38)
          {
                delay+=10;
                t.setDelay(delay);
          }
          else if(e.getKeyCode()==40)
          {
                if(delay-10<=0)
                {
                    delay=0;
                }
                else 
                {
                    delay=delay-10;
                }
                t.setDelay(delay);
                
          }
          else if(e.getKeyCode()==KeyEvent.VK_A)
          {
               new ImageSorter();
          }
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }


}

    public void verticalStep()
    {
        isSorted=true;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight()-1; y++) {
               int pixel1 = image.getRGB(x,y);
               int pixel2 = image.getRGB(x,y+1);
               if(rgbConverter(pixel2)>rgbConverter(pixel1))
               {
                isSorted= false;
                image.setRGB(x, y, pixel2);
                image.setRGB(x, y+1, pixel1);
               }
            }}
    }
    
    public void diagonalStep()
    {
            horizontalStep();
            verticalStep();
            displayImage();
            
    }

    public void startAnimatedBubbleSort()
    {
             t = new Timer(delay,(e->{
            diagonalStep();
            if(isSorted)
            {   
                t.stop();
            }
        }));
        t.start();
    }

    public static void main(String[] args) {
        ImageSorter nd = new ImageSorter();
    }
}