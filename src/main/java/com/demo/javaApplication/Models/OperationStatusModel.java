package com.demo.javaApplication.Models;

import com.demo.javaApplication.Shared.OperationStatus;
import com.demo.javaApplication.Shared.OperationsName;

public class OperationStatusModel {
    private OperationsName operationName;
    private OperationStatus status;

    public OperationStatusModel(){
    }

    public OperationStatusModel(OperationsName operationName){
        this.operationName = operationName;
    }

    public OperationsName getOperationName() {
        return operationName;
    }

    public void setOperationName(OperationsName operationName) {
        this.operationName = operationName;
    }

    public OperationStatus getOperationResult() {
        return  status;
    }

    public void setOperationResult(OperationStatus operationResult) {
        this.status = operationResult;
    }
}
