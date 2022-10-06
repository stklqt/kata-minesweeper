package de.ite.minesweeper;

import de.ite.minesweeper.Minesweeper.Xy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MinesweeperTest {

    private Minesweeper sut;

    @BeforeEach
    void setUp() {
        sut = new Minesweeper();
    }

    @Test
    void should_count_mines() {
        final String given = "3 3\n" +
                "*..\n" +
                "...\n" +
                "*.*";
        final String expected = "Field #1:\n" +
                "*10\n" +
                "231\n" +
                "*2*\n";
        assertThat(sut.count(given)).isEqualTo(expected);
    }

    @Test
    void should_build_a_fieldMap() {
        final String given = "2 3\n" +
                "*..\n" +
                "*.*";
        final Map<Xy, Character> expectedMap = new HashMap<>() {{
            put(new Xy(0,0), '*');
            put(new Xy(1,0), '.');
            put(new Xy(2,0), '.');
            put(new Xy(0,1), '*');
            put(new Xy(1,1), '.');
            put(new Xy(2,1), '*');
        }};

        assertThat(sut.buildFieldMap(given)).isEqualTo(expectedMap);
    }

    @Test
    void should_retrieve_fieldSize() {
        final String given = "2 3\n" +
                "*..\n" +
                "*.*";
        assertThat(sut.retrieveFieldSize(given)).isEqualTo(new Xy(3,2));
    }

    @ParameterizedTest
    @CsvSource({"1,1,3", "0,1,2", "2,0,0"})
    void should_count_one_field(int fieldX, int fieldY, int expectedCount) {
        final String fieldString = "3 3\n" +
                "*..\n" +
                "...\n" +
                "*.*";
        final Map<Xy, Character> fieldMap = sut.buildFieldMap(fieldString);
        assertThat(sut.getFieldCount(fieldMap, new Xy(fieldX,fieldY))).isEqualTo(expectedCount);
    }

    @Test
    void should_split_fields() {
        final String fieldString = "" +
                "1 1\n" +
                "*\n" +
                "1 2\n" +
                "**";
        final String[] expected = new String[] {
                "1 1\n" +
                "*\n",
                "1 2\n" +
                "**\n"
        };
        assertThat(sut.splitFields(fieldString)).isEqualTo(expected);
    }

    @Test
    void should_count_more_fields() {
        final String fieldString = "" +
                "1 1\n" +
                "*\n" +
                "1 2\n" +
                "**";
        final String expected = "" +
                "Field #1:\n" +
                "*\n" +
                "\n" +
                "Field #2:\n" +
                "**\n";
        assertThat(sut.count(fieldString)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0 0\n", "0 3\n", "3 0\n"})
    void should_ignore_fields_with_size_0(String fieldString) {
        assertThat(sut.count(fieldString)).isEqualTo("");
    }

}
