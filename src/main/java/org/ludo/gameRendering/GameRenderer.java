package org.ludo.gameRendering;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class GameRenderer {
  private static ArrayList<Renderable> renderableObjects = new ArrayList<>();

  public static void renderObjects() {
    for(var object: renderableObjects) {
      object.render();
    }
  }

  public static void addRenderable(Renderable object) {
    renderableObjects.add(object);
  }
}