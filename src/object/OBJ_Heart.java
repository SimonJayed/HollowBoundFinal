package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp){
        super(gp);

        setName("Heart");
        image1 = setup("/ui/heart", gp.tileSize, gp.tileSize);
    }
}

