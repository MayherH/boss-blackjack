package com.bossblackjack;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BlackjackPanel extends PluginPanel
{
    private final BlackjackPlugin plugin;

    private final JLabel targetLabel = new JLabel("Target: -");
    private final JLabel playerLabel = new JLabel("Your loot: -");
    private final JLabel simLabel = new JLabel("Sim loot: -");
    private final JLabel resultLabel = new JLabel("Result: -");
    private final JButton standButton = new JButton("Stand");

    public BlackjackPanel(BlackjackPlugin plugin)
    {
        this.plugin = plugin;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Boss Blackjack");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(10));
        content.add(targetLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(playerLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(simLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(resultLabel);

        add(content, BorderLayout.NORTH);

        content.add(resultLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(standButton);

        standButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        standButton.addActionListener(e ->
        {
            plugin.onStandPressed();

        });
    }

    public void updateResult(long playerTotal, long simTotal, int targetValue, String result)
    {
        targetLabel.setText("Target: " + String.format("%,d", targetValue) + " gp");
        playerLabel.setText("Your loot: " + String.format("%,d", playerTotal) + " gp");
        simLabel.setText("Sim loot: " + String.format("%,d", simTotal) + " gp");
        resultLabel.setText("Result: " + result);

        // color the result label
        if (result.contains("You win"))
        {
            resultLabel.setForeground(Color.GREEN);
        }
        else if (result.contains("Sim wins"))
        {
            resultLabel.setForeground(Color.RED);
        }
        else
        {
            resultLabel.setForeground(Color.YELLOW);
        }

        revalidate();
        repaint();
    }
}