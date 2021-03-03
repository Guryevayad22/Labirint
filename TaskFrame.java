import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TaskFrame extends JFrame {//делаем класс окна с заданием
    JPanel buttons = new JPanel();//панель с кнопками
    JButton rightButton;//кнопка правильного ответа
    JButton wrongButton;//неправильный ответ
    String task;//текст задания
    Random random = new Random();

    public TaskFrame(String task, String right, String wrong) {//в каждом новом окне вводим вопрос, правильный ответ и неправильный ответ
        setSize(640, 480);
        setLocationRelativeTo(null);//ставит окно в центр экрана
        this.task = task;
        add(new JLabel(task, SwingConstants.CENTER));//добавляем текст на экран
        rightButton = new JButton(right);
        rightButton.addActionListener(e -> {
            if (View.maze[View.ballY / 30][View.ballX / 30] == 2) {//если клетка серая
                View.maze[View.ballY / 30][View.ballX / 30] = 4;//красим в розовый
            } //если правильно, закрываем окно и красим клетку
            dispose();
        });

        wrongButton = new JButton(wrong);
        wrongButton.addActionListener(e -> {//если неправильно
            JDialog wrongAnswer = new JDialog(this, "Wrong answer", true);//создаем диалоговое окно
            wrongAnswer.setSize(200, 150);
            wrongAnswer.add(new JLabel("Wrong answer. Try again.", SwingConstants.CENTER), BorderLayout.CENTER);
            //добавляем текст
            JButton ok = new JButton("Ok");//при нажатии на ок диалоговое окно закрывается и возвращается вопрос
            ok.addActionListener(e2 -> {
                wrongAnswer.dispose();
            });
            wrongAnswer.add(ok, BorderLayout.SOUTH);//кнопка внизу
            wrongAnswer.setLocationRelativeTo(null);//окно в центре экрана
            wrongAnswer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//крестик не нажимается
            wrongAnswer.setVisible(true);
        });
        buttons.setLayout(new GridLayout(1,2));
        if (random.nextInt(2) + 1 == 1) {
            buttons.add(rightButton);
            buttons.add(wrongButton);//добавляем кнопки в панель
        } else {
            buttons.add(wrongButton);
            buttons.add(rightButton);
        }
        add(buttons, BorderLayout.SOUTH);//панель на окно
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//крестик не нажимается
        setVisible(true);
    }
}
