package config;

import static org.assertj.core.api.Assertions.assertThat;
import model.Card;

import org.junit.Test;

import resource.SprintTrackerServer;

import com.sun.jersey.api.core.DefaultResourceConfig;

public class VrandeshJerseyApplicationTest {

	@Test
	public void should_not_scan_classpath() throws Exception {
		DefaultResourceConfig config = new VrandeshJerseyApplication();
		assertThat(config.getExplicitRootResources()).isEmpty();
		assertThat(config.getClasses()).containsOnly(SprintTrackerServer.class, Card.class, org.codehaus.jackson.jaxrs.JacksonJsonProvider.class, org.codehaus.jackson.jaxrs.JsonParseExceptionMapper.class, org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class, org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper.class);
		assertThat(config.getRootResourceClasses()).containsOnly(SprintTrackerServer.class);
		assertThat(config.getFeatures()).containsEntry("com.sun.jersey.api.json.POJOMappingFeature", true);
	}
}
