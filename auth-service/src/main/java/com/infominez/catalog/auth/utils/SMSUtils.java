package com.infominez.catalog.auth.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SMSUtils {
    public static final String apiKey = "436466AzXfF4wz675c1d94P1"; // Replace with your MSG91 API key
    public static final String senderId = "4567";
    public static final String ACCOUNT_SID =  "AC8d976604469c19810a0be7ecd76ca5ec";
    public static final String AUTH_TOKEN = "1a4e709e6df5bde365dcb30aceaad670";
    public static final String SMS0 = "Meeting link https://ecco-app.com/meet/c9hTTNm0" ;

    public static final String SMS1 = "  \n" +
            "COFFEE Pop Up Event TOMORROW! \uD83D\uDE80\n" +
            "Join us for a FREE cup ☕ of your favorite brew at  ⭐ VISION COFFEE ⭐\n" +
            "Voted \"Top 5 Best Local Coffee Shops\" \uD83D\uDC4F at Austin Coffee Festival\n" +
            "\n https://ecco-app.com/meet/c9hTTNm0 " +
            "Click the invite above to RSVP";
    public static final String SMS2 = "The event will be Thursday Oct. 26th from 10 to 12.\n" +
            "Vision is located at: \n" +
            "1601 Barton Springs Rd, Austin, TX 78704\n" +
            "It is the same location and is a part of Wanderlust Wine Co. - Barton Springs";

    public static void main(String[] args) {
        String santized1 = "+49 (0)711 / 61947-12".replaceAll("[^0-9+]", "");
        String santized   = santized1.substring(santized1.length()-10);
        System.out.println(santized);
    }

    public static String sendSMS(String tempUrl) {

        CompletableFuture.runAsync(() -> {
            try {
                String response;

                log.info("Temp URL : {}", tempUrl);

                long t1 = System.currentTimeMillis();
                URL url = new URL(tempUrl);

                log.info("URL : {}", url);

                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("GET");
                http.setDoOutput(true);
                http.setConnectTimeout(300000);
                http.setReadTimeout(300000);
                http.connect();
                int responseCode = http.getResponseCode();
                log.debug("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuffer response1 = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response1.append(inputLine);
                }
                in.close();
                response = response1.toString();
                log.info("Response : {}", response);
            } catch (Exception e) {
                log.error("Error : ", e);
            }
        });
//        String url = "http://msg.msgclub.net/rest/services/sendSMS/sendGroupSms?AUTH_KEY=1e402516cc778c23f83bad1d9f236094&message="
//                + sms
//                + "&senderId=SEPTET&routeId=1&mobileNos="
//                + mobileNos
//                + "&smsContentType=english&tmid=1707164922668720000";
//        System.out.println("URL: " + url);
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            String response = restTemplate.getForObject(url, String.class);
//            System.out.println("Response: " + response);
//        } catch (Exception e) {
//            System.out.println("Error sending SMS: " + e);
//        }
        return "SUUCESS";
    }
//
//    public static String sendSMS(String recipient, String messageBody) {
//            String url = "https://control.msg91.com/api/v5/otp?template_id=65f473abd6fc05261c052182&mobile=1"+recipient+"&authkey=418212Axmq4Hi7vDp65f471a6P1";
//            String tempRes = null;
//
//            Connection.Response result = null;
//            Integer statusCode = null;
//            try {
//                result = Jsoup.connect(url)
//                        .header("Content-Type", "application/json")
//                        .header("authkey", "418212Axmq4Hi7vDp65f471a6P1")
//                        .followRedirects(true)
//                        .ignoreHttpErrors(true)
//                        .ignoreContentType(true)
//                        .userAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML," +
//                                " like Gecko) Chrome/45.0.2454.4 Safari/537.36")
//                        .method(Connection.Method.POST)
//                        .requestBody(messageBody)
//                        .maxBodySize(1_000_000 * 30) // 30 mb ~
//                        .timeout(0) // infinite timeout
//                        .execute();
//                statusCode = result.statusCode();
//                log.info("OTP SMS sent statusCode : {}", statusCode);
//                tempRes = result.body();
//                log.info("OTP SMS sent after receiving    url : {}, requestBody : {}, response : {}",
//                        url, messageBody, tempRes);
//        } catch (Exception e) {
//            log.error("Error : ", e);
//            return "Error: " + e;
//        }
//        return "success";
//    }

//    public static String sendSMS(String to, String body) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Message message = Message.creator(
//                        new PhoneNumber(to), // TO
//                        new PhoneNumber("+18883851763"), // FROM
//                        body) // SMS
//                .create();
//
//        System.out.println(message.getSid());
//        return message.getSid();
//    }

//    public static String sendSms(String mobileno, String message) {
//        log.info("Executing sendSms()");
////        BaseResponse response = new BaseResponse();
//        try {
//            String encodeMessage = URLEncoder.encode(message, "UTF-8");
//            String tempUrl = "https://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=91"
//                    + mobileno + "&msg=" + encodeMessage +
//                    "&msg_type=TEXT&userid=2000203085&auth_scheme=plain&password=7AyFt94m&v=1.1&format=text&mask=MYMAYA";
//            URL url = new URL(tempUrl);
//            URLConnection con = url.openConnection();
//            HttpURLConnection http = (HttpURLConnection) con;
//            http.setRequestMethod("GET");
//            http.setDoOutput(true);
//            http.setConnectTimeout(300000);
//            http.setReadTimeout(300000);
//            http.connect();
//            int responseCode = http.getResponseCode();
//            log.info("responseCode of otp : {}",responseCode);
//            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
//            String inputLine;
//            StringBuilder responseMessage = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                responseMessage.append(inputLine);
//            }
//            in.close();
//            return "message sent";
//        } catch (Exception e) {
//            log.error("Error : ", e);
//            log.error("Exception while executing sendSms() : {}", e);
//            return null;
//        }
//    }


    public static String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new SecureRandom()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public static String randomGeneratorForSessionCode() {
        UUID randomUUID = UUID.randomUUID();
        String alphanumeric = randomUUID.toString().replaceAll("-", "");
        String randomString = alphanumeric.substring(0, 18).toUpperCase();
        String pattern = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(new Date());
        String subString = randomString + date;
        return subString;
    }


    public static boolean isValidPhone(String phone) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[1-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }
    public static boolean isValidPin(String number) {
        // Regular expression to match a 4-digit number
        String regex = "^[0-9]{4}$";

        // Check if the number matches the regex
        return Pattern.matches(regex, number);
    }
}
