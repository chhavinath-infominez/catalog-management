package com.infominez.catalog.auth.service;

import com.infominez.catalog.auth.AuthServiceApplication;
import com.infominez.catalog.auth.base.BaseResponse;
import com.infominez.catalog.auth.repository.CountryRepository;
import com.infominez.catalog.auth.repository.UserRepository;
import com.infominez.catalog.auth.utils.Constants;
import com.infominez.catalog.auth.utils.EnumUtils;
import com.infominez.catalog.auth.utils.SMSUtils;
import com.infominez.catalog.auth.entity.Country;
import com.infominez.catalog.auth.entity.User;
import com.infominez.catalog.auth.mapper.UserMapper;
import com.infominez.catalog.auth.wrapper.AuthResponse;
import com.infominez.catalog.auth.wrapper.LoginForm;
import com.infominez.catalog.auth.wrapper.RegistrationForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final CountryRepository countryRepository;

	private final JwtTokenService jwtTokenService;

	private final SmsService smsService;

	private static final int OTP_MAX_ATTEMPTS = 3;
	private static final long OTP_BLOCK_DURATION_MS = 2 * 60 * 1000; // 2 minutes in milliseconds  for testing purposes
	private static final Map<String, List<Long>> otpAttemptsMap = new HashMap<>();
	private static final Map<String, List<Long>> wrongOtpAttemptsMap = new HashMap<>();
	private static final Map<String, List<Long>> failedLoginAttemptsMap = new HashMap<>();


	public BaseResponse login(LoginForm loginForm) {
		log.info("Executing login() with : {}", loginForm);
		BaseResponse response = new BaseResponse();
		try{
			if (!loginForm.validate(response)) {
				return response;
			}
			if (!SMSUtils.isValidPhone(loginForm.getPhone())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
			}
			if (!SMSUtils.isValidPin(loginForm.getPin())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_PIN);
			}

			String phone = loginForm.getPhone();
			long currentTime = System.currentTimeMillis();

			// Clean up old failed login attempts
			failedLoginAttemptsMap.putIfAbsent(phone, new ArrayList<>());
			List<Long> failedAttempts = failedLoginAttemptsMap.get(phone);
			failedAttempts.removeIf(attemptTime -> currentTime - attemptTime > OTP_BLOCK_DURATION_MS);

			// Check if user is blocked
			if (failedAttempts.size() >= OTP_MAX_ATTEMPTS) {
				long earliestAttemptTime = failedAttempts.get(0);
				long waitTime = OTP_BLOCK_DURATION_MS - (currentTime - earliestAttemptTime);
				if (waitTime > 0) {
					return response.set(302, Constants.TIME_LIMIT_EXCEEDED_FOR_LOGIN);
				}
			}
			Optional<User> userOptional = userRepository.findByPhoneAndIsActive(loginForm.getPhone(), true);
			if(userOptional.isEmpty()){
				failedAttempts.add(currentTime);
				return response.set(302, Constants.INCORRECT_MOBILE_NUMBER_AND_PIN);
			}
			User user = userOptional.get();
			if (!user.getPin().equals(loginForm.getPin())){
				failedAttempts.add(currentTime);
				return response.set(302, Constants.INCORRECT_MOBILE_NUMBER_AND_PIN);
			}
			AuthResponse authResponse = jwtTokenService.generateToken(loginForm.getPhone());
			user.setFireBaseId(loginForm.getFireBaseId());
			user.setLastLoginTime(new Date());
			userRepository.save(user);
			// Clear failed attempts after successful login
			failedLoginAttemptsMap.remove(phone);
			response.set(200, "Login Success", authResponse);
		} catch (Exception e) {
			log.error("Exception while executing login(): ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}

	public BaseResponse sendOTP(RegistrationForm registrationForm) {
		log.info("Executing sendOTP() : {}", registrationForm);
		BaseResponse response = new BaseResponse();
		try {
			if (!SMSUtils.isValidPhone(registrationForm.getPhone())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
			}
			if (registrationForm.getOtpType() == null) {
				return response.set(302, Constants.OTP_TYPE_REQUIRED);
			}

			String phone = registrationForm.getPhone();
			long currentTime = System.currentTimeMillis();

			// Clean up old attempts
			otpAttemptsMap.putIfAbsent(phone, new ArrayList<>());
			List<Long> attempts = otpAttemptsMap.get(phone);
			attempts.removeIf(attemptTime -> currentTime - attemptTime > OTP_BLOCK_DURATION_MS);

			// Check OTP attempt limits
			if (attempts.size() >= OTP_MAX_ATTEMPTS) {
				long earliestAttemptTime = attempts.get(0);
				long waitTime = OTP_BLOCK_DURATION_MS - (currentTime - earliestAttemptTime);
				if (waitTime > 0) {
					return response.set(302, Constants.TIME_LIMIT_EXCEEDED);
				}
			}
			Optional<Country> countryOptional;
			Country country;
			String otp = SMSUtils.GenerateRandomNumber(4);
			System.out.println("OTP : " + otp);
			if(registrationForm.getOtpType().equals(EnumUtils.SendOtpType.REGISTRATION)){
				if (registrationForm.getCountryId() == null ) {
					return response.set(302, Constants.PLEASE_ENTER_VALID_COUNTRY_CODE);
				}
				countryOptional = countryRepository.findById(registrationForm.getCountryId());
				if (!countryOptional.isPresent()) {
					return response.set(302, Constants.COUNTRY_NOT_FOUND);
				}
				country = countryOptional.get();
				registrationForm.setCountry(country);
				Optional<User> userOptional = userRepository.findByPhoneAndIsActive(registrationForm.getPhone(), true);
				if (userOptional.isPresent()) {
					return response.set(302, Constants.MOBILE_NUMBER_ALREADY_REGISTERED);
				}
				String sid =smsService.sendOtp(country, registrationForm.getPhone(), otp);
				System.out.println(sid);
			}
			if(registrationForm.getOtpType().equals(EnumUtils.SendOtpType.FORGET_PIN)){
				Optional<User> userOptional = userRepository.findByPhoneAndIsActive(registrationForm.getPhone(), true);
				if (userOptional.isEmpty()) {
					return response.set(302, Constants.USER_NOT_FOUND);
				}
				country = userOptional.get().getCountry();
				String sid =smsService.sendOtp(country, registrationForm.getPhone(), otp);
				System.out.println(sid);
			}
			if(registrationForm.getOtpType().equals(EnumUtils.SendOtpType.RESET_PIN)){
				User user = userRepository.findByPhoneAndIsActive(registrationForm.getPhone(), true).orElse(null);
				if (user == null) {
					return response.set(302, Constants.USER_NOT_FOUND);
				}
				country = user.getCountry();
				String sid =smsService.sendOtp(country, registrationForm.getPhone(), otp);
				System.out.println(sid);
			}

			registrationForm.setExpirationTime(SMSUtils.addMinutesToDate(10, new Date()));
			registrationForm.setOtp(otp);
			registrationForm.setIsVerified(false);
			AuthServiceApplication.registrationMap.put(registrationForm.getPhone(), registrationForm);
			// Record OTP attempt
			attempts.add(currentTime);
			response.set(200, Constants.OTP_SENT_SUCCESSFULLY, registrationForm.getPhone());
		}catch (Exception e) {
			log.error("Exception while executing sendOTP() : ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}

	public BaseResponse verifyOTP(RegistrationForm registrationForm) {
		log.info("Executing validateEmailOTP() : {}", registrationForm);
		BaseResponse response = new BaseResponse();
		try {
			if (Objects.isNull(registrationForm.getPhone()) || registrationForm.getPhone().isEmpty()) {
				return response.set(302, Constants.PLEASE_ENTER_YOUR_MOBILE_NUMBER);
			}
			if (!SMSUtils.isValidPhone(registrationForm.getPhone())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
			}
			if (Objects.isNull(registrationForm.getOtp()) || registrationForm.getOtp().isEmpty()) {
				return response.set(302, Constants.PLEASE_ENTER_THE_OTP_SENT_TO_YOUR_MOBILE_NUMBER);
			}
			if (!SMSUtils.isValidPin(registrationForm.getOtp())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_OTP);
			}

			String phone = registrationForm.getPhone();
			long currentTime = System.currentTimeMillis();

			// Clean up old wrong attempts
			wrongOtpAttemptsMap.putIfAbsent(phone, new ArrayList<>());
			List<Long> wrongAttempts = wrongOtpAttemptsMap.get(phone);
			wrongAttempts.removeIf(attemptTime -> currentTime - attemptTime > OTP_BLOCK_DURATION_MS);

			// Check wrong OTP attempt limits
			if (wrongAttempts.size() >= OTP_MAX_ATTEMPTS) {
				long earliestAttemptTime = wrongAttempts.get(0);
				long waitTime = OTP_BLOCK_DURATION_MS - (currentTime - earliestAttemptTime);
				if (waitTime > 0) {
					return response.set(302, Constants.TIME_LIMIT_EXCEEDED_FOR_VERIFY_OTP);
				}
			}

			RegistrationForm savedRegistrationForm = AuthServiceApplication.registrationMap.get(registrationForm.getPhone());
			if (Objects.isNull(savedRegistrationForm)) {
				return response.set(302, Constants.USER_NOT_FOUND);
			}
			if (!registrationForm.getOtp().equals(savedRegistrationForm.getOtp())) {
				wrongAttempts.add(currentTime); // Record the wrong attempt
				return response.set(302, Constants.PLEASE_ENTER_VALID_OTP);
			}
			if (savedRegistrationForm.getExpirationTime().before(new Date())) {
				return response.set(302, Constants.OTP_EXPIRED);
			}
			savedRegistrationForm.setIsVerified(true);
			AuthServiceApplication.registrationMap.put(registrationForm.getPhone(),savedRegistrationForm);
			// Clear wrong attempts after successful verification
			wrongOtpAttemptsMap.remove(phone);
			response.set(200, Constants.OTP_VERIFIED_SUCCESSFULLY, registrationForm.getPhone());
		} catch (Exception e) {
			log.error("Exception while executing sendOTP() : ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}

	public BaseResponse resetPin(RegistrationForm registrationForm) {
		log.info("Executing resetPin() : {}", registrationForm);
		BaseResponse response = new BaseResponse();
		try{
			if (registrationForm.getPhone() == null || registrationForm.getPhone().isEmpty()){
				return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
			}
			if (!SMSUtils.isValidPhone(registrationForm.getPhone())) {
                return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
            }
			if (!SMSUtils.isValidPin(registrationForm.getPin())) {
                return response.set(302, Constants.PLEASE_ENTER_VALID_PIN);
            }
			if (!SMSUtils.isValidPin(registrationForm.getConfirmPin())) {
                return response.set(302, Constants.PLEASE_ENTER_VALID_CONFIRM_PIN);
            }
			if (registrationForm.getOtp() == null || registrationForm.getOtp().isEmpty()){
				return response.set(302, Constants.OTP_REQUIRED);
			}
			if (!SMSUtils.isValidPin(registrationForm.getOtp())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_OTP);
			}
			BaseResponse otpVerifiedResponse = verifyOTP(registrationForm);
			if(!otpVerifiedResponse.getStatus().equals(200) || otpVerifiedResponse.getStatus() != 200){
				return otpVerifiedResponse;
			}
			if (registrationForm.getPin() == null || registrationForm.getPin().isEmpty()){
				return response.set(302, Constants.PLEASE_ENTER_YOUR_NEW_PIN);
			}
			if (registrationForm.getConfirmPin() == null || registrationForm.getConfirmPin().isEmpty()){
				return response.set(302, Constants.PLEASE_RE_ENTER_YOUR_NEW_PIN);
			}
			if(!registrationForm.getPin().equals(registrationForm.getConfirmPin())) {
				return response.set(302, Constants.PIN_DID_NOT_MATCH);
			}
			Optional<User> userOptional = userRepository.findByPhoneAndIsActive(registrationForm.getPhone(), true);
			if(userOptional.isEmpty()) {
                return response.set(302, Constants.USER_NOT_FOUND);
            }
			User user = userOptional.get();
			user.setPin(registrationForm.getPin());
			user = userRepository.save(user);
			response.set(200, Constants.PIN_RESET_SUCCESSFULLY, user);
		} catch (Exception e) {
			log.error("Exception while executing resetPin() : ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}
	public BaseResponse forgetPin(RegistrationForm registrationForm) {
		log.info("Executing forgetAndResetPin() : {}", registrationForm);
		BaseResponse response = new BaseResponse();
		try{
			if (registrationForm.getPhone() == null || registrationForm.getPhone().isEmpty()) {
				return response.set(302, Constants.PHONE_REQUIRED);
			}
			if (!SMSUtils.isValidPhone(registrationForm.getPhone())) {
                return response.set(302, Constants.INVALID_PHONE);
            }
			if (registrationForm.getOtp() == null || registrationForm.getOtp().isEmpty()) {
				return response.set(302, Constants.OTP_REQUIRED);
			}
			BaseResponse otpVerifiedResponse = verifyOTP(registrationForm);
			if(!otpVerifiedResponse.getStatus().equals(200) || otpVerifiedResponse.getStatus() != 200){
				return otpVerifiedResponse;
			}
			if (registrationForm.getPin() == null || registrationForm.getPin().isEmpty()){
				return response.set(302, Constants.PLEASE_ENTER_YOUR_NEW_PIN);
			}
			if (registrationForm.getConfirmPin() == null || registrationForm.getConfirmPin().isEmpty()){
				return response.set(302, Constants.PLEASE_RE_ENTER_YOUR_NEW_PIN);
			}
			if(!registrationForm.getPin().equals(registrationForm.getConfirmPin())) {
				return response.set(302, Constants.PIN_DID_NOT_MATCH);
			}
			Optional<User> userOptional = userRepository.findByPhoneAndIsActive(registrationForm.getPhone(), true);
			if(userOptional.isEmpty()) {
				return response.set(302, Constants.USER_NOT_FOUND);
			}
			User user = userOptional.get();
			user.setPin(registrationForm.getPin());
			user = userRepository.save(user);
			response.set(200, Constants.PIN_RESET_SUCCESSFULLY, user);
		} catch (Exception e) {
			log.error("Exception while executing forgetAndResetPin() : ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}

	public BaseResponse registerUser(RegistrationForm registrationForm) {
		log.info("Executing registerUser() : {}", registrationForm);
		BaseResponse response = new BaseResponse();
		try{
			if (registrationForm == null) {
				return response.set(302, Constants.INVALID_REQUEST);
			}
			if (!registrationForm.validate(response)) {
				return response;
			}
			if (!SMSUtils.isValidPhone(registrationForm.getPhone())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_MOBILE_NUMBER);
			}
			if (!SMSUtils.isValidPin(registrationForm.getPin())) {
				return response.set(302, Constants.PLEASE_ENTER_VALID_PIN);
			}
			if(!registrationForm.getPin().equals(registrationForm.getConfirmPin())) {
				return response.set(302, Constants.PIN_DID_NOT_MATCH);
			}
			RegistrationForm savedRegistrationForm = AuthServiceApplication.registrationMap.get(registrationForm.getPhone());
			if (Objects.isNull(savedRegistrationForm)) {
				return response.set(302, Constants.USER_NOT_FOUND);
			}
			if(!savedRegistrationForm.getIsVerified()){
				return response.set(302, Constants.PHONE_NUMBER_NOT_VERIFIED);
			}
			savedRegistrationForm.setPin(registrationForm.getPin());
			savedRegistrationForm.setUsername(registrationForm.getUsername());
			savedRegistrationForm.setFireBaseId(registrationForm.getFireBaseId());
			User user = userRepository.save(userMapper.toEntity(savedRegistrationForm));
			AuthServiceApplication.registrationMap.remove(registrationForm.getPhone());
			AuthResponse authResponse = jwtTokenService.generateToken(user.getPhone());
			response.set(200, Constants.USER_REGISTERED_SUCCESSFULLY, authResponse);
		} catch (Exception e) {
			log.error("Exception while executing registerUser() : ", e);
			response.setSomethingWentWrong();
		}
		return response;
	}

	public User getUserInContext() {
		User user;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			System.out.println("User 1 : " + ((UserDetails) principal).getUsername());
			String phone = ((UserDetails) principal).getUsername();
			user = userRepository.findByPhoneAndIsActive(phone, true).get();
		} else {
			user = null;
		}
		return user;
	}
}
