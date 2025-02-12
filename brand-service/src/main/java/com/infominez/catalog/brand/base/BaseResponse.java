/**
 *
 */
package com.infominez.catalog.brand.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class BaseResponse<T> implements Serializable {

    private Integer status;
    private String message;
    private T response;
    @JsonIgnore
    private String handlerUrl;


    public BaseResponse<T> set(Integer status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
        return this;
    }

    public BaseResponse<T> set(Integer status, String message) {
        this.status = status;
        this.message = message;
        return this;
    }

    public BaseResponse<T> setInternalServerError() {
        this.status = 500;
        this.message = "Internal Server Error";
        this.response = null;
        return this;
    }

    public void setSomethingWentWrong() {
        this.status = 302;
        this.message = "Something went wrong. Please try later";
        this.response = null;
    }

    public BaseResponse<T> setUnauthorized() {
        this.status = 401;
        this.message = "Unauthorised Access";
        this.response = null;
        return this;
    }

    public BaseResponse<T> setInvalidPinOrPhone() {
        this.status = 401;
        this.message = "Invalid phone number or pin";
        this.response = null;
        return this;
    }

    public BaseResponse<T> setTemporaryDown() {
        this.status = 302;
        this.message = "We are unable to process your request currently. Kindly check after sometime.";
        this.response = null;
        return this;
    }
}
