(function(){

    EtudiantReference = firebase.database().ref().child("Liste_Etudiants").child(localStorage.getItem("user_id"));
    
    //Insertion des données:
    var Nom = document.getElementById("Nom");
    EtudiantReference.child("nom").once("value", snap=> Nom.innerHTML = snap.val());

    var Prenom = document.getElementById("Prenom");
    EtudiantReference.child("prenom").once("value", snap=> Prenom.innerHTML = snap.val());
    
    var Email = document.getElementById("Email");
    EtudiantReference.child("email").once("value", snap=> Email.innerHTML = snap.val());

    var autre_Email = document.getElementById("autre_Email");
    EtudiantReference.child("other_email").once("value", snap=> autre_Email.innerHTML = snap.val());

    var carte_etud = document.getElementById("numero_carte_etudiant");
    EtudiantReference.child("numero_carte_etudiant").once("value", snap=> carte_etud.innerHTML = snap.val());

    var telephone = document.getElementById("telephone");
    EtudiantReference.child("telephone").once("value", snap=> telephone.innerHTML = snap.val());

    var birthday = document.getElementById("birthday");
    EtudiantReference.child("Date_de_naissance").once("value", snap=> birthday.innerHTML = snap.val());

    var birthplace = document.getElementById("birthplace");
    EtudiantReference.child("Lieu_de_naissance").once("value", snap=> birthplace.innerHTML = snap.val());

    var wilaya = document.getElementById("wilaya");
    EtudiantReference.child("wilaya").once("value", snap=> wilaya.innerHTML = snap.val());

    var Promotion = document.getElementById("promotion");
    EtudiantReference.child("Promotion").once("value", snap=> Promotion.innerHTML = snap.val());

    var Niveau = document.getElementById("niveau_etude");
    EtudiantReference.child("niveau").once("value", snap=> Niveau.innerHTML = snap.val());

    var carte_button = document.getElementById("carte_etudiant_download");
    carte_button.onclick = function(){
        EtudiantReference.child("lien_carte_etudiant").on("value", function(snap){
            if((snap.val() != null)&&(snap.val() != "")){
                var user = localStorage.getItem("user_id");
                var storageRef= firebase.storage().ref().child(user);
                alert(user);
                storageRef.child("carte_etudiant").getDownloadURL().then(function(url) {
                    var xhr = new XMLHttpRequest();
                    xhr.responseType = 'blob';
                    xhr.onload = function(event) {
                    var blob = xhr.response;
                    var a = document.createElement("a");
                    document.body.appendChild(a);
                    var url = window.URL.createObjectURL(blob);
                    a.href = url;
                    a.download = "carte_etudiant_"+user;
                    a.click();
                    window.URL.revokeObjectURL(url);


                    };
                    xhr.open('GET', url);
                    xhr.send();

                    
                }).catch(function(error) {
                });
            }else{
                alert("pas encore uploader");
            }
        });



    };

    EtudiantReference.child("Verifications").child("id").on("value", function(snap){
        if(snap != null){
            if(snap){
                document.getElementById("signaler_id_button").style.display = "none";
                document.getElementById("id_signale_span").style.display = "none";
                document.getElementById("Valider_id_button").style.display = "none";
            }else{
                document.getElementById("id_valide_span").style.display = "none";
                document.getElementById("Valider_id_button").style.display = "";
                document.getElementById("id_signale_span").style.display = "";
                document.getElementById("signaler_id_button").style.display = "none";

            }
        };
    });


    var valider_id_button = document.getElementById("Valider_id_button");
    valider_id_button.onclick = function(){
        EtudiantReference.child("Verifications").child("id").set({
            id: true
        });
        document.getElementById("id_valide_span").style.display = "";
        document.getElementById("Valider_id_button").style.display = "none";
        document.getElementById("signaler_id_button").style.display = "none";
        document.getElementById("id_signale_span").style.display = "none";

    
    };
    
    var Signaler_id_button = document.getElementById("signaler_id_button");
    Signaler_id_button.onclick = function(){
        EtudiantReference.child("Verifications").child("id").set({
            id: false
        });
        document.getElementById("id_signale_span").style.display = "";
        document.getElementById("signaler_id_button").style.display = "none";
    };



    
   


})();