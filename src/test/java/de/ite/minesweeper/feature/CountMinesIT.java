package de.ite.minesweeper.feature;

import de.ite.minesweeper.Minesweeper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountMinesIT {

    @Test
    void should_count_mines() {
        final Minesweeper minesweeper = new Minesweeper();

        final String given = "4 4\n" +
                "*...\n" +
                "....\n" +
                ".*..\n" +
                "....\n" +
                "3 5\n" +
                "**...\n" +
                ".....\n" +
                ".*...\n" +
                "0 0";
        final String expected = "Field #1:\n" +
                "*100\n" +
                "2210\n" +
                "1*10\n" +
                "1110\n" +
                "\n" +
                "Field #2:\n" +
                "**100\n" +
                "33200\n" +
                "1*100\n";
        Assertions.assertThat(minesweeper.count(given)).isEqualTo(expected);
    }
}
