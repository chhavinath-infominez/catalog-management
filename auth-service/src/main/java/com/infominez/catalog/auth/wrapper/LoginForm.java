package com.infominez.catalog.auth.wrapper;

import com.infominez.catalog.auth.base.BaseResponse;
import com.infominez.catalog.auth.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class LoginForm {
    private String phone;
    private String pin;
    private String fireBaseId;

    public boolean validate(BaseResponse response) {
        if (this.phone == null || this.phone.isEmpty()) {
            response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
            return false;
        }
        if (this.pin == null || this.pin.isEmpty()) {
            response.set(302, Constants.PLEASE_ENTER_VALID_PIN);
            return false;
        }
        return true;
    }
}
