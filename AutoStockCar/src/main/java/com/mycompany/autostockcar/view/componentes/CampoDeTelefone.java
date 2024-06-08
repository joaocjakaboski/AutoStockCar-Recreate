
package com.mycompany.autostockcar.view.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;


public class CampoDeTelefone extends JFormattedTextField {
    private String dicas;
    private Icon prefixoIcon;
    private Color cor;
    
    private MaskFormatter telefoneFormat;
    
    public CampoDeTelefone() {
        try {
            telefoneFormat = new MaskFormatter("(##) #####-####");
            telefoneFormat.setPlaceholderCharacter('_');
            setFormatter(telefoneFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#1D1B86"));
        setFont(new Font("sanserif", 1, 15));
        this.cor = new Color(28, 181, 223, 80);
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> setCaretPosition(1));
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        
        graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics2D.setColor(cor);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        paintIcon(g);
        
        super.paintComponent(g);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (getText().length() == 0) {
            int altura = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(new Color(200, 200, 200));
            g.drawString(dicas, ins.left, altura / 2 + fm.getAscent() / 2 - 2);
        }
    }
    
    private void paintIcon(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        
        if (prefixoIcon != null) {
            Image prefixo = ((ImageIcon) prefixoIcon).getImage();
            
            int altura = (getHeight() - prefixoIcon.getIconHeight()) / 2;
            
            graphics2D.drawImage(prefixo, 10, altura, this);
        }
    }
    
    private void inicializarBorda() {
        int esquerda = 10;
        
        if (prefixoIcon != null) {
            esquerda = prefixoIcon.getIconWidth() + 15;
        }
        
        setBorder(BorderFactory.createEmptyBorder(10, esquerda, 10, 20));
    }
    
    public void reiniciarCampo() {
        this.setValue(null);
        try {
            setValue(null); // Reseta o valor do campo
            setFormatterFactory(new DefaultFormatterFactory(telefoneFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDicas() {
        return dicas;
    }

    public void setDicas(String dicas) {
        this.dicas = dicas;
    }

    public Icon getPrefixoIcon() {
        return prefixoIcon;
    }

    public void setPrefixoIcon(Icon prefixoIcon) {
        this.prefixoIcon = prefixoIcon;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
}
