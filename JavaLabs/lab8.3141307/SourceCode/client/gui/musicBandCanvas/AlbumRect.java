package com.lab.client.gui.musicBandCanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AlbumRect extends Shape {
  public AlbumRect() {
    super(80, 80, 100, 100, 1);
  }

  @Override
  public void draw(GraphicsContext gc) {
    drawRect(gc, Color.MAGENTA);
  }
}
