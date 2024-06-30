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
      "properties": {
        "name": "Apartment"
      },
      "geometry": {
        "type": "GeometryCollection",
        "geometries": [
          {
            "type": "LineString",
            "coordinates": [
[0.9999999999999999, 1.0], [1.0103080050390891, 1.0179844380251437], [1.0206155085176656, 1.0359504693915227], [1.0309222028079017, 1.053880356250357], [1.0412277813531363, 1.0717563979169844], [1.05153193897739, 1.0895609483943787], [1.0618343721932104, 1.1072764338365246], [1.072134779507539, 1.124885369934037], [1.0824328617252847, 1.1423703792045103], [1.0927283222502897, 1.159714208170243], [1.1030208673833826, 1.1768997444060862], [1.1133102066172067, 1.193910033440375], [1.1235960529275257, 1.2107282954920553], [1.133878123060717, 1.2273379420273478], [1.1441561378171414, 1.2437225921194957], [1.1544298223301197, 1.259866088595391], [1.1646989063402164, 1.2757525139531354], [1.174963124464569, 1.2913662060348572], [1.185222216460977, 1.3066917734393977], [1.1954759274864846, 1.3217141106597925], [1.2057240083502105, 1.3364184129307826], [1.215966215760154, 1.3507901907719266], [1.2262023125637376, 1.3648152842122379], [1.2364320679818457, 1.3784798766826198], [1.2466552578361276, 1.3917705085627554], [1.2568716647693332, 1.4046740903694745], [1.2670810784584656, 1.4171779155740334], [1.2772832958205458, 1.429269673036126], [1.2874781212107764, 1.4409374590428812], [1.297665366612919, 1.4521697889414908], [1.307844851821695, 1.462955608354572], [1.3180164046170315, 1.4732843039677903], [1.328179860929987, 1.4831457138797115], [1.3383350650001968, 1.4925301375043003], [1.3484818695246827, 1.501428345016951], [1.3586201357978915, 1.5098315863353746], [1.3687497338428247, 1.5177315996271525], [1.378870542533143, 1.525120619336218], [1.3889824497061216, 1.5319913837210148], [1.3990853522663664, 1.5383371418975476], [1.409179156280183, 1.544151660381011], [1.4192637770605216, 1.5494292291201917], [1.4293391392424284, 1.5541646670192801], [1.4394051768489204, 1.5583533269422523], [1.4494618333472504, 1.5619911001954345], [1.4595090616955015, 1.5650744204843747], [1.4695468243794836, 1.5676002673416138], [1.4795750934399037, 1.5695661690224436], [1.4895938504897968, 1.570970204866231], [1.499603086722207, 1.571811007121359], [1.5096028029081356, 1.5720877622323448], [1.5195930093847505, 1.5718002115881535], [1.5295737260339028, 1.5709486517312405], [1.5395449822509713, 1.5695339340273156], [1.5495068169040804, 1.56755746379632], [1.559459278283748, 1.5650211989055893], [1.5694024240430293, 1.5619276478266408], [1.579336321128216, 1.5582798671575286], [1.5892610457001923, 1.554081458613167], [1.599176683046517, 1.5493365654865014], [1.6090833274843572, 1.5440498685838988], [1.6189810822543553, 1.5382265816385687], [1.6288700594055736, 1.5318724462063253], [1.638750379671625, 1.5249937260484572], [1.6486221723381387, 1.5175972010069305], [1.6584855751016971, 1.5096901603776176], [1.6683407339203964, 1.5012803957877034], [1.6781878028562023, 1.492376193583877], [1.6880269439092583, 1.4829863267383592], [1.6978583268443275, 1.4731200462802734], [1.707682129009558, 1.4627870722603076], [1.717498535147751, 1.4519975842570527], [1.727307737200349, 1.440762211433831], [1.7371099341043335, 1.4290920221552739], [1.7469053315822556, 1.4169985131733054], [1.756694141925619, 1.4044935983926345], [1.7664765837718321, 1.391589597226237], [1.7762528818749799, 1.3782992225517448], [1.786023266870636, 1.3646355682800255], [1.7957879750349668, 1.350612096547647], [1.8055472480383845, 1.3362426245452934], [1.8153013326939895, 1.32154131099456], [1.8250504807010732, 1.3065226422859355], [1.834794948383944, 1.2912014182911202], [1.8445349964263418, 1.275592737863171], [1.8542708896017184, 1.2597119840382964], [1.864002896499661, 1.243574808953453], [1.8737312892487377, 1.227197118494177], [1.8834563432360494, 1.210595056687425], [1.8931783368237822, 1.1937849898544202], [1.9028975510630362, 1.1767834905388281], [1.912614269405248, 1.1596073212258], [1.9223287774114708, 1.1422734178676783], [1.9320413624598387, 1.1247988732323868], [1.9417523134514942, 1.1072009200907285], [1.9514619205152925, 1.0894969142590119], [1.961170474711571, 1.0717043175136118], [1.9708782677353047, 1.0538406803942193], [1.9805855916189261, 1.0359236249126857], [1.9902927384351377, 1.0179708271844947], [1.9999999999999998, 1.0]]
},{
    "type": "Point",
    "coordinates": 
        [1.0, 3.0]
          }
        ]
      }
    },
    {
      "type": "Feature",
      "properties": {
        "name": "Elm Street"
      },
      "geometry": {
        "type": "Polygon",
        "coordinates": [[
        	[2.0, 2.0],
        	[3.0, 3.0],
        	[4.0, 4.0],
        	[2.0, 2.0]
    	]]
	}
    },
    {
      "type": "Feature",
      "properties": {
        "name": "Community Forest"
      },
      "geometry": {
        "type": "Polygon",
        "coordinates": [[
        [2.09005088784881, 4.0], [2.0900491103213796, 4.000564424425659], [2.090043777809263, 4.001128826568807], [2.0900348905229778, 4.001693184147816], [2.090022448813379, 4.002257474882813], [2.0900064531716445, 4.002821676496568], [2.0899869042292543, 4.003385766715366], [2.0899638027579672, 4.0039497232698915], [2.0899371496697903, 4.004513523896105], [2.0899069460169413, 4.005077146336124], [2.089873192991809, 4.005640568339099], [2.089835891926905, 4.006203767662094], [2.0897950442948114, 4.006766722070964], [2.0897506517081226, 4.007329409341233], [2.0897027159193824, 4.007891807258972], [2.089651238821013, 4.008453893621673], [2.0895962224452425, 4.0090156462391295], [2.0895376689640233, 4.009577042934309], [2.0894755806889465, 4.010138061544232], [2.089409960071151, 4.0106986799208455], [2.0893408097012256, 4.011258875931894], [2.0892681323091096, 4.011818627461799], [2.0891919307639815, 4.0123779124125285], [2.089112208074148, 4.01293670870447], [2.0890289673869242, 4.013494994277304], [2.08894221198851, 4.0140527470908705], [2.08885194530386, 4.014609945126043], [2.0887581708965484, 4.0151665663855995], [2.088660892468628, 4.015722588895084], [2.0885601138604843, 4.0162779907036805], [2.088455839050685, 4.016832749885077], [2.0883480721558207, 4.017386844538329], [2.0882368174303436, 4.017940252788731], [2.0881220792664004, 4.01849295278867], [2.0880038621936565, 4.019044922718499], [2.08788217087912, 4.019596140787389], [2.0877570101269556, 4.020146585234195], [2.0876283848782955, 4.0206962343283115], [2.0874963002110443, 4.021245066370535], [2.0873607613396783, 4.021793059693915], [2.0872217736150405, 4.022340192664615], [2.0870793425241274, 4.0228864436827605], [2.0869334736898755, 4.023431791183297], [2.0867841728709364, 4.02397621363684], [2.0866314459614506, 4.024519689550522], [2.086475298990815, 4.025062197468845], [2.0863157381234445, 4.025603715974525], [2.086152769658529, 4.026144223689339], [2.085986400029785, 4.026683699274969], [2.0858166358052, 4.02722212143384], [2.0856434836867748, 4.027759468909969], [2.085466950510259, 4.028295720489798], [2.08528704324488, 4.028830855003031], [2.085103768993068, 4.029364851323475], [2.0849171349901776, 4.02989768836987], [2.084727148604199, 4.030429345106721], [2.08453381733547, 4.03095980054513], [2.084337148816377, 4.031489033743625], [2.0841371508110575, 4.0320170238089865], [2.08393383121509, 4.03254374989707], [2.0837271980551835, 4.033069191213631], [2.083517259488862, 4.033593327015146], [2.08330402380414, 4.034116136609632], [2.083087499419198, 4.034637599357461], [2.0828676948820473, 4.035157694672176], [2.082644618870195, 4.035676402021305], [2.0824182801902995, 4.0361937009271704], [2.0821886877778253, 4.036709570967696], [2.081955850696687, 4.037223991777218], [2.081719778138894, 4.037736943047281], [2.081480479424187, 4.038248404527448], [2.081237963999669, 4.038758356026098], [2.080992241439434, 4.039266777411216], [2.080743321444188, 4.039773648611197], [2.080491213840866, 4.040278949615634], [2.080235928582245, 4.0407826604761095], [2.079977475746549, 4.04128476130698], [2.079715865537054, 4.041785232286165], [2.079451108281683, 4.042284053655927], [2.0791832144325992, 4.042781205723654], [2.078912194565793, 4.043276668862631], [2.0786380593806646, 4.0437704235128225], [2.078360819699602, 4.044262450181641], [2.0780804864675524, 4.044752729444716], [2.077797070751593, 4.045241241946661], [2.0775105837404895, 4.04572796840184], [2.07722103674426, 4.046212889595124], [2.0769284411937226, 4.046695986382658], [2.07663280864005, 4.047177239692605], [2.0763341507543087, 4.047656630525909], [2.0760324793270004, 4.048134139957041], [2.075727806267597, 4.048609749134746], [2.0754201436040685, 4.049083439282789], [2.0751095034824107, 4.049555191700694], [2.0747958981661636, 4.050024987764482], [2.074479340035928, 4.05049280892741], [2.0741598415888767, 4.050958636720699], [2.0738374154382617, 4.051422452754265], [2.073512074312915, 4.051884238717445], [2.0731838310567468, 4.0523439763797215], [2.07285269862824, 4.052801647591438], [2.0725186900999346, 4.0532572342845175], [2.072181818657916, 4.053710718473179], [2.071842097601292, 4.054162082254643], [2.071499540341668, 4.054611307809839], [2.0711541604026182, 4.055058377404113], [2.0708059714191513, 4.055503273387922], [2.070454987137172, 4.055945978197535], [2.0701012214129384, 4.0563864743557225], [2.0697446882125163, 4.056824744472452], [2.0693854016112256, 4.057260771245569], [2.069023375793086, 4.057694537461484], [2.068658625050257, 4.058126025995849], [2.0682911637824737, 4.0585552198142345], [2.067921006496477, 4.058982101972805], [2.0675481678054433, 4.059406655618982], [2.067172662428406, 4.059828863992117], [2.066794505189674, 4.060248710424145], [2.0664137110182472, 4.060666178340247], [2.066030294947228, 4.061081251259506], [2.0656442721132264, 4.0614939127955525], [2.065255657755763, 4.061904146657216], [2.0648644672166667, 4.062311936649167], [2.0644707159394704, 4.062717266672554], [2.0640744194688003, 4.063120120725642], [2.063675593449763, 4.063520482904443], [2.063274253627326, 4.063918337403344], [2.0628704158456985, 4.064313668515728], [2.062464096047705, 4.0647064606346035], [2.0620553102741557, 4.065096698253208], [2.061644074663212, 4.065484365965628], [2.061230405449752, 4.065869448467407], [2.060814318964728, 4.066251930556148], [2.060395831634523, 4.0666317971321115], [2.0599749599802983, 4.067009033198817], [2.0595517206173475, 4.067383623863629], [2.0591261302544357, 4.0677555543383495], [2.0586982056931413, 4.0681248099398015], [2.058267963827194, 4.068491376090405], [2.0578354216418053, 4.068855238318757], [2.057400596213, 4.0692163822601986], [2.0569635047069426, 4.069574793657386], [2.0565241643792556, 4.069930458360852], [2.0560825925743442, 4.070283362329561], [2.0556388067247067, 4.07063349163147], [2.055192824350249, 4.070980832444073], [2.0547446630575905, 4.07132537105495], [2.0542943405393728, 4.071667093862307], [2.0538418745735574, 4.072005987375511], [2.0533872830227256, 4.072342038215628], [2.052930583833374, 4.0726752331159455], [2.0524717950352045, 4.0730055589225], [2.0520109347404127, 4.073333002594593], [2.0515480211429744, 4.07365755120531], [2.051083072517926, 4.073979191942027], [2.0506161072206424, 4.074297912106919], [2.050147143686115, 4.074613699117459], [2.04967620042822, 4.074926540506918], [2.0492032960389914, 4.075236423924852], [2.0487284491878848, 4.075543337137595], [2.0482516786210407, 4.075847268028739], [2.047773003160545, 4.076148204599612], [2.047292441703685, 4.076446134969754], [2.046810013222204, 4.076741047377385], [2.0463257367615526, 4.0770329301798665], [2.045839631440136, 4.077321771854166], [2.0453517164485606, 4.077607560997309], [2.044862011048874, 4.077890286326829], [2.0443705345738077, 4.078169936681214], [2.0438773064260105, 4.078446501020348], [2.0433823460772857, 4.078719968425943], [2.04288567306782, 4.078990328101976], [2.042387307005414, 4.079257569375107], [2.0418872675647064, 4.079521681695111], [2.0413855744863976, 4.0797826546352844], [2.040882247576471, 4.0800404778928625], [2.040377306705412, 4.080295141289424], [2.0398707718074216, 4.080546634771295], [2.03936266287963, 4.0807949484099435], [2.038852999981307, 4.081040072402372], [2.038341803233072, 4.081281997071504], [2.0378290928160974, 4.08152071286657], [2.0373148889713115, 4.081756210363476], [2.036799211998603, 4.081988480265188], [2.036282082256015, 4.082217513402084], [2.035763520158944, 4.082443300732331], [2.0352435461793346, 4.08266583334223], [2.0347221808448683, 4.082885102446576], [2.034199444738156, 4.083101099388999], [2.0336753584959246, 4.08331381564231], [2.033149942808201, 4.083523242808836], [2.0326232184174975, 4.083729372620749], [2.0320952061179907, 4.083932196940401], [2.031565926754702, 4.084131707760631], [2.0310354012226743, 4.084327897205095], [2.030503650466148, 4.084520757528571], [2.0299706954777315, 4.084710281117262], [2.0294365572975757, 4.084896460489102], [2.0289012570125413, 4.085079288294047], [2.0283648157553666, 4.08525875731437], [2.0278272547038347, 4.08543486046494], [2.0272885950799346, 4.085607590793508], [2.026748858149027, 4.085776941480974], [2.0262080652190004, 4.085942905841664], [2.0256662376394345, 4.086105477323589], [2.0251233968007543, 4.086264649508706], [2.0245795641333864, 4.08642041611317], [2.0240347611069134, 4.08657277098758], [2.0234890092292264, 4.086721708117228], [2.0229423300456744, 4.086867221622331], [2.0223947451382154, 4.087009305758265], [2.021846276124565, 4.087147954915792], [2.0212969446573394, 4.0872831636212785], [2.0207467724232053, 4.087414926536919], [2.02019578114202, 4.087543238460938], [2.019643992565976, 4.0876680943278005], [2.0190914284787413, 4.0877894892084115], [2.0185381106946, 4.087907418310308], [2.0179840610575908, 4.088021876977851], [2.017429301440645, 4.088132860692408], [2.0168738537447215, 4.088240365072533], [2.0163177398979455, 4.088344385874136], [2.015760981854738, 4.088444918990655], [2.015203601594955, 4.088541960453214], [2.014645621123013, 4.088635506430783], [2.0140870624670266, 4.088725553230326], [2.0135279476779346, 4.08881209729695], [2.0129682988286324, 4.0888951352140435], [2.012408138013097, 4.088974663703412], [2.01184748734552, 4.089050679625405], [2.011286368959428, 4.089123179979047], [2.0107248050068147, 4.0891921619021465], [2.010162817657264, 4.089257622671415], [2.0096004290970737, 4.089319559702575], [2.009037661528381, 4.089377970550457], [2.0084745371682864, 4.0894328529091], [2.0079110782479748, 4.089484204611845], [2.00734730701184, 4.089532023631413], [2.006783245716605, 4.089576308079993], [2.006218916630444, 4.089617056209307], [2.005654342032103, 4.089654266410692], [2.00508954421002, 4.089687937215151], [2.004524545461446, 4.08971806729342], [2.0039593680915653, 4.0897446554560135], [2.0033940344126115, 4.089767700653278], [2.0028285667429904, 4.089787201975427], [2.002262987406398, 4.0898031586525825], [2.0016973187309373, 4.089815570054801], [2.0011315830482395, 4.089824435692104], [2.0005658026925803, 4.089829755214489], [2.0, 4.089831528411952], [1.9994341973074194, 4.089829755214489], [1.9988684169517605, 4.089824435692104], [1.9983026812690627, 4.089815570054801], [1.997737012593602, 4.0898031586525825], [1.9971714332570094, 4.089787201975427], [1.9966059655873885, 4.089767700653278], [1.9960406319084347, 4.0897446554560135], [1.9954754545385536, 4.08971806729342], [1.9949104557899802, 4.089687937215151], [1.9943456579678973, 4.089654266410692], [1.9937810833695562, 4.089617056209307], [1.993216754283395, 4.089576308079993], [1.99265269298816, 4.089532023631413], [1.9920889217520252, 4.089484204611845], [1.9915254628317138, 4.0894328529091], [1.990962338471619, 4.089377970550457], [1.9903995709029263, 4.089319559702575], [1.9898371823427359, 4.089257622671415], [1.989275194993185, 4.0891921619021465], [1.9887136310405722, 4.089123179979047], [1.9881525126544803, 4.089050679625405], [1.9875918619869026, 4.088974663703412], [1.9870317011713678, 4.0888951352140435], [1.9864720523220654, 4.08881209729695], [1.9859129375329736, 4.088725553230326], [1.9853543788769872, 4.088635506430783], [1.9847963984050454, 4.088541960453214], [1.9842390181452618, 4.088444918990655], [1.9836822601020547, 4.088344385874136], [1.9831261462552783, 4.088240365072533], [1.9825706985593552, 4.088132860692408], [1.982015938942409, 4.088021876977851], [1.9814618893054, 4.087907418310308], [1.9809085715212587, 4.0877894892084115], [1.980356007434024, 4.0876680943278005], [1.97980421885798, 4.087543238460938], [1.9792532275767947, 4.087414926536919], [1.9787030553426606, 4.0872831636212785], [1.9781537238754352, 4.087147954915792], [1.9776052548617844, 4.087009305758265], [1.9770576699543259, 4.086867221622331], [1.9765109907707739, 4.086721708117228], [1.9759652388930866, 4.08657277098758], [1.9754204358666136, 4.08642041611317], [1.974876603199246, 4.086264649508706], [1.9743337623605657, 4.086105477323589], [1.9737919347809998, 4.085942905841664], [1.9732511418509733, 4.085776941480974], [1.9727114049200651, 4.085607590793508], [1.9721727452961653, 4.08543486046494], [1.9716351842446331, 4.08525875731437], [1.9710987429874587, 4.085079288294047], [1.9705634427024243, 4.084896460489102], [1.9700293045222685, 4.084710281117262], [1.969496349533852, 4.084520757528571], [1.9689645987773254, 4.084327897205095], [1.968434073245298, 4.084131707760631], [1.9679047938820093, 4.083932196940401], [1.9673767815825025, 4.083729372620749], [1.9668500571917986, 4.083523242808836], [1.9663246415040752, 4.08331381564231], [1.9658005552618438, 4.083101099388999], [1.9652778191551317, 4.082885102446576], [1.9647564538206654, 4.08266583334223], [1.9642364798410559, 4.082443300732331], [1.9637179177439852, 4.082217513402084], [1.963200788001397, 4.081988480265188], [1.9626851110286883, 4.081756210363476], [1.9621709071839026, 4.08152071286657], [1.9616581967669278, 4.081281997071504], [1.9611470000186928, 4.081040072402372], [1.9606373371203702, 4.0807949484099435], [1.9601292281925784, 4.080546634771295], [1.9596226932945877, 4.080295141289424], [1.9591177524235288, 4.0800404778928625], [1.9586144255136027, 4.0797826546352844], [1.9581127324352936, 4.079521681695111], [1.9576126929945858, 4.079257569375107], [1.9571143269321796, 4.078990328101976], [1.9566176539227143, 4.078719968425943], [1.9561226935739895, 4.078446501020348], [1.9556294654261925, 4.078169936681214], [1.955137988951126, 4.077890286326829], [1.9546482835514394, 4.077607560997309], [1.9541603685598639, 4.077321771854166], [1.9536742632384474, 4.0770329301798665], [1.953189986777796, 4.076741047377385], [1.952707558296315, 4.076446134969754], [1.9522269968394548, 4.076148204599612], [1.951748321378959, 4.075847268028739], [1.951271550812115, 4.075543337137595], [1.9507967039610086, 4.075236423924852], [1.9503237995717801, 4.074926540506918], [1.9498528563138853, 4.074613699117459], [1.9493838927793574, 4.074297912106919], [1.9489169274820741, 4.073979191942027], [1.9484519788570256, 4.07365755120531], [1.9479890652595873, 4.073333002594593], [1.9475282049647957, 4.0730055589225], [1.947069416166626, 4.0726752331159455], [1.9466127169772742, 4.072342038215628], [1.9461581254264426, 4.072005987375511], [1.9457056594606272, 4.071667093862307], [1.9452553369424095, 4.07132537105495], [1.9448071756497511, 4.070980832444073], [1.944361193275293, 4.07063349163147], [1.9439174074256556, 4.070283362329561], [1.9434758356207442, 4.069930458360852], [1.9430364952930577, 4.069574793657386], [1.9425994037869998, 4.0692163822601986], [1.9421645783581947, 4.068855238318757], [1.941732036172806, 4.068491376090405], [1.9413017943068585, 4.0681248099398015], [1.9408738697455643, 4.0677555543383495], [1.9404482793826525, 4.067383623863629], [1.9400250400197017, 4.067009033198817], [1.9396041683654772, 4.0666317971321115], [1.9391856810352717, 4.066251930556148], [1.9387695945502479, 4.065869448467407], [1.9383559253367881, 4.065484365965628], [1.9379446897258445, 4.065096698253208], [1.9375359039522948, 4.0647064606346035], [1.9371295841543013, 4.064313668515728], [1.9367257463726741, 4.063918337403344], [1.9363244065502372, 4.063520482904443], [1.9359255805311995, 4.063120120725642], [1.9355292840605296, 4.062717266672554], [1.9351355327833333, 4.062311936649167], [1.9347443422442372, 4.061904146657216], [1.9343557278867736, 4.0614939127955525], [1.9339697050527718, 4.061081251259506], [1.9335862889817528, 4.060666178340247], [1.9332054948103263, 4.060248710424145], [1.932827337571594, 4.059828863992117], [1.9324518321945565, 4.059406655618982], [1.932078993503523, 4.058982101972805], [1.9317088362175265, 4.0585552198142345], [1.931341374949743, 4.058126025995849], [1.930976624206914, 4.057694537461484], [1.9306145983887744, 4.057260771245569], [1.9302553117874837, 4.056824744472452], [1.9298987785870614, 4.0563864743557225], [1.929545012862828, 4.055945978197535], [1.9291940285808487, 4.055503273387922], [1.9288458395973815, 4.055058377404113], [1.9285004596583317, 4.054611307809839], [1.9281579023987079, 4.054162082254643], [1.9278181813420838, 4.053710718473179], [1.9274813099000654, 4.0532572342845175], [1.9271473013717602, 4.052801647591438], [1.926816168943253, 4.0523439763797215], [1.9264879256870853, 4.051884238717445], [1.9261625845617385, 4.051422452754265], [1.9258401584111233, 4.050958636720699], [1.925520659964072, 4.05049280892741], [1.9252041018338364, 4.050024987764482], [1.9248904965175893, 4.049555191700694], [1.9245798563959313, 4.049083439282789], [1.9242721937324032, 4.048609749134746], [1.9239675206729996, 4.048134139957041], [1.9236658492456913, 4.047656630525909], [1.92336719135995, 4.047177239692605], [1.9230715588062772, 4.046695986382658], [1.9227789632557404, 4.046212889595124], [1.9224894162595105, 4.04572796840184], [1.9222029292484073, 4.045241241946661], [1.9219195135324474, 4.044752729444716], [1.921639180300398, 4.044262450181641], [1.9213619406193354, 4.0437704235128225], [1.9210878054342069, 4.043276668862631], [1.9208167855674008, 4.042781205723654], [1.9205488917183169, 4.042284053655927], [1.920284134462946, 4.041785232286165], [1.920022524253451, 4.04128476130698], [1.9197640714177553, 4.0407826604761095], [1.9195087861591338, 4.040278949615634], [1.919256678555812, 4.039773648611197], [1.9190077585605658, 4.039266777411216], [1.918762036000331, 4.038758356026098], [1.918519520575813, 4.038248404527448], [1.918280221861106, 4.037736943047281], [1.9180441493033131, 4.037223991777218], [1.9178113122221747, 4.036709570967696], [1.9175817198097003, 4.0361937009271704], [1.9173553811298052, 4.035676402021305], [1.9171323051179527, 4.035157694672176], [1.916912500580802, 4.034637599357461], [1.9166959761958597, 4.034116136609632], [1.916482740511138, 4.033593327015146], [1.9162728019448163, 4.033069191213631], [1.91606616878491, 4.03254374989707], [1.9158628491889422, 4.0320170238089865], [1.915662851183623, 4.031489033743625], [1.91546618266453, 4.03095980054513], [1.9152728513958008, 4.030429345106721], [1.9150828650098224, 4.02989768836987], [1.9148962310069317, 4.029364851323475], [1.9147129567551202, 4.028830855003031], [1.914533049489741, 4.028295720489798], [1.9143565163132252, 4.027759468909969], [1.9141833641948, 4.02722212143384], [1.914013599970215, 4.026683699274969], [1.9138472303414709, 4.026144223689339], [1.9136842618765555, 4.025603715974525], [1.9135247010091851, 4.025062197468845], [1.9133685540385494, 4.024519689550522], [1.9132158271290636, 4.02397621363684], [1.9130665263101243, 4.023431791183297], [1.9129206574758726, 4.0228864436827605], [1.9127782263849598, 4.022340192664615], [1.9126392386603217, 4.021793059693915], [1.9125036997889557, 4.021245066370535], [1.9123716151217045, 4.0206962343283115], [1.9122429898730444, 4.020146585234195], [1.91211782912088, 4.019596140787389], [1.9119961378063435, 4.019044922718499], [1.9118779207335999, 4.01849295278867], [1.9117631825696564, 4.017940252788731], [1.9116519278441793, 4.017386844538329], [1.911544160949315, 4.016832749885077], [1.9114398861395157, 4.0162779907036805], [1.9113391075313722, 4.015722588895084], [1.9112418291034519, 4.0151665663855995], [1.91114805469614, 4.014609945126043], [1.9110577880114898, 4.0140527470908705], [1.9109710326130758, 4.013494994277304], [1.910887791925852, 4.01293670870447], [1.9108080692360183, 4.0123779124125285], [1.9107318676908902, 4.011818627461799], [1.9106591902987742, 4.011258875931894], [1.9105900399288493, 4.0106986799208455], [1.9105244193110535, 4.010138061544232], [1.9104623310359767, 4.009577042934309], [1.9104037775547575, 4.0090156462391295], [1.910348761178987, 4.008453893621673], [1.9102972840806178, 4.007891807258972], [1.9102493482918774, 4.007329409341233], [1.9102049557051888, 4.006766722070964], [1.910164108073095, 4.006203767662094], [1.910126807008191, 4.005640568339099], [1.9100930539830585, 4.005077146336124], [1.9100628503302097, 4.004513523896105], [1.9100361972420326, 4.0039497232698915], [1.9100130957707457, 4.003385766715366], [1.9099935468283555, 4.002821676496568], [1.909977551186621, 4.002257474882813], [1.9099651094770222, 4.001693184147816], [1.9099562221907371, 4.001128826568807], [1.9099508896786201, 4.000564424425659], [1.90994911215119, 4.0], [1.9099508896786201, 3.9994355755743416], [1.9099562221907371, 3.998871173431193], [1.9099651094770222, 3.9983068158521844], [1.909977551186621, 3.9977425251171868], [1.9099935468283555, 3.997178323503432], [1.9100130957707457, 3.996614233284634], [1.9100361972420326, 3.9960502767301085], [1.9100628503302097, 3.995486476103895], [1.9100930539830585, 3.9949228536638763], [1.910126807008191, 3.9943594316609015], [1.910164108073095, 3.9937962323379064], [1.9102049557051888, 3.993233277929036], [1.9102493482918774, 3.9926705906587663], [1.9102972840806178, 3.992108192741028], [1.910348761178987, 3.991546106378327], [1.9104037775547575, 3.990984353760871], [1.9104623310359767, 3.990422957065691], [1.9105244193110535, 3.9898619384557676], [1.9105900399288493, 3.989301320079155], [1.9106591902987742, 3.9887411240681065], [1.9107318676908902, 3.988181372538201], [1.9108080692360183, 3.987622087587472], [1.910887791925852, 3.98706329129553], [1.9109710326130758, 3.9865050057226963], [1.9110577880114898, 3.98594725290913], [1.91114805469614, 3.9853900548739563], [1.9112418291034519, 3.9848334336144005], [1.9113391075313722, 3.9842774111049155], [1.9114398861395157, 3.9837220092963195], [1.911544160949315, 3.9831672501149233], [1.9116519278441793, 3.982613155461671], [1.9117631825696564, 3.9820597472112698], [1.9118779207335999, 3.9815070472113296], [1.9119961378063435, 3.980955077281501], [1.91211782912088, 3.980403859212611], [1.9122429898730444, 3.9798534147658056], [1.9123716151217045, 3.9793037656716885], [1.9125036997889557, 3.978754933629465], [1.9126392386603217, 3.9782069403060847], [1.9127782263849598, 3.9776598073353853], [1.9129206574758726, 3.9771135563172395], [1.9130665263101243, 3.976568208816703], [1.9132158271290636, 3.97602378636316], [1.9133685540385494, 3.9754803104494782], [1.9135247010091851, 3.974937802531155], [1.9136842618765555, 3.9743962840254747], [1.9138472303414709, 3.9738557763106606], [1.914013599970215, 3.973316300725031], [1.9141833641948, 3.97277787856616], [1.9143565163132252, 3.972240531090031], [1.914533049489741, 3.9717042795102024], [1.9147129567551202, 3.9711691449969693], [1.914896231006932, 3.970635148676525], [1.9150828650098224, 3.9701023116301304], [1.9152728513958008, 3.9695706548932796], [1.91546618266453, 3.96904019945487], [1.915662851183623, 3.9685109662563747], [1.9158628491889422, 3.9679829761910135], [1.91606616878491, 3.9674562501029302], [1.9162728019448163, 3.966930808786369], [1.916482740511138, 3.9664066729848537], [1.9166959761958597, 3.965883863390368], [1.916912500580802, 3.965362400642539], [1.9171323051179527, 3.964842305327824], [1.9173553811298052, 3.964323597978695], [1.9175817198097003, 3.9638062990728296], [1.9178113122221747, 3.9632904290323037], [1.9180441493033131, 3.9627760082227828], [1.918280221861106, 3.962263056952719], [1.9185195205758132, 3.9617515954725513], [1.918762036000331, 3.9612416439739024], [1.919007758560566, 3.9607332225887846], [1.919256678555812, 3.9602263513888034], [1.9195087861591338, 3.9597210503843656], [1.9197640714177553, 3.9592173395238905], [1.920022524253451, 3.9587152386930198], [1.920284134462946, 3.9582147677138346], [1.9205488917183169, 3.9577159463440723], [1.9208167855674008, 3.9572187942763466], [1.9210878054342069, 3.9567233311373693], [1.9213619406193354, 3.9562295764871775], [1.921639180300398, 3.955737549818359], [1.9219195135324474, 3.955247270555284], [1.9222029292484073, 3.9547587580533388], [1.9224894162595105, 3.95427203159816], [1.9227789632557404, 3.953787110404875], [1.9230715588062772, 3.953304013617342], [1.92336719135995, 3.9528227603073955], [1.9236658492456913, 3.9523433694740913], [1.9239675206729996, 3.951865860042959], [1.9242721937324032, 3.9513902508652534], [1.9245798563959313, 3.9509165607172103], [1.9248904965175893, 3.950444808299306], [1.9252041018338364, 3.949975012235518], [1.925520659964072, 3.9495071910725903], [1.9258401584111233, 3.9490413632793016], [1.9261625845617385, 3.948577547245735], [1.9264879256870853, 3.9481157612825544], [1.926816168943253, 3.9476560236202785], [1.9271473013717602, 3.947198352408562], [1.9274813099000654, 3.946742765715482], [1.9278181813420838, 3.9462892815268207], [1.9281579023987079, 3.945837917745357], [1.9285004596583317, 3.9453886921901606], [1.9288458395973815, 3.944941622595887], [1.9291940285808487, 3.944496726612078], [1.929545012862828, 3.9440540218024656], [1.9298987785870614, 3.9436135256442775], [1.9302553117874837, 3.943175255527548], [1.9306145983887744, 3.9427392287544305], [1.930976624206914, 3.942305462538516], [1.931341374949743, 3.9418739740041513], [1.9317088362175265, 3.9414447801857655], [1.932078993503523, 3.9410178980271953], [1.9324518321945565, 3.9405933443810173], [1.932827337571594, 3.9401711360078826], [1.9332054948103263, 3.939751289575855], [1.9335862889817528, 3.9393338216597527], [1.9339697050527718, 3.938918748740494], [1.9343557278867736, 3.9385060872044475], [1.9347443422442372, 3.9380958533427832], [1.9351355327833333, 3.9376880633508327], [1.9355292840605296, 3.937282733327445], [1.9359255805311997, 3.9368798792743576], [1.9363244065502372, 3.9364795170955564], [1.9367257463726741, 3.9360816625966564], [1.9371295841543013, 3.935686331484271], [1.9375359039522948, 3.9352935393653965], [1.9379446897258445, 3.9349033017467923], [1.9383559253367881, 3.934515634034372], [1.9387695945502479, 3.934130551532592], [1.9391856810352717, 3.9337480694438516], [1.9396041683654772, 3.933368202867888], [1.9400250400197017, 3.9329909668011833], [1.9404482793826525, 3.9326163761363717], [1.9408738697455643, 3.9322444456616505], [1.9413017943068585, 3.9318751900601985], [1.941732036172806, 3.931508623909595], [1.9421645783581947, 3.931144761681243], [1.9425994037869998, 3.9307836177398014], [1.9430364952930577, 3.9304252063426137], [1.9434758356207442, 3.9300695416391482], [1.9439174074256556, 3.929716637670439], [1.944361193275293, 3.92936650836853], [1.9448071756497511, 3.9290191675559267], [1.9452553369424095, 3.92867462894505], [1.9457056594606272, 3.9283329061376935], [1.9461581254264426, 3.927994012624489], [1.9466127169772742, 3.927657961784372], [1.947069416166626, 3.927324766884055], [1.9475282049647957, 3.926994441077501], [1.9479890652595873, 3.9266669974054076], [1.9484519788570256, 3.9263424487946903], [1.9489169274820741, 3.9260208080579733], [1.9493838927793574, 3.925702087893081], [1.9498528563138853, 3.9253863008825403], [1.95032379957178, 3.925073459493082], [1.9507967039610086, 3.9247635760751476], [1.951271550812115, 3.9244566628624047], [1.951748321378959, 3.9241527319712612], [1.952226996839455, 3.9238517954003878], [1.9527075582963151, 3.9235538650302457], [1.953189986777796, 3.923258952622615], [1.9536742632384474, 3.9229670698201335], [1.9541603685598639, 3.922678228145834], [1.9546482835514394, 3.922392439002691], [1.955137988951126, 3.9221097136731706], [1.9556294654261925, 3.9218300633187853], [1.9561226935739895, 3.9215534989796517], [1.9566176539227143, 3.921280031574056], [1.9571143269321798, 3.921009671898024], [1.9576126929945858, 3.9207424306248924], [1.9581127324352936, 3.920478318304889], [1.9586144255136027, 3.920217345364716], [1.9591177524235288, 3.919959522107138], [1.9596226932945877, 3.919704858710576], [1.9601292281925784, 3.919453365228705], [1.9606373371203702, 3.9192050515900565], [1.9611470000186928, 3.9189599275976286], [1.9616581967669278, 3.918718002928496], [1.9621709071839029, 3.9184792871334304], [1.9626851110286883, 3.9182437896365236], [1.963200788001397, 3.9180115197348124], [1.9637179177439852, 3.9177824865979156], [1.9642364798410559, 3.917556699267669], [1.9647564538206654, 3.9173341666577697], [1.9652778191551317, 3.9171148975534242], [1.9658005552618438, 3.916898900611001], [1.9663246415040752, 3.91668618435769], [1.9668500571917986, 3.9164767571911647], [1.9673767815825025, 3.9162706273792502], [1.9679047938820093, 3.9160678030595997], [1.968434073245298, 3.9158682922393693], [1.9689645987773254, 3.9156721027949044], [1.969496349533852, 3.915479242471429], [1.9700293045222685, 3.9152897188827382], [1.9705634427024243, 3.9151035395108984], [1.9710987429874587, 3.914920711705953], [1.9716351842446331, 3.91474124268563], [1.9721727452961653, 3.9145651395350596], [1.9727114049200651, 3.9143924092064926], [1.9732511418509733, 3.9142230585190263], [1.9737919347809998, 3.914057094158336], [1.9743337623605657, 3.9138945226764106], [1.974876603199246, 3.9137353504912933], [1.9754204358666136, 3.9135795838868304], [1.9759652388930866, 3.91342722901242], [1.9765109907707739, 3.9132782918827718], [1.9770576699543259, 3.913132778377669], [1.9776052548617844, 3.912990694241735], [1.9781537238754352, 3.912852045084209], [1.9787030553426606, 3.912716836378721], [1.9792532275767947, 3.912585073463081], [1.9798042188579799, 3.9124567615390617], [1.980356007434024, 3.912331905672199], [1.9809085715212587, 3.9122105107915885], [1.9814618893054, 3.9120925816896923], [1.982015938942409, 3.9119781230221498], [1.9825706985593552, 3.911867139307592], [1.9831261462552783, 3.9117596349274675], [1.9836822601020547, 3.911655614125864], [1.9842390181452618, 3.911555081009345], [1.9847963984050454, 3.911458039546786], [1.9853543788769872, 3.911364493569217], [1.9859129375329736, 3.911274446769674], [1.9864720523220654, 3.91118790270305], [1.9870317011713678, 3.9111048647859565], [1.9875918619869029, 3.9110253362965888], [1.9881525126544803, 3.910949320374595], [1.9887136310405722, 3.910876820020953], [1.989275194993185, 3.910807838097854], [1.9898371823427359, 3.9107423773285848], [1.9903995709029263, 3.9106804402974253], [1.990962338471619, 3.9106220294495437], [1.9915254628317138, 3.9105671470908994], [1.9920889217520252, 3.9105157953881546], [1.99265269298816, 3.9104679763685866], [1.993216754283395, 3.9104236919200077], [1.9937810833695562, 3.910382943790693], [1.9943456579678973, 3.910345733589308], [1.9949104557899802, 3.9103120627848487], [1.9954754545385536, 3.91028193270658], [1.9960406319084347, 3.910255344543986], [1.9966059655873887, 3.9102322993467222], [1.9971714332570094, 3.910212798024573], [1.9977370125936023, 3.910196841347418], [1.9983026812690627, 3.9101844299451987], [1.9988684169517605, 3.910175564307896], [1.9994341973074194, 3.9101702447855105], [2.0, 3.910168471588048], [2.0005658026925803, 3.9101702447855105], [2.0011315830482395, 3.910175564307896], [2.0016973187309373, 3.9101844299451987], [2.002262987406398, 3.910196841347418], [2.0028285667429904, 3.910212798024573], [2.0033940344126115, 3.9102322993467222], [2.0039593680915653, 3.910255344543986], [2.004524545461446, 3.91028193270658], [2.00508954421002, 3.9103120627848487], [2.005654342032103, 3.910345733589308], [2.006218916630444, 3.910382943790693], [2.006783245716605, 3.9104236919200077], [2.00734730701184, 3.9104679763685866], [2.0079110782479748, 3.9105157953881546], [2.0084745371682864, 3.9105671470908994], [2.009037661528381, 3.9106220294495437], [2.0096004290970737, 3.9106804402974253], [2.010162817657264, 3.9107423773285848], [2.0107248050068147, 3.910807838097854], [2.011286368959428, 3.910876820020953], [2.01184748734552, 3.910949320374595], [2.012408138013097, 3.9110253362965888], [2.0129682988286324, 3.9111048647859565], [2.0135279476779346, 3.91118790270305], [2.0140870624670266, 3.911274446769674], [2.014645621123013, 3.911364493569217], [2.015203601594955, 3.9114580395467855], [2.015760981854738, 3.911555081009345], [2.0163177398979455, 3.911655614125864], [2.016873853744722, 3.9117596349274675], [2.017429301440645, 3.911867139307592], [2.0179840610575908, 3.9119781230221498], [2.0185381106946, 3.9120925816896923], [2.0190914284787413, 3.9122105107915885], [2.019643992565976, 3.912331905672199], [2.02019578114202, 3.9124567615390617], [2.0207467724232053, 3.912585073463081], [2.0212969446573394, 3.912716836378721], [2.0218462761245646, 3.912852045084209], [2.0223947451382154, 3.912990694241735], [2.0229423300456744, 3.913132778377669], [2.0234890092292264, 3.9132782918827718], [2.0240347611069134, 3.91342722901242], [2.0245795641333864, 3.9135795838868304], [2.0251233968007543, 3.9137353504912933], [2.0256662376394345, 3.9138945226764106], [2.0262080652190004, 3.914057094158336], [2.0267488581490265, 3.9142230585190263], [2.027288595079935, 3.9143924092064926], [2.0278272547038347, 3.9145651395350596], [2.028364815755367, 3.91474124268563], [2.0289012570125413, 3.914920711705953], [2.0294365572975757, 3.9151035395108984], [2.0299706954777315, 3.9152897188827382], [2.030503650466148, 3.915479242471429], [2.0310354012226743, 3.9156721027949044], [2.031565926754702, 3.9158682922393693], [2.0320952061179907, 3.9160678030595997], [2.0326232184174975, 3.9162706273792502], [2.033149942808201, 3.9164767571911647], [2.0336753584959246, 3.91668618435769], [2.034199444738156, 3.916898900611001], [2.0347221808448683, 3.9171148975534242], [2.0352435461793346, 3.9173341666577697], [2.035763520158944, 3.917556699267669], [2.036282082256015, 3.9177824865979156], [2.036799211998603, 3.9180115197348124], [2.0373148889713115, 3.9182437896365236], [2.0378290928160974, 3.9184792871334304], [2.038341803233072, 3.918718002928496], [2.038852999981307, 3.9189599275976286], [2.03936266287963, 3.9192050515900565], [2.0398707718074216, 3.919453365228705], [2.040377306705412, 3.919704858710576], [2.040882247576471, 3.919959522107138], [2.0413855744863976, 3.920217345364716], [2.0418872675647064, 3.920478318304889], [2.042387307005414, 3.9207424306248924], [2.04288567306782, 3.921009671898024], [2.0433823460772857, 3.921280031574056], [2.0438773064260105, 3.9215534989796517], [2.0443705345738077, 3.9218300633187853], [2.044862011048874, 3.922109713673171], [2.0453517164485606, 3.922392439002691], [2.045839631440136, 3.922678228145834], [2.0463257367615526, 3.9229670698201335], [2.046810013222204, 3.923258952622615], [2.047292441703685, 3.9235538650302457], [2.047773003160545, 3.9238517954003878], [2.048251678621041, 3.9241527319712612], [2.0487284491878848, 3.9244566628624047], [2.0492032960389914, 3.9247635760751476], [2.04967620042822, 3.925073459493082], [2.050147143686115, 3.9253863008825403], [2.0506161072206424, 3.925702087893081], [2.051083072517926, 3.9260208080579733], [2.0515480211429744, 3.9263424487946907], [2.0520109347404127, 3.9266669974054076], [2.0524717950352045, 3.926994441077501], [2.052930583833374, 3.927324766884055], [2.0533872830227256, 3.927657961784372], [2.0538418745735574, 3.927994012624489], [2.0542943405393728, 3.9283329061376935], [2.0547446630575905, 3.92867462894505], [2.055192824350249, 3.9290191675559267], [2.0556388067247067, 3.92936650836853], [2.0560825925743442, 3.929716637670439], [2.056524164379256, 3.9300695416391482], [2.0569635047069426, 3.9304252063426137], [2.057400596213, 3.9307836177398014], [2.0578354216418053, 3.931144761681243], [2.058267963827194, 3.931508623909595], [2.0586982056931413, 3.931875190060199], [2.0591261302544357, 3.9322444456616505], [2.0595517206173475, 3.9326163761363717], [2.0599749599802983, 3.9329909668011833], [2.060395831634523, 3.933368202867888], [2.060814318964728, 3.9337480694438516], [2.061230405449752, 3.934130551532592], [2.061644074663212, 3.934515634034372], [2.0620553102741557, 3.9349033017467923], [2.062464096047705, 3.9352935393653965], [2.0628704158456985, 3.935686331484271], [2.063274253627326, 3.9360816625966564], [2.063675593449763, 3.9364795170955564], [2.0640744194688003, 3.9368798792743576], [2.0644707159394704, 3.9372827333274456], [2.0648644672166667, 3.9376880633508327], [2.065255657755763, 3.9380958533427832], [2.0656442721132264, 3.9385060872044475], [2.066030294947228, 3.938918748740494], [2.0664137110182472, 3.9393338216597527], [2.066794505189674, 3.939751289575855], [2.0671726624284057, 3.9401711360078826], [2.0675481678054433, 3.9405933443810173], [2.067921006496477, 3.9410178980271953], [2.0682911637824737, 3.9414447801857655], [2.068658625050257, 3.9418739740041513], [2.069023375793086, 3.942305462538516], [2.0693854016112256, 3.9427392287544305], [2.0697446882125163, 3.943175255527548], [2.0701012214129384, 3.9436135256442775], [2.070454987137172, 3.9440540218024656], [2.0708059714191513, 3.944496726612078], [2.0711541604026182, 3.944941622595887], [2.071499540341668, 3.9453886921901606], [2.071842097601292, 3.945837917745357], [2.072181818657916, 3.9462892815268207], [2.0725186900999346, 3.946742765715482], [2.07285269862824, 3.947198352408562], [2.0731838310567468, 3.9476560236202785], [2.073512074312915, 3.9481157612825544], [2.0738374154382617, 3.948577547245735], [2.0741598415888767, 3.9490413632793016], [2.074479340035928, 3.9495071910725903], [2.0747958981661636, 3.949975012235518], [2.0751095034824107, 3.950444808299306], [2.0754201436040685, 3.9509165607172103], [2.075727806267597, 3.9513902508652534], [2.0760324793270004, 3.9518658600429593], [2.0763341507543087, 3.9523433694740913], [2.07663280864005, 3.9528227603073955], [2.0769284411937226, 3.953304013617342], [2.07722103674426, 3.953787110404875], [2.0775105837404895, 3.95427203159816], [2.077797070751593, 3.9547587580533388], [2.0780804864675524, 3.9552472705552844], [2.078360819699602, 3.955737549818359], [2.0786380593806646, 3.9562295764871775], [2.078912194565793, 3.9567233311373693], [2.0791832144325992, 3.9572187942763466], [2.079451108281683, 3.9577159463440723], [2.079715865537054, 3.9582147677138346], [2.079977475746549, 3.9587152386930198], [2.080235928582245, 3.9592173395238905], [2.080491213840866, 3.9597210503843656], [2.080743321444188, 3.9602263513888034], [2.080992241439434, 3.9607332225887846], [2.081237963999669, 3.9612416439739024], [2.081480479424187, 3.9617515954725513], [2.081719778138894, 3.962263056952719], [2.081955850696687, 3.9627760082227828], [2.0821886877778253, 3.9632904290323037], [2.0824182801902995, 3.9638062990728296], [2.082644618870195, 3.964323597978695], [2.0828676948820473, 3.964842305327824], [2.083087499419198, 3.965362400642539], [2.08330402380414, 3.965883863390368], [2.083517259488862, 3.9664066729848537], [2.0837271980551835, 3.966930808786369], [2.08393383121509, 3.9674562501029302], [2.0841371508110575, 3.9679829761910135], [2.084337148816377, 3.9685109662563747], [2.08453381733547, 3.96904019945487], [2.084727148604199, 3.9695706548932796], [2.0849171349901776, 3.9701023116301304], [2.085103768993068, 3.970635148676525], [2.08528704324488, 3.9711691449969693], [2.085466950510259, 3.9717042795102024], [2.0856434836867748, 3.972240531090031], [2.0858166358052, 3.97277787856616], [2.085986400029785, 3.973316300725031], [2.086152769658529, 3.9738557763106606], [2.0863157381234445, 3.9743962840254747], [2.086475298990815, 3.974937802531155], [2.0866314459614506, 3.9754803104494782], [2.0867841728709364, 3.97602378636316], [2.0869334736898755, 3.976568208816703], [2.0870793425241274, 3.9771135563172395], [2.0872217736150405, 3.9776598073353853], [2.0873607613396783, 3.9782069403060847], [2.0874963002110443, 3.978754933629465], [2.0876283848782955, 3.9793037656716885], [2.0877570101269556, 3.9798534147658056], [2.08788217087912, 3.980403859212611], [2.0880038621936565, 3.980955077281501], [2.0881220792664004, 3.9815070472113296], [2.0882368174303436, 3.9820597472112698], [2.0883480721558207, 3.982613155461671], [2.088455839050685, 3.9831672501149233], [2.0885601138604843, 3.9837220092963195], [2.088660892468628, 3.9842774111049155], [2.0887581708965484, 3.9848334336144005], [2.08885194530386, 3.9853900548739563], [2.08894221198851, 3.98594725290913], [2.0890289673869242, 3.9865050057226963], [2.089112208074148, 3.98706329129553], [2.0891919307639815, 3.987622087587472], [2.0892681323091096, 3.988181372538201], [2.0893408097012256, 3.9887411240681065], [2.089409960071151, 3.989301320079155], [2.0894755806889465, 3.9898619384557676], [2.0895376689640233, 3.990422957065691], [2.0895962224452425, 3.990984353760871], [2.089651238821013, 3.991546106378327], [2.0897027159193824, 3.992108192741028], [2.0897506517081226, 3.9926705906587667], [2.0897950442948114, 3.993233277929036], [2.089835891926905, 3.9937962323379064], [2.089873192991809, 3.9943594316609015], [2.0899069460169413, 3.994922853663876], [2.0899371496697903, 3.995486476103895], [2.0899638027579672, 3.9960502767301085], [2.0899869042292543, 3.996614233284634], [2.0900064531716445, 3.997178323503432], [2.090022448813379, 3.9977425251171868], [2.0900348905229778, 3.9983068158521844], [2.090043777809263, 3.998871173431193], [2.0900491103213796, 3.9994355755743416], [2.09005088784881, 4.0]
          ]
        ]
      }
    }
  ]
}

```

Ta GeoJSON format pa lahko  za vizualizacijo rezultata nato uporabimo na spletni strani [geojson.io](https://geojson.io/)

![Slika](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Prevajanje%20programskih%20jezikov/Slike/geoJson.png)

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
