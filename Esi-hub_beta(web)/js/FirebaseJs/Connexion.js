(function(){
    firebase.auth().onAuthStateChanged(function(user){
    if (!user){
        document.open("login.html","_self");
    }
  });
})();




function login(){
    var pass = document.getElementById("Password_connexion");
    var email = document.getElementById("Email_connexion");
    try{
        switch(email.value){
            case "admin@esi-sba.dz":
                firebase.auth().signInWithEmailAndPassword(email.value, pass.value)
                .then(function(firebaseUser){
                    window.open("Page_Acceuil.html","_self");
                })
                .catch(function(error){
                    alert("failure");
                });
                break;
            case "biblio@esi-sba.dz":
                firebase.auth().signInWithEmailAndPassword(email.value, pass.value)
                .then(function(firebaseUser){
                    window.open("Page_bibliothècaire.html","_self");
                })
                .catch(function(error){
                    alert("failure");
                });
                break;
            case "stats@esi-sba.dz":
                firebase.auth().signInWithEmailAndPassword(email.value, pass.value)
                .then(function(firebaseUser){
                    window.open("Page_Statisticien.html","_self");
                })
                .catch(function(error){
                    alert("failure");
                });
                break;
            default:
                alert("ce compte n'existe pas");
                break;
        }
        
    }catch(error){
        console.log(error);
    }
    
}
function logout(){
    firebase.auth().signOut();
    window.open("login.html", "_self");
}
function resetPassword(){
    var email = document.getElementById("email_reset_connexion");
    if(email.value != ""){
        firebase.auth().sendPasswordResetEmail(email.value).then(function() {
            alert(`un email a été envoyer à l'adresse ${email.value}`);
          }).catch(function(error) {
            alert("operation echoué");
          });
    }else{
        alert("veuilliez saisir une adresse email");
    }
    
}