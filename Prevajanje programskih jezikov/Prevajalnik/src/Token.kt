import java.io.File
import java.io.InputStream
import java.io.OutputStream

const val ERROR_STATE = 0

enum class Symbol {
      MAP,
    MESTO,
    GOZD,
    REKA,
    STAVBA,
    PLAC,
    BUSSTOP,
    BUY,
    UL,
    VAR,
    PONOVI,
    TRUE ,
    FALSE,
    KROG,
    KVA,
    TRI,
    CRT,
    EQ,
    AND,
    OR,
    LT,
    RT,
    ASSIGN,
    PLUS,
    MINUS,
    TIMES,
    DIV,
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    COMMA,
    COLON,
    ID,
    NUMBER,
    STRING,
    EOF,
    NOT,
    POINTER,
    SKIP

}

const val EOF = -1
const val NEWLINE = '\n'.code

interface DFA {
    val states: Set<Int>
    val alphabet: IntRange
    fun next(state: Int, code: Int): Int
    fun symbol(state: Int): Symbol
    val startState: Int
    val finalStates: Set<Int>
}

object ForForeachFFFAutomaton : DFA {
    override val states = (1 .. 100).toSet()

    override val alphabet = 0..255
    override val startState = 1
    override val finalStates =
        setOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,17, 18, 20, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 87,88,89 , 90,91, 92,93,94,95,96,97,98,99,100)

    private val numberOfStates = states.max() + 1 // plus the ERROR_STATE
    private val numberOfCodes = alphabet.max() + 1 // plus the EOF
    private val transitions = Array(numberOfStates) { IntArray(numberOfCodes) }
    private val values = Array(numberOfStates) { Symbol.SKIP }

    private fun setTransition(from: Int, chr: Char, to: Int) {
        transitions[from][chr.code + 1] = to // + 1 because EOF is -1 and the array starts at 0
    }

    private fun setTransition(from: Int, code: Int, to: Int) {
        transitions[from][code + 1] = to
    }

    private fun setSymbol(state: Int, symbol: Symbol) {
        values[state] = symbol
    }

    override fun next(state: Int, code: Int): Int {
        assert(states.contains(state))
        assert(alphabet.contains(code))
        return transitions[state][code + 1]
    }

    override fun symbol(state: Int): Symbol {
        assert(states.contains(state))
        return values[state]
    }

    init {
        setTransition(1, '<', 2)
        setTransition(1, '>', 3)
        setTransition(1, '-', 4)
        setTransition(1, '+', 5)
        setTransition(1, '*', 6)
        setTransition(1, '/', 7)
        setTransition(1, '[', 8) //  Symbol.LBRACKET
        setTransition(1, ']', 9) // Symbol.RBRACKET
        setTransition(1, '{', 10) // Symbol.LBRACE
        setTransition(1, '}', 11)// Symbol.RBRACE
        setTransition(1, '(', 12)// Symbol.LPAREN
        setTransition(1, ')', 13)//  Symbol.RPAREN
        setTransition(1, ',', 14)
        setTransition(1, ':', 89 )
        setTransition(1, '=', 17)
        setTransition(17 , '=', 18)
        setTransition(1, '&', 19)
        setTransition(19, '&', 20)
        setTransition(1, '|', 21)
        setTransition(21, '|', 22)
        setTransition(1,'!',95)




        setTransition(1, 'm' , 23)
        for(c in ('a'..'l')) setTransition(23, c , 30)
        for(c in ('n'..'z')) setTransition(23, c , 30)
        setTransition(23, 'a' , 24)
        for(c in ('b'..'z')) setTransition(24, c , 30)
        setTransition(24, 'p' , 25)



        setTransition(23 , 'e', 26)
        for(c in ('a'..'d')) setTransition(26, c , 30)
        for(c in ('f'..'z')) setTransition(26, c , 30)
        setTransition(26, 's', 27)
        for(c in ('a'..'r')) setTransition(27, c , 30)
        for(c in ('t'..'z')) setTransition(27, c , 30)
        setTransition(27, 't', 28)
        for(c in ('a'..'s')) setTransition(28, c , 30)
        for(c in ('u'..'z')) setTransition(28, c , 30)
        setTransition(28, 'o', 29)

        setTransition(1, 'g' , 31)
        for(c in ('a'..'f')) setTransition(31, c , 30)
        for(c in ('h'..'z')) setTransition(31, c , 30)
        setTransition(31, 'o' , 32)
        for(c in ('a'..'n')) setTransition(32, c , 30)
        for(c in ('p'..'z')) setTransition(32, c , 30)
        setTransition(32, 'z' , 33)
        for(c in ('a'..'y')) setTransition(33, c , 30)
        setTransition(33, 'd' , 34)

        setTransition(1, 'r' , 35)
        for(c in ('a'..'q')) setTransition(35, c , 30)
        for(c in ('s'..'z')) setTransition(35, c , 30)
        setTransition(35, 'e' , 36)
        for(c in ('a'..'d')) setTransition(36, c , 30)
        for(c in ('f'..'z')) setTransition(36, c , 30)
        setTransition(36, 'k' , 37)
        for(c in ('a'..'j')) setTransition(37, c , 30)
        for(c in ('l'..'z')) setTransition(37, c , 30)
        setTransition(37, 'a' , 38)

        setTransition(1, 's' , 39)
        for(c in ('a'..'r')) setTransition(39, c , 30)
        for(c in ('t'..'z')) setTransition(39, c , 30)
        setTransition(39, 't' , 40)
        for(c in ('a'..'s')) setTransition(40, c , 30)
        for(c in ('u'..'z')) setTransition(40, c , 30)
        setTransition(40, 'a' , 41)
        for(c in ('b'..'z')) setTransition(41, c , 30)
        setTransition(41, 'v' , 42)
        for(c in ('a'..'u')) setTransition(42, c , 30)
        for(c in ('w'..'z')) setTransition(42, c , 30)
        setTransition(42, 'b' , 43)
        for(c in ('a'..'a')) setTransition(43, c , 30)
        for(c in ('c'..'z')) setTransition(43, c , 30)
        setTransition(43, 'a' , 44)


        setTransition(1, 'p' , 45)
        for(c in ('a'..'o')) setTransition(45, c , 30)
        for(c in ('q'..'z')) setTransition(45, c , 30)
        setTransition(45, 'l' , 46)
        for(c in ('a'..'k')) setTransition(46, c , 30)
        for(c in ('m'..'z')) setTransition(46, c , 30)
        setTransition(46, 'a' , 47)
        for(c in ('b'..'z')) setTransition(47, c , 30)
        setTransition(47, 'c' , 48)

        setTransition(45, 'o' , 49)
        for(c in ('a'..'n')) setTransition(49, c , 30)
        for(c in ('p'..'z')) setTransition(49, c , 30)
        setTransition(49, 'n' , 50)
        for(c in ('a'..'m')) setTransition(50, c , 30)
        for(c in ('o'..'z')) setTransition(50, c , 30)
        setTransition(50, 'o' , 51)
        for(c in ('a'..'n')) setTransition(51, c , 30)
        for(c in ('p'..'z')) setTransition(51, c , 30)
        setTransition(51, 'v' , 52)
        for(c in ('a'..'u')) setTransition(52, c , 30)
        for(c in ('w'..'z')) setTransition(52, c , 30)
        setTransition(52, 'i' , 53)



        setTransition(49 , 'i' ,96 )
        for(c in ('a'..'h')) setTransition(96, c , 30)
        for(c in ('j'..'z')) setTransition(96, c , 30)
        setTransition(96 , 'n' , 97)
        for(c in ('a'..'m')) setTransition(97, c , 30)
        for(c in ('o'..'z')) setTransition(97, c , 30)
        setTransition(97, 't' , 98)
        for(c in ('a'..'s')) setTransition(98, c , 30)
        for(c in ('u'..'z')) setTransition(98, c , 30)
        setTransition(98, 'e' , 99)
        for(c in ('a'..'d')) setTransition(99, c , 30)
        for(c in ('f'..'z')) setTransition(99, c , 30)
        setTransition(99, 'r' , 100)












        setTransition(1, 'b' , 54)
        for(c in ('a'..'a')) setTransition(54, c , 30)
        for(c in ('c'..'z')) setTransition(54, c , 30)
        setTransition(54, 'u' , 55)
        for(c in ('a'..'t')) setTransition(55, c , 30)
        for(c in ('v'..'z')) setTransition(55, c , 30)
        setTransition(55, 's' , 56)
        for(c in ('a'..'r')) setTransition(56, c , 30)
        for(c in ('t'..'z')) setTransition(56, c , 30)
        setTransition(56, 's' , 57)
        for(c in ('a'..'r')) setTransition(57, c , 30)
        for(c in ('t'..'z')) setTransition(57, c , 30)
        setTransition(57, 't' , 58)
        for(c in ('a'..'s')) setTransition(58, c , 30)
        for(c in ('u'..'z')) setTransition(58, c , 30)
        setTransition(58, 'o' , 59)
        for(c in ('a'..'n')) setTransition(59, c , 30)
        for(c in ('p'..'z')) setTransition(59, c , 30)
        setTransition(59, 'p' , 60)

        setTransition(55, 'y' , 61)

        setTransition(1, 'v' , 62)
        for(c in ('a'..'u')) setTransition(62, c , 30)
        for(c in ('w'..'z')) setTransition(62, c , 30)
        setTransition(62, 'a' , 63)
        for(c in ('b'..'z')) setTransition(63, c , 30)
        setTransition(63, 'r' , 64)

        setTransition(1, 't' , 65)
        for(c in ('a'..'s')) setTransition(65, c , 30)
        for(c in ('u'..'z')) setTransition(65, c , 30)
        setTransition(65, 'r' , 66)
        for(c in ('a'..'q')) setTransition(66, c , 30)
        for(c in ('s'..'z')) setTransition(66, c , 30)
        setTransition(66, 'u' , 67)
        for(c in ('a'..'t')) setTransition(67, c , 30)
        for(c in ('v'..'z')) setTransition(67, c , 30)
        setTransition(67, 'e' , 68)


        setTransition(66, 'i' , 69)

        setTransition(1, 'f' , 70)
        for(c in ('a'..'e')) setTransition(70, c , 30)
        for(c in ('g'..'z')) setTransition(70, c , 30)
        setTransition(70, 'a' , 71)
        for(c in ('b'..'z')) setTransition(71, c , 30)
        setTransition(71, 'l' , 72)
        for(c in ('a'..'k')) setTransition(72, c , 30)
        for(c in ('m'..'z')) setTransition(72, c , 30)
        setTransition(72, 's' , 73)
        for(c in ('a'..'r')) setTransition(73, c , 30)
        for(c in ('t'..'z')) setTransition(73, c , 30)
        setTransition(73, 'e' , 74)

        setTransition(1, 'c' , 75)
        for(c in ('a'..'b')) setTransition(75, c , 30)
        for(c in ('d'..'z')) setTransition(75, c , 30)
        setTransition(75, 'r' , 76)
        for(c in ('a'..'q')) setTransition(76, c , 30)
        for(c in ('s'..'z')) setTransition(76, c , 30)
        setTransition(76, 't' , 77)

        setTransition(1, 'k' , 78)
        for(c in ('a'..'j')) setTransition(78, c , 30)
        for(c in ('l'..'z')) setTransition(78, c , 30)
        setTransition(78, 'r' , 79)
        for(c in ('a'..'q')) setTransition(79, c , 30)
        for(c in ('s'..'z')) setTransition(79, c , 30)
        setTransition(79, 'o' , 80)
        for(c in ('a'..'n')) setTransition(80, c , 30)
        for(c in ('p'..'z')) setTransition(80, c , 30)
        setTransition(80, 'g' , 81)

        setTransition(78, 'v' , 82)
        for(c in ('a'..'u')) setTransition(82, c , 30)
        for(c in ('w'..'z')) setTransition(82, c , 30)
        setTransition(82, 'a' , 83)

        setTransition(1, 'u' , 90)
        for(c in ('a'..'t')) setTransition(90, c , 30)
        for(c in ('v'..'z')) setTransition(90, c , 30)

        setTransition(90, 'l' , 91)
        for(c in ('a'..'k')) setTransition(91, c , 30)
        for(c in ('m'..'z')) setTransition(91, c , 30)
        setTransition(91, 'i' , 92)
        for(c in ('a'..'h')) setTransition(92, c , 30)
        for(c in ('j'..'z')) setTransition(92, c , 30)
        setTransition(92, 'c' , 93)
        for(c in ('a'..'b')) setTransition(93, c , 30)
        for(c in ('d'..'z')) setTransition(93, c , 30)
        setTransition(93, 'a' , 94)


        //ID
        // for (c in ('a'..'z')) setTransition(1, c, 13) - k,c,f,t,v,b,o,p,s,m,r,g remove them
        val alfabeth = ('a'..'z').toMutableList()
        val charactersToRemove = listOf('k', 'c', 'f', 't', 'v', 'b', 'o', 'p', 's', 'm', 'r', 'g' ,'u')
        alfabeth.removeAll(charactersToRemove)
        for (c in alfabeth) setTransition(1, c, 30)

        for(c in ('A'..'Z')) setTransition(1, c, 30)
        for(c in ('a'..'z')) setTransition(30, c, 30)

        for(c in ('A'..'Z')) setTransition(30, c, 30)


        //NUMBER
        for(c in ('0'..'9')) setTransition(1, c, 88)
        for(c in ('0' .. '9')) setTransition(88 , c, 88)
        setTransition(88, '.', 84)
        for(c in ('0'..'9')) setTransition(84, c, 84)



        // STRING
        setTransition(1, '\"', 85)
        setTransition(85, ' ', 86)
        setTransition(86, ' ', 86)
        for(c in ('A'..'Z')) setTransition(85, c, 86)
        for(c in ('a'..'z')) setTransition(85, c, 86)
        for(c in ('0' .. '9')) setTransition(86, c, 86)
        for(c in ('A'..'Z')) setTransition(86, c, 86)
        for(c in ('a'..'z')) setTransition(86, c, 86)
        setTransition(86, '\"', 87)








        setTransition(1, ' ', 16)
        setTransition(1, '\n'.code, 16)
        setTransition(1, '\t'.code, 16)
        setTransition(1, '\r'.code, 16)


        setTransition(1, EOF, 15)
        setSymbol(2, Symbol.LT)
        setSymbol(3, Symbol.RT)
        setSymbol(4, Symbol.MINUS)
        setSymbol(5, Symbol.PLUS)
        setSymbol(6, Symbol.TIMES)
        setSymbol(7, Symbol.DIV)
        setSymbol(8, Symbol.LBRACKET)
        setSymbol(9, Symbol.RBRACKET)
        setSymbol(10, Symbol.LBRACE)
        setSymbol(11, Symbol.RBRACE)
        setSymbol(12, Symbol.LPAREN)
        setSymbol(13, Symbol.RPAREN)
        setSymbol(14, Symbol.COMMA)
        setSymbol(15, Symbol.EOF)
        setSymbol(16, Symbol.SKIP)
        setSymbol(17, Symbol.ASSIGN)

        setSymbol(18, Symbol.EQ)

        setSymbol(20, Symbol.AND)

        setSymbol(22, Symbol.OR)
        setSymbol(23 , Symbol.ID)
        setSymbol(24 , Symbol.ID)
        setSymbol(25, Symbol.MAP)
        setSymbol(26 , Symbol.ID)
        setSymbol(27 , Symbol.ID)
        setSymbol(28 , Symbol.ID)
        setSymbol(29, Symbol.MESTO)
        setSymbol(30 , Symbol.ID)//dejanski
        setSymbol(31 , Symbol.ID)
        setSymbol(32 , Symbol.ID)
        setSymbol(33 , Symbol.ID)
        setSymbol(34, Symbol.GOZD)
        setSymbol(35 , Symbol.ID)
        setSymbol(36 , Symbol.ID)
        setSymbol(37 , Symbol.ID)
        setSymbol(38, Symbol.REKA)
        setSymbol(39 , Symbol.ID)
        setSymbol(40 , Symbol.ID)
        setSymbol(41 , Symbol.ID)
        setSymbol(42 , Symbol.ID)
        setSymbol(43 , Symbol.ID)
        setSymbol(44, Symbol.STAVBA)
        setSymbol(45 , Symbol.ID)
        setSymbol(46 , Symbol.ID)
        setSymbol(47 , Symbol.ID)
        setSymbol(48, Symbol.PLAC)
        setSymbol(49 , Symbol.ID)
        setSymbol(50 , Symbol.ID)
        setSymbol(51 , Symbol.ID)
        setSymbol(52 , Symbol.ID)
        setSymbol(53, Symbol.PONOVI)
        setSymbol(54 , Symbol.ID)
        setSymbol(55 , Symbol.ID)
        setSymbol(56 , Symbol.ID)
        setSymbol(57 , Symbol.ID)
        setSymbol(58 , Symbol.ID)
        setSymbol(59 , Symbol.ID)
        setSymbol(60 , Symbol.BUSSTOP)
        setSymbol(61 , Symbol.BUY)
        setSymbol(62 , Symbol.ID)
        setSymbol(63 , Symbol.ID)
        setSymbol(64, Symbol.VAR)
        setSymbol(65 , Symbol.ID)
        setSymbol(66 , Symbol.ID)
        setSymbol(67 , Symbol.ID)
        setSymbol(68 , Symbol.TRUE)
        setSymbol(69, Symbol.TRI)
        setSymbol(70 , Symbol.ID)
        setSymbol(71 , Symbol.ID)
        setSymbol(72 , Symbol.ID)
        setSymbol(73 , Symbol.ID)
        setSymbol(74 , Symbol.FALSE)
        setSymbol(75 , Symbol.ID)
        setSymbol(76 , Symbol.ID)
        setSymbol(77 , Symbol.CRT)

        setSymbol(78 , Symbol.ID)
        setSymbol(79 , Symbol.ID)
        setSymbol(80 , Symbol.ID)
        setSymbol(81 , Symbol.KROG)
        setSymbol(82 , Symbol.ID)
        setSymbol(83 , Symbol.KVA)

        setSymbol(84 , Symbol.NUMBER)
        setSymbol(88, Symbol.NUMBER)

        setSymbol(87, Symbol.STRING)
        setSymbol(16, Symbol.SKIP)

        setSymbol(89, Symbol.COLON)
        setSymbol(94, Symbol.UL)
        setSymbol(95, Symbol.NOT)
        setSymbol(96, Symbol.ID)
        setSymbol(97, Symbol.ID)
        setSymbol(98, Symbol.ID)
        setSymbol(99, Symbol.ID)
        setSymbol(100, Symbol.POINTER)










    }
}

