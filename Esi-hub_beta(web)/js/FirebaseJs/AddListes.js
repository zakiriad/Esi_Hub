(function(){
    var ListeRef = firebase.database().ref();

    document.getElementById("boutton_Ajout_Liste").onclick = function(){
        if(document.getElementById("Fichier_Liste").value != null){
            
            //
            var fr=new FileReader();
            fr.onload=function(){
                var Content = fr.result;
                var arrayEmails = Content.split("\n");
                arrayEmails.forEach((item, index) => {
                    var email = ListeRef.child(document.getElementById("Niveaux").value).push();
                    if(item.slice(0, - 1) !== "") email.set(item.slice(0, - 1));
                    
                    //Upload des emails
                });
            }
            fr.readAsText(document.getElementById("Fichier_Liste").files[0]);

       
            //
        }
        
    };


})();