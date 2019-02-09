package test.twillio.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HttpMethod;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import test.twillio.cache.SingletonCache;
import test.twillio.constant.UriConstants;
import test.twillio.rest.TwilioRestClientFactory;

import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class TwilioTestService {
	private static final Logger LOGGER = LogManager
			.getLogger(TwilioTestService.class);

	public static void pushCall(final String inCountryCode,
			final String inToContact, final String inMessage) {
		Call call = TwilioRestClientFactory.getInstance().makeNewCall(
				getCallOptions(inCountryCode, inToContact,
				// getTwiMl(inMessage)));
						getTwiMlGather()));
		LOGGER.info("Call SID ::" + call.getSid());
		LOGGER.info("Call JSON :: " + call.toJSON());
		LOGGER.info("Feedback :: " + call.getFeedback().toJSON());
	}

	public static void pushSMS(final String inCountryCode,
			final String inToContact, final String inMessage) {
		Message message = TwilioRestClientFactory.getInstance().sendSMS(
				getSmsOptions(inCountryCode, inToContact, inMessage));
		LOGGER.info("Message SID :: " + message.getSid());
	}

	private static List<NameValuePair> getCallOptions(
			final String inCountryCode, final String inToContact,
			final String inMessage) {
		final String toContact = inCountryCode + inToContact;
		List<NameValuePair> callParams = new ArrayList<NameValuePair>();
		SingletonCache.INSTANCE.setTwiMlResponse(toContact, inMessage);
		
		LOGGER.info("TwiML Response Saved? :: "
				+ StringUtils.isNotBlank(SingletonCache.INSTANCE
						.getTwiMlResponse(toContact)));
		callParams.add(new BasicNameValuePair("To", "+" + toContact));
		callParams.add(new BasicNameValuePair("Url",
				UriConstants.URI_RS_EP_CALL_TWML + toContact));
		callParams.add(new BasicNameValuePair("Method", "GET"));
		// Set Callback URL
		callParams.add(new BasicNameValuePair("StatusCallback",
				UriConstants.URI_RS_EP_CALL_CLBK));
		callParams.add(new BasicNameValuePair("StatusCallbackMethod", "POST"));
		callParams.add(new BasicNameValuePair("StatusCallbackEvent",
				"initiated"));
		callParams
				.add(new BasicNameValuePair("StatusCallbackEvent", "ringing"));
		callParams
				.add(new BasicNameValuePair("StatusCallbackEvent", "answered"));
		callParams.add(new BasicNameValuePair("StatusCallbackEvent",
				"completed"));
		// Set Fallback URL
		callParams.add(new BasicNameValuePair("FallbackUrl",
				UriConstants.URI_RS_EP_CALL_FLBK));
		callParams.add(new BasicNameValuePair("FallbackMethod", "POST"));
		// Set Timeout
		callParams.add(new BasicNameValuePair("Timeout", "10"));
		return callParams;
	}

	private static List<NameValuePair> getSmsOptions(
			final String inCountryCode, final String inToContact,
			final String inMessage) {
		final String toContact = inCountryCode + inToContact;
		List<NameValuePair> callParams = new ArrayList<NameValuePair>();
		SingletonCache.INSTANCE.setMessage(toContact, inMessage);
		LOGGER.info("Message Saved? :: "
				+ StringUtils.isNotBlank(SingletonCache.INSTANCE
						.getTwiMlResponse(toContact)));
		callParams.add(new BasicNameValuePair("To", "+" + toContact));
		callParams.add(new BasicNameValuePair("Body", inMessage));
		callParams.add(new BasicNameValuePair("StatusCallback",
				UriConstants.URI_RS_EP_SMS_CLBK));
		return callParams;
	}

	private static String getTwiMl(final String inMessage) {
		Say say = new Say(inMessage);
		say.setVoice("man");
		String response = StringUtils.EMPTY;
		try {
			TwiMLResponse twiML = new TwiMLResponse();
			response = twiML.append(say).toXML();
		} catch (TwiMLException ex) {
			LOGGER.error("Exception while creating Twilio Message XML", ex);
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>"
				+ response + "</Response>";
	}

	private static String getTwiMlGather() {

		Gather gather = new Gather();
		gather.setAction(UriConstants.URI_RS_EP_CALL_GTHR);
		gather.setMethod(HttpMethod.POST);
		gather.setFinishOnKey("#");
		gather.setTimeout(10);
		gather.setNumDigits(5);
		Say say = new Say("Please Enter a 5 Digit Code and Press Hash");
		say.setVoice("alice");
		String response = StringUtils.EMPTY;
		try {
			TwiMLResponse twiML = new TwiMLResponse();
			// response = twiML.append(say).toXML();
			gather.append(say);
			response = twiML.append(gather).toXML();
		} catch (TwiMLException ex) {
			LOGGER.error("Exception while creating Twilio Message XML", ex);
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>"
				+ response + "</Response>";
	}
}