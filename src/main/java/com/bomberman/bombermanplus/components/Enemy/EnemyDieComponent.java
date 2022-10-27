package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.bomberman.bombermanplus.Config.TILE_SIZE;

public class EnemyDieComponent extends Component {
    private final AnimatedTexture texture;

    public EnemyDieComponent(){
        AnimationChannel animationChannel = new AnimationChannel(image("sprites.png"), 16,
                TILE_SIZE, TILE_SIZE, Duration.seconds(1.5), 75, 77);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }
}
