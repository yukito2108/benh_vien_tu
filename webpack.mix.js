const mix = require('laravel-mix');


mix.sass('assets/css/page/header.scss', 'assets/css/header.css');
mix.sass('assets/css/page/footer.scss', 'assets/css/footer.css');
mix.sass('assets/css/page/homepage.scss', 'assets/css/homepage.css');
mix.sass('assets/css/page/cart.scss', 'assets/css/cart.css');
mix.sass('assets/css/page/checkout.scss', 'assets/css/checkout.css');
mix.sass('assets/css/page/product-detail.scss', 'assets/css/product-detail.css');

mix.styles([
    'assets/css/header.css',
    'assets/css/footer.css',
    'assets/css/homepage.css',
    'assets/css/product-detail.css',
    'assets/css/cart.css',
    'assets/css/checkout.css',
], 'assets/css/style.css');
