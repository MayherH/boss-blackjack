package com.bossblackjack;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackjackPanel extends PluginPanel {
    private final BlackjackPlugin plugin;
    private final ItemManager itemManager;
    private final JLabel targetLabel = new JLabel("Target: ");
    private final JButton standButton = new JButton("Stand");


    private final JPanel lootGrid = new JPanel(new GridLayout(0, 5, 1, 1));
    private static final int GRID_COLUMNS = 5;

    public BlackjackPanel(BlackjackPlugin plugin, ItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ColorScheme.DARK_GRAY_COLOR);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(21,21,21));
        header.setBorder(new EmptyBorder(4, 6, 4, 6));
        Dimension headerSize = new Dimension(219, 26);

        JLabel leftLabel = new JLabel("You");
        leftLabel.setFont(FontManager.getRunescapeSmallFont());
        leftLabel.setForeground(Color.WHITE);

        JLabel rightLabel = new JLabel(plugin.getPlayerTotal + " gp");
        rightLabel.setFont(FontManager.getRunescapeSmallFont());
        rightLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.setPreferredSize(headerSize);
        header.setMinimumSize(headerSize);
        header.setMaximumSize(headerSize);

        JLabel title = new JLabel("Boss Blackjack");
        title.setFont(FontManager.getRunescapeBoldFont().deriveFont(22f));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        targetLabel.setForeground(Color.WHITE);


        lootGrid.setBackground(ColorScheme.DARK_GRAY_COLOR);
        lootGrid.setBorder(new EmptyBorder(1, 2, 2, 2));
        lootGrid.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(leftLabel, BorderLayout.WEST);
        header.add(rightLabel, BorderLayout.EAST);

        content.add(title);
        content.add(Box.createVerticalStrut(10));
        content.add(targetLabel);
        content.add(Box.createVerticalStrut(5));

        content.add(header);
        content.add(lootGrid);

        content.add(Box.createVerticalStrut(10));
        content.add(standButton);

        standButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(content, BorderLayout.NORTH);

        SwingUtilities.invokeLater(() -> // just test items
        {
            addLootItem(995, 10000000); // coins
            addLootItem(4151, 1);     // abyssal whip
            addLootItem(11840, 1);    // dragon boots
            addLootItem(11235, 1);    // dark bow
            addLootItem(6585, 1);     // amulet of fury
            addLootItem(560, 200000);    // death runes
            addLootItem(565, 500);    // blood runes
        });
    }

    private void fillRowWithEmptySlots() {
        int remainder = lootGrid.getComponentCount() % GRID_COLUMNS;

        if (remainder == 0)
        {
            return;
        }

        int slotsToAdd = GRID_COLUMNS - remainder;

        for (int i = 0; i < slotsToAdd; i++)
        {
            lootGrid.add(createEmptySlot());
        }
    }

    private void removeEmptySlots() {
        for (int i = lootGrid.getComponentCount() - 1; i >= 0; i--)
        {
            Component component = lootGrid.getComponent(i);

            if ("empty".equals(component.getName()))
            {
                lootGrid.remove(i);
            }
        }
    }

    private JLayeredPane createEmptySlot() {
        JLayeredPane slot = new JLayeredPane();
        slot.setName("empty");
        slot.setPreferredSize(new Dimension(42, 42));
        slot.setMinimumSize(new Dimension(42, 42));
        slot.setMaximumSize(new Dimension(42, 42));
        slot.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        slot.setOpaque(true);

        return slot;
    }

    public void addLootItem(int itemId, int quantity) {
        removeEmptySlots();

        BufferedImage image = itemManager.getImage(itemId, quantity, false);
        lootGrid.add(createLootSlot(image, quantity));

        fillRowWithEmptySlots();

        lootGrid.revalidate();
        lootGrid.repaint();
    }

    public void clearLootGrid() {
        lootGrid.removeAll();
        lootGrid.revalidate();
        lootGrid.repaint();
    }

    private JLayeredPane createLootSlot(BufferedImage image, int quantity) {
        JLayeredPane slot = new JLayeredPane();
        slot.setPreferredSize(new Dimension(42, 42));
        slot.setMinimumSize(new Dimension(42, 42));
        slot.setMaximumSize(new Dimension(42, 42));
        slot.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        slot.setOpaque(true);

        JLabel icon = new JLabel(new ImageIcon(image));
        icon.setBounds(3    , 5, 36, 32);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setVerticalAlignment(SwingConstants.CENTER);

        String qtyText = String.valueOf(quantity);
        Color qtyColor;

        if (quantity == 1){
            qtyText = "";
            qtyColor = Color.yellow;
        }
        else if (quantity >= 10000000)
        {
            qtyText = (quantity / 1000000) + "M";
            qtyColor = new Color(0, 255, 128);
        }
        else if (quantity >= 100000)
        {
            qtyText = (quantity / 1000) + "K";
            qtyColor = Color.WHITE;
        } else
        {
            qtyText = String.valueOf(quantity);
            qtyColor = Color.YELLOW;
        }


        JLabel shadow = new JLabel(qtyText);
        shadow.setFont(FontManager.getRunescapeSmallFont());
        shadow.setForeground(Color.BLACK);
        shadow.setBounds(4, 6, 36, 12);

        JLabel text = new JLabel(qtyText);
        text.setFont(FontManager.getRunescapeSmallFont());
        text.setForeground(qtyColor);
        text.setBounds(3, 5, 36, 12);

        slot.add(icon, JLayeredPane.DEFAULT_LAYER);
        slot.add(shadow, JLayeredPane.PALETTE_LAYER);
        slot.add(text, JLayeredPane.MODAL_LAYER);

        return slot;
    }

}