package org.ludo.gameLogic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ludo.gameRendering.Renderer;

public class GameObjectNode extends ImageView implements Renderer {
  private final GameObject gameObject;
  private final CoordinateMapper coordinateMapper;

  public GameObjectNode(GameObject gameObject, Image objectImage, CoordinateMapper coordinateMapper) {
    this.gameObject = gameObject;
    this.coordinateMapper = coordinateMapper;
    super.setImage(objectImage);
  }

  @Override
  public void render() {
    FXMLElements.getGameContainer().getChildren().add(this);
  }

  @Override
  public void updatePosition() {
    super.setLayoutX(coordinateMapper.getX(gameObject));
    super.setLayoutY(coordinateMapper.getY(gameObject));
  }
}
