package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor team;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
        /*
        get the team color
         */
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        validMoves.addAll(ChessPiece.pieceMoves(this.board, startPosition));
        return validMoves;
        /*

         */
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessBoard board = getBoard();

        ChessPosition startPosition = move.startPosition;
        ChessPosition endPosition = move.endPosition;
        ChessPiece currentPiece = board.getPiece(startPosition);
        ChessPiece.PieceType promotionPiece = move.promotionPiece;
        ChessPiece newPiece = new ChessPiece(currentPiece.pieceColor, promotionPiece);
        Collection<ChessMove> validMoves = validMoves(startPosition);

        if (validMoves.contains(move)) {
            //removing piece from startPosition
            board.addPiece(startPosition, null);

            //add piece to end position
            if (promotionPiece != null) {
                board.addPiece(endPosition, newPiece);
            } else {
                board.addPiece(endPosition, currentPiece);
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        /* ChessPosition kingPos = King's Position
        teamcolor enemy;
        if (this.team == black) {
        enemy = white;
        } else {
        enemy = black;
        }
        for pieces in enemy
            if kingPos in their validMoves
            return true
           else
           return false
         */
        ChessPosition kingPos;
        TeamColor enemy;

        if (team == TeamColor.BLACK) {
            enemy = TeamColor.WHITE;
        }else {
            enemy = TeamColor.BLACK;
        }

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
        /*
            if teamcolor is in check
            kingsMoves = king's valid moves
            for moves in validMoves
            if move is in check,
                remove it
            if kingsMoves is empty
                return true
            else
                return false

             else
                return false
         */
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
        /* kingMoves = kings valid moves
        if kingmoves == empty
        return true

        else
        return false
         */
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
