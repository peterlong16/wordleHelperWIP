package Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    JButton button = new JButton("Submit");
    wordPanel wp;
    BoxPanel bp;

    GUI(){

        JPanel pan = new JPanel();
        button.setFocusable(false);
        pan.add(button, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(pan,BorderLayout.CENTER);
        bp = new BoxPanel();
        add(bp, BorderLayout.NORTH);
        wp = new wordPanel();
        add(wp, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bp.complete()) {
                    Helper.submit(BoxPanel.getLetters(), BoxPanel.getStates());
                    bp.cur=0;
                }
            }
        });

    }




}
