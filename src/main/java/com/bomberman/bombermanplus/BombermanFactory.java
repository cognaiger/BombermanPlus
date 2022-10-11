/**
 * Place where all entities are created
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombermanFactory implements EntityFactory {

    /* value of annotation must match the type used in Tiled exactly */
    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("player.png")
                .build();
    }

    @Spawns("Background")
    public Entity spawnBackground(SpawnData data) {
        return FXGL.entityBuilder()
                .view(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.BLACK))
                .with(new IrremovableComponent())
                .zIndex(-100)                              /* render behind all other entities */
                .build();
    }
}
