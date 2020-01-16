const path = require('path');

module.exports = {
  entry: './src/app.js',
  output: {
    path: path.join(__dirname, '/static'),
    filename: 'js/app.js'
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: "babel-loader"
      }
    ]
  }
};