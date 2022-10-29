/**
 * Place contains information about how to create entities.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
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
import com.bomberman.bombermanplus.components.*;
import com.bomberman.bombermanplus.components.Enemy.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.bomberman.bombermanplus.Config.*;

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
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .with(new PlayerImgComponent())
                .with(new CellMoveComponent(TILE_SIZE, TILE_SIZE, ENEMY_SPEED_BASE))          /* AI */
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))    /* AI */
                .zIndex(5)
                .build();
    }

    /*
     * Spawn background.
     */
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

    /*
     * Spawn flame.
     */
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
                .with(new CollidableComponent(true))
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
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
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


    /*
     * Spawn obstacles.
     */
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

    @Spawns("around_wall")
    public Entity newArWall(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return entityBuilder(data)
                .type(BombermanType.AROUND_WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    /*
     * Spawn destructible objects.
     */
    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return FXGL.entityBuilder(data)
                .type(BombermanType.BRICK)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new BlockComponent(104, 104, TIME_PER_LEVEL))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick_break")
    public Entity newBrickBreak(SpawnData data) {
        var boundingShape = BoundingShape.box(
                TILE_SIZE / 2.0f - 3,
                TILE_SIZE / 2.0f - 3);

        var hitBox = new HitBox(boundingShape);

        return FXGL.entityBuilder(data)
                .type(BombermanType.BRICK_BREAK)
                .with(new BlockComponent(105, 107, 1))
                .bbox(hitBox)
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .zIndex(1)
                .build();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return FXGL.entityBuilder(data)
                .type(BombermanType.GRASS)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new BlockComponent(88, 88, TIME_PER_LEVEL))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("grass_break")
    public Entity newGrassBreak(SpawnData data) {
        var boundingShape = BoundingShape.box(
                TILE_SIZE / 2.0f - 3,
                TILE_SIZE / 2.0f - 3);

        var hitBox = new HitBox(boundingShape);

        return FXGL.entityBuilder(data)
                .type(BombermanType.GRASS_BREAK)
                .bbox(hitBox)
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new BlockComponent(89, 91, 1))
                .zIndex(1)
                .build();
    }

    @Spawns("coral")
    public Entity newCoral(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.CORAL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                        data.<Integer>get("height"))))
                .with(new BlockComponent(92, 92, TIME_PER_LEVEL))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("coral_break")
    public Entity newCoralBreak(SpawnData data) {
        var boundingShape = BoundingShape.box(
                TILE_SIZE / 2.0f - 3,
                TILE_SIZE / 2.0f - 3);

        var hitBox = new HitBox(boundingShape);

        return FXGL.entityBuilder(data)
                .type(BombermanType.CORAL_BREAK)
                .bbox(hitBox)
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new BlockComponent(93, 95, 1))
                .zIndex(1)
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

    /*
     * Spawn enemies.
     */
    @Spawns("balloom_e")
    public Entity newBalloom(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.BALLOOM_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new BalloomComponent())
                .with(new CollidableComponent(true))
                .zIndex(2)
                .build();
    }

    @Spawns("dahl_e")
    public Entity newDahl(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.DAHL_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new DahlComponent())
                .with(new CollidableComponent(true))
                .zIndex(2)
                .build();
    }

    @Spawns("ovape_e")
    public Entity newOvape(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.OVAPE_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new OvapeComponent())
                .with(new CollidableComponent(true))
                .zIndex(2)
                .build();
    }

    @Spawns("oneal_e")
    public Entity newOneal(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.ONEAL_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .with(new CollidableComponent(true))
                .atAnchored(new Point2D(radius, radius), new Point2D(radius, radius))
                .with(new CellMoveComponent(TILE_SIZE, TILE_SIZE, ENEMY_SPEED_BASE))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new OnealComponent())
                .zIndex(2)
                .build();
    }

    @Spawns("pass_e")
    public Entity newPass(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.PASS_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .with(new CollidableComponent(true))
                .atAnchored(new Point2D(radius, radius), new Point2D(radius, radius))
                .with(new CellMoveComponent(TILE_SIZE, TILE_SIZE, ENEMY_SPEED_BASE))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new PassComponent())
                .zIndex(2)
                .build();
    }

    @Spawns("doria_e")
    public Entity newDoria(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.DORIA_E)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .with(new CollidableComponent(true))
                .atAnchored(new Point2D(radius, radius), new Point2D(radius, radius))
                .with(new CellMoveComponent(TILE_SIZE, TILE_SIZE, ENEMY_SPEED_BASE + 20))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("_grid"))))
                .with(new DoriaComponent())
                .zIndex(2)
                .build();
    }

    @Spawns("enemyDie")
    public Entity newEnemyDie(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.ENEMY_DIE)
                .bbox(new HitBox(BoundingShape.circle(radius - 2)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new EnemyDieComponent())
                .build();
    }
}
