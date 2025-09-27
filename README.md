# Projet QuizApp : Monolithe vers Microservices

Ce projet est une application de quiz conçue comme un exercice pratique pour comprendre et mettre en œuvre la transition d'une architecture monolithique vers une architecture de microservices en utilisant Spring Boot et Spring Cloud.

## Objectif Pédagogique

L'objectif principal est de fournir un exemple concret pour les développeurs (en particulier les juniors) qui apprennent les concepts des microservices. Le projet contient à la fois le code du **monolithe d'origine** (dans le dossier `src` à la racine) et sa **version éclatée en microservices** (dans les sous-dossiers).

Cela permet de comparer les deux approches et de comprendre les avantages et les défis de chacune.

## Architecture des Microservices

L'application est divisée en plusieurs services indépendants qui collaborent pour fournir la fonctionnalité complète du quiz.

```
+-----------------+      +---------------------+      +-----------------------+
|                 |      |                     |      |                       |
|     Client      +------>   API Gateway       +------>  Service Registry     |
| (Postman, etc.) |      | (api-getway)        |      |  (server-registery)   |
|                 |      |                     |      |                       |
+-----------------+      +---------+-----------+      +-----------------------+
                                   |
                                   |
           +-----------------------+-----------------------+
           |                                               |
           v                                               v
+-----------------------+                      +-----------------------+
|                       |                      |                       |
|   Service Questions   |                      |     Service Quiz      |
|      (questions)      <---------------------->       (quizservice)     |
|                       |   (via OpenFeign)    |                       |
+-----------------------+                      +-----------------------+

```

### Composants

1.  **`server-registery` (Eureka Server)**
    *   **Rôle** : C'est l'annuaire de notre écosystème. Chaque microservice s'enregistre auprès de lui à son démarrage. Cela permet aux services de se trouver les uns les autres par leur nom de service (`question-service`, `quiz-service`) sans avoir besoin de connaître leurs adresses IP ou ports.
    *   **Port** : `8761`

2.  **`questions` (Microservice de Questions)**
    *   **Rôle** : Gère toute la logique liée aux questions du quiz (création, lecture, mise à jour, suppression - CRUD). Il expose une API REST pour ces opérations.
    *   **Port** : `8081`
    *   **Nom du service Eureka** : `QUESTION-SERVICE`

3.  **`quizservice` (Microservice de Quiz)**
    *   **Rôle** : Gère la logique de création et de déroulement des quiz. Pour créer un quiz, il doit récupérer des questions. Il le fait en communiquant avec le service `questions` de manière transparente grâce à **OpenFeign**.
    *   **Port** : `8082`
    *   **Nom du service Eureka** : `QUIZ-SERVICE`

4.  **`api-getway` (API Gateway)**
    *   **Rôle** : C'est le point d'entrée unique pour toutes les requêtes externes. Il redirige les requêtes vers le bon microservice en se basant sur le chemin de l'URL. Par exemple, une requête vers `/question/...` sera routée vers le service `questions`, et `/quiz/...` vers `quizservice`.
    *   **Port** : `8080`

## Technologies Utilisées

*   **Java 17**
*   **Spring Boot 3** : Framework principal pour créer les applications.
*   **Spring Web** : Pour créer les API REST.
*   **Spring Data JPA** : Pour interagir avec la base de données.
*   **H2 Database** : Une base de données en mémoire, simple pour le développement.
*   **Maven** : Outil de gestion de projet et de dépendances.
*   **Spring Cloud** :
    *   **Eureka** : Pour la découverte de services (Service Discovery).
    *   **Spring Cloud Gateway** : Pour la passerelle API.
    *   **OpenFeign** : Pour faciliter la communication REST entre les services.

## Comment Lancer le Projet

**Prérequis :**
*   JDK 17 ou supérieur
*   Maven

**Étapes de Lancement :**

L'ordre de démarrage est **crucial** pour que les services puissent s'enregistrer et se découvrir correctement.

1.  **Démarrer le Service Registry (Eureka)**
    ```bash
    cd server-registery
    mvn spring-boot:run
    ```
    Attendez qu'il soit complètement démarré. Vous pouvez vérifier son interface sur `http://localhost:8761`.

2.  **Démarrer le Service de Questions**
    ```bash
    cd questions
    mvn spring-boot:run
    ```
    Une fois démarré, il devrait apparaître dans l'interface d'Eureka.

3.  **Démarrer le Service de Quiz**
    ```bash
    cd quizservice
    mvn spring-boot:run
    ```
    Ce service apparaîtra également dans Eureka.

4.  **Démarrer l'API Gateway**
    ```bash
    cd api-getway
    mvn spring-boot:run
    ```

Une fois tous les services lancés, toutes les interactions doivent se faire via l'API Gateway sur le port `8080`.

## Concepts Clés des Microservices Illustrés

*   **Découverte de Services (Service Discovery)** : Regardez comment les `application.properties` des services `questions` et `quizservice` contiennent l'URL d'Eureka. Ils ne se connaissent pas directement.
*   **Passerelle API (API Gateway)** : Le fichier `application.properties` de `api-getway` montre comment les routes sont définies pour rediriger le trafic. C'est la seule porte d'entrée.
*   **Communication Inter-Services** : Explorez le package `feign` dans `quizservice`. L'interface `QuestionInterface` montre comment Spring Cloud OpenFeign permet d'appeler l'API du service `questions` en écrivant une simple interface Java, sans implémenter manuellement des appels HTTP.

## Pour Aller Plus Loin

Pour améliorer ce projet et continuer à monter en compétences, voici quelques pistes :

1.  **Configuration Centralisée** : Actuellement, chaque service a son propre fichier de configuration. Mettez en place **Spring Cloud Config** pour gérer la configuration de tous les services depuis un seul endroit (par exemple, un dépôt Git).
2.  **Résilience** : Implémentez un **Circuit Breaker** (avec Resilience4j) sur les appels OpenFeign. Que se passe-t-il si le service `questions` est indisponible ? Le Circuit Breaker empêchera une cascade de pannes.
3.  **Sécurité** : Sécurisez les endpoints en utilisant Spring Security et OAuth2/JWT. L'API Gateway est l'endroit idéal pour centraliser l'authentification.
4.  **Conteneurisation** : Écrivez un `Dockerfile` pour chaque service et utilisez `docker-compose` pour lancer tout l'écosystème avec une seule commande.
