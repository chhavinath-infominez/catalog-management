package com.infominez.catalog.auth.base;

import com.infominez.catalog.auth.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {


//	@Autowired
//	private TokenProvider tokenProvider;

	public User getLoggedInUser(String token) {
//		String phoneNumber = null;
//		try {
//			 phoneNumber = tokenProvider.getPhoneNumberByAccessToken(token);
//
//			if (!StringUtils.isEmpty(phoneNumber)) {
//
//				return userService.findByPhoneNumber(phoneNumber);
//
//			} else {
//
//				return null;
//
//			}
//		} catch (Exception exception) {
//
//			log.error("Error while fetching user details from access token with phone number {}",phoneNumber, exception);
//			exception.printStackTrace();
//
//		}
		return null;

	}
//    public Users getUser(HttpServletRequest request){
//        try{
//        String token = request.getHeader("Authorization").substring("Bearer".length()).trim();
//        String username = tokenProvider.getUsernameByAccessToken(token);
//        if (username != null){
//            return userService.findUserByName(username);
//        }else{
//            return null;
//        }
//        }catch (Exception e){
//            log.error("Error : ", e);
//            return null;
//        }
//    }
//}
	
}
