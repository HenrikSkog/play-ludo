package org.ludo.gameRendering;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class CanvasRenderer {
  private static GraphicsContext gc;
  private static ArrayList<Renderable> renderableObjects;


  public static void renderObjects() {
    for(var object: renderableObjects) {
      object.render(gc);
    }
  }


  public static void setGc(GraphicsContext gc) {
    CanvasRenderer.gc = gc;
  }

  public static void addRenderable(Renderable object) {
    renderableObjects.add(object);
  }
}