<a name="readme-top"></a>

<div align="center">
  <h1 align="center">Sistemska administracija</h1>

  <p align="center">
    Celovita aplikacija za pridobivanje, upravljanje in prikaz podatkov o dogodkih.
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/main">Projekt</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/main/Principi%20programskih%20jezikov/Scraper">Scraper</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/main/Principi%20programskih%20jezikov/Vmesnik">Vmesnik</a>
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
        <li><a href="#kloniranje-repozitorija">Kloniranje repozitorija</a></li>
      </ul>
    </li>
    <li>
        <a href="#opis-projekta">Opis projekta</a>
        <ul>
            <li><a href="#povzetek-delovanja-aplikacije">Povzetek delovanja aplikacije</a></li>
        </ul>
    </li>
    <li><a href="#kontakt">Kontakt</a></li>
    <li><a href="#viri">Viri</a></li>
  </ol>
</details>

<!-- O projektu -->
<h2 id="o-projektu">1. O projektu</h2>
Napredne tehnologije so ključne za učinkovito upravljanje in implementacijo sodobnih informacijskih sistemov. Ena izmed teh tehnologij je Docker, ki omogoča kontejnerizacijo aplikacij, kar poenostavi njihovo namestitev, upravljanje in skaliranje. V okviru predmeta sistemska administracija smo se lotili nalog, ki vključujejo vzpostavitev strežniške infrastrukture in implementacijo aplikacije v Docker okolju na platformi Microsoft Azure.
Projekt je sestavljen iz dveh glavnih nalog. Prva naloga zajema vzpostavitev potrebne infrastrukture v Docker okolju znotraj navidezne naprave na Azure, namestitev aplikacije, razvite pri predmetu Spletno programiranje, ter zagotovitev delujoče podatkovne baze, ki ni nameščena lokalno. Druga naloga se osredotoča na avtomatizacijo procesa posodabljanja aplikacije z uporabo Docker Hub, GitHub Actions in "Webhook", kar omogoča učinkovito in hitro implementacijo novih različic aplikacije, ter obveščanju o le teh na strežnik.
Namen nalog ni zgolj vzpostaviti delujočo aplikacijo, temveč tudi osvojiti napredne veščine upravljanja z infrastrukturo v oblaku ter uporabo sodobnih orodij za avtomatizacijo. 
V tem dokumentu podrobno opišemo načrtovane naloge, izvedbo posameznih korakov, izzive, s katerimi smo se soočili, in rešitve, ki smo jih uporabili. Poleg tega bomo predstavili tudi rezultate in zaključke projekta.

<h3 id="glavne-funkcionalnosti-aplikacije">1.1 Glavne funkcionalnosti naloge:</h3>

1. Vzpostavitev potrebne infrastrukture v Docker okolju:
   - Ustvarjanje uporabniškega računa na Docker Hub in Microsoft Azure.
   - Kreiranje virtualne naprave na Microsoft Azure z določenimi specifikacijami.
   - Namestitev Dockerja na virtualno napravo in vzpostavitev povezave.

2. Kontejnerizacija aplikacije:
   - Kreiranje Dockerfile za aplikacijo in podatkovno bazo MongoDB.
   - Gradnja in zagon Docker containerjev.

3. Avtomatizacija posodabljanja aplikacij:
   - Nastavitev GitHub Actions workflow za avtomatsko gradnjo in nalaganje Docker slik na Docker Hub.
   - Implementacija strežnika za prejemanje webhook sporočil in avtomatsko posodabljanje Docker containerjev.

 
 

<h2 id="uporabljena-oprema">1.2 Uporabljena oprema</h2>

* [![Docker-Desktop][Docker Desktop]][Docker-Desktop-url]
* [![Docker-Hub][Docker Hub]][Docker-Hub-url]
* [![GitHub][GitHub]][GitHub-url]
* [![Microsoft-Azure][Azure]][Azure-url]

<br />

<!-- GETTING STARTED -->
<h2 id="getting-started">2. Getting Started</h2>

<h3 id="Kaj-je-potrebno-narediti">2.1 Kaj je potrebno narediti?</h3>
Spodaj so podana navodila,  kako si naložiti potrebno infrastrukturo in strežniško storitev, vzpostaviti Github Actions Workflow, strežnik za prejemanje webhook sporočil ter skripto, ki za avtomatsko posodabljanje.

