package test.twillio.rest;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Message;

public class TwilioRestClientFactory {

	private static final String ACCOUNT_SID = "ACebb7fac837bd8de38aabb73925cefe12";
	private static final String AUTH_TOKEN = "207044f7b41c8420959278317b35c2ff";
	private static TwilioRestClientFactory factoryInstance = null;
	private static final Logger LOGGER = LogManager
			.getLogger(TwilioRestClientFactory.class);
	private static final String TWI_CALLER_ID = "+12519294455";

	private TwilioRestClientFactory() {
		// Default No-Arg Constructor
	}

	public static TwilioRestClientFactory getInstance() {
		if (factoryInstance == null) {
			factoryInstance = new TwilioRestClientFactory();
		}
		return factoryInstance;
	}

	public Call makeNewCall(final List<NameValuePair> inParams) {
		inParams.add(getTwilioCallerId());
		Call callInstance = null;
		try {
			callInstance = getTwilioAccount().getCallFactory().create(inParams);
		} catch (final TwilioRestException ex) {
			LOGGER.error("Failed to Make Call to Target", ex);
		}
		return callInstance;
	}

	public Call makeNewCall(final Map<String, String> inParams) {
		inParams.put("From", TWI_CALLER_ID);
		Call callInstance = null;
		try {
			callInstance = getTwilioAccount().getCallFactory().create(inParams);
		} catch (final TwilioRestException ex) {
			LOGGER.info("Failed to Make Call to Target");
		}
		return callInstance;
	}

	public Message sendSMS(final List<NameValuePair> inParams) {
		inParams.add(getTwilioCallerId());
		Message smsInstance = null;
		try {
			smsInstance = getTwilioAccount().getMessageFactory().create(
					inParams);
		} catch (final TwilioRestException ex) {
			LOGGER.info("Failed to send SMS to Target");
		}
		return smsInstance;
	}

	private TwilioRestClient getRestClient() {
		return new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}

	private Account getTwilioAccount() {
		return getRestClient().getAccount();
	}

	private NameValuePair getTwilioCallerId() {
		return new BasicNameValuePair("From", TWI_CALLER_ID);
	}
}
