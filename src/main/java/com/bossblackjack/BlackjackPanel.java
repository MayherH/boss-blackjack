package com.bossblackjack;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BlackjackPanel extends PluginPanel
{
    private final BlackjackPlugin plugin;
    private final ItemManager itemManager;

    private final JLabel targetLabel = new JLabel("Target: ");
    private final JButton standButton = new JButton("Stand");
    private final JButton resetButton = new JButton("Reset");

    private final GridPanel playerGrid;
    private final GridPanel simGrid;

    public static int gridPlayerVal;
    public static int gridSimVal;

    public BlackjackPanel(BlackjackPlugin plugin, ItemManager itemManager)
    {
        this.plugin = plugin;
        this.itemManager = itemManager;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ColorScheme.DARK_GRAY_COLOR);

        JLabel title = new JLabel("Boss Blackjack");
        title.setFont(FontManager.getRunescapeBoldFont().deriveFont(22f));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        targetLabel.setForeground(Color.WHITE);

        playerGrid = new GridPanel(itemManager, "You", 0 + " gp");
        simGrid = new GridPanel(itemManager, "Sim", 0 + " gp");

        content.add(title);
        content.add(Box.createVerticalStrut(10));
        content.add(targetLabel);
        content.add(Box.createVerticalStrut(5));

        content.add(playerGrid);
        content.add(Box.createVerticalStrut(8));
        content.add(simGrid);

        content.add(Box.createVerticalStrut(10));
        content.add(standButton);

        standButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(content, BorderLayout.NORTH);

        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        bottomPanel.add(resetButton);

        add(bottomPanel, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(() ->
        {
            playerGrid.addLootItem(995, 10_000_000);
            playerGrid.addLootItem(4151, 1);
            playerGrid.addLootItem(11840, 1);

            simGrid.addLootItem(560, 200_000);
            simGrid.addLootItem(565, 500);
        });

        resetButton.addActionListener(event ->
        {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to reset this round?",
                    "Confirm Reset",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION)
            {
                playerGrid.clearLootGrid();
                simGrid.clearLootGrid();
                playerGrid.setRightText(0 + " gp");
                simGrid.setRightText(0 + " gp");
            }
        });
    }

    public GridPanel getPlayerGrid()
    {
        return playerGrid;
    }

    public GridPanel getSimGrid()
    {
        return simGrid;
    }
}