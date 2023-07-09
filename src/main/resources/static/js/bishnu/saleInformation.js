// pdf file generate
function generatePDF() {
    $.ajax({
        type: "POST",
        url: `http://localhost:8080/bishnu/user/saleList`,
        success: function(response) {

            // Create a new jsPDF instance
            const doc = new jspdf.jsPDF();
            const date = new Date();

            // Get day, month, and year
            const day = date.getDate().toString().padStart(2, '0');
            const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month starts from 0
            const year = date.getFullYear().toString();

            // Get hours and minutes
            const hours = date.getHours().toString().padStart(2, '0');
            const minutes = date.getMinutes().toString().padStart(2, '0');

            // Create the formatted date-time string
            const formattedDateTime = `${day}-${month}-${year} ${hours}:${minutes}`;

            //doc.text(formattedDateTime);
            // Add content to the PDF

            doc.setFontSize(8); // Set font size to 16
            const textOffset = doc.internal.pageSize.getWidth() - 10; // Adjust the offset as needed
            doc.text(formattedDateTime, textOffset, 10, { align: 'right', encoding: 'UTF-8' });
            doc.setTextColor(0, 0, 255); // Set text color to blue (RGB values)
            doc.setFontSize(18)
            doc.text('Total Sale of SSB Company', 65, 20);
            //var dataArray = JSON.parse(saleList);
            const header = ['Sale Id', 'Product Name', 'Product Brand', 'Product Price', 'Sale Date', 'User Id', 'Product Id'];
            const tbody = [];
            // Extract detailed data for each row
            for (var i = 0; i < response.length; i++) {
                const saleData = response[i];
                const rowData = [
                    saleData.saleId,
                    saleData.saleName,
                    saleData.productBrand,
                    saleData.salePrice,
                    saleData.saleDate,
                    saleData.userId,
                    saleData.productId,
                    
                ];
                tbody.push(rowData);
            }

            const data = [header, ...tbody];

            doc.autoTable({
                head: [data[0]], // Table header row
                body: data.slice(1), // Table data (rows)
                startY: 30 // Y position to start the table
            });

            // Save the PDF as a file
            doc.save('sample.pdf');
        }
    })
}

// csv file generate
function downloadCSV() {
    $.ajax({
        type: "POST",
        url: `http://localhost:8080/bishnu/user/saleList`,
        success: function(response) {
            var header = ['\uFEFFSale Id', 'Product Name', 'Product Brand', 'Product Price', 'Sale Date', 'User Id', 'Product Id'];
            const tbody = [];
            // Extract detailed data for each row
            for (var i = 0; i < response.length; i++) {
                const saleData = response[i];
                const rowData = [
                    saleData.saleId,
                    saleData.saleName,
                    saleData.productBrand,
                    saleData.salePrice,
                    saleData.saleDate,
                    saleData.userId,
                    saleData.productId
                ];
                tbody.push("\n" + rowData);
            }
            const data = [header, ...tbody];
            var fileName = "totalSale.csv";
            var blob = new Blob([data], { type: 'text/csv;charset=utf-8;' });
            var url = URL.createObjectURL(blob);
            var link = document.createElement('a');
            link.href = url;
            link.download = fileName;
            link.click();
        }
    })
}

// search of sale
const saleSearchBy = ()=>{
let query = $("#search-input").val();

if(query==""){

 $(".search-result").hide();
}else{

//sending request to server
let searchCondition;
  radioButtonChange(function (selectedValue) {
  searchCondition = selectedValue;
}); 
let url=`http://localhost:8080/bishnu/user/saleSearch/${query}`;
fetch(url, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(searchCondition)
})
  .then((response) => {
    return response.json();
  })
.then((data) =>{
//data....
if (data == null || data.length === 0) {
    $(".search-result").hide();
        } else {
let text=`<div class="table-responsive" >
            <table class="table table-hover">
            <thead class="text-start" style="color: white; background-color: blue;">
              <tr>
                <th scope="col">Sale Id</th>
                <th scope="col">Product Name</th>
                <th scope="col">Product Brand</th>
                <th scope="col">Product Price</th>
                <th scope="col">Sale Date</th>
                <th scope="col">User Id</th>
                <th scope="col">Product Id</th>
              </tr>
            </thead>
          <tbody>`
data.forEach((saleData) => {
    text+=`<tr class="text-start" onclick="saleIdHover('${saleData.sale_Id}')" >
             <td scope="row">${saleData.sale_Id}</td>
             <td scope="row">${saleData.sale_Name}</td>
             <td scope="row">${saleData.product_brand}</td>
             <td scope="row">${saleData.sale_Price}</td>
             <td scope="row">${saleData.sale_Date}</td>
             <td scope="row">${saleData.user_Id}</td>
             <td scope="row">${saleData.product_Id}</td>
           </tr>`
});
text+= `</tbody>
        </table>
        </div>`

$(".search-result").html(text);
$(".search-result").show();



}

})

}
}
// click after result div is hide scroll to particular porduct color change
function saleIdHover(sale_Id){
    $(".search-result").hide();
   var saleId = document.getElementById(`sale${sale_Id}`);
   var height = saleId.offsetHeight + 100;
   var siblings = saleId.parentNode.children;
  for (var i = 0; i < siblings.length; i++) {
    siblings[i].style.backgroundColor = "white";
  }
    saleId.style.backgroundColor="#bde2e9";
    //saleId.scrollIntoView({ behavior: 'smooth' });
    scrollToTopSale(height);
}

function scrollToTopSale(height) {
  window.scrollTo({
    top: height,
    behavior: 'smooth'
  });
}

function radioButtonChange(callback) {
  const radioButtons = document.getElementsByName('exampleRadios');
  let selectedValue;
  radioButtons.forEach(function (radioButton) {
      if (radioButton.checked) {
         selectedValue = radioButton.value;
        callback(selectedValue);
      }
    });
}






