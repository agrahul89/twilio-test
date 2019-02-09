package test.twillio.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import test.twillio.cache.SingletonCache;
import test.twillio.service.TwilioTestService;

@Path("/sms")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class TwilioTestSmsResource extends BaseResource {

	private static final Logger LOGGER = LogManager
			.getLogger(TwilioTestSmsResource.class);

	@POST
	@Path("/callback")
	@Consumes({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	public Response callback(InputStream inStream) {
		LOGGER.info(" ~~~~~~~~~~~~~~~ Callback Details ~~~~~~~~~~~~~~~ ");
		LOGGER.info("Content-Type :: "
				+ headers.getRequestHeaders().get(HttpHeaders.CONTENT_TYPE));
		LOGGER.info("Content-Type First :: "
				+ headers.getRequestHeaders()
						.getFirst(HttpHeaders.CONTENT_TYPE));
		try {
			List<NameValuePair> params = URLEncodedUtils.parse(new String(
					IOUtils.toByteArray(inStream)), StandardCharsets.UTF_8);
			LOGGER.info("Request Body :: " + params);
		} catch (final IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		LOGGER.info(" ~~~~~~~~~~~~~~~ Callback Details ~~~~~~~~~~~~~~~ ");
		return Response.status(HttpStatus.SC_ACCEPTED).build();
	}

	@GET
	@Path("/twiml/{toContact}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_XML)
	public String getTwiml(@PathParam("toContact") String inToContact) {
		LOGGER.info("Get Message for Contact :: " + inToContact);
		LOGGER.info(SingletonCache.INSTANCE.getMessage(inToContact));
		return SingletonCache.INSTANCE.getMessage(inToContact);
	}

	@GET
	@Path("/push/{countryCode}/{toContact}/{message}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public String pushSMS(@PathParam("countryCode") String inCountryCode,
			@PathParam("toContact") String inToContact,
			@PathParam("message") String inMessage) {
		LOGGER.info("Country Code :: " + inCountryCode);
		LOGGER.info("Send Message To :: " + inToContact);
		LOGGER.info("Message Content :: " + inMessage);
		TwilioTestService.pushSMS(inCountryCode, inToContact, inMessage);
		return "Success";
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		String message = "Successfully connected to SMS TEST Service";
		LOGGER.info(message);
		return message;
	}

}
