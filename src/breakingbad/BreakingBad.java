/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbad;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class BreakingBad extends JFrame implements KeyListener, Runnable,
        MouseListener {

    private SoundClip tema;
    private SoundClip explosion;
    private static final long serialVersionUID = 1L;

    private ImageIcon fondo;
    private Image gameOver;
    private ImageIcon walter;
    private ImageIcon startGame;
    private LinkedList<Block> lista;
    private int posX;
    private int posY;
    private int posrX;
    private int posrY;
    private int poslX;
    private int poslY;
    private int vidas;
    private int direccion;
    private int direccion2;
    private int cont;
    private Paddle barra;
    private Block brick;
    private Ball ball;
    private Boolean pausa;
    private Boolean empieza;
    private Boolean choca;
    private Boolean colision;
    private Boolean perdiste;
    private Boolean ganaste;
    private Boolean reiniciar;
    private Boolean sonido;
    private Boolean startScreen;
    private Image dbImage;
    private Graphics dbg;
    private long tiempoActual;
    private long tiempoInicial;
    private Animacion barraAnimWalterLeft;
    private Animacion barraAnimWalterRight;
    private Animacion barraAnimWalterSt;
    private Animacion barraAnimJesseRight;
    private Animacion barraAnimJesseLeft;
    private Animacion barraAnimJesseSt;
    private Animacion ballAnim;
    private Animacion ballWalter;
    private Animacion animBrickFlask;
    private Animacion animBrickMoney;
    private Rectangle rectangleWalter;
    private Rectangle rectangleJesse;
    private Image vidasWalter;
    private Image vidasJesse;
    private Image vatilloFumado;
    private boolean clickWalter;
    private boolean clickJesse;


    public BreakingBad() {
        clickWalter = clickJesse = false;
        startScreen = true;
        sonido = true;
        vidas = 3;
        tema = new SoundClip("Sounds/explosion.wav");
        explosion = new SoundClip("Sounds/explosion.wav");
        cont = 0;
        rectangleWalter = new Rectangle(245, 470, 165, 280);
        rectangleJesse = new Rectangle(570, 470, 165, 280);
        setSize(1000, 800);
        addKeyListener(this);
        addMouseListener(this);
        posX = 253;
        posY = (int) (getHeight()-120);
        poslX = 260 ;
        poslY = (int) (getHeight() - 160);
        //Se declaran las imagenes
        URL vwURL = this.getClass().getResource("Images/vidasWalter.png");
        vidasWalter = Toolkit.getDefaultToolkit().getImage(vwURL);
        URL vjURL = this.getClass().getResource("Images/jesseface.png");
        vidasJesse = Toolkit.getDefaultToolkit().getImage(vjURL);
        URL vfURL = this.getClass().getResource("Images/fumado.gif");
        vatilloFumado = Toolkit.getDefaultToolkit().getImage(vfURL);
        
        
        URL cURL = this.getClass().getResource("Images/prueba1.png");
        fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cURL));
        URL gURL = this.getClass().getResource("Images/gameover.png");
        gameOver = Toolkit.getDefaultToolkit().getImage(gURL);
        URL sURL = this.getClass().getResource("Images/welcomeView.png");
        startGame = new ImageIcon(Toolkit.getDefaultToolkit().getImage(sURL));
        URL wURL = this.getClass().getResource("Images/walter.png");
        walter = new ImageIcon(Toolkit.getDefaultToolkit().getImage(wURL));
        URL rURL = this.getClass().getResource("Images/flask.png");
        URL hURL = this.getClass().getResource("Images/walter.png");
        URL pURL = this.getClass().getResource("Images/polloshermanos1.png");
        barra = new Paddle(posX, posY, Toolkit.getDefaultToolkit().getImage(hURL));
        ball = new Ball(poslX, poslY, Toolkit.getDefaultToolkit().getImage(pURL));
        posY = (int) (getHeight() - 150);
        empieza = false;
        reiniciar = false;
        perdiste = false;
        ganaste = false;
        choca = false;
        direccion = 0;
        direccion2 = 1;
        pausa = false;
        lista = new LinkedList<Block>();
        int l = 0;
        int t = 0;
        int s = 0;
        for (int i = 0; i < 15; i++) {

            if (i < 5) {

                posrX = (int) ((getWidth() / 6) + l - 17);    //posision x es tres cuartos del applet
                posrY = 100;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                l += (int) (getWidth() / 6);
            }

            if (i > 4 && i < 10) {

                posrX = (int) ((getWidth() / 6) + t - 17);    //posision x es tres cuartos del applet
                posrY = 200;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                t += (int) (getWidth() / 6);
            }

            if (i > 9) {

                posrX = (int) ((getWidth() / 6) + s - 17);    //posision x es tres cuartos del applet
                posrY = 300;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                s += (int) (getWidth() / 6);
            }
        }
        
        //se empieza a declarar e inicializar animaicones
        Image barraAnimWalterL1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterLeft1.png"));
        Image barraAnimWalterL2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterLeft2.png"));

        barraAnimWalterLeft = new Animacion();
        barraAnimWalterLeft.sumaCuadro(barraAnimWalterL1, 100);
        barraAnimWalterLeft.sumaCuadro(barraAnimWalterL2, 100);

        Image ballWalter1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth.png"));
        Image ballWalter2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth1.png"));
        Image ballWalter3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth2.png"));
        Image ballWalter4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth3.png"));

        ballWalter = new Animacion();
        ballWalter.sumaCuadro(ballWalter1, 50);
        ballWalter.sumaCuadro(ballWalter2, 50);
        ballWalter.sumaCuadro(ballWalter3, 50);
        ballWalter.sumaCuadro(ballWalter4, 50);

        Image barraAnimWalterR1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterRight1.png"));
        Image barraAnimWalterR2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterRight2.png"));

        barraAnimWalterRight = new Animacion();
        barraAnimWalterRight.sumaCuadro(barraAnimWalterR1, 100);
        barraAnimWalterRight.sumaCuadro(barraAnimWalterR2, 100);

        Image barraAnimWalterS = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterLeft.png"));
        Image barraAnimWalterS1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterRight.png"));

        barraAnimWalterSt = new Animacion();
        barraAnimWalterSt.sumaCuadro(barraAnimWalterS, 300);
        barraAnimWalterSt.sumaCuadro(barraAnimWalterS1, 300);

        Image barraAnimJesseL1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseLeft1.png"));
        Image barraAnimJesseL2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseLeft2.png"));

        barraAnimJesseLeft = new Animacion();
        barraAnimJesseLeft.sumaCuadro(barraAnimJesseL1, 100);
        barraAnimJesseLeft.sumaCuadro(barraAnimJesseL2, 100);

        Image barraAnimJesseR1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseRight1.png"));
        Image barraAnimJesseR2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseRight2.png"));

        barraAnimJesseRight = new Animacion();
        barraAnimJesseRight.sumaCuadro(barraAnimJesseR1, 100);
        barraAnimJesseRight.sumaCuadro(barraAnimJesseR2, 100);

        Image barraAnimJesseS = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseLeft.png"));
        Image barraAnimJesseS1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/jesseRight.png"));

        barraAnimJesseSt = new Animacion();
        barraAnimJesseSt.sumaCuadro(barraAnimJesseS, 300);
        barraAnimJesseSt.sumaCuadro(barraAnimJesseS1, 300);

        Image ballAnim1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/polloshermanos1.png"));
        Image ballAnim2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/polloshermanos2.png"));
        Image ballAnim3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/polloshermanos3.png"));
        Image ballAnim4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/polloshermanos4.png"));

        ballAnim = new Animacion();
        ballAnim.sumaCuadro(ballAnim1, 100);
        ballAnim.sumaCuadro(ballAnim2, 100);
        ballAnim.sumaCuadro(ballAnim3, 100);
        ballAnim.sumaCuadro(ballAnim4, 100);

        Image brickFlask = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/flask.png"));
        Image brickMoney = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/dollarBill.png"));
        // Image brickAnim2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/flask.png"));
        //Image brickAnim3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/flask.png"));
        //Image brickAnim4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/flask.png"));

        animBrickFlask = new Animacion();
        animBrickFlask.sumaCuadro(brickFlask, 100);

        animBrickMoney = new Animacion();
        animBrickMoney.sumaCuadro(brickMoney, 100);
       // brickAnim.sumaCuadro(brickAnim2, 100);
        // brickAnim.sumaCuadro(brickAnim3, 100);
        // brickAnim.sumaCuadro(brickAnim4, 100);

        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();

    }

    public void run() {
        while (true) {
            if (!pausa) {

                actualiza();
                checaColision();
            }

            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.

            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    public void actualiza() {
        if (cont == 15) {
            ganaste = true;
            cont = 0;
        }

        if (direccion == 1) {
            barra.setPosX(barra.getPosX() - 3);
            if (!empieza && !choca) {
                ball.setPosX(ball.getPosX() - 3);
            }
        }
        if (direccion == 2) {
            barra.setPosX(barra.getPosX() + 3);
            if (!empieza && !choca) {
                ball.setPosX(ball.getPosX() + 3);
            }
        }
        if (empieza) {
            if (direccion2 == 1) {
                if (cont < 2) {
                    ball.setPosX(ball.getPosX() + 2);
                    ball.setPosY(ball.getPosY() - 2);
                }
                if (cont > 1 && cont < 5) {
                    ball.setPosX(ball.getPosX() + 3);
                    ball.setPosY(ball.getPosY() - 3);
                }
                if (cont > 4 && cont < 9) {
                    ball.setPosX(ball.getPosX() + 4);
                    ball.setPosY(ball.getPosY() - 4);
                }
                if (cont > 8) {
                    ball.setPosX(ball.getPosX() + 5);
                    ball.setPosY(ball.getPosY() - 5);
                }
            }
            if (direccion2 == 2) {
                if (cont < 2) {
                    ball.setPosX(ball.getPosX() - 2);
                    ball.setPosY(ball.getPosY() - 2);
                }
                if (cont > 1 && cont < 5) {
                    ball.setPosX(ball.getPosX() - 3);
                    ball.setPosY(ball.getPosY() - 3);
                }
                if (cont > 4 && cont < 9) {
                    ball.setPosX(ball.getPosX() - 4);
                    ball.setPosY(ball.getPosY() - 4);
                }
                if (cont > 8) {
                    ball.setPosX(ball.getPosX() - 5);
                    ball.setPosY(ball.getPosY() - 5);
                }

            }
            if (direccion2 == 3) {
                if (cont < 2) {
                    ball.setPosX(ball.getPosX() - 2);
                    ball.setPosY(ball.getPosY() + 2);
                }
                if (cont > 1 && cont < 5) {
                    ball.setPosX(ball.getPosX() - 3);
                    ball.setPosY(ball.getPosY() + 3);
                }
                if (cont > 4 && cont < 9) {
                    ball.setPosX(ball.getPosX() - 4);
                    ball.setPosY(ball.getPosY() + 4);
                }
                if (cont > 8) {
                    ball.setPosX(ball.getPosX() - 5);
                    ball.setPosY(ball.getPosY() + 5);
                }
            }
            if (direccion2 == 4) {
                if (cont < 2) {
                    ball.setPosX(ball.getPosX() + 2);
                    ball.setPosY(ball.getPosY() + 2);
                }
                if (cont > 1 && cont < 5) {
                    ball.setPosX(ball.getPosX() + 3);
                    ball.setPosY(ball.getPosY() + 3);
                }
                if (cont > 4 && cont < 9) {
                    ball.setPosX(ball.getPosX() + 4);
                    ball.setPosY(ball.getPosY() + 4);
                }
                if (cont > 8) {
                    ball.setPosX(ball.getPosX() + 5);
                    ball.setPosY(ball.getPosY() + 5);
                }
            }
        }

        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        //Actualiza la animacion en base al tiempo transcurrido
        barraAnimWalterLeft.actualiza(tiempoTranscurrido);
        barraAnimWalterRight.actualiza(tiempoTranscurrido);
        barraAnimWalterSt.actualiza(tiempoTranscurrido);
        ballAnim.actualiza(tiempoTranscurrido);
        barraAnimJesseSt.actualiza(tiempoTranscurrido);
        barraAnimJesseLeft.actualiza(tiempoTranscurrido);
        barraAnimJesseRight.actualiza(tiempoTranscurrido);
        ballWalter.actualiza(tiempoTranscurrido);
        //brickAnim.actualiza(tiempoTranscurrido);

    }

    public void checaColision() {

        if (barra.getPosX() < 0) {
            barra.setPosX(0);
            if(!empieza){
                ball.setPosX(0);
            }
            choca = true;
        }

        if (barra.getPosX() + barra.getAncho() > getWidth()) {
            barra.setPosX(getWidth() - barra.getAncho());
            if(!empieza) {
                ball.setPosX(getWidth() - ball.getAncho());
                
            }
            choca = true;
        }

        if (ball.getPosX() + ball.getAncho() > getWidth() && direccion2 == 1) {
            direccion2 = 2;
        }
        if (ball.getPosX() + ball.getAncho() > getWidth() && direccion2 == 4) {
            direccion2 = 3;
        }
        if (ball.getPosX() < 0 && direccion2 == 2) {
            direccion2 = 1;
        }
        if (ball.getPosX() < 0 && direccion2 == 3) {
            direccion2 = 4;
        }
        if (ball.getPosY() < 0 && direccion2 == 2) {
            direccion2 = 3;
        }
        if (ball.getPosY() < 0 && direccion2 == 1) {
            direccion2 = 4;
        }
        if (ball.getPosY() + ball.getAlto() > getHeight()) {

            if (vidas > 0) {
                vidas--;
                ball.setPosY(getHeight() - 160);
                ball.setPosX(barra.getPosX() + 10);
                empieza = false;

            } else if (vidas == 0) {
                perdiste = true;
            }

        }
        if (ball.intersecta(barra)) {
            if (direccion2 == 3) {
                direccion2 = 2;
            }
            if (direccion2 == 4) {
                direccion2 = 1;
            }
        }
        for (int i = 0; i < lista.size(); i++) {
            Block brickI = (Block) (lista.get(i));
            if (ball.intersecta(brickI) && direccion2 == 1) {
                direccion2 = 4;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                explosion.play();
                cont++;
            }

            if (ball.intersecta(brickI) && direccion2 == 2) {
                direccion2 = 3;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                explosion.play();
                cont++;
            }

            if (ball.intersecta(brickI) && direccion2 == 3) {
                direccion2 = 2;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                explosion.play();
                cont++;
            }

            if (ball.intersecta(brickI) && direccion2 == 4) {
                direccion2 = 1;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                cont++;
                explosion.play();
            }
        }
    }

    public void paint(Graphics g) {
        //Inicializa el DoubleBuffer

        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        //Actualiza la imagen de fondo
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        //Actualiza el Foreground
        dbg.setColor(getForeground());
        paint1(dbg);
        //Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paint1(Graphics g) {

        if (startScreen) {
            g.setColor(Color.cyan);
            g.setFont(new Font("Avenir Black", Font.ITALIC, 30));
            g.drawImage(startGame.getImage(), 0, 0, this);


        } else if (vidas > 0) {

            if (sonido) {
                tema.play();
                sonido = false;
            }
            g.drawImage(fondo.getImage(), 0, 0, this);
            if (clickWalter) {
                if (direccion == 0) {
                    g.drawImage(barraAnimWalterSt.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direccion == 1) {
                    g.drawImage(barraAnimWalterLeft.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direccion == 2) {
                    g.drawImage(barraAnimWalterRight.getImagen(), barra.getPosX(), barra.getPosY(), this);
                }

                for (int i = 0; i < lista.size(); i++) {
                    brick = (Block) (lista.get(i));
                    g.drawImage(animBrickFlask.getImagen(), brick.getPosX(), brick.getPosY(), this);
                }
                g.drawImage(ballWalter.getImagen(), ball.getPosX(), ball.getPosY(), this);
                //Controla las vidas a desplegar
                if (vidas == 3) {
                    g.drawImage(vidasWalter, 850, 50, this);
                    g.drawImage(vidasWalter, 900, 50, this);
                    g.drawImage(vidasWalter, 950, 50, this);
                } else if (vidas == 2) {
                    g.drawImage(vidasWalter, 900, 50, this);
                    g.drawImage(vidasWalter, 950, 50, this);
                } else if (vidas == 1) {
                    g.drawImage(vidasWalter, 950, 50, this);
                }
            } else if (clickJesse) {
                if (direccion == 0) {
                    g.drawImage(barraAnimJesseSt.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direccion == 1) {
                    g.drawImage(barraAnimJesseLeft.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direccion == 2) {
                    g.drawImage(barraAnimJesseRight.getImagen(), barra.getPosX(), barra.getPosY(), this);
                }

                for (int i = 0; i < lista.size(); i++) {
                    brick = (Block) (lista.get(i));
                    g.drawImage(animBrickMoney.getImagen(), brick.getPosX(), brick.getPosY(), this);
                }
                g.drawImage(ballAnim.getImagen(), ball.getPosX(), ball.getPosY(), this);
                //controla las vidas a desplegar
                 if (vidas == 3) {
                g.drawImage(vidasJesse, 850, 50, this);
                g.drawImage(vidasJesse, 900, 50, this);
                g.drawImage(vidasJesse, 950, 50, this);
            } else if (vidas == 2) {
                g.drawImage(vidasJesse, 900, 50, this);
                g.drawImage(vidasJesse, 950, 50, this);
            } else if (vidas == 1) {
                g.drawImage(vidasJesse, 950, 50, this);
            }
            }

           
            //g.drawImage(Meth.getImagen(), brick.getPosX(),brick.getPosY(), this);
        } else {
                //Se acabo el juego
                g.drawImage(gameOver, 0, 0, this);
                g.drawImage(vatilloFumado, 325, 350, this);
                tema.stop();
           
        }

    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P ) {
            pausa = !pausa;
        }
        if (!pausa) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direccion = 1;
                if (choca) {
                    choca = false;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direccion = 2;
                if (choca) {
                    choca = false;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                empieza = true;
                if (vidas == 3) {
                    direccion2 = 2; 
                } else if (vidas == 2) {
                    direccion2 = 1;
                } else {
                    direccion2 = 2;
                }
                
                
            }

            if (e.getKeyCode() == KeyEvent.VK_R) {
                
                reinicia();
            }
        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        direccion = 0;

    }

    public void mouseClicked(MouseEvent e) {
        if (rectangleWalter.contains(e.getX(), e.getY()) && startScreen == true) {
            clickWalter = !clickWalter;
            startScreen = false;
        } else if (rectangleJesse.contains(e.getX(), e.getY()) && startScreen == true) {
            clickJesse = !clickJesse;
            startScreen = false;
        }
    }

    public void mouseEntered(MouseEvent e) { //metodo cuando entra el mouse

    }

    public void mouseExited(MouseEvent e) { //metodo cuando sale el mouse

    }

    public void mousePressed(MouseEvent e) {    //metodo cuando el mouse es presionado

    }

    public void mouseReleased(MouseEvent e) {//metodo cuando el mouse es soltado

    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

    }

    public void reinicia() {
        sonido = true;
        tema = new SoundClip("Sounds/explosion.wav");
        explosion = new SoundClip("Sounds/explosion.wav");
        cont = 0;
        setSize(1000, 800);
        addKeyListener(this);
        posX = 253;
        posY = (int) (getHeight()-120);
        poslX = 260 ;
        poslY = (int) (getHeight() - 160);
        URL cURL = this.getClass().getResource("Images/prueba1.png");
        fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cURL));
        URL gURL = this.getClass().getResource("Images/gameover.png");
        gameOver = Toolkit.getDefaultToolkit().getImage(gURL);
        URL rURL = this.getClass().getResource("Images/flask.png");
        URL hURL = this.getClass().getResource("Images/walter.png");
        URL pURL = this.getClass().getResource("Images/polloshermanos1.png");
        barra = new Paddle(posX, posY, Toolkit.getDefaultToolkit().getImage(hURL));
        ball = new Ball(poslX, poslY, Toolkit.getDefaultToolkit().getImage(pURL));
        empieza = false;
        reiniciar = false;
        perdiste = false;
        ganaste = false;
        choca = false;
        direccion = 0;
        direccion2 = 1;
        pausa = false;
        lista = new LinkedList<Block>();
        int l = 0;
        int t = 0;
        int s = 0;
        vidas=3;
        for (int i = 0; i < 15; i++) {

            if (i < 5) {

                posrX = (int) ((getWidth() / 6) + l - 17);    //posision x es tres cuartos del applet
                posrY = 100;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                l += (int) (getWidth() / 6);
            }

            if (i > 4 && i < 10) {

                posrX = (int) ((getWidth() / 6) + t - 17);    //posision x es tres cuartos del applet
                posrY = 200;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                t += (int) (getWidth() / 6);
            }

            if (i > 9) {

                posrX = (int) ((getWidth() / 6) + s - 17);    //posision x es tres cuartos del applet
                posrY = 300;    //posision y es tres cuartos del applet
                brick = new Block(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                s += (int) (getWidth() / 6);
            }
        }

    }

}
