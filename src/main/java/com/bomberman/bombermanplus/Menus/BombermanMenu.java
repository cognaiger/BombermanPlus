package com.bomberman.bombermanplus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.bomberman.bombermanplus.Menus.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

/**
 * Main menu, displayed at the start of the game.
 */
public class BombermanMenu extends FXGLMenu {
    public BombermanMenu() {
        super(MenuType.MAIN_MENU);
        displayBackground();
        displayTitle();
        displayOptionsBox();
    }

    private void displayBackground(){
        var background = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);
        getContentRoot().getChildren().add(background);
    }

    private void displayTitle(){
        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, 100);
        centerTextBind(title, getAppWidth() / 2.0, 250);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 40);
        centerTextBind(version, getAppWidth() / 2.0, 280);

        var dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKBLUE);
        dropShadow.setOffsetX(10);
        dropShadow.setOffsetY(10);

        title.setEffect(dropShadow);
        version.setEffect(dropShadow);

        getContentRoot().getChildren().addAll(title, version);
    }

    private void displayOptionsBox(){
        var buttonTextSize = 40;

        var menuBox = new VBox(
                new MenuButton("NEW GAME", buttonTextSize, this::fireNewGame),
                new MenuButton("CONTROL", buttonTextSize, this::instructions),
                new MenuButton("EXIT", buttonTextSize, this::fireExit)
        );

        var offsetCenterX = buttonTextSize * 2.5;
        var offsetCenterY = buttonTextSize * 2;

        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - offsetCenterX);
        menuBox.setTranslateY(getAppHeight() / 2.0 + offsetCenterY);
        menuBox.setSpacing(20);
        getContentRoot().getChildren().addAll(menuBox);
    }

    private void instructions(){
        var pane = new GridPane();
        pane.setHgap(25);
        pane.setVgap(10);
        pane.addRow(0, getUIFactoryService().newText("CONTROL"),
                new HBox(4, new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        pane.addRow(1, getUIFactoryService().newText("PLACE BOMB"),
                new KeyView(SPACE));
        pane.addRow(2, getUIFactoryService().newText("OPEN MENU GAME"),
                new KeyView(ESCAPE));

        FXGL.getDialogService().showBox("HOW TO PLAY", pane, getUIFactoryService().newButton("DONE"));
    }
}