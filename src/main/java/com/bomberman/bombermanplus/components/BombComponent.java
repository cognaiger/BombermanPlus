package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.onCollisionEnd;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.constants.GameConst.SIZE_BLOCK;

public class BombComponent extends Component {
    private ArrayList<Entity> listFire = new ArrayList<>();
    Entity wallBomb;
    private AnimatedTexture texture;
    private AnimationChannel animation;
    private List<Entity> listRightBlock = new ArrayList<>();
    private List<Entity> listLeftBlock = new ArrayList<>();
    private List<Entity> listAboveBlock = new ArrayList<>();
    private List<Entity> listBottomBlock = new ArrayList<>();

    public BombComponent(){
        onCollisionEnd(BombermanType.BOMB, BombermanType.PLAYER, (b, p) -> {
            if (entity != null) {
                wallBomb = spawn("wall_bomb", new SpawnData(entity.getX(), entity.getY()));
            }
        });

        animation = new AnimationChannel(image("sprites.png"), 16, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(0.5), 72, 74);
        texture = new AnimatedTexture(animation);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }


}
