package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.para.superMarket.bo.custom.ItemBO;
import lk.para.superMarket.bo.custom.impl.ItemBOImpl;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.view.tdm.CustomerTM;
import lk.para.superMarket.view.tdm.ItemTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemFormController {
    public JFXTextField txtItmCode;
    public JFXTextField txtItmDescription;
    public JFXTextField txtItmPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItmQtyOnHand;
    public TableView tblItmTable;
    public TableColumn colItmCode;
    public TableColumn colItmDescription;
    public TableColumn colItmPackSize;
    public TableColumn colItmUnitPrice;
    public TableColumn colItmQtyOnHand;
    public TableColumn colItmDiscount;
    public TextField txtSearchItem;
    public JFXButton btnAddItem;

    private final ItemBO itemBO = new ItemBOImpl();


    public void initialize(){
        colItmCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        colItmDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colItmPackSize.setCellValueFactory(new PropertyValueFactory("packSize"));
        colItmUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colItmQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));

        loadAllItem();
        txtItmCode.setText(generateNewID());
    }

    public void loadAllItem(){
        tblItmTable.getItems().clear();

        try {
            ArrayList<ItemDTO> allItem = itemBO.getAllItems();
            for (ItemDTO item : allItem) {
                tblItmTable.getItems().add(new ItemTM(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnAddItem(ActionEvent actionEvent) {
        String code = txtItmCode.getText();
        String description = txtItmDescription.getText();
        String packSize = txtItmPackSize.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qtyOnHand =  Integer.parseInt(txtItmQtyOnHand.getText());


        if (btnAddItem.getText().equalsIgnoreCase("Add Item")) {

            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                itemBO.saveItem(new ItemDTO(code, description, packSize, unitPrice, qtyOnHand));
                tblItmTable.getItems().add(new ItemTM(code, description, packSize, unitPrice, qtyOnHand));
                new Alert(Alert.AlertType.CONFIRMATION, "Added Item Successfully").show();
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the item " + e.getMessage()).show();
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }

                itemBO.updateItem(new ItemDTO(code, description, packSize, unitPrice, qtyOnHand));
                loadAllItem();
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Item Successfully").show();
                btnAddItem.setText("Add Customer");
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the item " + code + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(code);
    }

    public void menuEditOnAction(ActionEvent actionEvent) {
        btnAddItem.setText("Update");
        ItemTM selectItem = (ItemTM) tblItmTable.getSelectionModel().getSelectedItem();
        txtItmCode.setText(selectItem.getItemCode());
        txtItmDescription.setText(selectItem.getDescription());
        txtItmPackSize.setText(selectItem.getPackSize());
//        txtUnitPrice.setText();
//        txtItmQtyOnHand.setText(selectItem.getQtyOnHand());
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
        ItemTM selectItem = (ItemTM) tblItmTable.getSelectionModel().getSelectedItem();
        String code = selectItem.getItemCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the code " + code).show();
            }
            itemBO.deleteItem(code);
            tblItmTable.getItems().remove(tblItmTable.getSelectionModel().getSelectedItem());
            tblItmTable.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Item Successfully").show();
            txtItmCode.setText(generateNewID());


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewID(){
        try {
            return itemBO.generateNewItemCode();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblItmTable.getItems().isEmpty()) {
            return "I00-001";
        } else {
            String id = getLastItemCode();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("I00-%03d", newCustomerId);
        }
    }

    private String getLastItemCode() {
        List<ItemTM> tempCustomersList = new ArrayList<>(tblItmTable.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getItemCode();
    }


    public void btnItemClear(ActionEvent actionEvent) {
        txtItmDescription.clear();
        txtItmPackSize.clear();
        txtUnitPrice.clear();
        txtItmQtyOnHand.clear();
    }

    public void clearAllTexts(){
        txtItmCode.clear();
        txtItmDescription.clear();
        txtItmPackSize.clear();
        txtUnitPrice.clear();
        txtItmQtyOnHand.clear();
    }
}
