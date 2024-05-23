package com.notification.sender.dto;

import com.notification.sender.util.IDUtil;
import lombok.Data;

import java.util.Date;

@Data
public class ApiResponse<T> {

    private long requestId;
    private String message;
    private Date timestamp;
    private T data;

    public ApiResponse() {
        this.requestId = IDUtil.getGlobalApiRequestId();
        this.timestamp = new Date();
    }
}