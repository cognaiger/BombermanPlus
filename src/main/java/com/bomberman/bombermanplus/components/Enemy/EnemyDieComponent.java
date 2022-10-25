package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.bomberman.bombermanplus.constants.GameConst.SIZE_BLOCK;

public class EnemyDieComponent extends Component {
    private final AnimatedTexture texture;

    public EnemyDieComponent(){
        AnimationChannel animationChannel = new AnimationChannel(image("sprites.png"), 16,
                SIZE_BLOCK, SIZE_BLOCK, Duration.seconds(1.5), 75, 77);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }
}
