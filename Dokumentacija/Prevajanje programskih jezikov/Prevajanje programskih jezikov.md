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
                <li><a href="#neki">neki</a></li>
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

<h3 id="Analiza">3.1 Analiza in načrtovanje jezika</h3>

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
