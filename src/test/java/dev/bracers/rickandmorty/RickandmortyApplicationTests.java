package dev.bracers.rickandmorty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(RickandmortyApplicationTestsConfig.class)
class RickandmortyApplicationTests {

	@Test
	void contextLoads() {
	}

}
