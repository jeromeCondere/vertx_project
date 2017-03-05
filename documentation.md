# Documentation

## Structure des classes

Le projet comprend 2 classes abstraites qui constituent le coeur de l'architecture du client.  
La première classe abstraite *AbstractDBVerticle* est la classe qui permet d'interagir avec une BDD.  
C'est un client.

Les adresses qu'elle fournit sur l'event bus sont :
 - db.*dbType*.insert (insertion de documents dans la BDD)
 - db.*dbType*.find (recherche de documents)
 - db.*dbType*.delete (suppression de documents)
 - db.*dbType*.query (requête sur la BDD)

La seconde classe abstraite *AbstractApp* est la classe qui fait la relation entre l'utilisateur et
La BDD.

Les adresses qu'elle fournit sur l'event bus sont :
 - launch.service.*dbType*.insert 
 - launch.service.*dbType*.find
 - launch.service.*dbType*.delete
 - launch.service.*dbType*.query

Lorsque l'utilisateur envoie un message sur les adresses *launch.service.dbType* le message est envoyé
sur l'adresse *db.dbType* correspondante, une fois la réponse reçue est retransmise à l'utilisateur.  

Les sous-classes de *AbstractApp* sont là pour empêcher l'utilisateur d'effectuer des manipulations pouvant
mettre en danger l'intégrité de la BDD en préfiltrant les messages.  

## Structure des reponses


Pour les requetes de type insert, delete ou query le resultat est de la forme
```json
{
 "action" : "action_name" ,
 "state : "success/failed"
}

```
Avec des informations suplémentaires suivant la nature de la BDD (par exemple avec mongoDB on renvoie l'*id* 
du document concerné par la manipulation).  

Lorsque c'est une requête les données sont transferés via des streams.
