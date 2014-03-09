/*
 *Class BreakingBad
 *
 *@Author Gustavo Ferrufino && Andrés Gutiérrez Castaño
 *@Matricula A00812572 && A01191581
 */
package breakingbad;
 //Clases importadas

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

    //Declaracion de Variables
    private static final long serialVersionUID = 1L;

    private ImageIcon fondo;
    private Image gameOver;
    private ImageIcon walter;
    private ImageIcon startGame;
    private LinkedList<Block> lista;
    private LinkedList<Block> listaBricks;
    private SoundClip theme;
    private SoundClip failSound;
    private SoundClip chocan;

    private int vidas;
    private int direction;
    private int ballDirection;
    private int cont;
    private int score;
    private int posX;
    private int posY;
    private int posXB;
    private int posYB;
    private int posXr;
    private int posYr;

    private Paddle barra;
    private Block brick;
    private Block bricknivel;
    private Ball ball;
    private Boolean pausa;
    private Boolean empieza;
    private Boolean choca;

    private Boolean ganaste;
    private Boolean nivel2;
    private Boolean sonido;
    private Boolean startScreen;
    private Image dbImage;
    private Graphics dbg;
    private long tiempoActual;
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
    private URL bmURL;

    /**
     * Metodo <I>BreakingBad()</I> de la clase <code>BreakingBad</code>. Es el
     * constructor de la clase donde se definen las variables
     */
    public BreakingBad() {
        clickWalter = clickJesse = false;
        startScreen = true;
        sonido = true;
        vidas = 3;
        score = 0;
        nivel2 = false;
        //se carga audio
        theme = new SoundClip("Sounds/Breaking_Bad.wav");
        theme.setLooping(true);
        chocan = new SoundClip("Sounds/fail-buzzer-03.wav");
        failSound = new SoundClip("Sounds/ping.wav");
        cont = 0;
        rectangleWalter = new Rectangle(245, 470, 165, 280);
        rectangleJesse = new Rectangle(570, 470, 165, 280);
        setSize(1000, 800);
        addKeyListener(this);
        addMouseListener(this);
        posX = 253;
        posY = (int) (getHeight() - 120);
        posXB = posX + 10;
        posYB = (int) (getHeight() - 160);

        //Se declaran las imagenes
        URL vwURL = this.getClass().getResource("Images/vidasWalter.png");
        vidasWalter = Toolkit.getDefaultToolkit().getImage(vwURL);
        URL vjURL = this.getClass().getResource("Images/jesseface.png");
        vidasJesse = Toolkit.getDefaultToolkit().getImage(vjURL);
        URL vfURL = this.getClass().getResource("Images/fumado.gif");
        vatilloFumado = Toolkit.getDefaultToolkit().getImage(vfURL);

        URL abURL = this.getClass().getResource("Images/fondo1.png");
        fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(abURL));
        URL gURL = this.getClass().getResource("Images/gameover.png");
        gameOver = Toolkit.getDefaultToolkit().getImage(gURL);
        URL sURL = this.getClass().getResource("Images/welcomeView.png");
        startGame = new ImageIcon(Toolkit.getDefaultToolkit().getImage(sURL));
        URL wURL = this.getClass().getResource("Images/walter.png");
        walter = new ImageIcon(Toolkit.getDefaultToolkit().getImage(wURL));
        URL rURL = this.getClass().getResource("Images/flask.png");
        URL bmURL = this.getClass().getResource("Images/beakerMeth.png");
        URL hURL = this.getClass().getResource("Images/walter.png");
        URL pURL = this.getClass().getResource("Images/polloshermanos1.png");
        barra = new Paddle(posX, posY, Toolkit.getDefaultToolkit().getImage(hURL));
        ball = new Ball(posXB, posYB, Toolkit.getDefaultToolkit().getImage(pURL));

        posY = (int) (getHeight() - 150);
        empieza = false;

        ganaste = false;
        choca = false;
        direction = 0;
        ballDirection = 1;
        pausa = false;
        lista = new LinkedList<Block>();

        int diffBrickr1 = 0;
        int diffBrickr2 = 0;
        int diffBricker3 = 0;

        //se cargan bricks
        for (int i = 0; i < 29; i++) {

            if (i < 12) {

                posXr = (int) ((getWidth() / 30 - 10) + diffBrickr1);    //posision x es tres cuartos del applet
                posYr = 275;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr1 += (int) (getWidth() / 12);
            }

            if (i > 11 && i <= 19) {

                posXr = (int) ((getWidth() / 28 - 10) + diffBrickr2);    //posision x es tres cuartos del applet
                posYr = 350;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr2 += (int) (getWidth() / 9);
            }

            if (i > 20 && i <= 29) {

                posXr = (int) ((getWidth() / 8 + 10) + diffBricker3);    //posision x es tres cuartos del applet
                posYr = 425;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBricker3 += (int) (getWidth() / 9);
            }

        }

        //se empieza a declarar e inicializar animaicones
        Image barraAnimWalterL1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterLeft1.png"));
        Image barraAnimWalterL2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/walterLeft2.png"));

        barraAnimWalterLeft = new Animacion();
        barraAnimWalterLeft.sumaCuadro(barraAnimWalterL1, 100);
        barraAnimWalterLeft.sumaCuadro(barraAnimWalterL2, 100);

        Image ballWalter1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth.png"));
        Image ballWalter2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth2.png"));
        Image ballWalter3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth3.png"));
        Image ballWalter4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth4.png"));

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

        animBrickFlask = new Animacion();
        animBrickFlask.sumaCuadro(brickFlask, 100);

        animBrickMoney = new Animacion();
        animBrickMoney.sumaCuadro(brickMoney, 100);

        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();

    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
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

    /**
     * Metodo usado para Actualizar la posicion de objetos Paddle, Ball y Block.
     *
     */
    public void actualiza() {
        if (cont == 28 && !nivel2) {
            nuevoNivel();
        }

        if (nivel2 && cont == 31) {
            vidas = 0;
            ganaste = true;

        }

        if (direction == 1) {
            barra.setPosX(barra.getPosX() - barra.getSpeed());
            if (!empieza && !choca) {
                ball.setPosX(ball.getPosX() - 5);
            }
        }
        if (direction == 2) {
            barra.setPosX(barra.getPosX() + barra.getSpeed());
            if (!empieza && !choca) {
                ball.setPosX(ball.getPosX() + 5);
            }
        }
        if (empieza) {
            if (ballDirection == 1) {
                if (cont < 13) {
                    ball.setPosX(ball.getPosX() + 2);
                    ball.setPosY(ball.getPosY() - 2);
                    // barra.setSpeed(barra.getSpeed()+2);
                }
                if (cont > 12 && cont < 20) {
                    ball.setPosX(ball.getPosX() + 3);
                    ball.setPosY(ball.getPosY() - 3);
                    // barra.setSpeed(barra.getSpeed()+2);
                }
                if (cont > 19) {
                    ball.setPosX(ball.getPosX() + 4);
                    ball.setPosY(ball.getPosY() - 4);
                }
            }
            if (ballDirection == 2) {
                if (cont < 13) {
                    ball.setPosX(ball.getPosX() - 2);
                    ball.setPosY(ball.getPosY() - 2);
                }
                if (cont > 12 && cont < 20) {
                    ball.setPosX(ball.getPosX() - 3);
                    ball.setPosY(ball.getPosY() - 3);
                }
                if (cont > 19) {
                    ball.setPosX(ball.getPosX() - 4);
                    ball.setPosY(ball.getPosY() - 4);
                }

            }
            if (ballDirection == 3) {
                if (cont < 13) {
                    ball.setPosX(ball.getPosX() - 2);
                    ball.setPosY(ball.getPosY() + 2);
                }
                if (cont > 12 && cont < 20) {
                    ball.setPosX(ball.getPosX() - 3);
                    ball.setPosY(ball.getPosY() + 3);
                }
                if (cont > 19) {
                    ball.setPosX(ball.getPosX() - 4);
                    ball.setPosY(ball.getPosY() + 4);
                }
            }
            if (ballDirection == 4) {
                if (cont < 13) {
                    ball.setPosX(ball.getPosX() + 2);
                    ball.setPosY(ball.getPosY() + 2);
                }
                if (cont > 12 && cont < 20) {
                    ball.setPosX(ball.getPosX() + 3);
                    ball.setPosY(ball.getPosY() + 3);
                }
                if (cont > 19) {
                    ball.setPosX(ball.getPosX() + 4);
                    ball.setPosY(ball.getPosY() + 4);
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

    /**
     * Metodo usado para checar las colisiones de los objetos con las orillas
     * del <code>Applet</code>.
     */
    public void checaColision() {

        if (barra.getPosX() < 0) {
            barra.setPosX(0);
            if (!empieza) {
                ball.setPosX(barra.getPosX() + 10);
            }
            choca = true;
        }

        if (barra.getPosX() + barra.getAncho() > getWidth()) {
            barra.setPosX(getWidth() - barra.getAncho());
            if (!empieza) {
                ball.setPosX(barra.getPosX() + 10);

            }
            choca = true;
        }
        //Redirecta el impacto de la pelota/BALL
        if (ball.getPosX() + ball.getAncho() > getWidth() && ballDirection == 1) {
            ballDirection = 2;
        }
        if (ball.getPosX() + ball.getAncho() > getWidth() && ballDirection == 4) {
            ballDirection = 3;
        }
        if (ball.getPosX() < 0 && ballDirection == 2) {
            ballDirection = 1;
        }
        if (ball.getPosX() < 0 && ballDirection == 3) {
            ballDirection = 4;
        }
        if (ball.getPosY() < 0 && ballDirection == 2) {
            ballDirection = 3;
        }
        if (ball.getPosY() < 0 && ballDirection == 1) {
            ballDirection = 4;
        }
        //Impacta abajo
        if (ball.getPosY() + ball.getAlto() > getHeight() - ball.getAlto()) {

            if (vidas > 0) {
                vidas--;
                ball.setPosY(getHeight() - 160);
                ball.setPosX(barra.getPosX() + 10);
                empieza = false;
                score -= 50;
                failSound.play();

            }

        }
        //Colision entre objetos
        if (ball.intersecta(barra)) {

            if (ball.getPosY() + ball.getAlto() > getHeight() - barra.getAlto() + 10) {
                if (vidas > 0) {
                    vidas--;
                    ball.setPosY(getHeight() - 160);
                    ball.setPosX(barra.getPosX() + 10);
                    empieza = false;
                    score -= 50;
                    failSound.play();

                }
            } else {
                if (ballDirection == 3) {
                    ballDirection = 2;
                }
                if (ballDirection == 4) {
                    ballDirection = 1;
                }
            }

        }
        for (int i = 0; i < lista.size(); i++) {
            Block brickI = (Block) (lista.get(i));
            if (ball.intersecta(brickI) && ballDirection == 1) {
                ballDirection = 4;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                chocan.play();
                score += 100;
                cont++;
            }

            if (ball.intersecta(brickI) && ballDirection == 2) {
                ballDirection = 3;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                chocan.play();
                score += 100;
                cont++;
            }

            if (ball.intersecta(brickI) && ballDirection == 3) {
                ballDirection = 2;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                chocan.play();
                score += 100;
                cont++;
            }

            if (ball.intersecta(brickI) && ballDirection == 4) {
                ballDirection = 1;
                brickI.setPosX(-1000);
                brickI.setPosY(-1000);
                score += 100;
                cont++;
                chocan.play();
            }
        }
        //COLISION CON OBJETO SEGUNDO NIVEL
        if (nivel2) {
            for (int i = 0; i < listaBricks.size(); i++) {
                Block brickJ = (Block) (listaBricks.get(i));
                if (ball.intersecta(brickJ) && ballDirection == 1) {
                    ballDirection = 4;
                    brickJ.setPosX(-1000);
                    brickJ.setPosY(-1000);
                    chocan.play();
                    score += 250;
                    cont++;
                }

                if (ball.intersecta(brickJ) && ballDirection == 2) {
                    ballDirection = 3;
                    brickJ.setPosX(-1000);
                    brickJ.setPosY(-1000);
                    chocan.play();
                    score += 250;
                    cont++;
                }

                if (ball.intersecta(brickJ) && ballDirection == 3) {
                    ballDirection = 2;
                    brickJ.setPosX(-1000);
                    brickJ.setPosY(-1000);
                    chocan.play();
                    score += 250;
                    cont++;
                }

                if (ball.intersecta(brickJ) && ballDirection == 4) {
                    ballDirection = 1;
                    brickJ.setPosX(-1000);
                    brickJ.setPosY(-1000);
                    score += 250;
                    cont++;
                    chocan.play();
                }
            }
        }

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el Paint
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
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

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion Actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {

        if (startScreen) {

            g.drawImage(startGame.getImage(), 0, 0, this);

        } else if (vidas > 0) {

            if (sonido) {
                theme.play();
                sonido = false;
            }
            g.drawImage(fondo.getImage(), 0, 0, this);
            if (clickWalter) {
                if (direction == 0) {
                    g.drawImage(barraAnimWalterSt.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direction == 1) {
                    g.drawImage(barraAnimWalterLeft.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direction == 2) {
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
                if (direction == 0) {
                    g.drawImage(barraAnimJesseSt.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direction == 1) {
                    g.drawImage(barraAnimJesseLeft.getImagen(), barra.getPosX(), barra.getPosY(), this);
                } else if (direction == 2) {
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
            g.setColor(Color.white);
            g.setFont(new Font("Avenir Black", Font.ITALIC, 30));
            g.drawString("Score: " + score, 500, 80);

            if (!empieza && vidas == 3) {
                g.setFont(new Font("Avenir Black", Font.ITALIC, 60));
                g.drawString("Press Space Bar to Start!", 150, 600);
            }
            //Dibuja bloques de nivel dos
            if (nivel2) {
                for (int i = 0; i < listaBricks.size(); i++) {
                    bricknivel = (Block) (listaBricks.get(i));
                    g.drawImage(bricknivel.getImagenI(), bricknivel.getPosX(), bricknivel.getPosY(), this);
                }
            }

        } else {
            //Se acabo el juego

            g.drawImage(gameOver, 0, 0, this);
            g.setColor(Color.white);
            g.setFont(new Font("Avenir Black", Font.BOLD, 60));
            g.drawImage(vatilloFumado, 325, 350, this);
            if (ganaste) {
                g.drawString("You won!", 20, 250);
            } else if ((vidas == 0) && (!ganaste)) {
                g.drawString("You loose.", 20, 250);
            }
            theme.stop();
            failSound.stop();
            chocan.stop();

        }

    }
    /*
     *Metodo keyPressed
     *Cuando una tecla esta apretada
     *recibe de param un evento, en este caso se busca que sea la p
     *para pausar el juego
     */

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {

            pausa = !pausa;

        }
        if (!pausa) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction = 1;
                if (choca) {
                    choca = false;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction = 2;
                if (choca) {
                    choca = false;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if ((ball.getPosX() == barra.getPosX() + 10) && (ball.getPosY() == getHeight() - 160)) {
                    empieza = true;
                    if (vidas == 3) {
                        ballDirection = 2;
                    } else if (vidas == 2) {
                        ballDirection = 1;
                    } else {
                        ballDirection = 2;
                    }
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_R) {
                if (vidas > 0) {
                    reinicia();
                }

            }
            if (e.getKeyCode() == KeyEvent.VK_I && vidas == 0) {
                reinicia();
                startScreen = true;

            }
        }

    }

    public void keyTyped(KeyEvent e) {//metodo cuando una tecla fue typeada

    }

    public void keyReleased(KeyEvent e) { //metodo cuandos e suelta la tecla
        direction = 0;

    }

    public void mouseClicked(MouseEvent e) {
        if (rectangleWalter.contains(e.getX(), e.getY()) && startScreen == true) {
            clickWalter = true;
            clickJesse = false;
            startScreen = false;
            URL cURL = this.getClass().getResource("Images/fondo1.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cURL));
        } else if (rectangleJesse.contains(e.getX(), e.getY()) && startScreen == true) {
            clickJesse = true;
            clickWalter = false;
            startScreen = false;
            URL f4URL = this.getClass().getResource("Images/fondo3.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(f4URL));

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
    /*
     *Metodo reinicia
     *Cuando se apreta el boton r
     *no recibe  param 
     *sirve para reiniciar el juego desde nivel 1 siempre.
     */

    public void reinicia() {
        //sonido = true;
        //tema = new SoundClip("Sounds/Breaking_Bad.wav");
        //explosion = new SoundClip("Sounds/e.wav");
        theme.play();
        cont = 0;
        vidas = 3;
        score = 0;
        setSize(1000, 800);
        // addKeyListener(this);
        posX = 253;
        posY = (int) (getHeight() - 120);
        posXB = 263;
        posYB = (int) (getHeight() - 160);
        nivel2 = false;
        if (clickWalter) {
            URL cURL = this.getClass().getResource("Images/fondo1.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cURL));
        }
        if (clickJesse) {
            URL f4URL = this.getClass().getResource("Images/fondo3.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(f4URL));
        }
        URL gURL = this.getClass().getResource("Images/gameover.png");
        gameOver = Toolkit.getDefaultToolkit().getImage(gURL);
        URL rURL = this.getClass().getResource("Images/flask.png");
        URL hURL = this.getClass().getResource("Images/walter.png");
        URL pURL = this.getClass().getResource("Images/polloshermanos1.png");
        barra = new Paddle(posX, posY, Toolkit.getDefaultToolkit().getImage(hURL));
        ball = new Ball(posXB, posYB, Toolkit.getDefaultToolkit().getImage(pURL));
        empieza = false;
        ganaste = false;
        choca = false;
        direction = 0;
        ballDirection = 1;
        pausa = false;
        lista = new LinkedList<Block>();
        vidas = 3;

        int diffBrickr1 = 0;
        int diffBrickr2 = 0;
        int diffBricker3 = 0;

        for (int i = 0; i < 29; i++) {

            if (i < 12) {

                posXr = (int) ((getWidth() / 30 - 10) + diffBrickr1);    //posision x es tres cuartos del applet
                posYr = 275;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr1 += (int) (getWidth() / 12);
            }

            if (i > 11 && i <= 19) {

                posXr = (int) ((getWidth() / 28 - 10) + diffBrickr2);    //posision x es tres cuartos del applet
                posYr = 350;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr2 += (int) (getWidth() / 9);
            }

            if (i > 20 && i <= 29) {

                posXr = (int) ((getWidth() / 8 + 10) + diffBricker3);    //posision x es tres cuartos del applet
                posYr = 425;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBricker3 += (int) (getWidth() / 9);
            }

        }
    }
    /*
     *Metodo nuevoNivel
     *Cuando se pasa de nivel
     *no recibe  param 
     *sirve presentar el siguiente nivel debido
     */

    public void nuevoNivel() {
        //sonido = true;
        if (nivel2 == false) {
            nivel2 = true;
        }
        theme.play();

        //tema = new SoundClip("Sounds/Breaking_Bad.wav");
        //explosion = new SoundClip("Sounds/fail-buzzer-03.wav");
        cont = 0;
        vidas = 3;
        setSize(1000, 800);
        //addKeyListener(this);
        posX = 253;
        posY = (int) (getHeight() - 120);
        posXB = 263;
        posYB = (int) (getHeight() - 160);

        if (clickWalter) {
            URL cURL = this.getClass().getResource("Images/fondo2.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cURL));
            bmURL = this.getClass().getResource("Images/beakerMeth.png");
        }
        if (clickJesse) {
            URL f4URL = this.getClass().getResource("Images/fondo4.png");
            fondo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(f4URL));
            bmURL = this.getClass().getResource("Images/bolsa.png");
        }
        URL gURL = this.getClass().getResource("Images/gameover.png");
        gameOver = Toolkit.getDefaultToolkit().getImage(gURL);
        URL rURL = this.getClass().getResource("Images/flask.png");
        URL hURL = this.getClass().getResource("Images/walter.png");
        URL pURL = this.getClass().getResource("Images/polloshermanos1.png");
        barra = new Paddle(posX, posY, Toolkit.getDefaultToolkit().getImage(hURL));
        ball = new Ball(posXB, posYB, Toolkit.getDefaultToolkit().getImage(pURL));
        empieza = false;
        ganaste = false;
        choca = false;
        direction = 0;
        ballDirection = 1;
        pausa = false;
        lista = new LinkedList<Block>();

        int diffBrickr1 = 0;
        int diffBrickr2 = 0;
        int diffBricker3 = 0;

        for (int i = 0; i < 29; i++) {

            if (i < 12) {

                posXr = (int) ((getWidth() / 30 - 10) + diffBrickr1);    //posision x es tres cuartos del applet
                posYr = 275;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr1 += (int) (getWidth() / 12);
            }

            if (i > 11 && i <= 19) {

                posXr = (int) ((getWidth() / 28 - 10) + diffBrickr2);    //posision x es tres cuartos del applet
                posYr = 350;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBrickr2 += (int) (getWidth() / 9);
            }

            if (i > 20 && i <= 29) {

                posXr = (int) ((getWidth() / 8 + 10) + diffBricker3);    //posision x es tres cuartos del applet
                posYr = 425;    //posision y es tres cuartos del applet
                brick = new Block(posXr, posYr, Toolkit.getDefaultToolkit().getImage(rURL));
                lista.add(brick);
                diffBricker3 += (int) (getWidth() / 9);
            }

        }
        listaBricks = new LinkedList<Block>();

        for (int i = 0; i < 3; i++) {

            if (i == 0) {
                bricknivel = new Block(getWidth() / 2 - 42, getHeight() / 2 - 55, Toolkit.getDefaultToolkit().getImage(bmURL));
                listaBricks.add(bricknivel); // brick en el centro
            } else if (i == 1) {
                bricknivel = new Block(getWidth() / 4 - 10, getHeight() / 2 + 10, Toolkit.getDefaultToolkit().getImage(bmURL));
                listaBricks.add(bricknivel); // brick en la izquierda
            } else if (i == 2) {
                bricknivel = new Block(getWidth() - 315, getHeight() / 2 + 13, Toolkit.getDefaultToolkit().getImage(bmURL));
                listaBricks.add(bricknivel);// brick en la derecha

            }

        }
    }

}
