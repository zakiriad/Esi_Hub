(function(){

    
    var user = localStorage.getItem("user_id");
    var storageRef= firebase.storage().ref();
    storageRef.child(user).child(localStorage.getItem("Document")).getDownloadURL().then(function(url) {
      var xhr = new XMLHttpRequest();
      xhr.responseType = 'blob';
      xhr.onload = function(event) {
        var blob = xhr.response;
        try{
          blob.response.setHeader("Access-Control-Allow-Origin", "*");
          blob.response.setHeader("Access-Control-Allow-Credentials", "true");
          blob.response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
          blob.response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        }catch(error){
          console.log(error);
        }
       var a = document.createElement("a");
        document.body.appendChild(a);
        var url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = localStorage.getItem("Document")+"_"+user;
        a.click();
        window.URL.revokeObjectURL(url);
  
      };
      xhr.open('GET',url);
      xhr.send();
      
    }).catch(function(error) {
      switch (error.code) {
        case 'storage/object-not-found':
          alert("object not found");
          break;
    
        case 'storage/unauthorized':
          alert("you don't have permission for this action");
          break;
        default:
          alert("unknown error");
          break;
      }
  });
  
  setTimeout(function(){
    window.location.replace("Details_Etudiant_3SC.html");
  }, 10000);
  })();