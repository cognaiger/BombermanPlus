package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.bomberman.bombermanplus.constants.GameConst.TILE_SIZE;

public class FlameComponent extends Component {

    private final AnimatedTexture texture;

    public FlameComponent(int startF, int endF) {
        PhysicsWorld physics =getPhysicsWorld();

        AnimationChannel animationFlame = new AnimationChannel(image("sprites.png"), 16,
                TILE_SIZE, TILE_SIZE, Duration.seconds(0.4), startF, endF);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
