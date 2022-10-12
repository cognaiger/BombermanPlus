/**
 * Main file of application.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.bomberman.bombermanplus.components.PlayerComponent;
import com.bomberman.bombermanplus.constants.GameConst;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.BombermanType.BRICK;
import static com.bomberman.bombermanplus.BombermanType.WALL;

public class BombermanApp extends GameApplication {

    private static final int VIEW_HEIGHT = 720;
    private static final int VIEW_WIDTH =1080;

    private static final String TITLE = "BOMBERMAN";
    private static final String VERSION ="1.0";

    private static final String FONT = "Retro Gaming.ttf";

    private static final int TIME_PER_LEVEL =300;
    private static final int START_LEVEL = 0;
    private static final int MAX_LEVEL = 1;

    private AStarGrid grid;

    private Entity player;
    private PlayerComponent playerComponent;

    public static void main(String[] args) {                              /* entry point */
        launch(args);
    }

    /**
     * Setting can not be changed during runtime.
     * @param settings game setting
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setHeight(VIEW_HEIGHT);
        settings.setWidth(VIEW_WIDTH);
        settings.setTitle(TITLE);
        settings.setVersion(VERSION);
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(false);
        settings.setGameMenuEnabled(false);
        settings.setPreserveResizeRatio(false);
        settings.setManualResizeEnabled(false);
        settings.setDeveloperMenuEnabled(false);
        /* settings.setFontUI(FONT); */
    }

    /**
     * Set up all stuff that needs to be ready before game starts.
     */
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());


        Level level = getAssetLoader().loadLevel("0.txt",
                new TextLevelLoader(40, 40, '0'));
        getGameWorld().setLevel(level);

        FXGL.spawn("background");

        /* Add the player */
        player = spawn("player");

        /*
        grid = AStarGrid.fromWorld(getGameWorld(), 15, 15, 40, 40, type -> {
            if (type.equals(WALL) || type.equals(BRICK))
                return CellState.NOT_WALKABLE;

            return CellState.WALKABLE;
        });
         */
    }



    /**
     * Input handling for player.
     */
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);                                      /* move right 5 pixels */
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.A);
    }

    /**
     * Handle UI.
     */
    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);

        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved")
                .asString());

        FXGL.getGameScene().addUINode(textPixels);                        /* add to scene graph */
    }

    /**
     * Game variable.
     * Can be accessed and modified from any part of game.
     * @param vars
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("level", START_LEVEL);
        vars.put("speed", GameConst.SPEED);
    }

    private void loadNextLevel() {
        if (FXGL.geti("level") >= MAX_LEVEL) {
            showMessage("You win!");
        } else {
            getInput().setProcessInput(false);
            inc("level", +1);
        }
    }

    private void setLevel() {
        FXGL.setLevelFromMap("bbm_level1.tmx");
    }
}

