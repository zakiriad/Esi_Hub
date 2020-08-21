(function(){
var EtudiantReferences = firebase.database().ref().child('Liste_Etudiants');
EtudiantReferences.once("value", function(snapshot){
    snapshot.forEach(function(child){
        EtudiantReferences.child(child.key).child('niveau').on("value",function(snap){
            if(snap.val() == localStorage.getItem("niveau")){
                var table = document.getElementById("Liste_Etudiants_Tab_"+localStorage.getItem("niveau"));
                var New_row = table.insertRow(-1);


                var user_email = New_row.insertCell(0);
                var user_id = New_row.insertCell(1);
                var user_Nom= New_row.insertCell(2);
                var user_Prenom = New_row.insertCell(3);
                var user_Specialite = New_row.insertCell(4);
                var user_Details_button = New_row.insertCell(5);
                
                EtudiantReferences.child(child.key).child('email').on('value', snap=> user_email.innerHTML = snap.val());
                user_id.innerHTML = child.key;
                
                
                EtudiantReferences.child(child.key).child('nom').on('value', snap=>   user_Nom.innerHTML = snap.val());
                EtudiantReferences.child(child.key).child('prenom').on('value', snap=>  user_Prenom.innerHTML = snap.val());
                EtudiantReferences.child(child.key).child('specialite').on('value', snap=>  user_Specialite.innerHTML = snap.val());            

                var Details_button = document.createElement("button");
                var button_text = document.createTextNode('Details');
                Details_button.appendChild(button_text);
                Details_button.onclick = function(){
                    if(localStorage.getItem("niveau") == 5){
                        window.open("Details_Etudiant_3SC.html");
                        localStorage.setItem("user_id",child.key);
                    }
                    else{
                        window.open("Details_Etudiant.html");
                        localStorage.setItem("user_id",child.key);
                    }
                    
                };
                Details_button.className = "btn btn-round btn-info";

                user_Details_button.appendChild(Details_button);

            }
        });
    });


});
})();