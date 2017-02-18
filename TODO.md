## A faire

Completer les tests d'initialisation et de destruction 
###important !
Ajouter tests pour le client (*le client sera sans doute supprimé*) 
 - Tester le succes pour recuperer les données
 - Revoyer un message d'erreur au serveur si les données n'ont pas été chargées

Ajouter tests pour le serveur 
 - Tester le succes pour recuperer les données récupérées du client
 - Revoyer un message d'erreur au serveur si les données n'ont pas été chargées (route 404)

### =====
gestion des grosses ressources ?  
 - Dans le cas de gros fichiers les données doivent etre transmise par paquets (Stream)
gestion des gros volumes d'utilisateurs ?  
Comprendre api du gouv  

Evaluer la pertinence du modèle simple client/serveur(fait: non pertinent)  

### routes pour le serveur
Séparer en tâches unitaire afin d'obtenir les routes les plus utiles  

- obtenir le produit par son id
- obtenir le produit par son nom 
- obtenir la valeur d'une colonne associée à un identifiant. 

### ressource csv
charger le csv (fait)  

### creation d'un serveur gerant le chargement des données csv
Chargement naif (sans Stream : fait)
