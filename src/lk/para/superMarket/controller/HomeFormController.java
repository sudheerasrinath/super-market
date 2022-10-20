package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeFormController {
    public AnchorPane icnSideAnchorPane;
    public AnchorPane NameSideAnchorPane;
    public AnchorPane MainAnchorePane;
    public Label lblUserType;
    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public JFXButton btnOrder;
    public JFXButton btnReports;
    public JFXButton btnSetting;

    String userType;

    public void initialize() throws IOException {
        NameSideAnchorPane.setVisible(false);
        setUI("DashBoardForm");

    }

    public void iconSideOnAction(MouseEvent mouseEvent) {
        NameSideAnchorPane.setVisible(true);
    }

    public void NameSideOnAction(MouseEvent mouseEvent) {
        NameSideAnchorPane.setVisible(false);
    }

    public void setUI(String URI) throws IOException {
        MainAnchorePane.getChildren().clear();
        MainAnchorePane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/para/superMarket/view/" + URI + ".fxml")));
    }

    public void btnButtonOnAction(MouseEvent mouseEvent) throws IOException {
        Object button = mouseEvent.getSource();
        if(button instanceof JFXButton){
            JFXButton jfxButton = (JFXButton) button;
            if (jfxButton.getId().equals("CustomerButton")){
                setUI("CustomerForm");
            }else if (jfxButton.getId().equals("ItemButton")){
                setUI("ItemForm");
            }else if (jfxButton.getId().equals("OrderButton")){
                setUI("OrderForm");
            }else if (jfxButton.getId().equals("ReportsButton")){

            }else if (jfxButton.getId().equals("SettingButton")){

            }
        }
    }

    public void getAllData(String value) {
        this.userType = value;
        lblUserType.setText(value);
       // System.out.println(value);
        if(value.equalsIgnoreCase("Admin")){
            btnOrder.setDisable(true);
            btnCustomer.setDisable(true);
        }else if(value.equalsIgnoreCase("Cashier")){
            btnReports.setDisable(true);
            btnItem.setDisable(true);
        }
    }
}
