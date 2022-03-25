package chess.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @ParameterizedTest
    @ValueSource(strings = {"e5", "c8", "h2", "a3"})
    @DisplayName("출발 지점과 도착 지점을 입력 후, 이동할 수 없으면 예외가 발생한다.")
    void validateIsPossible(String input) {
        Position position = Position.from("d4");
        Rook rook = new Rook(Team.BLACK, position);
        Assertions.assertThatThrownBy(() -> {
                    rook.validateIsPossible(Position.from(input));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 말이 움직일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"f8", "h6", "b6", "f3"})
    @DisplayName("출발 지점과 도착 지점을 입력 후, 경로 리스트를 조회한다.")
    void isPossible(String input) {
        Position position = Position.from("f6");
        Rook rook = new Rook(Team.BLACK, position);
        rook.validateIsPossible(Position.from(input));
    }
}