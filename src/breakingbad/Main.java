/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbad;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        BreakingBad juego = new BreakingBad();
        juego.setVisible(true);
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
