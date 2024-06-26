<a name="readme-top"></a>

<div align="center">
  <h1 align="center">Sistemska administracija</h1>

  <p align="center">
    <a href="https://github.com/JanNamestnik/Serverless/tree/main">Projekt</a>
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
        <li><a href="#Nalaganje-infrastrukture-Docker-in-uporabniški-račun">Nalaganje infrastrukture Docker in uporabniški račun</a></li>
        <li><a href="#Uporabniški-račun-na-Microsoft-Azure">Uporabniški račun na Microsoft Azure</a></li>
        <li><a href="#Ustvarjanje-virtualne-naprave-na-Microsoft-Azure">Ustvarjanje virtualne naprave na Microsoft Azure</a></li>
        <li><a href="#Kreiranje-Dockerfile-Azure">Kreiranje Dockerfile Azure</a></li>
        <li><a href="#Povezava-na-virtualno-napravo">Povezava na virtualno napravo</a></li>
        <li><a href="#Vzpostavitev-Docker-na-Azure">Vzpostavitev Docker na Azure</a></li>
        <li><a href="#Docker-Hub">Docker Hub</a></li>
        <li><a href="#Github-Actions-Workflow">Github Actions Workflow</a></li>
        <li><a href="#Strežnik-za-prejemanje-webhook-sporočil">Strežnik za prejemanje webhook sporočil</a></li>
      </ul>
    </li>
    <li>
        <a href="#Katere">Katere projektne naloge smo izvedli (načrt rešitve)?</a></li>
        <ul>
            <li><a href="#Prva-faza">Prva faza</a></li>
            <ul>
                <li><a href="#Ustvarjanje-Azure-računa">Ustvarjanje Azure računa</a></li>
                <li><a href="#Postavitev-navidezne-naprave-(VM)-na-Azure">Postavitev navidezne naprave (VM) na Azure</a></li>
                <li><a href="#Namestitev-Docker-in-aplikacije">Namestitev Docker in aplikacije</a></li>
                <li><a href="#Konfiguracija-Gita">Konfiguracija Gita</a></li>
            </ul>
            <li><a href="#Druga-faza">Druga faza</a></li>
            <ul>
                <li><a href="#Ustvarjanje-Docker-Hub-računa">Ustvarjanje Docker Hub računa</a></li>
                <li><a href="#Gradnja-in-nalaganje-Docker-slik">Gradnja in nalaganje Docker slik</a></li>
                <li><a href="#Nastavitev-GitHub-Actions-workflow">Nastavitev GitHub Actions workflow</a></li>
                <li><a href="#Implementacija-Webhook">Implementacija Webhook in skripte za posodabljanje Docker containerja</a></li>
                <li><a href="#Varnostne-izboljšave">Varnostne izboljšave</a></li>
            </ul>
        </ul>
    <li>
        <a href="#Implementacija">Implementacija</a>
        <ul>
            <li><a href="#Prva-projektna-naloga">Prva projektna naloga</a></li>
            <li><a href="#Druga-projektna-naloga">Druga projektna naloga</a></li>
        </ul>
    </li>
    <li><a href="#zaključek">Zaključek</a></li>
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

Spodaj so podana navodila,  kako si naložiti potrebno infrastrukturo in strežniško storitev, vzpostaviti Github Actions Workflow, strežnik za prejemanje webhook sporočil ter skripto, ki za avtomatsko posodabljanje.

<h3 id="Nalaganje-infrastrukture-Docker-in-uporabniški-račun">2.1	Nalaganje infrastrukture Docker in uporabniški račun</h3>

