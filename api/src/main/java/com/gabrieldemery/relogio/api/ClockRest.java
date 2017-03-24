package com.gabrieldemery.relogio.api;

import com.gabrieldemery.relogio.model.Angle;
import com.gabrieldemery.relogio.model.Clock;
import com.gabrieldemery.relogio.util.DummyCache;
import com.gabrieldemery.relogio.util.DummyCacheProvider;
import org.jboss.resteasy.annotations.providers.jackson.Formatted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/clock")
@Produces("application/json")
public class ClockRest {
	
	final Logger LOG = LoggerFactory.getLogger(ClockRest.class);

	private static final DummyCache<String, Clock> DUMMY_CACHE = DummyCacheProvider.getCache();

	@GET
	@Path("/{hour}")
	@Formatted
	public Response getAngleForHour(final @PathParam("hour") Integer hour) {
		return getAngle(hour, 0);
	}

	@GET
	@Path("/{hour}/{minute}")
	@Formatted
	public Response getAngleForHourAndMinute(final @PathParam("hour") Integer hour, final @PathParam("minute") Integer minute) {
		return getAngle(hour, minute);
	}

	private Response getAngle(final Integer hour, final Integer minute) {
		final String key = this.getCacheKey(hour, minute);
		final Angle angle;

		if(DUMMY_CACHE.contains(key)) {
			angle = DUMMY_CACHE.get(key).getAngle();
			return getResponseWithFormat(angle.getAngle());
		}

		final Clock clock = new Clock(hour, minute);
		angle = clock.getAngle();

		LOG.debug("Angle for "+ clock + " is "+ angle.getAngle());
		DUMMY_CACHE.put(key, clock);

		return getResponseWithFormat(angle.getAngle());
	}

	private Response getResponseWithFormat(final double angle) {
		return Response.ok("{\"angle\":"+ String.format("%.0f", angle) +"}\n").build();
	}

	private final String getCacheKey(final Integer hour, final Integer minute) {
		return hour+":"+minute;
	}

}