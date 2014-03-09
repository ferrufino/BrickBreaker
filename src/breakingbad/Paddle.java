/*
 *Class Paddle
 *
 *@Author Gustavo Ferrufino &&  Andrés Gutiérrez Castaño
 *@Matricula A00812572  && A01191581
 */

package breakingbad;

import java.awt.Image;

/**
 *
 * @author Andrés
 */
public class Paddle extends Base {

    private int speed;

    public Paddle(int posX, int posY, Image image) {
        super(posX, posY, image);

        speed = 5;

    }
    //Variables Strings
    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";
    
    
    /**
     * Metodo <I>speed</I> de la clase <code>Base</code>.
     *
     * @return regresa la velocidad del objeto paddle.
     *
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Metodo <I>setSpeed</I> de la clase <code>Base</code>,
     *
     * Modifica el valos de speed
     *
     */
    public void setSpeed( int num) {
        speed+=num;
    }

}