<h4 id="Nalaganje-infrastrukture-Docker-in-uporabniški-račun">2.1.1	Nalaganje infrastrukture Docker in uporabniški račun</h4>

Za uspešno izvedbo zgoraj navedenega je potrebno pričeti z lokalno namestitvijo Docker okolja. Docker okolje je brezplačno in ga lahko namestimo s uporabo brskalnika. Potrebno je ustvariti tudi uporabniški račun, ki najbolje, da je strukturiran z skupnim geslom. Geslo naj bo strukturirano tako, da ga lahko uporablja celotna delovna skupina in varno pred vsiljivci. Povezava:  [Docker](https://www.docker.com/ ) 

![Slika1]()
![Slika2]()

<h4 id="Uporabniški-račun-na-Microsoft-Azure">2.1.2	Uporabniški račun na Microsoft Azure</h4>

Docker je sedaj lokalno nameščen in se lahko vanj prijavimo z uporabniškim računom, ki ga je v našem primeru, ustvaril kar vodja skupine. 
Naslednji korak je ustvarjanje uporabniškega računa na Microsoft Azure okolju, kjer bomo gostili našo virtualno napravo. Povezava: [Azure](https://azure.microsoft.com/en-gb/)  

![Slika3]()

<h4 id="Ustvarjanje-virtualne-naprave-na-Microsoft-Azure">2.1.3	Ustvarjanje virtualne naprave na Microsoft Azure</h4>

Po uspešno ustvarjenem računu je potrebno ustvariti novo Linux virtualno napravo z naslednjimi specifikacijami: 
- Ubuntu Server (ali poljubna distribucija)
- Standard B1s: 1 vcpu, 1 GiB memory
- Subscription: Azure for Students
- Resource group: (new) (izberite poljubno ime skupine virov)
- Virtual machine name: (izberite poljubno ime navidezne naprave)
- Region: West Europe
- Image: Ubuntu Server
- Size: Standard B1s (1 vcpu, 1 GiB memory)
- Authentication type: Password
- Username: (izberite poljubno uporabniško ime)
- Public inbound ports: SSH
- Azure Spot: No

Določiti je potrebno ime virtualne naprave in geslo s katerim se bodo uporabniki kasneje prijavili, ter povezali v virtualno napravo preko terminala.

![Slika4]()

<h4 id="Kreiranje-Dockerfile">2.1.4	Kreiranje Dockerfile  Azure</h4>

Za Dockerfile je potrebno v urejevalniku kode, (ki podpira takšne vrste datotek) ga ustvariti in napisati kodo ki nam bo služila za kreiranje Docker slike. V našem primeru se nam zažene express aplikacija na vratih 3000 preko node.js strežnika. Priporočamo urejevalnik kode Visual Studio Code.

![Slika5]()

<h4 id="Povezava-na-virtualno-napravo">2.1.5	Povezava na virtualno napravo</h4>

Če želimo uspešno uporabljati našo virtualno,  moramo omogočiti dostop povezave preko protokola SSH. S tem uporabnikom omogočimo, da se preko javnega IP naslova povežejo na našo virtualno napravo,  v kolikor poznajo geslo in lastnikov javni IP naslov.
Željeno zadevo lahko vzpostavimo v nastavitvah naše virtualne naprave na Microsoft Azure

![Slika6]()

Najdemo jo pod zavihkom »connect«, kjer imamo na izbiro dva načina povezave preko SSH. V našem primeru izberemo, kar drugo možnost »Native SSH« 

Ko stisnemo na gumb »select« se nam odpre nov zavihek, kjer dobimo povezavo ki jo uporabimo v terminalu za povezavo na našo virtualno napravo

![Slika7]()

<h4 id="Vzpostavitev-Docker-na-Azure">2.1.6	Vzpostavitev Docker na Azure</h4>

V kolikor želimo dostopati do Docker infrastrukture na naši virtualni napravi na MS Azure, moramo za to izvesti naslednje korake.

Namestitev Docker na našo virtualno napravo. Uporabimo ukaz »sudo apt install docker.io«

![Slika8]()

Pred tem je seveda potrebno vzpostaviti povezavo na našo virtualno napravo preko SSH protokola. Nato preverimo ali se je Docker uspešno namestil, kar storimo z ukazom« docker –version«, ki nam izpiše verzijo nameščene infrastrukture Docker.

Sledi zagon infrastrukture z ukazom »sudo systemctl start docker«

![Slika9]()

![Slika10]()

<h4 id="Docker-Hub">2.1.7 Docker Hub</h4>

Vzpostavitev okolja Docker Hub in ustvarjanje uporabniškega računa.
Povezava: [Docker](https://www.docker.com/products/docker-hub/) 

![Slika11]()

Na strani najdemo gumb »create repository«, kjer si lahko ustvarimo en privatni repozitorij za naše Docker slike, ki jih bomo preko Github Actions Workflow shranjevali v ta repozitorij.

![Slika12]()

<h4 id="Github-Actions-Workflow">2.1.8 Github Actions Workflow</h4>

Na Github je potrebno ustvariti svoj repozitorij in v glavno vejo (main branch) dodati mapo ».github/workflow«, kjer bomo shranjevali datoteke .yml  potrebne za avtomatizacijo. Tukaj je potrebno paziti, da se direktorij nahaja znotraj naše korenske veje oz. »main branch«.

![Slika13]()

Ustvarimo datoteko npr. deploy.yml v kateri se bo prožila akcija in s tem avtomatizirala željene stvari v našem repozitoriju.
V kodi lahko sami specificiramo v katerem primeru naj se akcija proži. Mi smo nastavili akcijo, da se proži ob vsaki spremembi v glavni veji. Pametno bi bilo to narediti v veji za razvoj (»devel branch«), kjer se dogaja največ sprememb, vendar bi tam priporočili raje kakšno  testiranje kode.
Povezava na Github: [Github](https://github.com/)

<h4 id="Strežnik-za-prejemanje-webhook-sporočil">2.1.9	Strežnik za prejemanje webhook sporočil</h4>

V kolikor si želimo, da ob proženju akcije dobimo sporočilo, moramo na naši virtualni napravi ustvariti node.js ali poljuben strežnik. Ta bo poslušal na določenih vratih in naše Github commite izpisal v konzoli.
Najprej je potrebno namestiti vse potrebno, da bo naš strežnik deloval kot se od njega pričakuje.
Poleg tega je potrebno na naši virtualni napravi omogočiti tudi poslušanje na vratih, kjer bomo strežnik zagnali.
Zraven node.js  strežnika namestimo tudi ogrodje express.js.

![Slika14]()

![Slika15]()

Omogočanje »port-forwarding« na MS Azure, je potrebno, da bo naš strežnik lahko sprejemal sporočila preko proženja akcij. V našem primeru se node.js strežnik zažene na vratih 3000, tako da je potrebno odpreti vrata 3000. Gremo pod zavihek »Networking« in nato »Network settings«. Odpre se nam novo okno, kjer poiščemo gumb »Create port rule«, ki omogoča odpiranje novih vrat oz. poslušalca.

![Slika16]()

![Slika17]()

Ko gumb pritisnemo se nam odpre meni in izberemo možnost »inbound port rule«. Nato se odpre nov zavihek, kjer lahko naredimo zasnovo novega pravila, po specifikacijah, ki jih prikazuje slika. Ustvarjen port rule se nato pridruži ostalim v tabeli prikazani spodaj na sliki 19.

![Slika18]()

![Slika19]()

![Slika20]()

<h2 id="Katere projektne naloge smo izvedli (načrt rešitve)?">3.	Katere projektne naloge smo izvedli (načrt rešitve)?</h2>

<h3 id="Prva-faza">3.1 Prva faza</h3>

<h4 id="Ustvarjanje-Azure-računa">3.1.1 Ustvarjanje Azure računa</h4>

Ustvarili smo brezplačni študentski račun na Azure platformi, kar nam je omogočilo dostop do virov brez dodatnih stroškov.

<h4 id="Postavitev-navidezne-naprave-(VM)-na-Azure">3.1.2 Postavitev navidezne naprave (VM) na Azure</h4>

Na vodilnem računu smo ustvarili novo Linux navidezno napravo (VM) z podanimi specifikacijami

<h4 id="Namestitev-Docker-in-aplikacije">3.1.3 Namestitev Docker in aplikacije</h4>

- Namestili smo Docker na VM ter pripravili Dockerfile za našo aplikacijo, razvito v okviru predmeta Spletno programiranje.
- Uporabili smo Dockerfile za gradnjo in zagon Docker containerja, ki vsebuje našo aplikacijo.
- Namestili smo ločeno Docker sliko za podatkovno bazo MongoDB, saj lokalna namestitev ni bila dovoljena.
- Preverili smo delovanje aplikacije in baze podatkov v Docker okolju ter dokumentirali rezultate.

<h4 id="Konfiguracija-Gita">3.1.4 Konfiguracija Gita</h4>

- Na VM smo klonirali Git repozitorij, v katerem so shranjene vse potrebne konfiguracijske datoteke, vključno z Dockerfile in ostalimi konfiguracijskimi datotekami.
- Poskrbeli smo, da so vse spremembe v kodi in konfiguraciji ustrezno verzionirane.

<h3 id="Druga-faza">3.2 Druga faza</h3>

<h4 id="Ustvarjanje-Docker-Hub-računa">3.2.1 Ustvarjanje Docker Hub računa</h4>

- Ustvarili smo račun na Docker Hub platformi, kjer smo pripravili privatni repozitorij za shranjevanje naših Docker slik.

<h4 id="Gradnja-in-nalaganje-Docker-slik">3.2.2 Gradnja in nalaganje Docker slik</h4>

- Pripravili smo Dockerfile za novo različico aplikacije in zgradili Docker sliko.
- Novo Docker sliko smo naložili na Docker Hub repozitorij, kar omogoča njeno uporabo na različnih napravah.

<h4 id="Nastavitev-GitHub-Actions-workflow">3.2.3 Nastavitev GitHub Actions workflow</h4>

- V Git repozitoriju smo ustvarili GitHub Actions workflow, ki vključuje naslednje korake:
- Gradnja Docker slike in njeno nalaganje na Docker Hub.
- Pošiljanje sporočila (webhook) strežniku na Azure, da je nova različica aplikacije pripravljena.
- Vse občutljive podatke, kot so Docker Hub in GitHub API ključi, smo shranili v GitHub Secrets.

<h4 id="implementacija">3.2.4 Implementacija Webhook in skripte za posodabljanje Docker containerja</h4>

- Na VM smo nastavili sprejemanje webhook sporočil.
- Razvili smo skripto, ki ob prejemu webhook sporočila zaustavi trenutno zagnani Docker container, prenese najnovejšo Docker sliko iz Docker Hub in zažene nov kontejner.
- Skripta je nastavljena tako, da se zažene ob vsakem prejetem webhook sporočilu.

<h4 id="Varnostne-izboljšave">3.2.5 Varnostne izboljšave</h4>

- Pregledali smo potencialne varnostne luknje pri uporabi webhook sporočil in izvedli potrebne izboljšave za zagotovitev varnega delovanja sistema


<h2 id="3.	Implementacija">4.	Implementacija</h2>



<br />

<!-- CONTACT -->
<h2 id="kontakt">5. Kontakt</h2>

Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
<h2 id="viri">6. Viri</h2>

<p align="right">(<a href="#readme-top">nazaj na vrh</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white -->
<!-- Docker Desktop Badge -->
[Docker Desktop]: https://img.shields.io/badge/Docker%20Desktop-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-Desktop-url]: https://www.docker.com/products/docker-desktop

<!-- Docker Hub Badge -->
[Docker Hub]: https://img.shields.io/badge/Docker%20Hub-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-Hub-url]: https://hub.docker.com/

<!-- GitHub Badge -->
[GitHub]: https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white
[GitHub-url]: https://github.com/

<!-- Microsoft Azure Badge -->
[Azure]: https://img.shields.io/badge/Microsoft%20Azure-0078D4?style=for-the-badge&logo=microsoft-azure&logoColor=white
[Azure-url]: https://azure.microsoft.com/en-gb/
