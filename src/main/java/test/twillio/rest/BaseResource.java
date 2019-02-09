package test.twillio.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public class BaseResource {
	@Context
	protected HttpHeaders headers;
	@Context
	protected SecurityContext secCtx;
	@Context
	protected UriInfo uriInfo;
}
