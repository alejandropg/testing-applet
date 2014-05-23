var connect = require('connect');
//connect().use(connect.static('build/webapp')).listen(8080);
connect().use(connect.static('src/main/webapp')).listen(8080);
