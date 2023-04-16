package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.example.App;
import com.example.model.Pasien;
import com.example.util.DBUtil;
import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private Button btnTambah;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnKeluar;

    @FXML
    private TableView<Pasien> tbvPasien;

    @FXML
    private TableColumn<Pasien, Integer> tbcNo;
    @FXML
    private TableColumn<Pasien, String> tbcNamaLengkap;
    @FXML
    private TableColumn<Pasien, String> tbcNik;
    @FXML
    private TableColumn<Pasien, Date> tbcTanggalLahir;
    @FXML
    private TableColumn<Pasien, String> tbcAlamat;

    Pasien pasien;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    Pasien selectedPasien = null;

    public void showAll() {
        try {
            con = DBUtil.getConnection();
            String sql = "SELECT * FROM pasien";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            Integer no = 0;
            while (rs.next()) {
                no++;

                tbcNo.setCellValueFactory(new PropertyValueFactory<Pasien, Integer>("no"));
                tbcNamaLengkap.setCellValueFactory(new PropertyValueFactory<Pasien, String>("namaPasien"));
                tbcNik.setCellValueFactory(new PropertyValueFactory<Pasien, String>("nik"));
                tbcTanggalLahir.setCellValueFactory(new PropertyValueFactory<Pasien, Date>("tanggalLahir"));
                tbcAlamat.setCellValueFactory(new PropertyValueFactory<Pasien, String>("alamat"));

                ObservableList<Pasien> data = FXCollections.observableArrayList(
                        new Pasien(rs.getInt("id"), no, rs.getString("nama_pasien"), rs.getString("nik"),
                                rs.getDate("tanggal_lahir"), rs.getString("alamat")));
                tbvPasien.getItems().addAll(data);
            }

            tbvPasien.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                selectedPasien = newSelection;
                System.out.println("selection model => " + (selectedPasien != null ? selectedPasien.getId() : null));
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambah() throws IOException {
        tbvPasien.getSelectionModel().clearSelection();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("form.fxml"));
        Parent detailParent = loader.load();
        FormController detailController = loader.getController();
        detailController.initData(null, this);
        Scene detailScene = new Scene(detailParent);
        Stage detailStage = new Stage();
        detailStage.setScene(detailScene);
        detailStage.show();
    }

    public void edit() throws IOException {
        if (selectedPasien != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("form.fxml"));
            Parent detailParent = loader.load();
            FormController detailController = loader.getController();
            detailController.initData(selectedPasien, this);
            Scene detailScene = new Scene(detailParent);
            Stage detailStage = new Stage();
            detailStage.setScene(detailScene);
            detailStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Harap pilih pasien terlebih dahulu!");
            alert.showAndWait();
        }
    }

    public void refresh() {
        tbvPasien.getSelectionModel().clearSelection();
        tbvPasien.getItems().clear();
        showAll();
    }

    public void delete() {
        if (selectedPasien != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Anda yakin ingin menghapus data ini?");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        con = DBUtil.getConnection();
                        String sql = "DELETE FROM pasien WHERE id = ?";
                        pstmt = con.prepareStatement(sql);
                        pstmt.setInt(1, selectedPasien.getId());

                        pstmt.executeUpdate();
                        tbvPasien.getSelectionModel().clearSelection();

                        refresh();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // User clicked Cancel or closed the dialog
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Harap pilih pasien terlebih dahulu!");
            alert.showAndWait();
        }
    }

    public void quit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        showAll();
    }

}
