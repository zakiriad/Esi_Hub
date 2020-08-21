
(function(){
    var EtudiantsReference = firebase.database().ref().child('Liste_Etudiants');
    EtudiantsReference.once("value", function(snapshot){
        snapshot.forEach(function(child){
            alert(child.key);
            EtudiantsReference.child(child.key).child('niveau').once("value", function(snap){
                if(snap.val() == 5){
                    var table = document.getElementById("Liste_Etudiants_Tab_biblio");
                    var New_row = table.insertRow(-1);


                    var user_email = New_row.insertCell(0);
                    var user_id = New_row.insertCell(1);
                    var user_carte_etudiant = New_row.insertCell(2);
                    var user_Nom= New_row.insertCell(3);
                    var user_Prenom = New_row.insertCell(4);
                    var user_Specialite = New_row.insertCell(5);
                    var user_validate_checkbox = New_row.insertCell(6);
                    EtudiantsReference.child(child.key).child('email').on('value', snap=> user_email.innerHTML = snap.val());
                    user_id.innerHTML = child.key;
                    
                    
                    EtudiantsReference.child(child.key).child('nom').on('value', snap=>   user_Nom.innerHTML = snap.val());
                    EtudiantsReference.child(child.key).child('prenom').on('value', snap=>  user_Prenom.innerHTML = snap.val());
                    EtudiantsReference.child(child.key).child('specialite').on('value', snap=>  user_Specialite.innerHTML = snap.val());
                    EtudiantsReference.child(child.key).child("Numero_Carte_Etudiant").on("value", snap => user_carte_etudiant.innerHTML = snap.val());
                    var validate = false;
                    var check = document.createElement("button");
                    var text_check = document.createTextNode("Valider");
                    check.appendChild(text_check);
                    check.className = "btn btn-round btn-info";
                    check.onclick = function(){
                        localStorage.setItem("user_id", child.key);
                        EtudiantsReference.child(localStorage.getItem("user_id")).child("Verifications").child("livres").set({
                            livres : true
                        });
                        user_validate_checkbox.removeChild(user_validate_checkbox.lastElementChild);

                        var checked = document.createElement("SPAN");
                        var text_checked = document.createTextNode("Validé");
                        checked.appendChild(text_checked);
                        checked.className = "label label-success"
                        user_validate_checkbox.appendChild(checked);
                    };

                    var checked = document.createElement("SPAN");
                    var text_checked = document.createTextNode("Validé");
                    checked.appendChild(text_checked);
                    checked.className = "label label-success"
 
                    
                    EtudiantsReference.child(child.key).child("Verifications").child("livres").once("value", function(snap){
                        if(snap.val() == true){
                            validate = true;
                        }
                    });
                    if(validate){
                        user_validate_checkbox.appendChild(checked);
                    }else{
                        user_validate_checkbox.appendChild(check);
                    }
                    

                }
            });

        });
    });
})();