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
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;


public class CampoDeTexto extends JTextField{
    
    private String dicas;
    private Icon prefixoIcon;
    private Color cor;
    private Color corTexto;

    public CampoDeTexto() {
        setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        setBackground(new Color(255, 0, 0, 0));
        setForeground(Color.decode("#000000"));
        setFont(new Font("sanserif", 1, 15));
        setSelectionColor(new Color(75, 175, 152));
        this.cor = new Color (255, 255, 255);
        this.corTexto = new Color(0, 0, 0);
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
            g.setColor(corTexto);
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

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.setCorTexto(new Color(158, 158, 158));
            this.setCor(new Color (255, 255, 255));
            this.setForeground(new Color(0, 0, 0));
        } else {
            this.setCorTexto(new Color(255, 255, 255));
            this.setForeground(new Color(255, 255, 255));
            this.setCor(new Color (169, 169, 169));
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
        inicializarBorda();
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
        repaint();
    }

    public Color getCorTexto() {
        return corTexto;
    }

    public void setCorTexto(Color corTexto) {
        this.corTexto = corTexto;
        repaint();
    }
    
    
}
