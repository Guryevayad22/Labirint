import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class View extends JFrame implements KeyEventDispatcher {

    public View() {
        setTitle("Simple Maze");
        setSize(640, 480);
        setLocationRelativeTo(null); //располагает окно в центре экрана
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        //добавляем штуку которая клаву читает

    }
    static int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 1, 0, 1, 0, 1, 0, 0, 0, 2, 0, 1},
            {1, 0, 1, 0, 2, 0, 2, 0, 1, 1, 1, 0, 1},
            {1, 2, 1, 0, 1, 2, 1, 0, 0, 2, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 2, 1, 1, 1, 0, 2, 0, 0, 0, 1},
            {1, 2, 1, 0, 2, 0, 2, 0, 1, 2, 1, 0, 1},
            {1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 2, 1},
            {1, 0, 2, 0, 2, 0, 0, 0, 2, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};

    //эмпирическим путем посчитала начальную координату мячика
    static int ballX = 330;
    static int ballY = 240;

    //следующие 4 метода - проверяем, можем ли пойти в каком-то из направлений
    public boolean isAllowedRight() {
        int x = ballX / 30; //берем координатты по х и у и делим их на 30 - получаем номер элемента в массиве
        int y = ballY / 30;

        boolean right = (maze[y][x + 1] == 0 /*| maze[y][x + 1] == 5*/ | maze[y][x + 1] == 4); //проверяем, будет ли следующий в каком-то из направлений элемент нулем
        return right; // возвращаем тру или фолз
    }

    // дальше все то же самое
    // х и у поменяны местами потому что в массиве сначала ряды потом колонны

    public boolean isAllowedLeft() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean left = (maze[y][x - 1] == 0  | maze[y][x - 1] == 4);

        return left;
    }

    public boolean isAllowedUp() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean up = (maze[y - 1][x] == 0 | maze[y - 1][x] == 4);

        return up;
    }

    public boolean isAllowedDown() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean down = (maze[y + 1][x] == 0 | maze[y + 1][x] == 4);

        return down;
    }

    //переменные для всех клеток
    boolean isAllowedRight = false;
    boolean isAllowedLeft = false;
    boolean isAllowedUp = false;
    boolean isAllowedDown = false;

    //4 метода для проверки клетки на серость
    public boolean upGrayCheck() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean up = (maze[y - 1][x] == 2);
        return up;
    }

    public boolean downGrayCheck() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean down = (maze[y + 1][x] == 2);
        return down;
    }

    public boolean leftGrayCheck() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean left = (maze[y][x - 1] == 2);
        return left;
    }

    public boolean rightGrayCheck() {
        int x = ballX / 30;
        int y = ballY / 30;

        boolean right = (maze[y][x + 1] == 2);
        return right;
    }

    public void isAllowed() {

        //проверяем серость хоть одной соседней клетки
        boolean gray = rightGrayCheck() || leftGrayCheck() || upGrayCheck() || downGrayCheck();
        if (gray) {//если есть серые соседи
            isAllowedRight = rightGrayCheck();
            isAllowedLeft = leftGrayCheck();
            isAllowedUp = upGrayCheck();
            isAllowedDown = downGrayCheck();
            //если клетка серая, в нее можно пойти

        } else if (maze[ballY / 30][ballX / 30] == 2) { //проверяем, на серой ли клетке стоим
            //проверяем какая именно серая клетка
            if (ballY / 30 == 6 && ballX / 30 == 9) {//создаем окно с вопросом
                TaskFrame task1 = new TaskFrame("Как звали мать Лорда Во́л-де-Мо́рта", "Меропа Редл.", "Патрисия Редл. ");
            } else if (ballY / 30 == 8 && ballX / 30 == 8) {
                TaskFrame task2 = new TaskFrame("Лучшая пара в ГП.", "Малфой и Гарри", "Гарри и Северус");
            } else if (ballY / 30 == 7 && ballX / 30 == 11) {
                TaskFrame task3 = new TaskFrame("Ты смотрел ГП?", "Каждый год пресматриваю.", "Я в горах живу (:");
            } else if (ballY / 30 == 6 && ballX / 30 == 6) {
                TaskFrame task4 = new TaskFrame("Какой из этих вопросов Северус Снегг не задавал Гарри Поттеру на самом первом уроке зельеварения?", "Какое растение является противоядием от большинства ядов?", "Что получится, если я смешаю измельченный корень асфоделя с настойкой полыни?");
            } else if (ballY / 30 == 6 && ballX / 30 == 4) {
                TaskFrame task5 = new TaskFrame(" Как зовут бабушку Невилла?", "Августа.", "Амелия.");
            } else if (ballY / 30 == 5 && ballX / 30 == 3) {
                TaskFrame task6 = new TaskFrame("Какой запах не чувствовала Гермиона, вдыхая аромат амортенции?", "Запах волос Рона.", "Запах скошенной травы.");
            } else if (ballY / 30 == 3 && ballX / 30 == 1) {
                TaskFrame task7 = new TaskFrame("Кто из этих персонажей не присутствовал на первом собрании клуба Слизнорта?", "Перси Уизли.", "Кормак Маклагген.");
            } else if (ballY / 30 == 6 && ballX / 30 == 1) {
                TaskFrame task8 = new TaskFrame("Какой из этих паролей является паролем к портрету Полной Дамы и  открывает дверь в башню Гриффиндора?", "Мимбулус Мемблетония.", "Сосновая свежесть.");
            } else if (ballY / 30 == 8 && ballX / 30 == 2) {
                TaskFrame task9 = new TaskFrame("Как известно, Филч, хоть и родился сквибом, пытался научиться магии. Какой курс он проходил?", "Курс скоромагии.", "«Теория магии для сквибов» Миранды Гуссокл.");
            } else if (ballY / 30 == 8 && ballX / 30 == 4) {
                TaskFrame task10 = new TaskFrame("Полное имя Дамблдора?", "Альбус Персиваль Вулфрик Брайан Дамблдор.", "Альбус Персиваль Брайан Вулфрик Дамблдор.");
            } else if (ballY / 30 == 7 && ballX / 30 == 5) {
                TaskFrame task11 = new TaskFrame("У Дамблдора, как известно, есть шрам над левым коленом. Что он собой представляет?", "Схема лондонской подземки.", "Он имеет очертания Хогвартса, конечно.");
            } else if (ballY / 30 == 5 && ballX / 30 == 8) {
                TaskFrame task12 = new TaskFrame("Сколько сиклей в галлеоне?", "17", "29");
            } else if (ballY / 30 == 3 && ballX / 30 == 9) {
                TaskFrame task13 = new TaskFrame("Из чего состоит сердцевина волшебной палочки Драко Малфоя?", "Волос единорога.", "Сердечная жила дракона.");
            } else if (ballY / 30 == 2 && ballX / 30 == 6) {
                TaskFrame task14 = new TaskFrame("Как звучит второе имя Гермионы?", "Джин.", "Роза.");
            } else if (ballY / 30 == 3 && ballX / 30 == 5) {
                TaskFrame task15 = new TaskFrame("Какую форму принимал патронус Полумны Лавгуд?", "Заяц.", "Мозгошмыг.");
            } else if (ballY / 30 == 2 && ballX / 30 == 4) {
                TaskFrame task16 = new TaskFrame("Кто из этих персонажей  был игроком в квиддич?", "Минерва Макгонагалл.", "Билл Уизли.");
            } else if (ballY / 30 == 1 && ballX / 30 == 10) {
                TaskFrame task17 = new TaskFrame("Как зовут подругу Полной Дамы, которая рассказывает ей все сплетни замка?", "Виолетта.", "Виола.");
            } else if (ballY / 30 == 1 && ballX / 30 == 1) {
                TaskFrame task18 = new TaskFrame("Вам понравилась игра?", "Лучшая игра в моей жизни!", "Конечно!");
            }

            //если ступили на серую, из нее никуда не пойти пока окно открыто
            isAllowedDown = false;
            isAllowedUp = false;
            isAllowedLeft = false;
            isAllowedRight = false;

        } else {//если серых соседей нет
            isAllowedRight = isAllowedRight();
            isAllowedLeft = isAllowedLeft();
            isAllowedUp = isAllowedUp();
            isAllowedDown = isAllowedDown();
            //если клетка белая или зеленая, в нее можно пойти
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // это чтобы отрисовывалось каждый раз заново а не друг на друге

        //  двойная буферизация скопированная с сайта полярника (не понимаю че да как)
        //но это для того чтобы окно не мигало когда нажимаешь
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.translate(70, 70); // эта штука как бы ставит начало координат в точку которая в скобочках написана

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Color color;
                switch (maze[row][col]) {
                    case 1:
                        color = Color.BLACK;
                        break;
                    case 2:
                        color = Color.BLACK;
                        break;
                    case 4:
                        color = Color.WHITE;
                        break;
                    default:
                        color = Color.WHITE;
                }
                g.setColor(color);

                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }

        //вот тут шарик рисую
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 30, 30);


        int d = 60;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));


        for (int i = 0; i <= 1000; i+=1) {
            g.setColor(Color.BLACK);

            g2d.drawOval(ballX + 15 - d, ballY + 15 - d, d * 2, d * 2);
            d++;
        }

        g.dispose();
        bufferStrategy.show();//кусок той штуки чтобы окно не мигало
    }

    /*public void fillPixel(Graphics g, int x, int y) {
        g.fillRect(x, y, 1,1);
    }*/

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getID() == KeyEvent.KEY_PRESSED) { //проверяем нажата ли клавиша

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                //System.out.println(isAllowedRight);
                isAllowed();
                if (isAllowedRight) { //если можно идти вправо
                    ballX += 30;//идем вправо
                    repaint();//и перерисовываем окно
                }
            } //дальше аналогично
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isAllowed();
                if (isAllowedLeft) {
                    ballX -= 30;
                    repaint();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                isAllowed();
                if (isAllowedDown) {
                    ballY += 30;
                    repaint();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                isAllowed();
                if (isAllowedUp) {
                    ballY -= 30;
                    repaint();
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
    }
}
