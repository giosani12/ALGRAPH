# ALGRAPH - Algoritmo di Johnson 
## Funzionalità:
Il progetto consiste nella presentazione e visualizzazione grafica dell'algoritmo di Johnson su un grafo non orientato. <br>
La parte grafica consiste in un file FXML, dotato di un riquadro, dove viene illustrato l'algoritmo passo per passo, e vari bottoni ai lati
che permettono all'utente di interagire con il programma. <br>

## Scelte implementative:
La parte grafica è realizzata con Scene Builder; per quanto riguarda l'implementazione viene utilizzato un controller che permette di gestire le varie strutture dati e l'algoritmo.
I nodi vengono memorizzati in un array e le connessioni sono memorizzate in una matrice, infine per l'esecuzione dell'algoritmo è stato implementato un heap binario.

## Interazione con l'utente:
Una volta eseguito il programma si apre immediatamente la schermata principale, intorno al rettangolo dove viene illustrato il funzionamento dell'algoritmo
sono presenti dei bottoni che permettono all'utente di interagire con il programma stesso. <br>
<b>Carica grafo: </b>Permette di caricare un grafo salvato in precedenza.<br>
<b>Genera grafo: </b>Presente accanto una casella di testo da riempire con un intero, genera un grafo casuale con un numero di nodi pari a quello inserito nella casella di testo.<br>
<b>Inserisci nodo: </b>Permette di inserire un nodo all'interno del grafo. Questi nodi possono essere trascinati con un click.<br>
<b>Salva: </b> Permette di salvare il grafo che si sta visualizzando.<br>
<b>Elimina grafo: </b>Permette di eliminare il grafico corrente. <br>
<b>Inizia algoritmo: </b>Fa iniziare l'esecuzione dell'algoritmo.<br>
<b>Procedi step-by-step: </b>Permette di passare allo step successivo.<br>
<b>Output finale: </b>Esegue l'algoritmo sul grafo e restituisce l'output finale.<br><br>
Ogni volta che l'utente preme uno dei bottoni compare un messaggio nella parte inferiore delle schermo, questo dimostra che l'operazione richiesta è avvenuta con successo; se invece si cerca di inserire caratteri non permessi compare un alert che blocca l'utente e lo invita a inserire nuovamente i dati.


## Membri del gruppo:
Sani Giovanni Santilli Francesco
