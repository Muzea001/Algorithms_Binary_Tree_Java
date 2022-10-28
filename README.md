# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Muaath Zerouga s333161


# Oppgavebeskrivelse

Oppgave 1)
- Vi starter med å sette p som roten og lage en hjelpevariabel int cmp = 0. Deretter fortsetter vi med å ta p ut av treet og sette q som forelder til p.
Vi bruker da komparatoren og flytter p ved bruk av ternary if.
P blir null som vil si at den er ute av treet og q blir den siste noden vi passerer. 

Oppgave 2)
- Vi starter med å si at hvis verdi er lik null så skal vi returnere null. Antall blir satt som null og p som roten. Deretter starter vi en while løkke, den sier at vi har en verdi p som ikke er lik null den skal få en temp verdi som skal sammenligne verdi med p.verdi. Hvis den er mindre enn null skal den settes til venstre for p.verdi og større skal settes til høyre. 

Oppgave 3)
- Vi starter med å lage metoden førstepostorden, det blir satt krav om at verdier ikke skal være lik null. Deretter starter vi en while løkke som sier at dersom p.venstre ikke er lik null skal roten p bli p.venstre, eller hvis p.høyre ikke er null skal den bli roten p. Til slutt hvis ikke dette stemmer skal den returnere roten p.
- Vi lager en til metode som heter nestepostorden. Der setter vi k som forelder og starter en if setning som sier at dersom k er null skal vi returnere null. Den andre if setningen sier at dersom k.høyre er lik roten p eller null skal den returnere k. Til slutt dersom det ikke stemmer skal vi returnere førstepostorden med verdien k.høyre.

Oppgave 4)
-	Vi traverserer en node og sjekker om den er tom, hvis den er tom gjør den ikke noe. Hvis ikke kaller den metoden rekursivt og printer verdien av noden. Starter alltid med venstre node og går videre til høyre. 
