
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

/* comment area add comment */
function comment(button){
var userString = button.dataset.user;

  // get userid
  var startIndex = userString.indexOf("id=") + 3;
  var endIndex = userString.indexOf(",");
  var userid = userString.substring(startIndex, endIndex);
  // get image from user
  var startImage = userString.indexOf("image=") + 6;
  var endImage = userString.indexOf(")");
  var image = userString.substring(startImage, endImage);
  // get firstName from user
  var startFirstName = userString.indexOf("firstName=") + 10;
  var endFirstName = userString.indexOf(",", startFirstName);
  var firstName = userString.substring(startFirstName, endFirstName);
  firstName = firstName.charAt(0) + firstName.slice(1).toLowerCase();
  // get lastName from user
  var startLastName = userString.indexOf("lastName=") + 9;
  var endLastName = userString.indexOf(",", startLastName);
  var lastName = userString.substring(startLastName, endLastName);
  lastName = lastName.charAt(0) + lastName.slice(1).toLowerCase();

var productid = button.dataset.productid;
const commentInput = $(`.comment-input${productid}`);
const commentList = $(`.comment-list${productid}`);

  const comment = commentInput.val();
  if (comment !== '') {  
   
     $.ajax({
     type:"POST",
     url:`http://localhost:8080/bishnu/user/comment/insert/${productid}/${userid}`, 
     data: { comment: comment},
     success: function (response){
        // div create 
   var mainDiv = document.createElement('div');
   mainDiv.id = `product${productid}comment${response.commentid}`;
        // div create 
   var div = document.createElement('div');
   div.classList.add('comment_pic_div')
   div.id = `commentBox${response.commentid}`;
    // div create 
   var div1 = document.createElement('div');
   div1.classList.add('comment-picture');
   // image tag create
   var img = document.createElement('img');
   img.src = '/img/bishnu/'+image;
   img.alt = 'profile photo';
   img.classList.add('comment_picture');
   div1.appendChild(img);
   div.appendChild(div1);
   //div create
   var div2 = document.createElement('div');
   div2.classList.add('comment-name-text');
   div2.id = `commentHide${response.commentid}`;
   // p element create
   var p1 = document.createElement('p');
   p1.classList.add('comment-name');
   p1.textContent = firstName + ' ' + lastName;
   div2.appendChild(p1);
   // p element create
   var p2 = document.createElement('p');
   p2.classList.add('comment-text');
   p2.id = `commentText${response.commentid}`;
   p2.textContent = comment;
   div2.appendChild(p2);
   div.appendChild(div2);
   // p element create
   var p3 = document.createElement('p');
   p3.classList.add('edit-delete', 'text-primary');
   p3.id = `edit-delete${response.commentid}`;
   // span elemet create
   var span1 = document.createElement('span');
   span1.classList.add('time');
   span1.textContent = response.time;
   p3.appendChild(span1);
   // span elemet create
   var span2 = document.createElement('span');
   span2.classList.add('edit');
   span2.id = `productCommentEdit${response.commentid}`;
   span2.addEventListener('click', function() {
    editComment(response.commentid, response.productid);
   });
   span2.textContent = 'edit';
   p3.appendChild(span2);
   // span elemet create
   var span3 = document.createElement('span');
   span3.classList.add('delete');
   span3.id = `productCommentDelete${response.commentid}`;
   span3.addEventListener('click', function() {
    deleteComment(response.commentid, response.userid, response.productid);
   });
   span3.textContent = 'delete';
   p3.appendChild(span3);
    mainDiv.append(div);
    mainDiv.append(p3)
    
    commentList.append(mainDiv);
    // comment success after comment box is empty;
    commentInput.val('');
        let c = $(`.comment-count${productid}`).html();
           c++;
            $(`.comment-count${productid}`).html(c);
        }
    }) 
  }
}

// delete comment by comment user and admin only
function deleteComment(commentid, userid, productid){
    swal({
  title: "Are you sure?",
  text: "Once deleted, you will not be able to recover this comment!",
  icon: "warning",
  buttons: true,
  dangerMode: true,
  
})
.then((willDelete) => {
  if (willDelete) {
    swal("Poof! Your comment has been deleted!", {
      icon: "success",
    });
    $.ajax({
    type:"POST",
     url:`http://localhost:8080/bishnu/user/delete/${commentid}/${userid}`,
     success: function (){
           // comment is delete of comment area
           $(`#product${productid}comment${commentid}`).html('');
           //count comment is less
           let c = $(`.comment-count${productid}`).html();
           c--;
            $(`.comment-count${productid}`).html(c);
    }
})  
  } else {
    swal("Your comment has been safe!");
  }
});
   
}

// update comment only by particular comment user
function editComment(commentid, productid){

  // div create 
   var div = document.createElement('div');
   div.id = `editComment${commentid}`;
   var textarea = document.createElement('textarea');
   textarea.id= `commentTextArea${commentid}`;
   textarea.rows = 4;
   textarea.cols = 50;
   // get comment of particular comment and set to textarea
   var commentText = document.getElementById(`commentText${commentid}`).textContent;

   textarea.textContent = commentText;
   div.append(textarea);
   var cancel = document.createElement('button');
   cancel.classList.add('btn', 'bg-warning', 'updateCommentBtn');
   cancel.type = 'reset'
   cancel.textContent = 'cancel';
   cancel.addEventListener('click', function() {
    cancelComment(commentid);
   });
   var update = document.createElement('button');
   update.classList.add('btn','bg-primary');
   update.type = 'submit';
   update.textContent = 'submit';
   update.addEventListener('click', function() {
    updateComment(productid, commentid);
   });
   div.append(cancel);
   div.append(update);
   // target of comment area 
    var commentArea = document.getElementById(`commentHide${commentid}`)
    var edit_delete = document.getElementById(`edit-delete${commentid}`);
    // display hide of comment 
    commentArea.style.display ='none';
    edit_delete.style.display = 'none';
    // target of new comment tex box set
    var commentBox = document.getElementById(`commentBox${commentid}`);
    commentBox.append(div);
   
}

// cancel button click in comment update time 
function cancelComment(commentid){
    // commentBox is delete
    var commentBox = document.getElementById(`editComment${commentid}`);
    commentBox.remove();
    // old comment area display is show 
    var commentArea = document.getElementById(`commentHide${commentid}`);
    commentArea.style.display = 'block';
    var edit_delete = document.getElementById(`edit-delete${commentid}`);
    edit_delete.style.display = 'block';
}

// submit button click in comment update time
function updateComment(productid, commentid){
  const commentInput = $(`#commentTextArea${commentid}`);
  const comment = commentInput.val();
  if (comment !== '') {  
    $.ajax({
    type:"POST",
     url:`http://localhost:8080/bishnu/user/comment/update/${productid}/${commentid}`,
     data: { comment: comment},
     success: function (){
      // commentBox is delete
    var commentBox = document.getElementById(`editComment${commentid}`);
    commentBox.remove();
    // old comment area display is show 
    var commentArea = document.getElementById(`commentHide${commentid}`);
    commentArea.style.display = 'block';
    var edit_delete = document.getElementById(`edit-delete${commentid}`);
    edit_delete.style.display = 'block';
    // set new comment
    var newComment = document.getElementById(`commentText${commentid}`);
    newComment.textContent = comment;     
    }
}) 
} else {
    alert("please enter a comment..");
}
}