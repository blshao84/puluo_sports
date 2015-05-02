var sha256 = require("crypto-js/sha256");
var http = require('http');
var url = require('url');  
var util = require('util');  
var server = http.createServer(function(req, res) {
    console.log(util.inspect(url.parse(req.url,true)));
    res.writeHead(200,{'Content-Type':'text/plain'});  
    var key = url.parse(req.url,true).query.key;
    var sec = sha256(key).toString();
    res.end(sec);
});
server.listen(3333);
