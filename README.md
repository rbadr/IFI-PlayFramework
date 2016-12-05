# IFI PlayFramework

Pour commencer, clonez ce projet sur votre poste :
```
mkdir -p $HOME/ifiplayframework
cd $HOME/ifiplayframework
git clone https://github.com/Namiro/IFI_PlayFramework.git
cd $HOME/ifiplayframework/IFI_PlayFramework
chmod u+x sbt
chmod u+x sbt-dist/bin/sbt
```

## TP : Application CRUD
  Il s'agit d'une application **Play** classique CRUD en **Scala**, soutenue par une base de données JDBC en mémoire. Elle utilise **ANORM** pour l'accès à la Base de Données, pagination classique, et l'intégration avec un framework CSS (Play Bootstrap).

  [Anorm](https://cchantep.github.io/anorm/) : Play comprend une couche d'accès aux données simple appelée Anorm qui utilise le langage SQL pour interagir avec la base de données et fournit une API pour analyser et transformer les ensembles de données résultants.

  [Play-Bootstrap](https://adrianhurt.github.io/play-bootstrap/) : Une collection d'input helpers et de field constructors pour PlayFramework utiles pour interpréter du code HTML avec Bootstrap.

Ce tp consiste à implémenter les actions Create, Read, Update et Delete.

Votre but est d'appeler les méthodes des services pour pouvoir implémenter ces actions.

Pour vous faciliter les choses, une base de l'application est fourni.

* **controllers** : ici on définit les actions de notre HomeController.
* **models** : contient les services et les méthodes dont vous aurez besoin pour implémenter les actions.
* **views** : contient les vues dont vous aurez besoin aussi.
* **evolutions** : contient les scripts sql exécutés pour peupler notre base de données.
* **routes** : ici on définit les routes pour les actions du HomeController.


# Builder et lancer l'application :

Pour cela, il suffit de taper la commande
```
./sbt run
```

Celle ci va lancer notre serveur sbt. (ctrl+D pour l'éteindre)

Accéder ensuite à l'url :

http://localhost:9000/

Un script SQL sera exécuté sur la base de données en mémoire embarquée, cliquer sur "Apply this script now!" afin de peupler la base de données qu'on utilisera dans ce tp.

**Remarque** : A chaque changement que vous effectuez sur le code de l'application, pas la peine d'éteindre le serveur et de le relancer, il suffit juste d'actualiser son navigateur! La magie du Play Framework et sa compilation à la volée !!!

# TP :

Pour ce TP, vous êtes donc amenez à implémenter des actions pour ajouter, mettre a jour et supprimer un étudiant:

## Comment le faire :
* Créer l'action correspondante a un bouton dans le HomeController en utilisant les méthodes déjà implémentées dans StudentService et ProgramService.

```scala
def actionName = Action {
	Ok(html.viewToRender(param1,param2))
}
```

* Ajouter le chemin de cette action dans le fichier routes
```scala
# Exemple
GET   /clients/:id          controllers.Clients.show(id: Long)
```
* Ajouter l'appel à cette action dans la view correspondante avec Play Bootsrap.
```scala
@b3.form(routes.controllerName.actionName(params)) {
<fieldset>...</fieldset>
<div>...</div>
}
```

## Les vues :

* list.scala.html

![alt text](https://github.com/Namiro/IFI_PlayFramework/blob/master/ressources/application.PNG "list.scala.html")

* createForm.scala.html

![alt text](https://github.com/Namiro/IFI_PlayFramework/blob/master/ressources/add.PNG "createForm.scala.html")

* editForm.scala.html

![alt text](https://github.com/Namiro/IFI_PlayFramework/blob/master/ressources/EditDelete.PNG "editForm.scala.html")

# Travail à faire :

##### 1. Ajouter un étudiant :

1. Créer dans le contrôleur l'action **create** qui s'occupe d'afficher le formulaire d'ajout qui prend en paramètres studentForm et programService.options .

2. Ajouter le chemin de cette action dans le fichier **routes**.

3. Ajouter cette action au bouton **Add a new student**.

4. Compléter l'action **save** du contrôleur afin de pouvoir enregistrer un nouveau étudiant.

5. Ajouter le chemin de cette action dans le fichier **routes**.

6. Ajouter cette action au bouton **Create this student**.

7. Ajouter l'action du bouton **Cancel** qui nous retourne vers la liste des étudiants (Home).

##### 2. Mettre à jour un étudiant :

1. Ajouter le chemin de l'action edit dans le fichier **routes**.

2. Ajouter le lien vers cette action dans la view correspondante afin de pouvoir afficher le formulaire d'édition quand on clique sur le nom d'un étudiant.

3. Compléter l'action **update** du contrôleur afin de pouvoir mettre à jour un étudiant.

4. Ajouter le chemin de l'action update dans le fichier **routes**.

5. Ajouter cette action au bouton **Save this student**.

6. Ajouter l'action du bouton **Cancel** qui nous retourne vers la liste des étudiants (Home).

##### 3. Supprimer un étudiant :

1. Créer dans le contrôleur l'action **delete** qui s'occupe de supprimer un étudiant.(Ne pas oublier la redirection avec Home.flashing(...))

2. Ajouter le chemin de l'action delete dans le fichier **routes**.

3. Ajouter cette action au bouton **Delete this student**.
