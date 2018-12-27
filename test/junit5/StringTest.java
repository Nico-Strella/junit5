package junit5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class StringTest {
	
	private String str;
	
	//SETUP	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Initialize connection to database");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("Close connection to database");
	}
	
	@BeforeEach
	void beforeEach(TestInfo info) {
		System.out.println("=======================================\nInitialize Test Data for " + info.getDisplayName());
	}
	
	@AfterEach
	void afterEach(TestInfo info) {
		System.out.println("Clean up Test Data for " + info.getDisplayName() + "\n=======================================");
	}
	
	//TESTS
	@Test
	void length_basic() {
		assertEquals(4, "ABCD".length());
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"ABCD", "ABC", "A", "DEF"})
	void length_greater_than_zero(String str) {
		assertTrue(str.length()>0);
	}
	
	@ParameterizedTest(name = "{0} toUpperCase is {1}")
	@CsvSource(value= {"abcd, ABCD", "abc, ABC", "'',''", "abcdefg, ABCDEFG"})
	void uppercase(String word, String capitalizedWord) {
		assertEquals(capitalizedWord, word.toUpperCase());
	}
	
	@ParameterizedTest(name = "{0} length is {1}")
	@CsvSource(value= {"abcd, 4", "abc, 3", "'',0", "abcdefg, 7"})
	void length(String word, int expectedLength) {
		assertEquals(expectedLength, word.length());
	}
	
	@Test
	@DisplayName("When length is null, throw an exception")
	void length_exception() {
		String str = null;
		assertThrows(NullPointerException.class, () -> {
			str.length();
		});
	}
	
	@Test
	void perfomanceTest() {
		assertTimeout(Duration.ofMillis(10), () -> {
			for (int i = 0; i <= 10; i++) {
				int j = i;
				System.out.println(j + "ms");
			}
		});
	}
	
	@Test
	@Disabled
	void toUpperCase_basic() {
		assertEquals("ABCD", "abcd".toUpperCase());
	}
	
	@Test
	@RepeatedTest(10)
	void contains_basic() {
		assertFalse("abcdefgh".contains("ijk"));
	}
	
	@Test
	void split_basic() {
		String str = "abc def ghi";
		String actualResult[] = str.split(" ");
		String[] expectedResult = new String[] {"abc", "def", "ghi"};
		assertArrayEquals(expectedResult, actualResult);
	}
	
	@Nested
	@DisplayName("For an empty String")
	class EmptyStringTests{
		
		@BeforeEach
		void setToEmtpy() {
			str = "";
		}
		
		@Test
		@DisplayName("length should be zero")
		void lengthIsZero() {
			assertEquals(0, str.length());
		}
		
		@Test
		@DisplayName("upper case is empty")
		void uppercaseIsEmtpy() {
			assertEquals("", str.toUpperCase());
		}
	}
	
	@Nested
	@DisplayName("For large strings")
	class LargeStringTests{
		
		@BeforeEach
		void setToLargeString() {
			str = "fdASFDAsfVCasfAdagfsnhkrybdHGBSWTN";
		}
		
		@Test
		@DisplayName("string shouldn't be null")
		void stringIsNotNull() {
			assertNotNull(str);
		}
	}
}
