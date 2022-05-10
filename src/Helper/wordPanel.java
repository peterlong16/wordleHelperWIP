package Helper;

import javax.swing.*;
import java.awt.*;

public class wordPanel extends JPanel {

    static String[] bestWords;

    wordPanel(){

        this.setPreferredSize(new Dimension(BoxPanel.width,200));
        setBestWords();
    }

    void setBestWords(){
        bestWords = Helper.top5();
        for(String s:bestWords){
            System.out.println(s);

        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(1,1,BoxPanel.width - 3,190);
        int texty = 15;
        g.drawString("Try these words:",(BoxPanel.width/2) - (g.getFontMetrics().stringWidth("Try these words:")/2),texty);
        for(String str:bestWords){
            texty+=g.getFontMetrics().getHeight();
            g.drawString(str,(BoxPanel.width/2) - (g.getFontMetrics().stringWidth(str)/2),texty);
        }

    }
}
