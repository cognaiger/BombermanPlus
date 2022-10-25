package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.bomberman.bombermanplus.constants.GameConst.SIZE_BLOCK;

public class BlockComponent extends Component {
    private final AnimatedTexture texture;

    public BlockComponent(int startF, int endF, double duration){
        AnimationChannel animationChannel = new AnimationChannel(image("sprites.png"), 16,
                SIZE_BLOCK, SIZE_BLOCK, Duration.seconds(duration), startF, endF);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }
}
