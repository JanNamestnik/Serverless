<a name="readme-top"></a>

<div align="center">
  <h1 align="center">Prevajanje programskih jezikov</h1>

  <p align="center">
    Predstavitev načrtovanja in implementacije svojega jezika za opisovanje in prikazovanje linij in oblik.
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/main">Projekt</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Prevajanje%20programskih%20jezikov/Prevajalnik">Prevajalnik</a>
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Dokumentacija"><strong>Vsa dokumentacija»</strong></a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Kazalo vsebine</summary>
  <ol>
    <li>
      <a href="#o-projektu">O projektu</a>
      <ul>
        <li><a href="#glavne-funkcionalnosti-aplikacije">Glavne funkcionalnosti aplikacije</a></li>
        <li><a href="#uporabljena-oprema">Uporabljena oprema</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#Prenos">Prenos projektne kode</a></li>
        <li><a href="#Namestitev">Namestitev potrebnih odvisnosti</a></li>
        <li><a href="#Zagon">Zagon glavnega programa</a></li>
        <li><a href="#Preverjanje">Preverjanje delovanja</a></li>
        <li><a href="#Uporaba">Uporaba pridobljenih podatkov v geojson.io</a></li>
      </ul>
    </li>
    <li>
        <a href="#opis-projekta">Opis projekta</a>
        <ul>
            <li><a href="#Analiza">Analiza in načrtovanje jezika</a></li>
            <ul>
                <li><a href="#Definicija-konstruktov">Definicija konstruktov</a></li>
                <li><a href="#Formalna-definicija-sintakse">Formalna definicija sintakse</a></li>
                <li><a href="#Testni-primeri">Testni primeri</a></li>
            </ul>
            <li><a href="#Implementacija">Implementacija jezika</a></li>
            <ul>
                <li><a href="#Main">Main.kt</a></li>
                <li><a href="#Token">Token.kt</a></li>
                <li><a href="#Parser">Parser.kt</a></li>
                <li><a href="#Classes">Classes.kt</a></li>
            </ul>
            <li><a href="#Predstavitev">Predstavitev jezika</a></li>
            <ul>
                <li><a href="#Primer-vhodnega-jezika">Primer vhodnega jezika</a></li>
                <li><a href="#Pretvorba-v-GeoJSON-format">Pretvorba v GeoJSON format</a></li>
            </ul>
            <li><a href="#First-in-Follow">First in Follow</a></li>
        </ul>
    </li>
    <li><a href="#kontakt">Kontakt</a></li>
    <li><a href="#viri">Viri</a></li>
  </ol>
</details>

<!-- O projektu -->
<h2 id="o-projektu">1. O projektu</h2>

Ta projekt je narejen v sklopu predmeta "Prevajanje programskih jezikov" in se se osredotoča na zasnovo jezika, ki omogoča opisovanje linij in oblik za prikazovanje v GeoJSON formatu.

![Slika](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Prevajanje%20programskih%20jezikov/Slike/primer.png)


<h3 id="glavne-funkcionalnosti-aplikacije">1.1 Glavne funkcionalnosti aplikacije:</h3>

- Definicija osnovnih konstrukcij jezika, kot so enote, realna števila, besedila in točke.
- Implementacija matematičnih izrazov in boolean izrazov.
- Podpora za različne oblike (krog, štirikotnik, trikotnik, črta).
- Uvajanje spremenljivk in ponovitvenih zank (loop).
- Gradniki za bolj jasen prikaz kode, kot so gozd, reka, ulica, stavba itd.
- Najvišja komponenta je zemljevid, ki vključuje vse ostale komponente.


<br />
<h2 id="uporabljena-oprema">1.2 Uporabljena oprema</h2>

* [![Kotlin][Kotlin]][Kotlin-url]
    * Programsko okolje za razvoj aplikacij.
* [![IntelliJ IDEA][IntelliJ]][IntelliJ-url]
    * Integrirano razvojno okolje (IDE) za pisanje in testiranje kode. 
* [![GeoJson][GeoJson]][GeoJson-url]
    * Format za kodiranje različnih geografskih podatkovnih struktur
* [![GitHub][GitHub]][GitHub-url]
    * Platforma za verzioniranje in sodelovanje pri razvoju programske opreme. 

<br />

<!-- GETTING STARTED -->
<h2 id="getting-started">2. Getting Started</h2>

Za začetek dela s projektom sledite spodnjim korakom:

<h3 id="Prenos">2.1 Prenos projektne kode</h3>

- Obiščite GitHub repozitorij, kjer je shranjena projektna koda.
- Prenesite repozitorij z uporabo ukaza git clone:

```
git clone https://github.com/JanNamestnik/Serverless.git
```

- Premaknite se v imenik projekta:

```
cd Serverless/Prevajanje programskih jezikov/Prevajalnik
```
<h3 id="Namestitev">2.2 Namestitev potrebnih odvisnosti</h3>

- Prepričajte se, da imate nameščeno okolje Java Development Kit (JDK) in IntelliJ IDEA.
- Odprite projekt v IntelliJ IDEA:
    - Kliknite na File -> Open in izberite preneseni imenik projekta.
