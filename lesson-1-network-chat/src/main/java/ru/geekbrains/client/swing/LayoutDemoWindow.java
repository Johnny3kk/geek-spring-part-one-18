package ru.geekbrains.client.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutDemoWindow extends JFrame {

    public LayoutDemoWindow() throws HeadlessException {
        setTitle("Application");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        //setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        //setLayout(new FlowLayout());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        JButton button1 = new JButton("Center");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LayoutDemoWindow.this,
                        "Message", "Title", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JButton button2 = new JButton("East");
        JButton button3 = new JButton("West");
        JButton button4 = new JButton("North");
        JButton button5 = new JButton("South");

        panel1.add(button1, BorderLayout.CENTER);
        panel1.add(button2, BorderLayout.EAST);
        panel1.add(button3, BorderLayout.WEST);
        panel1.add(button4, BorderLayout.NORTH);
        panel1.add(button5, BorderLayout.SOUTH);

        add(panel1, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        add(panel2, BorderLayout.NORTH);

        JButton button11 = new JButton("Button11");
        JButton button21 = new JButton("Button21");
        JButton button31 = new JButton("Button31");

        panel2.add(button11);
        panel2.add(button21);
        panel2.add(button31);

        setVisible(true);
    }
}
