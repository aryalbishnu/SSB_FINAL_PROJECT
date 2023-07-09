
const searchBy = ()=>{
let query = $("#search-input").val();

if(query==""){

 $(".search-result").hide();
}else{

//sending request to server
let url=`http://localhost:8080/search/${query}`;
fetch(url).then((Response) =>{
return Response.json();
})
.then((data) =>{
//data....
if (data == null || data.length === 0) {
    $(".search-result").hide();
        } else {
let text=`<div class="list-group">`
data.forEach((bishnuEntity) => {
    text+=`<a href='/bishnu/user/update/${bishnuEntity.id}' class='list-group-item list-group-action'>${bishnuEntity.firstName}</a>`
});

text+= `</div>`

$(".search-result").html(text);
$(".search-result").show();
}
})


}

}