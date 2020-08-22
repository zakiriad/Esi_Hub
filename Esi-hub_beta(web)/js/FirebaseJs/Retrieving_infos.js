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
    EtudiantReference.child("date_de_naissance").once("value", snap=> birthday.innerHTML = snap.val());

    var birthplace = document.getElementById("birthplace");
    EtudiantReference.child("lieu_de_naissance").once("value", snap=> birthplace.innerHTML = snap.val());

    var wilaya = document.getElementById("wilaya");
    EtudiantReference.child("wilaya").once("value", snap=> wilaya.innerHTML = snap.val());

    var Promotion = document.getElementById("promotion");
    EtudiantReference.child("promotion").once("value", snap=> Promotion.innerHTML = snap.val());

    var Niveau = document.getElementById("niveau_etude");
    EtudiantReference.child("niveau").once("value", snap=> Niveau.innerHTML = snap.val());

    var carte_button = document.getElementById("carte_etudiant_download");
    carte_button.onclick = function(){
        EtudiantReference.child("lien_carte_etudiant").on("value", function(snap){
            if((snap.val() != null)&&(snap.val() != "")){
                var user = localStorage.getItem("user_id");
                var storageRef = firebase.storage().ref().child(user);
                storageRef.child("carte_etudiant").getDownloadURL().then(function(url) {
                    var xhr = new XMLHttpRequest();
                    xhr.responseType = 'blob';
                    xhr.onload = function(event) {
                    var blob = xhr.response;

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


    var projet_button = document.getElementById("project_downloads");
    projet_button.onclick = function(){
        EtudiantReference.child("lien_Projet").on("value", function(snap){
            if((snap.val() != "")&&(snap.val() != null)){
                var user = localStorage.getItem("user_id");
                var storageRef= firebase.storage().ref().child(user);
                alert(user);
                storageRef.child("Projet").getDownloadURL().then(function(url) {
                    var xhr = new XMLHttpRequest();
                    xhr.responseType = 'blob';
                    xhr.onload = function(event) {
                    var blob = xhr.response;

                    var blob = xhr.response;
                    var a = document.createElement("a");
                    document.body.appendChild(a);
                    var url = window.URL.createObjectURL(blob);
                    a.href = url;
                    a.download = "Projet_"+user;
                    a.click();
                    window.URL.revokeObjectURL(url);


                    };
                    xhr.open('GET', url);
                    xhr.send();

                    
                }).catch(function(error) {
                });
            }else{
                alert("pas encorer uploader");
            }
        });


    };

    var memoire_button = document.getElementById("memoire_downloads");
    memoire_button.onclick = function(){
        EtudiantReference.child("lien_Memoire").on("value", function(snap){
            if((snap.val() != null) &&(snap.val() != "")){
                var user = localStorage.getItem("user_id");
                var storageRef= firebase.storage().ref().child(user);
                alert(user);
                storageRef.child("Memoire").getDownloadURL().then(function(url) {
                    var xhr = new XMLHttpRequest();
                    xhr.responseType = 'blob';
                    xhr.onload = function(event) {
                    var blob = xhr.response;

                    var blob = xhr.response;
                    var a = document.createElement("a");
                    document.body.appendChild(a);
                    var url = window.URL.createObjectURL(blob);
                    a.href = url;
                    a.download = "Memoire_"+user;
                    a.click();
                    window.URL.revokeObjectURL(url);


                    };
                    xhr.open('GET', url);
                    xhr.send();

                    
                }).catch(function(error) {
                });
            }else{
                alert("pas encorer uploader");
            }
        });

    };
    EtudiantReference.child("Verifications").child("id").on("value", function(snap){
        localStorage.setItem("id", snap.val());
        if(snap.val() != null){
            if(snap.val()){
                document.getElementById("signaler_id_button").style.display = "none";
                document.getElementById("id_signale_span").style.display = "none";
                document.getElementById("Valider_id_button").style.display = "none";
                document.getElementById("id_valide_span").style.display = "";
            }else{
                document.getElementById("id_valide_span").style.display = "none";
                document.getElementById("Valider_id_button").style.display = "";
                document.getElementById("id_signale_span").style.display = "";
                document.getElementById("signaler_id_button").style.display = "none";

            }
            
        };
    });
    EtudiantReference.child("Verifications").child("Documents").on("value", function(snap){
        localStorage.setItem('Docs', snap.val());
        if(snap.val() != null){
            
            if(snap.val()){
                document.getElementById("projet_valide_span").style.display = "";
                document.getElementById("memoire_valide_span").style.display = "";
                document.getElementById("Valider_Docs_button").style.display = "none";
                document.getElementById("projet_signale_span").style.display = "none";
                document.getElementById("memoire_signale_span").style.display = "none";
                document.getElementById("signaler_Docs_button").style.display = "none";

            }else{
                document.getElementById("projet_valide_span").style.display = "none";
                document.getElementById("memoire_valide_span").style.display = "none";
                document.getElementById("Valider_Docs_button").style.display = "";
                document.getElementById("projet_signale_span").style.display = "";
                document.getElementById("memoire_signale_span").style.display = "";
                document.getElementById("signaler_Docs_button").style.display = "none";
            }
        };
    });


    var valider_id_button = document.getElementById("Valider_id_button");
    valider_id_button.onclick = function(){
        localStorage.setItem('id', true);
        if(localStorage.getItem('Docs') === "null"){
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
            });
        }else{
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
                Documents: localStorage.getItem('Docs') === 'true'
            });
        }
        
        document.getElementById("id_valide_span").style.display = "";
        document.getElementById("Valider_id_button").style.display = "none";
        document.getElementById("signaler_id_button").style.display = "none";
        document.getElementById("id_signale_span").style.display = "none";

    
    };
    
    var Signaler_id_button = document.getElementById("signaler_id_button");
    Signaler_id_button.onclick = function(){
        localStorage.setItem('id', false);
        if(localStorage.getItem('Docs') === "null"){
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
            });
        }else{
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
                Documents: localStorage.getItem('Docs') === 'true'
            });
        }
        document.getElementById("id_signale_span").style.display = "";
        document.getElementById("signaler_id_button").style.display = "none";
        document.getElementById("id_valide_span").style.display = "none";
        document.getElementById("Valider_id_button").style.display = "";
    };




    var valider_Docs_button = document.getElementById("Valider_Docs_button");
    valider_Docs_button.onclick = function(){
        localStorage.setItem('Docs', true);
        if(localStorage.getItem('id') === "null"){
            EtudiantReference.child("Verifications").set({
                Documents: localStorage.getItem('Docs') === 'true',
            });
        }else{
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
                Documents: localStorage.getItem('Docs') === 'true'
            });
        }
        document.getElementById("projet_valide_span").style.display = "";
        document.getElementById("memoire_valide_span").style.display = "";
        document.getElementById("Valider_Docs_button").style.display = "none";
        document.getElementById("projet_signale_span").style.display = "none";
        document.getElementById("memoire_signale_span").style.display = "none";
        document.getElementById("signaler_Docs_button").style.display = "none";

    };

    var Signaler_Docs_button = document.getElementById("signaler_Docs_button");
    Signaler_Docs_button.onclick = function(){
        localStorage.setItem('Docs', false);
        if(localStorage.getItem('id') === "null"){
            EtudiantReference.child("Verifications").set({
                Documents: localStorage.getItem('Docs') === 'true',
            });
        }else{
            EtudiantReference.child("Verifications").set({
                id: localStorage.getItem('id') === 'true',
                Documents: localStorage.getItem('Docs') === 'true'
            });
        }
        document.getElementById("projet_valide_span").style.display = "none";
        document.getElementById("memoire_valide_span").style.display = "none";
        document.getElementById("Valider_Docs_button").style.display = "";
        document.getElementById("projet_signale_span").style.display = "";
        document.getElementById("memoire_signale_span").style.display = "";
        document.getElementById("signaler_Docs_button").style.display = "none";

    };
    


})();