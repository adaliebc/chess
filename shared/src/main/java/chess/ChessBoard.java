package chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static chess.EscapeSequences.SET_BG_COLOR_DARK_GREEN;
import static chess.EscapeSequences.SET_BG_COLOR_GREEN;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares = new ChessPiece[9][9];
    public ChessBoard() {
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
       return squares[position.getRow()][position.getColumn()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    private boolean isValidMove(Collection<ChessMove> validMoves, ChessPosition potentialPosition){
        if(validMoves.isEmpty()){
            return false;
        }
        for (ChessMove move : validMoves){
            if(move.endPosition == potentialPosition){
                return true;
            }
        }
        return false;
    }

    public String toStringWhite(Collection<ChessMove> validMoves) {
        //String box = "";
        String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for(String letter :columns) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + "\s\s" + letter);
        }
        System.out.println();
        for(int x = 1; x < 9; x++) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + x);
            for(int y = 1; y < 9; y++) {
                String whitePiece;
                ChessPiece item = getPiece(new ChessPosition(x, y));
                if(item != null) {
                    whitePiece = item.toString();
                } else {
                    whitePiece = " \u2003 ";
                }
                ChessPosition potentialMove = new ChessPosition(x, y);
                String bgColorLight = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                String bgColorDark = EscapeSequences.SET_BG_COLOR_DARK_GREY;
                if(isValidMove(validMoves, potentialMove)){
                    bgColorLight = SET_BG_COLOR_GREEN;
                    bgColorDark = SET_BG_COLOR_DARK_GREEN;
                }
                //box += piece;
                if (x % 2 == 0 && y % 2 == 0){
                    System.out.print(bgColorLight + whitePiece);
                } else if (x % 2 == 0 && y % 2 != 0){
                    System.out.print(bgColorDark + whitePiece);
                } else if (x % 2 != 0 && y % 2 == 0){
                    System.out.print(bgColorDark + whitePiece);
                } else if (x % 2 != 0 && y % 2 != 0){
                    System.out.print(bgColorLight + whitePiece);
                }
            }
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + x);
            System.out.println();
            //box += "\n";
        }
        for(String letter :columns) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + "\s\s" + letter);
        }
        System.out.println();
        System.out.println();
        //return box;
        return "";
    }

    public String toStringBlack(Collection<ChessMove> validMoves) {
        //String box = "";
        String[] columns = {"H", "G", "F", "E", "D", "C", "B", "A"};
        for(String letter :columns){
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + "\s\s" + letter);
        }
        System.out.println();
        for(int x = 8; x >= 1; x--) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + x);
            for (int y = 8; y >= 1; y--) {
                String blackPiece;
                ChessPiece item = getPiece(new ChessPosition(x, y));
                if (item != null) {
                    blackPiece = item.toString();
                } else {
                    blackPiece = " \u2003 ";
                }
                //box += piece;
                ChessPosition potentialMove = new ChessPosition(x, y);
                String bgColorLight = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                String bgColorDark = EscapeSequences.SET_BG_COLOR_DARK_GREY;
                if(isValidMove(validMoves, potentialMove)){
                    bgColorLight = SET_BG_COLOR_GREEN;
                    bgColorDark = SET_BG_COLOR_DARK_GREEN;
                }
                //box += piece;
                if (x % 2 == 0 && y % 2 == 0){
                    System.out.print(bgColorLight + blackPiece);
                } else if (x % 2 == 0 && y % 2 != 0){
                    System.out.print(bgColorDark + blackPiece);
                } else if (x % 2 != 0 && y % 2 == 0){
                    System.out.print(bgColorDark + blackPiece);
                } else if (x % 2 != 0 && y % 2 != 0){
                    System.out.print(bgColorLight + blackPiece);
                }
            }
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + x);
            System.out.println();
            //box += "\n";
        }
        //return box;
        for(String letter :columns) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + "\s\s" + letter);
        }
        System.out.println();
        return "";
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        //Create Pieces
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece blackKing= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece blackKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece blackBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece blackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);

        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece whiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece whiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        //Create Positions
        ChessPosition place = new ChessPosition(8, 1);
        addPiece(place, blackRook);
        place = new ChessPosition(8, 8);
        addPiece(place, blackRook);
        place = new ChessPosition(8, 2);
        addPiece(place, blackKnight);
        place = new ChessPosition(8, 7);
        addPiece(place, blackKnight);
        place = new ChessPosition(8, 3);
        addPiece(place, blackBishop);
        place = new ChessPosition(8, 6);
        addPiece(place, blackBishop);
        place = new ChessPosition(8, 4);
        addPiece(place, blackQueen);
        place = new ChessPosition(8, 5);
        addPiece(place, blackKing);

        for (int i = 1; i < 9; i ++) {
            place = new ChessPosition(7, i);
            addPiece(place, blackPawn);
        }
        place = new ChessPosition(1, 1);
        addPiece(place, whiteRook);
        place = new ChessPosition(1, 8);
        addPiece(place, whiteRook);
        place = new ChessPosition(1, 2);
        addPiece(place, whiteKnight);
        place = new ChessPosition(1, 7);
        addPiece(place, whiteKnight);
        place = new ChessPosition(1, 3);
        addPiece(place, whiteBishop);
        place = new ChessPosition(1, 6);
        addPiece(place, whiteBishop);
        place = new ChessPosition(1, 4);
        addPiece(place, whiteQueen);
        place = new ChessPosition(1, 5);
        addPiece(place, whiteKing);

        for (int i = 1; i < 9; i ++) {
            place = new ChessPosition(2, i);
            addPiece(place, whitePawn);
        }
        }

    public ChessPosition getKing(ChessGame.TeamColor team) {
        for (int x = 1; x < 9; x++){
            for (int y = 1; y < 9; y++){
                ChessPosition place = new ChessPosition(x, y);
                ChessPiece piece = getPiece(place);
                    if (piece != null){
                        if (piece.getTeamColor() == team && piece.getPieceType() == ChessPiece.PieceType.KING) {
                            return place;
                        }
                    }
                }
            }
        return null;
    }

    public Collection<ChessPosition> getOtherPieces(ChessGame.TeamColor team) {

        Collection<ChessPosition> pieceLocations = new HashSet<ChessPosition>();
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                ChessPosition place = new ChessPosition(x, y);
                ChessPiece piece = getPiece(place);
                if (piece != null){
                    if (piece.getTeamColor() == team) {
                        pieceLocations.add(place);
                    }
                }
            }
        }
        return pieceLocations;
    }
    }
