package structure.com.foodportal.interfaces;

import structure.com.foodportal.models.Order;

public interface OrderListner {

    void Click(int pos);
    void Accept(Order order, int pos,String dateandtime);
    void Reject(Order order,int pos);
}
