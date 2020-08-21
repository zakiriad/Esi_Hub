var Script = function () {
    var nbr_etudiant_1 = 0;
    var nbr_etudiant_2 = 0;
    var nbr_etudiant_3 = 0;
    var nbr_etudiant_4 = 0;
    var nbr_etudiant_5 = 0;
    var nbr_etudiant_SIW = 0;
    var nbr_etudiant_ISI = 0;
    var nbr_etudiants = 0; 
    var Etudiants = firebase.database().ref().child("Liste_Etudiants");
    Etudiants.on("value",function(snapShot){
        snapShot.forEach(function(child){
            Etudiants.child(child.key).child("niveau").once("value", function(snap){
                nbr_etudiants += 1;
                switch(snap.val()){
                    case 1:
                        nbr_etudiant_1 = nbr_etudiant_1 + 1;
                        break;
                    case 2:
                        nbr_etudiant_2 = nbr_etudiant_2 + 1;
                        break;
                    case 3:
                        nbr_etudiant_3 = nbr_etudiant_3 + 1;
                        break;
                    case 4:
                        Etudiants.child(child.key).child("specialite").on("value", function(snap){
                            if(snap.val() !== null){
                                if(snap.val() == "SIW"){nbr_etudiant_SIW += 1;}
                                else{nbr_etudiant_ISI += 1;}
                            }
                        });
                        nbr_etudiant_4 = nbr_etudiant_4 + 1;
                        break;
                    case 5:
                        Etudiants.child(child.key).child("specialite").on("value", function(snap){
                            if(snap.val() !== null){
                                if(snap.val() == "SIW"){ nbr_etudiant_SIW += 1;}
                                else{ nbr_etudiant_ISI =+ 1;}
                            }
                        });
                        nbr_etudiant_5 = nbr_etudiant_5 + 1;
                        break;
                };
                
                
            });

        });
        setData();
        
    });
    
    
    function setData(){
        document.getElementById("nbr_users_stats").innerHTML = nbr_etudiants;
        var pieData = [
            {
                value: nbr_etudiant_ISI,
                color:"#1abc9c"
            },
            {
                value : nbr_etudiant_SIW,
                color : "#16a085"
            }

        ];
        var doughnutData = [
            {
                value: nbr_etudiant_1,
                color:"#1abc9c"
            },
            {
                value : nbr_etudiant_2,
                color : "#2ecc71"
            },
            {
                value : nbr_etudiant_3,
                color : "#3498db"
            },
            {
                value : nbr_etudiant_4,
                color : "#9b59b6"
            },
            {
                value : nbr_etudiant_5,
                color : "#34495e"
            }
        ];
        new Chart(document.getElementById("doughnut").getContext("2d")).Doughnut(doughnutData);
        new Chart(document.getElementById("pie").getContext("2d")).Pie(pieData);
    }


}();