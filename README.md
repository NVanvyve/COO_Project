# fractal
=======
Demarche a suivre :

Pour cloner le dossier sur son ordi : Avec le terminal tu te positionnes dans le dossier ou tu veux cloner les dossiers. (le plus simple c'est de creer un nouveau repertoire specialement pour github).

Ensuite une fois dans ce répertoire tu tapes les commandes suivantes:

$git init 

$git config --global user.name "ton nom d'utilisateur github"

$git config --global user.email tonAdresseEmailAssocie

$git config core.editor gedit

Maintenant que le repertoire est pret on peut cloner les fichiers.

$git clone https://github.com/NomUtilisateurCreateur/NomProjet

Normalement la tu as tout les fichiers dans ton dossier.

Tu modifies le code ect ... 

Tu veux renvoyer ce que t'as modifier sur le serveur. Il faut bien que tu vérifies que tu te trouves dans le répertoire ou ce trouve les fichiers/dossiers que tu veux renvoyer. Ensuite tu procèdes de la manière suivante:

$git add <nomfichier>   //si tu veux envoyer qu'un seul fichier/répertoire du répertoire

$git add -A             //si tu veux envoyer tout

$git commit -m 'ce que tu as modifier'

$git remote add origin https://github.com/NomUtilisateurCreateur/NomProjet    //seulement si c'est la première fois que tu vas envoyer                                                                               un fichier sur le serveur.

$git push origin master



Si maintenant tu veux juste voire s'il y a eu un nouvel update sur le serveur tu peux utiliser la commande suivante:

$git pull https://github.com/NomUtilisateurCreateur/NomProjet


Pour plus d'info, voir :

http://sites.uclouvain.be/SystInfo/notes/Outils/html/git.html

