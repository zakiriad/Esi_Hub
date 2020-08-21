(function(){        

    switch(localStorage.getItem("TypeAjout")){
        case "Formation":
            var AjoutButton = document.getElementById("boutton_Ajout_Formation");
            AjoutButton.onclick = function(){
                var FormationsReference = firebase.database().ref().child("Offres").child("Formations");
                var Nom = document.getElementById("Nom_Ajout_Formation");
                var Domaine = document.getElementById("Domaine_Ajout_Formation");
                var img = document.getElementById("img_Ajout_Formation");
                var etablissement = document.getElementById("etablissement_Ajout_Formation");
                var Datelimite = document.getElementById("DateLimite_Ajout_Formation");
                var Datedebut = document.getElementById("DateDebut_Ajout_Formation");
                
                //vérifier la validité des entrés:
                var valid = true;
                var upload_img = false;
                if(Nom.value == ""){ valid = false};
                if(Domaine.value == ""){ valid = false};
                if(etablissement.value == ""){ valid = false};
                if(Datedebut.value == ""){ valid = false};
                if(Datelimite.value == ""){ valid = false};
                if(img.value != ""){upload_img = true};


                if(valid && upload_img){
                    var storageRef = firebase.storage().ref().child("offres_imgs").child(img.files[0].name);
                    try{
                        var uploadTask = storageRef.put(img.files[0]);
                        uploadTask.on("state_changed", function(snapshot){

                        }, function(error){

                        }, function(){
                                uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL){
                                    FormationsReference.child(Nom.value).set({
                                        nom : Nom.value, 
                                        domaine : Domaine.value,
                                        etablissement : etablissement.value,
                                        datedebut : Datedebut.value,
                                        datelimiteinscrp : Datelimite.value,
                                        lien_image : img.value
                                    });
                                });                        
                        });
                        //Fin de l'upload
                    } catch(error){
                        console.log("error = "+ error);
                    }
                    
                    
                }else if(upload_img){
                    alert("vous avez laissé un champs vide");
                }else{
                    FormationsReference.child(Nom.value).set({
                        nom : Nom.value, 
                        domaine : Domaine.value,
                        etablissement : etablissement.value,
                        datedebut : Datedebut.value,
                        datelimiteinscrp : Datelimite.value,
                        lien_image : ""
                    });
                }
            };
            break;









        case "Job":
            var AjoutButton = document.getElementById("boutton_Ajout_Job");
            AjoutButton.onclick = function(){


                var jobsReference = firebase.database().ref().child("Offres").child("Jobs");
                var Titre = document.getElementById("Titre_Ajout_Job");
                var Societe = document.getElementById("Societe_Ajout_Job");
                var img = document.getElementById("img_Ajout_Job");
                var email = document.getElementById("email_Ajout_Job");
                var Experience = document.getElementById("Experience_Ajout_Job");
                //vérifier la validité des entrés:
                var valid = true;
                var upload_img = false;
                if(Societe.value == ""){ valid = false};
                if(Titre.value == ""){ valid = false};
                if(email.value == ""){ valid = false};
                if(Experience.value == ""){valid = false};
                if(img.value != ""){upload_img = true}

                //dans le cas ou il y a une image à importer
                if(valid && upload_img){
                    //Debut de l'upload + creation de la branche de l'offre
                    var storageRef = firebase.storage().ref().child("offres_imgs").child(img.files[0].name);
                    try{
                        var uploadTask = storageRef.put(img.files[0]);
                        uploadTask.on("state_changed", function(snapshot){

                        }, function(error){

                        }, function(){
                                uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL){
                                    jobsReference.child(Titre.value).set({
                                        nom : Titre.value, 
                                        societe : Societe.value,
                                        email : email.value,
                                        experience : Number(Experience.value),
                                        lien_logo : downloadURL
                                    });
                                });                        
                        });
                        //Fin de l'upload
                    } catch(error){
                        console.log("error = "+ error);
                    }

                    
                }else if(upload_img){
                    alert("vous avez laissé un champs vide");
                }else{
                    jobsReference.child(Titre.value).set({
                        nom : Titre.value, 
                        societe : Societe.value,
                        email : email.value,
                        experience : Number(Experience.value),
                        lien_logo : ""
                    });
                }
            };
            break;








        case "Actualite":
            var ActualitesReference = firebase.database().ref().child("Actualites");
            var Titre = document.getElementById("Titre_Ajout_Actualite");
            var SousTitre = document.getElementById("SousTitre_Ajout_Actualite");
            var img = document.getElementById("img_Ajout_Actualite");
            var lien = document.getElementById("lien_Ajout_Actualite");
            var AjoutButton = document.getElementById("boutton_Ajout_Actualite");
            
            
            AjoutButton.onclick = function(){
                //vérifier la validité des entrés:
                var valid = true;
                var upload_img = false;
                if(Titre.value == ""){ valid = false};
                if(SousTitre.value == ""){ valid = false};
                if(lien.value == ""){ valid = false};
                if(img.value != ""){upload_img = true}  
                if((valid)&&(upload_img)){
                var ActualiteKey = ActualitesReference.push().key;
                var storageRef = firebase.storage().ref().child("Actualite_imgs").child(img.files[0].name);
                try{
                    var uploadTask = storageRef.put(img.files[0]);
                    uploadTask.on("state_changed", function(snapshot){

                    }, function(error){

                    }, function(){
                            uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL){
                                ActualitesReference.child(ActualiteKey).set({
                                    titre : Titre.value, 
                                    soustitre : SousTitre.value,
                                    lien_photo : downloadURL,
                                    lien : lien.value,
                                });
                            });                        
                    });
                } catch(error){
                    console.log("error = "+ error);
                }
                }else if(upload_img){
                    alert("vous avez laissé un champs vide");
                }else{
                    var ActualiteKey = ActualitesReference.push().key;
                    ActualitesReference.child(ActualiteKey).set({
                        titre : Titre.value, 
                        soustitre : SousTitre.value,
                        lien_photo : "",
                        lien : lien.value,
                    });  
                }
            };
            break;
    }



})();