- Po odprtju projekta IntelliJ IDEA običajno samodejno zazna in namesti vse potrebne odvisnosti. Če se to ne zgodi, uporabite pom.xml ali build.gradle datoteko (odvisno od vašega projekta) za ročno namestitev odvisnosti.

<h3 id="Zagon">2.3 Zagon glavnega programa</h3>

- Poiščite glavni vhodni razred (main class) v vašem projektu. Običajno se nahaja v mapi src/main/kotlin ali src/main/java.
- Zaženite glavni program:
    - Desni klik na glavni razred in izberite Run.
- Glavni program bo prebral vhodne podatke in jih pretvoril v želene oblike v GeoJSON formatu.

<h3 id="Preverjanje">2.4 Preverjanje delovanja</h3>

- Preverite, ali se podatki pravilno pretvorijo v GeoJSON format.
- Za dodatno preverjanje uporabite priložene testne primere ali dodajte svoje testne primere.

<h3 id="Uporaba">2.5 Uporaba pridobljenih podatkov v geojson.io</h3>

- Pridobljene GeoJSON podatke lahko uporabite in preizkusite na spletni strani [geojson.io](https://geojson.io/#map=2/0/20).
- Obiščite spletno stran, prilepite GeoJSON podatke in preverite njihov prikaz na zemljevidu.

S temi koraki boste lahko uspešno začeli z delom na projektu. Če naletite na težave, preverite dokumentacijo ali se obrnite na vzdrževalce repozitorija.


<!-- USAGE EXAMPLES -->
<h2 id="opis-projekta">3. Opis projekta</h2>

Projekt v sklopu predmeta "Prevajanje programskih jezikov" je namenjen razvoju specifičnega programskega jezika, ki omogoča opisovanje geometrijskih oblik in linij za vizualizacijo v formatu GeoJSON. Glavni cilj projekta je omogočiti uporabnikom, da definirajo in prikažejo različne geometrijske strukture na zemljevidu.

<h3 id="Analiza">3.1 Analiza in načrtovanje jezika</h3>

Pri analizi in načrtovanju jezika smo določili potrebne konstrukte in sintakso jezika, ki omogočajo opisovanje mesta in njegovih infrastrukturnih komponent. Postopek je vključeval naslednje korake:

<h4 id="Definicija-konstruktov">3.1.1 Definicija konstruktov</h4>

Konstrukti so sestavni deli jezika, ki sestavljajo sintakso. V nadaljevanju je prikazana njihova 
uporaba ter njihov opis.:

- `Enota`: Nevtralni element, ki ne nosi posebne vrednosti.

- `Realna števila`: To so vsa števila, ki bodo uporabljena v jeziku. Jezik bo omogočal uporabo vseh realnih 
števil, za katere ne bo nujno, da so ločene z piko.
</br> Primeri:     
    ```
    1.0234
    1
    20324
    ```

- `Besedilo`: Niz znakov, ki se začne in konča z narekovaji. 
</br> Primeri:     
    ```
    "String"
    "Besedilo napisano na papir"
    ```

- `Točka`: Osnovna enota za prikazovanje likov in linij na zemljevidu. Sestavljena je iz longitude (prvi izraz) in latitude (drugi izraz).
</br> Primeri:     
    ```
    [Izraz, Izraz]
    [2,1] 
    [3+4, 5+6] 
    ```

- `Izrazi`: Matematični izrazi, ki omogočajo operacije, kot so seštevanje, množenje, deljenje in odštevanje. </br> Primeri:     
    ```
    Plus : Izraz + Izraz
        10 + 20 →30
        A + 6
    
    Minus : Izraz – Izraz
        10 – 20 → -10
        B - 7
    
    Krat : Izraz * Izraz
        10 * 20 →200
        C * 3
    
    Deli : Izraz / Izraz
        10 / 20 →1/2 
        D / 6
    ```
Izraz lahko enačimo tudi z Imenom spremenljivke ali številko in lahko jih ciklamo.

- `Boolean` To so izrazi, katerih namen je bil izvajanje zanke. Zato je potem to primerjanje 2 števil.
Rezultat izraz je potem pozitiven, če je trditev izraz res in obratno je negativne, če je 
trditev izraza neresnična
</br> Primeri:     
    ```
    Izraz == Izraz
        1 == 2
        Izraz vrne true, če je izraz1 enak izraz2 in false, če nista enaka.
        
    Izraz < Izraz
        1 < 2
        Izraz vrne true, če je izraz1 manjši od izraz2, in false če je enak ali večji
    
    Izraz > Izraz
        1 > 2
        Izraz vrne true, če je izraz 1 večji od izraz2, in false, če je manjši ali enak
    ```

- `Ukazi`: Ukaz ima generalno 2 funkcionalnosti: Izris oblike ali vpliv na programski tok.
    - Izris oblike
        - `Krog` : Nariše krog okoli točke1, ki ima radi             izraz. : krog:{Točka 1, Izraz}
            </br>Primer: ```krog :{[4, 2], 10000}```
                
        - `Štirikotnik` : Točke med seboj poveš iz leve             proti desni in končaš tako, da zadnjo 
        poveš z prvo : kva { Točka1, Točka2,Točka3,Točka4}
            </br>Primer: ```kva: {[1, 1], [1, 2], [2, 2], [2, 1]}```
        - `Trikotnik` : Točke med seboj poveš iz leve           proti desni in končaš tako, da zadnjo 
        poveš z prvo : tri: {Točka1, Točka2, Točka 3}
            </br>Primer: ```tri : {[3, 3], [4, 4], [5, 5]}```
        - `Črta` : Povežeš točki med seboj z ukrivljenostjo, kar pomeni, da je vrednost izraz 
        med 1 in -1 : crt :{Točka 1, Točka 2 , Izraz}
            </br>Primer: ```crta : {[1, 1], [1, 2], 0.01}```
        - `Pointer` : Prikaže točko na polju : pointer:{Točka},
            </br>Primer: ```pointer : {[1,3]}```
    
    - Vpliv na programski tok
        - `Spremenljivka` : var ImeSpremenljivke = Izraz - doda vrednost neki spremenljivki 
            tako, da jo shrani v slovar
        - `SpremenjljivkaRe` : ImeSpremenljivke= Izraz - spremeni vrednost neki 
            spremenljivki v slovarju
        - `Loop` : ponovi BooleanIzraz { Ukaz1, Ukaz2? ,….}
            - Ponavlja ukaz v oklepaju, dokler je boolean izraz resničen



- `Gradniki`: Namen gradnikov je jasnost prikaza koda, kar pomeni, da je njihov glavni namen, da 
razvijalcu olajšajo delo in naredijo kodo bolj pregledno. Spodaj je za vsako opisana 
sintaksa
    - `Gozd` : gozd Besedilo: : {Ukaz 1 , Ukaz2?, ….}
    - `Reka` : reka Besedilo : {Črta1 $ Realno Število1, Črta2 $ Realno Število2 ?, …}
    - `Ulica` :  ulica Besedilo: {Ukaz 1 , Ukaz2?, ….}
    - `Objekti` :
        -  stavba : stavba Besedilo Realno število : {Ukaz 1 , Ukaz2?, ….}
        -  Prireditveni prostor : plac Besedilo: {Ukaz 1 , Ukaz2?, ….}
        -  Avtobusna Postaja: busStop Besedilo : {Točka}
        -  Nakupno Mesto : buy Besedilo : { Točka }
    - `Mesto` : mesto Besedilo {{ Objekt1, Objekt2? , …} , {Ulica1, Ulica2 ?,….} ,
{Narava1,Narava2} ? }

- `Zemljevid`: Zemljevid je najvišja komponenta , v kateri so vse ostale komponente.
</br> Primer:
    ```map Besedilo ( Mesto1,Mesto2?,… )```

<h4 id="Formalna-definicija-sintakse">3.1.2 Formalna definicija sintakse</h4>

Sintaksa jezika je formalno definirana z uporabo BNF (Backus-Naur Form) notacije. BNF diagram prikazuje strukturo jezika in povezave med različnimi konstrukti.

<strong>BNF diagram</strong>: 
```
Zemljevid ::= map Besedilo ( Mesto Mesto')
Mesto' ::= ,Mesto Mesto' | Ɛ
Mesto :: = mesto Besedilo {{Objekt Objekt'} , {Ulica Ulica'} NaravaSeznam}
NaravaSeznam ::= ,{ Narava Narava'} | Ɛ
Narava' ::= ,Narava Narava' | Ɛ
Narava ::= Gozd | Reka
Gozd ::= gozd Besedilo : { Ukaz Ukaz' }
Reka::= reka Besedilo : { Ukaz Ukaz' }
Objekt' ::= ,Objekt Objekt' | Ɛ
Objekt ::= Stavba | PrireditveniProstor | AvtobusnaPostaja | NakupnoMesto 
Stavba :: = stavba Besedilo : { Ukaz Ukaz' }
PrireditveniProstor ::= plac Besedilo : { Ukaz Ukaz' }
AvtobusnaPostaja ::= busStop Besedilo : { Ukaz Ukaz' }
NakupnoMesto ::= buy Besedilo : { Ukaz Ukaz' }
Ulica' ::= , Ulica Ulica' | Ɛ
Ulica :: = ul Besedilo : { Ukaz Ukaz' }
Ukaz' ::= , Ukaz Ukaz' | Ɛ
Ukaz ::= Krog | Štirikotnik | Trikotnik | Črta | Spremenjlivka | Loop | SpremenjlivkaRe | Pointer
ImeSpremenljike
Spremenjlivka ::= var ImeSpremenljike = Izraz|BooleanIzraz
SpremenjlivkaRe ::= ImeSpremenljike = Izraz|BooleanIzraz
Črta ::= crt : {Točka , Točka, Izraz , Izraz } 
Pointer ::= point : {Točka}
Trikotnik ::= tri : {Točka, Točka, Točka} 
Štirikotnik ::= kva :{Točka ,Točka ,Točka, Točka}
Krog ::= krog:{Točka , Točka}
Loop :: = ponovi (BooleanIzraz) :{Ukaz Ukaz'}
Izraz ::= Izraz +|-|*|/ Izraz | RealnoŠtevilo | ImeSpremenljivke
BooleanIzraz :: = Izraz ==|<| >|! Izraz 
Točka:: =[Izraz, Izraz ]
Besedilo:: = ».+«
RealnoŠtevilo ::= [+- ] ?\d(\.\d*)?
ImeSpremenljike = .+
```

<h4 id="Testni-primeri">3.1.3 Testni primeri</h4>

Pripravili smo 10 smiselnih testnih primerov, ki pokrivajo različne funkcionalnosti jezika:

- `Primer 1`: Osnovni primer brez dodatnih elementov ali dodajanja narave
    ```kotlin
    map "Map1" :
    (
        mesto "City Center":
        {
            {
                stavba "Library" :
                {
                    krog :{[1, 2], 2000}
                }
            },
            {
                ulica "Main Street" : 
                {
                    crt : {[1, 1], [2, 2], 1}
                }
            }
        }
    )
    ```

- `Primer 2`: Uporaba narave
    ```kotlin
    map "Map2":
    (
        mesto "Village Center" :
        {
            {
                busstop "Central Bus Stop" :
                {
                    tri : {[1, 1], [2, 2], [3, 3]}
                }
            },
            {
                ulica "North Street" : 
                {
                    krog :{[1, 1], 1000}
                }
            },
            {
                gozd "Central Forest" : 
                {
                    krog :{[5, 5], 1000}
                }
            }
        }
    )
    ```

- `Primer 3`: Uporaba definicije spremenljivke
    ```kotlin
    map "Map10" :
    (
        mesto "Ancient Town":
        {
            {
                busstop "Old Bus Stop" : 
                {
                    crt : {[0, 1], [1, 2], 1}
                }
            },
            {
                ulica "Heritage Road" : 
                {
                    var a = 1000000, 
                    krog :{[26.81527317438463, 64.71454194486398], a}
                }
            },
            {
                reka "Old River" : 
                {
                    kva : {[1, 2], [2, 2], [3, 4], [4, 4]}
                }
            }
        }
    )
    ```

- `Primer 4`: Uporaba ponovne definiranja spremenljivke
    ```kotlin
    map "Map12" :
    (
        mesto "Science Campus" :
        {
            {
                plac "Lab" :
                {
                    var a = 3, 
                    a = a * 2, 
                    crt : {[0, a], [1, a], a}
                }
            },
            {
                ulica "Research Ave" : 
                {
                    krog :{[2, 2], 1000}
                }
            },
            {
                gozd "Research Forest" : 
                {
                    krog :{[4, 4], 1000}
                }
            }
        }
    )
    ```

- `Primer 5`: Uporaba ponovi kot zanke
    ```kotlin
    map "Map13" :
    (
        mesto "Historic Park":
        {
            {
                buy "Gift Shop" : 
                {
                    tri : {[3, 3], [4, 4], [5, 5]}, 
                    var items = 20, 
                    ponovi (items > 0) :
                    {
                        crt : {[1, items], [2, items], 1}, 
                        items = items - 1
                    }
                }
            },
            {
                ulica "Heritage Lane" : 
                {
                    krog :{[2, 2], 10000}
                }
            },
            {
                gozd "Historic Forest" : 
                {
                    krog :{[4, 4], 10000}
                }
            }
        }
    )
    ```

- `Primer 6`: Uporaba ponovi kot if stavka
    ```kotlin
    map "Map14" :
    (
        mesto "Eco Village" :
        {
            {
                stavba "Community Center" : 
                {
                    kva : {[1, 1], [1, 2], [2, 2], [2, 1]}, 
                    var visitors = 100, 
                    ponovi (visitors == 100) :
                    {
                        crt : {[0, visitors], [1, visitors], 1}, 
                        visitors = visitors - 1
                    }
                }
            },
            {
                ulica "Eco Street" : 
                {
                    krog :{[1, 1], 10000}
                }
            },
            {
                gozd "Eco Forest" : 
                {
                    krog :{[2, 2], 10000}
                }
            }
        }
    )
    ```

- `Primer 7`: Generalni primer
    ```kotlin
    map "Map15":
    (
        mesto "Festival Grounds" :
        {
            {
                plac "Main Stage" : 
                {
                    crt : {[0, 0], [1, 1], 1}, 
                    var seats = 75, 
                    ponovi (seats > 0) :
                    {
                        tri : {[2, seats], [3, seats +1], [4, seats]}, 
                        seats = seats - 1
                    }
                }
            },
            {
                ulica "Festival Street" : 
                {
                    krog :{[1, 1], 10000}
                }
            },
            {
                gozd "Festival Forest" : 
                {
                    krog :{[2, 2], 10000}
                }
            }
        }
    )
    ```

- `Primer 8`: Generalni primer
    ```kotlin
    map "Map8" :
    (
        mesto "Town Center":
        {
            {
                plac "Community Center" : 
                {
                    crt : {[1, 1], [2, 2], 1}
                }
            },
            {
                ulica "Market Street" : 
                {
                    krog :{[1, 1], 10000}
                }
            },
            {
                reka "Town River" : 
                {
                    tri : {[3, 3], [3, 4], [5, 5]}
                }
            }
        }
    )
    ```

- `Primer 9`: Generalni primer
    ```kotlin
    map "Map9" :
    (
        mesto "Residential Area" :
        {
            {
                stavba "Apartment" :
                {
                    crt : {[1, 1], [1, 2], 0.01}, 
                    pointer : {[1, 3]}
                }
            },
            {
                ulica "Elm Street" :
                {
                    tri : {[2, 2], [3, 3], [4, 4]}
                }
            },
            {
                gozd "Community Forest" :
                {
                    krog :{[4, 2], 10000}
                }
            }
        }
    )
    ```

- `Primer 10`: Generalni primer
    ```kotlin
    map "Map11" :
    (
        mesto "Tech Park" :
        {
            {
                stavba "Office" : 
                {
                    var x = 5, 
                    var y = 10, 
                    ponovi ( x > 0 ) :
                    {
                        crt : {[1, x], [y, 2], 0}, 
                        x = x - 1, 
                        y = y + 1
                    }
                }
            },
            {
                ulica "Innovation Blvd" : 
                {
                    krog :{[1, 1], 100000}
                }
            },
            {
                gozd "Tech Forest" : 
                {
                    krog :{[3, 3], 1000}
                }
            }
        }
    )
    ```

<h3 id="Implementacija">3.2 Implementacija jezika</h3>

Implementacija jezika je sestavljena iz štirih glavnih komponent: glavne datoteke (`Main.kt`), leksikalnega analizatorja (`Token.kt`), sintaktičnega analizatorja (`Parser.kt`) in definicije razredov (`Classes.kt`). Tukaj je podroben opis vsake od teh komponent.

<h4 id="Main">3.2.1 Main.kt</h4>

Glavna datoteka `Main.kt` je vstopna točka programa. V tej datoteki ustvarimo objekt `Scanner`, ki prebere vhodno datoteko in objekt `Parser`, ki analizira vhodni tok. Končni rezultat je pretvorba vhodnih podatkov v GeoJSON format.

```kotlin
import java.io.File

fun main() {
    val Scanner = Scanner(ForForeachFFFAutomaton, File("C:\\Users\\metod\\Desktop\\Faks\\4.semester\\ProjektniPraktikum\\Serverless\\Prevajanje programskih jezikov\\Prevajalnik\\src\\examples\\example10.txt").inputStream())
    val parser = Parser(Scanner)
    val zemlejvid = parser.parse()
    var slovar = mutableMapOf<String, Double>()
    print(zemlejvid.toGeoJson(slovar))
}

```

<h4 id="Token">3.2.2 Token.kt</h4>

Datoteka `Token.kt` vsebuje definicije simbolov, konstant in implementacijo determinističnega končnega avtomata (DFA) za leksikalno analizo. Simboli predstavljajo različne elemente jezika, kot so ključe besede, operatorji in ločila. DFA prepoznava te simbole v vhodnem toku znakov.

```kotlin
enum class Symbol {
    MAP, MESTO, GOZD, REKA, STAVBA, PLAC, BUSSTOP, BUY, UL, VAR, PONOVI, TRUE, FALSE, KROG, KVA, TRI, CRT, EQ, AND, OR, LT, RT, ASSIGN, PLUS, MINUS, TIMES, DIV, LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET, COMMA, COLON, ID, NUMBER, STRING, EOF, NOT, POINTER, SKIP
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
        ...
        ...
        ...

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
```
Tukaj je prikazan samo del kode, tako da če si želite ogledati celotno kodo kliknite na naslednjo povezavo --> [Celotna koda](https://github.com/JanNamestnik/Serverless/blob/devel/Prevajanje%20programskih%20jezikov/Prevajalnik/src/Token.kt)


<h4 id="Parser">3.2.3 Parser.kt</h4>

Datoteka `Parser.kt` vsebuje implementacijo sintaktičnega analizatorja, ki analizira vhodni tok tokenov, generiranih s strani leksikalnega analizatorja, in gradi abstraktno sintaktično drevo (AST). Ta datoteka vsebuje metode za analizo različnih konstrukcij jezika.

```kotlin
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

    private fun parseSeznamNarava(): List<Narava> {
        // Implementacija funkcije za analizo seznama narave
    }

    private fun parseObjekt(): Objekt {
        // Implementacija funkcije za analizo objekta
    }

    private fun parseUlica(): Ulica {
        // Implementacija funkcije za analizo ulice
    }

    private fun parseNarava(): Narava {
        // Implementacija funkcije za analizo narave
    }

    private fun parseSeznamUkaz(): List<Ukaz> {
        // Implementacija funkcije za analizo seznama ukazov
    }

    private fun parseUkaz(): Ukaz {
        // Implementacija funkcije za analizo ukaza
    }

    private fun parseIzraz(): Izraz {
        // Implementacija funkcije za analizo izraza
    }

    private fun parseBooleanIzraz(): BooleanIzraz {
        // Implementacija funkcije za analizo boolean izraza
    }

    private fun parseTocka(): Tocka {
        // Implementacija funkcije za analizo točke
    }

    private fun parseImeSpremenljivke(): ImeSpremenljivke {
        // Implementacija funkcije za analizo imena spremenljivke
    }

    private fun parseBesedilo(): Besedilo {
        // Implementacija funkcije za analizo besedila
    }
}

```

Tukaj je prikazan samo del kode, tako da če si želite ogledati celotno kodo kliknite na naslednjo povezavo --> [Celotna koda](https://github.com/JanNamestnik/Serverless/blob/devel/Prevajanje%20programskih%20jezikov/Prevajalnik/src/Parser.kt)

<h4 id="Classes">3.2.4 Classes.kt</h4>

Datoteka `Classes.kt` vsebuje definicije različnih razredov, ki predstavljajo konstrukte jezika, kot so `Zemlejvid`, `Mesto`, `Objekt`, `Ulica`, `Narava` in različne `ukaze`. Vsak razred implementira metodo toGeoJson, ki pretvori objekt v GeoJSON format.

```kotlin
interface Sentence {
    fun toGeoJson(slovar: MutableMap<String, Double>): String
}

interface IzrazSentence {
    fun evaluate(slovar: MutableMap<String, Double>): Double
}

interface BooleanSentence {
    fun evaluate(slovar: MutableMap<String, Double>): Boolean
}

class Zemlejvid(private val besedilo: Besedilo, private val mesta: List<Mesto>) : Sentence {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        val placesGeoJson = mesta.joinToString(", ") { it.toGeoJson(slovar) }
        return """
        {
            "type": "FeatureCollection",
            "features": [
                $placesGeoJson
            ]
        }
        """.trimIndent()
    }
}

class Mesto(private val besedilo: Besedilo, private val objekti: List<Objekt>, private val ulice: List<Ulica>, private val narava: List<Narava>) : Sentence {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        val objektiGeoJson = objekti.joinToString(", ") { it.toGeoJson(slovar) }
        val uliceGeoJson = ulice.joinToString(", ") { it.toGeoJson(slovar) }
        val naravaGeoJson = narava.joinToString(", ") { it.toGeoJson(slovar) }
        return """
        $objektiGeoJson,
        $uliceGeoJson${if (naravaGeoJson.isNotEmpty()) "," else ""}
        $naravaGeoJson
        """.trimIndent()
    }
}

// Definicije razredov Narava, Reka, Gozd, Ulica, Objekt in Ukaz
abstract class Narava : Sentence

// Implementacija razredov Narava, Objekt in Ukaz

class Pointer(private val tocka1: Tocka) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        return """
        {
            "type": "Point",
            "coordinates": [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}]
        }
        """.trimIndent()
    }
}

class Ponovi(private val pogoj: BooleanIzraz, private val ukazi: List<Ukaz>) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        var returnString = ""
        while (pogoj.evaluate(slovar)) {
            for (ukaz in ukazi) {
                var appendString = ukaz.toGeoJson(slovar)
                if (appendString != "")
                    appendString += ","
                returnString += appendString
            }
        }
        returnString = returnString.substring(0, returnString.length - 1)
        return returnString
    }
}

abstract class Izraz : IzrazSentence

class Plus(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) + izraz2.evaluate(slovar)
    }
}

class Minus(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) - izraz2.evaluate(slovar)
    }
}

class BooleanIzraz(izraz1: Izraz, operator: Token, izraz2: Izraz) : BooleanSentence {
    private val izraz1 = izraz1
    private val izraz2 = izraz2
    private val operator = operator
    override fun evaluate(slovar: MutableMap<String, Double>): Boolean {
        return when (operator.symbol) {
            Symbol.EQ -> izraz1.evaluate(slovar) == izraz2.evaluate(slovar)
            Symbol.NOT -> izraz1.evaluate(slovar) != izraz2.evaluate(slovar)
            Symbol.RT -> izraz1.evaluate(slovar) > izraz2.evaluate(slovar)
            Symbol.LT -> izraz1.evaluate(slovar) < izraz2.evaluate(slovar)
            else -> false
        }
    }
}

class Tocka(val x: Izraz, val y: Izraz)

class Besedilo(val besedilo: String)

```

Tukaj je prikazan samo del kode, tako da če si želite ogledati celotno kodo kliknite na naslednjo povezavo --> [Celotna koda](https://github.com/JanNamestnik/Serverless/blob/devel/Prevajanje%20programskih%20jezikov/Prevajalnik/src/Classes.kt)


S temi komponentami je implementacija jezika zaključena. Glavni program prebere vhodno datoteko, analizira njeno vsebino s pomočjo leksikalnega in sintaktičnega analizatorja ter generira GeoJSON format, ki ga lahko uporabimo za vizualizacijo podatkov.

<h3 id="Predstavitev">3.3 Predstavitev jezika</h3>

V tem razdelku bomo predstavili jezik z osnovnimi elementi, kot so krog, črta, točka in trikotnik. Prikazali bomo tudi izhod našega programa v GeoJSON formatu, ki ga lahko uporabimo v geojson.io.

<h4 id="Primer-vhodnega-jezika">3.3.1 Primer vhodnega jezika</h4>

Podani primer opisuje zemljevid "Map9", ki vsebuje naslednje elemente:

- Mesto "Residential Area"
    - `Stavba` "Apartment" z definicijo črte in točke
    - `Ulica` "Elm Street" z definicijo trikotnika
    - `Gozd` "Community Forest" z definicijo kroga

```
map "Map9" :
(
    mesto "Residential Area" :
    {
        {
            stavba "Apartment" :
            {
                crt : {[1, 1], [1, 2], 0.01},
                pointer : {[1,3]}
            }
        },
        {
            ulica "Elm Street" :
            {
                tri : {[2, 2], [3, 3], [4, 4]}
            }
        },
        {
            gozd "Community Forest" :
            {
                krog : {[4, 2], 10000}
            }
        }
    }
)

```

<h4 id="Pretvorba-v-GeoJSON-format">3.3.2 Pretvorba v GeoJSON format</h4>

Po izvedbi programa nad zgornjim primerom dobimo naslednji izhod v GeoJSON formatu:

```
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": { "name": "Apartment" },
      "geometry": {
        "type": "GeometryCollection",
        "geometries": [
          {
            "type": "LineString",
            "coordinates": [
              [1.0, 1.0],
              [1.0, 2.0]
            ]
          },
          {
            "type": "Point",
            "coordinates": [1.0, 3.0]
          }
        ]
      }
    },
    {
      "type": "Feature",
      "properties": { "name": "Elm Street" },
      "geometry": {
        "type": "Polygon",
        "coordinates": [
          [
            [2.0, 2.0],
            [3.0, 3.0],
            [4.0, 4.0],
            [2.0, 2.0]
          ]
        ]
      }
    },
    {
      "type": "Feature",
      "properties": { "name": "Community Forest" },
      "geometry": {
        "type": "Polygon",
        "coordinates": [
          [
            [1.9976466943609807, 4.053365812652004],
            [1.986281162472381, 4.052808777785004],
            [1.9750251330177544, 4.051143040023404],
            [1.963987052046765, 4.048384648108683],
            [1.9532732630874754, 4.044560177856725],
            [1.942986981401253, 4.039706475781352],
            [1.9332272985725532, 4.03387030365617],
            [1.924088227075268, 4.027107887461871],
            [1.9156577940612283, 4.019484375091993],
            [1.9080171931294794, 4.0110732080726175],
            [1.9012400022625107, 4.001955413381826],
            [1.8953914754637207, 3.992218822225117],
            [1.890527914906084, 3.98195722332571],
            [1.8866961296127815, 3.9712694589171837],
            [1.8839329858446499, 3.9602584721742367],
            [1.8822650534753878, 3.9490303152804245],
            [1.8817083517024704, 3.937693127705276],
            [1.882268196479008, 3.9263560945438423],
            [1.8839391510687464, 3.9151283949571956],
            [1.8867050801325824, 3.9041181508409575],
            [1.8905393067598757, 3.893431385840186],
            [1.8954048708708353, 3.88317100472317],
            [1.9012548864467085, 3.873435802925062],
            [1.9080329941012417, 3.8643195157770394],
            [1.9156739045987932, 3.8559099165507296],
            [1.9241040280597115, 3.848287971974861],
            [1.933242182780183, 3.841527063326012],
            [1.9430003768389843, 3.8356922805634484],
            [1.9532846549744054, 3.830839796275203],
            [1.9639960025971819, 3.827016325435737],
            [1.975031298265283, 3.824258676151741],
            [1.9862843054886832, 3.8225933956999887],
            [1.9976466943609807, 3.822036515247646],
            [2.009009083233278, 3.8225933956999887],
            [2.0202620904566784, 3.824258676151741],
            [2.031297386124779, 3.827016325435737],
            [2.042008733747556, 3.830839796275203],
            [2.052293011882977, 3.8356922805634484],
            [2.062051205941778, 3.841527063326012],
            [2.0711893606622493, 3.848287971974861],
            [2.079619484123168, 3.8559099165507296],
            [2.0872603946207198, 3.8643195157770394],
            [2.0940385022752523, 3.873435802925062],
            [2.0998885178511255, 3.88317100472317],
            [2.1047540819620854, 3.893431385840186],
            [2.108588308589379, 3.9041181508409575],
            [2.111354237653215, 3.9151283949571956],
            [2.1130251922429535, 3.9263560945438423],
            [2.1135850370194906, 3.937693127705276],
            [2.1130283352465735, 3.9490303152804245],
            [2.1113604028773114, 3.9602584721742367],
            [2.10859725910918, 3.9712694589171837],
            [2.104765473815877, 3.98195722332571],
            [2.0999019132582406, 3.992218822225117],
            [2.094053386459451, 4.001955413381826],
            [2.087276195592482, 4.0110732080726175],
            [2.0796355946607332, 4.019484375091993],
            [2.0712051616466933, 4.027107887461871],
            [2.062066090149408, 4.03387030365617],
            [2.052306407320708, 4.039706475781352],
            [2.0420201256344854, 4.044560177856725],
            [2.0313063366751964, 4.048384648108683],
            [2.0202682557042073, 4.051143040023404],
            [2.0090122262495798, 4.052808777785004],
            [1.9976466943609807, 4.053365812652004]
          ]
        ]
      }
    }
  ]
}

```

Ta GeoJSON format pa lahko  za vizualizacijo rezultata nato uporabimo na spletni strani [geojson.io](https://geojson.io/)

![Slika](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Prevajanje%20programskih%20jezikov/Slike/map9.png)

<h3 id="First-in-Follow">3.4 First in Follow</h3>

Jezik smo predstavili z opisi posameznih konstruktov in sintakse, poleg tega pa smo ga prikazali tudi v obliki FIRST in FOLLOW množice za različne produkcije v jeziku.

Primer FIRST in FOLLOW množic:

```
FIRST(AvtobusnaPostaja) = {busStop}
FOLLOW(AvtobusnaPostaja) = {,, }}

FIRST(Besedilo) = {»}
FOLLOW(Besedilo) = {,, {, :, )}

FIRST(BooleanIzraz) = {RealnoŠtevilo, ImeSpremenljike, +, -, *, /}
FOLLOW(BooleanIzraz) = {)}

FIRST(Gozd) = {gozd}
FOLLOW(Gozd) = {,, }}

FIRST(ImeSpremenljike) = {.+}
FOLLOW(ImeSpremenljike) = {=, ,, ]}

FIRST(Izraz) = {RealnoŠtevilo, ImeSpremenljike, +, -, *, /}
FOLLOW(Izraz) = {==, <, >, !, ,, }}

FIRST(Krog) = {krog}
FOLLOW(Krog) = {,, }}

FIRST(Loop) = {ponovi}
FOLLOW(Loop) = {,, }}

FIRST(Mesto) = {mesto}
FOLLOW(Mesto) = {,, )}

FIRST(Mesto') = {,, ε}
FOLLOW(Mesto') = {)}

FIRST(NakupnoMesto) = {buy}
FOLLOW(NakupnoMesto) = {,, }}

FIRST(Narava) = {Gozd, Reka}
FOLLOW(Narava) = {,, }}

FIRST(Narava') = {,, ε}
FOLLOW(Narava') = {}}

FIRST(NaravaSeznam) = {,, ε}
FOLLOW(NaravaSeznam) = {}}

FIRST(Objekt) = {stavba, plac, busStop, buy}
FOLLOW(Objekt) = {,, }}

FIRST(Objekt') = {,, ε}
FOLLOW(Objekt') = {}}

FIRST(Pointer) = {point}
FOLLOW(Pointer) = {,, }}

FIRST(PrireditveniProstor) = {plac}
FOLLOW(PrireditveniProstor) = {,, }}

FIRST(RealnoŠtevilo) = {[+-]?\d(\.\d*)?}
FOLLOW(RealnoŠtevilo) = {+, -, *, /, ==, <, >, !, ,, ]}

FIRST(Reka) = {reka}
FOLLOW(Reka) = {,, }}

FIRST(Spremenjlivka) = {var}
FOLLOW(Spremenjlivka) = {,, }}

FIRST(SpremenjlivkaRe) = {ImeSpremenljike}
FOLLOW(SpremenjlivkaRe) = {,, }}

FIRST(Stavba) = {stavba}
FOLLOW(Stavba) = {,, }}

FIRST(Točka) = {[}
FOLLOW(Točka) = {,, }}

FIRST(Trikotnik) = {tri}
FOLLOW(Trikotnik) = {,, }}

FIRST(Ukaz) = {krog, kva, tri, crt, var, ponovi, point, ImeSpremenljike}
FOLLOW(Ukaz) = {,, }}

FIRST(Ukaz') = {,, ε}
FOLLOW(Ukaz') = {}}

FIRST(Ulica) = {ul}
FOLLOW(Ulica) = {,, }}

FIRST(Ulica') = {,, ε}
FOLLOW(Ulica') = {}}

FIRST(Zemljevid) = {map}
FOLLOW(Zemljevid) = {$}

FIRST(Črta) = {crt}
FOLLOW(Črta) = {,, }}

FIRST(Štirikotnik) = {kva}
FOLLOW(Štirikotnik) = {,, }}
```

Množice FIRST in FOLLOW pomagajo pri razumevanju in implementaciji sintaktičnega analizatorja, saj določajo, kateri simboli se lahko pojavijo na določenem mestu v programu.

<!-- CONTACT -->
<h2 id="kontakt">4. Kontakt</h2>

Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
<h2 id="viri">5. Viri</h2>

* [geojson.io](https://geojson.io/#map=2/0/20)
* [GeoJSON Wikipedia](https://en.m.wikipedia.org/wiki/GeoJSON)
* [Izris krožnice](https://ppj.lpm.feri.um.si/tasks/circle.html)
* [Bezier](https://gist.github.com/brokenpylons/a055457075a5b34866e4ad1a5a56c0df)
* [Funkcije](https://gist.github.com/brokenpylons/3d4dacd1521b99e8d5a3070a5f5911a5)
* [Tipi](https://gist.github.com/brokenpylons/2cd318d101223abf97b17544335c2ab6)


<p align="right">(<a href="#readme-top">nazaj na vrh</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Kotlin]: https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white
[Kotlin-url]: https://kotlinlang.org/
[IntelliJ]: https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white
[IntelliJ-url]: https://www.jetbrains.com/idea/
[GitHub]: https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white
[GitHub-url]: https://github.com/
[GeoJson]: https://img.shields.io/badge/GeoJson-005571?style=for-the-badge&logo=geojson&logoColor=white
[GeoJson-url]: https://geojson.org/
