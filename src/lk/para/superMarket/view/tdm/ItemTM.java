/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/26/2022
 * Time        : 10:00 PM
 */

package lk.para.superMarket.view.tdm;

import java.math.BigDecimal;

public class ItemTM implements Comparable<ItemTM>{
    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private int qtyOnHand;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String description, String packSize, BigDecimal unitPrice, int qtyOnHand) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
    @Override
    public int compareTo(ItemTM o) {
        return itemCode.compareTo(o.getItemCode());
    }
}
