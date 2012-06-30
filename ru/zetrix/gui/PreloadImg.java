package ru.zetrix.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PreloadImg extends JPanel
{
  private Image bgIm;

  public PreloadImg(String img)
  {
    setOpaque(true);
    try
    {
      BufferedImage src = ImageIO.read(PreloadImg.class.getResource("/ru/zetrix/res/" + img));
      int w = src.getWidth();
      int h = src.getHeight();
      bgIm = src.getScaledInstance(w, h, 16);
      setPreferredSize(new Dimension(w + 32, h + 32));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paintComponent(Graphics g2) {
    g2.drawImage(bgIm, 0, 0, null);
  }
}