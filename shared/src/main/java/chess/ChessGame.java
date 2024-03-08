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
    public ChessGame(String chessGame) {

    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", team=" + team +
                '}';
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.team;
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
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece currentPiece = board.getPiece(startPosition);
        Collection<ChessMove> possibleMoves = new HashSet<ChessMove>(currentPiece.pieceMoves(this.board, startPosition));
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        //ChessBoard originalBoard = board;
        for (ChessMove move : possibleMoves) {
            //board = originalBoard;
            //removing piece from startPosition
            board.addPiece(startPosition, null);
            ChessPiece oldPiece = board.getPiece(move.endPosition);
            //add piece to end position
            if (move.promotionPiece != null) {
                ChessPiece newPiece = new ChessPiece(currentPiece.pieceColor, move.promotionPiece);
                board.addPiece(move.endPosition, newPiece);
            } else {
                board.addPiece(move.endPosition, currentPiece);
            }
            if (!isInCheck(currentPiece.getTeamColor()) && !isInStalemate(currentPiece.getTeamColor())) {
                validMoves.add(move);
            }
            //undoing change
            board.addPiece(move.endPosition, oldPiece);
            board.addPiece(startPosition, currentPiece);


        }
        //board = originalBoard;
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.startPosition;
        ChessPosition endPosition = move.endPosition;
        ChessPiece currentPiece = board.getPiece(startPosition);
        ChessPiece.PieceType promotionPiece = move.promotionPiece;
        ChessPiece newPiece = new ChessPiece(currentPiece.pieceColor, promotionPiece);
        Collection<ChessMove> validMoves = validMoves(startPosition);

        if (currentPiece.getTeamColor() != this.getTeamTurn()){
            throw new InvalidMoveException();
        }
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

        if (this.team == TeamColor.WHITE){
            this.team = TeamColor.BLACK;
        } else {
            this.team = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = board.getKing(teamColor);
        TeamColor enemy;

        if (teamColor == TeamColor.BLACK) {
            enemy = TeamColor.WHITE;
        } else {
            enemy = TeamColor.BLACK;
        }

        Collection<ChessPosition> enemyPositions = board.getOtherPieces(enemy);
        Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

        for (ChessPosition piecePos : enemyPositions) {
            ChessPiece piece = board.getPiece(piecePos);
            possibleMoves.addAll(piece.pieceMoves(board, piecePos));
        }
        for (ChessMove move : possibleMoves) {
            if (move.endPosition.equals(kingPos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        return isInCheck(teamColor) && isInStalemate(teamColor);
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessPosition> ownPositions = board.getOtherPieces(teamColor);

        for (ChessPosition piecePos : ownPositions) {
            ChessPiece currentPiece = board.getPiece(piecePos);
            Collection<ChessMove> possibleMoves = new HashSet<ChessMove>(currentPiece.pieceMoves(this.board, piecePos));
            Collection<ChessMove> validMoves = new HashSet<ChessMove>();
            //ChessBoard originalBoard = board;
            for (ChessMove move : possibleMoves) {
                //removing piece from startPosition
                board.addPiece(piecePos, null);
                ChessPiece oldPiece = board.getPiece(move.endPosition);
                //add piece to end position
                if (move.promotionPiece != null) {
                    ChessPiece newPiece = new ChessPiece(currentPiece.pieceColor, move.promotionPiece);
                    board.addPiece(move.endPosition, newPiece);
                } else {
                    board.addPiece(move.endPosition, currentPiece);
                }
                if (!isInCheck(team)) {
                    //undoing change
                    board.addPiece(move.endPosition, oldPiece);
                    board.addPiece(piecePos, currentPiece);
                    return false;
                }
                //undoing change
                board.addPiece(move.endPosition, oldPiece);
                board.addPiece(piecePos, currentPiece);


            }

        } return true;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
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
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }
}
