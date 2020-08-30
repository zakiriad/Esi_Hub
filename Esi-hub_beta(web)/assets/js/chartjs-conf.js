var Script = function () {
    var speci_etudiant= [0, 0];
    var nbr_Etudiant = [0,0,0,0,0];
    var nbr_etudiants = 0;

    function getData(){
        var Query_Etudiant_5 = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(5).once("value",function(snap){
            nbr_Etudiant[4] = snap.numChildren();
        });

        var Query_Etudiant_4 = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(4).once("value",function(snap){
            nbr_Etudiant[3] = snap.numChildren();
            
        });
        var Query_Etudiant_3 = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(3).once("value",function(snap){
            nbr_Etudiant[2] = snap.numChildren();
        
        });
        var Query_Etudiant_2 = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(2).once("value",function(snap){
            nbr_Etudiant[1] = snap.numChildren();
        
        });
        var Query_Etudiant_1 = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(1).once("value",function(snap){
            nbr_Etudiant[0] = snap.numChildren();
        
        });

        var Query_Etudiant_ISI = firebase.database().ref().child("Liste_Etudiants").orderByChild("specialite").equalTo("ISI").once("value",function(snap){
            speci_etudiant[0] = snap.numChildren();
        
        });
        var Query_Etudiant_SIW = firebase.database().ref().child("Liste_Etudiants").orderByChild("specialite").equalTo("SIW").once("value",function(snap){
            speci_etudiant[1]= snap.numChildren();
            
        });
        
          
        
    }
    getData();
    setTimeout(function(){
        document.getElementById("nbr_users_stats").innerHTML = nbr_etudiants;
        alert("hello");
        var pieData = [
            {
                value: speci_etudiant[0],
                color:"#1abc9c"
            },
            {
                value : speci_etudiant[1],
                color : "#16a085"
            }

        ];
        var doughnutData = [
            {
                value: nbr_Etudiant[0],
                color:"#1abc9c"
            },
            {
                value : nbr_Etudiant[1],
                color : "#2ecc71"
            },
            {
                value : nbr_Etudiant[2],
                color : "#3498db"
            },
            {
                value : nbr_Etudiant[3],
                color : "#9b59b6"
            },
            {
                value : nbr_Etudiant[4],
                color : "#34495e"
            }
        ];
        new Chart(document.getElementById("doughnut").getContext("2d")).Doughnut(doughnutData);
        new Chart(document.getElementById("pie").getContext("2d")).Pie(pieData);
    }, 2000);
}();