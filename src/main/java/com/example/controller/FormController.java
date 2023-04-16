package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.App;
import com.example.model.Pasien;
import com.example.util.DBUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FormController implements Initializable {

    @FXML
    private TextField txfNik;

    @FXML
    private TextField txfNamaPasien;

    @FXML
    private DatePicker dpkTanggalLahir;

    @FXML
    private TextField txfAlamat;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    Pasien pasien;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    Pasien selectedPasien = null;

    public void submit() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            con = DBUtil.getConnection();

            if (selectedPasien == null) {
                String sql = "INSERT INTO `pasien` (`nama_pasien`, `nik`, `tanggal_lahir`, `alamat`) VALUES (?, ?, ?, ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, txfNamaPasien.getText());
                pstmt.setString(2, txfNik.getText());
                
                LocalDate localDate = dpkTanggalLahir.getValue();
                LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
                java.util.Date utilDate = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                pstmt.setString(3, sdf.format(utilDate));
                pstmt.setString(4, txfAlamat.getText());

                pstmt.executeUpdate();
            } else {
                String sql = "UPDATE `pasien` SET `nama_pasien` = ?, `nik` = ?, `tanggal_lahir` = ?, `alamat` = ? WHERE `id` = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, txfNamaPasien.getText());
                pstmt.setString(2, txfNik.getText());

                LocalDate localDate = dpkTanggalLahir.getValue();
                LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
                java.util.Date utilDate = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                pstmt.setString(3, sdf.format(utilDate));
                pstmt.setString(4, txfAlamat.getText());
                pstmt.setInt(5, selectedPasien.getId());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cancel();
    }

    MenuController parentController = null;

    public void initData(Pasien parameterValue, MenuController parent) {
        selectedPasien = parameterValue;
        if (selectedPasien != null) {
            txfNik.setText(selectedPasien.getNik());
            txfNamaPasien.setText(selectedPasien.getNamaPasien());
            dpkTanggalLahir.setValue(selectedPasien.getTanggalLahir().toLocalDate());
            txfAlamat.setText(selectedPasien.getAlamat());
        }

        parentController = parent;
    }

    public void quit() {
        Platform.exit();
    }

    public void cancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        parentController.refresh();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int nikMax = 15;
        int namaPasienMax = 20;
        int alamatMax = 50;

        txfNik.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txfNik.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (newValue.length() > nikMax) {
                txfNik.setText(newValue.substring(0, nikMax));
            }
        });
        
        txfNamaPasien.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > namaPasienMax) {
                txfNamaPasien.setText(newValue.substring(0, namaPasienMax));
            }
        });
        
        txfAlamat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > alamatMax) {
                txfAlamat.setText(newValue.substring(0, alamatMax));
            }
        });
    }

}
