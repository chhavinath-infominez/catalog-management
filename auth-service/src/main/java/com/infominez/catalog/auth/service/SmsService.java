package com.infominez.catalog.auth.service;

import com.infominez.catalog.auth.utils.SMSUtils;
import com.infominez.catalog.auth.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsService {

    public String sendOtp(Country country, String phone, String otp) {
        log.info("sendOtp(), country : {}, phone : {}", country, phone);
        String response = "";
        try {
            if (country.getPhoneCode().equalsIgnoreCase("+91")) {
                log.info("Sending SMS to Indian user...");
                String msg = "Dear customer, please enter the following one time password {#var#} to login into andbuy customer app. SEPTET";
                msg = msg.replace("{#var#}", otp);
                msg = msg.replaceAll(" ", "%20");
                log.info("Message : {}", msg);
                String tempUrl = "http://msg.msgclub.net/rest/services/sendSMS/sendGroupSms?AUTH_KEY=1e402516cc778c23f83bad1d9f236094&message="+msg+"&senderId=SEPTET&routeId=1&mobileNos="+phone+"&smsContentType=english&tmid=1707164922668720000";
                response = SMSUtils.sendSMS(tempUrl);
                log.info("SMS Response : {}", response);
            } else if (country.getPhoneCode().equalsIgnoreCase("+1")) {
                String url = "https://control.msg91.com/api/v5/otp?template_id=65f985f2d6fc0533f7002ad2&mobile=1"+phone+"&authkey=418212Axmq4Hi7vDp65f471a6P1";
                JSONObject requestBody = new JSONObject();
                requestBody.put("OTP", otp);
                Connection.Response result = null;
                Integer statusCode = null;
                try {
                    result = Jsoup.connect(url)
                            .header("Content-Type", "application/json")
                            .header("authkey", "418212Axmq4Hi7vDp65f471a6P1")
                            .followRedirects(true)
                            .ignoreHttpErrors(true)
                            .ignoreContentType(true)
                            .userAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML," +
                                    " like Gecko) Chrome/45.0.2454.4 Safari/537.36")
                            .method(Connection.Method.POST)
                            .requestBody(requestBody.toString())
                            .maxBodySize(1_000_000 * 30) // 30 mb ~
                            .timeout(0) // infinite timeout
                            .execute();
                    statusCode = result.statusCode();
                    log.info("Forgot OTP SMS sent statusCode : {}", statusCode);
                    response = result.body();
                    log.info("Forgot OTP SMS sent after receiving    url : {}, requestBody : {}, response : {}",
                            url, requestBody, requestBody);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            log.info("Exception while sending OTP : ", e);
        }
        return response;
    }
}
