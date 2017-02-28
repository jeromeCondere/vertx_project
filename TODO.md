## A faire


##important !
Ajouter tests  
L' App doit permettre le developpement polyglotte  

### système BDD
isoler le client qui gère la BDD de l'application  

### App
Le système qui gère les Appels à la BDD dispose de services  
 - importer des fichiers (Abandonné)
 - acceder aux ressources via des query (70%)
 - supprimer des ressources(50%)  

### Retours
Lorsqu'une requête est traitée, le résultat est stocké dans  
un objet  
C'est l'utilisateur qui caste  

###Problèmes
Comment on fait pour que l'utilisateur ne puisse pas avoir acccès au client des BDD
 - rajouter un système de droits (trop long)
 - spécifier une clé de cryptage  

Comment rendre l'application capable de revoyer de gros resultset (Stream)
