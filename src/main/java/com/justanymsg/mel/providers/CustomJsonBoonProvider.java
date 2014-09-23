package com.justanymsg.mel.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.boon.json.ObjectMapper;
import org.boon.json.implementation.ObjectMapperImpl;

@Provider
@Consumes({ MediaType.APPLICATION_JSON, "text/json" })
@Produces({ MediaType.APPLICATION_JSON, "text/json" })
public class CustomJsonBoonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private static final String UTF_8 = "UTF-8";
	
	protected ObjectMapper mapper;

	public CustomJsonBoonProvider() {
		if (mapper == null) {
			mapper = new ObjectMapperImpl();
		}
	}

	/**
	 * Helper method used to check whether given media type is JSON type or sub
	 * type. Current implementation essentially checks to see whether
	 * {@link MediaType#getSubtype} returns "json" or something ending with
	 * "+json".
	 */
	protected boolean isJsonType(MediaType mediaType) {
		if (mediaType != null) {
			// Ok: there are also "xxx+json" subtypes, which count as well
			String subtype = mediaType.getSubtype();
			return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json");
		}
		return true;
	}

	public long getSize(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		// Hardcoding because to find the size it requires actual writing.
		return -1;
	}

	/**
	 * Method that JAX-RS container calls to try to check whether given value
	 * (of specified type) can be serialized by this provider. Implementation
	 * will first check that expected media type is a JSON type (via call to
	 * {@link #isJsonType}; then verify that type is not one of "untouchable"
	 * types (types we will never automatically handle), and finally that there
	 * is a serializer for type (iff {@link #checkCanSerialize} has been called
	 * with true argument -- otherwise assumption is there will be a handler)
	 */
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (!isJsonType(mediaType)) {
			return false;
		}
		return true;
	}

	/**
	 * Method that JAX-RS container calls to serialize given value.
	 */
	public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		mapper.writeValue(entityStream, value);
		writer.close();

	}

	/**
	 * Method that JAX-RS container calls to try to check whether values of
	 * given type (and media type) can be deserialized by this provider.
	 * Implementation will first check that expected media type is a JSON type
	 * (via call to {@link #isJsonType}; then verify that type is not one of
	 * "untouchable" types (types we will never automatically handle), and
	 * finally that there is a deserializer for type (iff
	 * {@link #checkCanDeserialize} has been called with true argument --
	 * otherwise assumption is there will be a handler)
	 */
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (!isJsonType(mediaType)) {
			return false;
		}
		return true;
	}

	/**
	 * Method that JAX-RS container calls to deserialize given value.
	 */
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		return mapper.readValue(entityStream, type);
	}
}
