package com.secres.secresbrowser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Browser extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        
        AnchorPane root = new AnchorPane();
        
        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            tabs.getTabs().add(new Tab("New Tab", new SinglePage().getBox()));
            tabs.getSelectionModel().selectLast();
        });

        AnchorPane.setTopAnchor(tabs, 5.0);
        AnchorPane.setLeftAnchor(tabs, 5.0);
        AnchorPane.setRightAnchor(tabs, 5.0);
        AnchorPane.setTopAnchor(addButton, 7.0);
        AnchorPane.setRightAnchor(addButton, 10.0);
        root.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(0))));

        Tab primaryTab = new Tab("New Tab", new SinglePage().getBox());
        tabs.getTabs().add(primaryTab);
        
        root.getChildren().addAll(tabs, addButton);
        
        BorderPane borderPane = new BorderPane(root);
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("SecresBrowser");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
