import Symbol.*
import java.io.InputStream
import java.io.OutputStream

class Parser (private val scanner: Scanner) {

private var currentToken: Token = scanner.getToken()


    fun parse(): Zemlejvid {
        val slovar = mutableMapOf<String,Double>()
        return parseZemljevid()
    }
    fun parseZemljevid (): Zemlejvid {
        if(currentToken.symbol == Symbol.MAP) {
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.STRING){
                val ime = parseBesedilo()
                if(currentToken.symbol == Symbol.COLON){
                    currentToken = scanner.getToken()
                }else{ throw Error("Invalid pattern at (:) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
                }
                val lokacije = parseSeznamMesto()
                return Zemlejvid(ime,lokacije);

            }else{ throw Error("Invalid pattern at (string) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
            }
        }
        else {
            throw Error("Invalid pattern at ( map)  ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
        }

    }
    fun parseSeznamMesto (): List<Mesto> {
        val mesta = mutableListOf<Mesto>()
        if(currentToken.symbol == Symbol.LPAREN){
            currentToken = scanner.getToken()
        }else {
            throw Error("Invalid pattern at (() ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
        }
            while(currentToken.symbol == Symbol.MESTO){
            mesta.add(parseMesto())
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
            }else { break;}
        }
            if(currentToken.symbol == Symbol.RPAREN){
                currentToken = scanner.getToken()
            }else{ throw Error("Invalid pattern at ()) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
            }
        return mesta
    }

    fun parseMesto(): Mesto {
        if(currentToken.symbol == Symbol.MESTO){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.STRING){
                val ime = parseBesedilo()
                if(currentToken.symbol == Symbol.COLON){ currentToken = scanner.getToken()}
                else {throw Error("Invalid pattern at (:)  ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")}

                if(currentToken.symbol == Symbol.LBRACE){
                    currentToken = scanner.getToken()
                    val objekti = parseSeznamObjekt()
                    var narava = emptyList<Narava>()
                    if(currentToken.symbol == Symbol.COMMA){
                        currentToken = scanner.getToken()
                    }
                    else{
                        throw Error ("Invalid pattern at  (,) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
                    }

                    val ulice = parseSeznamUlica()

                    if(currentToken.symbol != Symbol.COMMA){

                        if(currentToken.symbol != RBRACE){
                            throw Error("Invalid pattern at  (,) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken}")
                        }
                    }else{
                        currentToken = scanner.getToken()
                        narava= parseSeznamNarava()
                    }
                    if(currentToken.symbol == Symbol.RBRACE){
                        currentToken = scanner.getToken()
                        return Mesto(ime,objekti,ulice,narava)
                    }
                    else{
                        throw Error("Invalid pattern at  ({)${currentToken.startRow}:${currentToken.startColumn} : ${currentToken} ")

                    }
                }
                else{
                    throw Error("Invalid pattern at  (})${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")

                }
            }
            else{
                throw Error("Invalid pattern at  (string)${currentToken.startRow}:${currentToken.startColumn}")

            }
        }
        throw Error("Invalid pattern at mesto ${currentToken.startRow}:${currentToken.startColumn}")
    }
    fun parseSeznamObjekt(): List<Objekt> {
        val objekti = mutableListOf<Objekt>()
        if(currentToken.symbol == Symbol.LBRACE){
            currentToken = scanner.getToken()
        }else {
            throw Error("Invalid pattern at (() ${currentToken.startRow}:${currentToken.startColumn}")
        }
        while(currentToken.symbol == Symbol.STAVBA || currentToken.symbol == Symbol.PLAC || currentToken.symbol == Symbol.BUSSTOP || currentToken.symbol == Symbol.BUY){
            objekti.add(parseObjekt())
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
            }
        }
        if(currentToken.symbol == Symbol.RBRACE){
            currentToken = scanner.getToken()
        }else{
            throw Error("Invalid pattern at Seznam ojbektov ()) ${currentToken.startRow}:${currentToken.startColumn}")
        }
        return objekti
    }
    fun parseSeznamUlica (): List<Ulica> {
        val ulice = mutableListOf<Ulica>()
        if(currentToken.symbol == Symbol.LBRACE){
            currentToken = scanner.getToken()
        }else {
            throw Error("Invalid pattern at (() ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
        }
        while(currentToken.symbol == Symbol.UL){
            ulice.add(parseUlica())
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
            }
        }
        if(currentToken.symbol == Symbol.RBRACE){
            currentToken = scanner.getToken()
        }else{
            throw Error("Invalid pattern at (}) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken}")
        }
        return ulice
    }

    fun parseSeznamNarava (): List<Narava> {
        val narava = mutableListOf<Narava>()
        if(currentToken.symbol == Symbol.LBRACE){
            currentToken = scanner.getToken()
        }else {
            throw Error("Invalid pattern at ({) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
        }
        while(currentToken.symbol == Symbol.REKA || currentToken.symbol == Symbol.GOZD){
            narava.add(parseNarava())
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
            }
        }
        if(currentToken.symbol == Symbol.RBRACE){
            currentToken = scanner.getToken()
        }else{
            throw Error("Invalid pattern at (}) ${currentToken.startRow}:${currentToken.startColumn} : ${currentToken.lexeme}")
        }

        return narava
    }

    fun parseObjekt(): Objekt {
        var symbol = Symbol.PLAC
        when (currentToken.symbol) {
            Symbol.PLAC -> symbol = Symbol.PLAC
            Symbol.BUSSTOP -> symbol = Symbol.BUSSTOP
            Symbol.BUY -> symbol = Symbol.BUY
            Symbol.STAVBA -> symbol = Symbol.STAVBA
            else -> throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
        }

            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.STRING){
                val ime = parseBesedilo()

                if(currentToken.symbol == Symbol.COLON){ currentToken = scanner.getToken()}
                else {throw Error("Invalid pattern at (:)${currentToken.startRow}:${currentToken.startColumn}")}
                    val ukazi = parseSeznamUkaz()
                    when (symbol){
                            Symbol.PLAC -> return PrireditveniProstor(ime,ukazi);
                            Symbol.BUSSTOP -> return AvtobusnaPostaja(ime,ukazi)
                            Symbol.BUY -> return NakupnoMesto(ime,ukazi)
                            else -> return Stavba(ime,ukazi)
                        }


            }
            else {throw Error("Invalid pattern at (string)${currentToken.startRow}:${currentToken.startColumn}")}

    }

    fun parseUlica(): Ulica {
        if(currentToken.symbol == Symbol.UL){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.STRING){
                val ime = parseBesedilo()

                if(currentToken.symbol == Symbol.COLON){ currentToken = scanner.getToken()}
                else {throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn} ${currentToken.lexeme}")}


                    val ukazi = parseSeznamUkaz()

                        return Ulica(ime,ukazi)


            }
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn} ${currentToken.lexeme}")
    }
    fun parseNarava(): Narava {
        if(currentToken.symbol == Symbol.REKA || currentToken.symbol == Symbol.GOZD){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.STRING){
                val ime = parseBesedilo()

                if(currentToken.symbol == Symbol.COLON){ currentToken = scanner.getToken()}
                else {throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")}
                val ukazi = parseSeznamUkaz()
                return Reka(ime,ukazi)

            }
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }

    fun parseSeznamUkaz(): List<Ukaz> {
        val ukazi = mutableListOf<Ukaz>()
        if(currentToken.symbol == Symbol.LBRACE){
            currentToken = scanner.getToken()
        }else {
            throw Error("Invalid pattern at ({) ${currentToken.startRow}:${currentToken.startColumn}")
        }
        while(currentToken.symbol == Symbol.VAR || currentToken.symbol == Symbol.ID  || currentToken.symbol == Symbol.KROG || currentToken.symbol == Symbol.KVA || currentToken.symbol == Symbol.TRI || currentToken.symbol == Symbol.CRT  || currentToken.symbol == Symbol.PONOVI || currentToken.symbol == Symbol.POINTER){
            ukazi.add(parseUkaz())
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
            }
        }
        if(currentToken.symbol == Symbol.RBRACE){
            currentToken = scanner.getToken()
        }else{
            throw Error("Invalid pattern at (}) ${currentToken.startRow}:${currentToken.startColumn}")
        }
        return ukazi
    }
    fun parseUkaz(): Ukaz {
        if( currentToken.symbol == Symbol.PONOVI ){
            currentToken = scanner.getToken()
            val pogoj = parseBooleanIzraz()
            if(currentToken.symbol == Symbol.COLON) {
                currentToken = scanner.getToken()
                val ukazi = parseSeznamUkaz()
                return Ponovi(pogoj, ukazi)
            }

        }else if(currentToken.symbol == Symbol.VAR ){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.ID){
                val ime = parseImeSpremenljivke()
                if(currentToken.symbol == Symbol.ASSIGN){
                    currentToken = scanner.getToken()
                    val izraz = parseIzraz()
                    return DefinicijaSpremenljivke(ime,izraz)
                }
            }
        }
        else if(currentToken.symbol == Symbol.ID  ){
            val ime = currentToken.lexeme
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.ASSIGN){
                currentToken = scanner.getToken()
                val izraz = parseIzraz()
                val imeSpremenljivke = ImeSpremenljivke(ime)
                return RedefinicijaSpremenljivke(imeSpremenljivke,izraz)

            }
            else {
                return ImeSpremenljivke(ime)
            }
        }
        else if(currentToken.symbol == Symbol.KROG  ){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.COLON) {
                currentToken = scanner.getToken()
            }
            else{
                throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
            }
            if(currentToken.symbol == Symbol.LBRACE){
                currentToken = scanner.getToken()
                val tocka1 = parseTocka()
                if(currentToken.symbol == Symbol.COMMA){
                    currentToken = scanner.getToken()
                    val izraz = parseIzraz()
                    if(currentToken.symbol == Symbol.RBRACE){
                        currentToken = scanner.getToken()
                        return Krog(tocka1,izraz)
                    }
                }
            }
        }
        else if(currentToken.symbol == Symbol.KVA) {
            currentToken = scanner.getToken()

            if(currentToken.symbol == Symbol.COLON) {
                currentToken = scanner.getToken()
            }
            else{
                throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
            }
            if(currentToken.symbol == Symbol.LBRACE){
                currentToken = scanner.getToken()
                val tocka1 = parseTocka()
                if(currentToken.symbol == Symbol.COMMA){
                    currentToken = scanner.getToken()
                    val tocka2 = parseTocka()
                    if(currentToken.symbol == Symbol.COMMA){
                        currentToken = scanner.getToken()
                        val tocka3 = parseTocka()
                        if(currentToken.symbol == Symbol.COMMA){
                            currentToken = scanner.getToken()
                            val tocka4 = parseTocka()
                            if(currentToken.symbol == Symbol.RBRACE){
                                currentToken = scanner.getToken()
                                return Stirikotnik(tocka1,tocka2,tocka3,tocka4)
                            }
                        }
                    }
                }
            }
        }
        else if(currentToken.symbol == Symbol.TRI  ) {

            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.COLON) {
                currentToken = scanner.getToken()
            }
            else{
                throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
            }
            if(currentToken.symbol == Symbol.LBRACE){
                currentToken = scanner.getToken()
                val tocka1 = parseTocka()
                if(currentToken.symbol == Symbol.COMMA){
                    currentToken = scanner.getToken()
                    val tocka2 = parseTocka()
                    if(currentToken.symbol == Symbol.COMMA){
                        currentToken = scanner.getToken()
                        val tocka3 = parseTocka()
                        if(currentToken.symbol == Symbol.RBRACE){
                            currentToken = scanner.getToken()
                            return Trikotnik(tocka1,tocka2,tocka3)
                        }
                    }
                }
            }
        }
        else if(currentToken.symbol == Symbol.CRT  ) {
            currentToken = scanner.getToken()
            if (currentToken.symbol == Symbol.COLON) {
                currentToken = scanner.getToken()

                if (currentToken.symbol == Symbol.LBRACE) {
                    currentToken = scanner.getToken()
                    val tocka1 = parseTocka()
                    if (currentToken.symbol == Symbol.COMMA) {
                        currentToken = scanner.getToken()
                        val tocka2 = parseTocka()
                        if (currentToken.symbol == Symbol.COMMA) {
                            currentToken = scanner.getToken()
                            val ukrivljenost = parseIzraz()

                                if (currentToken.symbol == Symbol.RBRACE) {
                                    currentToken = scanner.getToken()
                                    return Crta(tocka1, tocka2, ukrivljenost)
                                }

                        }
                    }
                }

            }
        }
        else if (currentToken.symbol == Symbol.POINTER){
            currentToken = scanner.getToken()
            if(currentToken.symbol == Symbol.COLON){
                currentToken = scanner.getToken()
            }

            if (currentToken.symbol == Symbol.LBRACE) {
                currentToken = scanner.getToken()
                val tocka1 = parseTocka()
                if(currentToken.symbol == Symbol.RBRACE)
                {
                    currentToken = scanner.getToken()
                    return Pointer(tocka1)
                }
            }
        }
        else{
            throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
        }


        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn} ${currentToken} ")
    }

    fun parseIzraz(): Izraz {
        var izraz1:Izraz = SteviloIzraz(0.0);
        if(currentToken.symbol == Symbol.ID){
            izraz1 = SpremenljikaIzraz(parseImeSpremenljivke())

        }else if(currentToken.symbol == Symbol.NUMBER){
            izraz1 = SteviloIzraz(currentToken.lexeme.toDouble())
            currentToken = scanner.getToken()
        }
        if(currentToken.symbol == Symbol.PLUS || currentToken.symbol == Symbol.MINUS || currentToken.symbol == Symbol.TIMES || currentToken.symbol == Symbol.DIV){
            var operator = currentToken
            currentToken = scanner.getToken()
            var izraz2 = parseIzraz()
            when (operator.symbol){
                Symbol.PLUS -> return Plus(izraz1,izraz2)
                Symbol.MINUS -> return Minus(izraz1,izraz2)
                Symbol.TIMES -> return Krat(izraz1,izraz2)
                Symbol.DIV -> return Deljenje(izraz1,izraz2)
                else -> return Plus(izraz1,izraz2)
            }
        }
        else{
            return izraz1
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }


    fun parseBooleanIzraz(): BooleanIzraz {
        if(currentToken.symbol == Symbol.LPAREN) {
            currentToken = scanner.getToken()
            val izraz1 = parseIzraz()
            if(currentToken.symbol == Symbol.EQ || currentToken.symbol == Symbol.NOT  || currentToken.symbol == Symbol.LT || currentToken.symbol == Symbol.RT ){
                var operator = currentToken
                currentToken = scanner.getToken()
                val izraz2 = parseIzraz()
                if(currentToken.symbol == Symbol.RPAREN){
                    currentToken = scanner.getToken()
                    return BooleanIzraz(izraz1,operator,izraz2)
                }
            }
        }
        else{
            throw Error("Invalid pattern at  ( ${currentToken.startRow}:${currentToken.startColumn}")
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }

    fun parseTocka(): Tocka {
        if(currentToken.symbol == Symbol.LBRACKET){
            currentToken = scanner.getToken()
            val x = parseIzraz()
            if(currentToken.symbol == Symbol.COMMA){
                currentToken = scanner.getToken()
                val y = parseIzraz()
                if(currentToken.symbol == Symbol.RBRACKET){
                    currentToken = scanner.getToken()
                    return Tocka(x,y)
                }
            }
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }

    fun parseImeSpremenljivke(): ImeSpremenljivke {
        if(currentToken.symbol == Symbol.ID){
            val ime = currentToken.lexeme
            currentToken = scanner.getToken()
            return ImeSpremenljivke(ime)
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }

    fun parseBesedilo(): Besedilo {
        if(currentToken.symbol == Symbol.STRING){
            val besedilo = currentToken.lexeme
            currentToken = scanner.getToken()
            return Besedilo(besedilo)
        }
        throw Error("Invalid pattern at ${currentToken.startRow}:${currentToken.startColumn}")
    }


}
