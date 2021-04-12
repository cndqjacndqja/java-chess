package domain.piece.position;

import domain.exception.InvalidPositionException;

import java.util.Objects;

public class Position {
    public static final int POSITION_INPUT_LENGTH = 2;

    public static final Position EMPTY_POSITION = new Position(null, null);
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position valueOf(int row, int column) {
        return new Position(Row.findRow(row), Column.findColumn(column));
    }

    public static Position of(String input) {
        validateLength(input);
        Column col = Column.convertColumn(input.charAt(0));
        Row row = Row.convertRow(input.charAt(1));
        return new Position(row, col);
    }

    private static void validateLength(String input) {
        if (input.length() > POSITION_INPUT_LENGTH || input.length() <= 0) {
            throw new InvalidPositionException();
        }
    }

    public static int makeRowDiff(Position start, Position end) {
        return end.row.getIndex() - start.row.getIndex();
    }

    public static int makeColumnDiff(Position start, Position end) {
        return end.column.getIndex() - start.column.getIndex();
    }

    public Position move(Direction direction) {
        try {
            return Position.valueOf(row.getIndex() + direction.getX(), column.getIndex() + direction.getY());
        } catch (InvalidPositionException e) {
            return EMPTY_POSITION;
        }
    }

    public boolean validPosition() {
        return !this.equals(EMPTY_POSITION);
    }

    @Override
    public String toString() {
        return column.toString() + row.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}