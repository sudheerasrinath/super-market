package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.para.superMarket.bo.custom.CustomerBO;
import lk.para.superMarket.bo.custom.impl.CustomerBOImpl;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.view.tdm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFormController {
    public JFXTextField txtCusId;
    public JFXTextField txtCusName;
    public JFXTextField txtCusAddress;
    public JFXTextField txtCusCity;
    public JFXTextField txtCusProvince;
    public JFXTextField CusPostalCode;
    public TableView tblCusTable;
    public TableColumn colCusId;
    public TableColumn colCusTitle;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusCity;
    public TableColumn colCusProvince;
    public TableColumn colCusPostalCode;
    public JFXComboBox<String>cmbCusTitle;
    public JFXButton btnAddCustomer;

    private final CustomerBO customerBO = new CustomerBOImpl();


    public void initialize(){
        cmbCusTitle.getItems().add("Mr");
        cmbCusTitle.getItems().add("Mrs");
        cmbCusTitle.getItems().add("Miss");

        colCusId.setCellValueFactory(new PropertyValueFactory("CusID"));
        colCusTitle.setCellValueFactory(new PropertyValueFactory("CusTitle"));
        colCusName.setCellValueFactory(new PropertyValueFactory("CusName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory("CusAddress"));
        colCusCity.setCellValueFactory(new PropertyValueFactory("City"));
        colCusProvince.setCellValueFactory(new PropertyValueFactory("Province"));
        colCusPostalCode.setCellValueFactory(new PropertyValueFactory("PostalCode"));

        loadAllCustomer();
        txtCusId.setText(generateNewId());

    }

    public void loadAllCustomer(){
        tblCusTable.getItems().clear();

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customer : allCustomers) {
                tblCusTable.getItems().add(new CustomerTM(customer.getCusID(), customer.getCusTitle(), customer.getCusName(),customer.getCusAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddCustomer(ActionEvent actionEvent) {
        String CusID = txtCusId.getText();
        String CusTitle = cmbCusTitle.getValue();
        String CusName = txtCusName.getText();
        String CusAddress = txtCusAddress.getText();
        String City =  txtCusCity.getText();
        String Province =  txtCusProvince.getText();
        String PostalCode = CusPostalCode.getText();

        if (btnAddCustomer.getText().equalsIgnoreCase("Add Customer")) {

            try {
                if (existCustomer(CusID)) {
                    new Alert(Alert.AlertType.ERROR, CusID + " already exists").show();
                }
                customerBO.saveCustomer(new CustomerDTO(CusID, CusTitle, CusName, CusAddress, City, Province, PostalCode));
                tblCusTable.getItems().add(new CustomerTM(CusID, CusTitle, CusName, CusAddress, City, Province, PostalCode));
                new Alert(Alert.AlertType.CONFIRMATION, "Added Customer Successfully").show();
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            try {
                if (!existCustomer(CusID)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + CusID).show();
                }

                customerBO.updateCustomer(new CustomerDTO(CusID, CusTitle, CusName, CusAddress, City, Province, PostalCode));
                loadAllCustomer();
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Customer Successfully").show();
                btnAddCustomer.setText("Add Customer");
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + CusID + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnCustomerClear(ActionEvent actionEvent) {
        cmbCusTitle.setValue(null);
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusCity.clear();
        txtCusProvince.clear();
        CusPostalCode.clear();
    }

    public void menuEditOnAction(ActionEvent actionEvent) {
        btnAddCustomer.setText("Update");
        CustomerTM selectItem = (CustomerTM) tblCusTable.getSelectionModel().getSelectedItem();
        txtCusId.setText(selectItem.getCusID());
        cmbCusTitle.setValue(selectItem.getCusTitle());
        txtCusName.setText(selectItem.getCusName());
        txtCusAddress.setText(selectItem.getCusAddress());
        txtCusCity.setText(selectItem.getCity());
        txtCusProvince.setText(selectItem.getProvince());
        CusPostalCode.setText(selectItem.getPostalCode());
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
        CustomerTM selectItem = (CustomerTM) tblCusTable.getSelectionModel().getSelectedItem();
        String id = selectItem.getCusID();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCusTable.getItems().remove(tblCusTable.getSelectionModel().getSelectedItem());
            tblCusTable.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Customer Successfully").show();
            txtCusId.setText(generateNewId());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean existCustomer(String CusID) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(CusID);
    }

    private String generateNewId(){
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblCusTable.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<CustomerTM>tempCustomersList = new ArrayList<>(tblCusTable.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCusID();
    }

    private void clearAllTexts() {
        txtCusId.clear();
        cmbCusTitle.setValue(null);
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusCity.clear();
        txtCusProvince.clear();
        CusPostalCode.clear();
    }
}
