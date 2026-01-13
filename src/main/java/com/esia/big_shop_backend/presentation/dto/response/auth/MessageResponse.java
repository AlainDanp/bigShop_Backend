package com.esia.big_shop_backend.presentation.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private String status;

    public MessageResponse(String message) {
        this.message = message;
        this.status = "success";
    }
}
