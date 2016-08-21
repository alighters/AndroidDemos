var http = require('http');
var url = require('url');
var querystring = require('querystring');

http.createServer(function (request, response) {

   // 发送 HTTP 头部 
   // HTTP 状态值: 200 : OK
   // 内容类型: text/plain
   response.writeHead(200, {'Content-Type': 'text/plain'});

   var pathname = url.parse(request.url).pathname;
   if (pathname == "/get_token" || pathname == "/refresh_token"){
      // get a new token or refresh the token
      var result = {
         "success" : true,
         "data" : {
            "token" : new Date().getTime().toString()
         }
      }
      response.end(JSON.stringify(result));
   }else if (pathname == "/request"){
      // Normal request
      var token_str = querystring.parse(url.parse(request.url).query)['token'];
      if (token_str){
         var token_time = parseFloat(token_str);
         var cur_time = new Date().getTime();
         if(cur_time - token_time < 30 * 1000){
            var result = {
               "success" : true,
               "data" : {
                  "result" : true
               }
            }
            response.end(JSON.stringify(result)); 
         }else{
            response.end(JSON.stringify({"success": false, "error_code" : 1001})); 
         }
      } else {
         response.end(JSON.stringify({"success": false, "error_code" : 1000})); 
      }
   }

}).listen(8888);

// 终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/');
