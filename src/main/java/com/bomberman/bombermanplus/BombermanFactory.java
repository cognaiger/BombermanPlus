/**
 * Place contains information about how to create entities.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BooleanComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.bomberman.bombermanplus.components.BombComponent;
import com.bomberman.bombermanplus.components.FlameComponent;
import com.bomberman.bombermanplus.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.bomberman.bombermanplus.constants.GameConst.*;

public class BombermanFactory implements EntityFactory {

    private int radius = TILE_SIZE / 2;

    /* value of annotation must match the type used in Tiled exactly */
    @Spawns("player")
    public Entity spawnPlayer(SpawnData data) {
        var physics = new PhysicsComponent();

        /* fixture contain non-geometrical properties of physics bodies for collision detection */
        var fixtureDef = new FixtureDef();
        fixtureDef.setFriction(0);                              /* glide along wall */
        fixtureDef.setDensity(0.5f);
        /* set custom fixture definition to describe a generated fixture for this physics entity */
        physics.setFixtureDef(fixtureDef);

        var bodyDef = new BodyDef();
        bodyDef.setFixedRotation(true);
        bodyDef.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bodyDef);

        return entityBuilder(data)
                .atAnchored(new Point2D(radius, radius), new Point2D(radius, radius))     /* */
                .type(BombermanType.PLAYER)
                .bbox(new HitBox(BoundingShape.circle(radius)))
                .with(physics)
                .collidable()
                .with(new PlayerComponent())
                .with(new CellMoveComponent(TILE_SIZE, TILE_SIZE, ENEMY_SPEED_BASE))          /* AI */
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))    /* AI */
                .zIndex(5)
                .build();
    }

    @Spawns("background")
    public Entity spawnBackground(SpawnData data) {
        return FXGL.entityBuilder()
                .view(new Rectangle(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, Color.LIGHTGRAY))
                .with(new IrremovableComponent())
                .zIndex(-100)                              /* render behind all other entities */
                .build();
    }

    @Spawns("bomb")
    public Entity newBomb(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.BOMB)
                .with(new BombComponent())
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.circle(radius - 2)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("wall_bomb")
    public Entity newWallBomb(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.WALL_BOMB)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(TILE_SIZE, TILE_SIZE)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new PhysicsComponent())              /* ? */
                .with(new CollidableComponent())
                .build();
    }

    @Spawns("centerFlame")
    public Entity newCenterFlame(SpawnData data) {
        return entityBuilder()
                .type(BombermanType.FLAME)
                .with(new FlameComponent(128, 130))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("horizontalFlame")
    public Entity newHorizontalFlame(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(147, 149))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent())
                .zIndex(1)
                .build();
    }

    @Spawns("verticalFlame")
    public Entity newVerticalFlame(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(160, 162))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getY(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("aboveEFlame")
    public Entity newAboveEFl(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(144, 146))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("bottomEFlame")
    public Entity newBottomEFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(176, 178))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("leftEFlame")
    public Entity newLeftEFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(131, 133))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("rightEFlame")
    public Entity newRightEFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.FLAME)
                .with(new FlameComponent(163, 165))
                .viewWithBBox(new Rectangle(TILE_SIZE / 2.0 - 3, TILE_SIZE / 2.0 - 3,
                        Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("check_flame")
    public Entity newCheckFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.CHECK_FLAME)
                .viewWithBBox(new Rectangle(TILE_SIZE, TILE_SIZE, Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }


    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return entityBuilder(data)
                .type(BombermanType.WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    /*
     * Spawn buff item.
     */
    @Spawns("speedItem")
    public Entity newItem(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.SPEED_ITEM)
                .view("items/powerup_speed.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bombsItem")
    public Entity newItem1(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.BOMB_ITEM)
                .view("items/powerup_bombs.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("flameItem")
    public Entity newItem2(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME_ITEM)
                .view("items/powerup_flames.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("flamePassItem")
    public Entity newItem3(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME_PASS_ITEM)
                .view("items/powerup_flamepass.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    /*
     * Spawn portal.
     */
    @Spawns("portal")
    public Entity newPortal(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        var boundingShape = BoundingShape.box(width, height);
        var hitBox = new HitBox(boundingShape);

        return entityBuilder(data)
                .type(BombermanType.PORTAL)
                .bbox(hitBox)
                .view("items/portal.png")
                .with(new CollidableComponent(true))
                .build();
    }
}
