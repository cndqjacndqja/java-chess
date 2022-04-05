package chess.web;

import chess.dto.MoveDTO;
import chess.service.ChessService;
import com.google.gson.Gson;

import static spark.Spark.*;

public class ChessController {
    private static final int roomId = 1;

    private final ChessService chessService = new ChessService();

    public void run() {
        Gson gson = new Gson();

        get("/board", (req, res) -> gson.toJson(chessService.getInitialBoard(roomId)));

        post("/move", (req, res) -> {
            MoveDTO moveDTO = gson.fromJson(req.body(), MoveDTO.class);
            return gson.toJson(chessService.move(moveDTO, roomId));
        });

        get("/status", (req, res) -> gson.toJson(chessService.getStatus()));

        post("/reset", (req, res) -> gson.toJson(chessService.resetBoard(roomId)));

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(exception.getMessage());
        });
    }
}