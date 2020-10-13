function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
}
function Export(Section){
    if(Section){
        var WorkBook = XLSX.utils.book_new();
        
        switch(Section){
            case "1CPI":
                WorkBook.Props = {
                    Title : "1CPI_DB",
                    Author : "Esi_Admin",
                };
                WorkBook.SheetNames.push("1CPI");
                var data = [["Nom","Prenom","Email","Genre","Date de naissance", "Lieu de naissance"]];
                var EtudiantsRef = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(1).once("value", function(snap){
                    snap.forEach(function(child) {
                        data.push([child.val().nom,
                            child.val().prenom,
                            child.val().email,
                            child.val().genre,
                            child.val().Date_de_naissance,
                            child.val().Lieu_de_naissance
                        ]);
                    });
                });
                setTimeout(function(){
                    var WorkSheet = XLSX.utils.aoa_to_sheet(data);
                    WorkBook.Sheets["3CS"] = WorkSheet;
    
                    var Workbout = XLSX.write(WorkBook, {bookType:"xlsx", type:"binary"});
    
                    saveAs(new Blob([s2ab(Workbout)],{type:"application/octet-stream"}), 'DB_3CS.xlsx');
                }, 2000);
                break;



            case "2CPI":
                WorkBook.Props = {
                    Title : "2CPI_DB",
                    Author : "Esi_Admin",
                };
                WorkBook.SheetNames.push("2CPI");
                
                var data = [["Nom","Prenom","Email","Genre","Date de naissance", "Lieu de naissance"]];
                var EtudiantsRef = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(2).once("value", function(snap){
                    snap.forEach(function(child) {
                        data.push([child.val().nom,
                            child.val().prenom,
                            child.val().email,
                            child.val().genre,
                            child.val().Date_de_naissance,
                            child.val().Lieu_de_naissance
                        ]);
                    });
                });
                setTimeout(function(){
                    var WorkSheet = XLSX.utils.aoa_to_sheet(data);
                    WorkBook.Sheets["3CS"] = WorkSheet;
    
                    var Workbout = XLSX.write(WorkBook, {bookType:"xlsx", type:"binary"});
    
                    saveAs(new Blob([s2ab(Workbout)],{type:"application/octet-stream"}), 'DB_3CS.xlsx');
                }, 2000);
                break;


            case "1CS":
                WorkBook.Props = {
                    Title : "1CS_DB",
                    Author : "Esi_Admin",
                };
                WorkBook.SheetNames.push("1CS");
                
                var data = [["Nom","Prenom","Email","Genre","Date de naissance", "Lieu de naissance"]];
                var EtudiantsRef = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(3).once("value", function(snap){
                    snap.forEach(function(child) {
                        data.push([child.val().nom,
                            child.val().prenom,
                            child.val().email,
                            child.val().genre,
                            child.val().Date_de_naissance,
                            child.val().Lieu_de_naissance
                        ]);
                    });
                });
                setTimeout(function(){
                    var WorkSheet = XLSX.utils.aoa_to_sheet(data);
                    WorkBook.Sheets["1CS"] = WorkSheet;
    
                    var Workbout = XLSX.write(WorkBook, {bookType:"xlsx", type:"binary"});
    
                    saveAs(new Blob([s2ab(Workbout)],{type:"application/octet-stream"}), 'DB_1CS.xlsx');
                }, 2000);
                break;


            case "2CS":
                WorkBook.Props = {
                    Title : "2CS_DB",
                    Author : "Esi_Admin",
                };
                WorkBook.SheetNames.push("2CS");
                
                var data = [["Nom","Prenom","Email","Spécialité","Genre","Date de naissance", "Lieu de naissance"]];
                var EtudiantsRef = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(4).once("value", function(snap){
                    snap.forEach(function(child) {
                        data.push([child.val().nom,
                            child.val().prenom,
                            child.val().email,
                            child.val().Spécialité,
                            child.val().genre,
                            child.val().Date_de_naissance,
                            child.val().Lieu_de_naissance
                        ]);
                    });
                });
                setTimeout(function(){
                    var WorkSheet = XLSX.utils.aoa_to_sheet(data);
                    WorkBook.Sheets["2CS"] = WorkSheet;
    
                    var Workbout = XLSX.write(WorkBook, {bookType:"xlsx", type:"binary"});
    
                    saveAs(new Blob([s2ab(Workbout)],{type:"application/octet-stream"}), 'DB_2CS.xlsx');
                }, 2000);
                break;
                
            case "3CS":
                WorkBook.Props = {
                    Title : "3CS_DB",
                    Author : "Esi_Admin",
                };
                WorkBook.SheetNames.push("3CS");
                var data = [["Nom","Prenom","Email","Spécialité","Genre","Date de naissance", "Lieu de naissance"]];
                var EtudiantsRef = firebase.database().ref().child("Liste_Etudiants").orderByChild("niveau").equalTo(5).once("value", function(snap){
                    snap.forEach(function(child) {
                        data.push([child.val().nom,
                            child.val().prenom,
                            child.val().email,
                            child.val().Specialite,
                            child.val().genre,
                            child.val().Date_de_naissance,
                            child.val().Lieu_de_naissance
                        ]);
                    });
                });
                setTimeout(function(){
                    var WorkSheet = XLSX.utils.aoa_to_sheet(data);
                    WorkBook.Sheets["3CS"] = WorkSheet;
    
                    var Workbout = XLSX.write(WorkBook, {bookType:"xlsx", type:"binary"});
    
                    saveAs(new Blob([s2ab(Workbout)],{type:"application/octet-stream"}), 'DB_3CS.xlsx');
                }, 2000);
               
                break;    
        }
    }
};

document.getElementById("bouton_download_bd").onclick = function(){
        Export(document.getElementById("Section").value);
    
};