# Comment utiliser ce repository
Il y a plusieurs **tags git** que tu peux checkout, représentant les différentes étapes du refactoring d'une app MVC en MVVM + Flow : 
 * `MVC` ([Github Web IDE](https://github.dev/NinoDLC/Talk_MVC_to_MVVM/commit/MVC))-> Un truc pas très robuste, pas très testable, pas très performant, pas très évolutif
 * `MVVM` ([Github Web IDE](https://github.dev/NinoDLC/Talk_MVC_to_MVVM/commit/MVVM)) -> Refacto en MVVM terminé (en [LiveData](https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54))
 * `flows` ([Github Web IDE](https://github.dev/NinoDLC/Talk_MVC_to_MVVM/commit/flows)) -> Ajout des Flows pour une meilleure gestion des Threads ([entre](https://kotlinlang.org/docs/flow.html) [autres](https://developer.android.com/kotlin/flow) !)

# Comment migrer une application MVC en MVVM ? 
## Je veux juste voir la différence entre MVC et MVVM ! 
 * [Commit de refacto MVC -> MVVM](https://github.com/NinoDLC/Talk_MVC_to_MVVM/commit/MVVM_start)
 * [Commit d'ajout des Flows](https://github.com/NinoDLC/Talk_MVC_to_MVVM/commit/flows)
 
## Version "prends moi par la main"
 * Premièrement, je recommande de te familiariser avec le code en MVC. Récupère cette version du repository grâce au tag `MVC` : `git checkout MVC`. 
 * Joue un peu avec l'application. Fais la crasher (lis les `TODO` dans le code pour trouver une façon de faire), identifie les faiblesses, tourne l'écran et vois le (triste) résultat, etc...
 * [Regarde mon talk !](https://www.youtube.com/watch?v=FfSq1twWzT4) 
 * Analyse mon code sur la partie ViewModel / LiveData pour te sentir plus à l'aise en MVVM : `git checkout MVVM`. Utilise l'application et vois les avantages du MVVM. 
 * Termine ton apprentissage avec la partie Flows dans le Repository : `git checkout flows`. Simplification du code du Repository et arrêt des requêtes 5 secondes après changement d'application ou appui sur bouton "Home". 
