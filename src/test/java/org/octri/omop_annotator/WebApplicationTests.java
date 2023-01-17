package org.octri.omop_annotator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = { "classpath:application.properties", "classpath:test-application.properties" })
@SpringBootTest
public class WebApplicationTests {

	@Test
	public void canary() {
		assertThat("Canary test with full application context", true, is(equalTo(true)));
	}

}
