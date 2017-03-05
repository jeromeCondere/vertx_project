# A faire


## important !
Ajouter tests  
L' App doit permettre le developpement polyglotte  

### système BDD
isoler le client qui gère la BDD de l'application  

### App
Le système qui gère les Appels à la BDD dispose de services  
 - acceder aux ressources via des query (70%)
 - supprimer des ressources(80%)  

### Retours
Passer par les streams  

### Problèmes
Comment on fait pour que l'utilisateur ne puisse pas avoir acccès au client des BDD?  

Comment rendre l'application capable de revoyer de gros resultset (Stream)  
 - JDBC -> queryStream (Ou envoyer les données)
 - MongoDb (Pas trouvé)  
Rajouter les codecs  


### Finition
Utiliser les services discorvery
