var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'src/client/public');
var APP_DIR = path.resolve(__dirname, 'src/client/app');

var config = {
  entry: {
	  index: APP_DIR + '/index.jsx',
	  questionHome:APP_DIR + '/questionHome.jsx',
	  posts:APP_DIR + '/posts.jsx',
	  postsDetails:APP_DIR + '/postsDetails.jsx',
	  posts_Component:APP_DIR + '/posts_Component.jsx'
	  },
  output: {
    path: BUILD_DIR,
    filename: '[name]-bundle.js'
  },
 module : {
    loaders : [
      {
        test : /\.jsx?/,
        include : APP_DIR,
        loader : 'babel-loader'
      }
    ]
  }
};

module.exports = config;