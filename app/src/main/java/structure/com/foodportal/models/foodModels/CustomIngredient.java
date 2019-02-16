package structure.com.foodportal.models.foodModels;

public class CustomIngredient {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }


    public CustomIngredient(String name, int isHeader,String mainquantity,String subquantity) {
        this.name = name;
        this.mainquantity = mainquantity;
        this.subquantity = subquantity;
        this.name = name;
        this.isHeader = isHeader;
    }

    int isHeader;

    String mainquantity;

    public String getMainquantity() {
        return mainquantity;
    }

    public void setMainquantity(String mainquantity) {
        this.mainquantity = mainquantity;
    }

    public String getSubquantity() {
        return subquantity;
    }

    public void setSubquantity(String subquantity) {
        this.subquantity = subquantity;
    }

    String subquantity;



}
