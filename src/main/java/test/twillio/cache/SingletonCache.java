package test.twillio.cache;

import java.util.HashMap;
import java.util.Map;

public enum SingletonCache {
	INSTANCE;

	private final Map<String, String> message = new HashMap<String, String>();
	private final Map<String, String> twiMlResponse = new HashMap<String, String>();

	public String getMessage(final String inToContact) {
		return message.get(inToContact);
	}

	public String getTwiMlResponse(final String inToContact) {
		return twiMlResponse.get(inToContact);
	}

	public void removeMessage(final String inToContact) {
		message.remove(inToContact);
	}

	public void removeTwiMlResponse(final String inToContact) {
		twiMlResponse.remove(inToContact);
	}

	public void setMessage(final String inToContact, final String inMessage) {
		this.message.put(inToContact, inMessage);
	}

	public void setTwiMlResponse(final String inToContact,
			final String inTwiMlResponse) {
		this.twiMlResponse.put(inToContact, inTwiMlResponse);
	}

}
