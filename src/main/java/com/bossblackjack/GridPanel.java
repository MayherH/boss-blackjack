package com.bossblackjack;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GridPanel extends JPanel
{
    private static final int GRID_COLUMNS = 5;
    private static final int SLOT_SIZE = 42;

    private final ItemManager itemManager;

    private final JLabel leftLabel = new JLabel("");
    private final JLabel rightLabel = new JLabel("");

    private final JPanel header = new JPanel(new BorderLayout());
    private final JPanel lootGrid = new JPanel(new GridLayout(0, GRID_COLUMNS, 1, 1));

    public final Map<Integer, Integer> itemQuantities = new HashMap<>();
    private final Map<Integer, JLayeredPane> itemSlots = new HashMap<>();



    public GridPanel(ItemManager itemManager, String leftText, String rightText)
    {
        this.itemManager = itemManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setupHeader(leftText, rightText);
        setupGrid();

        add(header);
        add(lootGrid);
    }

    private void setupHeader(String leftText, String rightText)
    {
        Dimension headerSize = new Dimension(219, 26);

        header.setBackground(new Color(21, 21, 21));
        header.setBorder(new EmptyBorder(4, 6, 4, 6));
        header.setPreferredSize(headerSize);
        header.setMinimumSize(headerSize);
        header.setMaximumSize(headerSize);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftLabel.setText(leftText);
        leftLabel.setFont(FontManager.getRunescapeSmallFont());
        leftLabel.setForeground(Color.WHITE);

        rightLabel.setText(rightText);
        rightLabel.setFont(FontManager.getRunescapeSmallFont());
        rightLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(leftLabel, BorderLayout.WEST);
        header.add(rightLabel, BorderLayout.EAST);
    }

    public int getQuantity(int itemId)
    {
        return itemQuantities.getOrDefault(itemId, 0);
    }

    private void setupGrid()
    {
        lootGrid.setBackground(ColorScheme.DARK_GRAY_COLOR);
        lootGrid.setBorder(new EmptyBorder(1, 2, 2, 2));
        lootGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setHeaderText(String leftText, String rightText)
    {
        leftLabel.setText(leftText);
        rightLabel.setText(rightText);

        revalidate();
        repaint();
    }

    public void addLootItem(int itemId, int quantity)
    {
        removeEmptySlots();

        if (itemQuantities.containsKey(itemId))
        {
            int newQuantity = itemQuantities.get(itemId) + quantity;
            itemQuantities.put(itemId, newQuantity);

            JLayeredPane slot = itemSlots.get(itemId);
            updateSlotQuantity(itemId, newQuantity);

            fillRowWithEmptySlots();
            lootGrid.revalidate();
            lootGrid.repaint();
            return;
        }

        itemQuantities.put(itemId, quantity);

        BufferedImage image = itemManager.getImage(itemId, quantity, false);
        JLayeredPane slot = createLootSlot(image, quantity);

        itemSlots.put(itemId, slot);
        lootGrid.add(slot);

        fillRowWithEmptySlots();

        lootGrid.revalidate();
        lootGrid.repaint();
    }
    public void updateSlotQuantity(int itemId, int quantity)
    {
        JLayeredPane slot = itemSlots.get(itemId);


        if (slot == null)
        {
            return;
        }

        itemQuantities.put(itemId, quantity);

        String qtyText;
        Color qtyColor;

        if (quantity == 1)
        {
            qtyText = "";
            qtyColor = Color.YELLOW;
        }
        else if (quantity >= 10_000_000)
        {
            qtyText = (quantity / 1_000_000) + "M";
            qtyColor = new Color(0, 255, 128);
        }
        else if (quantity >= 100_000)
        {
            qtyText = (quantity / 1000) + "K";
            qtyColor = Color.WHITE;
        }
        else
        {
            qtyText = String.valueOf(quantity);
            qtyColor = Color.YELLOW;
        }

        JLabel shadow = (JLabel) slot.getClientProperty("qtyShadow");
        JLabel text = (JLabel) slot.getClientProperty("qtyText");

        shadow.setText(qtyText);
        text.setText(qtyText);
        text.setForeground(qtyColor);

        lootGrid.revalidate();
        lootGrid.repaint();
    }

    public void clearLootGrid()
    {
        itemQuantities.clear();
        itemSlots.clear();

        lootGrid.removeAll();
        lootGrid.revalidate();
        lootGrid.repaint();
    }

    public boolean containsItem(int itemId)
    {
        return itemQuantities.containsKey(itemId);
    }


    private void fillRowWithEmptySlots()
    {
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

    private void removeEmptySlots()
    {
        for (int i = lootGrid.getComponentCount() - 1; i >= 0; i--)
        {
            Component component = lootGrid.getComponent(i);

            if ("empty".equals(component.getName()))
            {
                lootGrid.remove(i);
            }
        }
    }

    private JLayeredPane createEmptySlot()
    {
        JLayeredPane slot = new JLayeredPane();
        slot.setName("empty");
        slot.setPreferredSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setMinimumSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setMaximumSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        slot.setOpaque(true);

        return slot;
    }

    private JLayeredPane createLootSlot(BufferedImage image, int quantity)
    {
        JLayeredPane slot = new JLayeredPane();
        slot.setPreferredSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setMinimumSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setMaximumSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        slot.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        slot.setOpaque(true);

        JLabel icon = new JLabel(new ImageIcon(image));
        icon.setBounds(3, 5, 36, 32);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setVerticalAlignment(SwingConstants.CENTER);

        String qtyText;
        Color qtyColor;

        if (quantity == 1)
        {
            qtyText = "";
            qtyColor = Color.YELLOW;
        }
        else if (quantity >= 10_000_000)
        {
            qtyText = (quantity / 1_000_000) + "M";
            qtyColor = new Color(0, 255, 128);
        }
        else if (quantity >= 100_000)
        {
            qtyText = (quantity / 1000) + "K";
            qtyColor = Color.WHITE;
        }
        else
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

        slot.putClientProperty("qtyShadow", shadow);
        slot.putClientProperty("qtyText", text);

        return slot;
    }
}