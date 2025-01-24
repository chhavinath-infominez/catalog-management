package com.infominez.catalog.auth.utils;

public interface ApiResponseMessages {

	String DATA_AVAILABLE = "Data Available";
	String NO_DATA_AVAILABLE = "No Data Found";
	String SUCCESS = "Success";
	String INVALID_REQUEST = "Invalid Request";
	String FAILURE_REQUEST = "Failed Request";

	String INVALID_TOKEN = "Authentication failed , invalid token";

	String OTP_VERIFIED_SUCCESSFULLY = "OTP verification successfully";
	String PIN_RESET_SUCCESSFULLY = "PIN Reset successfully.Kindly login with new PIN";
	String OTP_VERIFIED_FAILURE = "Invalid OTP";
	String OTP_GENERATED_SUCCESSFULLY = "OTP generated successfully";
	String OTP_SENT_TO_YOUR_REGISTRED_PHONE = "OTP sent to given phone number";
	String OTP_GENERATED_FAILED = "Unable to generate OTP";

	String USER_CREATED_SUCCESSFULLY = "User Created Successfully";
	String USER_NOT_FOUND = "User Not Found";

	String MEETING_CREATED_SUCCESSFULLY = "Meeting Created Successfully";
	String MEETING_NOT_FOUND = "Meeting Not Found";
	String MEETING_CREATION_FAILED = "Meeting Creation Failed";
	String GET_MEETING_HASH_FAILED = "Fetch Meeting by Hash Failed";
	String FETCH_MEETING_DATES_WITH_COUNT_FAILED = "Fetch meeting details with sum Failed";

	String MEETING_DAY_CREATED_SUCCESSFULLY = "Meeting Day Created Successfully";
	String MEETING_DAY_NOT_FOUND = "Meeting Day Not Found";

	String TIME_SLOT_CREATED_SUCCESSFULLY = "Time Slot Created Successfully";
	String TIME_SLOT_NOT_FOUND = "Time Slot Not Found";
	String TIME_SLOT_ID_NOT_FOUND = "Time Slot Id Not Found";
	String TIME_SLOT_UPDATE_FAILED = "Time Slot Update Failed";
	String TIME_SLOT_CREATE_FAILED = "Time Slot Create Failed";
	String TIME_SLOT_SHOULD_NOT_BE_OF_PAST = "Time Slot should be not less than current time";
	String TIME_SLOT_CREATE_FAILED_OVERLAPPING_DATES = "Time Slot Create Failed for Overlapping dates";
	String GET_TIME_SLOT_FAILED = "Fetch Time Slot Failed";
	String DELETE_TIME_SLOT_FAILED = "Delete Time Slot Failed";
	String DELETE_TIME_SLOT_SUCCESS = "Time Slot deleted Successfully";

	String VALIDATE_TIME_SLOT_DATES = "Time Slot end time should be before start time ";

	String MEETING_ATTENDEE_CREATED_SUCCESSFULLY = "Meeting Attendee Created Successfully";

	String MEETING_ATTENDEE_UPDATED_SUCCESSFULLY = "Meeting Attendee Updated Successfully";

	String MEETING_ATTENDEE_ALREADY_EXISTED = "Meeting Attendee Already Existed";

	String INVALID_MEETING_HASH_REQUEST = "Invalid Meeting Hash in Request";

	String MEETING_ATTENDEE_REGISTRATION_FAILED = "Meeting Invitee registration failed";
	String MEETING_ATTENDEE_IS_ORGANIZER = "You Can't be attendee because you are organizer";
	String MEETING_HAS_BEEN_CANCELD = "Meeting has been CANCELLED by organizer";

	String GET_LOCATIONS_FAILED = "Fetch location preferences failed";

	String LOCATION_NOT_FOUND = "No Location preferences found";

	String FINALIZE_MEETING_FAILED = "Meeting finalization failed";

	String MEETING_BASE_URL ="https://ecco-app.com/meet/";

    String PLEASE_ENTER_CORRECT_PIN = "Please enter correct pin";
    String CURRENT_PIN_IS_REQUIRED = "Current pin is required";
	String NEW_PIN_IS_REQUIRED = "New pin is required";
    String PLEASE_ENTER_VALID_PIN = "Please enter valid pin";
    String INVALID_OLD_PIN = "Invalid Old Pin";
	String NEW_PIN_CANNOT_BE_SAME_AS_OLD_PIN = "New Pin can not be same as old Pin";
	String PHONE_NUMBER_LIST_CANNOT_EMPTY = "Phone number list can not be empty";
	String INVITITION_SENT_ON_SMS = "Invitation sent via SMS";
}