Za uspešno izvedbo zgoraj navedenega je potrebno pričeti z lokalno namestitvijo Docker okolja. Docker okolje je brezplačno in ga lahko namestimo s uporabo brskalnika. Potrebno je ustvariti tudi uporabniški račun, ki najbolje, da je strukturiran z skupnim geslom. Geslo naj bo strukturirano tako, da ga lahko uporablja celotna delovna skupina in varno pred vsiljivci. Povezava:  [Docker](https://www.docker.com/ ) 

![Slika1](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture1.png)
![Slika2](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture2.png)

<h3 id="Uporabniški-račun-na-Microsoft-Azure">2.2	Uporabniški račun na Microsoft Azure</h3>

Docker je sedaj lokalno nameščen in se lahko vanj prijavimo z uporabniškim računom, ki ga je v našem primeru, ustvaril kar vodja skupine. 
Naslednji korak je ustvarjanje uporabniškega računa na Microsoft Azure okolju, kjer bomo gostili našo virtualno napravo. Povezava: [Azure](https://azure.microsoft.com/en-gb/)  

![Slika3](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture3.png)

<h3 id="Ustvarjanje-virtualne-naprave-na-Microsoft-Azure">2.3	Ustvarjanje virtualne naprave na Microsoft Azure</h3>

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

![Slika4](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture4.png)

<h3 id="Kreiranje-Dockerfile">2.4	Kreiranje Dockerfile  Azure</h3>

Za Dockerfile je potrebno v urejevalniku kode, (ki podpira takšne vrste datotek) ga ustvariti in napisati kodo ki nam bo služila za kreiranje Docker slike. V našem primeru se nam zažene express aplikacija na vratih 3000 preko node.js strežnika. Priporočamo urejevalnik kode Visual Studio Code.

![Slika5](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture5.png)

<h3 id="Povezava-na-virtualno-napravo">2.5	Povezava na virtualno napravo</h3>

Če želimo uspešno uporabljati našo virtualno,  moramo omogočiti dostop povezave preko protokola SSH. S tem uporabnikom omogočimo, da se preko javnega IP naslova povežejo na našo virtualno napravo,  v kolikor poznajo geslo in lastnikov javni IP naslov.
Željeno zadevo lahko vzpostavimo v nastavitvah naše virtualne naprave na Microsoft Azure

![Slika6](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture6.png)

Najdemo jo pod zavihkom »connect«, kjer imamo na izbiro dva načina povezave preko SSH. V našem primeru izberemo, kar drugo možnost »Native SSH« 

Ko stisnemo na gumb »select« se nam odpre nov zavihek, kjer dobimo povezavo ki jo uporabimo v terminalu za povezavo na našo virtualno napravo

![Slika7](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture7.png)

<h3 id="Vzpostavitev-Docker-na-Azure">2.6	Vzpostavitev Docker na Azure</h3>

V kolikor želimo dostopati do Docker infrastrukture na naši virtualni napravi na MS Azure, moramo za to izvesti naslednje korake.

Namestitev Docker na našo virtualno napravo. Uporabimo ukaz »sudo apt install docker.io«

![Slika8](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture8.png)

Pred tem je seveda potrebno vzpostaviti povezavo na našo virtualno napravo preko SSH protokola. Nato preverimo ali se je Docker uspešno namestil, kar storimo z ukazom« docker –version«, ki nam izpiše verzijo nameščene infrastrukture Docker.

Sledi zagon infrastrukture z ukazom »sudo systemctl start docker«

![Slika9](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture9.png)

![Slika10](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture10.png)

<h3 id="Docker-Hub">2.7 Docker Hub</h3>

Vzpostavitev okolja Docker Hub in ustvarjanje uporabniškega računa.
Povezava: [Docker](https://www.docker.com/products/docker-hub/) 

![Slika11](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture11.png)

Na strani najdemo gumb »create repository«, kjer si lahko ustvarimo en privatni repozitorij za naše Docker slike, ki jih bomo preko Github Actions Workflow shranjevali v ta repozitorij.

![Slika12](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture12.png)

<h3 id="Github-Actions-Workflow">2.8 Github Actions Workflow</h3>

Na Github je potrebno ustvariti svoj repozitorij in v glavno vejo (main branch) dodati mapo ».github/workflow«, kjer bomo shranjevali datoteke .yml  potrebne za avtomatizacijo. Tukaj je potrebno paziti, da se direktorij nahaja znotraj naše korenske veje oz. »main branch«.

![Slika13](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture13.png)

Ustvarimo datoteko npr. deploy.yml v kateri se bo prožila akcija in s tem avtomatizirala željene stvari v našem repozitoriju.
V kodi lahko sami specificiramo v katerem primeru naj se akcija proži. Mi smo nastavili akcijo, da se proži ob vsaki spremembi v glavni veji. Pametno bi bilo to narediti v veji za razvoj (»devel branch«), kjer se dogaja največ sprememb, vendar bi tam priporočili raje kakšno  testiranje kode.
Povezava na Github: [Github](https://github.com/)

<h3 id="Strežnik-za-prejemanje-webhook-sporočil">2.9	Strežnik za prejemanje webhook sporočil</h3>

V kolikor si želimo, da ob proženju akcije dobimo sporočilo, moramo na naši virtualni napravi ustvariti node.js ali poljuben strežnik. Ta bo poslušal na določenih vratih in naše Github commite izpisal v konzoli.
Najprej je potrebno namestiti vse potrebno, da bo naš strežnik deloval kot se od njega pričakuje.
Poleg tega je potrebno na naši virtualni napravi omogočiti tudi poslušanje na vratih, kjer bomo strežnik zagnali.
Zraven node.js  strežnika namestimo tudi ogrodje express.js.

![Slika14](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture14.png)

![Slika15](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture15.png)

Omogočanje »port-forwarding« na MS Azure, je potrebno, da bo naš strežnik lahko sprejemal sporočila preko proženja akcij. V našem primeru se node.js strežnik zažene na vratih 3000, tako da je potrebno odpreti vrata 3000. Gremo pod zavihek »Networking« in nato »Network settings«. Odpre se nam novo okno, kjer poiščemo gumb »Create port rule«, ki omogoča odpiranje novih vrat oz. poslušalca.

![Slika16](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture16.png)

![Slika17](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture17.png)

Ko gumb pritisnemo se nam odpre meni in izberemo možnost »inbound port rule«. Nato se odpre nov zavihek, kjer lahko naredimo zasnovo novega pravila, po specifikacijah, ki jih prikazuje slika. Ustvarjen port rule se nato pridruži ostalim v tabeli prikazani spodaj na sliki 19.

![Slika18](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture19.png)

![Slika19](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture20.png)

![Slika20](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture21.png)

<h2 id="Katere">3.	Katere projektne naloge smo izvedli (načrt rešitve)?</h2>

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

<h4 id="Implementacija-Webhook">3.2.4 Implementacija Webhook in skripte za posodabljanje Docker containerja</h4>

- Na VM smo nastavili sprejemanje webhook sporočil.
- Razvili smo skripto, ki ob prejemu webhook sporočila zaustavi trenutno zagnani Docker container, prenese najnovejšo Docker sliko iz Docker Hub in zažene nov kontejner.
- Skripta je nastavljena tako, da se zažene ob vsakem prejetem webhook sporočilu.

<h4 id="Varnostne-izboljšave">3.2.5 Varnostne izboljšave</h4>

- Pregledali smo potencialne varnostne luknje pri uporabi webhook sporočil in izvedli potrebne izboljšave za zagotovitev varnega delovanja sistema


<h2 id="Implementacija">4.	Implementacija</h2>

Reševanja nalog smo se lotili tako, da smo vse večje naloge razčlenili na manjše naloge oz. opravila, ki smo jih enakovredno razdelili med člane mikroskupine. Vsak član je tedensko poročal na projektnih sestankih, kako se je naloge lotil in ali so bile kakšne težave pri samem reševanju.

<h3 id="Prva-projektna-naloga ">4.1	Prva projektna naloga </h3>

Kot že omenjeno smo najprej vzpostavili okolje Docker, brez katerega ne bi morali realizirati prve projektne naloge.

- Ustvarili smo skupni uporabniški račun do katerega lahko dostopamo vsi člani. Namestili smo Docker Desktop lokalno in se vanj tudi prijavili

![Slika22](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture22.png)

- Nato smo ustvarili smo uporabniški račun na Microsoft Azure, do katerega lahko dostopamo vsi člani. 

- S pomočjo Docker CLI smo namestili našo podatkovno bazo MongoDB, katera ni lokalna

![Slika23](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture23.png)

- Pri tem smo uporabili CLI ukaze, ki jih ponuja Docker, ko ga namestimo na naš sistem. Na primer: »docker build« za grajenje slik, »docker run« za kontejnerizacijo slik, »docker ps« za izpis vseh aktivnih kontejnerjev. S pomočjo le teh smo omogočili, da je naša aplikacija iz predmeta spletno programiranje aktivno tekla na vratih.

- Po tem, ko smo ustvarili virtualno napravo na MS Azure z podanimi specifikacijami, smo jo tudi povezali z našim Docker-jem. Da smo zagotovili, da je povezava res uspela smo tudi to preizkusili z ukazom »sudo docker –version«, ki nam je izpisal trenutno različico Docker na naši virtualni napravi.

- S pomočjo naše Dockerfile datoteke smo ustvarili prvo Docker sliko in kontejner za našo express aplikacijo pri predmetu spletno programiranje. Stvar smo speljali na vratih 4000.

![Slika24](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture24.png)

- Dostopnost naše aplikacije iz javnega omrežja smo rešili z uporabo SSH protokola. Klonirali smo naš Git repozitorij na virtualno napravo preko CLI ukazov. Tukaj smo imeli težavo z avtentikacijo Github računa. Težavo smo rešili, tako da smo uporabili »GitHub Access token«, ki je omogočil, da smo se prijavili na naš repozitorij preko terminala.

- Možnost povezave vseh članov na virtualno napravo smo testirali z ukazom »ssh -i ~/.ssh/id_rsa.pem Serverless@51.136.39.245« . Ukaz ob proženju zahteva geslo naše virtualne naprave in ob uspešnem vnosu se lahko prijavimo.

![Slika25](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture25.png)

![Slika26](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture26.png)

- Pri odgovarjanju na zastavljena vprašanja, smo si pri obeh pomagali s spletom in datotekami naloženimi v e-študij. V poštev je prišlo tudi samo okolje MS Azure, kjer smo iskali odgovore.

<h3 id="Druga-projektna-naloga ">4.1 Druga projektna naloga </h3>

Pri drugi projektni nalogi je bila implementacija nekoliko težja kot pri prvi. Potrebno je bilo vzpostavit aktiven GitHub Actions Workflow, ki omogoča avtomatsko nalaganje Docker slik v naš Docker Cotainer Registry. 

- Kot prvo smo se lotili izdelave uporabniškega računa na Docker Hub. Ponovno smo izbrali enake prijavne podatke tako, da smo vsem članom omogočili dostop do računa. 

![Slika27](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture27.png)

- Nato smo ustvarili repozitorij v katerega se nalagajo Docker slike, ki so ustvarjene v našem Github Actions Workflow ob vsaki spremembi v glavni veji.

![Slika28](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture28.png)

- Naslednji korak je bil dejanska implementacija našega workflowa. Najprej smo naredili direktorij ».github/workflows« v našem repozitoriju na Github. Ustvarili smo .yml datoteko poimenovano »deploy.yml«. 

- Tukaj smo se srečali s problemi, saj nismo vedeli da mora biti ta direktorij obvezno v korenski veji našega Github repozitorija. Zadevo smo rešili in nadaljevali s pisanjem kode.

- Koda je sestavljena iz dveh glavnih delov. V prvem imamo vključeno github akcijo, ki dejansko ustvari novo sliko glede na naš Dockerfile, ki se nahaja v Backend direktoriju. Slika se nato potisne v repozitorij na Docker Hub. V drugem delu imamo sporočila preko webhook orodja. Na naš strežnik, ki je zagnan se bo poslalo sporočilo ob vsaki akciji, ki se bo prožila v našo <strong>glavno vejo</strong>.

- Pri webhook smo naleteli na težavo, da se sporočila niso pravilno poslala. Ugotovili smo, da je problem tičal v vratih, ki smo jih nastavili za ustvarjanje naših kontejnerjev.

- Posluževali smo se tudi »Github Secrets«, ki nam koristijo pri varnosti v našem Github Actions Workflow«. V njih smo shranili uporabniško ime in geslo do našega Docker Hub repozitorija ter URL povezavo na naš webhook strežnik.

![Slika29](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture29.png)

- Težavo smo odpravili tako, da smo naše kontejnerje začeli ustvarjati na portu 4000.


![Slika30](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture30.png)

- Sam strežnik smo namestili na naši virtualni napravi s pomočjo node.js in express.js. Ustvarili smo direktorij webhook-handler v katerem imamo vse konfiguracijske in strežniške datoteke. V enakem direktoriju se nahaja tudi bash skripta, ki omogoča avtomatično posodabljanje kontejnerjev v našem repozitoriju. Potrebno je poudarit, da se skripta ne proži v kolikor se webhook sporočilio ni poslalo. Enako velja za Webhook, ki se ne pošlje  v primeru, da se slika na potisnila na repozitorij.

![Slika31](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture31.png)

- Skripta poimenovana update_container deluje tako da v spremenljivke shranimo ime kontejnerja, ki ga ustvarimo pred tem v Github Actions Workflowu in ime slike. V if stavku nato preverimo ali kontejner že teče v ozadju, v primeru da ga najdemo se ustavi. V novem if stavku ta kontejner zbrišemo. Za preverjanje ali se res naloži nova slika se pridobi id stare in se lahko primerja z novim. Na koncu se zažene nov kontejner z novo sliko iz Docker Container Registry.

![Slika32](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Sistemska%20administracija/Slike/Picture32.png)

- Skripto smo obogatili tudi z izpisi starih in novih slik v konzolo, kar nam omogoča lažji pregled nad dogajanjem, ter odkrivanjem napak v sami skripti. 

<br />

<h2 id="zaključek">5. zaključek</h2>

Naloge, izvedene v okviru predmeta sistemska administracija, so omogočile pridobitev dragocenih praktičnih izkušenj na področju uporabe naprednih tehnologij za upravljanje in implementacijo aplikacij v oblačnem okolju. S pomočjo Dockerja smo uspešno kontejnerizirali aplikacijo, razvito pri predmetu Spletno programiranje, in jo namestili na navidezno napravo na platformi Microsoft Azure. S tem smo pridobili vpogled v prednosti kontejnerizacije, kot so poenostavljena namestitev, boljša prenosljivost aplikacij in enostavnejše upravljanje odvisnosti.

Uspešno smo vzpostavili GitHub Actions workflow, ki avtomatsko gradi in nalaga nove Docker slike ter obvešča strežnik o novih različicah aplikacije. S pomočjo preproste skripte na strežniku smo avtomatizirali proces posodabljanja aplikacije, kar je izboljšalo učinkovitost in zmanjšalo možnost napak pri ročnem posodabljanju.

Skozi ta projekt smo pridobili pomembne veščine, ki so ključne za delo na področju sistemske administracije in DevOps. Naučili smo se uporabljati različna orodja in tehnologije za vzpostavitev, upravljanje in avtomatizacijo infrastrukture v oblaku. Poleg tega smo pridobili tudi izkušnje pri reševanju težav in izboljšanju varnosti sistema.

Na koncu smo dosegli cilje, ki smo si jih zadali, in uspešno izvedli vse načrtovane naloge. Naše delo predstavlja dobro osnovo za nadaljnje projekte in omogoča, da se v prihodnosti lotimo še bolj zahtevnih nalog na področju sistemske administracije in oblačnih tehnologij.


<!-- CONTACT -->
<h2 id="kontakt">6. Kontakt</h2>

Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
<h2 id="viri">7. Viri</h2>

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [GitHub](https://github.com/)
- [Microsoft Azure](https://azure.microsoft.com/en-gb/)
- [Docker Hub](https://hub.docker.com/)

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
