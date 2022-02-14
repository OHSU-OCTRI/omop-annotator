package org.octri.omop_annotator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(locations = { "classpath:application.properties", "classpath:test-application.properties" })
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebApplicationTests {

	@Test
	public void canary() {
		assertThat("Canary test with full application context", true, is(equalTo(true)));
	}

}
