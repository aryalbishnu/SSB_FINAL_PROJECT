
function addressSearch(){
    
    let api = 'https://zipcloud.ibsnet.co.jp/api/search?zipcode=';
    let error = document.getElementById('zipCodeError');
    let input = document.getElementById('zipCode_field');
    let address1 = document.getElementById('address1_field');
    let address2 = document.getElementById('address2_field');
   // let address3 = document.getElementById('address3_field');
    let param = input.value.replace("-",""); //入力された郵便番号から「-」を削除
    let url = api + param;
    
    fetchJsonp(url, {
        timeout: 1000, //タイムアウト時間
    })
    .then((response)=>{
        error.textContent = ''; //HTML側のエラーメッセージ初期化
        return response.json();  
    })
    .then((data)=>{
        if(data.status === 400){ //エラー時
            error.textContent = data.message;
        }else if(data.results === null){
            error.textContent = '郵便番号から住所が見つかりませんでした。';
        } else {
            address1.value = data.results[0].address1.concat(" ",data.results[0].address2, "", data.results[0].address3);
            address2.value = data.results[0].kana1.concat(" ",data.results[0].kana2, "", data.results[0].kana3 ) ;
          //  address3.value = data.results[0].address3 ;
        }
    })
    .catch((ex)=>{ //例外処理
        console.log(ex);
    });
};