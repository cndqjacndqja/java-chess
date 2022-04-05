package chess.service;

import chess.dao.BoardDao;
import chess.dao.RoomDao;
import chess.domain.Score;
import chess.domain.Team;
import chess.domain.piece.Blank;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceFactory;
import chess.domain.position.Position;
import chess.domain.state.*;
import chess.dto.GameStateDTO;
import chess.dto.MoveDTO;
import chess.dto.PieceDTO;
import chess.dto.ScoreDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessService {
    private GameState gameState;
    BoardDao boardDao = new BoardDao();
    RoomDao roomDao = new RoomDao();

    public List<PieceDTO> getInitialBoard(int roomId) {
        gameState = getGameState(roomId);
        Map<String, String> board = getBoardPieces();
        return board.entrySet()
                .stream()
                .map(i -> new PieceDTO(i.getKey(), i.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    private GameState getGameState(int roomId) {
        Map<String, String> room = roomDao.findById(roomId);
        if (room == null) {
            return createGameState(roomId);
        }
        return getGameState(room);
    }

    private WhiteTurn createGameState(int roomId) {
        roomDao.save(roomId, "White");
        Map<Position, Piece> board = BoardInitialize.create();
        boardDao.saveAll(board);
        return new WhiteTurn(board);
    }

    private Playing getGameState(Map<String, String> room) {
        Map<String, String> boardDaos = boardDao.findAll(room.get("id"));
        Map<Position, Piece> board = new HashMap<>();
        for (String position : boardDaos.keySet()) {
            Piece piece = PieceFactory.create(boardDaos.get(position));
            board.put(Position.from(position), piece);
        }
        if (room.get("status").equals("White")) {
            return new WhiteTurn(board);
        }
        return new BlackTurn(board);
    }

    public GameStateDTO move(MoveDTO moveDTO, int roomId) {
        String source = moveDTO.getSource();
        String destination = moveDTO.getDestination();
        gameState = gameState.move(source, destination);

        updateMove(roomId, source, destination);

        Team team = gameState.getTeam();
        return new GameStateDTO(team.getValue(), gameState.isRunning());
    }

    private void updateMove(int roomId, String source, String destination) {
        Map<Position, Piece> board = gameState.getBoard();
        Piece sourcePiece = board.get(Position.from(source));
        boardDao.updatePosition(source, Blank.SYMBOL);
        boardDao.updatePosition(destination, sourcePiece.getSymbol());
        if (!gameState.isRunning()) {
            roomDao.delete(roomId);
            return;
        }
        roomDao.updateStatus(gameState.getTeam().getValue(), roomId);
    }

    public ScoreDTO getStatus() {
        Score score = new Score(gameState.getBoard());
        return new ScoreDTO(score.getTotalScoreWhiteTeam(), score.getTotalScoreBlackTeam());
    }

    public List<PieceDTO> resetBoard(int roomId) {
        gameState = createGameState(roomId);
        Map<String, String> board = getBoardPieces();
        return board.entrySet()
                .stream()
                .map(i -> new PieceDTO(i.getKey(), i.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    private Map<String, String> getBoardPieces() {
        Map<Position, Piece> chessBoard = gameState.getBoard();
        Map<String, String> board = new HashMap<>();
        for (Position position : chessBoard.keySet()) {
            Piece piece = chessBoard.get(position);
            board.put(position.getPositionToString(), piece.getSymbol());
        }
        return board;
    }
}
