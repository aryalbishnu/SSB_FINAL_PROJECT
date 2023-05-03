

 function deleteBy(id){
      
      
    swal({
          title: 'Are you sure?',
          text: "You won't be able to revert this!",
          icon: 'warning',
         buttons: true,
         dangerMode:true,
        })
        .then((result) => {
          if (result) {
         window.location="/bishnu/user/delete/"+id;
          swal("your file is deleted");
          }else{
            swal("your file is save");
          }
        });  
  }
  