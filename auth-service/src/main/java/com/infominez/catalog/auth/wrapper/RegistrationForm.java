package com.infominez.catalog.auth.wrapper;

import com.infominez.catalog.auth.base.BaseResponse;
import com.infominez.catalog.auth.utils.Constants;
import com.infominez.catalog.auth.utils.EnumUtils;
import com.infominez.catalog.auth.entity.Country;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class RegistrationForm {

    private String username;

    private Integer countryId;

    private String phone;

    private String pin;

    private String confirmPin;

    private Date expirationTime;

    private String otp;

    private Boolean isVerified;

    private EnumUtils.SendOtpType otpType;

    private Country country;

    private String fireBaseId;


    public boolean validate(BaseResponse response) {
        if (this.username == null || this.username.isEmpty()) {
            response.set(302, Constants.USERNAME_REQUIRED);
            return false;
        }
        if(this.username.length() < 3){
            response.set(302, "Please enter more than 3 characters");
            return false;
        }
        if (this.phone == null || this.phone.isEmpty()) {
            response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
            return false;
        }
        if (this.pin == null || this.pin.isEmpty()) {
            response.set(302, Constants.PIN_REQUIRED);
            return false;
        }
        if (this.confirmPin == null || this.confirmPin.isEmpty()) {
            response.set(302,  Constants.RE_ENTER_YOUR_PIN_TO_PROCEED);
            return false;
        }
        return true;
    }
}