data class Token(val symbol: Symbol, val lexeme: String, val startRow: Int, val startColumn: Int)

class Scanner(private val automaton: DFA, private val stream: InputStream) {
    private var last: Int? = null
    private var row = 1
    private var column = 1

    private fun updatePosition(code: Int) {
        if (code == NEWLINE) {
            row += 1
            column = 1
        } else {
            column += 1
        }
    }

    fun getToken(): Token {

        val startRow = row
        val startColumn = column
        val buffer = mutableListOf<Char>()

        var code = last ?: stream.read()
        var state = automaton.startState

        while (true) {
            val nextState = automaton.next(state, code)
            if (nextState == ERROR_STATE) break // Longest match

            state = nextState
            updatePosition(code)
            buffer.add(code.toChar())
            code = stream.read()


        }
        last = code // The code following the current lexeme is the first code of the next lexeme

        if (automaton.finalStates.contains(state)) {
            val symbol = automaton.symbol(state)

            return if (symbol == Symbol.SKIP) {

                getToken()
            } else {

                val lexeme = String(buffer.toCharArray())
                Token(symbol, lexeme, startRow, startColumn)
            }
        } else {
            throw Error("Invalid pattern at ${row}:${column} $state $code")
        }
    }
}

fun name(symbol: Symbol) =
    when (symbol) {
        Symbol.MAP -> "map"
        Symbol.MESTO -> "mesto"
        Symbol.GOZD -> "gozd"
        Symbol.REKA -> "reka"
        Symbol.STAVBA -> "stavba"
        Symbol.PLAC -> "plac"
        Symbol.BUSSTOP -> "busstop"
        Symbol.BUY -> "buy"
        Symbol.UL -> "ul"
        Symbol.VAR -> "var"
        Symbol.PONOVI -> "ponovi"
        Symbol.TRUE -> "true"
        Symbol.FALSE -> "false"
        Symbol.KROG -> "krog"
        Symbol.KVA -> "kva"
        Symbol.CRT -> "crt"
        Symbol.EQ -> "eq"
        Symbol.AND -> "and"
        Symbol.OR -> "or"
        Symbol.LT -> "lt"
        Symbol.RT -> "rt"
        Symbol.RBRACE -> "rbrace"
        Symbol.LBRACE -> "lbrace"
        Symbol.RBRACKET -> "rbracket"
        Symbol.LBRACKET -> "lbracket"

        Symbol.PLUS -> "plus"
        Symbol.MINUS -> "minus"
        Symbol.TIMES -> "times"
        Symbol.DIV -> "divides"

        Symbol.LPAREN -> "lparen"
        Symbol.RPAREN -> "rparen"
        Symbol.SKIP -> "skip"
        Symbol.ASSIGN -> "assign"
        Symbol.COMMA -> "comma"
        Symbol.ID -> "id"
        Symbol.NUMBER -> "number"
        Symbol.STRING -> "string"
        Symbol.EOF -> "eof"
        Symbol.COLON -> "colon"
        Symbol.NOT -> "not"
        Symbol.POINTER -> "pointer"
        Symbol.TRI-> "tri"

        else -> throw Error("Invalid symbol $symbol")
    }

fun printTokens(scanner: Scanner, output: OutputStream) {
    val writer = output.writer(Charsets.UTF_8)

    var token = scanner.getToken()
    writer.appendLine(token.toString())
    while (token.symbol != Symbol.EOF && token.symbol != Symbol.SKIP) {
        writer.append("${name(token.symbol)}(\"${token.lexeme}\") ") // The output ends with a space

        token = scanner.getToken()
    }
    writer.appendLine()
    writer.flush()
}

fun main(args: Array<String>) {
      printTokens( Scanner(ForForeachFFFAutomaton,File("C:\\Users\\metod\\Desktop\\Faks\\4.semester\\ProjektniPraktikum\\Serverless\\Prevajanje programskih jezikov\\Prevajalnik\\src\\examples\\example9.txt").inputStream()), System.out)
    //printTokens(Scanner(ForForeachFFFAutomaton, File(args[0]).inputStream()), File(args[1]).outputStream())
}


