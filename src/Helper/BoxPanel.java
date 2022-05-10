package Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoxPanel extends JPanel {
    static Square[][] squares;
    static int ROWS = 6;
    static int COLS = 5;
    static int BOX_SIZE = 40;
    static int width = (BOX_SIZE * 6 )+ 20;
    static int height = (BOX_SIZE * 8 )+ 10;
    static String submitText = "";
    public int cur = 0;

    BoxPanel(){
        setFocusable(true);
        requestFocus();
        this.setPreferredSize(new Dimension(width,height));
        squares = new Square[6][5];

        int boxX = 0;
        int boxY = 10;



        for(int r = 0; r<ROWS;r++){

            boxX = 10;
            for(int c = 0;c<COLS;c++){

                squares[r][c] = new Square(boxX,boxY,r,c);
                boxX += BOX_SIZE + 10;
            }
            boxY += BOX_SIZE + 10;
        }

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                Square targetSquare = findSquare(e.getX(),e.getY());
                if(targetSquare!=null && targetSquare.row == Helper.turn){
                    targetSquare.switchState();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getExtendedKeyCode());
                char input = Character.toLowerCase(e.getKeyChar());
                System.out.println("PRESS");

                if(Helper.isAlphabetical(input) && cur!=5){
                    squares[Helper.turn][cur].setLetter(e.getKeyChar());
                    System.out.println(squares[Helper.turn][cur-1].letter);
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE && cur!=0){
                    cur--;
                    squares[Helper.turn][cur].deleteLetter();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


    }

    Square findSquare(int x, int y){
        for(int r = 0; r<ROWS;r++){
            for(int c = 0;c<COLS;c++){
                Square square = squares[r][c];
                if(x>square.x && x<square.x2 &&
                   y>square.y && y<square.y2){
                    return square;
                }
            }
        }

        return null;
    }

    static public Character[] getLetters(){

        Character[] Letters = new Character[5];

        for(int i = 0;i<COLS;i++){
            Letters[i] = squares[Helper.turn][i].letter;
        }

        return Letters;
    }

    static public int[] getStates(){
        int[] states = new int[5];

        for(int i = 0;i<COLS;i++){
            states[i] = squares[Helper.turn][i].state;
        }

        return states;
    }

    public boolean complete(){

        for(int i = 0;i<COLS;i++){
            if(squares[Helper.turn][i].state == 0){
                return false;
            }
        }

        return squares[Helper.turn][4].letter!=null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       Graphics2D g2 = (Graphics2D) g;
        for(int r = 0; r<ROWS;r++){
            for(int c = 0;c<COLS;c++){
                g2.setColor(Color.black);
                g2.drawRect((int)squares[r][c].x,(int)squares[r][c].y,BOX_SIZE,BOX_SIZE);

                switch (squares[r][c].state) {
                    case 0 -> g.setColor(Color.white);
                    case 1 -> g.setColor(Color.yellow);
                    case 2 -> g.setColor(Color.green);
                    case 3 -> g.setColor(Color.gray);
                }

                g2.fillRect((int)squares[r][c].x,(int)squares[r][c].y,BOX_SIZE,BOX_SIZE);

                if(squares[r][c].letter!=null){
                    g2.setColor(Color.black);
                    g2.setFont(new Font("sans-serif",Font.BOLD,BOX_SIZE));
                    g2.drawString(String.valueOf(Character.toUpperCase(squares[r][c].letter)),
                            (int)squares[r][c].x + (BOX_SIZE/2) - (g2.getFontMetrics().stringWidth(String.valueOf(Character.toUpperCase(squares[r][c].letter)))/2),
                            (int)squares[r][c].y + ((BOX_SIZE/2) + g2.getFontMetrics().getHeight()/4));
                    repaint();
                }
            }
        }
    }

    class Square {
        float x;
        float y;
        float x2;
        float y2;
        int row;
        int column;
        // 0 = neutral, 1 = wrong place, 2 = right place, 3 = incorrect
        int state;
        int size;
        Character letter;

        Square(float x, float y, int row, int col){
            this.x = x;
            this.y = y;
            this.size = BOX_SIZE;
            this.x2 = x + size;
            this.y2 = y + size;
            this.state = 0;
            this.letter = null;
            this.row = row;
            this.column = col;
        }

        void setLetter(Character c){

            this.letter = c;
            cur++;
            repaint();
        }

        void deleteLetter(){
            this.letter = null;
            repaint();
        }

        void switchState(){
            state++;
            if(state>3){
                state = 0;
            }
            repaint();
        }
    }
}
