// Like and dislike Condition check of onclick of like button
function liked(productid, userid) {
   var likeChange = document.getElementById(`likeChange${productid}`);
   var likeTextChange = document.getElementById(`likeTextChange${productid}`); 
   
   if ( likeChange.classList.contains("bi-hand-thumbs-up") || likeTextChange.classList.contains("btn-outline-primary")){
    $.ajax({
    type:"POST",
     url:`http://localhost:8080/bishnu/user/like/${productid}/${userid}`,
     success: function (){
            let c = $(`.like-count${productid}`).html();
            c++;
            $(`.like-count${productid}`).html(c);
            likeChange.classList.remove("bi-hand-thumbs-up");
            likeChange.classList.add("bi-hand-thumbs-down");
            likeTextChange.classList.remove("btn-outline-primary");
            likeTextChange.classList.add("btn-outline-secondary");    
    }
})
} else {
    $.ajax({
     type:"POST",
     url:`http://localhost:8080/bishnu/user/dislike/${productid}/${userid}`, 
     success: function (){
        let c = $(`.like-count${productid}`).html();
        c--;
        $(`.like-count${productid}`).html(c);
        likeChange.classList.remove("bi-hand-thumbs-down");
        likeChange.classList.add("bi-hand-thumbs-up");
        likeTextChange.classList.remove("btn-outline-secondary");
        likeTextChange.classList.add("btn-outline-primary"); 
        }
    })    
}
}

/* open comment Box click of comment */
function clickCommentBox(productid){
    let commentBox = document.getElementById(`commentBoxProduct${productid}`);
    commentBox.style.display = 'block';
}

/* comment area */
const commentForm = document.getElementById('comment-form');
const commentInput = document.getElementById('comment-input');
const commentList = document.getElementById('comment-list');
commentForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const comment = commentInput.value;
  if (comment !== '') {
    const li = document.createElement('li');
    li.textContent = comment;
    commentList.appendChild(li);
    commentInput.value = '';
    const productid = document.getElementById('product').value;
    const userid = document.getElementById('user').value;
      console.log(productid, userid);
     $.ajax({
     type:"POST",
     url:`http://localhost:8080/bishnu/user/comment/${productid}/${userid}`, 
     data: { comment: comment },
     success: function (){
        console.log(comment);
        }
    }) 
  }
});


