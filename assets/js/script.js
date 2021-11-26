$(document).ready(function () {
    $('.product-media-list').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        asNavFor: '.product-media-nav'
    });

    $('.product-media-nav').slick({
        slidesToShow: 3,
        asNavFor: '.product-media-list',
        dots: false,
        arrows: false,
        centerMode: true,
        focusOnSelect: true
    });

    $('.product-info-options-list').slick({
        dots: false,
        arrows: false,
        infinite: false,
        slidesToShow: 4,
        slidesToScroll: 4,
        margin: 30,
        centerPadding: '10px',
        loop: false,
    });

    $('.home-banner').slick({
        dots: false,
        arrows: true,
        infinite: false,
        slidesToShow: 1,
        slidesToScroll: 1,
        loop: false,
        prevArrow: '<button class="slick-prev fas fa-chevron-left"></button>',
        nextArrow: '<button class="slick-next fas fa-chevron-right"></button>'
    });

    $('.product-list-items').slick({
        dots: false,
        arrows: true,
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 5,
        loop: true,
        prevArrow: '<button class="slick-prev fas fa-chevron-left"></button>',
        nextArrow: '<button class="slick-next fas fa-chevron-right"></button>',
        responsive: [
            {
                breakpoint: 991,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 4,
                }
            },
            {
                breakpoint: 767,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                }
            },
            {
                breakpoint: 574,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2
                }
            }
        ]
    })

    $('.flash-sale').slick({
        dots: false,
        arrows: false,
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        loop: true,
    })

    $('.partner-list-items').slick({
        dots: false,
        arrows: true,
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        loop: true,
        prevArrow: '<button class="slick-prev fas fa-chevron-left"></button>',
        nextArrow: '<button class="slick-next fas fa-chevron-right"></button>'
    })

    receiveItem();
})

receiveItem = () => {
    let item = $('.checkout-receive-item');
    let content = $('.checkout-receive-content');

    item.on('click', function () {
        $(this).siblings().removeClass('checked');
        $(this).addClass('checked');
        $(this).parents('.checkout-receive').removeClass('active-store active-home')
        $(this).parents('.checkout-receive').addClass( `active-${$(this).attr('data-location')}`)
    })
}
