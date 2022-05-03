const path = require('path');

const { VueLoaderPlugin } = require('vue-loader');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const webpack = require('webpack');

const buildEnv = process.argv.includes('--mode=production')
  ? 'production'
  : 'development';
const devMode = buildEnv === 'development';
const jsPrefix = 'src/main/resources/frontend';

function entrypointPath(filename) {
  return path.join(__dirname, jsPrefix, filename);
}

module.exports = {
  mode: buildEnv,
  externals: {
    jquery: 'jQuery'
  },
  entry: {
    'annotation-admin': entrypointPath('annotation-admin.js'),
    judge: entrypointPath('judge.js'),
    person: entrypointPath('person.js'),
    'judge-entry': entrypointPath('judge-entry.js'), // TODO: temporary entry point for demo.
    'judge-control': entrypointPath('judge-control.js'),
    'pool-entries': entrypointPath('pool-entries.js')
  },
  resolve: {
    alias: {
      '@': path.join(__dirname, jsPrefix)
    },
    extensions: ['.js', '.vue', '.json']
  },
  output: {
    path: path.join(__dirname, 'target/classes/static/assets'),
    filename: 'js/[name].js'
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
        options: {
          presets: ['@babel/preset-env']
        }
      },
      {
        test: /\.css$/,
        use: [devMode ? 'style-loader' : MiniCssExtractPlugin.loader, 'css-loader']
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        loader: 'file-loader',
        options: {
          name: 'assets/img/[name].[ext]'
        }
      }
    ]
  },
  devtool: 'cheap-source-map',
  plugins: [
    new MiniCssExtractPlugin(),
    new VueLoaderPlugin(),
    new webpack.DefinePlugin({
      __VUE_OPTIONS_API__: JSON.stringify(true),
      __VUE_PROD_DEVTOOLS__: JSON.stringify(devMode)
    })
  ],
  optimization: {
    splitChunks: {
      cacheGroups: {
        vendor: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendor',
          chunks: 'initial'
        }
      }
    }
  }
};
