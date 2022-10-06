package de.ite.minesweeper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Minesweeper {

    public static final char MINE_CHAR = '*';

    public String count(String fieldsString) {
        final String[] fieldStringList = splitFields(fieldsString);
        final StringBuilder resultString = new StringBuilder();
        int fieldCounter = 0;

        for (String fieldString : fieldStringList) {
            fieldCounter++;
            countOneField(resultString, fieldCounter, fieldString);
        }

        return resultString.toString();
    }

    private void countOneField(StringBuilder resultString, int fieldCounter, String fieldString) {
        final Xy fieldSize = retrieveFieldSize(fieldString);
        final Map<Xy, Character> fieldMap = buildFieldMap(fieldString);

        if (fieldSize.getY() == 0 || fieldSize.getX() == 0)
            return;

        if (fieldCounter >1) {
            resultString.append("\n");
        }

        resultString.append("Field #").append(fieldCounter).append(":");

        for (int y = 0; y < fieldSize.getY(); y++) {
            resultString.append("\n");
            for (int x = 0; x < fieldSize.getX(); x++) {
                final Character fieldValue = fieldMap.get(new Xy(x, y));

                if (fieldValue == MINE_CHAR) {
                    resultString.append(MINE_CHAR);
                } else {
                    resultString.append(getFieldCount(fieldMap, new Xy(x,y)));
                }

            }
        }
        resultString.append("\n");
    }

    Map<Xy, Character> buildFieldMap(String fieldString) {
        final String[] lines = fieldString.split("\n");
        final Map<Xy, Character> fieldMap = new HashMap<>();

        int y = 0;
        for (int line = 1; line < lines.length; line++) {
            int x = 0;
            for (char c : lines[line].toCharArray()) {
                fieldMap.put(new Xy(x,y), c);
                x++;
            }
            y++;
        }
        return fieldMap;
    }

    Xy retrieveFieldSize(String fieldString) {
        final String[] lines = fieldString.split("\n");
        final String[] fieldSize = lines[0].split(" ");
        return new Xy(Integer.parseInt(fieldSize[1]), Integer.parseInt(fieldSize[0]));
    }

    int getFieldCount(Map<Xy, Character> fieldMap, Xy fieldXy) {
        final List<Xy> surroundingFieldMatrix = Arrays.asList(
                new Xy(-1,-1), new Xy(0,-1), new Xy(1,-1),
                new Xy(-1,0),  new Xy(1,0),
                new Xy(-1,1), new Xy(0,1), new Xy(1,1)
        );
        int mineCounter = 0;
        for (Xy relativeXy : surroundingFieldMatrix) {
            final Xy absoluteXy = fieldXy.add(relativeXy);
            if (!fieldMap.containsKey(absoluteXy)) continue;
            if (fieldMap.get(absoluteXy) == MINE_CHAR) mineCounter++;
        }
        return mineCounter;
    }

    public String[] splitFields(String fieldString) {
        final String[] fieldLines = fieldString.split("\n");
        final List<String> result = new ArrayList<>();
        StringBuilder fieldHolder = new StringBuilder();
        for (String line : fieldLines) {
            if (isFieldHeadLine(line) && !isFirstLine(fieldLines, line)) {
                result.add(fieldHolder.toString());
                fieldHolder = new StringBuilder();
            }
            fieldHolder.append(line).append("\n");
        }
        result.add(fieldHolder.toString());
        return result.toArray(new String[0]);
    }

    private boolean isFirstLine(String[] fieldLines, String line) {
        return line.equals(fieldLines[0]);
    }

    private boolean isFieldHeadLine(String line) {
        return !(line.toCharArray()[0] == '*' || line.toCharArray()[0] == '.');
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Getter
    static class Xy {
        final int x;
        final int y;

        public Xy add(Xy xy) {
            return new Xy(this.x + xy.getX(), this.y + xy.getY());
        }
    }
}
