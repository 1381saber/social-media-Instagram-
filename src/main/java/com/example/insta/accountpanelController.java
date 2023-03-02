package com.example.insta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class accountpanelController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void acclist(MouseEvent event) {

    }

    @FXML
    void addpost(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("addpost.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void block(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("block.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void delaccount(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("deleteaccount.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void delpost(MouseEvent event) {

    }

    @FXML
    void editinfo(MouseEvent event) {

    }

    @FXML
    void follow(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("follow.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void menuid(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("firstmenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void notif(MouseEvent event) {

    }

    @FXML
    void show(MouseEvent event) {

    }
}
