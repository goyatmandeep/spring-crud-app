package com.demo.javaApplication.Models;

import com.demo.javaApplication.Shared.OperationStatus;
import com.demo.javaApplication.Shared.OperationsName;

public class OperationStatusModel {
    private String operationName;
    private String operationStatus;

    public OperationStatusModel(){
    }

    public OperationStatusModel(String operationName){
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationStatus() {
        return  operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }
}
