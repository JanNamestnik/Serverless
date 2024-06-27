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
                <li><a href="#neki">neki</a></li>
            </ul>
            <li><a href="#Predstavitev">Predstavitev jezika</a></li>
            <ul>
                <li><a href="#neki">neki</a></li>
            </ul>
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

<h3 id="Predstavitev">3.3 Predstavitev jezika</h3>

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
