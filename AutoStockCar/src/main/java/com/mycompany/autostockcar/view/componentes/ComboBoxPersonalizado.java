package com.mycompany.autostockcar.view.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBoxPersonalizado<E> extends JComboBox<E> {

    private Icon prefixoIcon;
    private Color cor;

    public ComboBoxPersonalizado() {
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#1D1B86"));
        setFont(new Font("sanserif", 1, 15));
        this.cor = new Color(131, 191, 205);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(cor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        paintIcon(g);
    }

    private void paintIcon(Graphics g) {
        if (prefixoIcon != null) {
            Graphics2D g2 = (Graphics2D) g;
            ImageIcon imageIcon = (ImageIcon) prefixoIcon;
            int altura = (getHeight() - imageIcon.getIconHeight()) / 2;
            g2.drawImage(imageIcon.getImage(), 10, altura, this);
        }
    }

    public Icon getPrefixoIcon() {
        return prefixoIcon;
    }

    public void setPrefixoIcon(Icon prefixoIcon) {
        this.prefixoIcon = prefixoIcon;
        if (prefixoIcon != null) {
            setBorder(BorderFactory.createEmptyBorder(10, prefixoIcon.getIconWidth() + 15, 10, 20));
        } else {
            setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        }
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
        repaint();
    }

    public void addEditorActionListener(ActionListener listener) {
        ((CustomComboBoxEditor) getEditor()).addActionListener(listener);
    }
    
    private class CustomComboBoxRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(new Font("sanserif", 1, 15));
            if (isSelected) {
                label.setBackground(new Color(75, 175, 152));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(new Color(0,0,0));
            }
            return label;
        }
    }

    private class CustomComboBoxEditor extends BasicComboBoxEditor {

        private JTextField editor;
        private JPanel panel;

        public CustomComboBoxEditor() {
            editor = new JTextField();
            editor.setBorder(null);
            editor.setOpaque(false);
            editor.setFont(new Font("sanserif", 1, 15));
            editor.setForeground(new Color(0, 0, 0));
            editor.setBackground(new Color(0, 0, 0, 0));

            editor.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    editor.setBackground(new Color(0, 0, 0, 0));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    editor.setBackground(new Color(0, 0, 0, 0));
                }
            });

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);
            panel.add(editor, BorderLayout.CENTER);

            if (prefixoIcon != null) {
                JLabel label = new JLabel(prefixoIcon);
                panel.add(label, BorderLayout.WEST);
            }
        }

        @Override
        public Component getEditorComponent() {
            return panel;
        }

        @Override
        public void setItem(Object anObject) {
            editor.setText(anObject != null ? anObject.toString() : "");
        }

        @Override
        public Object getItem() {
            return editor.getText();
        }
        
        public void addActionListener(ActionListener listener) {
            editor.addActionListener(listener);
        }
    }
}
